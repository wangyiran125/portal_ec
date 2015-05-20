<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.under.user")}</title>
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
		${message("common.current.position")}：${message("admin.main.admin")}&raquo; ${message("admin.under.user")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.jhtml" method="get">
		<div class="bar">
				<a href="javascript:;" id="refreshButtonFirst" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
				<div class="menuWrap">
					<div class="search">
						<input type="text" id="searchValue" name="searchValue" value="${page.searchValue}" maxlength="200" />
						<button type="submit">&nbsp;</button>
					</div>
					<span>
						<select id="searchPropertyOption" class="selectField">
							<option value="" selected="selected">${message("admin.common.choose")}</option>
			                <option [#if page.searchProperty == "fName"] selected="selected"[/#if] value="fName">${message("Admin.username")}</option>
			                <option [#if page.searchProperty == "fEmail"] selected="selected"[/#if] value="fEmail">${message("Admin.email")}</option>
			                <option [#if page.searchProperty == "fReal"] selected="selected"[/#if] value="fReal">${message("Admin.name")}</option>
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
		</div>
		<table id="listTable" class="list">
			<tr>
				<th>
					<a href="javascript:;" class="sort" name="fName">${message("Admin.username")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="fEmail">${message("Admin.email")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="fReal">${message("Admin.name")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="fNick">${message("Admin.nickName")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="fTime">${message("admin.common.createDate")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="fAddress">${message("Individual.address")}</a>
				</th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.content as user]
				<tr>
					<td>
						${user.fName}
					</td>
					<td>
						${user.fEmail}
					</td>
					<td>
						${user.fReal}
					</td>
					<td>
						${user.fNick}
					</td>
					<td>
						[#if user.fTime??]
						<span title="${user.fTime?string("yyyy-MM-dd HH:mm:ss")}">${user.fTime}</span>
						[#else]
						   &nbsp;&nbsp;-
						[/#if]
					</td>
					<td>
						${user.fAddress}
					</td>
					<td>
						<a href="bindDevice.jhtml?id=${user.fId}&userName=${user.fName}">[${message("admin.handle.device")}]</a>
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