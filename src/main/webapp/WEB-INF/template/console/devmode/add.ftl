<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.devmode.devinfo")}</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.cookie.js"></script>
<style type="text/css">
	html{
		padding-left: 20px;
		padding-right: 15px;
	}
</style>
<script type="text/javascript">
$(function(){
	var $inputForm = $("#inputForm");
	var $developer = $("#developer");
	var $backendmode=$("#backendmode");
	var $devId = $("#devId");
	var $devDomain = $("#devDomain")
	var $devKeyContent = $("#devKeyContent")
	var $devKey = $("#devKey")
	
	var $submit =$(":submit");
		// 表单验证
	$inputForm.validate({
		rules: {
			devDomain: "required"
		}
				
	});
	$devDomain.blur(function(){
			if($devDomain.val() == ""){
				return false;
			}
			$.ajax({
			url: "${base}/console/devmode/bulid.jhtml",
			type: "POST",
			data: {"devDomain":$devDomain.val(),"devId":$devId.val()},
			dataType: "json",
			cache: false,
			success: function(message) {
				if (message.type == "success") {
					$devKey.val(message.content);
					$devKey.parent().parent().show();
					$devKeyContent.text(message.content);
				}  
			}
		});
	})
	
	$developer.click(function(){
		 $.ajax({
					 url: "${base}/console/setting/selectModeType.jhtml",
					 type: "POST",
					 data: {"modeType":"DEVMODE"},
					 dataType: "json",
					 cache: false,
					 success: function(message) {
						 if (message.type == "success") {
						 	$.cookie('cust_pre_url',"${base}/console/devmode/apilist.jhtml",{expires:1,path:'/'});
							 window.parent.location.href='${base}/console/common/main.jhtml';
						 }
					 }
				 });
	})
	
	$backendmode.click(function(){
		 $.ajax({
					 url: "${base}/console/setting/selectModeType.jhtml",
					 type: "POST",
					 data: {"modeType":"BACKENDMODE"},
					 dataType: "json",
					 cache: false,
					 success: function(message) {
						 if (message.type == "success") {
						 $.cookie('cust_pre_url',"${base}/console/device/list.jhtml",{expires:1,path:'/'});
							 	window.parent.location.href='${base}/console/common/main.jhtml';  
						 }
					 }
				 });
	})
});
	
</script>
</head>
<body>
	<div class="path">
		${message("common.current.position")}: ${message("admin.main.devcenter")}&raquo;${message("admin.devmode.base")}
	</div>
	
	<form id="inputForm" action="save.jhtml" method="post">
		<input type="hidden" name="modeType" value="${user.modeType}">
		<table class="input tabContent">
		[#if display??]
			<tr>
				<th >${message("admin.devmode.selectmode")}</th>
				<td id="modeSelect">
					<input type="button" id="developer" class="button " value="${message("Developer.mode")}"/>
					<input type="button" id="backendmode" class="button" value="${message("Backstage.mode")}"/>
				</td>
			</tr>
			[/#if]
			<tr>
				<th>${message("admin.devmode.devid")}</th>
				<td><input id="devId" type="text"  name="devId" readonly value="${user.userName}"/></td>
			</tr>
			<tr>
				<th>${message("admin.devmode.devdomain")}</th>
				<td><input id="devDomain" type="text" name="devDomain" value="${user.devModeUserExtend.devDomain}"/></td>
			</tr>
			<tr [#if !user.devModeUserExtend.devKey??]style="display:none"[/#if]>
				<th>${message("admin.devmode.apiKey")}</th>
				<td >
					<input type="hidden" id="devKey" name="devKey" value="${user.devModeUserExtend.devKey}">
					<span id="devKeyContent">[#if user.devModeUserExtend.devKey??]${user.devModeUserExtend.devKey}[/#if]</span>
				</td>
			</tr>
			<tr>
				<th>${message("admin.devmode.alarmstate")}</th>
				<td>
					<select name="alarmState">
						<option value="OPEN"  [#if user.devModeUserExtend.alarmState=="OPEN"]
                            selected="selected"[/#if]>${message("admin.devmode.alarmopen")}</option>
						<option value="CLOSE"  [#if user.devModeUserExtend.alarmState=="CLOSE"]
                            selected="selected"[/#if]>${message("admin.devmode.alarmclose")}</option>
					</select>
				</td>
			</tr>
			
			<tr >
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button"  value="${message("admin.common.submit")}" onsubmit="javascript:onsubmit()" />
				</td>
			</tr>
		</table>
		
	</form>
</body>
</html>