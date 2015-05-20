package com.chinalbs.controller;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinalbs.beans.AuthenticationToken;
import com.chinalbs.beans.Message;
import com.chinalbs.beans.Setting.CaptchaType;
import com.chinalbs.entity.User;
import com.chinalbs.framework.controller.BaseController;
import com.chinalbs.framework.entity.BaseEntity.Save;
import com.chinalbs.json.ApiCommonJson;
import com.chinalbs.json.ApiRegJson;
import com.chinalbs.service.CaptchaService;
import com.chinalbs.service.RSAService;
import com.chinalbs.service.UserService;
import com.chinalbs.utils.ApiUtils;
import com.chinalbs.utils.JsonUtils;
import com.chinalbs.utils.MD5Util;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Controller - register
 * 
 * @author sujinxuan
 * 
 */
@Controller("registerController")
@RequestMapping("/console/register")
public class RegisterController extends BaseController {

  @Resource(name = "rsaServiceImpl")
  private RSAService rsaService;
  @Resource(name = "userServiceImpl")
  private UserService userService;
  @Resource(name = "captchaServiceImpl")
  private CaptchaService captchaService;



  /**
   * 检查E-mail是否存在
   */
  @RequestMapping(value = "/check_email", method = RequestMethod.GET)
  public @ResponseBody
  boolean checkEmail(String email) {
    
    /** 检查客户数据库的用户名是否已经存在 */
    String apiResponse = ApiUtils.apiUserNameCheck(email);
    ApiCommonJson commonJson = null;
    try {
      commonJson = new ObjectMapper().readValue(apiResponse, ApiCommonJson.class);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Assert.notNull(commonJson);
    if (commonJson.getRet() == 2) {
      return false;
    }
    
    if (StringUtils.isEmpty(email)) {
      return false;
    }
    if (userService.emailExists(email)) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * 检查手机号码是否存在
   */
  @RequestMapping(value = "/check_mobile", method = RequestMethod.GET)
  public @ResponseBody
  boolean checkMobile(String mobile) {
    if (StringUtils.isEmpty(mobile)) {
      return false;
    }
    if (userService.mobileExists(mobile)) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * 检查用户名是否存在
   */
  @RequestMapping(value = "/check_username", method = RequestMethod.GET)
  public @ResponseBody
  boolean checkUserName(String username) {
    if (StringUtils.isEmpty(username)) {
      return false;
    }
    if (userService.userNameExists(username)) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * 用户注册提交
   * 
   * @param captchaId
   * @param captcha
   * @param nickname
   * @param mobile
   * @param password
   * @param email
   * @param request
   * @param response
   * @param session
   * @return
   */
  @RequestMapping(value = "/submit", method = RequestMethod.POST)
  public @ResponseBody
  Message submit(String captchaId, String captcha, String nickName, String mobile, String email,
      HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    String password = rsaService.decryptParameter("enPassword", request);
    rsaService.removePrivateKey(request);
    if (!captchaService.isValid(CaptchaType.userReg, captchaId, captcha)) {
      return Message.error("admin.captcha.invalid");
    }
    if (!isValid(User.class, "nickName", nickName, Save.class)
        || !isValid(User.class, "password", password, Save.class)
        || !isValid(User.class, "email", email, Save.class)
        || !isValid(User.class, "mobile", mobile, Save.class)) {
      return Message.error("admin.common.invalid");
    }
    if (userService.emailExists(email)) {
      return Message.error("admin.reg.emailExist");
    }

    User user = new User();
    user.setCreateDate(new Date());
    user.setEmail(email);
    user.setUserName(email);
    user.setMobile(mobile);
    user.setNickName(nickName);
    /** 使用客户提供的MD5工具 */
    user.setPassword(MD5Util.getMd5(password));
    // user.setPassword(DigestUtils.md5Hex(password));
    user.setIsSystem(0);

    String rs = ApiUtils.apiRegister(user);
    ApiRegJson apiRegJson = new ApiRegJson();
    try {
      apiRegJson = new ObjectMapper().readValue(rs, ApiRegJson.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    user.setAppName(apiRegJson.getApp_name());
    userService.save(user);
    AuthenticationToken authenticationToken =
        new AuthenticationToken(user.getUserName(), password, captchaId, captcha, false, null, true);
    SecurityUtils.getSubject().login(authenticationToken);

    return SUCCESS_MESSAGE;

  }

  /**
   * lbs用户注册
   * 
   * @param password
   * @param nickName
   * @param mobile
   * @param email
   * @param request
   * @param response
   * @param session
   * @return
   */
  @RequestMapping(value = "/lbsreg", method = RequestMethod.GET)
  public @ResponseBody
  void registerForlbs(String nickName, String email, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    boolean flag = true;
    Message msg = null;
    String callbackFunName = request.getParameter("callback");
    String password = rsaService.decryptParameter("enPassword", request);
    rsaService.removePrivateKey(request);
    if (!isValid(User.class, "nickName", nickName, Save.class)
        || !isValid(User.class, "password", password, Save.class)
        || !isValid(User.class, "email", email, Save.class)) {
      msg = Message.error("admin.common.invalid");
      flag = false;
    }

    if (userService.emailExists(email)) {
      msg = Message.error("admin.reg.emailExist");
      flag = false;
    }

    if (flag) {
      User user = new User();
      user.setCreateDate(new Date());
      user.setEmail(email);
      user.setUserName(email);
      user.setNickName(nickName);
      user.setPassword(MD5Util.getMd5(password));
      user.setIsSystem(0);
      String rs = ApiUtils.apiRegister(user);
      ApiRegJson apiRegJson = new ApiRegJson();
      try {
        apiRegJson = new ObjectMapper().readValue(rs, ApiRegJson.class);
      } catch (IOException e) {
        e.printStackTrace();
      }
      user.setAppName(apiRegJson.getApp_name());
      userService.save(user);
      msg = SUCCESS_MESSAGE;
    }
    try {
      response.getWriter().write(callbackFunName + "(" + JsonUtils.obj2Str(msg) + ")");
    } catch (IOException e) {
      e.printStackTrace();
    }
    response.setContentType("text/javascript");

  }
}
