package com.chinalbs.beans;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 系统设置
 * 
 */
public class Setting implements Serializable {

	private static final long serialVersionUID = -1478999889661796840L;

	/**
	 * 验证码类型
	 */
	public enum CaptchaType {

		/** 后台登录 */
		userLogin,

		/** 后台注册 */
		userReg,

		/** 找回密码 */
		findPassword,

		/** 重置密码 */
		resetPassword,

		/** 其它 */
		other
	}

	/** 缓存名称 */
	public static final String CACHE_NAME = "setting";

	/** 缓存Key */
	public static final Integer CACHE_KEY = 0;

	/** 分隔符 */
	private static final String SEPARATOR = ",";

	/** 验证码类型 */
	private CaptchaType[] captchaTypes;

	/** 网站url */
	private String siteUrl;

	/** Cookie路径 */
	private String cookiePath;

	/** Cookie作用域 */
	private String cookieDomain;

	/** 密码最小长度 */
	private Integer passwordMinlength;

	/** 基础API访问路径 */
	private String apiUrl;

	/** 基础API用户注册 */
	private String apiRegister;

	/** 基础API用户登陆 */
	private String apiLogin;

	/** 基础API用户类型 */
	private String apiBackendUserType;

	/** 基础API用户操作模式 */
	private String apiBackendUserMode;

	/** 基础API编辑用户 */
	private String apiEditUser;

	/** 基础API修改密码 */
	private String apiUpatePwd;

	/** 概要统计API */
	private String apiViewStatistics;

	/**
	 * 当天设备统计API
	 */
	private String apiDeviceStatistics;

	/**
	 * 当天用户统计API
	 */
	private String apiUserStatistics;

	private String apiDeviceAdd;

	private String apiDeviceEdit;

	/** 开发者模式角色ID */
	private String developmentModeRoleId;

	/** 获取所有的api接口 */
	private String apiGetAllApi;

	/** 设置apiKey */
	private String apiDevApikey;

	/** 后台模式角色ID */
	private String backendModeRoleId;

	/** 距离自定义时间的月份数（用于统计报表） */
	private Integer monthCounts;

	/* default_site_domain */
	private String defaultSiteDomainForEnterprise;

	private String defaultSiteDomainForIndividual;

	/**
	 * API warning email
	 */
	private String alarmEmailSender;
	private String alarmEmailTitle;
	private String alarmEmailContent;

	/** 发件人邮箱 */
	private String smtpFromMail;

	/** SMTP服务器地址 */
	private String smtpHost;

	/** SMTP服务器端口 */
	private Integer smtpPort;

	/** SMTP用户名 */
	private String smtpUsername;

	/** SMTP密码 */
	private String smtpPassword;

	/* 获取最新GPS点 */
	/**
	 * 改为新接口 -- 2014-09-03 12:28 Shijun
	 */
	// private String apiGetStatus;

	/** 客户数据库用户名检查 */
	private String apiUserNameCheck;

	/** 新接口获取新GPS点 */
	private String apiLatestPosition;

	/** 获取最近3天GPS点 */
	private String apiGetTrack;

	/** 网站名称 */
	private String siteName;

	private String simulatorServerIp;

	private Integer simulatorServerPort;

	/** 是否对轨迹分表 */
	private String dividedTable;

	/** 分表设计是轨迹表前缀 */
	private String tableNamePrefix;

	private String apiGetUserId;

	/** 重置密码token有效时间 */
	private Integer pwdTokenExpiryTime;

	/** 获取API文档列表 */
	private String apiGetApiDocList;

	/** 后去APi文档细节 */
	private String apiGetApiDocDetail;

	private String apiIndoorBeaconAdd;

	private String apiIndoorBeaconEdit;
	
	private String apiIndoorBeaconDelete;

	/**
	 * 获取验证码类型
	 * 
	 * @return 验证码类型
	 */
	public CaptchaType[] getCaptchaTypes() {
		return captchaTypes;
	}

	/**
	 * 设置验证码类型
	 * 
	 * @param captchaTypes
	 *            验证码类型
	 */
	public void setCaptchaTypes(CaptchaType[] captchaTypes) {
		this.captchaTypes = captchaTypes;
	}

	/**
	 * 获取Cookie路径
	 * 
	 * @return Cookie路径
	 */
	@NotEmpty
	@Length(max = 200)
	public String getCookiePath() {
		return cookiePath;
	}

	/**
	 * 设置Cookie路径
	 * 
	 * @param cookiePath
	 *            Cookie路径
	 */
	public void setCookiePath(String cookiePath) {
		if (cookiePath != null && !cookiePath.endsWith("/")) {
			cookiePath += "/";
		}
		this.cookiePath = cookiePath;
	}

	/**
	 * 获取Cookie作用域
	 * 
	 * @return Cookie作用域
	 */
	@Length(max = 200)
	public String getCookieDomain() {
		return cookieDomain;
	}

	/**
	 * 设置Cookie作用域
	 * 
	 * @param cookieDomain
	 *            Cookie作用域
	 */
	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}

	/**
	 * 获取密码最小长度
	 * 
	 * @return 密码最小长度
	 */
	public Integer getPasswordMinlength() {
		return passwordMinlength;
	}

	/**
	 * 设置密码最小长度
	 * 
	 * @param passwordMinlength
	 *            密码最小长度
	 */
	public void setPasswordMinlength(Integer passwordMinlength) {
		this.passwordMinlength = passwordMinlength;
	}

	/**
	 * 获取基础API访问路径
	 * 
	 * @return 基础API访问路径
	 */
	public String getApiUrl() {
		return apiUrl;
	}

	/**
	 * 设置基础API访问路径
	 * 
	 * @param apiUrl
	 */
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	/**
	 * 获取基础API用户注册路径
	 * 
	 * @return 基础API用户注册
	 */
	public String getApiRegister() {
		return apiRegister;
	}

	/**
	 * 设置基础API用户注册路径
	 * 
	 * @param apiRegister
	 */
	public void setApiRegister(String apiRegister) {
		this.apiRegister = apiRegister;
	}

	/**
	 * 获取基础API用户登陆路径
	 * 
	 * @return apiLogin
	 */
	public String getApiLogin() {
		return apiLogin;
	}

	/**
	 * 设置基础API用户登陆路径
	 * 
	 * @param apiLogin
	 */
	public void setApiLogin(String apiLogin) {
		this.apiLogin = apiLogin;
	}

	/**
	 * 获取基础API,选择用户类型路径
	 * 
	 * @return 选择用户类型路径
	 */
	public String getApiBackendUserType() {
		return apiBackendUserType;
	}

	/**
	 * 设置基础API,选择用户类型路径
	 * 
	 * @param apiBackendUserType
	 */
	public void setApiBackendUserType(String apiBackendUserType) {
		this.apiBackendUserType = apiBackendUserType;
	}

	/**
	 * 获取基础API,选择用户操作模式路径
	 * 
	 * @return apiBackendUserMode
	 */
	public String getApiBackendUserMode() {
		return apiBackendUserMode;
	}

	/**
	 * 设置基础API,选择用户操作模式路径
	 * 
	 * @param apiBackendUserType
	 */
	public void setApiBackendUserMode(String apiBackendUserMode) {
		this.apiBackendUserMode = apiBackendUserMode;
	}

	public String getApiDeviceAdd() {
		return apiDeviceAdd;
	}

	public void setApiDeviceAdd(String apiDeviceAdd) {
		this.apiDeviceAdd = apiDeviceAdd;
	}

	public String getApiDeviceEdit() {
		return apiDeviceEdit;
	}

	public void setApiDeviceEdit(String apiDeviceEdit) {
		this.apiDeviceEdit = apiDeviceEdit;
	}

	/**
	 * 获取基础API编辑用户
	 * 
	 * @return apiEditUser
	 */
	public String getApiEditUser() {
		return apiEditUser;
	}

	/**
	 * 设置基础API编辑用户
	 * 
	 * @param apiEditUser
	 */
	public void setApiEditUser(String apiEditUser) {
		this.apiEditUser = apiEditUser;
	}

	/**
	 * 获取基础API修改密码
	 * 
	 * @return apiSetPwd
	 */
	public String getApiUpatePwd() {
		return apiUpatePwd;
	}

	/**
	 * 设置基础API修改密码
	 * 
	 * @param apiSetPwd
	 */
	public void setApiUpatePwd(String apiUpatePwd) {
		this.apiUpatePwd = apiUpatePwd;
	}

	public String getDevelopmentModeRoleId() {
		return developmentModeRoleId;
	}

	public void setDevelopmentModeRoleId(String developmentModeRoleId) {
		this.developmentModeRoleId = developmentModeRoleId;
	}

	public String getBackendModeRoleId() {
		return backendModeRoleId;
	}

	public void setBackendModeRoleId(String backendModeRoleId) {
		this.backendModeRoleId = backendModeRoleId;
	}

	public Integer getMonthCounts() {
		return monthCounts;
	}

	public void setMonthCounts(Integer monthCounts) {
		this.monthCounts = monthCounts;
	}

	/**
	 * 获取基础API概要统计
	 * 
	 * @return apiViewStatistics
	 */
	public String getApiViewStatistics() {
		return apiViewStatistics;
	}

	/**
	 * 设置基础API概要统计
	 * 
	 * @param apiViewStatistics
	 */
	public void setApiViewStatistics(String apiViewStatistics) {
		this.apiViewStatistics = apiViewStatistics;
	}

	public String getApiDeviceStatistics() {
		return apiDeviceStatistics;
	}

	public void setApiDeviceStatistics(String apiDeviceStatistics) {
		this.apiDeviceStatistics = apiDeviceStatistics;
	}

	public String getApiUserStatistics() {
		return apiUserStatistics;
	}

	public void setApiUserStatistics(String apiUserStatistics) {
		this.apiUserStatistics = apiUserStatistics;
	}

	public String getApiGetAllApi() {
		return apiGetAllApi;
	}

	public void setApiGetAllApi(String apiGetAllApi) {
		this.apiGetAllApi = apiGetAllApi;
	}

	public String getApiDevApikey() {
		return apiDevApikey;
	}

	public void setApiDevApikey(String apiDevApikey) {
		this.apiDevApikey = apiDevApikey;
	}

	public String getDefaultSiteDomainForEnterprise() {
		return defaultSiteDomainForEnterprise;
	}

	public void setDefaultSiteDomainForEnterprise(
			String defaultSiteDomainForEnterprise) {
		this.defaultSiteDomainForEnterprise = defaultSiteDomainForEnterprise;
	}

	public String getDefaultSiteDomainForIndividual() {
		return defaultSiteDomainForIndividual;
	}

	public void setDefaultSiteDomainForIndividual(
			String defaultSiteDomainForIndividual) {
		this.defaultSiteDomainForIndividual = defaultSiteDomainForIndividual;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public String getAlarmEmailSender() {
		return alarmEmailSender;
	}

	public void setAlarmEmailSender(String alarmEmailSender) {
		this.alarmEmailSender = alarmEmailSender;
	}

	public String getAlarmEmailTitle() {
		return alarmEmailTitle;
	}

	public void setAlarmEmailTitle(String alarmEmailTitle) {
		this.alarmEmailTitle = alarmEmailTitle;
	}

	public String getAlarmEmailContent() {
		return alarmEmailContent;
	}

	public void setAlarmEmailContent(String alarmEmailContent) {
		this.alarmEmailContent = alarmEmailContent;
	}

	public String getSmtpFromMail() {
		return smtpFromMail;
	}

	public void setSmtpFromMail(String smtpFromMail) {
		this.smtpFromMail = smtpFromMail;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public Integer getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpUsername() {
		return smtpUsername;
	}

	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public String getApiGetTrack() {
		return apiGetTrack;
	}

	public void setApiGetTrack(String apiGetTrack) {
		this.apiGetTrack = apiGetTrack;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSimulatorServerIp() {
		return simulatorServerIp;
	}

	public void setSimulatorServerIp(String simulatorServerIp) {
		this.simulatorServerIp = simulatorServerIp;
	}

	public Integer getSimulatorServerPort() {
		return simulatorServerPort;
	}

	public void setSimulatorServerPort(Integer simulatorServerPort) {
		this.simulatorServerPort = simulatorServerPort;
	}

	public String getDividedTable() {
		return dividedTable;
	}

	public void setDividedTable(String dividedTable) {
		this.dividedTable = dividedTable;
	}

	public String getTableNamePrefix() {
		return tableNamePrefix;
	}

	public void setTableNamePrefix(String tableNamePrefix) {
		this.tableNamePrefix = tableNamePrefix;
	}

	public String getApiGetUserId() {
		return apiGetUserId;
	}

	public void setApiGetUserId(String apiGetUserId) {
		this.apiGetUserId = apiGetUserId;
	}

	public Integer getPwdTokenExpiryTime() {
		return pwdTokenExpiryTime;
	}

	public void setPwdTokenExpiryTime(Integer pwdTokenExpiryTime) {
		this.pwdTokenExpiryTime = pwdTokenExpiryTime;
	}

	public String getApiLatestPosition() {
		return apiLatestPosition;
	}

	public void setApiLatestPosition(String apiLatestPosition) {
		this.apiLatestPosition = apiLatestPosition;
	}

	public String getApiUserNameCheck() {
		return apiUserNameCheck;
	}

	public void setApiUserNameCheck(String apiUserNameCheck) {
		this.apiUserNameCheck = apiUserNameCheck;
	}

	public String getApiGetApiDocList() {
		return apiGetApiDocList;
	}

	public void setApiGetApiDocList(String apiGetApiDocList) {
		this.apiGetApiDocList = apiGetApiDocList;
	}

	public String getApiGetApiDocDetail() {
		return apiGetApiDocDetail;
	}

	public void setApiGetApiDocDetail(String apiGetApiDocDetail) {
		this.apiGetApiDocDetail = apiGetApiDocDetail;
	}

	public String getApiIndoorBeaconAdd() {
		return apiIndoorBeaconAdd;
	}

	public void setApiIndoorBeaconAdd(String apiIndoorBeaconAdd) {
		this.apiIndoorBeaconAdd = apiIndoorBeaconAdd;
	}

	public String getApiIndoorBeaconEdit() {
		return apiIndoorBeaconEdit;
	}

	public void setApiIndoorBeaconEdit(String apiIndoorBeaconEdit) {
		this.apiIndoorBeaconEdit = apiIndoorBeaconEdit;
	}

	public String getApiIndoorBeaconDelete() {
		return apiIndoorBeaconDelete;
	}

	public void setApiIndoorBeaconDelete(String apiIndoorBeaconDelete) {
		this.apiIndoorBeaconDelete = apiIndoorBeaconDelete;
	}

}
