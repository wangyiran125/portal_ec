<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.forget.password")}</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/css/reg.css" rel="stylesheet" type="text/css"/>
<link href="${base}/resources/css/password.css" rel="stylesheet" type="text/css"/>
<link rel="shortcut icon" type="image/x-icon" href="${base}/resources/images/favicon.ico" media="screen" /> 
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $forgetPwdForm = $("#forgetPwdForm");
	var $email = $("#email");
	var $captcha = $("#captcha");
	var $captchaImage = $("#captchaImage");
	var $submit = $(":submit");
	
	if(getCookie("pwdEmail") != null) {
			$email.val(getCookie("pwdEmail"));
		} 
		
	// 更换验证码
	$captchaImage.click(function() {
		$captchaImage.attr("src", "${base}/console/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
	});
		
	$forgetPwdForm.validate({
		rules:{
			email:{
				required:true,
				email:true,
			}
		},
			messages:{
				email:{
					required:"${message("admin.reg.emailRequired")}",
					email:"${message("admin.reg.emailIllegal")}",	
				},
				
			},
		submitHandler:function(form){
			addCookie("pwdEmail", $email.val(), {expires:10});
		if ($captcha.val() == "") {
				$.message("warn", "${message("admin.login.captchaRequired")}");
				return false;
			}
			$.ajax({
				url:$forgetPwdForm.attr("action"),
				type:"POST",
				data: $forgetPwdForm.serialize(),
				cache:false,
				beforeSend: function() {
					$submit.prop("disabled", true);
				},
				success:function(message){
					$submit.prop("disabled", false);
					$.message(message);
					if (message.type == "success") {
						setTimeout(function() {
							location.href = "${base}/";
						}, 3000);
					} else {
						setTimeout(function() {
						location.reload();
						}, 3000);
					}
				},
			});
		},
	});
});
</script>
</head>
<body>
		<div class="header">
		    <img class="logo" src="${base}/resources/images/header_logo.png" alt="中国位置"/>
		</div>
		<div class="container">
		    <div class="stance"></div>
		    <div class="register">
		        <div class="title">
		            <h1>找回密码</h1>
		        </div>
		        <div class="content">
					<form id="forgetPwdForm" action="sendPwdEmail.jhtml" method="post">
						<input type="hidden" name="captchaId" value="${captchaId}" />
						<table class="input">
							<tr>
								<th>
									<span class="requiredField">*</span>${message("admin.reg.email")}:
								</th>
								<td>
									<input type="text" id="email" name="email" class="text"/>
								</td>
							</tr>
							<tr>
									<th>
										<span class="requiredField">*</span>${message("admin.captcha.name")}:
									</th>
									<td>
										<input type="text" id="captcha" name="captcha" class="captcha" maxlength="4" autocomplete="off" /><img id="captchaImage" class="captchaImage" src="${base}/console/common/captcha.jhtml?captchaId=${captchaId}" title="${message("admin.captcha.imageTitle")}" />
									</td>
							</tr>
							<tr>
								<th>
									&nbsp;
								</th>
								<td>
									<input type="submit" class="regButton" value="${message("admin.find.password")}" />
								</td>
							</tr>
							 <tr>
		                        <td>&nbsp;</td>
		                        <td>
		                         		   如果忘记邮箱？请返回<a href="${base}/reg.jsp" class="loginLink">注册</a>
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
</body>
</html>
