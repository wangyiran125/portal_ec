<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.find.password")}</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/css/password.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $passwordForm = $("#passwordForm");
	var $captcha = $("#captcha");
	var $captchaImage = $("#captchaImage");
	var $submit = $(":submit");
	
	
	// 更换验证码
	$captchaImage.click(function() {
		$captchaImage.attr("src", "${base}/console/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
	});
	
	// 表单验证
	$passwordForm.validate({
		rules: {
			newPassword: {
				required: true,
				pattern: /^[^\s&\"<>]+$/,
				minlength: 6,
			},
			rePassword: {
				equalTo: "#newPassword"
			},
			
		},
		messages:{
		
			newPassword:{
				required:"${message("admin.login.passwordRequired")}",
				pattern:"${message("admin.reg.passwordIllegal")}",
				minlength:"${message("admin.password.minlength",6)}",
			},
			
			rePassword:{
				equalTo:"${message("admin.reg.repasswordIllegal")}",
			},
		},
		
		submitHandler: function(form) {
			if ($captcha.val() == "") {
				$.message("warn", "${message("admin.login.captchaRequired")}");
				return false;
			}
			$.ajax({
				url: $passwordForm.attr("action"),
				type: "POST",
				data: $passwordForm.serialize(),
				dataType: "json",
				cache: false,
				beforeSend: function() {
					$submit.prop("disabled", true);
				},
				success: function(message) {
					$.message(message);
					if (message.type == "success") {
						setTimeout(function() {
							$submit.prop("disabled", false);
							location.href = "${base}/";
						}, 3000);
					} else {
						$submit.prop("disabled", false);
						setTimeout(function() {
						location.reload();
						}, 3000);
					}
				}
			});
		}
	});

});
</script>
</head>
<body>
	 <div class="forgetPwd">
		<form id="passwordForm" action="resetPwd.jhtml" method="post">
			<input type="hidden" name="captchaId" value="${captchaId}" />
			<input type="hidden" name="userName" value="${userName}" />
			<input type="hidden" name="pwdToken" value="${pwdToken}" />
			<div style="text-align:center;font-size:large;"><b>${userName},${message("admin.reset.newpwd")}</b></div>
			<table>
				<tr>
						<td width="160" rowspan="2" align="center" valign="center">
							<img src="${base}/resources/images/login_logo.png" />
						</td>
						<th width="50px">
							${message("admin.new.password")}:
						</th>
						<td>
							<input type="password" id="newPassword" name="newPassword" class="text" maxlength="20" autocomplete="off"/>
						</td>
					</tr>
				<tr>
					<th width="50px">
						${message("admin.new.repassword")}:
					</th>
					<td>
						<input type="password" id="rePassword" name="rePassword" class="text" maxlength="20" autocomplete="off" />
					</td>
				</tr>
				
					<tr>
						<td>
							&nbsp;
						</td>
						<th width="50px">
							${message("admin.captcha.name")}:
						</th>
						<td>
							<input type="text" id="captcha" name="captcha" class="text captcha" maxlength="4" autocomplete="off" /><img id="captchaImage" class="captchaImage" src="${base}/console/common/captcha.jhtml?captchaId=${captchaId}" title="${message("admin.captcha.imageTitle")}" />
						</td>
					</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<th>
						&nbsp;
					</th>
					<td>
						<input type="submit" class="forgetPwdButton" value="${message("admin.password.submit")}" />
					</td>
				</tr>
			</table>
		</form>
	 </div>
</body>
</html>