<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.admin.list")}</title>
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
$().ready(function() {

		[@flash_message /]

});
</script>
</head>
<body>
	<div class="path">
		${message("common.current.position")}：${message("admin.main.admin")}&raquo; ${message("admin.admin.list")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.jhtml" method="get">
		<div class="bar">
			<a href="add.jhtml" id="addButton" class="iconButton">
				<span class="addIcon">&nbsp;</span>${message("admin.common.add")}
			</a>
			<a href="javascript:;" id="refreshButton" class="iconButton">
				<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
			</a>
			<div class="menuWrap">
				<div class="search">
					<input type="text" id="searchValue" name="searchValue" value="${page.searchValue}" maxlength="200" />
					<button type="submit">&nbsp;</button>
				</div>
				<span class="selectSpan">
					<select id="searchPropertyOption" class="selectField">
						<option value="" selected="selected">${message("admin.common.choose")}</option>
		                <option [#if page.searchProperty == "userName"] selected="selected"[/#if] value="userName">${message("Admin.username")}</option>
		                <option [#if page.searchProperty == "email"] selected="selected"[/#if] value="email">${message("Admin.email")}</option>
		                <option [#if page.searchProperty == "name"] selected="selected"[/#if] value="name">${message("Admin.name")}</option>
		             </select>
	            </span>
			</div>
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
			<span>${message("admin.main.admin")}</span>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th>
					<a href="javascript:;" class="sort" name="userName">${message("Admin.username")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="email">${message("Admin.email")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="name">${message("Admin.name")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="loginDate">${message("Admin.loginDate")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="loginIp">${message("Admin.loginIp")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="createDate">${message("admin.common.createDate")}</a>
				</th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.content as admin]
				<tr>
					<td>
						${admin.userName}
					</td>
					<td>
						${admin.email}
					</td>
					<td>
						${admin.name}
					</td>
					<td>
						[#if admin.loginDate??]
							<span title="${admin.loginDate?string("yyyy-MM-dd HH:mm:ss")}">${admin.loginDate}</span>
						[#else]
							-
						[/#if]
					</td>
					<td>
						${(admin.loginIp)!"-"}
					</td>
					<td>
						<span title="${admin.createDate?string("yyyy-MM-dd HH:mm:ss")}">${admin.createDate}</span>
					</td>
					<td>
						<a href="edit.jhtml?id=${admin.id}">[${message("admin.common.edit")}]</a>
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