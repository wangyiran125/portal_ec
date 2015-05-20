<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.account.edit")}</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/js/input.js"></script>
<style type="text/css">
	html{
		padding-left: 20px;
		padding-right: 15px;
	}
</style>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $password = $("#password");
	var $nickName = $("#nickName");
	var $submit = $(":submit");
	
	
	// 表单验证
	$inputForm.validate({
		rules: {
			password: {
				required: true,
				pattern: /^[^\s&\"<>]+$/,
				minlength: 6
			},
			rePassword: {
				equalTo: "#password"
			},
			nickName: {
				required: true
			}
		},
		messages: {
			password: {
				pattern: "${message("admin.reg.passwordIllegal")}",
				minlength: "${message("admin.password.minlength",6)}"
			},
			repassword:{
					 equalTo:"${message("admin.reg.passwordIllegal")}"
			},
			nickName:{
				required:"${message("admin.reg.nickNameRequired")}"
			}
		},
		
		submitHandler:function(form){
			$submit.attr("disabled",true);
			$.ajax({
				url:$inputForm.attr("action"),
				type:"POST",
				data:{
						password:$password.val(),
						nickName:$nickName.val()
				},
				dataType:"json",
				cache:false,
				success:function(message){
					$.message(message);
					$submit.attr("disabled",false);
					setTimeout("location.href='accountInfo.jhtml'",1000);
				}
			});
		}
	});
});
</script>
</head>
<body>
	<div class="path">
		 ${message("common.current.position")}：${message("admin.account.setting")}&raquo; ${message("admin.account.settingGroup")}
	</div>
	<form id="inputForm" action="edit.jhtml" method="post">
		<input type="hidden" name="id" value="${user.id}" />
		<input type="hidden" name="userName" value="${user.userName}" />
		<table class="input tabContent">
			<tr>
				<th>
					${message("Admin.username")}:
				</th>
				<td>
					${user.userName}
				</td>
			</tr>
			<tr>
				<th>
					${message("Admin.password")}:
				</th>
				<td>
					<input type="password" id="password" name="password" class="text" maxlength="20" />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.admin.rePassword")}:
				</th>
				<td>
					<input type="password" name="rePassword" id="rePassword" class="text" maxlength="20" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Admin.nickName")}:
				</th>
				<td>
					<input type="text" id="nickName" name="nickName" class="text" value="${user.nickName}" maxlength="200" />
				</td>
			</tr>				
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" 
					[#if user.isSystem == 1]
						onclick="javascript:window.top.adminBack('admin');"
					[/#if]
					[#if user.isSystem == 0 && user.modeType == "DEVMODE"]
						onclick="javascript:window.top.adminBack('devmode');"
					[/#if]
					[#if user.isSystem == 0 && user.modeType == "BACKENDMODE"]
						onclick="javascript:window.top.adminBack('config');"
					[/#if]
					 />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>