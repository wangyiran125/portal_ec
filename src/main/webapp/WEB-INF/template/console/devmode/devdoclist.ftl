<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.devmode.devdoc")}</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/js/list.js"></script>
<style type="text/css">
	html{
		padding-left: 20px;
		padding-right: 15px;
	}
</style>
<script type="text/javascript">
	function select(mode){
		window.parent.location.href=mode
		};
</script>
</head>
<body>
	<div class="path">
		${message("common.current.position")}：${message("admin.main.devcenter")}&raquo; ${message("admin.devmode.devdoc")}
	</div>
	<div class="bar">
			
			<div class="buttonWrap">
				
				<a href="javascript:;" id="refreshButtonFirst" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
			</div>
		</div>
	<form id="inputForm" action="" method="post">
		<div class="listTopBar">
			<span>${message("admin.main.devcenter")}</span>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th><a href="javascript:;" class="sort">${message("admin.devmode.apimodel")}</a></th>
				<th><a href="javascript:;" class="sort">${message("admin.devmode.apiname")}</a></th>
				<th><a href="javascript:;" class="sort">${message("admin.devmode.apiurl")}</a></th>
				<th><a href="javascript:;" class="sort">${message("admin.devmode.apidesc")}</a></th>
				<th><a href="javascript:;" class="sort">${message("admin.devmode.doc")}</a></th>
			</tr>
			[#list apiDocJsonList as devdoc]
			<tr>
				<td><a>${devdoc.modelName}</a></td>
				<td><a>${devdoc.apiName}</a></td>
				<td><a>${devdoc.api}</a></td>
				<td><a>${devdoc.desc}</a></td>
				<td><a href="viewapidoc.jhtml?id=${devdoc.id}&apiName=${devdoc.apiName}">${message("admin.common.view")}</a></td>
					
			</tr>
			[/#list]
		</table>
	</form>
</body>
</html>