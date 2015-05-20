<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.devmode.devdoc")}</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/common.js"></script>
<style type="text/css">
	html{
		padding-left: 20px;
		padding-right: 15px;
	}
</style>
</head>
<body>
	<div class="path">
		${message("common.current.position")}：${message("admin.main.devcenter")}&raquo; ${message("admin.devmode.devdoc")}&raquo; ${apiName}
	</div>
	
	${message("development.doc.detail.param")}
	<table id="paramListTable" class="list">
			<tr >
				<th >
					${message("development.doc.detail.param.name")}
				</th>
				<th>
					${message("development.doc.detail.param.type")}
				</th>
				<th>
					${message("development.doc.detail.param.mandatory")}
				</th>
				<th>
					${message("development.doc.detail.param.desc")}
				</th>
			</tr>
		    
			[#list apiDocDetail.request_param as params]
			<tr>
				<td><a>${params.name}</a></td>
				<td><a>${params.type}</a></td>
				<td><a>${params.must}</a></td>
				<td><a>${params.desc}</a></td>
			</tr>
			[/#list]
	</table>
	<br/>
	${message("development.doc.detail.returning")}
	<table id="returningListTable" class="list">
			<tr >
				<th >
					${message("development.doc.detail.returning.field")}
				</th>
				<th>
					${message("development.doc.detail.returning.field.type")}
				</th>
				<th>
					${message("development.doc.detail.returning.field.desc")}
				</th>
			</tr>
		    
			[#list apiDocDetail.return_desc as returning]
			<tr>
				<td><a>${returning.name}</a></td>
				<td><a>${returning.type}</a></td>
				<td><a>${returning.desc}</a></td>
			</tr>
			[/#list]
	</table>
	
	<br/>
	${message("development.doc.detail.returning.result")}
	<table id="resultListTable" class="list">
			<tr >
				<th >
					${message("development.doc.detail.returning.json.demo")}
				</th>
			</tr>
		    
			[#list apiDocDetail.return_json as returningJson]
			<tr >
				<td style="text-align: left">${returningJson.json}</td>
			</tr>
			[/#list]
	</table>
	
	<table class="input">
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='devdoclist.jhtml'" />
				</td>
			</tr>
    </table>
</body>
</html>