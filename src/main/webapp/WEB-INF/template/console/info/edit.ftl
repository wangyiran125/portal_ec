<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.main.statisticsNav")}</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/css/jquery.jqplot.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.cookie.js"></script>
[#if user.modeType == "BACKENDMODE"]
<script type="text/javascript" src="${base}/resources/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.fileupload.js"></script>
[/#if]
<style type="text/css">
	html{
		padding-left: 20px;
		padding-right: 15px;
	}
  .imageTd{
       width:170px;
  }
  .imageTd img{
       width:150px;
       max-height:150px;
   }
 table.input th {
  	width:20%;
  }
  .imageTd{
        width:170px;
   }
  .imageTd img{
        width:150px;
        max-height:150px;
   }
   .fieldSet label{
       float:left;
    }
</style>
</head>
<body>
	<div class="path">
		${message("common.current.position")}：
		[#if user.userType == "INDIVIDUAL"]
			${message("info.show.individual")}
		[#else]
			${message("info.show.enterprise")}
		[/#if]
		&raquo;
		[#if user.userType == "INDIVIDUAL"]
			${message("info.edit.individual")}
		[#else]
			${message("info.edit.enterprise")}
		[/#if]
	</div>
			<ul id="tab" class="tab">
				[#if user.userType == "ENTERPRISE"] 
					<li>
						<input type="button" value="${message("info.show.enterprise")}" />
					</li>
				[/#if]
				[#if user.userType == "INDIVIDUAL"] 
					<li>
						<input type="button" value="${message("info.show.individual")}" />
					</li>
				[/#if]
			</ul>
			<form id="enterpriseInputForm">
			[#if user.userType == "ENTERPRISE"]
				<table id="enterpriseShow" class="input tabContent">
					<tr>
							<th>
								 ${message("Company.name")}:
							</th>
							<td>
								[#if user.enterpriseUserExtend.companyName??]${user.enterpriseUserExtend.companyName}[/#if]
							</td>
						</tr>
						<tr>
							<th>
								${message("Company.address")}:
							</th>
							<td>
								<input type="text" name="companyAddress" value="[#if user.enterpriseUserExtend.companyAddress??]${user.enterpriseUserExtend.companyAddress}[/#if]"/>
							</td>
						</tr>
						<tr>
							<th>
								${message("Company.contactPerson")}:
							</th>
							<td>
								<input type="text" name="contactPerson" value="[#if user.enterpriseUserExtend.contactPerson??]${user.enterpriseUserExtend.contactPerson}[/#if]"/>
							</td>
						</tr>
						<tr>
							<th>
								${message("Company.contactEmail")}:
							</th>
							<td>
								<input type="text" name="contactEmail" value="[#if user.enterpriseUserExtend.contactEmail??]${user.enterpriseUserExtend.contactEmail}[/#if]""/>
							</td>
						</tr>
						<tr>
							<th>
								${message("Company.contactCellPhoneNum")}:
							</th>
							<td>
								<input type="text" name="contactCellPhoneNum" value="[#if user.enterpriseUserExtend.contactCellPhoneNum??]${user.enterpriseUserExtend.contactCellPhoneNum}[/#if]"/>
							</td>
						</tr>
						<tr>
							<th>
								${message("Company.contactPhoneNum")}:
							</th>
							<td>
								<input type="text" name="contactPhoneNum" value="[#if user.enterpriseUserExtend.contactPhoneNum??]${user.enterpriseUserExtend.contactPhoneNum}[/#if]"/>
							</td>
						</tr>
						<tr>
							<th>
								${message("Company.licenseNum")}:
							</th>
							<td>
								[#if user.enterpriseUserExtend.licenseNum??]${user.enterpriseUserExtend.licenseNum}[/#if]
							</td>
						</tr>
						<tr>
							<th>
								${message("Company.organizationCode")}:
							</th>
							<td>
								[#if user.enterpriseUserExtend.organizationCode??]${user.enterpriseUserExtend.organizationCode}[/#if]
							</td>
						</tr>
						<tr>
							<th></th>
							<td>
								<input type="submit" class="button" id="enterpriseSubmit" name="submit" value="${message("guide.operate.confirm")}">
							</td>
						</tr>
				</table>
			[/#if]
			</form>
			<form id="individualForm">
				[#if user.userType == "INDIVIDUAL"] 
					<table id="individualShow" class="input tabContent">
						<tr>
							<th>
								${message("Individual.name")}:
							</th>
							<td>
								${user.individualUserExtend.name}
							</td>
						</tr>
						<tr>
							<th>
								${message("Individual.identityCard")}:
							</th>
							<td>
								${user.individualUserExtend.identityCard}
							</td>
						</tr>
						<tr>
							<th>
								${message("Individual.address")}:
							</th>
							<td>
								<input type="text" name="address" value="[#if user.individualUserExtend.address??]${user.individualUserExtend.address}[/#if]"/>
							</td>
						</tr>
						<tr>
							<th>
								${message("Individual.email")}:
							</th>
							<td>
								<input type="text" name="email" value="[#if user.individualUserExtend.email??]${user.individualUserExtend.email}[/#if]"/>
							</td>
						</tr>
						<tr>
							<th>
								${message("Individual.cellPhoneNum")}:
							</th>
							<td>
								<input type="text" name="cellPhoneNum" value="[#if user.individualUserExtend.cellPhoneNum??]${user.individualUserExtend.cellPhoneNum}[/#if]"/>
							</td>
						</tr>
						<tr>
							<th>
								${message("Individual.phoneNum")}:
							</th>
							<td>
								<input type="text" name="phoneNum" value="${user.individualUserExtend.phoneNum}"/>
							</td>
						</tr>
							<tr>
							<th>
							</th>
							<td>
								<input type="submit" id="individualSubmit" name="individualSubmit" class="button" value="${message("guide.operate.confirm")}"/>
							</td>
						</tr>
					</table>
				[/#if]
			</form>
			
			
[#if user.userType == "ENTERPRISE"]
<script>
$(function(){
//企业表单信息
	
		$enterpriseInputForm =$("#enterpriseInputForm");
		$enterpriseSubmit =$("#enterpriseSubmit");
		// 表单验证
		$enterpriseInputForm.validate({
			rules: {
				companyAddress: {
					required: true
				},
				contactPerson: {
					required: true
				},
				contactEmail: {
					required: true
				}
			},
			submitHandler: function(form) {
				$contactCellPhoneNum =$("input[name='contactCellPhoneNum']");
				$contactPhoneNum =$("input[name='contactPhoneNum']");
				
				if($contactCellPhoneNum.val().length<1 &&$contactPhoneNum.val().length<1){
					$.message("warn", "${message("guide.validate.phone")}");
					return;
				}
				$enterpriseSubmit.prop("disabled", true);
				enterpriseUpdate();
			}
		});
		
		function enterpriseUpdate(){
				$.ajax({
					 url: "${base}/console/info/update/enterprise.jhtml",
					 type: "POST",
					 data: $enterpriseInputForm.serialize(),
					 dataType: "json",
					 cache: false,
					 success: function(message) {
						 if(message.type == "success"){
						 	$.message("success", "${message("user.info.upload.success")}");
						 }else{
						 	$.message("warn", "${message("user.info.upload.failure")}");
						 }
						 	$enterpriseSubmit.prop("disabled", false);
					 }
				 });
		
		}
		
	
});
</script>
[/#if]
[#if user.userType == "INDIVIDUAL"] 
<script>
//个人表单信息
$(function(){
	$individualForm =$("#individualForm");
	$individualSubmit=$("#individualSubmit");
	$individualForm.validate({
			rules: {
				address: {
					required: true
				},
				email: {
					required: true
				}
			},
			submitHandler: function(form) {
				$cellPhoneNum =$("input[name='cellPhoneNum']");
				$phoneNum =$("input[name='phoneNum']");
				
				if($cellPhoneNum.val().length<1 && $phoneNum.val().length<1){
					$.message("warn", "${message("guide.validate.phone")}");
					return;
				}
				$individualSubmit.prop("disabled", true);
				individualUpdate();
			}			
		})
		function individualUpdate(){
				$.ajax({
					 url: "${base}/console/info/update/individual.jhtml",
					 type: "POST",
					 data: $individualForm.serialize(),
					 dataType: "json",
					 cache: false,
					 success: function(message) {
						 if(message.type == "success"){
						 	$.message("success", "${message("user.info.upload.success")}");
						 }else{
						 	$.message("warn", "${message("user.info.upload.failure")}");
						 }
						 	$individualSubmit.prop("disabled", false);
					 }
				 });
		
		}
});
</script>
[/#if]

 <script>
$(function(){
		
		var $developer = $("#developer");
		var $backendmode=$("#backendmode");
		var $btnPreview=$("#btnPreview");
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
	$btnPreview.click(function(){
		 $.ajax({
					 url: "${base}/console/backendmode/preview.jhtml",
					 type: "POST",
					 data: $("#backendmodeForm").serialize(),
					 dataType: "text",
					 cache: false,
					 success: function(message) {
							 	window.open('${base}'+message);  
					 }
				 });
	})
})
</script>

</body>
</html>
