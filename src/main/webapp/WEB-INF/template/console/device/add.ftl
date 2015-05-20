<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.admin.add")}</title>
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
					 url: "${base}/console/device/save.jhtml",
					 type: "POST",
					 data: $("#inputForm").serialize(),
					 dataType: "json",
					 cache: false,
					 success: function(message) {
					 	if (message.type == "success") {
							window.location.href='${base}/console/device/list.jhtml';
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
	<form id="inputForm" action="save.jhtml" method="post">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("User.device.info")}" />
			</li>
		</ul>
		<table class="input tabContent">
			<tr>
				<th>
					<span class="requiredField">*</span>IMEI:
				</th>
				<td>
					<input type="text" name="fSn" class="text"  maxlength="15"/>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("User.device.isvirtual")}:
				</th>
				<td>
					<select id="fIsVirtual" name="fIsvirtual">
						<option value="" selected="selected">${message("admin.common.choose")}</option>
		                <option value="YES">${message("admin.common.true")}</option>
		                <option value="NO"">${message("admin.common.false")}</option>
		            </select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("User.device.protocol")}:
				</th>
				<td>
					<select id="fProtocol" name="fProtocol">
						<option value="" selected="selected">${message("admin.common.choose")}</option>
		                <option value="1">M2616</option>
		                <option value="2">OBD</option>
		                <option value="3">MT90</option>
		                <option value="4">T808</option>
		                <option value="5">RD</option>
		            </select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("User.device.sim")}:
				</th>
				<td>
					<input type="text" name="fPhone" class="text" maxlength="20" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("User.device.type")}:
				</th>
				<td>
					<select id="fType" name="fType">
						<option value="" selected="selected">${message("admin.common.choose")}</option>
		                <option value="1">${message("User.device.type.auto")}</option>
		                <option value="2">${message("User.device.type.pet")}</option>
		                <option value="3">${message("User.device.type.human")}</option>
		                <option value="4">手持机</option>
		            </select>
				</td>
			</tr>
			<tr>
				<th>
			<span class="requiredField">*</span>手持机sn:
				</th>
				<td>
				<select id="conductorSn" name="conductorSn">
					[#list conductors as conductor]
		                <option value="${conductor.sn}">${conductor.sn}</option>
					[/#list]
		        </select>
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