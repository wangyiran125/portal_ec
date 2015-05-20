<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=9">
	<title>${userConf.backendModeUserExtend.siteName}</title>
    <link rel="shortcut icon" href="${"${base}/upload/"+userConf.backendModeUserExtend.icon}">
    <link type="text/css" href="${base}/resources/demo_files/base.css" rel="stylesheet">
    <script type="text/javascript" src="${base}/resources/demo_files/jquery-1.9.1.min.js"></script>
    <link href="${base}/resources/demo_files/iealert.css" rel="stylesheet" charset="utf-8">
    <link href="${base}/resources/demo_files/monitor.css" rel="stylesheet" charset="utf-8">
    <link href="${base}/resources/demo_files/jquery-ui-1.css" rel="stylesheet" charset="utf-8">
    <link href="${base}/resources/demo_files/jquery.css" rel="stylesheet" charset="utf-8">
    <link type="text/css" rel="stylesheet" href="${base}/resources/demo_files/map.css">
    <style>
    	#mainlogo{
    		width:60px;
    		max-height:80px;
    	}
    </style>
    <script>
        function loadCss(path) {
            var head = document.getElementsByTagName('head')[0];
            if (!path || path.length === 0) {
                throw new Error('argument "path" is required !');
            }
            var link = document.createElement('link');
            link.href = path;
            link.rel = 'stylesheet';
            link.type = 'text/css';
            head.appendChild(link);
        }
         function setUi() {
           var userSource = {
	            	"company_name":"${company_name}",
	                "user_name": "${userConf.userName}",//预览用户
	                "custCopyRight": "${custCopyRight}",//版权
	                "isEnable": "false",
	                "company_logo": "${"${base}/upload/"+userConf.backendModeUserExtend.logo}",
	                "company_icon": "${"${base}/upload/"+userConf.backendModeUserExtend.icon}",
	                "company_favIcon": "${"${base}/upload/"+userConf.backendModeUserExtend.favIcon}",
	                "company_theme": "${userConf.backendModeUserExtend.frontStyle}",//字符串[css,goldenCss,greenCss]
	                "location":"false",
	                "track":"false",
	                "obd":"false",
	                "fence":"false",
	                "warning":"false",
	                "count":"false",
	                "zone":"false"
            };
            
             [#list userConf.backendModeUserExtend.userCustomizeFuns as customizeFun]
	                	userSource.${customizeFun.funcationName}="true";
	         [/#list]
            var maxwidth=8;
            if(userSource.user_name.length > maxwidth){
                userSource.user_name = userSource.user_name.substring(0,maxwidth);
                userSource.user_name += '...';
            }
            if(userSource.company_name.length > maxwidth){
                userSource.company_name = userSource.company_name.substring(0,maxwidth);
                userSource.company_name += '...';
            }
            $('.name').html(userSource.user_name);
            
            $('#footer').html(userSource.custCopyRight);
             $('.company_name').html(userSource.company_name);
           if(userSource.company_logo&&userSource.company_logo.length!=""){
               $('#logo').html('<a href="#">  <img style="height: 40px;" id="mainlogo" src="'+userSource.company_logo+'"></a>')
           }else{
               $('#logo').html('<a href="#" style="font-size: 35px;font-weight: bold;" class="company_name">'+userSource.company_name+'</a>')
           }
            loadCss('${base}/resources/demo_files/'+userSource.company_theme+"/theme.css")
             $('.map_location').css("display", "");
            if (userSource.location == "false") {
                $('#nav_monitor').html("我的设备");
                var dev_monitors = $('span.dev_monitor');
                $('.map_location').css("display", "none");
                dev_monitors.css("display", "none");
            }
            if (userSource.track == "false") {
                var dev_tracks = $('span.dev_track');
                dev_tracks.css("display", "none");
            }
            if (userSource.obd == "false") {
                var dev_speedings = $('span.dev_speeding');
                dev_speedings.css("display", "none");
            }
            if (userSource.fence == "false") {
                var dev_fences = $('span.dev_fence');
                dev_fences.css("display", "none");
            }
            if (userSource.count == "false") {
                $('#nav_report').css("display", "none");
            }
            if (userSource.warning == "false") {
                $('#nav_warning').css("display", "none");
            }
            if (userSource.zone == "false") {
                $('#nav_buffer').css("display", "none");
            }
        }
        $(document).ready(function(){
             setUi();
        });
    </script>
</head>
<body style="overflow-y: hidden;">
<div id="container">
<div id="header">
    <div id="logo">
    </div>
</div>
<div id="mainnav">
    <div id="userGuide">
        <span style="display: inline-block;" class="wel"><em title="dfxtd" class="name"></em>, 欢迎使用<em class="company_name"></em></span>
        <span class="settings" id="changePwd">修改密码</span> | <span class="exit">注销</span>
    </div>
    <ul>
        <li><a class="here" id="nav_monitor" href="#app/monitor">定位监控</a></li>
        <li><a id="nav_report" href="#app/monitor/analyse" >数据报表</a>  </li>
        <li><a id="nav_manage" href="#app/manage">设备管理</a></li>
        <li><a id="nav_warning" href="#app/warning">设备警报<span style="display: none;" class="num">0</span></a></li>
        <!--//todo 空间分析-->
        <li><a id="nav_buffer" href="#app/buffer">空间分析</a></li>
    </ul>
</div>
<div style="height: 719px; position: relative;" id="page">
<div>
<div style="height: 719px; position: relative;" id="home">
<div class="pannerWrap">
<div>
<div class="devPanner">
<div class="header">
    <span class="tip">我的设备</span>

    <div class="status">
        <span class="all here">全部</span>
        <span class="onLine">在线</span>
        <span class="offLine">离线</span>
    </div>
</div>
<div class="deviceListWrap">
<div tabindex="5000" style="height: 677px; overflow: hidden;" class="deviceList">
<div>
    <div class="dev_item">
        <p class="curLocation">地址：<span class="location">广西壮族自治区钦州市浦北县光明路8号</span></p>

        <div class="dev_img"><img src="${base}/resources/demo_files/354188046994203.jpg" class="dev_logo" height="58" width="58"></div>
        <div class="dev_info">
            <span class="dev_name">354188046994203</span>
            <span class="dev_no">设备编号：354188046994203</span>

            <div class="dev_op">
                <span class="dev_monitor">跟踪</span>
                <span class="dev_track"> |  轨迹播放</span>
                <span class="dev_fence"> |  电子围栏</span>
                <span class="dev_detail"> |  设备信息</span>
                <span class="dev_speeding"> |  设置</span>
                <span style="display:none;" class="dev_analyse">分析</span>
            </div>
        </div>
        <span class="status type2">离线</span>
        <span class="clear"></span>
    </div>
</div>
<div>
    <div class="dev_item">
        <p class="curLocation">地址：<span class="location">北京市昌平区太平庄中一街</span></p>

        <div class="dev_img"><img src="${base}/resources/demo_files/1.png" class="dev_logo" height="58" width="58"></div>
        <div class="dev_info">
            <span class="dev_name">354188046971524</span>
            <span class="dev_no">设备编号：354188046971524</span>

            <div class="dev_op">
                <span class="dev_monitor">跟踪</span>
                <span class="dev_track"> |  轨迹播放</span>
                <span class="dev_fence"> |  电子围栏</span>
                <span class="dev_detail"> |  设备信息</span>
                <span class="dev_speeding"> |  设置</span>
                <span style="display:none;" class="dev_analyse">分析</span>
            </div>
        </div>
        <span class="status type2">离线</span>
        <span class="clear"></span>
    </div>
</div>
<div>
    <div class="dev_item">
        <p class="curLocation">地址：<span class="location">上海市普陀区真北路3461-、3463-、3465号</span></p>

        <div class="dev_img"><img src="${base}/resources/demo_files/1.png" class="dev_logo" height="58" width="58"></div>
        <div class="dev_info">
            <span class="dev_name">帕萨特旧</span>
            <span class="dev_no">设备编号：354188046993643</span>

            <div class="dev_op">
                <span class="dev_monitor">跟踪</span>
                <span class="dev_track"> |  轨迹播放</span>
                <span class="dev_fence"> |  电子围栏</span>
                <span class="dev_detail"> |  设备信息</span>
                <span class="dev_speeding"> |  设置</span>
                <span style="display:none;" class="dev_analyse">分析</span>
            </div>
        </div>
        <span class="status type1">在线</span>
        <span class="clear"></span>
    </div>
</div>
<div>
    <div class="dev_item">
        <p class="curLocation">地址：<span class="location">上海市杨浦区民京路658弄1-21号</span></p>

        <div class="dev_img"><img src="${base}/resources/demo_files/1.png" class="dev_logo" height="58" width="58"></div>
        <div class="dev_info">
            <span class="dev_name">354188046994161</span>
            <span class="dev_no">设备编号：354188046994161</span>

            <div class="dev_op">
                <span class="dev_monitor">跟踪</span>
                <span class="dev_track"> |  轨迹播放</span>
                <span class="dev_fence"> |  电子围栏</span>
                <span class="dev_detail"> |  设备信息</span>
                <span class="dev_speeding"> |  设置</span>
                <span style="display:none;" class="dev_analyse">分析</span>
            </div>
        </div>
        <span class="status type2">离线</span>
        <span class="clear"></span>
    </div>
</div>
<div>
    <div class="dev_item">
        <p class="curLocation">地址：<span class="location">上海市奉贤区环城南路1309弄-51号</span></p>

        <div class="dev_img"><img src="${base}/resources/demo_files/354188046994492.png" class="dev_logo" height="58" width="58"></div>
        <div class="dev_info">
            <span class="dev_name">福特SUV</span>
            <span class="dev_no">设备编号：354188046994492</span>

            <div class="dev_op">
                <span class="dev_monitor">跟踪</span>
                <span class="dev_track"> |  轨迹播放</span>
                <span class="dev_fence"> |  电子围栏</span>
                <span class="dev_detail"> |  设备信息</span>
                <span class="dev_speeding"> |  设置</span>
                <span style="display:none;" class="dev_analyse">分析</span>
            </div>
        </div>
        <span class="status type2">离线</span>
        <span class="clear"></span>
    </div>
</div>
<div>
    <div class="dev_item">
        <p class="curLocation">地址：<span class="location">上海市青浦区青湖路1150弄-23号</span></p>

        <div class="dev_img"><img src="${base}/resources/demo_files/1.png" class="dev_logo" height="58" width="58"></div>
        <div class="dev_info">
            <span class="dev_name">路虎</span>
            <span class="dev_no">设备编号：354188046971573</span>

            <div class="dev_op">
                <span class="dev_monitor">跟踪</span>
                <span class="dev_track"> |  轨迹播放</span>
                <span class="dev_fence"> |  电子围栏</span>
                <span class="dev_detail"> |  设备信息</span>
                <span class="dev_speeding"> |  设置</span>
                <span style="display:none;" class="dev_analyse">分析</span>
            </div>
        </div>
        <span class="status type2">离线</span>
        <span class="clear"></span>
    </div>
</div>
<div>
    <div class="dev_item">
        <p class="curLocation">地址：<span class="location">上海市青浦区a9</span></p>

        <div class="dev_img"><img src="${base}/resources/demo_files/1.png" class="dev_logo" height="58" width="58"></div>
        <div class="dev_info">
            <span class="dev_name">君越</span>
            <span class="dev_no">设备编号：354188046973611</span>

            <div class="dev_op">
                <span class="dev_monitor">跟踪</span>
                <span class="dev_track"> |  轨迹播放</span>
                <span class="dev_fence"> |  电子围栏</span>
                <span class="dev_detail"> |  设备信息</span>
                <span class="dev_speeding"> |  设置</span>
                <span style="display:none;" class="dev_analyse">分析</span>
            </div>
        </div>
        <span class="status type2">离线</span>
        <span class="clear"></span>
    </div>
</div>
<div>
    <div class="dev_item">
        <p class="curLocation">地址：<span class="location">上海市青浦区盈港路262号</span></p>

        <div class="dev_img"><img src="${base}/resources/demo_files/1.png" class="dev_logo" height="58" width="58"></div>
        <div class="dev_info">
            <span class="dev_name">别克GL8</span>
            <span class="dev_no">设备编号：354188046974171</span>

            <div class="dev_op">
                <span class="dev_monitor">跟踪</span>
                <span class="dev_track"> |  轨迹播放</span>
                <span class="dev_fence"> |  电子围栏</span>
                <span class="dev_detail"> |  设备信息</span>
                <span class="dev_speeding"> |  设置</span>
                <span style="display:none;" class="dev_analyse">分析</span>
            </div>
        </div>
        <span class="status type2">离线</span>
        <span class="clear"></span>
    </div>
</div>
<div>
    <div class="dev_item">
        <p class="curLocation">地址：<span class="location">上海市杨浦区赤峰路160号</span></p>

        <div class="dev_img"><img src="${base}/resources/demo_files/1.png" class="dev_logo" height="58" width="58"></div>
        <div class="dev_info">
            <span class="dev_name">马六</span>
            <span class="dev_no">设备编号：354188046974189</span>

            <div class="dev_op">
                <span class="dev_monitor">跟踪</span>
                <span class="dev_track"> |  轨迹播放</span>
                <span class="dev_fence"> |  电子围栏</span>
                <span class="dev_detail"> |  设备信息</span>
                <span class="dev_speeding"> |  设置</span>
                <span style="display:none;" class="dev_analyse">分析</span>
            </div>
        </div>
        <span class="status type2">离线</span>
        <span class="clear"></span>
    </div>
</div>
<div>
    <div class="dev_item">
        <p class="curLocation">地址：<span class="location">上海市静安区新闸路1913号</span></p>

        <div class="dev_img"><img src="${base}/resources/demo_files/1.png" class="dev_logo" height="58" width="58"></div>
        <div class="dev_info">
            <span class="dev_name">qqqq</span>
            <span class="dev_no">设备编号：354188046974197</span>

            <div class="dev_op">
                <span class="dev_monitor">跟踪</span>
                <span class="dev_track"> |  轨迹播放</span>
                <span class="dev_fence"> |  电子围栏</span>
                <span class="dev_detail"> |  设备信息</span>
                <span class="dev_speeding"> |  设置</span>
                <span style="display:none;" class="dev_analyse">分析</span>
            </div>
        </div>
        <span class="status type2">离线</span>
        <span class="clear"></span>
    </div>
</div>
<div>
    <div class="dev_item">
        <p class="curLocation">地址：<span class="location">上海市闸北区延长中路800弄-21号</span></p>

        <div class="dev_img"><img src="${base}/resources/demo_files/1.png" class="dev_logo" height="58" width="58"></div>
        <div class="dev_info">
            <span class="dev_name">张</span>
            <span class="dev_no">设备编号：354188046975343</span>

            <div class="dev_op">
                <span class="dev_monitor">跟踪</span>
                <span class="dev_track"> |  轨迹播放</span>
                <span class="dev_fence"> |  电子围栏</span>
                <span class="dev_detail"> |  设备信息</span>
                <span class="dev_speeding"> |  设置</span>
                <span style="display:none;" class="dev_analyse">分析</span>
            </div>
        </div>
        <span class="status type2">离线</span>
        <span class="clear"></span>
    </div>
</div>
<div>
    <div class="dev_item">
        <p class="curLocation">地址：<span class="location">安徽省安庆市宜秀区石塘湖路</span></p>

        <div class="dev_img"><img src="${base}/resources/demo_files/1.png" class="dev_logo" height="58" width="58"></div>
        <div class="dev_info">
            <span class="dev_name">354188046994765</span>
            <span class="dev_no">设备编号：354188046994765</span>

            <div class="dev_op">
                <span class="dev_monitor">跟踪</span>
                <span class="dev_track"> |  轨迹播放</span>
                <span class="dev_fence"> |  电子围栏</span>
                <span class="dev_detail"> |  设备信息</span>
                <span class="dev_speeding"> |  设置</span>
                <span style="display:none;" class="dev_analyse">分析</span>
            </div>
        </div>
        <span class="status type2">离线</span>
        <span class="clear"></span>
    </div>
</div>
<div>
    <div class="dev_item">
        <p class="curLocation">地址：<span class="location">北京市房山区苏庄西街</span></p>

        <div class="dev_img"><img src="${base}/resources/demo_files/dog2.jpg" class="dev_logo" height="58" width="58"></div>
        <div class="dev_info">
            <span class="dev_name">354188046984410</span>
            <span class="dev_no">设备编号：354188046984410</span>

            <div class="dev_op">
                <span class="dev_monitor">跟踪</span>
                <span class="dev_track"> |  轨迹播放</span>
                <span class="dev_fence"> |  电子围栏</span>
                <span class="dev_detail"> |  设备信息</span>
                <span class="dev_speeding"> |  设置</span>
                <span style="display:none;" class="dev_analyse">分析</span>
            </div>
        </div>
        <span class="status type2">离线</span>
        <span class="clear"></span>
    </div>
</div>
<div>
    <div class="dev_item">
        <p class="curLocation">地址：<span class="location">辽宁省沈阳市大东区大北关街44号楼-13门</span></p>

        <div class="dev_img"><img src="${base}/resources/demo_files/car5.png" class="dev_logo" height="58" width="58"></div>
        <div class="dev_info">
            <span class="dev_name">354188046994963</span>
            <span class="dev_no">设备编号：354188046994963</span>

            <div class="dev_op">
                <span class="dev_monitor">跟踪</span>
                <span class="dev_track"> |  轨迹播放</span>
                <span class="dev_fence"> |  电子围栏</span>
                <span class="dev_detail"> |  设备信息</span>
                <span class="dev_speeding"> |  设置</span>
                <span style="display:none;" class="dev_analyse">分析</span>
            </div>
        </div>
        <span class="status type2">离线</span>
        <span class="clear"></span>
    </div>
</div>
<div>
    <div class="dev_item">
        <p class="curLocation">地址：<span class="location">北京市西城区老墙根街107号院-1号楼</span></p>

        <div class="dev_img"><img src="${base}/resources/demo_files/1.png" class="dev_logo" height="58" width="58"></div>
        <div class="dev_info">
            <span class="dev_name">354188046995127</span>
            <span class="dev_no">设备编号：354188046995127</span>

            <div class="dev_op">
                <span class="dev_monitor">跟踪</span>
                <span class="dev_track"> |  轨迹播放</span>
                <span class="dev_fence"> |  电子围栏</span>
                <span class="dev_detail"> |  设备信息</span>
                <span class="dev_speeding"> |  设置</span>
                <span style="display:none;" class="dev_analyse">分析</span>
            </div>
        </div>
        <span class="status type2">离线</span>
        <span class="clear"></span>
    </div>
</div>
<div>
    <div class="dev_item">
        <p class="curLocation">地址：<span class="location">广东省广州市白云区三元里大道1239</span></p>

        <div class="dev_img"><img src="${base}/resources/demo_files/354188046977729.jpg" class="dev_logo" height="58" width="58"></div>
        <div class="dev_info">
            <span class="dev_name">奥迪Ａ6Ｌ</span>
            <span class="dev_no">设备编号：354188046977729</span>

            <div class="dev_op">
                <span class="dev_monitor">跟踪</span>
                <span class="dev_track"> |  轨迹播放</span>
                <span class="dev_fence"> |  电子围栏</span>
                <span class="dev_detail"> |  设备信息</span>
                <span class="dev_speeding"> |  设置</span>
                <span style="display:none;" class="dev_analyse">分析</span>
            </div>
        </div>
        <span class="status type2">离线</span>
        <span class="clear"></span>
    </div>
</div>
<div>
    <div class="dev_item">
        <p class="curLocation">地址：<span class="location">山西省临汾市尧都区西赵路</span></p>

        <div class="dev_img"><img src="${base}/resources/demo_files/354188046971748.png" class="dev_logo" height="58" width="58"></div>
        <div class="dev_info">
            <span class="dev_name">麻豆爸爸</span>
            <span class="dev_no">设备编号：354188046971748</span>

            <div class="dev_op">
                <span class="dev_monitor">跟踪</span>
                <span class="dev_track"> |  轨迹播放</span>
                <span class="dev_fence"> |  电子围栏</span>
                <span class="dev_detail"> |  设备信息</span>
                <span class="dev_speeding"> |  设置</span>
                <span style="display:none;" class="dev_analyse">分析</span>
            </div>
        </div>
        <span class="status type2">离线</span>
        <span class="clear"></span>
    </div>
</div>
<div>
    <div class="dev_item">
        <p class="curLocation">地址：<span class="location">北京市朝阳区广顺北大街33号院-1</span></p>

        <div class="dev_img"><img src="${base}/resources/demo_files/1.png" class="dev_logo" height="58" width="58"></div>
        <div class="dev_info">
            <span class="dev_name">354188046985268</span>
            <span class="dev_no">设备编号：354188046985268</span>

            <div class="dev_op">
                <span class="dev_monitor">跟踪</span>
                <span class="dev_track"> |  轨迹播放</span>
                <span class="dev_fence"> |  电子围栏</span>
                <span class="dev_detail"> |  设备信息</span>
                <span class="dev_speeding"> |  设置</span>
                <span style="display:none;" class="dev_analyse">分析</span>
            </div>
        </div>
        <span class="status type2">离线</span>
        <span class="clear"></span>
    </div>
</div>
</div>
</div>
<span class="toggle close"></span>
</div>
</div>
</div>
<div style="height: 719px;" class="mainWrap" id="mainContainer">
    <div class="options">
    </div>
    <div style="height: 678px; width: 896px; overflow: hidden; position: relative; z-index: 0; background-color: rgb(243, 241, 236); color: rgb(0, 0, 0); text-align: left;"
         id="map">
        <div style="overflow: visible; position: absolute; z-index: 0; left: 0px; top: 0px; cursor: grab;">
            <div style="position: absolute; left: 0px; top: 0px; z-index: 9; overflow: hidden; width: 896px; height: 678px;"
                 class="BMap_mask"></div>
            <div class="map_location" style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 200;">
                <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 800;"></div>
                <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 700;"><span
                        title="" class="BMap_Marker BMap_noprint" unselectable="on" "="" style="position: absolute;
                    padding: 0px; margin: 0px; border: 0px none; -moz-user-select: none; cursor: pointer; background:
                    url(&quot;http://api.map.baidu.com/images/blank.gif&quot;) repeat scroll 0% 0% transparent; width:
                    70px; height: 57px; left: 318px; top: 508px; z-index: -4454388;"></span><span title=""
                                                                                                  class="BMap_Marker BMap_noprint"
                                                                                                  unselectable="on" "=""
                    style="position: absolute; padding: 0px; margin: 0px; border: 0px none; -moz-user-select: none;
                    cursor: pointer; background: url(&quot;http://api.map.baidu.com/images/blank.gif&quot;) repeat
                    scroll 0% 0% transparent; width: 70px; height: 57px; left: 412px; top: 225px; z-index:
                    -8014776;"></span><span title="" class="BMap_Marker BMap_noprint" unselectable="on" "=""
                    style="position: absolute; padding: 0px; margin: 0px; border: 0px none; -moz-user-select: none;
                    cursor: pointer; background: url(&quot;http://api.map.baidu.com/images/blank.gif&quot;) repeat
                    scroll 0% 0% transparent; width: 70px; height: 57px; left: 479px; top: 372px; z-index:
                    -6256638;"></span><span title="" class="BMap_Marker BMap_noprint" unselectable="on" "=""
                    style="position: absolute; padding: 0px; margin: 0px; border: 0px none; -moz-user-select: none;
                    cursor: pointer; background: url(&quot;http://api.map.baidu.com/images/blank.gif&quot;) repeat
                    scroll 0% 0% transparent; width: 70px; height: 57px; left: 481px; top: 371px; z-index:
                    -6265334;"></span><span title="" class="BMap_Marker BMap_noprint" unselectable="on" "=""
                    style="position: absolute; padding: 0px; margin: 0px; border: 0px none; -moz-user-select: none;
                    cursor: pointer; background: url(&quot;http://api.map.baidu.com/images/blank.gif&quot;) repeat
                    scroll 0% 0% transparent; width: 70px; height: 57px; left: 480px; top: 378px; z-index:
                    -6181238;"></span><span title="" class="BMap_Marker BMap_noprint" unselectable="on" "=""
                    style="position: absolute; padding: 0px; margin: 0px; border: 0px none; -moz-user-select: none;
                    cursor: pointer; background: url(&quot;http://api.map.baidu.com/images/blank.gif&quot;) repeat
                    scroll 0% 0% transparent; width: 70px; height: 57px; left: 476px; top: 374px; z-index:
                    -6230580;"></span><span title="" class="BMap_Marker BMap_noprint" unselectable="on" "=""
                    style="position: absolute; padding: 0px; margin: 0px; border: 0px none; -moz-user-select: none;
                    cursor: pointer; background: url(&quot;http://api.map.baidu.com/images/blank.gif&quot;) repeat
                    scroll 0% 0% transparent; width: 70px; height: 57px; left: 477px; top: 374px; z-index:
                    -6230544;"></span><span title="" class="BMap_Marker BMap_noprint" unselectable="on" "=""
                    style="position: absolute; padding: 0px; margin: 0px; border: 0px none; -moz-user-select: none;
                    cursor: pointer; background: url(&quot;http://api.map.baidu.com/images/blank.gif&quot;) repeat
                    scroll 0% 0% transparent; width: 70px; height: 57px; left: 476px; top: 374px; z-index:
                    -6233070;"></span><span title="" class="BMap_Marker BMap_noprint" unselectable="on" "=""
                    style="position: absolute; padding: 0px; margin: 0px; border: 0px none; -moz-user-select: none;
                    cursor: pointer; background: url(&quot;http://api.map.baidu.com/images/blank.gif&quot;) repeat
                    scroll 0% 0% transparent; width: 70px; height: 57px; left: 481px; top: 372px; z-index:
                    -6257436;"></span><span title="" class="BMap_Marker BMap_noprint" unselectable="on" "=""
                    style="position: absolute; padding: 0px; margin: 0px; border: 0px none; -moz-user-select: none;
                    cursor: pointer; background: url(&quot;http://api.map.baidu.com/images/blank.gif&quot;) repeat
                    scroll 0% 0% transparent; width: 70px; height: 57px; left: 480px; top: 372px; z-index:
                    -6246598;"></span><span title="" class="BMap_Marker BMap_noprint" unselectable="on" "=""
                    style="position: absolute; padding: 0px; margin: 0px; border: 0px none; -moz-user-select: none;
                    cursor: pointer; background: url(&quot;http://api.map.baidu.com/images/blank.gif&quot;) repeat
                    scroll 0% 0% transparent; width: 70px; height: 57px; left: 480px; top: 372px; z-index:
                    -6254452;"></span><span title="" class="BMap_Marker BMap_noprint" unselectable="on" "=""
                    style="position: absolute; padding: 0px; margin: 0px; border: 0px none; -moz-user-select: none;
                    cursor: pointer; background: url(&quot;http://api.map.baidu.com/images/blank.gif&quot;) repeat
                    scroll 0% 0% transparent; width: 70px; height: 57px; left: 420px; top: 383px; z-index:
                    -6113508;"></span><span title="" class="BMap_Marker BMap_noprint" unselectable="on" "=""
                    style="position: absolute; padding: 0px; margin: 0px; border: 0px none; -moz-user-select: none;
                    cursor: pointer; background: url(&quot;http://api.map.baidu.com/images/blank.gif&quot;) repeat
                    scroll 0% 0% transparent; width: 70px; height: 57px; left: 408px; top: 231px; z-index:
                    -7946834;"></span><span title="" class="BMap_Marker BMap_noprint" unselectable="on" "=""
                    style="position: absolute; padding: 0px; margin: 0px; border: 0px none; -moz-user-select: none;
                    cursor: pointer; background: url(&quot;http://api.map.baidu.com/images/blank.gif&quot;) repeat
                    scroll 0% 0% transparent; width: 70px; height: 57px; left: 508px; top: 194px; z-index:
                    -8364052;"></span><span title="" class="BMap_Marker BMap_noprint" unselectable="on" "=""
                    style="position: absolute; padding: 0px; margin: 0px; border: 0px none; -moz-user-select: none;
                    cursor: pointer; background: url(&quot;http://api.map.baidu.com/images/blank.gif&quot;) repeat
                    scroll 0% 0% transparent; width: 70px; height: 57px; left: 411px; top: 228px; z-index:
                    -7980016;"></span><span title="" class="BMap_Marker BMap_noprint" unselectable="on" "=""
                    style="position: absolute; padding: 0px; margin: 0px; border: 0px none; -moz-user-select: none;
                    cursor: pointer; background: url(&quot;http://api.map.baidu.com/images/blank.gif&quot;) repeat
                    scroll 0% 0% transparent; width: 70px; height: 57px; left: 369px; top: 495px; z-index:
                    -4637356;"></span><span title="" class="BMap_Marker BMap_noprint" unselectable="on" "=""
                    style="position: absolute; padding: 0px; margin: 0px; border: 0px none; -moz-user-select: none;
                    cursor: pointer; background: url(&quot;http://api.map.baidu.com/images/blank.gif&quot;) repeat
                    scroll 0% 0% transparent; width: 70px; height: 57px; left: 345px; top: 294px; z-index:
                    -7215426;"></span><span title="" class="BMap_Marker BMap_noprint" unselectable="on" "=""
                    style="position: absolute; padding: 0px; margin: 0px; border: 0px none; -moz-user-select: none;
                    cursor: pointer; background: url(&quot;http://api.map.baidu.com/images/blank.gif&quot;) repeat
                    scroll 0% 0% transparent; width: 70px; height: 57px; left: 412px; top: 226px; z-index:
                    -8000060;"></span></div>
                <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 600;"></div>
                <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 500;"><label
                        style="position: absolute; -moz-user-select: none; display: none; cursor: inherit; background-color: rgb(190, 190, 190); border: 1px solid rgb(190, 190, 190); padding: 1px; white-space: nowrap; font: 12px arial,simsun,sans-serif; z-index: -20000; color: rgb(190, 190, 190);"
                        unselectable="on" class="BMapLabel">shadow</label></div>
                <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 400;"><span
                        class="BMap_Marker" unselectable="on"
                        style="position: absolute; padding: 0px; margin: 0px; border: 0px none; width: 0px; height: 0px; -moz-user-select: none; left: 318px; top: 508px; z-index: -4454388;"><div
                        style="position: absolute; margin: 0px; padding: 0px; width: 70px; height: 57px; overflow: hidden;">
                    <img src="${base}/resources/demo_files/icon_car_off.png" style="border:none;left:0px; top:0px; position:absolute;">
                </div><label
                        style="position: absolute; -moz-user-select: none; display: inline; cursor: inherit; background-color: transparent; border: medium none; padding: 1px; white-space: nowrap; font: 12px arial,simsun,sans-serif; z-index: 80; left: -20px; top: -48px;"
                        unselectable="on" class="BMapLabel">
                    <div class="markLabel"><span class="labelName" title="354188046994203">354188046994203<br>2014-03-09 17:55:47</span>

                        <div class="labelArrow"></div>
                    </div>
                </label></span><span class="BMap_Marker" unselectable="on"
                                     style="position: absolute; padding: 0px; margin: 0px; border: 0px none; width: 0px; height: 0px; -moz-user-select: none; left: 412px; top: 225px; z-index: -8014776;"><div
                        style="position: absolute; margin: 0px; padding: 0px; width: 70px; height: 57px; overflow: hidden;">
                    <img src="${base}/resources/demo_files/icon_car_off.png" style="border:none;left:0px; top:0px; position:absolute;">
                </div><label
                        style="position: absolute; -moz-user-select: none; display: inline; cursor: inherit; background-color: transparent; border: medium none; padding: 1px; white-space: nowrap; font: 12px arial,simsun,sans-serif; z-index: 80; left: -20px; top: -48px;"
                        unselectable="on" class="BMapLabel">
                    <div class="markLabel"><span class="labelName" title="354188046971524">354188046971524<br>2014-03-09 13:57:13</span>

                        <div class="labelArrow"></div>
                    </div>
                </label></span><span class="BMap_Marker" unselectable="on"
                                     style="position: absolute; padding: 0px; margin: 0px; border: 0px none; width: 0px; height: 0px; -moz-user-select: none; left: 479px; top: 372px; z-index: -6256638;"><div
                        style="position: absolute; margin: 0px; padding: 0px; width: 70px; height: 57px; overflow: hidden;">
                    <img src="${base}/resources/demo_files/icon_car.png" style="border:none;left:0px; top:0px; position:absolute;">
                </div><label
                        style="position: absolute; -moz-user-select: none; display: inline; cursor: inherit; background-color: transparent; border: medium none; padding: 1px; white-space: nowrap; font: 12px arial,simsun,sans-serif; z-index: 80; left: -20px; top: -48px;"
                        unselectable="on" class="BMapLabel">
                    <div class="markLabel"><span class="labelName" title="帕萨特旧">帕萨特旧<br>2015-03-08 06:48:00</span>

                        <div class="labelArrow"></div>
                    </div>
                </label></span><span class="BMap_Marker" unselectable="on"
                                     style="position: absolute; padding: 0px; margin: 0px; border: 0px none; width: 0px; height: 0px; -moz-user-select: none; left: 481px; top: 371px; z-index: -6265334;"><div
                        style="position: absolute; margin: 0px; padding: 0px; width: 70px; height: 57px; overflow: hidden;">
                    <img src="${base}/resources/demo_files/icon_car_off.png" style="border:none;left:0px; top:0px; position:absolute;">
                </div><label
                        style="position: absolute; -moz-user-select: none; display: inline; cursor: inherit; background-color: transparent; border: medium none; padding: 1px; white-space: nowrap; font: 12px arial,simsun,sans-serif; z-index: 80; left: -20px; top: -48px;"
                        unselectable="on" class="BMapLabel">
                    <div class="markLabel"><span class="labelName" title="354188046994161">354188046994161<br>2014-03-10 03:45:08</span>

                        <div class="labelArrow"></div>
                    </div>
                </label></span><span class="BMap_Marker" unselectable="on"
                                     style="position: absolute; padding: 0px; margin: 0px; border: 0px none; width: 0px; height: 0px; -moz-user-select: none; left: 480px; top: 378px; z-index: -6181238;"><div
                        style="position: absolute; margin: 0px; padding: 0px; width: 70px; height: 57px; overflow: hidden;">
                    <img src="${base}/resources/demo_files/icon_car_off.png" style="border:none;left:0px; top:0px; position:absolute;">
                </div><label
                        style="position: absolute; -moz-user-select: none; display: inline; cursor: inherit; background-color: transparent; border: medium none; padding: 1px; white-space: nowrap; font: 12px arial,simsun,sans-serif; z-index: 80; left: -20px; top: -48px;"
                        unselectable="on" class="BMapLabel">
                    <div class="markLabel"><span class="labelName" title="福特SUV">福特SUV<br>2014-03-09 22:25:30</span>

                        <div class="labelArrow"></div>
                    </div>
                </label></span><span class="BMap_Marker" unselectable="on"
                                     style="position: absolute; padding: 0px; margin: 0px; border: 0px none; width: 0px; height: 0px; -moz-user-select: none; left: 476px; top: 374px; z-index: -6230580;"><div
                        style="position: absolute; margin: 0px; padding: 0px; width: 70px; height: 57px; overflow: hidden;">
                    <img src="${base}/resources/demo_files/icon_car_off.png" style="border:none;left:0px; top:0px; position:absolute;">
                </div><label
                        style="position: absolute; -moz-user-select: none; display: inline; cursor: inherit; background-color: transparent; border: medium none; padding: 1px; white-space: nowrap; font: 12px arial,simsun,sans-serif; z-index: 80; left: -20px; top: -48px;"
                        unselectable="on" class="BMapLabel">
                    <div class="markLabel"><span class="labelName" title="路虎">路虎<br>2014-03-09 20:45:47</span>

                        <div class="labelArrow"></div>
                    </div>
                </label></span><span class="BMap_Marker" unselectable="on"
                                     style="position: absolute; padding: 0px; margin: 0px; border: 0px none; width: 0px; height: 0px; -moz-user-select: none; left: 477px; top: 374px; z-index: -6230544;"><div
                        style="position: absolute; margin: 0px; padding: 0px; width: 70px; height: 57px; overflow: hidden;">
                    <img src="${base}/resources/demo_files/icon_car_off.png" style="border:none;left:0px; top:0px; position:absolute;">
                </div><label
                        style="position: absolute; -moz-user-select: none; display: inline; cursor: inherit; background-color: transparent; border: medium none; padding: 1px; white-space: nowrap; font: 12px arial,simsun,sans-serif; z-index: 80; left: -20px; top: -48px;"
                        unselectable="on" class="BMapLabel">
                    <div class="markLabel"><span class="labelName" title="君越">君越<br>2014-02-10 16:34:43</span>

                        <div class="labelArrow"></div>
                    </div>
                </label></span><span class="BMap_Marker" unselectable="on"
                                     style="position: absolute; padding: 0px; margin: 0px; border: 0px none; width: 0px; height: 0px; -moz-user-select: none; left: 476px; top: 374px; z-index: -6233070;"><div
                        style="position: absolute; margin: 0px; padding: 0px; width: 70px; height: 57px; overflow: hidden;">
                    <img src="${base}/resources/demo_files/icon_car_off.png" style="border:none;left:0px; top:0px; position:absolute;">
                </div><label
                        style="position: absolute; -moz-user-select: none; display: inline; cursor: inherit; background-color: transparent; border: medium none; padding: 1px; white-space: nowrap; font: 12px arial,simsun,sans-serif; z-index: 80; left: -20px; top: -48px;"
                        unselectable="on" class="BMapLabel">
                    <div class="markLabel"><span class="labelName" title="别克GL8">别克GL8<br>2014-03-09 14:52:16</span>

                        <div class="labelArrow"></div>
                    </div>
                </label></span><span class="BMap_Marker" unselectable="on"
                                     style="position: absolute; padding: 0px; margin: 0px; border: 0px none; width: 0px; height: 0px; -moz-user-select: none; left: 481px; top: 372px; z-index: -6257436;"><div
                        style="position: absolute; margin: 0px; padding: 0px; width: 70px; height: 57px; overflow: hidden;">
                    <img src="${base}/resources/demo_files/icon_car_off.png" style="border:none;left:0px; top:0px; position:absolute;">
                </div><label
                        style="position: absolute; -moz-user-select: none; display: inline; cursor: inherit; background-color: transparent; border: medium none; padding: 1px; white-space: nowrap; font: 12px arial,simsun,sans-serif; z-index: 80; left: -20px; top: -48px;"
                        unselectable="on" class="BMapLabel">
                    <div class="markLabel"><span class="labelName" title="马六">马六<br>2014-03-09 18:27:55</span>

                        <div class="labelArrow"></div>
                    </div>
                </label></span><span class="BMap_Marker" unselectable="on"
                                     style="position: absolute; padding: 0px; margin: 0px; border: 0px none; width: 0px; height: 0px; -moz-user-select: none; left: 480px; top: 372px; z-index: -6246598;"><div
                        style="position: absolute; margin: 0px; padding: 0px; width: 70px; height: 57px; overflow: hidden;">
                    <img src="${base}/resources/demo_files/icon_car_off.png" style="border:none;left:0px; top:0px; position:absolute;">
                </div><label
                        style="position: absolute; -moz-user-select: none; display: inline; cursor: inherit; background-color: transparent; border: medium none; padding: 1px; white-space: nowrap; font: 12px arial,simsun,sans-serif; z-index: 80; left: -20px; top: -48px;"
                        unselectable="on" class="BMapLabel">
                    <div class="markLabel"><span class="labelName" title="qqqq">qqqq<br>2014-02-11 21:07:03</span>

                        <div class="labelArrow"></div>
                    </div>
                </label></span><span class="BMap_Marker" unselectable="on"
                                     style="position: absolute; padding: 0px; margin: 0px; border: 0px none; width: 0px; height: 0px; -moz-user-select: none; left: 480px; top: 372px; z-index: -6254452;"><div
                        style="position: absolute; margin: 0px; padding: 0px; width: 70px; height: 57px; overflow: hidden;">
                    <img src="${base}/resources/demo_files/icon_car_off.png" style="border:none;left:0px; top:0px; position:absolute;">
                </div><label
                        style="position: absolute; -moz-user-select: none; display: inline; cursor: inherit; background-color: transparent; border: medium none; padding: 1px; white-space: nowrap; font: 12px arial,simsun,sans-serif; z-index: 80; left: -20px; top: -48px;"
                        unselectable="on" class="BMapLabel">
                    <div class="markLabel"><span class="labelName" title="张">张<br>2014-03-04 15:42:42</span>

                        <div class="labelArrow"></div>
                    </div>
                </label></span><span class="BMap_Marker" unselectable="on"
                                     style="position: absolute; padding: 0px; margin: 0px; border: 0px none; width: 0px; height: 0px; -moz-user-select: none; left: 420px; top: 383px; z-index: -6113508;"><div
                        style="position: absolute; margin: 0px; padding: 0px; width: 70px; height: 57px; overflow: hidden;">
                    <img src="${base}/resources/demo_files/icon_car_off.png" style="border:none;left:0px; top:0px; position:absolute;">
                </div><label
                        style="position: absolute; -moz-user-select: none; display: inline; cursor: inherit; background-color: transparent; border: medium none; padding: 1px; white-space: nowrap; font: 12px arial,simsun,sans-serif; z-index: 80; left: -20px; top: -48px;"
                        unselectable="on" class="BMapLabel">
                    <div class="markLabel"><span class="labelName" title="354188046994765">354188046994765<br>2014-01-29 15:27:56</span>

                        <div class="labelArrow"></div>
                    </div>
                </label></span><span class="BMap_Marker" unselectable="on"
                                     style="position: absolute; padding: 0px; margin: 0px; border: 0px none; width: 0px; height: 0px; -moz-user-select: none; left: 408px; top: 231px; z-index: -7946834;"><div
                        style="position: absolute; margin: 0px; padding: 0px; width: 70px; height: 57px; overflow: hidden;">
                    <img src="${base}/resources/demo_files/icon_car_off.png" style="border:none;left:0px; top:0px; position:absolute;">
                </div><label
                        style="position: absolute; -moz-user-select: none; display: inline; cursor: inherit; background-color: transparent; border: medium none; padding: 1px; white-space: nowrap; font: 12px arial,simsun,sans-serif; z-index: 80; left: -20px; top: -48px;"
                        unselectable="on" class="BMapLabel">
                    <div class="markLabel"><span class="labelName" title="354188046984410">354188046984410<br>2014-03-04 02:51:26</span>

                        <div class="labelArrow"></div>
                    </div>
                </label></span><span class="BMap_Marker" unselectable="on"
                                     style="position: absolute; padding: 0px; margin: 0px; border: 0px none; width: 0px; height: 0px; -moz-user-select: none; left: 508px; top: 194px; z-index: -8364052;"><div
                        style="position: absolute; margin: 0px; padding: 0px; width: 70px; height: 57px; overflow: hidden;">
                    <img src="${base}/resources/demo_files/icon_car_off.png" style="border:none;left:0px; top:0px; position:absolute;">
                </div><label
                        style="position: absolute; -moz-user-select: none; display: inline; cursor: inherit; background-color: transparent; border: medium none; padding: 1px; white-space: nowrap; font: 12px arial,simsun,sans-serif; z-index: 80; left: -20px; top: -48px;"
                        unselectable="on" class="BMapLabel">
                    <div class="markLabel"><span class="labelName" title="354188046994963">354188046994963<br>2014-03-09 20:51:03</span>

                        <div class="labelArrow"></div>
                    </div>
                </label></span><span class="BMap_Marker" unselectable="on"
                                     style="position: absolute; padding: 0px; margin: 0px; border: 0px none; width: 0px; height: 0px; -moz-user-select: none; left: 411px; top: 228px; z-index: -7980016;"><div
                        style="position: absolute; margin: 0px; padding: 0px; width: 70px; height: 57px; overflow: hidden;">
                    <img src="${base}/resources/demo_files/icon_car_off.png" style="border:none;left:0px; top:0px; position:absolute;">
                </div><label
                        style="position: absolute; -moz-user-select: none; display: inline; cursor: inherit; background-color: transparent; border: medium none; padding: 1px; white-space: nowrap; font: 12px arial,simsun,sans-serif; z-index: 80; left: -20px; top: -48px;"
                        unselectable="on" class="BMapLabel">
                    <div class="markLabel"><span class="labelName" title="354188046995127">354188046995127<br>2014-01-01 17:13:41</span>

                        <div class="labelArrow"></div>
                    </div>
                </label></span><span class="BMap_Marker" unselectable="on"
                                     style="position: absolute; padding: 0px; margin: 0px; border: 0px none; width: 0px; height: 0px; -moz-user-select: none; left: 369px; top: 495px; z-index: -4637356;"><div
                        style="position: absolute; margin: 0px; padding: 0px; width: 70px; height: 57px; overflow: hidden;">
                    <img src="${base}/resources/demo_files/icon_car_off.png" style="border:none;left:0px; top:0px; position:absolute;">
                </div><label
                        style="position: absolute; -moz-user-select: none; display: inline; cursor: inherit; background-color: transparent; border: medium none; padding: 1px; white-space: nowrap; font: 12px arial,simsun,sans-serif; z-index: 80; left: -20px; top: -48px;"
                        unselectable="on" class="BMapLabel">
                    <div class="markLabel"><span class="labelName" title="奥迪Ａ6Ｌ">奥迪Ａ6Ｌ<br>2014-03-09 18:04:18</span>

                        <div class="labelArrow"></div>
                    </div>
                </label></span><span class="BMap_Marker" unselectable="on"
                                     style="position: absolute; padding: 0px; margin: 0px; border: 0px none; width: 0px; height: 0px; -moz-user-select: none; left: 345px; top: 294px; z-index: -7215426;"><div
                        style="position: absolute; margin: 0px; padding: 0px; width: 70px; height: 57px; overflow: hidden;">
                    <img src="${base}/resources/demo_files/icon_car_off.png" style="border:none;left:0px; top:0px; position:absolute;">
                </div><label
                        style="position: absolute; -moz-user-select: none; display: inline; cursor: inherit; background-color: transparent; border: medium none; padding: 1px; white-space: nowrap; font: 12px arial,simsun,sans-serif; z-index: 80; left: -20px; top: -48px;"
                        unselectable="on" class="BMapLabel">
                    <div class="markLabel"><span class="labelName" title="麻豆爸爸">麻豆爸爸<br>2014-01-14 18:51:21</span>

                        <div class="labelArrow"></div>
                    </div>
                </label></span><span class="BMap_Marker" unselectable="on"
                                     style="position: absolute; padding: 0px; margin: 0px; border: 0px none; width: 0px; height: 0px; -moz-user-select: none; left: 412px; top: 226px; z-index: -8000060;"><div
                        style="position: absolute; margin: 0px; padding: 0px; width: 70px; height: 57px; overflow: hidden;">
                    <img src="${base}/resources/demo_files/icon_car_off.png" style="border:none;left:0px; top:0px; position:absolute;">
                </div><label
                        style="position: absolute; -moz-user-select: none; display: inline; cursor: inherit; background-color: transparent; border: medium none; padding: 1px; white-space: nowrap; font: 12px arial,simsun,sans-serif; z-index: 80; left: -20px; top: -48px;"
                        unselectable="on" class="BMapLabel">
                    <div class="markLabel"><span class="labelName" title="354188046985268">354188046985268<br>2014-01-08 15:31:23</span>

                        <div class="labelArrow"></div>
                    </div>
                </label></span></div>
                <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 300;"></div>
                <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 201;"></div>
                <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 200;"></div>
            </div>
            <div style="position: absolute; overflow: visible; top: 0px; left: 0px; z-index: 1;">
                <div style="position: absolute; overflow: visible; z-index: -100; left: 448px; top: 339px;"><img
                        src="${base}/resources/demo_files/a_009.png"
                        style="-moz-user-select: none; position: absolute; border: medium none; width: 256px; height: 256px; left: -303px; top: -6px; max-width: none; opacity: 1;"><img
                        src="${base}/resources/demo_files/a_014.png"
                        style="-moz-user-select: none; position: absolute; border: medium none; width: 256px; height: 256px; left: -559px; top: -262px; max-width: none; opacity: 1;"><img
                        src="${base}/resources/demo_files/a_012.png"
                        style="-moz-user-select: none; position: absolute; border: medium none; width: 256px; height: 256px; left: -303px; top: 250px; max-width: none; opacity: 1;"><img
                        src="${base}/resources/demo_files/a_005.png"
                        style="-moz-user-select: none; position: absolute; border: medium none; width: 256px; height: 256px; left: 209px; top: -262px; max-width: none; opacity: 1;"><img
                        src="${base}/resources/demo_files/a_015.png"
                        style="-moz-user-select: none; position: absolute; border: medium none; width: 256px; height: 256px; left: -559px; top: -518px; max-width: none; opacity: 1;"><img
                        src="${base}/resources/demo_files/a_011.png"
                        style="-moz-user-select: none; position: absolute; border: medium none; width: 256px; height: 256px; left: -47px; top: -518px; max-width: none; opacity: 1;"><img
                        src="${base}/resources/demo_files/a_007.png"
                        style="-moz-user-select: none; position: absolute; border: medium none; width: 256px; height: 256px; left: 209px; top: 250px; max-width: none; opacity: 1;"><img
                        src="${base}/resources/demo_files/a_004.png"
                        style="-moz-user-select: none; position: absolute; border: medium none; width: 256px; height: 256px; left: 209px; top: -518px; max-width: none; opacity: 1;"><img
                        src="${base}/resources/demo_files/a.png"
                        style="-moz-user-select: none; position: absolute; border: medium none; width: 256px; height: 256px; left: -47px; top: 250px; max-width: none; opacity: 1;"><img
                        src="${base}/resources/demo_files/a_016.png"
                        style="-moz-user-select: none; position: absolute; border: medium none; width: 256px; height: 256px; left: -559px; top: 250px; max-width: none; opacity: 1;"><img
                        src="${base}/resources/demo_files/a_002.png"
                        style="-moz-user-select: none; position: absolute; border: medium none; width: 256px; height: 256px; left: -303px; top: -262px; max-width: none; opacity: 1;"><img
                        src="${base}/resources/demo_files/a_008.png"
                        style="-moz-user-select: none; position: absolute; border: medium none; width: 256px; height: 256px; left: -47px; top: -6px; max-width: none; opacity: 1;"><img
                        src="${base}/resources/demo_files/a_003.png"
                        style="-moz-user-select: none; position: absolute; border: medium none; width: 256px; height: 256px; left: -47px; top: -262px; max-width: none; opacity: 1;"><img
                        src="${base}/resources/demo_files/a_010.png"
                        style="-moz-user-select: none; position: absolute; border: medium none; width: 256px; height: 256px; left: 209px; top: -6px; max-width: none; opacity: 1;"><img
                        src="${base}/resources/demo_files/a_013.png"
                        style="-moz-user-select: none; position: absolute; border: medium none; width: 256px; height: 256px; left: -303px; top: -518px; max-width: none; opacity: 1;">
                </div>
            </div>
            <div style="position: absolute; overflow: visible; top: 0px; left: 0px; z-index: 2;"></div>
        </div>
        <div class=" anchorBL"
             style="height: 32px; position: absolute; z-index: 10; -moz-user-select: none; bottom: 0px; right: auto; top: auto; left: 1px;">
            <a style="outline: medium none;" href="http://map.baidu.com/?sr=1" target="_blank" title="到百度地图查看此区域"><img
                    style="border:none;width:77px;height:32px" src="${base}/resources/demo_files/copyright_logo.png"></a></div>
        <div id="zoomer"
             style="position:absolute;z-index:0;top:0px;left:0px;overflow:hidden;visibility:hidden;cursor:-moz-grab">
            <div class="BMap_zoomer" style="top:0;left:0;"></div>
            <div class="BMap_zoomer" style="top:0;right:0;"></div>
            <div class="BMap_zoomer" style="bottom:0;left:0;"></div>
            <div class="BMap_zoomer" style="bottom:0;right:0;"></div>
        </div>
        <div style="width: 62px; height: 192px; bottom: auto; right: auto; top: 10px; left: 10px; position: absolute; z-index: 1100; -moz-user-select: none;"
             class=" BMap_stdMpCtrl BMap_stdMpType0 BMap_noprint anchorTL" unselectable="on">
            <div class="BMap_stdMpPan">
                <div class="BMap_button BMap_panN" title="向上平移"></div>
                <div class="BMap_button BMap_panW" title="向左平移"></div>
                <div class="BMap_button BMap_panE" title="向右平移"></div>
                <div class="BMap_button BMap_panS" title="向下平移"></div>
                <div class="BMap_stdMpPanBg BMap_smcbg"></div>
            </div>
            <div style="height: 147px; width: 62px;" class="BMap_stdMpZoom">
                <div class="BMap_button BMap_stdMpZoomIn" title="放大一级">
                    <div class="BMap_smcbg"></div>
                </div>
                <div style="top: 126px;" class="BMap_button BMap_stdMpZoomOut" title="缩小一级">
                    <div class="BMap_smcbg"></div>
                </div>
                <div style="height: 108px;" class="BMap_stdMpSlider">
                    <div style="height: 108px;" class="BMap_stdMpSliderBgTop">
                        <div class="BMap_smcbg"></div>
                    </div>
                    <div style="top: 85px; height: 27px;" class="BMap_stdMpSliderBgBot"></div>
                    <div class="BMap_stdMpSliderMask" title="放置到此级别"></div>
                    <div style="cursor: grab; top: 85px;" class="BMap_stdMpSliderBar" title="拖动缩放">
                        <div class="BMap_smcbg"></div>
                    </div>
                </div>
                <div class="BMap_zlHolder">
                    <div class="BMap_zlSt">
                        <div class="BMap_smcbg"></div>
                    </div>
                    <div class="BMap_zlCity">
                        <div class="BMap_smcbg"></div>
                    </div>
                    <div class="BMap_zlProv">
                        <div class="BMap_smcbg"></div>
                    </div>
                    <div class="BMap_zlCountry">
                        <div class="BMap_smcbg"></div>
                    </div>
                </div>
            </div>
        </div>
        <div style="bottom: 18px; right: auto; top: auto; left: 81px; width: 80px; position: absolute; z-index: 10; -moz-user-select: none;"
             class=" BMap_scaleCtrl BMap_noprint anchorBL" unselectable="on">
            <div style="background-color: transparent; color: black;" class="BMap_scaleTxt" unselectable="on">500&nbsp;公里</div>
            <div style="background-color: black;" class="BMap_scaleBar BMap_scaleHBar"><img style="border:none"
                                                                                            src="${base}/resources/demo_files/mapctrls.png">
            </div>
            <div style="background-color: black;" class="BMap_scaleBar BMap_scaleLBar"><img style="border:none"
                                                                                            src="${base}/resources/demo_files/mapctrls.png">
            </div>
            <div style="background-color: black;" class="BMap_scaleBar BMap_scaleRBar"><img style="border:none"
                                                                                            src="${base}/resources/demo_files/mapctrls.png">
            </div>
        </div>
        <div style="width: 150px; height: 150px; bottom: 0px; right: 0px; top: auto; left: auto; position: absolute; z-index: 10; -moz-user-select: none;"
             class=" BMap_omCtrl BMap_ieundefined BMap_noprint anchorBR quad4" unselectable="on">
            <div style="width: 149px; height: 149px;" class="BMap_omOutFrame">
                <div style="bottom: auto; right: auto; top: 5px; left: 5px; width: 142px; height: 142px;"
                     class="BMap_omInnFrame">
                    <div style="overflow: hidden; background-color: rgb(243, 241, 236); color: rgb(0, 0, 0); text-align: left;"
                         class="BMap_omMapContainer">
                        <div style="overflow: visible; position: absolute; z-index: 0; left: 0px; top: 0px; cursor: grab;">
                            <div style="position: absolute; left: 0px; top: 0px; z-index: 9; overflow: hidden; width: 142px; height: 142px;"
                                 class="BMap_mask"></div>
                            <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 200;">
                                <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 800;"></div>
                                <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 700;">
                                    <div class="BMap_Division"
                                         style="position: absolute; width: 0px; display: block; overflow: hidden; border-width: 1px; border-style: solid; border-color: rgb(173, 207, 244) rgb(39, 75, 139) rgb(39, 75, 139) rgb(132, 176, 223); -moz-border-top-colors: none; -moz-border-right-colors: none; -moz-border-bottom-colors: none; -moz-border-left-colors: none; border-image: none; opacity: 1; z-index: 60; left: 70px; top: 70px;">
                                        <div class="BMap_omViewInnFrame">
                                            <div class="BMap_omViewMask"></div>
                                        </div>
                                    </div>
                                </div>
                                <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 600;"></div>
                                <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 500;">
                                    <label style="position: absolute; -moz-user-select: none; display: none; cursor: inherit; background-color: rgb(190, 190, 190); border: 1px solid rgb(190, 190, 190); padding: 1px; white-space: nowrap; font: 12px arial,simsun,sans-serif; z-index: -20000; color: rgb(190, 190, 190);"
                                           unselectable="on" class="BMapLabel">shadow</label></div>
                                <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 400;"></div>
                                <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 300;"></div>
                                <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 201;"></div>
                                <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 200;"></div>
                            </div>
                            <div style="position: absolute; overflow: visible; top: 0px; left: 0px; z-index: 1;">
                                <div style="position: absolute; overflow: visible; z-index: -100; left: 71px; top: 71px;">
                                    <img src="${base}/resources/demo_files/a_006.png"
                                         style="-moz-user-select: none; position: absolute; border: medium none; width: 256px; height: 256px; left: -139px; top: -129px; max-width: none; opacity: 1;">
                                </div>
                            </div>
                            <div style="position: absolute; overflow: visible; top: 0px; left: 0px; z-index: 2;"></div>
                        </div>
                    </div>
                    <div style="cursor: grab; display: none;" class="BMap_omViewMv">
                        <div class="BMap_omViewInnFrame">
                            <div></div>
                        </div>
                    </div>
                </div>
            </div>
            <div style="bottom: 0px; right: 0px; top: auto; left: auto;" class="BMap_omBtn"></div>
        </div>
        <div style="cursor: default; white-space: nowrap; -moz-user-select: none; color: black; background: none repeat scroll 0% 0% transparent; font: 11px/15px arial,simsun,sans-serif; bottom: 2px; right: auto; top: auto; left: 81px; position: absolute; z-index: 10;"
             class=" BMap_cpyCtrl BMap_noprint anchorBL" unselectable="on"><span style="display: inline;" _cid="1"><span
                style="font-size:11px">? 2014 Baidu&nbsp;- Data ? <a target="_blank" href="http://www.navinfo.com/"
                                                                     style="display:inline;">NavInfo</a> &amp; <a
                target="_blank" href="http://www.cennavi.com.cn/" style="display:inline;">CenNavi</a> &amp; <a
                target="_blank" href="http://www.365ditu.com/" style="display:inline;">道道通</a></span></span></div>
    </div>
    <div id="analyse">
    </div>
</div>
<span class="clear"></span>
</div>
</div>
</div>
<p style="display: block;" id="footer">
    版权所有?
</p>
</div>

<div style="width: 4px; z-index: auto; cursor: default; position: absolute; top: 156px; left: 379px; height: 677px; opacity: 0;"
     class="nicescroll-rails" id="ascrail2000">
    <div style="position: relative; top: 0px; float: right; width: 2px; height: 165px; background-color: rgb(99, 189, 249); border: 1px solid rgb(255, 255, 255); background-clip: padding-box; border-radius: 0px;"></div>
</div>
</body>
</html>