package com.chinalbs.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinalbs.beans.CommonAttributes;
import com.chinalbs.beans.Message;
import com.chinalbs.beans.Server;
import com.chinalbs.beans.Setting;
import com.chinalbs.common.log.LogUtil;
import com.chinalbs.entity.Conductor;
import com.chinalbs.entity.Device;
import com.chinalbs.entity.HandSetToConductor;
import com.chinalbs.entity.Simulator;
import com.chinalbs.entity.Simulator.DeviceType;
import com.chinalbs.entity.Spot;
import com.chinalbs.entity.User;
import com.chinalbs.entity.UserDevice;
import com.chinalbs.framework.controller.BaseController;
import com.chinalbs.framework.datasource.DataSourceContextHolder;
import com.chinalbs.framework.datasource.datatype.DataSourceType;
import com.chinalbs.framework.ordering.Ordering.Direction;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;
import com.chinalbs.service.ConductorService;
import com.chinalbs.service.DeviceService;
import com.chinalbs.service.HandsetToConductorService;
import com.chinalbs.service.SpotJDBCService;
import com.chinalbs.service.SpotService;
import com.chinalbs.service.UserDeviceService;
import com.chinalbs.service.UserService;
import com.chinalbs.simulator.utils.BytesConvert;
import com.chinalbs.simulator.utils.MessageUtils;
import com.chinalbs.simulator.utils.WebConnectFactory;
import com.chinalbs.utils.ApiUtils;
import com.chinalbs.utils.ExcelUtils;
import com.chinalbs.utils.SettingUtils;

@Controller
@RequestMapping("/console/device")
public class DeviceController extends BaseController {

    @Resource(name = "deviceServiceImpl")
    private DeviceService             deviceService;

    @Resource(name = "spotServiceImpl")
    private SpotService               spotService;

    @Resource(name = "userDeviceServiceImpl")
    private UserDeviceService         userDeviceService;

    @Resource(name = "spotJDBCServiceImpl")
    private SpotJDBCService           spotJDBCService;

    @Resource(name = "userServiceImpl")
    private UserService               userService;

    @Resource(name = "conductorServiceImpl")
    private ConductorService          conductorService;

    @Resource(name = "handsetToConductorServiceImpl")
    private HandsetToConductorService handsetToConductorService;

    /**
     * 列表
     * 
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list (Pageable pageable, ModelMap model, HttpSession session) {

        try {
            String userId = (String) session.getAttribute (CommonAttributes.API_USERID_SESSION);
            Assert.notNull (userId);
            pageable.setOrderProperty ("fSn");
            pageable.setOrderDirection (Direction.asc);
            DataSourceContextHolder.setDataSourceType (DataSourceType.CUST);
            Page <Device> pages = deviceService.findDeviceWithServiceId (Long.parseLong (userId), pageable);

            for (Device device : pages.getContent ()) {
                List <UserDevice> userDevices = userDeviceService.findListBySn (device.getfSn ());
                if (userDevices.size () > 0) {
                    device.setIsBinding (true);
                    if (userDevices.get (0) != null && userDevices.get (0).getfUserId () != null) {
                        device.setTargetId (userDevices.get (0).getfUserId ());
                    }
                }
            }

            Boolean isDividedTable = Boolean.parseBoolean (SettingUtils.get ().getDividedTable ());

            if (isDividedTable) {
                DataSourceContextHolder.setDataSourceType (DataSourceType.SPOT);
            }

            for (Device device : pages.getContent ()) {
                List <Spot> spots = null;
                if (!isDividedTable) {
                    //** 没有采用分表来存储轨迹数据 **//*
                    spots = spotService.findSpotBySn (device.getfSn ());
                }
                else {
                    //** 采用分表来存储轨迹数据 **//*
                    spots = spotJDBCService.findSpotByDeviceSn (device.getfSn ());
                }
                if (spots == null || spots.size () < 1) {
                    device.setIsUploadSpot (false);
                }
                else {
                    device.setIsUploadSpot (true);
                    device.setUploadDate (new Date (spots.get (0).getfReceive ()));
                    device.setFirstUploadDate (new Date (spots.get (0).getfReceive ()));
                    device.setLastUploadDate (new Date (spots.get (spots.size () - 1).getfReceive ()));
                }
            }
            model.addAttribute ("page", pages);
        }
        catch (Exception e) {
            e.printStackTrace ();
        }
        finally {
            DataSourceContextHolder.setDataSourceType (DataSourceType.OWN);
        }
        return "/console/device/list";
    }

    /**
     * 跳转到批量上传页面
     * 
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping(value = "/batchAdd", method = RequestMethod.GET)
    public String batchAdd (Device device, ModelMap model) {

        return "/console/device/batch_add";
    }

    /**
     * 批量上传
     * 
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping(value = "/batchAddSave", method = RequestMethod.POST)
    public String batchAddSave (Device device, ModelMap model, HttpSession session) {

        Message message = new Message ();
        int successCount = 0;// 成功条数
        int faileCount = 0;// 失败条数
        List <String> dupfailDeviceIds = new ArrayList <String> ();
        List <String> formatFailDeviceIds = new ArrayList <String> ();
        try {
            String fileSuffix = device.getFile ().getOriginalFilename ().split ("\\.") [1];
            List <Device> devices = null;

            if (fileSuffix.toUpperCase ().equals ("XLS")) {
                devices = ExcelUtils.processingExcel_XLS (device.getFile ().getInputStream ());
            }
            else if (fileSuffix.toUpperCase ().equals ("XLSX")) {
                devices = ExcelUtils.processingExcel_XLSX (device.getFile ().getInputStream ());
            }

            model.addAttribute ("device", device);

            if (LogUtil.isDebugEnabled (DeviceController.class)) {
                LogUtil.debug (DeviceController.class, "batchAddSave",
                        "Load data from excel successful and count is : %s", devices.size ());
            }

            if (devices.size () > 3000) {
                if (LogUtil.isDebugEnabled (DeviceController.class)) {
                    LogUtil.debug (DeviceController.class, "batchAddSave", "batchAddSave", "Rows exceed 3000 : %s",
                            devices.size ());
                }
                message.setType (Message.Type.error);
                message.setContent ("gutid.batch.upload.err");
                model.addAttribute ("resMessage", message);
                return "/console/device/batch_add";
            }

            String userId = (String) session.getAttribute (CommonAttributes.API_USERID_SESSION);
            User user = userService.getCurrent ();
            DataSourceContextHolder.setDataSourceType (DataSourceType.CUST);
            for (Device deviceTemp : devices) {
                try {
                    if (deviceTemp.getfSn () != null && deviceTemp.getfPhone () != null) {
                        if (deviceTemp.getfSn ().length () != 15) {
                            formatFailDeviceIds.add (deviceTemp.getfSn ());
                            faileCount++;
                        }
                        else {

                            deviceTemp.setfType (device.getfType ());
                            deviceTemp.setfProtocol (device.getfProtocol ());
                            deviceTemp.setfIsvirtual (device.getfIsvirtual ());

                            // ApiUtils.apiDeviceAdd(device, session);
                            deviceTemp.setfServiceId (Long.parseLong (userId));
                            deviceTemp.setfAppName (user.getAppName ());
                            deviceTemp.setfStamp (new java.util.Date ().getTime ());
                            /**默认开启*/
                            deviceTemp.setfStatus (1);
                            if (deviceTemp.getfType () == 1) {
                                deviceTemp.setfFenceSwitch (2);
                                deviceTemp.setfMoveingSwitch (2);
                                deviceTemp.setfSpeedingSwitch (2);
                                deviceTemp.setfTick (30);
                                deviceTemp.setfSpeedThreshold (120);
                                deviceTemp.setfIcon ("1.png");
                            }
                            else if (deviceTemp.getfType () == 2) {
                                deviceTemp.setfTick (240);
                                deviceTemp.setfFenceSwitch (2);
                                deviceTemp.setfWeight (0.0f);
                                deviceTemp.setfBirth (0l);
                                deviceTemp.setfAge (0);
                                deviceTemp.setfHeight (0.0f);
                                deviceTemp.setfIcon ("2.png");
                            }
                            else if (deviceTemp.getfType () == 3) {
                                deviceTemp.setfSpeedingSwitch (2);
                                deviceTemp.setfTick (60);
                                deviceTemp.setfFenceSwitch (2);
                                deviceTemp.setfSosNum ("");
                                deviceTemp.setfSpeedThreshold (30);
                                deviceTemp.setfIcon ("3.png");
                            }

                            deviceService.save (deviceTemp);

                            if (LogUtil.isDebugEnabled (DeviceController.class)) {
                                LogUtil.debug (DeviceController.class, "batchAddSave",
                                        "Device saved , Sn : %s , ServiceId : %s", deviceTemp.getfSn (),
                                        deviceTemp.getfServiceId ());
                            }
                            successCount++;
                        }
                    }
                    else {
                        formatFailDeviceIds.add (deviceTemp.getfSn () + "\n");
                        faileCount++;

                    }
                }
                catch (Exception e) {
                    faileCount++;
                    dupfailDeviceIds.add (deviceTemp.getfSn ());
                }
            }
            if (successCount > 0) {
                message.setType (Message.Type.success);
                message.setContent (message ("gutid.batch.upload.success"));
                model.addAttribute ("resMessage", message);
            }
            else {
                message.setType (Message.Type.error);
                message.setContent ("gutid.batch.upload.fail");
                model.addAttribute ("resMessage", message);
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
        }
        finally {
            DataSourceContextHolder.setDataSourceType (DataSourceType.OWN);
        }
        model.addAttribute ("successCount", successCount);
        model.addAttribute ("faileCount", faileCount);
        model.addAttribute ("dupfailDeviceIds", dupfailDeviceIds);
        model.addAttribute ("formatFailDeviceIds", formatFailDeviceIds);
        return "/console/device/batch_add";
    }

    /**
     * 编辑
     * 
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit (Device device, ModelMap model) {

        try {
            DataSourceContextHolder.setDataSourceType (DataSourceType.CUST);
            model.addAttribute ("device", deviceService.findBySn (device.getfSn ()));
            DataSourceContextHolder.setDataSourceType (DataSourceType.OWN);
        }
        catch (Exception e) {
            e.printStackTrace ();
        }

        return "/console/device/edit";
    }

    /**
     * 添加
     * 
     * @param device
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add (ModelMap model, HttpSession session) {

        String userId = (String) session.getAttribute (CommonAttributes.API_USERID_SESSION);
        List <Conductor> conductors = conductorService.findAll ();
        model.put ("conductors", conductors);
        return "/console/device/add";
    }

    /**
     * 添加
     * 
     * @param device
     * @return
     */
    @RequestMapping(value = "/uploadSpot", method = RequestMethod.GET)
    public String uploadSpot () {

        return "/console/device/uploadSpot";
    }

    /**
     * 保存
     * 
     * @param device
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody Message save (Device device, HttpSession session) {

        /** 保存之前确认设备是否已经被添加过 */
        DataSourceContextHolder.setDataSourceType (DataSourceType.CUST);
        Device deviceFromCust = deviceService.find (device.getfSn ());
        if (deviceFromCust == null) {
            ApiUtils.apiDeviceAdd (device, session);
            DataSourceContextHolder.setDataSourceType (DataSourceType.OWN);
            return SUCCESS_MESSAGE;

        }
        else {
            DataSourceContextHolder.setDataSourceType (DataSourceType.OWN);
            return Message.error ("User.device.exist");
        }
        // return "redirect:/console/device/list.jhtml";
    }

    /**
     * 更新
     * 
     * @param device
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update (Device device, HttpSession session) {

        ApiUtils.apiDeviceEdit (device, session, (long) device.getfStatus ());
        return "redirect:/console/device/list.jhtml";
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody Message delete (String [] ids, HttpSession session) {

        DataSourceContextHolder.setDataSourceType (DataSourceType.CUST);
        if (ids.length >= deviceService.count ()) {
            return Message.error ("user.common.deleteAllNotAllowed");
        }
        DataSourceContextHolder.setDataSourceType (DataSourceType.OWN);
        for (String id : ids) {
            Device device = new Device ();
            device.setfSn (id);
            ApiUtils.apiDeviceEdit (device, session, 3l);
        }
        return SUCCESS_MESSAGE;
    }

    /**
     * 删除指挥机
     */
    @RequestMapping(value = "/deleteConductors", method = RequestMethod.POST)
    public @ResponseBody Message deleteConductors (String [] ids, HttpSession session) {

//            DataSourceContextHolder.setDataSourceType(DataSourceType.CUST);
        try {
            DataSourceContextHolder.setDataSourceType (DataSourceType.OWN);
            if (ids.length >= conductorService.count ()) {
                return Message.error ("user.common.deleteAllNotAllowed");
            }
            List <String> deviceSns = new ArrayList <String> ();
            List <Long> handsIds = new ArrayList <Long> ();
            List <HandSetToConductor> handSetToConductors = new ArrayList <HandSetToConductor> ();
            for (String id : ids) {
                Conductor conductor = conductorService.find (Long.parseLong (id));
                conductorService.delete (Long.parseLong (id));
                DataSourceContextHolder.setDataSourceType(DataSourceType.CUST);
                handSetToConductors = handsetToConductorService.findByConductorSn (conductor.getSn ());
                for (HandSetToConductor handSetToConductor : handSetToConductors) {
                    deviceSns.add (handSetToConductor.getDeviceSn ());
                    handsIds.add (handSetToConductor.getId ());
                }
                DataSourceContextHolder.setDataSourceType (DataSourceType.OWN);
            }
            DataSourceContextHolder.setDataSourceType(DataSourceType.CUST);
            for (HandSetToConductor htc : handSetToConductors) {
                handsetToConductorService.deleteById (htc.getId ());
            }
            DataSourceContextHolder.setDataSourceType (DataSourceType.OWN);
            for (String id : deviceSns) {
                Device device = new Device ();
                device.setfSn (id);
                ApiUtils.apiDeviceEdit (device, session, 3l);
            }
            return SUCCESS_MESSAGE;
        }
        catch (Exception e) {
            e.printStackTrace ();
        }
        return SUCCESS_MESSAGE;
    }

    /**
     * 查看最新位置点
     * 
     * @param fsn
     * @return
     */
    @RequestMapping(value = "/getLast")
    public String getLast (String fSn, String target_id, HttpSession session, ModelMap model) {

        String userId = (String) session.getAttribute (CommonAttributes.API_USERID_SESSION);
        String token = (String) session.getAttribute (CommonAttributes.API_TOKEN_SESSION);
        Setting setting = SettingUtils.get ();
        String apiUrl = setting.getApiUrl ();
        if (LogUtil.isDebugEnabled (DeviceController.class)) {
            LogUtil.debug (DeviceController.class, "API URL", "API URL is : %s", apiUrl);
        }
        /**接口改动,改为了get.position.do,和原来的区别是不用传target_id
         * 也就是说设备未绑定也能看最新位置
         * */
        //    String url = apiUrl + setting.getApiGetStatus();
        String url = apiUrl + setting.getApiLatestPosition ();
        model.put ("userId", userId);
        model.put ("token", token);
        model.put ("url", url);
        model.put ("fSn", fSn);
        /***/
        //    model.put("target_id", target_id);
        return "/console/device/map/location";
    }

    /**
     * 查看最近3天位置点
     * 
     * @param fsn
     * @return
     */
    @RequestMapping(value = "/getTrack")
    public String getTrack (String fSn, String target_id, HttpSession session, ModelMap model) {

        String userId = (String) session.getAttribute (CommonAttributes.API_USERID_SESSION);
        String token = (String) session.getAttribute (CommonAttributes.API_TOKEN_SESSION);
        Setting setting = SettingUtils.get ();
        String url = setting.getApiUrl () + setting.getApiGetTrack ();
        model.put ("userId", userId);
        model.put ("token", token);
        model.put ("url", url);
        model.put ("fSn", fSn);
        model.put ("target_id", target_id);
        return "/console/device/map/track";
    }

    /**
     * 位置点上传
     * 
     * @param
     * @return
     */
    @RequestMapping(value = "/simulator")
    public String simulator (Device device, HttpSession session, ModelMap model) {

        model.addAttribute ("device", device);
        return "/console/device/map/simulator";
    }

    /**
     * 保存位置点上传信息
     * 
     * @param
     * @return
     */
    @RequestMapping(value = "/simulator/save", method = RequestMethod.POST)
    public @ResponseBody String simulatorSave (Simulator simulator, Server server, HttpSession session, ModelMap model) {

        Setting setting = SettingUtils.get ();
        server.setIp (setting.getSimulatorServerIp ());
        server.setPort (setting.getSimulatorServerPort ());
        //chrome使用message返回reponse有异常
        //Message message = new Message();
        String message = new String ();

        if (simulator.getDeviceType ().equals (DeviceType.OBD.value ())) {
            if (session.getAttribute ("login-" + server.getDeviceType ()) == null
                    || !simulator.getSn ().equals (session.getAttribute ("login"))) {
                byte [] bs = BytesConvert.decodeHex ("676701000B00010" + simulator.getSn () + "00");
                WebConnectFactory.send (session.getId (), server, bs);
                session.setAttribute ("login-" + server.getDeviceType (), simulator.getSn ());
                try {
                    Thread.sleep (3000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace ();
                }

                long times = Calendar.getInstance ().getTimeInMillis () / 1000;

                String date = BytesConvert.encodeHexStr (BytesConvert.long2Bytes (times, 4));

                byte [] bytes = BytesConvert.decodeHex ("676702001B0001" + date + "044A1C41" + "0C7B5919" + "AE"
                        + "0153" + "01CC000010DD001FF2" + "01");
                if (simulator.getLongitude () != null && simulator.getLatitude () != null) {
                    BigDecimal bd = new BigDecimal (simulator.getLongitude ());
                    byte [] lngByte = BytesConvert.getBytes (bd.multiply (new BigDecimal (1800000)).intValue ());
                    byte [] latByte = BytesConvert.getBytes (new BigDecimal (simulator.getLatitude ()).multiply (
                            new BigDecimal (1800000)).intValue ());
                    for (int i = 11; i < 15; i++) {
                        bytes [i] = latByte [i - 11];
                    }

                    for (int i = 15; i < 19; i++) {
                        bytes [i] = lngByte [i - 15];
                    }
                }
                message = "success";
            }
        }
        else if (simulator.getDeviceType ().equals (DeviceType.T808.value ())) {
            message = "warn";
        }
        else {
            String msg = new String ();
            msg = MessageUtils.getMessage (simulator, 35);
            WebConnectFactory.send (session.getId (), server, msg);
            message = "success";
        }

        try {
            LogUtil.debug (DeviceController.class, "simulatorSave",
                    "waiting 6 seconds for the socket transfer complete...");
            Thread.sleep (6000);
            LogUtil.debug (DeviceController.class, "simulatorSave", "return the result...");
        }
        catch (InterruptedException e) {
            e.printStackTrace ();
        }

        return message;
    }

    /**
     * 添加指挥机
     */
    @RequestMapping(value = "/addConductor", method = RequestMethod.GET)
    public String addConductor () {

        return "/console/device/addConductor";
    }

    /**
     * 保存指挥机
     */
    @RequestMapping(value = "/saveConductor", method = RequestMethod.POST)
    public @ResponseBody Message saveConductor (Conductor conductor, ModelMap modelMap) {

        try {
            boolean exist = conductorService.existBySn (conductor.getSn ());
            if (exist) {
                return Message.error ("该sn已存在");
            }
            else {
                User user = userService.getCurrent ();
                conductorService.saveWithUserId (conductor, user.getId ());
                return SUCCESS_MESSAGE;
            }
        }
        catch (Exception e) {
            e.printStackTrace ();
            return Message.error ("查询异常");
        }

    }

    /**
     * 列表
     * 
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping(value = "/listConductor", method = RequestMethod.GET)
    public String listConductor (Pageable pageable, ModelMap model, HttpSession session) {

        //	  String userId = (String) session.getAttribute(CommonAttributes.API_USERID_SESSION);
        long userId = userService.getCurrent ().getId ();
        Page <Conductor> pages;
        try {
            pages = conductorService.findByUserId (userId, pageable);
            model.addAttribute ("page", pages);
        }
        catch (Exception e) {
            e.printStackTrace ();
        }
        return "/console/device/listConductor";
    }
}
