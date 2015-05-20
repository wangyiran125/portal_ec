package com.chinalbs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinalbs.entity.User;
import com.chinalbs.entity.User.ModeType;
import com.chinalbs.entity.User.UserType;
import com.chinalbs.framework.controller.BaseController;
import com.chinalbs.service.MailService;
import com.chinalbs.service.TemplateService;
import com.chinalbs.service.UserService;
import com.chinalbs.utils.SpringUtils;

/**
 * Controller - ESB Message controller
 * 
 */
@Controller("eSBMessageController")
@RequestMapping("/console/esb")
public class ESBMessageController extends BaseController {

	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "mailServiceImpl")
	private MailService mailService;
	@Resource(name = "templateServiceImpl")
	private TemplateService templateService;
	
	/**
     * send email warning
     */
    @RequestMapping(value = "/sendmail", method = RequestMethod.GET)
    public @ResponseBody
    boolean sendEmail(String api) {
    	List<User> userList = userService.findAll();
		boolean isAlarmOpen;
		String emailAddress;
		for (User user : userList) {
			
			isAlarmOpen = false;
			emailAddress = null;
			
			if (ModeType.BACKENDMODE.equals(user.getModeType())) {
				if (user.getBackendModeUserExtend() != null && user.getBackendModeUserExtend().getAlarmState()
						.equals(com.chinalbs.entity.BackendModeUserExtend.Alarm.OPEN))
				{
					isAlarmOpen = true;
				}
			}else if (ModeType.DEVMODE.equals(user.getModeType())) {
				if (user.getDevModeUserExtend() != null && user.getDevModeUserExtend().getAlarmState()
						.equals(com.chinalbs.entity.DevModeUserExtend.Alarm.OPEN))
				{
					isAlarmOpen = true;
				}
			}
			
			if (UserType.ENTERPRISE.equals(user.getUserType())) {
				if (user.getEnterpriseUserExtend() != null) {
					emailAddress = user.getEnterpriseUserExtend().getContactEmail();
				}
			}else if (UserType.INDIVIDUAL.equals(user.getUserType())) {
				if (user.getIndividualUserExtend() != null) {
					emailAddress = user.getIndividualUserExtend().getEmail();
				}
			}
			
			if (isAlarmOpen) {
		        String subject = SpringUtils.getMessage("api.notify.title", api);
		        String templatePath = templateService.get("apiNotifyMail").getTemplatePath();
		        Map<String, Object> model = new HashMap<String, Object>();
		        model.put("api", api);
		        mailService.send(emailAddress, subject, templatePath, model, true);
			}
		}
        
        return true;
    }

}