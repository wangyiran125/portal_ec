<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.setting.edit")}</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/css/guide.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/js/guide.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.fileupload.js"></script>
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
</style>
<script type="text/javascript">
$().ready(function() {
	
		var $enterpriseInputForm = $("#enterpriseInputForm");
		var $individualInputForm = $("#individualInputForm");
		var $browserButton = $("input.browserButton");
			[#if user.userType?? ]
				[#if user.userType == "ENTERPRISE"]
					$("#enterprise").show();
				[/#if]
				[#if user.userType == "INDIVIDUAL"]
					$("#individual").show();
				[/#if]
			[#else]
			var selectContent='<form id="userTypeForm"><input type="radio" name="userType" value="ENTERPRISE"> 企业&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="userType" value="INDIVIDUAL"> 个人</form>';
			$.dialog({
					type: "success",
					title:"${message("guide.dialog.title")}",
					content:selectContent.trim(),
					width : 410,
					cancel:null,
					close:false,
					onOk: function() {
						var $param =  $("input[name='userType']:checked").val(); ;
						console.log($param);
						if(!$param){
							$.message("warn", "${message("guide.dialog.warn")}");
							return false;
						}else{
							$.message("success", "${message("guide.dialog.success")}");
								$.ajax({
									url: "selectUserType.jhtml",
									type: "POST",
									data: {"userType": $param},
									dataType: "json",
									cache: false,
									success: function(message) {
										if (message.type == "success") {
												if($param == "ENTERPRISE"){
													$("#enterprise").show(500);
												}
												else if($param == "INDIVIDUAL"){
													$("#individual").show(500);
												}
										}
									}
								});
						}
					}
				});
			[/#if]	
	[@flash_message /]
	
	$("#enterprise").scrollable({
		onSeek: function(event,i){
			$("#enterprise #status li").removeClass("active").eq(i).addClass("active");
		},
		onBeforeSeek:function(event,i){
			if(i==1){
				var $contactEmail = $("input[name='contactEmail']");
				var params=new Array();
				params.push($("input[name='companyName']"));
				params.push($("input[name='companyAddress']"));
				params.push($("input[name='contactPerson']"));
				params.push($contactEmail);
				if(paramsValidate(params) >0){
					return false;
				}else{
					if(!validateEmail($contactEmail.val())){
						validatePrompt("add",$contactEmail,'${message("admin.validate.email")}');
						return false;
					}else{
						validatePrompt("remove",$contactEmail,'')
					}
					var $contactCellPhoneNum =$("input[name='contactCellPhoneNum']");
					var $contactPhoneNum =$("input[name='contactPhoneNum']");
					if($contactCellPhoneNum.val() ==""  && $contactPhoneNum.val() == "" ){
						validatePrompt("add",$contactCellPhoneNum,'${message("guide.validate.phone")}');
						return false;
					}else{
						validatePrompt("remove",$contactCellPhoneNum,'')
					}
				}
					$.ajax({
						url: "enterpriseUser/save.jhtml",
						type: "POST",
						data: $("#enterpriseInputForm").serialize(),
						cache: false,
						success: function(result) {
							if(result.type=="success"){
								var $enterpriseIdInput = $("#enterpriseIdInput");
								if(!$enterpriseIdInput.val()){
									$enterpriseIdInput.val(result.content);
								}
							}
						}
					});
				
				
			}else if(i ==2){
				var params=new Array();
				params.push($("input[name='licenseNum']"));
				params.push($("input[name='organizationCode']"));
				params.push($("input[name='licenseImagePath']"));
				if(paramsValidate(params) >0){
					return false;
				}else{
					$.ajax({
						url: "enterpriseUser/save.jhtml",
						type: "POST",
						data: $("#enterpriseInputForm").serialize(),
						cache: false,
						success: function(message) {
							if(message.type=="success"){
								var $enterpriseIdInput = $("#enterpriseIdInput");
								if(!$enterpriseIdInput.val()){
									$enterpriseIdInput.val(result.content);
								}
							}
						}
					});
				}
			}else if(i == 3){
				var $modeType = $("input[name='modeType']:checked");
				 if(!$modeType){
					 $.message("warn", "${message("guide.modetype.not.empty")}");
					 return false;
				 }
				 $.ajax({
					 url: "${base}/console/setting/selectModeType.jhtml",
					 type: "POST",
					 data: {"modeType": $modeType.val()},
					 dataType: "json",
					 cache: false,
					 success: function(message) {
						 if (message.type == "success") {
							  if($modeType.val() == "DEVMODE" ){
							 	$.message("success", "${message("guide.modetype.selectdevmode.success.message")}");
							 	setTimeout("window.parent.location.href='${base}/console/common/main.jhtml'",3000);   
							 }
							 if($modeType.val() == "BACKENDMODE" ){
							 	$.message("success", "${message("guide.modetype.selectbackendmode.success.message")}");
							 	setTimeout("window.parent.location.href='${base}/console/common/main.jhtml'",3000);   
							 }
						 }
					 }
				 });
				 
			}
		}
	});
	
	$("#individual").scrollable({
		onSeek: function(event,i){
			$("#individual #status li").removeClass("active").eq(i).addClass("active");
		},
		onBeforeSeek:function(event,i){
			if(i==1){
					var $email = $("input[name='email']");
					var $identityCard = $("input[name='identityCard']");
					var params=new Array();
					params.push($("input[name='name']"));
					params.push($("input[name='address']"));
					params.push($identityCard);
					params.push($email);
					if(paramsValidate(params) >0){
						return false;
					}else{
						//身份证号码验证
						if(!validateIdCardNo($identityCard.val())){
							validatePrompt("add",$identityCard,'${message("guide.validate.identityCard")}');
							return false;
						}else{
							validatePrompt("remove",$identityCard,'')
						}
						//邮箱验证
						if(!validateEmail($email.val())){
							validatePrompt("add",$email,'${message("admin.validate.email")}');
							return false;
						}else{
							validatePrompt("remove",$email,'')
						}
						//手机号及座机号验证
						var $cellPhoneNum =$("input[name='cellPhoneNum']");
						var $phoneNum =$("input[name='phoneNum']");
						if($cellPhoneNum.val() ==""  && $phoneNum.val() == "" ){
							validatePrompt("add",$cellPhoneNum,'${message("guide.validate.phone")}');
							return false;
						}else{
							if(/^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$/.test($cellPhoneNum)){
								validatePrompt("add",$cellPhoneNum,'${message("guide.validate.cellPhone")}');
								return false;
							}else{
								validatePrompt("remove",$cellPhoneNum,'')
							}
						}
					}
					$.ajax({
						url: "individualUser/save.jhtml",
						type: "POST",
						data: $("#individualInputForm").serialize(),
						cache: false,
						success: function(result) {
							if(result.type=="success"){
								var $individualIdInput = $("#individualIdInput");
								if(!$individualIdInput.val()){
									$individualIdInput.val(result.content);
								}
							}
						}
					});
			}
			else if(i == 2){
				var $modeType = $("input[name='modeType']:checked");
				 if(!$modeType){
					 $.message("warn", "${message("guide.modetype.not.empty")}");
					 return false;
				 }
				 $.ajax({
					 url: "${base}/console/setting/selectModeType.jhtml",
					 type: "POST",
					 data: {"modeType": $modeType.val()},
					 dataType: "json",
					 cache: false,
					 success: function(message) {
						 if (message.type == "success") {
							 if($modeType.val() == "DEVMODE" ){
							 	$.message("success", "${message("guide.modetype.selectdevmode.success.message")}");
							 	setTimeout("window.parent.location.href='${base}/console/common/main.jhtml'",3000);   
							 }
							 if($modeType.val() == "BACKENDMODE" ){
							 	$.message("success", "${message("guide.modetype.selectbackendmode.success.message")}");
							 	setTimeout("window.parent.location.href='${base}/console/common/main.jhtml'",3000);   
							 }
						 }
					 }
				 });
			}
			
		}
	});
	
});
</script>
</head>
<body>
		<div class="path">
			${message("common.current.position")}：${message("admin.main.guide")}&raquo; ${message("admin.setting.guide")}
		</div>
		<form id="enterpriseInputForm" action="update.jhtml" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" id="enterpriseIdInput"   value="[#if user.enterpriseUserExtend.id??]${user.enterpriseUserExtend.id}[/#if]"/>
			<div id="enterprise" class="hiddenDiv">
					<ul id="status">
						<li class="active"><strong>1.</strong>${message("guide.enterprise.basicInfo")}</li>
						<li><strong>2.</strong>${message("guide.enterprise.qualificationInfo")}</li>
						<li><strong>3.</strong>${message("guide.enterprise.complete")}</li>
					</ul>
					<div class="items">
						<div class="page">
								<h3><em>${message("guide.phone.not.empty")}</em></h3>
				              	<table class="input tabContent" border="0">
									<tr>
										<th>
											<span class="requiredField">*</span>${message("Company.name")}:
										</th>
										<td>
											<input type="text" id="companyName" name="companyName" class="text"  value="[#if user.enterpriseUserExtend.companyName??]${user.enterpriseUserExtend.companyName}[/#if]"/>
											<label class="" for="companyName"></label>
										</td>
									</tr>
									<tr>
										<th>
											<span class="requiredField">*</span>${message("Company.address")}:
										</th>
										<td>
											<input type="text" id="companyAddress" name="companyAddress" class="text" value="[#if user.enterpriseUserExtend.companyAddress??]${user.enterpriseUserExtend.companyAddress}[/#if]"/>
											<label class="" for="companyAddress"></label>
										</td>
									</tr>
									<tr>
										<th>
											<span class="requiredField">*</span>${message("Company.contactPerson")}:
										</th>
										<td>
											<input type="text" name="contactPerson" class="text" maxlength="20" value="[#if user.enterpriseUserExtend.contactPerson??]${user.enterpriseUserExtend.contactPerson}[/#if]"/>
											<label class="" for="contactPerson"></label>
										</td>
									</tr>
									<tr>
										<th>
											<span class="requiredField">*</span>${message("Company.contactEmail")}:
										</th>
										<td>
											<input type="text" name="contactEmail" class="text" maxlength="200" value="[#if user.enterpriseUserExtend.contactEmail??]${user.enterpriseUserExtend.contactEmail}[/#if]"/>
											<label class="" for="contactEmail"></label>
										</td>
									</tr>
									<tr>
										<th>
											${message("Company.contactCellPhoneNum")}:
										</th>
										<td>
											<input type="text" name="contactCellPhoneNum" class="text" maxlength="200" value="[#if user.enterpriseUserExtend.contactCellPhoneNum??]${user.enterpriseUserExtend.contactCellPhoneNum}[/#if]"/>
											<label class="" for="contactCellPhoneNum"></label>
										</td>
									</tr>
									<tr>
										<th>
											${message("Company.contactPhoneNum")}:
										</th>
										<td>
											<input type="text" name="contactPhoneNum" class="text" maxlength="200" value="[#if user.enterpriseUserExtend.contactPhoneNum??]${user.enterpriseUserExtend.contactPhoneNum}[/#if]"/>
										</td>
									</tr>
								</table>
				               <div class="btn_nav">
				                  <input type="button" class="next right" value="${message("guide.operate.next")}&raquo;" />
				               </div>
			            </div>
						<div class="page">
				                  	<table class="input tabContent">
									<tr>
										<th>
											<span class="requiredField">*</span>${message("Company.licenseNum")}:
										</th>
										<td>
											<input type="text" name="licenseNum" class="text" maxlength="20" value="[#if user.enterpriseUserExtend.licenseNum??]${user.enterpriseUserExtend.licenseNum}[/#if]"/>
											<label class="" for="licenseNum"></label>
										</td>
									</tr>
									<tr>
										<th>
											<span class="requiredField">*</span>${message("Company.organizationCode")}:
										</th>
										<td>
											<input type="text" id="organizationCode" name="organizationCode" class="text" maxlength="20" value="[#if user.enterpriseUserExtend.organizationCode??]${user.enterpriseUserExtend.organizationCode}[/#if]"/>
											<label class="" for="organizationCode"></label>
										</td>
									</tr>
									  <tr>
								      <th><span class="requiredField">*</span>${message("Company.licenseUpload")}:</th>
								      <td class="imageTd">
								      	<img src="[#if user.enterpriseUserExtend.licenseImagePath??]${"${base}/upload/"+user.enterpriseUserExtend.licenseImagePath}[/#if]" id="imageInpt" alt="">
								      	<label class="" for="licenseImagePath"></label>
								      </td>
								      <td>
									       <button type="button" id="licenseImageBtnAgent" class="btn btn-info ">${message("guide.file.select")}</button>
									       <input type="text"  name="licenseImagePath" style="display: none;" id="licenseImagePathInpt" value="[#if user.enterpriseUserExtend.licenseImagePath??]${user.enterpriseUserExtend.licenseImagePath}[/#if]">
									       <input type="file"  name="licenseImagePath" id="licenseImageBtn" style="display: none;">
									       <input type="button"  id="uploadLicenseImageBtn"value="${message("guide.file.upload.button")}"></input>
									       <label class="" for="licenseImagePath"></label>
								      </td>
							    	 </tr>
								</table>
				               <div class="btn_nav">
				                  <input type="button" class="prev" style="float:left" value="&laquo;${message("guide.operate.pre")}" />
				                  <input type="button" class="next right" value="${message("guide.operate.next")}&raquo;" />
				               </div>
			            </div>
						<div class="page">
			                  <h3><em>${message("guide.need.select.modetype")}</em></h3>
			             	<table class=" tabContent operationMode">
									<tr>
										<th>
											${message("Developer.mode")}:
										</th>
										<td>
											<input type="radio" name="modeType"  value="DEVMODE" size="7"/>
										</td>
										<th>
											${message("Backstage.mode")}:
										</th>
										<td>
											<input type="radio" id="modeType" name="modeType" value="BACKENDMODE" size="7"/>
										</td>
									</tr>
								</table>
			               <div class="btn_nav">
			                  <input type="button" class="prev" style="float:left" value="&laquo;${message("guide.operate.pre")}" />
			                  <input type="button" class="next right" id="sub" value="${message("guide.operate.confirm")}" />
			               </div>
			            </div>
				</div>
			</div>
		</form>
			
		
		<form id="individualInputForm" action="update.jhtml" method="post" enctype="multipart/form-data">
				<input type="hidden" name="id" id="individualIdInput"  value="[#if user.individualUserExtend.id??]${user.individualUserExtend.id}[/#if]"/>
				<div id="individual" class="hiddenDiv">
					<ul id="status">
						<li class="active"><strong>1.</strong>${message("guide.enterprise.personalInfo")}</li>
						<li><strong>2.</strong>${message("guide.enterprise.complete")}</li>
					</ul>
					<div class="items">
						<div class="page">
				                    	<table class="input tabContent" border="0">
									<tr>
										<th>
											<span class="requiredField">*</span>${message("Individual.name")}:
										</th>
										<td>
											<input type="text" name="name" class="text"  value="[#if user.individualUserExtend.name??]${user.individualUserExtend.name}[/#if]"/>
											<label class="" for="name"></label>
										</td>
									</tr>
									<tr>
										<th>
											<span class="requiredField">*</span>${message("Individual.identityCard")}:
										</th>
										<td>
											<input type="text" id="identityCard" name="identityCard" class="text" value="[#if user.individualUserExtend.identityCard??]${user.individualUserExtend.identityCard}[/#if]"/>
											<label class="" for="identityCard"></label>
										</td>
									</tr>
									<tr>
										<th>
											<span class="requiredField">*</span>${message("Individual.address")}:
										</th>
										<td>
											<input type="text" name="address" class="text" maxlength="20" value="[#if user.individualUserExtend.address??]${user.individualUserExtend.address}[/#if]"/>
											<label class="" for="address"></label>
										</td>
									</tr>
									<tr>
										<th>
											<span class="requiredField">*</span>${message("Individual.email")}:
										</th>
										<td>
											<input type="text" name="email" class="text" maxlength="200" value="[#if user.individualUserExtend.email??]${user.individualUserExtend.email}[/#if]"/>
											<label class="" for="email"></label>
										</td>
									</tr>
									<tr>
										<th>
											${message("Individual.cellPhoneNum")}:
										</th>
										<td>
											<input type="text" name="cellPhoneNum" class="text" maxlength="200" value="[#if user.individualUserExtend.cellPhoneNum??]${user.individualUserExtend.cellPhoneNum}[/#if]"/>
											<label class="" for="cellPhoneNum"></label>
										</td>
									</tr>
									<tr>
										<th>
											${message("Individual.phoneNum")}:
										</th>
										<td>
											<input type="text" name="phoneNum" class="text" maxlength="200" value="[#if user.individualUserExtend.phoneNum??]${user.individualUserExtend.phoneNum}[/#if]"/>
											<label class="" for="phoneNum"></label>
										</td>
									</tr>
								</table>
				               <div class="btn_nav">
				                  <input type="button" class="next right" value="${message("guide.operate.next")}&raquo;" />
				               </div>
			            </div>
						<div class="page">
			               <h3><em>${message("guide.need.select.modetype")}</em></h3>
			             	<table class=" tabContent operationMode">
									<tr>
										<th>
											${message("Developer.mode")}:
										</th>
										<td>
											<input type="radio" name="modeType"  value="DEVMODE"  size="7"/>
										</td>
										<th>
											${message("Backstage.mode")}:
										</th>
										<td>
											<input type="radio" id="mode" name="modeType"  value="BACKENDMODE"  size="5"/>
										</td>
									</tr>
								</table>
			               <div class="btn_nav">
			                  <input type="button" class="prev" style="float:left" value="&laquo;${message("guide.operate.pre")}" />
			                  <input type="button" class="next right" id="sub" value="${message("guide.operate.confirm")}" />
			               </div>
			            </div>
				</div>
			</div>
		</form>
<script>
$(function(){

	var $licenseImageBtnAgent = $("#licenseImageBtnAgent") ;
		$licenseImageBtnAgent.click(function(){
			$('#licenseImageBtn').click();
			$('#licenseImageBtn').fileupload({
         		dataType: 'json',
         		url:"license/file.jhtml",
                add: function (e, data) {
                     $.each(data.files, function (index, file) {
                     	if(file.size >= 3109996){
                     		alert(file.name + '${message("admin.upload.file.size.too.large")}');
                     		return false;
                     	}
                     	$('img').attr("src",URL.createObjectURL(file));
                     });
                     data.context = $('#uploadLicenseImageBtn').click(function(){
                    	data.formData={};
                    	data.submit();
                     });
                 },
         	    done: function (e, result) {
         	    	console.log(result.result);
         	    	if(result.result.type == "success"){
         	    		$("#licenseImagePathInpt").val(result.result.content);
         	    		alert("${message("admin.upload.success")}");
         	    	}else{
         	    		alert("${message(" admin.upload.faild")}");
         	    	}
                 }
         	});
	
	});
})
</script>
</body>
</html>
