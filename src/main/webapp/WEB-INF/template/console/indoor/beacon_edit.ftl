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
			fLng:"required",
			fLat:"required",
			fLocate:"required",
			fFloor:"required",
			fRoom:"required",
			fSystem:"required",
			fPx:"required",
			fPy:"required"
		}
	});
	
	
	
});
</script>
</head>
<body>
	<div class="path">
		${message("common.current.position")}：${message("admin.indoor.manage")}&raquo;${message("Indoor.beacon.manage.list")}&raquo;${message("Indoor.beacon.edit")}
	</div>
	<form id="inputForm" action="beacon_update.jhtml" method="post">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("Indoor.beacon.info")}" />
				
			</li>
		</ul>
		<table class="input tabContent">
			<tr>
				<th>
					${message("Indoor.coordinate.mac")}
				</th>
				<td>
					<input type="hidden" name="fMac" value="${beacon.fMac}" />${beacon.fMac}
				</td>
			</tr>
			<tr>
				<th>
					${message("Indoor.coordinate.lng")}
				</th>
				<td>
					<input type="text" name="fLng" value="[#if beacon.fLng??]${beacon.fLng}[/#if]"/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Indoor.coordinate.lat")}
				</th>
				<td>
					<input type="text" name="fLat" value="[#if beacon.fLat??]${beacon.fLat}[/#if]"/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Indoor.coordinate.locate")}
				</th>
				<td>
					<input type="text" name="fLocate" value="[#if beacon.fLocate??]${beacon.fLocate}[/#if]"/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Indoor.coordinate.floor")}
				</th>
				<td>
					<input type="text" name="fFloor" value="[#if beacon.fFloor??]${beacon.fFloor}[/#if]"/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Indoor.coordinate.room")}
				</th>
				<td>
					<input type="text" name="fRoom" value="[#if beacon.fRoom??]${beacon.fRoom}[/#if]"/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Indoor.coordinate.system")}
				</th>
				<td>
					<select id="fSystem" name="fSystem">
						[#list page.content as coordinate]
						 <option  [#if coordinate.id=beacon.fSystem] selected="selected"[/#if] value="${coordinate.id}">${coordinate.fLocation}--${coordinate.fName}</option>
						[/#list]
		            </select>
				</td>
			</tr>
			<tr>
				<th>
					${message("Indoor.coordinate.x")}
				</th>
				<td>
					<input type="text" name="fPx" value="[#if beacon.fPx??]${beacon.fPx}[/#if]"/>
				</td>
			</tr>
			<tr>
				<th>
					${message("Indoor.coordinate.y")}
				</th>
				<td>
					<input type="text" name="fPy" value="[#if beacon.fPy??]${beacon.fPy}[/#if]"/>
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
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='beacon_list.jhtml'" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>