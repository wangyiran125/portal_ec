package com.chinalbs.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinalbs.beans.CommonAttributes;
import com.chinalbs.beans.Message;
import com.chinalbs.entity.User;
import com.chinalbs.framework.controller.BaseController;
import com.chinalbs.service.UserService;
import com.chinalbs.utils.ApiUtils;

/**
 * Controller -- Account
 * @author sujinxuan
 *
 */
@Controller("accountController")
@RequestMapping("/console/account")
public class AccountController extends BaseController {

	@Resource(name = "userServiceImpl")
	private UserService userService;

	/**
	 * 编辑个人信息页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/accountInfo", method = RequestMethod.GET)
	public String edit(ModelMap model) {
		model.addAttribute("user",userService.getCurrent());
		return "/console/account/edit";
	}
	

	/**
	 * 更新个人信息
	 * @param password
	 * @param nickName
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public @ResponseBody Message update(String password,String nickName,HttpSession session) {
    	  User user = userService.getCurrent();
    	  String oldPwd = user.getPassword();
    	  String userId = (String) session.getAttribute(CommonAttributes.API_USERID_SESSION);
    	  if(!StringUtils.isEmpty(password)){
    	    user.setPassword(DigestUtils.md5Hex(password));
    	  }
    	  user.setNickName(nickName);
    	  userService.update(user);
    	  if(!StringUtils.isEmpty(userId)){
    	    ApiUtils.apiEditUser(user, userId,oldPwd,session);
    	    if(!StringUtils.isEmpty(password)){
              ApiUtils.apiUpdatePwd(user, userId,oldPwd,session);
            }
    	   
    	  }
    	  
    	  return SUCCESS_MESSAGE;
	}

    
}