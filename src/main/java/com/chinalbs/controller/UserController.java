package com.chinalbs.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chinalbs.beans.Message;
import com.chinalbs.entity.Role;
import com.chinalbs.entity.User;
import com.chinalbs.framework.controller.BaseController;
import com.chinalbs.framework.entity.BaseEntity.Save;
import com.chinalbs.framework.paging.Pageable;
import com.chinalbs.service.RoleService;
import com.chinalbs.service.UserService;

/**
 * Controller - user
 * 
 */
@Controller("userController")
@RequestMapping("/console/admin")
public class UserController extends BaseController {

	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "roleServiceImpl")
	private RoleService roleService;

	
	/**
     * 检查E-mail是否存在
     */
    @RequestMapping(value = "/check_email", method = RequestMethod.GET)
    public @ResponseBody
    boolean checkEmail(String email) {
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
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("roles", roleService.findAll());
		return "/console/admin/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(User user, Long[] roleIds, RedirectAttributes redirectAttributes) {
		user.setRoles(new HashSet<Role>(roleService.findList(roleIds)));
		if (!isValid(user, Save.class)) {
			return ERROR_VIEW;
		}
		if (userService.userNameExists(user.getUserName())) {
			return ERROR_VIEW;
		}
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		user.setLoginDate(null);
		user.setLoginIp(null);
		user.setIsSystem(0);
		userService.save(user);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("roles", roleService.findAll());
		model.addAttribute("user", userService.find(id));
		return "/console/admin/edit";
	}
	
	 /**
     * Admin 修改密码跳转
     */
    @RequestMapping(value = "/adminEdit", method = RequestMethod.GET)
    public String adminEdit(ModelMap model) {
        model.addAttribute("user", userService.getCurrent());
        return "/console/admin/admin_edit";
    }
    
    /**
     * Admin 修改密码
     */
    @RequestMapping(value = "/adminChangePwd", method = RequestMethod.POST)
    public String adminChangePwd(User user, RedirectAttributes redirectAttributes) {
        User puser = userService.find(user.getId());
        if (puser == null) {
            return ERROR_VIEW;
        }
        
        if (StringUtils.isNotEmpty(user.getPassword())) {
          puser.setPassword(DigestUtils.md5Hex(user.getPassword()));
          userService.update(puser);
        } 
        return "redirect:list.jhtml";
    }

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(User user, Long[] roleIds, RedirectAttributes redirectAttributes) {
	    List<Role> rolesList = roleService.findList(roleIds);
	    Set<Role> roles = new HashSet<Role>(rolesList);
	    user.setRoles(roles);
		if (!isValid(user)) {
			return ERROR_VIEW;
		}
		User puser = userService.find(user.getId());
		if (puser == null) {
			return ERROR_VIEW;
		}
		puser.setRoles(new HashSet<Role>(roleService.findList(roleIds)));
	    userService.update(puser);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", userService.findPage(pageable));
		return "/console/admin/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids.length >= userService.count()) {
			return Message.error("user.common.deleteAllNotAllowed");
		}
		userService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}