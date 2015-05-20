[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>${message("admin.main.title")}</title>
<link rel="shortcut icon" type="image/x-icon" href="${base}/resources/images/favicon.ico" media="screen" /> 
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.cookie.js"></script>
<style type="text/css">
*{
	font: 12px tahoma, Arial, Verdana, sans-serif;
}
html, body {
	width: 100%;
	height: 100%;
}
</style>
<script type="text/javascript">
$().ready(function() {

	var $nav = $("#nav a");
	var $menu = $("#menu dl");
	var $menuItem = $("#menu a");
	var $accoutSetting = $("#accoutSetting");
	var $iframe=$("#iframe");
	
	$nav.click(function() {
		var $this = $(this);
		$nav.removeClass("current");
		$this.addClass("current");
		var $currentMenu = $($this.attr("href"));
		$menu.hide();
		$currentMenu.show();
		var $currentItem = $currentMenu.find("dd:first").find("a");
		$menuItem.removeClass("current");
		$currentItem.addClass("current");
		window.iframe.location.href= $currentItem.attr("href");
		return false;
	});
	
	$accoutSetting.click(function(){
		var $currentMenu = $("#account");
		var $currentNav = $("[href='#admin']");
 		$nav.removeClass("current");
 		$currentNav.addClass("current");
 		$menu.hide();
 		$currentMenu.show();
 		var $currentItem = $currentMenu.find("dd:first").find("a");
 		$currentItem.addClass("current");
 		window.iframe.location.href= $currentItem.attr("href");
 	});
 	
	$menuItem.click(function() {
		var $this = $(this);
		$menuItem.removeClass("current");
		$this.addClass("current");
	});
	if (self == top) {
      String.prototype.endWith=function(str){    
		 	 var reg=new RegExp(str+"$");    
		  	return reg.test(this);       
			} 	
		var url = $.cookie('pre_url');
		var cust_pre_url = $.cookie('cust_pre_url');
		var pre_str=window.iframe.location.href;
		if(cust_pre_url){
			$.removeCookie('cust_pre_url', { path: '/' });
			pre_str=cust_pre_url;
		}else{
			if (url && !url.endWith("main.jhtml")&& url !="about:blank" ){
				$.removeCookie('pre_url',{ path: '/' });
				pre_str=url;
			}
	    }
	    if($.cookie('user_name')=='[@shiro.principal /]'){
	   if($iframe != null && pre_str!= "about:blank"){
		var strs= new Array();  
		strs=pre_str.split("/"); 
		var menuType=strs[strs.length-2];
		if(menuType != "role"){
			var $menuType=$("#"+menuType);
			var id ="#"+$menuType.attr("id") ;
			var nav = $("[href='"+id+"']");
				$nav.removeClass("current");
				nav.addClass("current");
				var $currentMenu = $(nav.attr("href"));
				$menu.hide();
				$currentMenu.show();
				var muenItem="../"+strs[strs.length-2]+"/"+strs[strs.length-1];
				$("[href='"+muenItem+"']").click();
				document.getElementById("iframe").contentWindow.window.location.href = pre_str;
		}
		else{
			var nav = $("[href='#admin']");
				$nav.removeClass("current");
				nav.addClass("current");
				var $currentMenu = $(nav.attr("href"));
				$menu.hide();
				$currentMenu.show();
				var muenItem="../"+strs[strs.length-2]+"/"+strs[strs.length-1];
				$("[href='"+muenItem+"']").click();
				document.getElementById("iframe").contentWindow.window.location.href = pre_str;
		}
	}
	   }
    }
    $.cookie('user_name','[@shiro.principal /]',{expires:1,path:'/'})
});
function setcookie()
{
	var iframe=document.getElementById("iframe");
	if(iframe)
	{
		var pre_url = iframe.contentWindow.window.location.href;
		$.cookie('pre_url',pre_url,{expires:1,path:'/'});
		
	}
}

window.onbeforeunload = setcookie;

</script>

</head>
<body>
	<script type="text/javascript">
		if (self != top) {
			top.location = self.location;
		};
	</script>
	<table class="main" border="0" rowspan="0" colspan="0">
		<tr>
			<th class="logo">
				<a href="main.jhtml">
					<span></span>
				</a>
			</th>
			<th >
				<div class="link">
					<strong>[@shiro.principal /]</strong>
					${message("admin.main.hello")}!
					<a href="#account" id="accoutSetting">[${message("admin.account.setting")}]</a>
					<a href="../common/logout.jhtml" target="_top">[${message("admin.main.logout")}]</a>
				</div>
			</th>
		</tr>
		<tr>
			<td class="preNav"></td>
			<td class="navTd">
				<div id="nav" class="nav">
					<ul>
						[#list ["user:userinfo"] as permission]
							[@shiro.hasPermission name = permission]
								<li>
									<a href="#config">${message("config.show.main")}</a>
								</li>
								<li>
									<a href="#info">
									[#if user.userType == "INDIVIDUAL"]${message("info.show.individual")}
									[#else]
										${message("info.show.enterprise")}
									[/#if]
									</a>
								</li>									
								[#break /]
							[/@shiro.hasPermission]
						[/#list]
						[#list ["admin:user", "admin:role"] as permission]
							[@shiro.hasPermission name = permission]
								<li>
									<a href="#admin" class="current">${message("admin.main.systemNav")}</a>
								</li>
								[#break /]
							[/@shiro.hasPermission]
						[/#list]
						[#list ["devmode:base", "devmode:api", "devmode:devdoc", "devmode:test"] as permission]
								[@shiro.hasPermission name = permission]
									<li>
										<a href="#devmode" class="current">${message("admin.main.devcenter")}</a>
									</li>
									[#break /]
								[/@shiro.hasPermission]
						[/#list]
						[#list ["backendmode:infomanage"] as permission]
								[@shiro.hasPermission name = permission]
									<li>
										<a href="#backendmode" class="current">${message("admin.backendmode.managecenter")}</a>
									</li>
									[#break /]
								[/@shiro.hasPermission]
						[/#list]
						[#list ["device:manage", "device:batchadd","device:addConductor"] as permission]
							[@shiro.hasPermission name = permission]
								<li>
									<a href="#device">${message("User.device")}</a>
								</li>
								[#break /]
							[/@shiro.hasPermission]
						[/#list]
						[#list ["statistics:general","statistics:user","statistics:device","statistics:system"] as permission]
							[@shiro.hasPermission name = permission]
								<li>
									<a href="#statistics">${message("admin.main.statisticsNav")}</a>
								</li>
								[#break /]
							[/@shiro.hasPermission]
						[/#list]
						[#list ["user:usermanage"] as permission]
							[@shiro.hasPermission name = permission]
								<li>
									<a href="#usermanage">${message("admin.main.admin")}</a>
								</li>
								[#break /]
							[/@shiro.hasPermission]
						[/#list]
						[#list ["user:fishing"] as permission]
							[@shiro.hasPermission name = permission]
								<li>
									<a href="http://fish.unilbs.com/UserLogin.aspx" target="1">${message("admin.main.fishing")}</a>
								</li>
								[#break /]
							[/@shiro.hasPermission]
						[/#list]
						[#list ["indoor:beacon:manage","indoor:fence:manage","indoor:coordinate:manage"] as permission]
							[@shiro.hasPermission name = permission]
								<li>
									<a href="#indoor">${message("admin.indoor.manage")}</a>
								</li>
								[#break /]
							[/@shiro.hasPermission]
						[/#list]
					</ul>
				</div>
			</td>
		</tr>
		<tr>
			<td class="menuTd">
				<div id="menu" class="menu">
					<dl id="admin" [#if user.isSystem == 1]class="default" [/#if]>
						<dt><i></i>${message("admin.login.welcome")}</dt>
						[@shiro.hasPermission name="admin:user"]
							<dd>
								<a href="../admin/list.jhtml" target="iframe"><i></i>${message("admin.main.admin")}</a>
							</dd>
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:role"]
							<dd>
								<a href="../role/list.jhtml" target="iframe"><i></i>${message("admin.main.role")}</a>
							</dd>
						[/@shiro.hasPermission]
						<dd>
								<a href="../admin/adminEdit.jhtml" target="iframe"><i></i>${message("admin.main.changepwd")}</a>
						</dd>
					</dl>
					<dl id="devmode"  [#if user.modeType =="DEVMODE"&& user.confIsEnable ==1]class="default" [/#if]>
						<dt><i></i>${message("admin.login.welcome")}</dt>
							[@shiro.hasPermission name="devmode:api"]
								<dd>
									<a href="../devmode/apilist.jhtml" target="iframe" class="current"><i></i>${message("admin.devmode.api")}</a>
								</dd>
							[/@shiro.hasPermission]
							[@shiro.hasPermission name="devmode:devdoc"]
								<dd>
									<a href="../devmode/devdoclist.jhtml" target="iframe"><i></i>${message("admin.devmode.devdoc")}</a>
								</dd>
							[/@shiro.hasPermission]
							[@shiro.hasPermission name="devmode:test"]
								<dd>
									<a href="../devmode/testlist.jhtml" target="iframe"><i></i>${message("admin.devmode.test")}</a>
								</dd>
							[/@shiro.hasPermission]
					</dl>
					<dl id="backendmode" >
						<dt><i></i>${message("admin.login.welcome")}</dt>
						[@shiro.hasPermission name="backendmode:infomanage"]
							<dd>
								<a href="../backendmode/infoAdd.jhtml" target="iframe" class="current"><i></i>${message("admin.backendmode.infomanage")}</a>
							</dd>
						[/@shiro.hasPermission]
					</dl>
						<dl id="device" [#if user.modeType =="BACKENDMODE"&& user.confIsEnable ==1]class="default" [/#if]>
						<dt><i></i>${message("admin.login.welcome")}</dt>
						[@shiro.hasPermission name="device:manage"]
							<dd>
								<a href="../device/list.jhtml" target="iframe"><i></i>${message("User.device.manage.list")}</a>
							</dd>
						[/@shiro.hasPermission]
						[@shiro.hasPermission name="device:batchadd"]
							<dd>
								<a href="../device/batchAdd.jhtml" target="iframe"><i></i>${message("User.device.batch.upload")}</a>
							</dd>
						[/@shiro.hasPermission]
						<dd>
							<a href="../device/listConductor.jhtml" target="iframe"><i></i>指挥机列表</a>
						</dd>
					</dl>
					 <dl id="account">
							<dt><i></i>${message("admin.login.welcome")}</dt>
						[@shiro.hasPermission name="setting:account"]
								<dd>
									<a href="../account/accountInfo.jhtml" target="iframe"><i></i>${message("admin.account.settingGroup")}</a>
								</dd>
						[/@shiro.hasPermission]
					 </dl>
					 <dl id="usermanage">
						<dt><i></i>${message("admin.login.welcome")}</dt>
						[@shiro.hasPermission name="user:usermanage"]
								<dd>
									<a href="../usermanage/list.jhtml" target="iframe"><i></i>${message("admin.under.user")}</a>
								</dd>
						[/@shiro.hasPermission]
					 </dl>
					  <dl id="statistics">
							<dt><i></i>${message("admin.login.welcome")}</dt>
							[@shiro.hasPermission name="statistics:general"]
								<dd>
									<a href="../statistics/view.jhtml" target="iframe"><i></i>${message("admin.main.statisticsView")}</a>
								</dd>
							[/@shiro.hasPermission]
							[@shiro.hasPermission name="statistics:user"]
								<dd>
									<a href="../statistics/user.jhtml" target="iframe"><i></i>${message("admin.main.statisticsUser")}</a>
								</dd>
							[/@shiro.hasPermission]
							[@shiro.hasPermission name="statistics:device"]
								<dd>
									<a href="../statistics/equipment.jhtml" target="iframe"><i></i>${message("admin.main.statisticsEquipment")}</a>
								</dd>
							[/@shiro.hasPermission]
							[@shiro.hasPermission name="statistics:system"]
								<dd>
									<a href="../statistics/system.jhtml" target="iframe"><i></i>${message("admin.main.statisticsSystem")}</a>
								</dd>
							[/@shiro.hasPermission]
					 </dl>
					  <dl id="info">
							<dt><i></i>${message("admin.login.welcome")}</dt>
							[@shiro.hasPermission name="user:userinfo"]
								<dd>
									<a href="../info/show.jhtml" target="iframe"><i></i>
									[#if user.userType == "INDIVIDUAL"]
									${message("info.show.individual")}
									[#else]
										${message("info.show.enterprise")}
									[/#if]
									
									</a>
								</dd>
							[/@shiro.hasPermission]
							[@shiro.hasPermission name="user:userinfo"]
								<dd>
									<a href="../info/edit.jhtml" target="iframe"><i></i>
									[#if user.userType == "INDIVIDUAL"]
										${message("info.edit.individual")}
									[#else]
										${message("info.edit.enterprise")}
									[/#if]
									</a>
								</dd>
							[/@shiro.hasPermission]
					 </dl>
					 <dl id="config">
							<dt><i></i>${message("admin.login.welcome")}</dt>
							[@shiro.hasPermission name="user:userinfo"]
								<dd>
									<a href="../config/show.jhtml" target="iframe"><i></i> ${message("config.show.info")}</a>
								</dd>
							[/@shiro.hasPermission]
							[@shiro.hasPermission name="user:userinfo"]
								<dd>
									<a href="../config/edit.jhtml" target="iframe"><i></i>${message("config.edit.info")}</a>
								</dd>
							[/@shiro.hasPermission]
					 </dl>
					 <dl id="indoor">
							<dt><i></i>${message("admin.login.welcome")}</dt>
							[@shiro.hasPermission name="indoor:beacon:manage"]
								<dd>
									<a href="../indoor/beacon_list.jhtml" target="iframe"><i></i>${message("indoor.beacon.manage")}</a>
								</dd>
							[/@shiro.hasPermission]
							[@shiro.hasPermission name="indoor:fence:manage"]
								<dd>
									<a href="../indoor/fence_list.jhtml" target="iframe"><i></i>${message("indoor.fence.manage")}</a>
								</dd>
							[/@shiro.hasPermission]
							[@shiro.hasPermission name="indoor:coordinate:manage"]
								<dd>
									<a href="../indoor/coordinate_list.jhtml" target="iframe"><i></i>${message("indoor.coordinate.manage")}</a>
								</dd>
							[/@shiro.hasPermission]
							
					 </dl>
				 </div>
			</td>
			<td class="iframeTd">
					<script>
						function adminBack(arg){
							var $navTemp  = $("#nav a");
							var $focusTemp = $("[href='#"+arg+"']");
							var $menuTemp = $("#menu dl");
							var $currentTemp = $("#"+arg);
							$navTemp.removeClass("current");
							$focusTemp.addClass("current");
							$menuTemp.hide();
							$currentTemp.show();
							var $currentTempMenu = $currentTemp.find("dd:first").find("a");
 							$currentTempMenu.addClass("current");
 							window.iframe.location.href= $currentTempMenu.attr("href");
						}
					</script>
					[#if url??]
						<iframe id="iframe" name="iframe" src="${base}${url}" frameborder="0" ></iframe>
					[#else]
						[#if user.modeType =="DEVMODE"&& user.confIsEnable ==1]
							<iframe id="iframe" name="iframe" src="../devmode/apilist.jhtml" frameborder="0" ></iframe>
						[#elseif user.modeType =="BACKENDMODE"&& user.confIsEnable ==1]
							<iframe id="iframe" name="iframe" src="../device/list.jhtml" frameborder="0" ></iframe>
						[#elseif user.isSystem == 1]
							<iframe id="iframe" name="iframe" src="../admin/list.jhtml" frameborder="0" ></iframe>
						[/#if]
					[/#if]
				</td>
		</tr>
		<tr class="footer">
			<td colspan="2">
				<div class="copy-warp">
					  <div class="copy">${message("common.copyright")}<span>Copyright © 2014 ChinaLbs International BV.All rights reserved.</span></div>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>
