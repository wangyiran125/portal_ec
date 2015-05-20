[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.main.title")}</title>
<link rel="shortcut icon" type="image/x-icon" href="${base}/resources/images/favicon.ico" media="screen" /> 
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<style type="text/css">
*{
	font: 12px tahoma, Arial, Verdana, sans-serif;
}
html, body {
	width: 100%;
	height: 100%;
	overflow: hidden;
}
</style>
<script type="text/javascript">
$().ready(function() {

	var $nav = $("#nav a");
	var $menu = $("#menu dl");
	var $menuItem = $("#menu a");
	
	$nav.click(function() {
		var $this = $(this);
		$nav.removeClass("current");
		$this.addClass("current");
		var $currentMenu = $($this.attr("href"));
		$menu.hide();
		$currentMenu.show();
		return false;
	});
	
	$menuItem.click(function() {
		var $this = $(this);
		$menuItem.removeClass("current");
		$this.addClass("current");
	});

});
</script>
</head>
<body>
	<script type="text/javascript">
		if (self != top) {
			top.location = self.location;
		};
	</script>
	<table class="main">
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
					<a href="../common/logout.jhtml" target="_top">[${message("admin.main.logout")}]</a>
				</div>
			</th>
		</tr>
			<td class="preNav"></td>
			<td class="navTd">
				<div id="nav" class="nav">
				 	 <ul>
	                    <li>
	                        <a href="#devcenter"  class="current">${message("admin.main.devcenter")}</a>
	                    </li>
	                </ul>
	             </div>
	         </td>
		</tr>
		<tr>
			<td class="menuTd">
				<div id="menu" class="menu">
					<dl id="guide" class="default">
						<dt><i></i>${message("admin.login.welcome")}</dt>
		                <dd>
		                    <a href="../devmode/add.jhtml" class="current" target="iframe"><i></i>${message("admin.main.devcenter")}</a>
		                </dd>
					</dl>
			</td>
			<td class="iframeTd">
					<iframe id="iframe" name="iframe" src="../devmode/add.jhtml" frameborder="0" onload="javascript:this.height=this.contentWindow.document.body.scrollHeight+30;"></iframe>
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
