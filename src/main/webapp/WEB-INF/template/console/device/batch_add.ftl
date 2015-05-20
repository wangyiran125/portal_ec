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
	var $batchFile = $("#batchFile");
	var $fIsvirtual = $("#fIsvirtual");
	var $fProtocol = $("#fProtocol");
	var $fType = $("#fType");
	var $file = $("#file");
	var $submit = $(":submit");
	var $statusTr = $("#statusTr");
	
	var fIsvirtual;	
	var fProtocol;	
	var fType;	
	var file;	
	var first;
	var buildCount;
	var buildTime;
	
	// 表单验证
	$inputForm.validate({
		rules: {
			fIsvirtual: {
				required: true
			},
			fProtocol: {
				required: true
			},
			fType: {
				required: true
			},
			file: {
				required: true
			}
		},
		submitHandler: function(form) {
				var excelFile = $("#file").val();
			   if(excelFile=='') {
			   		$.message("warn", "${message("device.batch.excle.select")}");
			   		return false;
			   	}
		       if(excelFile.indexOf('.xls')==-1 && excelFile.indexOf('.xlsx')==-1){
		       		$.message("warn", "${message("device.batch.excle.nullity")}");
		       		return false;
		       	}
			$submit.prop("disabled", true);
			$statusTr.show();
			form.submit();
		}
	});
	[#if resMessage.content??]
	alert("${message("${resMessage.content}")}");
	[/#if]
});
</script>
</head>
<body>
	<div class="path">
		${message("common.current.position")}：${message("User.device")}&raquo; ${message("User.device.batch.upload")}
	</div>
	<form id="inputForm" action="batchAddSave.jhtml" method="post" enctype="multipart/form-data">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("User.device.info")}" />
			</li>
		</ul>
		<table class="input tabContent">
		<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="${message("admin.common.downloadTemplate")}" onclick="location.href='${base}/template/template.xlsx'" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("User.device.isvirtual")}:
				</th>
				<td>
					<select id="isVirtual" name="fIsvirtual">
						<option value="" selected="selected">${message("admin.common.choose")}</option>
		                <option value="YES">${message("admin.common.true")}</option>
		                <option value="NO">${message("admin.common.false")}</option>
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
		                <option value="1">2616</option>
		                <option value="2">OBD</option>
		                <option value="3">MT90</option>
		                <option value="4">808</option>
		            </select>
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
		            </select>
				</td>
			</tr>		
			<tr>
				<th>
					<span class="requiredField">*</span>${message("User.device.batch.choosefile")}:
				</th>
				<td>
					<input type="file" id="file" name="file"/>
				</td>
			</tr>
				<tr id="statusTr" class="hidden">
				<th>
					&nbsp;
				</th>
				<td>
					<span class="loadingBar">&nbsp;</span>
					<div id="status">${message("device.batch.upload.processing")}</div>
				</td>
			</tr>
			[#if resMessage.content??]
			<tr>
				<th>${message("device.upload.result")}</th>
				
				<td>
					<table>
						<tr>
							<td>${message("device.upload.success")}</td>
							<td>${successCount}</td>
						</tr>
						<tr>
							<td>${message("device.upload.fail")}</td>
							<td>${faileCount}</td>
						</tr>
						[#if faileCount > 0]
							[#if dupfailDeviceIds?? && dupfailDeviceIds?size>0]
							<tr>
								<td>${message("device.upload.failimei")}</td>
								<td>${dupfailDeviceIds}</td>
							</tr>
							[/#if]
							[#if formatFailDeviceIds?? && formatFailDeviceIds?size>0]
								<tr>
								<td>${message("device.upload.failformat")}</td>
								<td>${formatFailDeviceIds}</td>
							</tr>
							[/#if]
						[/#if]
					</table>
				</td>
			</tr>
			[/#if]
		</table>
		<table class="input">
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button"   value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
			</tr>
		</table>
	</form>
</form>
</body>
</html>
