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
			fLocation:"required",
			fName:"required",
			fLng:"required",
			fLat:"required",
			fFloor:"required"
		}
	});
	
	
	
});
</script>
</head>
<body>
	<div class="path">
		${message("common.current.position")}：${message("admin.indoor.manage")}&raquo;${message("indoor.coordinate.manage")}&raquo;${message("Indoor.coordinate.edit")}
	</div>
	<form id="inputForm" action="coordinate_update.jhtml" method="post">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("Indoor.coordinate.info")}" />
				
			</li>
		</ul>
		<table class="input tabContent">
		
		<input type="hidden" name="id" value="${coordinate.id}" />
		
			<tr>
				<th>
					${message("Indoor.coordinate.location")}
				</th>
				<td>
					<input type="text" name="fLocation" value="${coordinate.fLocation}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Indoor.coordinate.name")}
				</th>
				<td>
					<input type="text" name="fName" value="${coordinate.fName}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Indoor.coordinate.lng")}
				</th>
				<td>
					<input type="text" name="fLng" value="[#if coordinate.fLng??]${coordinate.fLng}[/#if]"/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Indoor.coordinate.lat")}
				</th>
				<td>
					<input type="text" name="fLat" value="[#if coordinate.fLat??]${coordinate.fLat}[/#if]"/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Indoor.coordinate.floor")}
				</th>
				<td>
					<input type="text" name="fFloor" value="[#if coordinate.fFloor??]${coordinate.fFloor}[/#if]"/>
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
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='coordinate_list.jhtml'" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>