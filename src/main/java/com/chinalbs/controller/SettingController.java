package com.chinalbs.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.chinalbs.beans.Message;
import com.chinalbs.entity.EnterpriseUserExtend;
import com.chinalbs.entity.IndividualUserExtend;
import com.chinalbs.entity.User;
import com.chinalbs.entity.User.ModeType;
import com.chinalbs.entity.User.UserType;
import com.chinalbs.framework.controller.BaseController;
import com.chinalbs.service.EnterpriseUserExtendService;
import com.chinalbs.service.FileService;
import com.chinalbs.service.IndividualUserExtendService;
import com.chinalbs.service.RoleService;
import com.chinalbs.service.UserService;
import com.chinalbs.utils.ApiUtils;

@Controller("SettingController")
@RequestMapping("/console/setting")
public class SettingController extends BaseController{
  
  @Resource(name="userServiceImpl")
  private UserService userService;
  
  @Resource(name="enterpriseUserExtendServiceImpl")
  private EnterpriseUserExtendService enterpriseUserExtendService;
  
  @Resource(name="individualUserExtendServiceImpl")
  private IndividualUserExtendService individualUserExtendService;

  @Resource(name ="fileServiceImpl")
  private FileService fileService;
  
  @Resource(name ="roleServiceImpl")
  private RoleService roleService;
  
  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  public String edit(ModelMap map){
    try {
      User user = userService.getCurrent();
      map.addAttribute("user", user);
    } catch (Exception e) {
     e.printStackTrace();
    }
     
    
    return "/console/setting/guide";
  }
  
  /**
   * 
   * 修改用户类型
   */
  @RequestMapping(value = "/selectUserType", method = RequestMethod.POST)
  public @ResponseBody Message selectUserType(UserType userType,HttpSession session) {
    User user = userService.getCurrent();
    user.setUserType(userType);
    userService.update(user);
    ApiUtils.apiBackendUserType(user,session);
    return SUCCESS_MESSAGE;
  }
 
  /**
   * 修改用户操作类型
   * @param modeType
   * @return
   */
  @RequestMapping(value = "/selectModeType", method = RequestMethod.POST)
  public @ResponseBody
  Message selectModeType(ModeType modeType,HttpSession session) {
    User user = userService.getCurrent();
    user.setModeType(modeType);
    user.setConfIsEnable(0);
    userService.update(user);
    ApiUtils.apiBackendUserMode(user,session);
    return SUCCESS_MESSAGE;
  }
  
  
  /**
   * 保存企业信息
   * @param enterpriseUserExtend
   * @return
   */
  @RequestMapping(value = "/enterpriseUser/save", method = RequestMethod.POST)
  public @ResponseBody Message save( EnterpriseUserExtend enterpriseUserExtend) {
    User user = userService.getCurrent();
    enterpriseUserExtend.setUser(user);
    if(enterpriseUserExtend.getId()==null){
      enterpriseUserExtendService.save(enterpriseUserExtend);
    }else {
      enterpriseUserExtendService.update(enterpriseUserExtend);
    }
    user.setEnterpriseUserExtend(enterpriseUserExtend);
    userService.update(user);
    Message message = new Message();
    message.setContent(String.valueOf(enterpriseUserExtend.getId()));
    message.setType(Message.Type.success);
    return message;
  }
  
  /**
   * 保存个人用户信息
   * @param individualUserExtend
   * @return
   */
   @RequestMapping(value = "/individualUser/save", method = RequestMethod.POST)
   public @ResponseBody Message save( IndividualUserExtend individualUserExtend) {
     User user = userService.getCurrent();
     individualUserExtend.setUser(user);
     if(individualUserExtend.getId()==null){
       individualUserExtendService.save(individualUserExtend);
     }else {
       individualUserExtendService.update(individualUserExtend);
     }
     user.setIndividualUserExtend(individualUserExtend);
     userService.update(user);
     Message message = new Message();
     message.setContent(String.valueOf(individualUserExtend.getId()));
     message.setType(Message.Type.success);
     return message;
   }
   
   @RequestMapping(value = "license/file", method = RequestMethod.POST, consumes = "multipart/form-data")
   public  @ResponseBody Message create(
           @RequestParam(value = "licenseImagePath", required = true)  MultipartFile[] files) {
     
       String filePath = null;
       Message message = new Message();
      if (files.length > 0) {
             filePath = this.fileService.saveImage(files);
             System.out.println(filePath);
             message.setContent(filePath);
             message.setType(Message.Type.success);
       }
       return message;
   }
   
}
