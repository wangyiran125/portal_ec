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
			fMac: {
				required:true,
				minlength: 15,
				maxlength: 15
			},
			fLng:"required",
			fLat:"required",
			fLocate:"required",
			fFloor:"required",
			fRoom:"required",
			fSystem:"required",
			fPx:"required",
			fPy:"required"
		
		},
		submitHandler:function(form){
			 $.ajax({
					 url: "${base}/console/indoor/beacon_save.jhtml",
					 type: "POST",
					 data: $("#inputForm").serialize(),
					 dataType: "json",
					 cache: false,
					 success: function(message) {
					 	if (message.type == "success") {
							window.location.href='${base}/console/indoor/beacon_list.jhtml';
						 }else{
					 		alert("信标已存在");
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
		${message("common.current.position")}：${message("admin.indoor.manage")}&raquo; ${message("Indoor.beacon.manage.list")}&raquo; ${message("Indoor.beacon.add")}
	</div>
	<form id="inputForm" action="beacon_save.jhtml" method="post">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("Indoor.beacon.info")}" />
			</li>
		</ul>
		<table class="input tabContent">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Indoor.coordinate.mac")}:
				</th>
				<td>
					<input type="text" name="fMac" class="text"  maxlength=""/>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Indoor.coordinate.lng")}:
				</th>
				<td>
					<input type="text" name="fLng" class="text" maxlength="20" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Indoor.coordinate.lat")}:
				</th>
				<td>
					<input type="text" name="fLat" class="text" maxlength="20" />
				</td>
			</tr>
			
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Indoor.coordinate.locate")}:
				</th>
				<td>
					<input type="text" name="fLocate" class="text" maxlength="20" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Indoor.coordinate.floor")}:
				</th>
				<td>
					<input type="text" name="fFloor" class="text" maxlength="20" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Indoor.coordinate.room")}:
				</th>
				<td>
					<input type="text" name="fRoom" class="text" maxlength="20" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Indoor.coordinate.system")}:
				</th>
				<td>
					<select id="fSystem" name="fSystem">
						<option value="" selected="selected">${message("admin.common.choose")}</option>
						[#list page.content as coordinate]
						 <option value="${coordinate.id}">${coordinate.fLocation}--${coordinate.fName}</option>
						[/#list]
		            </select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Indoor.coordinate.x")}:
				</th>
				<td>
					<input type="text" name="fPx" class="text" maxlength="20" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Indoor.coordinate.y")}:
				</th>
				<td>
					<input type="text" name="fPy" class="text" maxlength="20" />
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