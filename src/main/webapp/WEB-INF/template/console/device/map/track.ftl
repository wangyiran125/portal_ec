<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=48E5B926fa6a18bd765400ab3211cbad"></script>
    <script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/js/device/common.js"></script>
    <script type="text/javascript" src="${base}/resources/datePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${base}/resources/js/common.js"></script>
    <title>设备位置轨迹</title>
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
        .trackOp {margin-top:-22px;background: url("../../resources/images/bg_options.jpg") repeat-x scroll left top rgba(0, 0, 0, 0);}
        .trackOp .guideName {color: #f26609;display: inline-block;font-weight: bold;height: 40px;line-height: 40px;padding-left: 40px;padding-right: 15px;}
        .trackOp .times span { padding: 0 10px; position: relative; }
        input, textarea { color: #555;font-family: 'Microsoft Yahei';font-size: 12px;}
        .trackOp .times input {border: 1px solid #b3bdc3; padding: 0 5px;width: 140px; }
        button { background: url("../../resources/images/bg_button_op.jpg") repeat-x scroll left top rgba(0, 0, 0, 0); border: 1px solid #b3bdc3; color: #2c343b;cursor: pointer; font-size: 12px;height: 24px;padding: 0 10px 2px; font-family: 'Microsoft Yahei'; font-weight: normal;line-height: 100%;}
        /*百度地图样式*/
        .markLabel{ position: relative;color: #fff;background-color : #3b4139;border: 1px solid #000;border-radius:4px;padding:5px 20px;}
        .markLabel .labelName{ display: block;  text-align: center; overflow: hidden;}
        .markLabel .labelArrow{position:absolute;width:0;height:0;border-color:transparent;border-style:solid;bottom:-8px;left:50px;margin-left:-5px;border-width:8px 8px 0;border-top-color:#3b4139;}

    </style>
</head>
<body>
<div class="path">
${message("common.current.position")}：${message("User.device")}&raquo; ${message("User.device.manage.list")} &raquo; 设备位置轨迹
</div>
<div class="trackOp">
    <span class="guideName">选择时间</span>
    <span class="times">
        从 :<span><input class="date Wdate" id="beginTime" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  /></span>
        到 :<span><input class="date Wdate" id="endTime" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></span>
    </span>
    <span class="startBtn"><button class="traceQuery" onclick="getTrace()">轨迹查询</button></span>
</div>
<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
    var map = null;
    //ajax 返回的跟踪GPS
    var polyPoints =[];
    //划线
    var polyline =null;
    function getTrace(){
        map.clearOverlays();
        var api_get_latest_position = "${url}";
        var device_sn ="${fSn}";
        var user_id="${userId}";
        var target_id="${target_id}";
        var token="${token}";
        var beginTimePicker = $('#beginTime').val();
        beginTimePicker = new Date(beginTimePicker.replace(/-/g,"/") );
        var endTimePicker = $('#endTime').val();
        endTimePicker = new Date(endTimePicker.replace(/-/g,"/") );
        if(cLib.base.validateDate(beginTimePicker,endTimePicker)) {
            var beginDate = beginTimePicker.getTime();
            var endDate = endTimePicker.getTime();
            if (beginDate < cLib.base.subDaysFromToday(60)) {
                $.message("warn",  '${message("User.device.getTrack.only60day")}');
                return;
            }
            if ((endDate - beginDate) > 259200000) {
                $.message("warn",  '${message("User.device.getTrack.view3day")}');
                return;
            }
            cLib.ajax.get({
                'url':api_get_latest_position,
                'data':{
                    'device_sn':device_sn,
                    'user_id':user_id,
                    'taget_id':target_id,
                    'token':token,
                    'begin':beginDate,//new Date("06 24,2014 00:00:00").getTime(),//beginDate;
                    'end':endDate//new Date("06 25,2014 00:00:00").getTime()//endDate
                },
                'success':function (response) {
                    if (response&&response.track) {
                        if(response.track&&response.track.length>0){
                            var j=0;
                            for(var i =(response.track.length-1);i>=0 ;i--){
                                corG = convertWgsToGcj02(response.track[i].lng,response.track[i].lat);
                                if(corG){
                                    var   corP = convertGcj02ToBd09(corG.longitude,corG.latitude);
                                    var point = new BMap.Point(corP.longitude, corP.latitude);
                                    response.track[i].indexNo =j ;
                                    response.track[i].point = point;
                                    if(i==0){  response.track[i].end=true}
                                    if(i==(response.track.length-1)){  response.track[i].start=true}
                                    var marker = createMarkIcon(response.track[i])
                                    map.centerAndZoom(point,19);
                                    map.addOverlay(marker); // 将标注添加到地图中
                                    polyPoints[j++]=point;
                                }
                            }
                            run();
                        }else{
                            $.message("warn",   '${message("User.device.getTrack.noData")}');
                        }
                    }
                },
                'complete':function(){
                }
            })
        }
    }
    $().ready(function () {
                /** 百度地图API功能 **/
                map = new BMap.Map("allmap");            // 创建Map实例
                map.enableScrollWheelZoom();
                var point = new BMap.Point(116.404, 39.915);    // 创建点坐标
                map.centerAndZoom(point,15);
                map.addControl(new BMap.NavigationControl());
                var currDate = cLib.base.getDateTime(new Date().getTime());
                currDate.hour = currDate.hour.toString().length== 1?'0'+currDate.hour:currDate.hour.toString();
                currDate.min = currDate.min.toString().length== 1?'0'+currDate.min:currDate.min.toString();
                var iniDateEnd = currDate.year+'-'+currDate.month+'-'+currDate.day+' '+currDate.hour+':'+currDate.min+":00";
                $('#endTime').val(iniDateEnd);
                var beginDate = cLib.base.getDateTime(new Date().getTime()-3*24*60*60*1000);
                var iniDateBegin = beginDate.year+'-'+beginDate.month+'-'+beginDate.day+' '+currDate.hour+':'+currDate.min+":00";
                $('#beginTime').val(iniDateBegin);
            }
    )
    var run = function () {
        if (polyline) {
            map.removeOverlay(polyline);
        }
        polyline = new BMap.Polyline(polyPoints, {strokeColor: "blue", strokeWeight: 6, strokeOpacity: 0.5});
        map.addOverlay(polyline);
        map.setViewport(polyPoints);
    }

</script>