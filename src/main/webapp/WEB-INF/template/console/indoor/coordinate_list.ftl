<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.role.list")}</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/js/list.js"></script>
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
		${message("common.current.position")}：${message("admin.indoor.manage")}&raquo;${message("indoor.coordinate.manage")}<span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="coordinate_list.jhtml" method="get">
		<div class="bar">
			<a href="coordinate_add.jhtml" id="addButton" class="iconButton">
				<i class="addIcon">&nbsp;</i>${message("admin.common.add")}
			</a>
			<a href="javascript:;" id="deleteCoordinateButton" class="iconButton disabled">
				<i class="deleteIcon"></i>${message("admin.common.delete")}
			</a>
			<a href="javascript:;" id="refreshButton" class="iconButton">
				<i class="refreshIcon">&nbsp;</i>${message("admin.common.refresh")}
			</a>
			<div class="menuWrap">
				<div class="search">
					<input type="text" id="searchValue" name="searchValue" value="${page.searchValue}" maxlength="200"/>
					<button type="submit">&nbsp;</button>
				</div>
				<span>
					<select id="searchPropertyOption"  class="selectField">
						<option value="" selected="selected">${message("admin.common.choose")}</option>
		                <option [#if page.searchProperty == "fLocation"] selected="selected"[/#if] value="fLocation">位置</option>
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
			<span>${message("indoor.coordinate.manage")}</span>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					<input type="checkbox" id="selectAll" />
				</th>
				<th>
					<a href="javascript:;" class="sort" name="location">${message("Indoor.coordinate.location")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="name">${message("Indoor.coordinate.name")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="lng">${message("Indoor.coordinate.lng")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="lat">${message("Indoor.coordinate.lat")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="floor">${message("Indoor.coordinate.floor")}</a>
				</th>
				<th>
					${message("admin.common.operator")}
				</th>
			</tr>
			[#list page.content as coordinate]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${coordinate.id}"/>
					</td>
					<td>
						${coordinate.fLocation}
					</td>
					<td>
						${coordinate.fName}
					</td>
					<td>
						${coordinate.fLng}
					</td>
					<td>
						${coordinate.fLat}
					</td>
					<td>
						${coordinate.fFloor}
					</td>
					<td>
                        		<div class="menuWrap">
									<a href="javascript:;" name="locationSelect">
											[${message("admin.common.operator")}]
									</a>
									<div class="popupMenu">
											<ul id="locationOption">
													<li>
														<a href="coordinate_edit.jhtml?id=${coordinate.id}">[${message("admin.common.edit")}]</a>
													</li>
											</ul>
									</div>
								</div>
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
