<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="java.util.UUID"%>
<%@page import="java.security.interfaces.RSAPublicKey"%>
<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.chinalbs.beans.Setting"%>
<%@page import="com.chinalbs.utils.SettingUtils"%>
<%@page import="com.chinalbs.utils.SpringUtils"%>
<%@page import="com.chinalbs.beans.Setting.CaptchaType"%>
<%@page import="com.chinalbs.service.RSAService"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String base = request.getContextPath();
String captchaId = UUID.randomUUID().toString();
ApplicationContext applicationContext = SpringUtils.getApplicationContext();
Setting setting = SettingUtils.get();
if (applicationContext != null) {
%>
<shiro:authenticated>
<%
	response.sendRedirect(base + "/console/common/main.jhtml");
%>
</shiro:authenticated>
<%
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" type="image/x-icon" href="<%=base%>/resources/images/favicon.ico" media="screen" /> 
<%
if (applicationContext != null) {
	RSAService rsaService = SpringUtils.getBean("rsaServiceImpl", RSAService.class);
	RSAPublicKey publicKey = rsaService.generateKey(request);
	String modulus = Base64.encodeBase64String(publicKey.getModulus().toByteArray());
	String exponent = Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray());
	
	String message = null;
	String loginFailure = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	if (loginFailure != null) {
		if (loginFailure.equals("org.apache.shiro.authc.pam.UnsupportedTokenException")) {
			message = "admin.captcha.invalid";
		} else if (loginFailure.equals("org.apache.shiro.authc.UnknownAccountException")) {
			message = "admin.login.unknownAccount";
		} else if (loginFailure.equals("org.apache.shiro.authc.DisabledAccountException")) {
			message = "admin.login.disabledAccount";
		} else if (loginFailure.equals("org.apache.shiro.authc.LockedAccountException")) {
			message = "admin.login.lockedAccount";
		} else if (loginFailure.equals("org.apache.shiro.authc.IncorrectCredentialsException")) {
				message = "admin.login.incorrectCredentials";
		} else if (loginFailure.equals("org.apache.shiro.authc.AuthenticationException")) {
			message = "admin.login.authentication";
		}else{
			message = "admin.login.incorrectCredentials";
		}
	}
%>
<title><%=SpringUtils.getMessage("admin.login.title")%> </title>
<meta http-equiv="expires" content="0" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<link href="<%=base%>/resources/css/common.css" rel="stylesheet" type="text/css" />
<link href="<%=base%>/resources/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=base%>/resources/js/jquery.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/jquery.placeholder.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/jsbn.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/prng4.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/rng.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/rsa.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/base64.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/common.js"></script>
<script type="text/javascript">
	$().ready( function() {
		if (window != top)
		{
			top.location.href = location.href;
		}

		var $loginForm = $("#loginForm");
		var $enPassword = $("#enPassword");
		var $username = $("#username");
		var $password = $("#password");
		var $captcha = $("#captcha");
		var $captchaImage = $("#captchaImage");
		var $isRememberUsername = $("#isRememberUsername");
		
		// 记住用户名
		if(getCookie("adminUsername") != null) {
			$isRememberUsername.prop("checked", true);
			$username.val(getCookie("adminUsername"));
			$password.focus();
		} else {
			$isRememberUsername.prop("checked", false);
			$username.focus();
		}
		
		// 更换验证码
		$captchaImage.click( function() {
			$captchaImage.attr("src", "<%=base%>/console/common/captcha.jhtml?captchaId=<%=captchaId%>&timestamp=" + (new Date()).valueOf());
		});
		
		// 表单验证、记住用户名
		$loginForm.submit( function() {
			if ($username.val() == "") {
				$.message("warn", "<%=SpringUtils.getMessage("admin.login.usernameRequired")%>");
				return false;
			}
			if ($password.val() == "") {
				$.message("warn", "<%=SpringUtils.getMessage("admin.login.passwordRequired")%>");
				return false;
			}
			if ($captcha.val() == "") {
				$.message("warn", "<%=SpringUtils.getMessage("admin.login.captchaRequired")%>");
				return false;
			}
			
			if ($isRememberUsername.prop("checked")) {
				addCookie("adminUsername", $username.val(), {expires: 7 * 24 * 60 * 60});
			} else {
				removeCookie("adminUsername");
			}
			
			var rsaKey = new RSAKey();
			rsaKey.setPublic(b64tohex("<%=modulus%>"), b64tohex("<%=exponent%>"));
			var enPassword = hex2b64(rsaKey.encrypt($password.val()));
			$enPassword.val(enPassword);
		});
		
		<%if (message != null) {%>
		$.message("error", "<%=SpringUtils.getMessage(message)%>");
	<%}%>
	});
</script>
<%} else {%>
<title>提示信息 </title>
<meta http-equiv="expires" content="0" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<link href="<%=base%>/resources/css/common.css" rel="stylesheet" type="text/css" />
<link href="<%=base%>/resources/css/login.css" rel="stylesheet" type="text/css" />
<%}%>
</head>
<body >
	<div class="header">
   		 <img class="logo" src="<%=base%>/resources/images/header_logo.png" alt="中国位置"/>
	</div>
	<div class="container">
	    <div class="stance"></div>
	    <div class="content">
          <h1>账号登陆</h1>
			<form id="loginForm" action="login.jsp" method="post">
				<input type="hidden" id="enPassword" name="enPassword" />
				<input type="hidden" id="localUrl" />
				<%if (ArrayUtils.contains(setting.getCaptchaTypes(), CaptchaType.userLogin)) {%>
					<input type="hidden" name="captchaId" value="<%=captchaId%>" />
				<%}%>
					 <div class="div_input">		
							<input type="text" id="username" name="username" class="text" maxlength="30"  placeholder ="用户名"/>
					</div>
					<div class="div_input">
							<input type="password" id="password" class="text" maxlength="20" autocomplete="off" placeholder="密码"/>
					</div>
					<%if (ArrayUtils.contains(setting.getCaptchaTypes(), CaptchaType.userLogin)) {%>
					<div class="div_input">
								<input type="text" id="captcha" name="captcha" class="text captcha" maxlength="4" autocomplete="off" placeholder="验证码"/>
								<img id="captchaImage" class="captchaImage" src="<%=base%>/console/common/captcha.jhtml?captchaId=<%=captchaId%>" title="<%=SpringUtils.getMessage("admin.captcha.imageTitle")%>" />
					</div>
					<%}%>
					<div class="div_input">
						<input type="checkbox" class="checkbox" id="isRememberUsername" value="true" /><%=SpringUtils.getMessage("admin.login.rememberUsername")%>
						<a href="<%=base%>/console/password/forgetPwd.jhtml" class="forgetLink" target="_blank" style="color:red"><%=SpringUtils.getMessage("admin.forget.password")%></a>
					</div>
					<div class="div_input">
							<input type="submit" class="loginButton" value="<%=SpringUtils.getMessage("admin.login.login")%>" />
					</div>
					<div class="div_input">
	                	 <span>还没有账号？<a href="<%=base%>/reg.jsp" class="registerLink">立刻注册</a></span>
	               </div>
			</form>
		    </div>
		</div>
		<div class="footer-warp">
		    <div class="copy-warp">
		        <div class="copy">版权所有©2014北斗导航位置服务(北京)有限公司-京ICP备05085635号<span>Copyright © 2014 ChinaLbs International BV.All rights reserved.</span></div>
		    </div>
		</div>
		<script type="text/javascript">
    $(function(){
        //解决IE下不支持placeholder
        if($.browser.msie) {
            $(":input[placeholder]").each(function(){
                $(this).placeholder();
            });
        }
    })
</script>
</body>
</html>
