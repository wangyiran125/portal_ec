<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.role.list")}</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/js/listConductor.js"></script>
<style>
html{
	padding-left: 20px;
	padding-right: 15px;
}
</style>
<script type="text/javascript">
$().ready(function() {

	[@flash_message /]
});
</script>
</head>
<body>
	<div class="path">
		${message("common.current.position")}：${message("User.device")}&raquo; ${message("User.device.manage.list")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="listConductor.jhtml" method="get">
		<div class="bar">
			<a href="addConductor.jhtml" id="addButton" class="iconButton">
				<i class="addIcon">&nbsp;</i>${message("admin.common.add")}
			</a>
			<a href="javascript:;" id="deleteDeviceButton" class="iconButton disabled">
				<i class="deleteIcon"></i>${message("admin.common.delete")}
			</a>
			<a href="javascript:;" id="refreshButton" class="iconButton">
				<i class="refreshIcon">&nbsp;</i>${message("admin.common.refresh")}
			</a>
			<div class="menuWrap">
				<a href="javascript:;" id="pageSizeSelect" class="button">
						${message("admin.page.pageSize")}
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
		</div>
		<div class="listTopBar">
			<span>${message("User.device")}</span>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					<input type="checkbox" id="selectAll" />
				</th>
				<th>
					<a href="javascript:;" class="sort" name="sn">设备号</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="createDate">创建时间</a>
				</th>
			</tr>
			[#list page.content as conductor]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${conductor.id}"/>
					</td>
					<td>
						${conductor.sn}
					</td>
					<td>
						${conductor.createDate}
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
