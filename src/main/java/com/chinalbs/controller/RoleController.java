
package com.chinalbs.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chinalbs.beans.Message;
import com.chinalbs.entity.Role;
import com.chinalbs.framework.controller.BaseController;
import com.chinalbs.framework.paging.Pageable;
import com.chinalbs.service.RoleService;

/**
 * Controller - 角色
 * 
 */
@Controller("roleController")
@RequestMapping("/console/role")
public class RoleController extends BaseController {

	@Resource(name = "roleServiceImpl")
	private RoleService roleService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add() {
		return "/console/role/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Role role, RedirectAttributes redirectAttributes) {
		if (!isValid(role)) {
			return ERROR_VIEW;
		}
		role.setIsSystem(false);
		role.setUsers(null);
		roleService.save(role);
	//	addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("role", roleService.find(id));
		return "/console/role/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Role role, RedirectAttributes redirectAttributes) {
		if (!isValid(role)) {
			return ERROR_VIEW;
		}
		Role pRole = roleService.find(role.getId());
		if (pRole == null || pRole.getIsSystem()) {
			return ERROR_VIEW;
		}
		roleService.update(role, "isSystem", "admins");
	//	addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", roleService.findPage(pageable));
		return "/console/role/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				Role role = roleService.find(id);
				if (role != null && (role.getIsSystem() || (role.getUsers() != null && !role.getUsers().isEmpty()))) {
					return Message.error("admin.role.deleteExistNotAllowed", role.getName());
				}
			}
			roleService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}

}