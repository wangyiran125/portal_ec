<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.main.statisticsNav")}</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/css/jquery.jqplot.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/js/input.js"></script>

<style type="text/css">
	html{
		padding-left: 20px;
		padding-right: 15px;
	}
  .imageTd{
       width:170px;
  }
  .imageTd img{
       width:150px;
       max-height:150px;
   }
 table.input th {
  	width:20%;
  }
   
</style>
</head>
<body>
	<div class="path">
		${message("common.current.position")}：
		[#if user.userType == "INDIVIDUAL"]
			${message("info.show.individual")}
		[#else]
			${message("info.show.enterprise")}
		[/#if]
		&raquo;
		[#if user.userType == "INDIVIDUAL"]
			${message("info.show.individual")}
		[#else]
			${message("info.show.enterprise")}
		[/#if]
	</div>
		<form id="inputForm">
			<ul id="tab" class="tab">
				[#if user.userType == "ENTERPRISE"] 
					<li>
						<input type="button" value="${message("info.show.enterprise")}" />
					</li>
				[/#if]
				[#if user.userType == "INDIVIDUAL"] 
					<li>
						<input type="button" value="${message("info.show.individual")}" />
					</li>
				[/#if]
			</ul>
			[#if user.userType == "ENTERPRISE"]
				<table id="enterpriseShow" class="input tabContent">
					<tr>
							<th>
								 ${message("Company.name")}:
							</th>
							<td>
								[#if user.enterpriseUserExtend.companyName??]${user.enterpriseUserExtend.companyName}[/#if]
							</td>
						</tr>
						<tr>
							<th>
								${message("Company.address")}:
							</th>
							<td>
								[#if user.enterpriseUserExtend.companyAddress??]${user.enterpriseUserExtend.companyAddress}[/#if]
							</td>
						</tr>
						<tr>
							<th>
								${message("Company.contactPerson")}:
							</th>
							<td>
								[#if user.enterpriseUserExtend.contactPerson??]${user.enterpriseUserExtend.contactPerson}[/#if]
							</td>
						</tr>
						<tr>
							<th>
								${message("Company.contactEmail")}:
							</th>
							<td>
								[#if user.enterpriseUserExtend.contactEmail??]${user.enterpriseUserExtend.contactEmail}[/#if]
							</td>
						</tr>
						<tr>
							<th>
								${message("Company.contactCellPhoneNum")}:
							</th>
							<td>
								[#if user.enterpriseUserExtend.contactCellPhoneNum??]${user.enterpriseUserExtend.contactCellPhoneNum}[/#if]
							</td>
						</tr>
						<tr>
							<th>
								${message("Company.contactPhoneNum")}:
							</th>
							<td>
								[#if user.enterpriseUserExtend.contactPhoneNum??]${user.enterpriseUserExtend.contactPhoneNum}[/#if]
							</td>
						</tr>
						<tr>
							<th>
								${message("Company.licenseNum")}:
							</th>
							<td>
								[#if user.enterpriseUserExtend.licenseNum??]${user.enterpriseUserExtend.licenseNum}[/#if]
							</td>
						</tr>
						<tr>
							<th>
								${message("Company.organizationCode")}:
							</th>
							<td>
								[#if user.enterpriseUserExtend.organizationCode??]${user.enterpriseUserExtend.organizationCode}[/#if]
							</td>
						</tr>
				</table>
			[/#if]
			[#if user.userType == "INDIVIDUAL"] 
				<table id="individualShow" class="input tabContent">
					<tr>
						<th>
							${message("Individual.name")}:
						</th>
						<td>
							[#if user.individualUserExtend.name??]${user.individualUserExtend.name}[/#if]
						</td>
					</tr>
					<tr>
						<th>
							${message("Individual.identityCard")}:
						</th>
						<td>
							[#if user.individualUserExtend.identityCard??]${user.individualUserExtend.identityCard}[/#if]
						</td>
					</tr>
					<tr>
						<th>
							${message("Individual.address")}:
						</th>
						<td>
							[#if user.individualUserExtend.address??]${user.individualUserExtend.address}[/#if]
						</td>
					</tr>
					<tr>
						<th>
							${message("Individual.email")}:
						</th>
						<td>
							[#if user.individualUserExtend.email??]${user.individualUserExtend.email}[/#if]
						</td>
					</tr>
					<tr>
						<th>
							${message("Individual.cellPhoneNum")}:
						</th>
						<td>
							[#if user.individualUserExtend.cellPhoneNum??]${user.individualUserExtend.cellPhoneNum}[/#if]
						</td>
					</tr>
					<tr>
						<th>
							${message("Individual.phoneNum")}:
						</th>
						<td>
							[#if user.individualUserExtend.phoneNum??]${user.individualUserExtend.phoneNum}[/#if]
						</td>
					</tr>
				</table>
			[/#if]
		</form>
	</body>
	
</html>
