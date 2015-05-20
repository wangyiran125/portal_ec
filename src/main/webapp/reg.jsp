<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.UUID"%>
<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="com.chinalbs.beans.Setting"%>
<%@page import="com.chinalbs.utils.SettingUtils"%>
<%@page import="com.chinalbs.utils.SpringUtils"%>
<%@page import="com.chinalbs.beans.Setting.CaptchaType"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%
String base = request.getContextPath();
String captchaId = UUID.randomUUID().toString();
ApplicationContext applicationContext = SpringUtils.getApplicationContext();
Setting setting = SettingUtils.get();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><%=SpringUtils.getMessage("admin.login.title")%></title>
<meta http-equiv="expires" content="0" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<link rel="shortcut icon" type="image/x-icon" href="<%=base%>/resources/images/favicon.ico" media="screen" /> 
<link href="<%=base%>/resources/css/common.css" rel="stylesheet"
	type="text/css" />
<link href="<%=base%>/resources/css/reg.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=base%>/resources/js/jquery.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/jsbn.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/prng4.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/rng.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/rsa.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/base64.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/common.js"></script>
<script type="text/javascript" src="<%=base%>/resources/js/jquery.validate.js"></script>
<script type="text/javascript">
	$().ready( function() {
		
		var $regForm = $("#regForm");
		var $password = $("#password");
		var $repassword = $("#repassword");
		var $mobile = $("#mobile");
		var $email = $("#email");
		var $nickname = $("#nickname");
		var $captcha = $("#captcha");
		var $captchaImage = $("#captchaImage");
		var $submit = $(":submit");
		
		if(getCookie("regEmail") != null) {
			$email.val(getCookie("regEmail"));
		} 
		if(getCookie("regMobile") != null) {
			$mobile.val(getCookie("regMobile"));
		} 
		if(getCookie("regNickName") != null) {
			$nickname.val(getCookie("regNickName"));
		} 
		if(getCookie("regPassword") != null) {
			$password.val(getCookie("regPassword"));
		} 
		if(getCookie("regRepassword") != null) {
			$repassword.val(getCookie("regRepassword"));
		} 
	
		// 更换验证码
		$captchaImage.click( function() {
			$captchaImage.attr("src", "<%=base%>/console/common/captcha.jhtml?captchaId=<%=captchaId%>&timestamp=" + (new Date()).valueOf());
		});
		
		//表单验证
		$regForm.validate({
			rules: {
				email:{
					required:true,
					email:true,
					remote:{
						url:"<%=base%>/console/register/check_email.jhtml",
						cache:false
					}
				},
				password: {
					required:true,
					pattern: /^[^\s&\"<>]+$/,
					minlength:<%=setting.getPasswordMinlength()%>
				},
				repassword: {
					equalTo: "#password"
				},
				mobile: {
					required:true,
					pattern:/^[0-9]{1,20}$/,
					remote:{
						url:"<%=base%>/console/register/check_mobile.jhtml",
						cache:false
					}
				},
				nickname:{
					required:true
				}
			},
				
			messages: {
				email:{
					required:"<%=SpringUtils.getMessage("admin.reg.emailRequired")%>",
					email: "<%=SpringUtils.getMessage("admin.reg.emailIllegal")%>",
					remote: "<%=SpringUtils.getMessage("admin.reg.emailExist")%>"
				},
				password: {
					required:"<%=SpringUtils.getMessage("admin.login.passwordRequired")%>",
					pattern: "<%=SpringUtils.getMessage("admin.reg.passwordIllegal")%>",
					minlength:"<%=SpringUtils.getMessage("admin.password.minlength",setting.getPasswordMinlength())%>"
				},
				repassword:{
					 equalTo:"<%=SpringUtils.getMessage("admin.reg.repasswordIllegal")%>"
				},
				mobile:{
					required:"<%=SpringUtils.getMessage("admin.reg.mobileRequired")%>",
					pattern: "<%=SpringUtils.getMessage("admin.reg.mobileIllegal")%>",
					remote: "<%=SpringUtils.getMessage("admin.reg.mobileExist")%>"
				},
				nickname:{
					required:"<%=SpringUtils.getMessage("admin.reg.nickNameRequired")%>"
				}
			},
			
			submitHandler:function(form){
				addCookie("regEmail", $email.val(), {expires:10});
				addCookie("regMobile", $mobile.val(), {expires:10});
				addCookie("regNickName", $nickname.val(), {expires:10});
				addCookie("regPassword", $password.val(), {expires:10});
				addCookie("regRepassword", $repassword.val(), {expires:10});
				
				if ($captcha.val() == "") {
					$.message("warn", "<%=SpringUtils.getMessage("admin.login.captchaRequired")%>");
											return false;
										}
				$.ajax({
					url: "<%=base%>/console/common/public_key.jhtml",
					type: "GET",
					dataType: "json",
					cache: false,
					beforeSend: function() {
						$submit.prop("disabled", true);
					},
					success:function(data){
						var rsaKey = new RSAKey();
						rsaKey.setPublic(b64tohex(data.modulus), b64tohex(data.exponent));
						var enPassword = hex2b64(rsaKey.encrypt($password.val()));
						$.ajax({
							  url:$regForm.attr("action"),
							  type:"POST",
							  data:{
								  email: $email.val(),
								  enPassword:enPassword,
								  mobile:$mobile.val(),
								  nickName:$nickname.val(),
								  captcha:$captcha.val(),
								  captchaId: "<%=captchaId%>"
							  },
							  dataType:"json",
							  cache:false,
							  success:function(message){
								  $.message(message);
								  if(message.type == "success"){
									  setTimeout("window.parent.location.href='<%=base%>/login.jsp'",1000);
								  }else{
									  setTimeout("window.parent.location.href='<%=base%>/reg.jsp'",1000);
								  }
							  }
						  
						  });
					}
				});
				
			}
		});
		
});
</script>
</head>
<body>
	<%if (applicationContext != null) {%>
	<div class="header">
            <img class="logo" src="resources/images/header_logo.png" alt="中国位置"/>
    </div>
    <div class="container">
        <div class="stance"></div>
        <div class="register">
            <div class="title">
                <h1>用户注册</h1>
            </div>
            <div class="content">
				<form id="regForm" action="<%=base%>/console/register/submit.jhtml" method="post">
					<%if (ArrayUtils.contains(setting.getCaptchaTypes(), CaptchaType.userReg)) {%>
					<input type="hidden" name="captchaId" value="<%=captchaId%>" />
					<%}%>
					<table class="input">
						<tr>
							<th><span class="requiredField">*</span><%=SpringUtils.getMessage("admin.reg.email")%>:
							</th>
							<td><input type="text" id="email" name="email"
								class="text" maxlength="30" /></td>
						</tr>
						<tr>
							<th><span class="requiredField">*</span><%=SpringUtils.getMessage("admin.reg.pwd")%>:</th>
							<td><input type="password" id="password" class="text" name="password"
								maxlength="20" autocomplete="off" /></td>
						</tr>
						<tr>
							<th><span class="requiredField">*</span><%=SpringUtils.getMessage("admin.reg.repwd")%>:</th>
							<td><input type="password" id="repassword" class="text" name="repassword"
								maxlength="20" autocomplete="off" /></td>
						</tr>
						<tr>
							<th><span class="requiredField">*</span><%=SpringUtils.getMessage("admin.reg.mobile")%>:
							</th>
							<td><input type="text" id="mobile" name="mobile"
								class="text" maxlength="20" /></td>
						</tr>
						<tr>
							<th><span class="requiredField">*</span><%=SpringUtils.getMessage("admin.reg.nickname")%>:
							</th>
							<td><input type="text" id="nickname" name="nickname"
								class="text" maxlength="20" /></td>
						</tr>
						<%if (ArrayUtils.contains(setting.getCaptchaTypes(), CaptchaType.userLogin)) {%>
						<tr>
							
							<th><%=SpringUtils.getMessage("admin.captcha.name")%>:</th>
							<td><input type="text" id="captcha" name="captcha"
								class="captcha" maxlength="4" autocomplete="off" />
								<img
								id="captchaImage" class="captchaImage"
								src="<%=base%>/console/common/captcha.jhtml?captchaId=<%=captchaId%>"
								title="<%=SpringUtils.getMessage("admin.captcha.imageTitle")%>" />
							</td>
						</tr>
						<%}%>
						<tr>
							<td>&nbsp;</td>
							<td>
								<input type="submit" class="regButton"
								value="<%=SpringUtils.getMessage("admin.common.register")%>" />
							</td>
						</tr>
						 <tr>
                            <td>&nbsp;</td>
                            <td>
				                                已有账号?<a href="<%=base%>/" class="loginLink">点击登陆</a>
				            </td>                  
				         </tr>
					</table>
				</form>
	          </div>
        </div>
    </div>
    <div class="footer-warp">
        <div class="copy-warp">
            <div class="copy">版权所有©2014北斗导航位置服务(北京)有限公司-京ICP备05085635号<span>Copyright © 2014 ChinaLbs International BV.All rights reserved.</span></div>
        </div>
    </div>
	<%} else {%>
	<fieldset>
		<legend>系统出现异常</legend>
	</fieldset>
	<%}%>
</body>
</html>
