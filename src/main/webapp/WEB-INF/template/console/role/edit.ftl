<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.role.edit")}</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/js/input.js"></script>
<style type="text/css">
	html{
		padding-left: 20px;
		padding-right: 15px;
	}
.authorities label {
	min-width: 120px;
	_width: 120px;
	display: block;
	float: left;
	padding-right: 4px;
	_white-space: nowrap;
}
</style>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $selectAll = $("#inputForm .selectAll");
	
	
	$selectAll.click(function() {
		var $this = $(this);
		var $thisCheckbox = $this.closest("tr").find(":checkbox");
		if ($thisCheckbox.filter(":checked").size() > 0) {
			$thisCheckbox.prop("checked", false);
		} else {
			$thisCheckbox.prop("checked", true);
		}
		return false;
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			authorities: "required"
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		${message("common.current.position")}：${message("admin.main.role")}&raquo; ${message("admin.role.edit")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
		<input type="hidden" name="id" value="${role.id}" />
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Role.name")}:
				</th>
				<td>
					<input type="text" name="name" class="text" value="${role.name}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Role.description")}:
				</th>
				<td>
					<input type="text" name="description" class="text" value="${role.description}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>
			<tr class="authorities">
				<th>
					<a href="javascript:;" class="selectAll" title="${message("admin.role.selectAll")}">${message("admin.role.systemGroup")}</a>
				</th>
				<td>
					<span class="fieldSet">
						<label>
							<input type="checkbox" name="authorities" value="admin:user"[#if role.authorities?seq_contains("admin:user")] checked="checked"[/#if] />${message("admin.role.admin")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:role"[#if role.authorities?seq_contains("admin:role")] checked="checked"[/#if] />${message("admin.role.role")}
						</label>
					</span>
				</td>
			</tr>
			<tr class="authorities">
				<th>
					<a href="javascript:;" class="selectAll" title="${message("admin.role.selectAll")}">${message("admin.device.manage")}</a>
				</th>
				<td>
					<span class="fieldSet">
						<label>
							<input type="checkbox" name="authorities" value="device:manage"[#if role.authorities?seq_contains("device:manage")] checked="checked"[/#if] />${message("User.device.manage.list")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="device:batchadd"[#if role.authorities?seq_contains("device:batchadd")] checked="checked"[/#if] />${message("User.device.batch.upload")}
						</label>
					</span>
				</td>
			</tr>
			<tr class="authorities">
				<th>
					<a href="javascript:;" class="selectAll" title="${message("admin.role.selectAll")}">${message("admin.statistics")}</a>
				</th>
				<td>
					<span class="fieldSet">
						<label>
							<input type="checkbox" name="authorities" value="statistics:general"[#if role.authorities?seq_contains("statistics:general")] checked="checked"[/#if] />${message("Statistics.general")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="statistics:user"[#if role.authorities?seq_contains("statistics:user")] checked="checked"[/#if] />${message("Statistics.user")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="statistics:device"[#if role.authorities?seq_contains("statistics:device")] checked="checked"[/#if] />${message("Statistics.device")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="statistics:system"[#if role.authorities?seq_contains("statistics:system")] checked="checked"[/#if] />${message("Statistics.system")}
						</label>
					</span>
				</td>
			</tr>
			<tr class="authorities">
				<th>
					<a href="javascript:;" class="selectAll" title="${message("admin.role.selectAll")}">${message("admin.user.setting")}</a>
				</th>
				<td>
					<span class="fieldSet">
						<label>
							<input type="checkbox" name="authorities" value="setting:account"[#if role.authorities?seq_contains("setting:account")] checked="checked"[/#if] />${message("account.info.setting")}
						</label>
					</span>
				</td>
			</tr>
			<tr class="authorities">
				<th>
					<a href="javascript:;" class="selectAll" title="${message("admin.role.selectAll")}">${message("admin.user.enterprise.manage")}</a>
				</th>
				<td>
					<span class="fieldSet">
						<label>
							<input type="checkbox" name="authorities" value="user:usermanage"[#if role.authorities?seq_contains("user:usermanage")] checked="checked"[/#if] />${message("admin.under.user")}
						</label>
					</span>
				</td>
			</tr>
			<tr class="authorities">
				<th>
					<a href="javascript:;" class="selectAll" title="${message("admin.role.selectAll")}">${message("Developer.mode")}</a>
				</th>
				<td>	
					<span class="fieldSet">
						<label>
							<input type="checkbox" name="authorities" value="devmode:api"[#if role.authorities?seq_contains("devmode:api")] checked="checked"[/#if] />${message("admin.devmode.api")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="devmode:devdoc"[#if role.authorities?seq_contains("devmode:devdoc")] checked="checked"[/#if] />${message("admin.devmode.devdoc")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="devmode:test"[#if role.authorities?seq_contains("devmode:test")] checked="checked"[/#if] />${message("admin.devmode.test")}
						</label>
					</span>
				</td>
			</tr>
			<tr class="authorities">
				<th>
					<a href="javascript:;" class="selectAll" title="${message("admin.role.selectAll")}">${message("info.show.main")}</a>
				</th>
				<td>
					<span class="fieldSet">
						<label>
							<input type="checkbox" name="authorities" value="user:userinfo"[#if role.authorities?seq_contains("user:userinfo")] checked="checked"[/#if] />${message("info.show.main")}
						</label>
					</span>
				</td>
			</tr>
			<tr class="authorities">
				<th>
					<a href="javascript:;" class="selectAll" title="${message("admin.role.selectAll")}">${message("admin.main.fishing")}</a>
				</th>
				<td>
					<span class="fieldSet">
						<label>
							<input type="checkbox" name="authorities" value="user:fishing"[#if role.authorities?seq_contains("user:fishing")] checked="checked"[/#if] />${message("admin.main.fishing")}
						</label>
					</span>
				</td>
			</tr>
			<tr class="authorities">
				<th>
					<a href="javascript:;" class="selectAll" title="${message("admin.role.selectAll")}">${message("admin.indoor.manage")}</a>
				</th>
				<td>
					<span class="fieldSet">
						<label>
							<input type="checkbox" name="authorities" value="indoor:beacon:manage"[#if role.authorities?seq_contains("indoor:beacon:manage")] checked="checked"[/#if] />${message("indoor.beacon.manage")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="indoor:fence:manage"[#if role.authorities?seq_contains("indoor:fence:manage")] checked="checked"[/#if] />${message("indoor.fence.manage")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="indoor:coordinate:manage"[#if role.authorities?seq_contains("indoor:coordinate:manage")] checked="checked"[/#if] />${message("indoor.coordinate.manage")}
						</label>
					</span>
				</td>
			</tr>
			
			[#if role.isSystem]
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						<span class="tips">${message("admin.role.editSystemNotAllowed")}</span>
					</td>
				</tr>
			[/#if]
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}"[#if role.isSystem] disabled="disabled"[/#if] />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>