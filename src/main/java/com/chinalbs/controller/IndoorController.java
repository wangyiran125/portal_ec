package com.chinalbs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinalbs.beans.CommonAttributes;
import com.chinalbs.beans.Message;
import com.chinalbs.entity.Beacon;
import com.chinalbs.entity.Coordinate;
import com.chinalbs.framework.controller.BaseController;
import com.chinalbs.framework.datasource.DataSourceContextHolder;
import com.chinalbs.framework.datasource.datatype.DataSourceType;
import com.chinalbs.framework.ordering.Ordering.Direction;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;
import com.chinalbs.service.BeaconService;
import com.chinalbs.service.CoordinateService;

@Controller
@RequestMapping("/console/indoor")
public class IndoorController extends BaseController {

	@Resource(name = "beaconServiceImpl")
	private BeaconService beaconService;

	@Resource(name = "coordinateServiceImpl")
	private CoordinateService coordinateService;

	//
	// @Resource(name = "fenceServiceImpl")
	// private FenceService fenceService;

	/**
	 * 列表
	 * 
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/beacon_list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model, HttpSession session) {
		try {
			String userId = (String) session
					.getAttribute(CommonAttributes.API_USERID_SESSION);
			Assert.notNull(userId);
			pageable.setOrderProperty("fMac");
			pageable.setOrderDirection(Direction.asc);
			DataSourceContextHolder.setDataSourceType(DataSourceType.CUST);

			Page<Beacon> pages = beaconService.findIndoorBeaconWithServiceId(
					Long.parseLong(userId), pageable);

			List<Coordinate> list = coordinateService
					.findIndoorCoordinateWithServiceId(Long.parseLong(userId));
			Map<String, String> map = new HashMap<String, String>();
			for (Coordinate c : list) {
				map.put(String.valueOf(c.getId()),
						c.getfLocation() + "-" + c.getfName());
			}

			model.addAttribute("page", pages);
			model.addAttribute("cmap", map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSourceContextHolder.setDataSourceType(DataSourceType.OWN);
		}
		return "/console/indoor/beacon_list";
	}

	/**
	 * 编辑
	 * 
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/beacon_edit", method = RequestMethod.GET)
	public String edit(Pageable pageable, HttpSession session, Beacon beacon,
			ModelMap model) {
		try {
			DataSourceContextHolder.setDataSourceType(DataSourceType.CUST);
			model.addAttribute("beacon",
					beaconService.findByMac(beacon.getfMac()));

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

			DataSourceContextHolder.setDataSourceType(DataSourceType.OWN);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/console/indoor/beacon_edit";
	}

	/**
	 * 添加
	 * 
	 * @param device
	 * @return
	 */
	@RequestMapping(value = "/beacon_add", method = RequestMethod.GET)
	public String add(Pageable pageable, ModelMap model, HttpSession session) {

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

		return "/console/indoor/beacon_add";
	}

	/**
	 * 保存
	 * 
	 * @param device
	 * @return
	 */
	@RequestMapping(value = "/beacon_save", method = RequestMethod.POST)
	public @ResponseBody
	Message save(Beacon beacon, HttpSession session) {

		/** 保存之前确认是否已经被添加过 */
		DataSourceContextHolder.setDataSourceType(DataSourceType.CUST);
		Beacon beaconFromCust = beaconService.findByMac(beacon.getfMac());
		if (beaconFromCust == null) {
			// ApiUtils.apiIndoorBeaconAdd(beacon, session);

			String userIdStr = (String) session
					.getAttribute(CommonAttributes.API_USERID_SESSION);
			Long userId = Long.valueOf(userIdStr);
			beacon.setfServiceId(userId);
			beaconService.save(beacon);

			DataSourceContextHolder.setDataSourceType(DataSourceType.OWN);
			return SUCCESS_MESSAGE;

		} else {
			DataSourceContextHolder.setDataSourceType(DataSourceType.OWN);
			return Message.error("User.device.exist");
		}
	}

	/**
	 * 更新
	 * 
	 * @param device
	 * @return
	 */
	@RequestMapping(value = "/beacon_update", method = RequestMethod.POST)
	public String update(Beacon beacon, HttpSession session) {
		// ApiUtils.apiIndoorBeaconEdit(beacon, session);

		DataSourceContextHolder.setDataSourceType(DataSourceType.CUST);

		String userIdStr = (String) session
				.getAttribute(CommonAttributes.API_USERID_SESSION);
		Long userId = Long.valueOf(userIdStr);
		beacon.setfServiceId(userId);
		beaconService.update(beacon);

		return "redirect:/console/indoor/beacon_list.jhtml";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/beacon_delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(String[] ids, HttpSession session) {
		DataSourceContextHolder.setDataSourceType(DataSourceType.CUST);
		// if (ids.length >= deviceService.count()) {
		// return Message.error("user.common.deleteAllNotAllowed");
		// }
		// DataSourceContextHolder.setDataSourceType(DataSourceType.OWN);
		// for (String id : ids) {
		// Beacon beacon = new Beacon();
		// beacon.setfMac(id);
		// ApiUtils.apiIndoorBeaconDelete(beacon, session);
		// }

		if (ids != null) {
			try {
				beaconService.delete(ids);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return SUCCESS_MESSAGE;
	}

}
