<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.main.statisticsNav")}</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	html{
		padding-left: 20px;
		padding-right: 15px;
	}
.input .powered {
	font-size: 11px;
	text-align: right;
	color: #cccccc;
}
</style>
</head>
<body>
	<div class="path">
		${message("common.current.position")}：${message("admin.main.statisticsNav")} &raquo; ${message("admin.main.statisticsSystem")}
	</div>
	<div class="bar">
		<div class="buttonWrap">
				<a href="javascript:location.reload(true);" id="refreshButtonFirst" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
		</div>
	</div>
	<table class="input">
		<tr>
			<th>
				${message("statistics.system.name")}:
			</th>
			<td>
				${systemName}
			</td>
			<th>
				${message("statistics.system.version")}:
			</th>
			<td>
				${systemVersion}
			</td>
		</tr>
		<tr>
			<td colspan="4">
				&nbsp;
			</td>
		</tr>
		<tr>
			<th>
				${message("statistics.system.javaVersion")}:
			</th>
			<td>
				${javaVersion}
			</td>
			<th>
				${message("statistics.system.javaHome")}:
			</th>
			<td>
				${javaHome}
			</td>
		</tr>
		<tr>
			<th>
				${message("statistics.system.osName")}:
			</th>
			<td>
				${osName}
			</td>
			<th>
				${message("statistics.system.osArch")}:
			</th>
			<td>
				${osArch}
			</td>
		</tr>
		<tr>
			<th>
				${message("statistics.system.servletInfo")}:
			</th>
			<td>
				${serverInfo}
			</td>
			<th>
				${message("statistics.system.servletVersion")}:
			</th>
			<td>
				${servletVersion}
			</td>
		</tr>
		<tr>
			<td colspan="4">
				&nbsp;
			</td>
		</tr>
		<tr>
			<th>
				${message("statistics.system.cpuUsage")}:
			</th>
			<td>
				${cpuUsage}%
			</td>
			<th>
				${message("statistics.system.memoryUsage")}:
			</th>
			<td>
				${memoryUsage}%
			</td>
		</tr>
		<tr>
			<th>
				${message("statistics.system.phymemoryUsage")}:
			</th>
			<td>
				${phymemoryUsage}%
			</td>
			<th>
				${message("statistics.system.thread")}:
			</th>
			<td>
				${thread}
			</td>
		</tr>
	</table>
</body>
</html>