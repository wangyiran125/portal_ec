package com.chinalbs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinalbs.beans.Message;
import com.chinalbs.common.log.LogUtil;
import com.chinalbs.entity.BackendModeUserExtend;
import com.chinalbs.entity.DevModeAPI;
import com.chinalbs.entity.DevModeAPITest;
import com.chinalbs.entity.DevModeUserExtend;
import com.chinalbs.entity.Role;
import com.chinalbs.entity.User;
import com.chinalbs.entity.User.ModeType;
import com.chinalbs.framework.controller.BaseController;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;
import com.chinalbs.json.APIDocApiJson;
import com.chinalbs.json.APIDocDetailJson;
import com.chinalbs.json.ApiDocJson;
import com.chinalbs.service.DevModeUserExtendService;
import com.chinalbs.service.RoleService;
import com.chinalbs.service.UserService;
import com.chinalbs.utils.ApiUtils;
import com.chinalbs.utils.JsonUtils;
import com.chinalbs.utils.SettingUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/console/devmode")
public class DevelopModeController extends BaseController {

  @Resource(name = "devModeUserExtendServiceImpl")
  private DevModeUserExtendService devModeUserExtService;
  @Resource(name = "userServiceImpl")
  private UserService userService;

  @Resource(name = "roleServiceImpl")
  private RoleService roleService;

  /**
   * 跳转到添加页面
   * 
   * @param modelMap
   * @return
   */
  @RequestMapping(value = "/change", method = RequestMethod.GET)
  public String changeMode(ModelMap model) {
    User user = userService.getCurrent();
    DevModeUserExtend devModeUserExtend=null;
    user.setModeType(ModeType.DEVMODE);
    userService.update(user);
    if (user.getDevModeUserExtend() == null) {
      BackendModeUserExtend backendModeUserExtend=user.getBackendModeUserExtend();
      
      devModeUserExtend= new DevModeUserExtend();
      devModeUserExtend.setDevId(user.getUserName());
      if (backendModeUserExtend.getSiteDomain() != null) {
        devModeUserExtend.setDevDomain(backendModeUserExtend.getSiteDomain());
      }
      else {
        devModeUserExtend.setDevDomain(backendModeUserExtend.getDefaultSiteDomain());
      }
    }
    else {
      devModeUserExtend=user.getDevModeUserExtend();
    }
    model.addAttribute("devModeUserExtend", devModeUserExtend);
    return "/console/devmode/add";
  }

  /**
   * 跳转到添加页面
   * 
   * @param modelMap
   * @return
   */
  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public String add(ModelMap model) {
    User user = userService.getCurrent();
    if (user.getBackendModeUserExtend() == null && user.getDevModeUserExtend()==null) {
      model.put("display", "display");
    }
      model.addAttribute("user", user);
      return "/console/devmode/add";
  }

  /**
   * 保存开发者基本信息
   * 
   * @param modelMap
   * @param devModeUserExtend
   * @return
   */
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public String save(DevModeUserExtend devModeUserExtend, ModeType modeType,HttpSession session) {
    User user = userService.getCurrent();
    /*
     * if (user.getModeType() != modeType) { user.setModeType(modeType); }
     */
    String key = devModeUserExtend.getDevId() + devModeUserExtend.getDevDomain();
    devModeUserExtend.setUser(user);
    devModeUserExtend.setDevKey(DigestUtils.md5Hex(key));
    /**
     * 设置用户角色 1.选择的是开发者模式，用户为开发者模式角色 2.选择的是后台模式,用户为后台模式角色
     */
    Set<Role> roles = new HashSet<Role>();
    Role role = null;
    if (ModeType.DEVMODE.equals(user.getModeType())) {
      role = roleService.find(Long.parseLong(SettingUtils.get().getDevelopmentModeRoleId()));
    } else {
      role = roleService.find(Long.parseLong(SettingUtils.get().getBackendModeRoleId()));
    }
    if (null != role) {
      roles.add(role);
    }
    if (LogUtil.isDebugEnabled(SettingController.class)) {
      LogUtil.debug(DevelopModeController.class, "selectModeType", "Setting user role with role id : %d",
          role.getId());
    }
    user.setRoles(roles);
    user.setConfIsEnable(1);
    userService.update(user);
    
    if(user.getDevModeUserExtend()!= null){
      DevModeUserExtend dbDevModeUserExtend=user.getDevModeUserExtend();
      devModeUserExtend.setId(dbDevModeUserExtend.getId());
      devModeUserExtService.update(devModeUserExtend);
    }
    else {
      devModeUserExtService.save(devModeUserExtend);  
    }
    ApiUtils.apiSetApiKey(devModeUserExtend, session);
    return "redirect:/console/common/main.jhtml";
  }

  /**
   * 显示开发者基本信息
   * 
   * @param modelMap
   * @return
   */
  @RequestMapping(value = "/show")
  public String show(ModelMap model) {

    DevModeUserExtend devModeUserExtend = userService.getCurrent().getDevModeUserExtend();

    model.addAttribute("devModeUserExtend", devModeUserExtend);
    return "/console/devmode/show";
  }

  /**
   * 遍历 API
   * 
   * @param map
   * @return
   */
  @RequestMapping(value = "/apilist")
  public String apiList(ModelMap model, HttpSession session) {
    List<DevModeAPI> apiList = new ArrayList<DevModeAPI>();
    try {
      String apis = ApiUtils.apiGetAllApi(session);
      Map<String, List<Map<String, Object>>> devModeAPIMap;
      devModeAPIMap = JsonUtils.getMapList(apis);
      List<Map<String, Object>> list = devModeAPIMap.get("apis");
      Assert.notNull(list);
      for (Map<String, Object> map : list) {
        DevModeAPI devModeAPI = new DevModeAPI();
        devModeAPI.setId(Integer.parseInt(map.get("id").toString()));
        devModeAPI.setApi(map.get("api").toString());
        devModeAPI.setApiName(map.get("apiName").toString());
        devModeAPI.setApiState(Integer.parseInt(map.get("status").toString()));
        apiList.add(devModeAPI);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }


    model.addAttribute("apiList", apiList);
    return "/console/devmode/apilist";
  }

  /**
   * 遍历开发者文档
   * 
   * @return
   */
  @RequestMapping(value = "/devdoclist")
  public String devdocList(ModelMap model, HttpSession session) {

    String apis = ApiUtils.apiGetApiDocList(session);
    ApiDocJson apiDocJson = new ApiDocJson();
    try {
      apiDocJson = new ObjectMapper().readValue(apis, ApiDocJson.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    List<APIDocApiJson> apiDocJsonList = apiDocJson.getApis();
    int i = 0;
    int j = 0;
    for (; i < apiDocJsonList.size(); i += j) {
      for (j = 0; (i+j + 1) < apiDocJsonList.size(); j++) {
        if (apiDocJsonList.get(i).getModelName() != null
            && apiDocJsonList.get(i).getModelName()
                .equals(apiDocJsonList.get(i+j + 1).getModelName())) {
          apiDocJsonList.get(i+j + 1).setModelName("");
        } else {
          break;
        }
      }
      j++;
    }
    model.addAttribute("apiDocJsonList", apiDocJsonList);

    return "/console/devmode/devdoclist";
  }
  
  @RequestMapping(value = "/viewapidoc")
  public String viewAPIDoc(ModelMap model , HttpSession session ,String id,String apiName){
    String rest = ApiUtils.apiGetApiDocDetail(session, id);
    Assert.notNull(rest);
    
    APIDocDetailJson apiDocDetailJson = null;
    try {
      apiDocDetailJson = new ObjectMapper().readValue(rest, APIDocDetailJson.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    model.addAttribute("apiDocDetail", apiDocDetailJson);
    model.addAttribute("apiName",apiName);
    
    return "/console/devmode/viewapidoc";
  }

  /**
   * 遍历API测试地址
   * 
   * @param map
   * @return
   */
  @RequestMapping(value = "/testlist")
  public String testList(ModelMap model, HttpSession session) {

    List<DevModeAPITest> tests = new ArrayList<DevModeAPITest>();
    DevModeAPITest test = new DevModeAPITest();

    test.setApi("导航");
    test.setApiAddress("www.chinagbs.com");

    DevModeAPITest test2 = new DevModeAPITest();

    test2.setApi("搜索");
    test2.setApiAddress("www.chinasearch.com");
    tests.add(test);
    tests.add(test2);
    String apiList = ApiUtils.apiGetAllApi(session);
    System.out.println(apiList);
    model.addAttribute("devModeAPITest", tests);
    return "/console/devmode/testlist";
  }


  /**
   * 生成apikey
   * 
   * @param modelMap
   * @param devModeUserExtend
   * @return
   */
  @RequestMapping(value = "/bulid", method = RequestMethod.POST)
  public @ResponseBody
  Message bulid(DevModeUserExtend devModeUserExtend, ModeType modeType) {
    String key = devModeUserExtend.getDevId() + devModeUserExtend.getDevDomain();
    Message message = new Message();
    message.setType(Message.Type.success);
    message.setContent(DigestUtils.md5Hex(key));

    return message;
  }
  
  
}
