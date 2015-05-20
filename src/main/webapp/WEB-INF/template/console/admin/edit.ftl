<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.admin.edit")}</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/js/input.js"></script>
<style type="text/css">
	html{
		padding-left: 20px;
		padding-right: 15px;
	}
.roles label {
	width: 150px;
	display: block;
	float: left;
	padding-right: 5px;
}
</style>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	
	
	// 表单验证
	$inputForm.validate({
		rules: {
			password: {
				pattern: /^[^\s&\"<>]+$/,
				minlength: 4,
				maxlength: 20
			},
			rePassword: {
				equalTo: "#password"
			},
			email: {
				required: true,
				email: true
			},
			name: "required",
			roleIds: "required",
			adminStatus: "required"
		},
		messages: {
			password: {
				pattern: "${message("admin.validate.illegal")}"
			}
		}
	});

});
</script>
</head>
<body>
	<div class="path">
		${message("common.current.position")}：${message("admin.main.admin")}&raquo; ${message("admin.admin.edit")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
		<input type="hidden" name="id" value="${user.id}" />
		<input type="hidden" name="userName" value="${user.userName}" />
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("admin.admin.base")}" />
			</li>
		</ul>
		<table class="input tabContent">
			<tr>
				<th>
					${message("Admin.username")}:
				</th>
				<td>
					${user.userName}
				</td>
			</tr>
			<tr class="roles">
				<th>
					<span class="requiredField">*</span>${message("Admin.roles")}:
				</th>
				<td>
					<span class="fieldSet">
						[#list roles as role]
							<label>
								<input type="checkbox" name="roleIds" value="${role.id}"[#if user.roles?seq_contains(role)] checked="checked"[/#if] />${role.name}
							</label>
						[/#list]
					</span>
				</td>
			</tr>
		</table>
		<table class="input">
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>