<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.devmode.edit")}</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>

<style type="text/css">
	html{
		padding-left: 20px;
		padding-right: 15px;
	}
</style>
<script type="text/javascript">
	function select(mode){
		window.parent.location.href=mode
		};
</script>
</head>
<body>
	<div class="path">
		${message("common.current.position")}：${message("admin.main.devcenter")}&raquo; ${message("admin.devmode.edit")}
	</div>
	<div>
		${message("admin.devmode.selectmode")}:
		<select name="selectmode" onchange="select(this.value)">
			<option value="../common/main.jhtml?mode=DEVMODE" selected="true">${message("admin.devmode.devmode")}</option>
			<option value="../common/main.jhtml?mode=BACKENDMODE">${message("admin.devmode.backendmode")}</option>
		</select>
	</div>
	<form id="inputForm" action="" method="post">
		<table class="input tabContent">
			<tr>
				<td>${message("admin.devmode.devid")}</td>
				<td><input id="devid" type="textbox" readonly="true" style="color: gray;" value="${devModeUserExtend.devId}"/></td>
			</tr>
			<tr>
				<td>${message("admin.devmode.devdomain")}</td>
				<td><input id="devname" type="textbox" readonly="true" style="color: gray;" value="${devModeUserExtend.devDomain}"/></td>
			</tr>
			<tr>
				<td>${message("admin.devmode.devkey")}</td>
				<td><input id="devkey" type="textbox" readonly="true" style="color: gray;" value="${devModeUserExtend.devKey}"/></td>
			</tr>
			<tr>
				<td>${message("admin.devmode.alarmstate")}</td>
				<td>
					<select name="alarmState" readonly="true">
						<option value="OPEN" [#if alarmState="OPEN"] selected="selected"[/#if]>${message("admin.devmode.alarmopen")}</option>
						<option value="CLOSE" [#if alarmState="CLOSE"] selected="selected"[/#if]>${message("admin.devmode.alarmclose")}</option>
					</select>
				</td>
			</tr>
		</table>
		
	</form>
</body>
</html>