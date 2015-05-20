<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.devmode.api")}</title>
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
</head>
<body>
	<div class="path">
		${message("common.current.position")}：${message("admin.main.devcenter")}&raquo; ${message("admin.devmode.api")}
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
		<tr >
			<th >
				${message("admin.devmode.apilist")}</a>
			</th>
			<th>
				${message("admin.devmode.apiurl")}</a>
			</th>
			<th>${message("admin.devmode.apistate")}</a></th>
		</tr>
		[#list apiList as api]
				<tr>
					<td>
						<a>${api.apiName}</a>
					</td>
					<td>
						<a>${api.api}</a>
					</td>
					<td>
						<select>
							<option [#if api.apiState=1] selected="selected"[/#if]>${message("admin.mode.apienable")}</option>
							<option [#if api.apiState=2] selected="selected"[/#if]>${message("admin.mode.apidisable")}</option>
						</select>
						
					</td>
				</tr>
			[/#list]
		</table>
		
	</form>
</body>
</html>