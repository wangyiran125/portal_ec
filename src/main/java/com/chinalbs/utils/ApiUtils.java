package com.chinalbs.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpSession;

import org.springframework.util.Assert;

import com.chinalbs.beans.CommonAttributes;
import com.chinalbs.beans.Setting;
import com.chinalbs.common.log.LogUtil;
import com.chinalbs.entity.DevModeUserExtend;
import com.chinalbs.entity.Device;
import com.chinalbs.entity.Beacon;
import com.chinalbs.entity.User;

/**
 * API工具类
 * 
 * @author sujinxuan
 * 
 */
public final class ApiUtils {

	public static Setting setting = SettingUtils.get();

	/**
	 * 向基础API发送请求
	 * 
	 * @param controller
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	private static String sendPost(String controller, String parameters,
			String operation) {

		StringBuffer response = new StringBuffer();
		try {
			String domain = setting.getApiUrl() + controller;

			if (LogUtil.isDebugEnabled(ApiUtils.class)) {
				LogUtil.debug(ApiUtils.class, operation,
						"Request API URL is : %s", domain + "?" + parameters);
			}

			URL url = new URL(domain);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.write(parameters.getBytes());
			// wr.writeBytes(parameters);
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (LogUtil.isDebugEnabled(ApiUtils.class)) {
			LogUtil.debug(ApiUtils.class, "Response",
					"Response from API is : %s", response.toString());
		}
		return response.toString();
	}

	/**
	 * API用户注册
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 * @author sujinxuan
	 */
	public static String apiRegister(User user) {
		String param = "";
		if (user.getMobile() != null) {// EC调用API参数
			param = "type=2&name=" + user.getEmail() + "&pwd="
					+ user.getPassword() + "&phone=" + user.getMobile()
					+ "&nick_name=" + user.getNickName() + "&plat_form=1";
		} else {// chinalbs通过EC调用API参数
			param = "type=2&name=" + user.getEmail() + "&pwd="
					+ user.getPassword() + "&nick_name=" + user.getNickName()
					+ "&plat_form=1";
		}

		String rs = sendPost(setting.getApiRegister(), param, "Register");
		return rs;

	}

	/**
	 * API概要统计
	 * 
	 * @param serviceId
	 * @return
	 * @author sujinxuan
	 */
	public static String apiViewStatistics(String serviceId, HttpSession session) {
		String token = (String) session
				.getAttribute(CommonAttributes.API_TOKEN_SESSION);
		String userId = (String) session
				.getAttribute(CommonAttributes.API_USERID_SESSION);
		String param = "user_id=" + userId + "&service_id=" + serviceId
				+ "&token=" + token;
		String rs = sendPost(setting.getApiViewStatistics(), param,
				"Statistics");
		return rs;

	}

	public static String apiDeviceStatistics(String serviceId,
			HttpSession session) {
		String token = (String) session
				.getAttribute(CommonAttributes.API_TOKEN_SESSION);
		String userId = (String) session
				.getAttribute(CommonAttributes.API_USERID_SESSION);
		String param = "user_id=" + userId + "&service_id=" + serviceId
				+ "&token=" + token;
		String rs = sendPost(setting.getApiDeviceStatistics(), param,
				"Statistics");
		return rs;
	}

	public static String apiUserStatistics(String serviceId, HttpSession session) {
		String token = (String) session
				.getAttribute(CommonAttributes.API_TOKEN_SESSION);
		String userId = (String) session
				.getAttribute(CommonAttributes.API_USERID_SESSION);
		String param = "user_id=" + userId + "&service_id=" + serviceId
				+ "&token=" + token;
		String rs = sendPost(setting.getApiUserStatistics(), param,
				"Statistics");
		return rs;
	}

	/**
	 * API编辑用户（当前只修改用户昵称）
	 * 
	 * @param user
	 * @param session
	 * @return
	 * @author sujinxuan
	 */
	public static String apiEditUser(User user, String userId, String oldPwd,
			HttpSession session) {
		String token = (String) session
				.getAttribute(CommonAttributes.API_TOKEN_SESSION);
		String ts = String.valueOf((int) (Math.random() * 100));
		String pwd = SpringUtils.getMd5(SpringUtils.getMd5(oldPwd) + ts);
		String param = "user_id=" + userId + "&pwd=" + pwd + "&operate=2"
				+ "&app_name=app_name" + "&target_id=" + userId + "&nick="
				+ user.getNickName() + "&ts=" + ts + "&token=" + token;
		return sendPost(setting.getApiEditUser(), param, "EditUser");
	}

	/**
	 * API修改密码
	 * 
	 * @param user
	 * @param userId
	 * @param oldPwd
	 * @return
	 * @author sujinxuan
	 */
	public static String apiUpdatePwd(User user, String userId, String oldPwd,
			HttpSession session) {
		String token = (String) session
				.getAttribute(CommonAttributes.API_TOKEN_SESSION);
		String ts = String.valueOf((int) (Math.random() * 100));
		String pwd = SpringUtils.getMd5(SpringUtils.getMd5(oldPwd) + ts);
		String newPwd = user.getPassword();
		String param = "user_id=" + userId + "&password=" + pwd
				+ "&new_password=" + newPwd + "&ts=" + ts + "&token=" + token;
		return sendPost(setting.getApiUpatePwd(), param, "UpdatePwd");
	}

	/**
	 * API用户登陆
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public static String apiLogin(User user) {
		String ts = String.valueOf((int) (Math.random() * 100));
		String pwd = SpringUtils.getMd5(SpringUtils.getMd5(user.getPassword())
				+ ts);
		String param = "username=" + user.getUserName() + "&pwd=" + pwd
				+ "&ts=" + ts;
		return sendPost(setting.getApiLogin(), param, "Login");

	}

	/**
	 * API选择用户类型
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public static String apiBackendUserType(User user, HttpSession session) {

		String userId = (String) session
				.getAttribute(CommonAttributes.API_USERID_SESSION);
		String token = (String) session
				.getAttribute(CommonAttributes.API_TOKEN_SESSION);
		Assert.notNull(userId);
		String param = "user_id=" + userId + "&user_type="
				+ (user.getUserType().ordinal() + 1) + "&token=" + token;
		return sendPost(setting.getApiBackendUserType(), param,
				"BackendUserType");

	}

	/**
	 * API选择用户操作模式
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public static String apiBackendUserMode(User user, HttpSession session) {
		String userId = (String) session
				.getAttribute(CommonAttributes.API_USERID_SESSION);
		String token = (String) session
				.getAttribute(CommonAttributes.API_TOKEN_SESSION);
		Assert.notNull(userId);
		String param = "user_id=" + userId + "&user_mode="
				+ (user.getModeType().ordinal() + 1) + "&token=" + token;
		return sendPost(setting.getApiBackendUserMode(), param,
				"BackendUserMode");

	}

	/**
	 * API编辑设备
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public static String apiDeviceEdit(Device device, HttpSession session,
			Long operate) {
		String userId = (String) session
				.getAttribute(CommonAttributes.API_USERID_SESSION);
		String token = (String) session
				.getAttribute(CommonAttributes.API_TOKEN_SESSION);
		Assert.notNull(userId);
		Assert.notNull(token);
		String param = "user_id=" + userId + "&device_sn=" + device.getfSn()
				+ "&operate=" + operate + "&token=" + token;

		String string = sendPost(setting.getApiDeviceEdit(), param,
				"DeviceEdit");

		return string;

	}

	/**
	 * API添加设备
	 * 
	 * @param device
	 * @return
	 * @throws Exception
	 */
	public static String apiDeviceAdd(Device device, HttpSession session) {
		String userId = (String) session
				.getAttribute(CommonAttributes.API_USERID_SESSION);
		String token = (String) session
				.getAttribute(CommonAttributes.API_TOKEN_SESSION);
		Assert.notNull(userId);
		String param = "device_sn=" + device.getfSn() + "&user_id=" + userId
				+ "&is_virtual=" + device.getfIsvirtual().ordinal() + "&phone="
				+ device.getfPhone() + "&type=" + device.getfType()
				+ "&protocol=" + device.getfProtocol() + "&token=" + token+"&conductorSn="+device.getConductorSn();
		return sendPost(setting.getApiDeviceAdd(), param, "DeviceAdd");
	}

	/**
	 * API添加设备
	 * 
	 * @param device
	 * @return
	 * @throws Exception
	 */
	public static String apiGetAllApi(HttpSession session) {
		String userId = (String) session
				.getAttribute(CommonAttributes.API_USERID_SESSION);
		String token = (String) session
				.getAttribute(CommonAttributes.API_TOKEN_SESSION);
		Assert.notNull(userId);
		Assert.notNull(token);
		String param = "user_id=" + userId + "&language=zh_CN&token=" + token;
		return sendPost(setting.getApiGetAllApi(), param, "GetAllApi");
	}

	/**
	 * API KEY
	 * 
	 * @param device
	 * @return
	 * @throws Exception
	 */
	public static String apiSetApiKey(DevModeUserExtend devModeUserExtend,
			HttpSession session) {
		String userId = (String) session
				.getAttribute(CommonAttributes.API_USERID_SESSION);
		String token = (String) session
				.getAttribute(CommonAttributes.API_TOKEN_SESSION);
		Assert.notNull(userId);
		String param = "user_id=" + userId + "&api_key="
				+ devModeUserExtend.getDevKey() + "&token=" + token;
		return sendPost(setting.getApiDevApikey(), param, "SetAPIKey");
	}

	/**
	 * 检查客户数据是否有重名用户
	 * 
	 * @param userName
	 * @return
	 */
	public static String apiUserNameCheck(String userName) {
		String param = "username=" + userName;
		return sendPost(setting.getApiUserNameCheck(), param,
				"apiUserNameCheck");
	}

	public static String apiGetApiDocList(HttpSession session) {
		String userId = (String) session
				.getAttribute(CommonAttributes.API_USERID_SESSION);
		String token = (String) session
				.getAttribute(CommonAttributes.API_TOKEN_SESSION);
		Assert.notNull(userId);
		String param = "user_id=" + userId + "&token=" + token;
		return sendPost(setting.getApiGetApiDocList(), param,
				"apiUserNameCheck");
	}

	public static String apiGetApiDocDetail(HttpSession session, String docId) {
		String userId = (String) session
				.getAttribute(CommonAttributes.API_USERID_SESSION);
		String token = (String) session
				.getAttribute(CommonAttributes.API_TOKEN_SESSION);
		Assert.notNull(userId);
		Assert.notNull(docId);
		String param = "user_id=" + userId + "&token=" + token + "&doc_id="
				+ docId;
		return sendPost(setting.getApiGetApiDocDetail(), param,
				"apiGetApiDocDetail");
	}

	public static String apiGetBeacon(String serviceId, HttpSession session) {
		String token = (String) session
				.getAttribute(CommonAttributes.API_TOKEN_SESSION);
		String userId = (String) session
				.getAttribute(CommonAttributes.API_USERID_SESSION);
		String param = "user_id=" + userId + "&service_id=" + serviceId
				+ "&token=" + token;
		String rs = sendPost(setting.getApiUserStatistics(), param,
				"Statistics");
		return rs;
	}

	public static String apiIndoorBeaconAdd(Beacon beacon, HttpSession session) {
		String userId = (String) session
				.getAttribute(CommonAttributes.API_USERID_SESSION);
		String token = (String) session
				.getAttribute(CommonAttributes.API_TOKEN_SESSION);
		Assert.notNull(userId);
		String param = "mac=" + beacon.getfMac() + "&user_id=" + userId
				+ "&lng=" + beacon.getfLng() + "&lat=" + beacon.getfLat()
				+ "&locate=" + beacon.getfLocate() + "&floor="
				+ beacon.getfFloor() + "&room=" + beacon.getfRoom()
				+ "&system=" + beacon.getfSystem() + "&x=" + beacon.getfPx()
				+ "&y=" + beacon.getfPy() + "&token=" + token;
		return sendPost(setting.getApiIndoorBeaconAdd(), param,
				"IndoorBeaconAdd");
	}

	public static String apiIndoorBeaconEdit(Beacon beacon, HttpSession session) {
		String userId = (String) session
				.getAttribute(CommonAttributes.API_USERID_SESSION);
		String token = (String) session
				.getAttribute(CommonAttributes.API_TOKEN_SESSION);
		Assert.notNull(userId);
		Assert.notNull(token);
		String param = "mac=" + beacon.getfMac() + "&user_id=" + userId
				+ "&lng=" + beacon.getfLng() + "&lat=" + beacon.getfLat()
				+ "&locate=" + beacon.getfLocate() + "&floor="
				+ beacon.getfFloor() + "&room=" + beacon.getfRoom()
				+ "&system=" + beacon.getfSystem() + "&x=" + beacon.getfPx()
				+ "&y=" + beacon.getfPy() + "&token=" + token;

		String string = sendPost(setting.getApiIndoorBeaconEdit(), param,
				"IndoorBeaconEdit");

		return string;
	}

	public static String apiIndoorBeaconDelete(Beacon beacon,
			HttpSession session) {
		String userId = (String) session
				.getAttribute(CommonAttributes.API_USERID_SESSION);
		String token = (String) session
				.getAttribute(CommonAttributes.API_TOKEN_SESSION);
		Assert.notNull(userId);
		Assert.notNull(token);
		String param = "mac=" + beacon.getfMac() + "&user_id=" + userId
				+ "&lng=" + beacon.getfLng() + "&lat=" + beacon.getfLat()
				+ "&locate=" + beacon.getfLocate() + "&floor="
				+ beacon.getfFloor() + "&room=" + beacon.getfRoom()
				+ "&system=" + beacon.getfSystem() + "&x=" + beacon.getfPx()
				+ "&y=" + beacon.getfPy() + "&token=" + token;

		String string = sendPost(setting.getApiIndoorBeaconDelete(), param,
				"IndoorBeaconEdit");

		return string;
	}

}
