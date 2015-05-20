<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=48E5B926fa6a18bd765400ab3211cbad"></script>
    <script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/js/device/common.js"></script>
    <script type="text/javascript" src="${base}/resources/js/common.js"></script>
    <title>设备最新位置</title>
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
        #allmap {height:100%;margin:-20px auto;}
        /*百度地图样式*/
        .markLabel{ position: relative;color: #fff;background-color : #3b4139;border: 1px solid #000;border-radius:4px;padding:5px 20px;}
        .markLabel .labelName{ display: block;  text-align: center; overflow: hidden;}
        .markLabel .labelArrow{position:absolute;width:0;height:0;border-color:transparent;border-style:solid;bottom:-8px;left:50px;margin-left:-5px;border-width:8px 8px 0;border-top-color:#3b4139;}
    </style>
</head>
<body>
	<div class="path">
		${message("common.current.position")}：${message("User.device")}&raquo; ${message("User.device.manage.list")} &raquo; 设备最新位置
	</div>
<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
    $().ready(function () {
                var api_get_latest_position = "${url}";
                var device_sn ="${fSn}";
                var user_id="${userId}";
                var target_id="${target_id}";
                var token="${token}";
                /** 百度地图API功能 **/
                var map = new BMap.Map("allmap");            // 创建Map实例
                map.enableScrollWheelZoom();
                var point = new BMap.Point(116.404, 39.915);    // 创建点坐标
                map.centerAndZoom(point,15);
                map.addControl(new BMap.NavigationControl());
                function _autoUpdate(){
                    cLib.ajax.get({
                        'url':api_get_latest_position,
                        'data':{
                            'device_sn':device_sn,
                            'user_id':user_id,
                            'token':token
                        },
                        'success':function (response) {
                            if (response&&response.status) {
                                if(response.status.lng&&response.status.lat){
                                    var  corG = convertWgsToGcj02(response.status.lng,response.status.lat);
                                    if(corG){
                                        var   corP = convertGcj02ToBd09(corG.longitude,corG.latitude);
                                        var point = new BMap.Point(corP.longitude, corP.latitude);
                                        point.speed = response.status.speed;
                                        point.stayed = response.status.stayed;
                                        point.receive = cLib.base.longToDate( response.status.receive);
                                        point.direction = response.status.direction;
                                        point.deviceSn = response.status.deviceSn;
                                        response.status.point = point;
                                        var marker = createMarkIcon(response.status)
                                        map.centerAndZoom(point,18);
                                        map.addOverlay(marker); // 将标注添加到地图中
                                       // marker.setPosition(point)
                                    }
                                }
                            }else{
                                $.message("warn", "${message("User.device.getTrack.noData")}");
                            }
                        },
                        'complete':function(){
                        }
                    })
                }
                _autoUpdate();
            }
    )
</script>