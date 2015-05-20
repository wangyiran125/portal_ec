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
		overflow-y: auto;
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
    .fieldSet{
        	position:relative; 
			z-index:2;
    }
    
   
	.cusImg {
		margin:0px 0px 20px -150px;
		border:1px solid;
		border-style:ridge;
		display:none;
	}
	
	.cusImg img{
		width:930px;
		height:480px;
	}
</style>
</head>
<body>
	<div class="path">
		${message("common.current.position")}：${message("config.show.main")}&raquo; ${message("config.edit.info")}
	</div>
			<ul id="tab" class="tab">
				[#if user.modeType == "DEVMODE"] 
					<li>
						<input type="button" value="${message("info.show.develop")}" />
					</li>
				[/#if]
				[#if user.modeType == "BACKENDMODE"]
					<li>
						<input type="button" value="${message("info.show.backend")}" />
					</li>
				[/#if]
			</ul>
			[#if user.modeType == "DEVMODE"]
				<table  id="developShow" class="input tabContent">
					<tr>
						<th ><span class="requiredField">*</span>${message("admin.devmode.selectmode")}</th>
						<td id="modeSelect" colspan="2">
							<input type="hidden" id="modeType" name="modeType" value="${user.modeType}"/>
							<input type="button" id="backendmode" class="button" value="${message("Backstage.mode")}" />
						</td>
					</tr>
					<tr>
						<th>${message("admin.devmode.devid")}</th>
						<td>${user.devModeUserExtend.devId}</td>
					</tr>
					<tr>
						<th>${message("admin.devmode.devdomain")}</th>
						<td>${user.devModeUserExtend.devDomain}</td>
					</tr>
					<tr >
						<th>${message("admin.devmode.apiKey")}</th>
						<td >
							${user.devModeUserExtend.devKey}
						</td>
					</tr>
					<tr>
						<th>${message("admin.devmode.alarmstate")}</th>
						<td>
							<input type="radio" name="alarmState" value="OPEN" [#if user.devModeUserExtend.alarmState == "OPEN"]checked="checked"[/#if]> 开启 &nbsp; &nbsp; &nbsp;
							<input type="radio" name="alarmState" value="CLOSE" [#if user.devModeUserExtend.alarmState == "CLOSE"]checked="checked"[/#if]> 关闭
						</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<input type="button" class="button" id="devmodeSubmit" name="devmodeSubmit" value="${message("guide.operate.confirm")}">
						</td>
					</tr>
				</table>
			[/#if]
			<form id="backendmodeForm">
				[#if user.modeType == "BACKENDMODE"]
					<table id="backendShow" class="input tabContent">
					<tr>
						<th ><span class="requiredField">*</span>${message("admin.devmode.selectmode")}</th>
						<td id="modeSelect" colspan="2">
							<input type="hidden" id="modeType" name="modeType" value="${user.modeType}"/>
							<input type="button" id="developer" class="button " value="${message("Developer.mode")}" />
							<!--<input type="button" id="backendmode" class="button" value="${message("Backstage.mode")}"/>-->
						</td>
					</tr>
						 <tr>
				            <th>${message("admin.backendmode.sitename")}</th>
				            <td colspan="2"><input type="text" name="siteName" value="${user.backendModeUserExtend.siteName}"></td>
				        </tr>
						<tr>
				            <th>${message("admin.backendmode.logo")}:</th>
				            <td class="imageTd"><img src="[#if user.backendModeUserExtend.logo??]${"${base}/upload/"+user.backendModeUserExtend.logo}[/#if]" id="imageLogoInpt" alt=""></td>
				            <td>
				                <button type="button" id="logoBtnAgent" class="btn btn-info ">选择文件</button>
				                <input type="text" name="logo" style="display: none;" id="logoInpt" value="[#if user.backendModeUserExtend.logo??]${user.backendModeUserExtend.logo}[/#if]">
				                <input type="file" name="logoFile" style="display: none;" id="logoBtn" >
				                <button type="button" id="uploadLogoBtn">上传</button>
				            </td>
				        </tr>

				        <tr>
				            <th>${message("admin.backendmode.icon")}</th>
				            <td class="imageTd"><img src="[#if user.backendModeUserExtend.icon??]${"${base}/upload/"+user.backendModeUserExtend.icon}[/#if]" id="imageIconInpt" alt=""></td>
				            <td>
				                <button type="button" id="iconBtnAgent" class="btn btn-info ">选择文件</button>
				                <input type="text" name="icon" style="display: none;" id="iconInpt"  value="[#if user.backendModeUserExtend.icon??]${user.backendModeUserExtend.icon}[/#if]">
				                <input type="file" name="iconFile" style="display: none;" id="iconBtn">
				                <button type="button" id="uploadIconBtn">上传</button>
				            </td>
				        </tr>
                        <tr>
                            <th>${message("admin.backendmode.Favicon")}:</th>
                            <td class="imageTd"><img src="[#if user.backendModeUserExtend.favIcon??]${"${base}/upload/"+user.backendModeUserExtend.favIcon}[/#if]"
                                                     id="imageFaviconInpt"  alt="favicon" style="width:16px;height:16px;"><span style="color:red;margin-left:10px">图片大小为16*16</span></td>
                            <td>
                                <button type="button" id="faviconBtnAgent" class="btn btn-info ">选择文件</button>
                                <input type="text" name="favIcon" style="display:none;" id="faviconInpt" value="[#if user.backendModeUserExtend.favIcon??]${user.backendModeUserExtend.favIcon}[/#if]">
                                <input type="file" name="favIconFile"  style="display:none;" id="faviconBtn">
                                <button type="button" id="uploadFaviconBtn">上传</button>
                            </td>
                        </tr>
						<tr>
							<th>${message("admin.backendmode.sitedomain")}</th>
				            <td colspan="2">
				               <input type="text" name="siteDomain" size="55" value="${user.backendModeUserExtend.siteDomain}">
				            </td>
						</tr>
						<tr>
							<th>${message("admin.backendmode.defaultsitedomain")}</th>
				            <td colspan="2">
				            	<input type="hidden" name="defaultSiteDomain" value="${user.backendModeUserExtend.defaultSiteDomain}"/>
				               <a>${user.backendModeUserExtend.defaultSiteDomain}</a>
				            </td>
						</tr>
						   <tr>
					            <th>${message("admin.backendmode.editfontstyle")}</th>
					            <td colspan="2">
					                <select name="frontStyle">
					                    <option value="css"       [#if user.backendModeUserExtend.frontStyle=="css"]       selected="selected"[/#if]>${message("admin.mode.defaultstyle")}</option>
					                    <option value="goldenCss" [#if user.backendModeUserExtend.frontStyle=="goldenCss"] selected="selected"[/#if]>${message("admin.mode.goldenstyle")}</option>
					                    <option value="greenCss"  [#if user.backendModeUserExtend.frontStyle=="greenCss"]  selected="selected"[/#if]>${message("admin.mode.greenstyle")}</option>
					                </select>
					            </td>
					        </tr>
						<tr>
				            <th>${message("admin.devmode.alarmstate")}</th>
				            <td colspan="2">
				                <select name="alarmState">
				                    <option value="OPEN" [#if user.backendModeUserExtend.alarmState=="OPEN"]
				                            selected="selected"[/#if]>${message("admin.devmode.alarmopen")}</option>
				                    <option value="CLOSE" [#if user.backendModeUserExtend.alarmState=="CLOSE"]
				                            selected="selected"[/#if]>${message("admin.devmode.alarmclose")}</option>
				                </select>
				            </td>
				        </tr>
						<tr>
				            <th>
				                <a href="javascript:;" class="selectAll" title="">${message("admin.backendmode.sellectall")}</a>
				            </th>
				            <td colspan="2">
									<span class="fieldSet">
										<span class="cusImg"><img src="" alt=""/></span>
				                    [#list customizeFunList as allcustfun]
				                        <a class="cusFuns" onMouseOver="showTipPic('${base}/resources/images/${allcustfun.picPath}');" onMouseOut="hideTipPic()"> 
					                        <label>
					                            <input type="checkbox" name="customizeFunsId" value="${allcustfun.id}"
					                                [#list user.backendModeUserExtend.userCustomizeFuns as custfun]
					                                    [#if custfun.id == allcustfun.id]
					                                   checked="checked"
					                                        [#break]
					                                    [/#if]
					                                [/#list]
					                                    />
					                       		${message("admin.backendmode."+allcustfun.funcationName)}<img src="${base}/resources/images/${allcustfun.iconPath}"/>
					                        </label>
				                        </a>
				                    [/#list]
				                    </span>
				            </td>
			        	</tr>
			        	<tr>
							<th>${message("admin.backendmode.custcopyright")}</th>
            				<td colspan="2">
            					<input type="text" id="custCopyRight" name="custCopyRight" size="80" value="${user.backendModeUserExtend.custCopyRight}"></input>
            				</td>
						</tr>
			        	<tr>
			        		<th></th>
			        		<td colspan="2">
			        			<input id="btnPreview" type="button" class="button" value='${message("admin.backendmode.genpreview")}'/>
			        			 <input type="submit" id="backendModeSubmit" class="button" value="${message("guide.operate.confirm")}">
			        		</td>
			        	</tr>
					</table>
				[/#if]
			</form>


[#if user.modeType == "DEVMODE"]
<script>
$(function(){
	$devmodeSubmit = $("#devmodeSubmit");
	$devmodeSubmit.click(function(){
		$.ajax({
				url: "${base}/console/info/update/devMode.jhtml",
				type: "POST",
				data: {"alarmState":$("input[name='alarmState']:checked").val()},
				dataType: "json",
				cache: false,
				success: function(message) {
					 if(message.type == "success"){
							$.message("success", "${message("user.info.upload.success")}");
					 }else{
					 	$.message("warn", "${message("user.info.upload.failure")}");
					 }
				}
			});
	});
})
</script>
[/#if]
[#if user.modeType == "BACKENDMODE"]
<script>
//后台
	function showTipPic(picUrl){
		$(".fieldSet").css({"z-index":"3","background":"none"});
		$(".cusImg img").attr("src",picUrl);
		$(".cusImg").css({"position":"absolute","display":"block","bottom":"1px"});
	};
	function hideTipPic(){
		$(".cusImg img").attr("src","");
		$(".cusImg").css("display","none");
	};
 $(function(){	
        var $backendmodeForm = $("#backendmodeForm");
        var $backendModeSubmit=$("#backendModeSubmit");
        var $selectAll = $("#backendmodeForm .selectAll");
        var $custCopyRight=$("#custCopyRight");
    [@flash_message /]
        $selectAll.click(function () {
            var $this = $(this);
            var $thisCheckbox = $this.closest("tr").find(":checkbox");
            if ($thisCheckbox.filter(":checked").size() > 0) {
                $thisCheckbox.prop("checked", false);
            } else {
                $thisCheckbox.prop("checked", true);
            }
            return false;
        });
      
		
        $backendmodeForm.validate({
            rules: {
                siteName: {
                    required: true,
                    remote: {
                        url: "${base}/console/backendmode/check_sitename.jhtml",
                        cache: false
                    }
                },
                siteDomain: {
                    remote: {
                        url: "${base}/console/backendmode/check_sitedomain.jhtml",
                        cache: false
                    }
                }
            },
            messages: {
				siteName: {
					remote: "${message("admin.validate.siteName")}"
				},
				siteDomain: {
					remote: "${message("admin.validate.siteDomain")}"
				}
			},
			submitHandler: function(form) {
				$backendModeSubmit.prop("disabled", true);
				backendmodeUpdate();
			}

        });
        	
        function backendmodeUpdate(){
        		$.ajax({
					 url: "${base}/console/info/update/backendMode.jhtml",
					 type: "POST",
					 data: $backendmodeForm.serialize(),
					 dataType: "json",
					 cache: false,
					 success: function(message) {
						 if(message.type == "success"){
						 	$.message("success", "${message("user.info.upload.success")}");
						 		
						 }else{
						 	$.message("warn", "${message("user.info.upload.failure")}");
						 }
						 	$backendModeSubmit.prop("disabled", false);
					 }
				 });
        
        
        }
    });
    //创建上传控件
    $(function () {
        var $logoBtnAgent = $("#logoBtnAgent");
        $logoBtnAgent.click(function () {
            $('#logoBtn').click();
            $('#logoBtn').fileupload({
                dataType: 'json',
                url: "${base}/console/backendmode/logo/file.jhtml",
                add: function (e, data) {
                    $.each(data.files, function (index, file) {
                        if (file.size >= 3109996) {
                            alert(file.name + '太大, 最大不能超过3M!');
                            return false;
                        }
                        $('#imageLogoInpt').attr("src", URL.createObjectURL(file));
                    });
                    data.context = $('#uploadLogoBtn').click(function () {
                        data.formData = {};
                        data.submit();
                    });
                },
                done: function (e, result) {
                    if (result.result.type == "success") {
                        $("#logoInpt").val(result.result.content);
                        alert('${message("admin.upload.success")}');
                    } else {
                        alert('${message("admin.upload.faild")}');
                    }
                }
            });

        });
        var $faviconBtnAgent = $("#faviconBtnAgent");
        $faviconBtnAgent.click(function () {
            $('#faviconBtn').click();
            $('#faviconBtn').fileupload({
                dataType: 'json',
                url: "${base}/console/backendmode/favicon/file.jhtml",
                add: function (e, data) {
                    $.each(data.files, function (index, file) {
                        if (file.size >= 3109996) {
                            alert(file.name + '太大, 最大不能超过3M!');
                            return false;
                        }
                        $('#imageFaviconInpt').attr("src", URL.createObjectURL(file));
                    });
                    data.context = $('#uploadFaviconBtn').click(function () {
                        data.formData = {};
                        data.submit();
                    });
                },
                done: function (e, result) {
                    console.log(result.result);
                    if (result.result.type == "success") {
                        $("#faviconInpt").val(result.result.content);
                        alert('${message("admin.upload.success")}');
                    } else {
                        alert('${message("admin.upload.faild")}');
                    }
                }
            });

        });
        var $iconBtnAgent = $("#iconBtnAgent");
        $iconBtnAgent.click(function () {
            $('#iconBtn').click();
            $('#iconBtn').fileupload({
                dataType: 'json',
                url: "${base}/console/backendmode/icon/file.jhtml",
                add: function (e, data) {
                    $.each(data.files, function (index, file) {
                        if (file.size >= 3109996) {
                            alert(file.name + '太大, 最大不能超过3M!');
                            return false;
                        }
                        $('#imageIconInpt').attr("src", URL.createObjectURL(file));
                    });
                    data.context = $('#uploadIconBtn').click(function () {
                        data.formData = {};
                        data.submit();
                    });
                },
                done: function (e, result) {
                    console.log(result.result);
                    if (result.result.type == "success") {
                        $("#iconInpt").val(result.result.content);
                        alert('${message("admin.upload.success")}');
                    } else {
                        alert('${message("admin.upload.faild")}');
                    }
                }
            });

        });
    
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
