<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.main.statisticsNav")}</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/css/jquery.jqplot.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/js/input.js"></script>
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
  .fieldSet label{
        	float:left;
        }
   .fieldSet{
        position:relative; 
		z-index:2;
    }
    

	.cusImg {
		margin:0px 0px 30px -150px;
		border:1px solid;
		border-style:ridge;
		display:none;
	}
	
	.cusImg img{
		width:930px;
		height:470px;
	}
   
</style>
</head>
<body>
	<div class="path">
		${message("common.current.position")}：${message("config.show.main")}&raquo; ${message("config.show.info")}
	</div>
		<form id="inputForm">
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
							[#if user.devModeUserExtend.alarmState == "OPEN"]开启[/#if]
							[#if user.devModeUserExtend.alarmState == "CLOSE"]关闭[/#if]
						</td>
					</tr>
				</table>
			[/#if]
			[#if user.modeType == "BACKENDMODE"]
				<table id="backendShow" class="input tabContent">
					 <tr>
			            <th>${message("admin.backendmode.sitename")}</th>
			            <td>${user.backendModeUserExtend.siteName}</td>
			        </tr>
			        <tr>
			        	<th>${message("admin.backendmode.logo")}</th>
          				  <td class="imageTd">
          				  		<img alt="" onerror="this.style.display='none'" src="[#if user.backendModeUserExtend.logo??]${"${base}/upload/"+user.backendModeUserExtend.logo}[/#if]">
          				  </td>
					</tr>

					 <tr>
			        	<th>${message("admin.backendmode.icon")}</th>
          				  <td class="imageTd">
          				  		<img alt="" onerror="this.style.display='none'" src="[#if user.backendModeUserExtend.icon??]${"${base}/upload/"+user.backendModeUserExtend.icon}[/#if]">
          				  </td>
					</tr>
                    <tr>
                        <th>${message("admin.backendmode.Favicon")}</th>
                        <td class="imageTd">
                            <img alt="" onerror="this.style.display='none'" style="width:16px;height:16px;" src="[#if user.backendModeUserExtend.favIcon??]${"${base}/upload/"+user.backendModeUserExtend.favIcon}[/#if]">
                        </td>
                    </tr>
					<tr>
						<th>${message("admin.backendmode.sitedomain")}</th>
			            <td >
			               <a href="http://${user.backendModeUserExtend.siteDomain} " target="1">${user.backendModeUserExtend.siteDomain}</a>
			            </td>
					</tr>
					<tr>
							<th>${message("admin.backendmode.defaultsitedomain")}</th>
				            <td>
				               <a href="http://${user.backendModeUserExtend.defaultSiteDomain} " target="1">${user.backendModeUserExtend.defaultSiteDomain}</a>
				            </td>
					</tr>
					<tr>
					            <th>${message("admin.backendmode.editfontstyle")}</th>
					            <td colspan="2">
					                   [#if user.backendModeUserExtend.frontStyle=="css"]${message("admin.mode.defaultstyle")}[/#if]
					                   [#if user.backendModeUserExtend.frontStyle=="goldenCss"]${message("admin.mode.goldenstyle")}[/#if]
					                   [#if user.backendModeUserExtend.frontStyle=="greenCss"]${message("admin.mode.greenstyle")}[/#if]
					             </td>
					</tr>
					<tr>
				            <th>${message("admin.devmode.alarmstate")}</th>
				            <td colspan="2">
				                   [#if user.backendModeUserExtend.alarmState=="OPEN"]${message("admin.devmode.alarmopen")}[/#if]
				                   [#if user.backendModeUserExtend.alarmState=="CLOSE"]${message("admin.devmode.alarmclose")}[/#if]
				            </td>
				        </tr>
					<tr>
			            <th>
			                ${message("admin.backendmode.customize")}
			            </th>
			            <td colspan="2">
								<span class="fieldSet">
									<span class="cusImg"><img src="" alt=""/></span>
			                    [#list customizeFunList as allcustfun]
			                    	<a class="cusFuns" onMouseOver="showTipPic('${base}/resources/images/${allcustfun.picPath}');" onMouseOut="hideTipPic()">
				                        <label>
				                            <input type="checkbox" name="customizeFunsId" disabled="disabled" value="${allcustfun.id}"
				                                [#list user.backendModeUserExtend.userCustomizeFuns as custfun]
				                                    [#if custfun.id == allcustfun.id]
				                                   checked="checked"
				                                        [#break]
				                                    [/#if]
				                                [/#list]
				                                    />
				                         		${message("admin.backendmode."+allcustfun.funcationName)}<img src="${base}/resources/images/${allcustfun.iconPath}"/>
				                        </label>
			                        </span>
			                    [/#list]
			                    </span>
			            </td>
		        	</tr>
		        	<tr>
						<th>${message("admin.backendmode.custcopyright")}</th>
            				<td colspan="2">
               				${user.backendModeUserExtend.custCopyRight}
           			 	</td>
					</tr>
				</table>
			[/#if]
		</form>
	</body>
	
</html>

<script>
function showTipPic(picUrl){
	$(".fieldSet").css({"z-index":"3","background":"none"});
	$(".cusImg img").attr("src",picUrl);
	$(".cusImg").css({"position":"absolute","display":"block","bottom":"1px"});
};
function hideTipPic(){
	$(".cusImg img").attr("src","");
	$(".cusImg").css("display","none");
};
</script>
