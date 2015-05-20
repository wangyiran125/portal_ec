package com.chinalbs.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chinalbs.beans.CommonAttributes;
import com.chinalbs.beans.Message;
import com.chinalbs.entity.Coordinate;
import com.chinalbs.entity.Role;
import com.chinalbs.framework.controller.BaseController;
import com.chinalbs.framework.datasource.DataSourceContextHolder;
import com.chinalbs.framework.datasource.datatype.DataSourceType;
import com.chinalbs.framework.ordering.Ordering.Direction;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;
import com.chinalbs.service.CoordinateService;
import com.chinalbs.utils.ApiUtils;

@Controller
@RequestMapping("/console/indoor")
public class IndoorCoordinateController extends BaseController {

	@Resource(name = "coordinateServiceImpl")
	private CoordinateService coordinateService;

	/**
	 * 列表
	 * 
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/coordinate_list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model, HttpSession session) {
		try {
			String userId = (String) session
					.getAttribute(CommonAttributes.API_USERID_SESSION);
			Assert.notNull(userId);
			pageable.setOrderProperty("id");
			pageable.setOrderDirection(Direction.asc);
			DataSourceContextHolder.setDataSourceType(DataSourceType.CUST);
			Page<Coordinate> pages = coordinateService
					.findIndoorCoordinateWithServiceId(Long.parseLong(userId),
							pageable);
			model.addAttribute("page", pages);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSourceContextHolder.setDataSourceType(DataSourceType.OWN);
		}
		return "/console/indoor/coordinate_list";
	}

	/**
	 * 编辑
	 * 
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/coordinate_edit", method = RequestMethod.GET)
	public String edit(Coordinate coordinate, ModelMap model) {
		try {
			DataSourceContextHolder.setDataSourceType(DataSourceType.CUST);
			model.addAttribute("coordinate", coordinateService.findById(String
					.valueOf(coordinate.getId())));
			DataSourceContextHolder.setDataSourceType(DataSourceType.OWN);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/console/indoor/coordinate_edit";
	}

	/**
	 * 添加
	 * 
	 * @param device
	 * @return
	 */
	@RequestMapping(value = "/coordinate_add", method = RequestMethod.GET)
	public String add() {
		return "/console/indoor/coordinate_add";
	}

	/**
	 * 保存
	 * 
	 * @param device
	 * @return
	 */
	@RequestMapping(value = "/coordinate_save", method = RequestMethod.POST)
	public @ResponseBody
	Message save(Coordinate coordinate, HttpSession session) {

		/** 保存之前确认是否已经被添加过 */
		DataSourceContextHolder.setDataSourceType(DataSourceType.CUST);
		// Coordinate coordinateFromCust = coordinateService.find(String
		// .valueOf(coordinate.getId()));
		// if (coordinateFromCust == null) {
		// ApiUtils.apiIndoorCoordinateAdd(coordinate, session);

		String userIdStr = (String) session
				.getAttribute(CommonAttributes.API_USERID_SESSION);
		Long userId = Long.valueOf(userIdStr);
		coordinate.setfServiceId(userId);
		try {
			coordinateService.save(coordinate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		DataSourceContextHolder.setDataSourceType(DataSourceType.OWN);
		return SUCCESS_MESSAGE;

		// } else {
		// DataSourceContextHolder.setDataSourceType(DataSourceType.OWN);
		// return Message.error("User.device.exist");
		// }
	}

	/**
	 * 更新
	 * 
	 * @param device
	 * @return
	 */
	@RequestMapping(value = "/coordinate_update", method = RequestMethod.POST)
	public String update(Coordinate coordinate, HttpSession session) {
		// ApiUtils.apiIndoorCoordinateEdit(coordinate, session);

		DataSourceContextHolder.setDataSourceType(DataSourceType.CUST);
		
		String userIdStr = (String) session
				.getAttribute(CommonAttributes.API_USERID_SESSION);
		Long userId = Long.valueOf(userIdStr);
		coordinate.setfServiceId(userId);
		coordinateService.update(coordinate);

		return "redirect:/console/indoor/coordinate_list.jhtml";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/coordinate_delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids, HttpSession session) {
		DataSourceContextHolder.setDataSourceType(DataSourceType.CUST);
		// if (ids.length >= deviceService.count()) {
		// return Message.error("user.common.deleteAllNotAllowed");
		// }
//		DataSourceContextHolder.setDataSourceType(DataSourceType.OWN);
		if (ids != null) {
			coordinateService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}

}
