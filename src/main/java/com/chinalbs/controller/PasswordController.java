package com.chinalbs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinalbs.beans.CommonAttributes;
import com.chinalbs.beans.Message;
import com.chinalbs.beans.Setting.CaptchaType;
import com.chinalbs.entity.UsUser;
import com.chinalbs.entity.User;
import com.chinalbs.framework.controller.BaseController;
import com.chinalbs.framework.datasource.DataSourceContextHolder;
import com.chinalbs.framework.datasource.datatype.DataSourceType;
import com.chinalbs.framework.filter.Filter;
import com.chinalbs.framework.filter.Filter.Operator;
import com.chinalbs.framework.ordering.Ordering;
import com.chinalbs.framework.ordering.Ordering.Direction;
import com.chinalbs.service.CaptchaService;
import com.chinalbs.service.MailService;
import com.chinalbs.service.UsUserService;
import com.chinalbs.service.UserService;
import com.chinalbs.utils.ApiUtils;

/**
 * Controller -- Password
 * @author sujinxuan
 *
 */
@Controller("passwordController")
@RequestMapping("/console/password")
public class PasswordController extends BaseController {

    @Resource(name = "captchaServiceImpl")
    private CaptchaService captchaService;
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "mailServiceImpl")
	private MailService mailService;
	@Resource(name = "usUserServiceImpl")
    private UsUserService usUserService;

	/**
	 * 忘记密码页面
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/forgetPwd", method = RequestMethod.GET)
    public String forgetPwdPage(ModelMap model) {
        model.addAttribute("captchaId", UUID.randomUUID().toString());
        return "/console/password/findPassword";
    }
    
    
  
    /**
     * 发送重置密码邮件
     * @param model
     * @param email
     * @param captchaId
     * @param captcha
     * @param session
     * @return
     */
    @RequestMapping(value = "/sendPwdEmail", method = RequestMethod.POST)
    public @ResponseBody Message sendPwdEmail(ModelMap model,String email,String captchaId, String captcha,HttpSession session) {
      if (!captchaService.isValid(CaptchaType.findPassword, captchaId, captcha)) {
        return Message.error("admin.captcha.invalid");
    }
      if(!userService.emailExists(email)){
        return Message.error("admin.email.notExist");
      }
      String pwd_token = UUID.randomUUID().toString();
      session.setAttribute(CommonAttributes.PWD_TOKEN, DigestUtils.md5Hex(pwd_token));
      mailService.sendFindPasswordMail(email, email, pwd_token);
      return Message.success("admin.verify.pwdEmail");
    }
	
    /**
     * 重置密码页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/resetPwdPage", method = RequestMethod.GET)
    public String resetPwdPage(ModelMap model,String userName,String pwdToken) {
        model.addAttribute("captchaId", UUID.randomUUID().toString());
        model.addAttribute("userName",userName);
        model.addAttribute("pwdToken",pwdToken);
        return "/console/password/resetPassword";
    }
    
   
    /**
     * 重置密码
     * @param model
     * @param userName
     * @param captchaId
     * @param captcha
     * @param pwdToken
     * @param newPassword
     * @param session
     * @return
     */
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    public @ResponseBody Message resetPwd(ModelMap model,String userName,String captchaId,String captcha,String pwdToken,String newPassword,HttpSession session) {
      if (!captchaService.isValid(CaptchaType.findPassword, captchaId, captcha)) {
        return Message.error("admin.captcha.invalid");
      }
      if(!userService.emailExists(userName)){
        return Message.error("admin.email.notExist");
      }
      if(pwdToken!=null){
         if(!DigestUtils.md5Hex(pwdToken).equals(session.getAttribute(CommonAttributes.PWD_TOKEN))){
           return Message.error("admin.password.hasExpired");
         }
      }else{
         return Message.error("admin.password.hasExpired");
      }
      session.removeAttribute(CommonAttributes.PWD_TOKEN);
      User user = userService.findByUserName(userName);
      String oldPassword = user.getPassword();
      user.setPassword(DigestUtils.md5Hex(newPassword));
      userService.update(user);
      
      List<Filter> filters = new ArrayList<Filter>();
      Filter userNameFilter = new Filter("fName",Operator.eq, userName);
      filters.add(userNameFilter);
      List<Ordering> orders = new ArrayList<Ordering>();
      Ordering order = new Ordering("fId", Direction.asc);
      orders.add(order);
      DataSourceContextHolder.setDataSourceType(DataSourceType.CUST);
      List<UsUser> usUsers = usUserService.findList(null, filters, orders);
      DataSourceContextHolder.setDataSourceType(DataSourceType.OWN);
      if(usUsers == null || (usUsers!=null && usUsers.size()>1)){
        return Message.error("admin.email.notExist");
      }
      String userId = usUsers.get(0).getfId().toString();
      ApiUtils.apiUpdatePwd(user, userId, oldPassword,session);
    
      return SUCCESS_MESSAGE;
    }
    
}