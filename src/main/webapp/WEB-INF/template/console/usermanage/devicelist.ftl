<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.binddevice.list")}</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {
		[@flash_message /]
});
</script>
<style type="text/css">
	html{
		padding-left: 20px;
		padding-right: 15px;
	}
.menuUser{
	text-align: center;
	color:#B8860B; 
	font-size:17px;
	line-height:40px;
}
</style>
</head>
<body>
	<div class="path">
		${message("common.current.position")}：${message("admin.user.personal")}&raquo; ${message("admin.binddevice.list")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="bindDevice.jhtml" method="get">
	    <input type="hidden" name="userName" value="${userName}">
	    <input type="hidden" name="id" value="${selectUserId}">
		<div class="bar">
				<a href="list.jhtml" id="backButton" class="iconButton">
					<span class="button"></span>${message("admin.common.back")}
				</a>
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
				<div class="menuWrap">
					<a href="javascript:;" id="pageSizeSelect" class="button">
						${message("admin.page.pageSize")}<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="pageSizeOption">
							<li>
								<a href="javascript:;"[#if page.pageSize == 10] class="current"[/#if] val="10">10</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 20] class="current"[/#if] val="20">20</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 50] class="current"[/#if] val="50">50</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 100] class="current"[/#if] val="100">100</a>
							</li>
						</ul>
					</div>
			</div>
		<div class="menuUser">
			${message("Admin.username")}: ${userName}
		</div>
	</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					
				</th>
				<th>
					<a href="javascript:;" class="sort" name="fSn">${message("User.device.IMEI")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="fIsvirtual">${message("User.device.isvirtual")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="fProtocol">${message("User.device.protocol")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="fPhone">${message("User.device.sim")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="fType">${message("User.device.type")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="fTime">${message("User.device.time")}</a>
				</th>
			</tr>
			[#list page.content as bindDevice]
				<tr>
					<td>						
					</td>
					<td>
						${bindDevice.fDeviceSn}
					</td>
					<td>
						[#if bindDevice.fIsvirtual == "YES"]${message("device.fIsvirtual.yes")}[/#if]
						[#if bindDevice.fIsvirtual == "NO"]${message("device.fIsvirtual.no")}[/#if]
					</td>
					<td>
						[#if bindDevice.fProtocol == 1]
						${message("User.device.fProtocol.M2616")}
						[#elseif bindDevice.fProtocol == 2]
						${message("User.device.fProtocol.OBD")}
						[#elseif bindDevice.fProtocol == 3]
						${message("User.device.fProtocol.MT90")}
						[#else]
						${message("User.device.fProtocol.T808")}
						[/#if]
				
					</td>
					<td>
						${bindDevice.fPhone}
					</td>
					<td>
						[#if  bindDevice.fType== "1"]${message("User.device.car")}[/#if]
						[#if  bindDevice.fType== "2"]${message("User.device.pet")}[/#if]
						[#if  bindDevice.fType== "3"]${message("User.device.person")}[/#if]
					</td>
					<td>
						[#if bindDevice.fTime??]
						<span title="${bindDevice.fTime?string("yyyy-MM-dd HH:mm:ss")}">${bindDevice.fTime}</span>
						[#else]
						   &nbsp;&nbsp;-
						[/#if]
					</td>
					
				</tr>
			[/#list]
		</table>
		[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
			[#include "/console/include/pagination.ftl"]
		[/@pagination]
	</form>
</body>
</html>