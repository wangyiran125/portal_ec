<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=48E5B926fa6a18bd765400ab3211cbad"></script>
    <script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/js/jquery.tools.js"></script>
	<script type="text/javascript" src="${base}/resources/js/jquery.validate.js"></script>
	<script type="text/javascript" src="${base}/resources/js/common.js"></script>
	<script type="text/javascript" src="${base}/resources/js/input.js"></script>
	<script type="text/javascript" src="${base}/resources/js/coord_tools.js"></script>
	
    <title>位置点上传</title>
    <style type="text/css">
    html{
		height: 100%;
		width:100%;
	}
	body{
		height: 100%;
		padding-left: 20px;
		padding-right: 15px;
	}
        #allmap {height:100%;margin:0px auto;}
        table.input th{width:50px;}
    </style>
<script>
$(function(){
	var $inputForm = $("#inputForm");
	var $submit =$(":submit");
	// 表单验证
	$inputForm.validate({
		rules: {
			longitude:"required",
			latitude:"required"
		},
		submitHandler: function(form) {
		$submit.prop("disabled", true);
		sendPostion();
		}
	});
	
	function sendPostion(){
		if($("#deviceType").val() == 4){
			$.message("warn", "${message("protocol.not.support.upload")}");
			return ;
		}		
		$.ajax({
			url: "simulator/save.jhtml",
			type: "POST",
			data: $inputForm.serialize(),
			cache: false,
			beforeSend: function(request) {
				$("#loading_bar").css({display:""});
			},
			success: function(result) {
				$("#loading_bar").css({display:"none"});
				if(result=="success"){
					$.message("success", "${message("admin.upload.success")}");
					$submit.prop("disabled", false);
				}
				if(result=="warn"){
					$.message("warn", "${message("protocol.not.support.upload")}");
				}
			}
		});						
	}
})
</script>
</head>
<body>
	<div class="path" style="margin-bottom:0px">
		${message("common.current.position")}：${message("User.device")}&raquo; ${message("User.device.manage.list")} &raquo; ${message("admin.common.uploadSpot")}
	</div>
		<form id="inputForm"  method="post">
			<table class="input tabContent" style="margin-top:0px">
				<tr>
					<th>
						sn:
					</th>
					<td width="120px">
						${device.fSn}
						<input type="hidden"  name="sn" value="${device.fSn}"/>
						<input type="hidden" id="deviceType" name="deviceType" value="${device.fProtocol}"/>
					</td>
					<th>
						<span class="requiredField">*</span>经度:
					</th>
					<td width="220px">
						<input type="text" id="longitude" readonly name="longitude" class="text"/>
					</td>
					<th>
						<span class="requiredField">*</span>纬度:
					</th>
					<td width="220px">
						<input type="text" id="latitude" readonly name="latitude" class="text"/>
					</td>
					<td width="50px">
						<input type="submit" id="send" class="button" value="${message("device.upload.send")}" />
					</td>
					<td>
					<div id="loading_bar" style="display:none;background: url(../../resources/images/loading_bar.gif) no-repeat;width:208px;height:13px;z-index: 100;"></div>
					</td>
				</tr>
			</table>
	</form>
		<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
    var map = null;
    $().ready(function () {
                /** 百度地图API功能 **/
                map = new BMap.Map("allmap");            // 创建Map实例
                var point = new BMap.Point(116.404074, 39.913904);    // 创建点坐标
                map.centerAndZoom(point,15);
                map.addControl(new BMap.NavigationControl());
                map.enableScrollWheelZoom(true);
                map.addControl(new BMap.OverviewMapControl());              //添加默认缩略地图控件
				map.addControl(new BMap.OverviewMapControl({isOpen:true}));   //右上角，打开
				map.addEventListener("click", showInfo);
				var marker ;
				 function showInfo(e){
					 if(marker){
						map.removeOverlay(marker);
					 }
					 var wgsPoint = convertBd09ToWgs(e.point.lng,e.point.lat);
					$("#longitude").val(wgsPoint.lon);
					$("#latitude").val(wgsPoint.lat);
					marker = new BMap.Marker(new BMap.Point(e.point.lng, e.point.lat));  // 创建标注
					map.addOverlay(marker);              // 将标注添加到地图中
					marker.enableDragging(); 
					marker.addEventListener("dragend", function showInfo(){
						 var p = marker.getPosition();       //获取marker的位置
						 $("#longitude").val(p.lng);
						 $("#latitude").val(p.lat); 
					});
				}
				
    });
</script>
