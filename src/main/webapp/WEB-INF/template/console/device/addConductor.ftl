<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加指挥机</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/js/list.js"></script>

<style type="text/css">
html{
	padding-left: 20px;
	padding-right: 15px;
}
.roles label {
	width: 150px;
	display: block;
	float: left;
	padding-right: 6px;
}
</style>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	
	// 表单验证
	$inputForm.validate({
		rules: {
			fSn: {
				required:true,
				minlength: 15,
				maxlength: 15
			},
			fIsvirtual:"required",
			fProtocol:"required",
			fPhone:"required",
			fType:"required"
		
		},
		submitHandler:function(form){
			 $.ajax({
					 url: "${base}/console/device/saveConductor.jhtml",
					 type: "POST",
					 data: $("#inputForm").serialize(),
					 dataType: "json",
					 cache: false,
					 success: function(message) {
					 	if (message.type == "success") {
							window.location.href='${base}/console/device/listConductor.jhtml';
						 }else{
					 		alert("设备已存在");
					 	}
							 	
					 }
				 });
		}
	});	
	
	
});
</script>
</head>
<body>
	<div class="path">
		${message("common.current.position")}：${message("User.device")}&raquo; ${message("User.device.manage.list")}&raquo; ${message("User.device.add")}
	</div>
	<form id="inputForm" action="saveConductor.jhtml" method="post">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("User.device.info")}" />
			</li>
		</ul>
		<table class="input tabContent">
			<tr>
				<th>
					<span class="requiredField">*</span>手持机设备号:
				</th>
				<td>
					<input type="text" name="sn" class="text"  maxlength="15"/>
				</td>
			</tr>
			<!--
			<tr>
				<th>
					<span class="requiredField">*</span>手持机名称:
				</th>
				<td>
					<input type="text" name="name" class="text"  maxlength="15"/>
				</td>
			</tr>
			-->
			
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