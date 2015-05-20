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
</style>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	
	// 表单验证
	$inputForm.validate({
		rules: {
			fStatus: "required"
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		${message("common.current.position")}：${message("User.device")}&raquo; ${message("User.device.edit")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("User.device.info")}" />
				
			</li>
		</ul>
		<table class="input tabContent">
			<tr>
				<th>
					IMEI:
				</th>
				<td>
					<input type="hidden" name="fSn" value="${device.fSn}" />${device.fSn}
				</td>
			</tr>
			<tr>
				<th>
					${message("User.device.isvirtual")}:
				</th>
				<td>
		                 [#if "${device.fIsvirtual}" == "YES" ] ${message("admin.common.true")} [/#if]
		                 [#if "${device.fIsvirtual}" == "NO" ] ${message("admin.common.false")} [/#if]
				</td>
			</tr>
			<tr>
				<th>
					${message("User.device.protocol")}:
				</th>
				<td>
		               [#if "${device.fProtocol}" == "1" ] ${message("User.device.fProtocol.M2616")} [/#if]
		               [#if "${device.fProtocol}" == "2" ] ${message("User.device.fProtocol.OBD")} [/#if]
		               [#if "${device.fProtocol}" == "3" ] ${message("User.device.fProtocol.MT90")}  [/#if]
		               [#if "${device.fProtocol}" == "4" ] ${message("User.device.fProtocol.T808")}  [/#if]
				</td>
			</tr>
			<tr>
				<th>
					${message("User.device.sim")}:
				</th>
				<td>
					${device.fPhone} 
				</td>
			</tr>
			<tr>
				<th>
					${message("User.device.type")}:
				</th>
				<td>
		                  [#if "${device.fType}" == "1" ]${message("User.device.type.auto")} [/#if]
		                  [#if "${device.fType}" == "2" ] ${message("User.device.type.pet")} [/#if]
		                 [#if "${device.fType}" == "3" ]${message("User.device.type.human")} [/#if]
				</td>
			</tr>
				<tr>
				<th>
					${message("User.device.status")}:
				</th>
				<td>
					${message("user.device.status.enabled")}<input type="radio" name="fStatus" [#if "${device.fStatus}" == "1" ]checked="checked" [/#if] value="1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					${message("user.device.status.disabled")}<input type="radio" name="fStatus" [#if "${device.fStatus}" == "2" ]checked="checked" [/#if]  value="2">
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