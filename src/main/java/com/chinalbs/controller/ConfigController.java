package com.chinalbs.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinalbs.beans.Message;
import com.chinalbs.entity.BackendModeUserExtend;
import com.chinalbs.entity.CustomizeFun;
import com.chinalbs.entity.DevModeUserExtend;
import com.chinalbs.entity.EnterpriseUserExtend;
import com.chinalbs.entity.IndividualUserExtend;
import com.chinalbs.entity.User;
import com.chinalbs.entity.User.ModeType;
import com.chinalbs.framework.controller.BaseController;
import com.chinalbs.service.BackendModeService;
import com.chinalbs.service.CustomizeFunService;
import com.chinalbs.service.DevModeUserExtendService;
import com.chinalbs.service.EnterpriseUserExtendService;
import com.chinalbs.service.IndividualUserExtendService;
import com.chinalbs.service.UserService;

@Controller
@RequestMapping("/console/config")
public class ConfigController extends BaseController{
  
  @Resource(name="userServiceImpl")
  private UserService userService;
  
  @Resource(name = "customizeFunServiceImpl")
  private CustomizeFunService customizeFunService;
  
  @Resource(name="enterpriseUserExtendServiceImpl")
  private EnterpriseUserExtendService enterpriseUserExtendService;
  
  @Resource(name="individualUserExtendServiceImpl")
  private IndividualUserExtendService individualUserExtendService;
  
  @Resource(name = "backendModeServiceImpl")
  private BackendModeService backendModeService;
  
  @Resource(name = "devModeUserExtendServiceImpl")
  private DevModeUserExtendService devModeUserExtService;
  
  @RequestMapping(value="show",method=RequestMethod.GET)
  public String show(ModelMap map,Long userId){
    User user=null;
    if(userId ==null){
      user = userService.getCurrent();
    }else{
      user =  userService.find(userId);
    }
    List<CustomizeFun> customizeFunList = customizeFunService.findAll();
    map.addAttribute("customizeFunList", customizeFunList);
    map.addAttribute("user", user);
    return "/console/config/show";
  }
  
  @RequestMapping(value="edit",method=RequestMethod.GET)
  public String edit(ModelMap map){
    User user = userService.getCurrent();
    map.addAttribute("user", user);
    if (user.getModeType().equals(ModeType.BACKENDMODE)) {
      List<CustomizeFun> customizeFunList = customizeFunService.findAll();
      map.addAttribute("customizeFunList", customizeFunList);
    }
    
    return "/console/config/edit";
  }
  
  @RequestMapping(value="update/enterprise",method=RequestMethod.POST)
  @ResponseBody
  public Message updateEnterprise(EnterpriseUserExtend enterpriseUserExtend,ModelMap map){
    User user = userService.getCurrent();
    enterpriseUserExtend.setId(user.getEnterpriseUserExtend().getId());
    String[] ignoreProperties = {"companyName","licenseNum","organizationCode","licenseImagePath","user"};
    enterpriseUserExtendService.update(enterpriseUserExtend, ignoreProperties);
    return SUCCESS_MESSAGE;
  }
  
  @RequestMapping(value="update/individual",method=RequestMethod.POST)
  @ResponseBody
  public Message updateIndividual(IndividualUserExtend individualUserExtend,ModelMap map){
    User user = userService.getCurrent();
    individualUserExtend.setId(user.getIndividualUserExtend().getId());
    String[] ignoreProperties = {"user","name","identityCard"};
    individualUserExtendService.update(individualUserExtend, ignoreProperties);
    return SUCCESS_MESSAGE;
  }
  
  @RequestMapping(value="update/backendMode",method=RequestMethod.POST)
  @ResponseBody
  public Message updateBackendMode(BackendModeUserExtend backendModeUserExtend, Long[] customizeFunsId,ModelMap map){
    User user = userService.getCurrent();
    backendModeUserExtend.setId(user.getBackendModeUserExtend().getId());
    String[] ignoreProperties = {"user"};
    
    List<CustomizeFun> list = customizeFunService.findList(customizeFunsId);
    Set<CustomizeFun> customizeFuns = new HashSet<CustomizeFun>(list);
    backendModeUserExtend.setUserCustomizeFuns(customizeFuns);
    
    backendModeService.update(backendModeUserExtend, ignoreProperties);
   
    
    return SUCCESS_MESSAGE;
  }
  
  @RequestMapping(value="update/devMode",method=RequestMethod.POST)
  @ResponseBody
  public Message updateDevMode(DevModeUserExtend devModeUserExtend,ModelMap map){
    User user = userService.getCurrent();
    DevModeUserExtend entity = user.getDevModeUserExtend();
    entity.setAlarmState(devModeUserExtend.getAlarmState());
    devModeUserExtService.update(entity);
    return SUCCESS_MESSAGE;
  }
  
}
