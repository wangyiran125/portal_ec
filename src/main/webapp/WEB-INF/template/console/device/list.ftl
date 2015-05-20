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
		${message("common.current.position")}：${message("User.device")}&raquo; ${message("User.device.manage.list")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.jhtml" method="get">
		<div class="bar">
			<a href="add.jhtml" id="addButton" class="iconButton">
				<i class="addIcon">&nbsp;</i>${message("admin.common.add")}
			</a>
			<a href="javascript:;" id="deleteDeviceButton" class="iconButton disabled">
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
		                <option [#if page.searchProperty == "fSn"] selected="selected"[/#if] value="fSn">IMEI</option>
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
			<span>${message("User.device")}</span>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					<input type="checkbox" id="selectAll" />
				</th>
				<th>
					<a href="javascript:;" class="sort" name="fSn">IMEI</a>
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
					<a href="javascript:;" class="sort" name="binding">${message("User.device.isbinding")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="uploadLocation">${message("User.device.isupload.location")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="firstUploadLocationDate">${message("User.device.fisrtupload.location.date")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="lasttUploadLocationDate">${message("User.device.lastupload.location.date")}</a>
				</th>
				<th>
					${message("User.device.status")}</a>
				</th>
				<th>
					${message("admin.device.operator")}
				</th>
			</tr>
			[#list page.content as device]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${device.fSn}"/>
					</td>
					<td>
						${device.fSn}
					</td>
					<td>
						[#if device.fIsvirtual == "YES"]${message("device.fIsvirtual.yes")}[/#if]
						[#if device.fIsvirtual == "NO"]${message("device.fIsvirtual.no")}[/#if]
					</td>
					<td>
						[#if  device.fProtocol== "1"]${message("User.device.fProtocol.M2616")}[/#if]
						[#if  device.fProtocol== "2"]${message("User.device.fProtocol.OBD")}[/#if]
						[#if  device.fProtocol== "3"]${message("User.device.fProtocol.MT90")}[/#if]
						[#if  device.fProtocol== "4"]${message("User.device.fProtocol.T808")}[/#if]
					    [#if  device.fProtocol== "5"]RD[/#if]
						
					</td>
					<td>
						${device.fPhone}
					</td>
					<td>
							[#if  device.fType== "1"]${message("User.device.car")}[/#if]
							[#if  device.fType== "2"]${message("User.device.pet")}[/#if]
							[#if  device.fType== "3"]${message("User.device.person")}[/#if]
							[#if  device.fType== "4"]手持机[/#if]
					</td>
					<td>
						[#if  device.isBinding]${message("User.device.isBinding.true")}[#else]${message("User.device.isBinding.false")}[/#if]
					</td>
					<td>
						[#if  device.isUploadSpot]${message("User.device.isUploadSpot.true")}[#else]${message("User.device.isUploadSpot.false")}[/#if]
					</td>
					<td>
						${device.firstUploadDate}
					</td>
					<td>
						${device.lastUploadDate}
					</td>
					<td>
						[#if  device.fStatus == "1"]${message("user.device.status.enabled")}[/#if]
						[#if  device.fStatus == "2"]${message("user.device.status.disabled")}[/#if]
					</td>
					<td>
                        		<div class="menuWrap">
									<a href="javascript:;" name="locationSelect">
											[${message("admin.device.operator")}]
									</a>
									<div class="popupMenu">
											<ul id="locationOption">
													<li>
														<a href="edit.jhtml?fSn=${device.fSn}">[${message("admin.common.edit")}]</a>
													</li>
												[#if device.fIsvirtual == "YES"&&device.fSn??]
													<li>
														<a href="simulator.jhtml?fSn=${device.fSn}&fProtocol=${device.fProtocol}">[${message("admin.common.uploadSpot")}]</a>
													</li>
												 [/#if]
												 [#if device.fIsvirtual == "YES"&&device.fSn??]
													<li>
													 	 <a href="getLast.jhtml?fSn=${device.fSn}&target_id=${device.targetId}">[${message("admin.common.last")}]</a>
													</li>
													<li>
														 <a href="getTrack.jhtml?fSn=${device.fSn}&target_id=${device.targetId}">[${message("admin.common.track")}]</a>
													</li>
												[/#if]
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
