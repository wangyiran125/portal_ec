<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.main.statisticsNav")}</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/css/jquery.jqplot.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.jqplot.min.js"></script>
<script type="text/javascript" src="${base}/resources/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/js/jqplot.logAxisRenderer.min.js"></script>
<script type="text/javascript" src="${base}/resources/js/jqplot.canvasTextRenderer.min.js"></script>
<script type="text/javascript" src="${base}/resources/js/jqplot.canvasAxisLabelRenderer.min.js"></script>
<script type="text/javascript" src="${base}/resources/js/jqplot.cursor.min.js"></script>
<script type="text/javascript" src="${base}/resources/js/jqplot.dateAxisRenderer.min.js"></script>
<script type="text/javascript" src="${base}/resources/js/jqplot.canvasAxisTickRenderer.min.js"></script>
<script type="text/javascript" src="${base}/resources/js/jqplot.json2.min.js"></script>
<script type="text/javascript" src="${base}/resources/js/excanvas.min.js"></script>
<script type="text/javascript" src="${base}/resources/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/js/date.js"></script>
<style type="text/css">
	html{
		padding-left: 20px;
		padding-right: 15px;
		overflow-y: auto;
}
.chartContent {
	margin:10px;
	padding:10px;
}
#space {
	margin:50px;
}
</style>
<script type="text/javascript">
var installDeviceJson = new Array();
var installTotalDeviceJson = new Array();
var dateArray = getXDate();
$().ready(function() {
	loadData();
	$("#installTotalDeviceTab").click(function(){
		 drawChart('installTotalDeviceChart',installTotalDeviceJson,'${message("admin.device.installTotalDevice")}');
	});
	
});
	var loadData = function () {
		 var jsonurl = "equipment/chart.jhtml";
	      $.ajax({
	            async: false,
	            type: "POST",
	            url: jsonurl,
	            dataType: "json",
	            success: function (data) {
	                //构造数组
	                for (var i = 0; i < data.length; i++) {
	                    var chObj = data[i];
	                    installDeviceJson.push([chObj.fStatDate,chObj.fInstallDevice]);
	                    installTotalDeviceJson.push([chObj.fStatDate,chObj.fInstallTotalDevice]);
	                }
	             	drawChart('installDeviceChart',installDeviceJson,'${message("admin.device.installDevice")}');
	            }
	        });
	};
	
	var drawChart = function(chart,data,labelInfo){
	  var plot = $.jqplot(chart, [data], {
	  cursor:{
	  	style:'hand',
	  	show:true,
	  	showTooltip: true, // 是否显示提示信息栏 
		followMouse: true, //光标的提示信息栏是否随光标（鼠标）一起移动 
		showTooltipUnitPosition: true,// 是否显示提示光标所在位置
		tooltipLocation:'nw', //提示栏相对鼠标位置
	  },
      axes: {
        xaxis: {
          renderer: $.jqplot.DateAxisRenderer,
          tickRenderer: $.jqplot.CanvasAxisTickRenderer,
           ticks:dateArray,
          tickOptions: {formatString: "%Y-%m-%d"},
        },
        yaxis: {
        	min:0,
         	label: labelInfo,
            labelRenderer: $.jqplot.CanvasAxisLabelRenderer
        }
      }
    });
}

</script>
</head>
<body>
	<div class="path">
	   ${message("common.current.position")}：${message("admin.main.statisticsNav")} &raquo; ${message("admin.main.statisticsEquipment")}
	</div>
		<form id="listForm" action="equipment.jhtml" method="get">
			<div class="bar">
		
					<a href="javascript:;" id="refreshButtonFirst" class="iconButton">
						<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
					</a>
					<div class="menuWrap">
						<a href="javascript:;" id="pageSizeSelect" class="button">
							${message("admin.page.pageSize")}<span class="arrow">&nbsp;</span>
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
			<table id="listTable" class="list">
				<tr>
					<th>
					<a href="javascript:;" class="sort" name="fStatDate">${message("admin.statistics.statictiscDate")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="fInstallDevice">${message("statistics.device.installDevice")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="fNewInstallRate">${message("statistics.device.newInstallRate")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="fInstallTotalDevice">${message("statistics.device.installTotalDevice")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="fInstallRate">${message("statistics.device.installRate")}</a>
				</th>
				</tr>
				[#list page.content as deviceStatistics]
				<tr>
					<td>
						${deviceStatistics.fStatDate?string("yyyy-MM-dd")}
					</td>
					<td>
						${deviceStatistics.fInstallDevice}
					</td>
					<td>
						${deviceStatistics.fNewInstallRate*100}%
					</td>
					<td>
						${deviceStatistics.fInstallTotalDevice}
					</td>
					<td>
						${deviceStatistics.fInstallRate*100}%
					</td>
				</tr>
				[/#list]
			</table>
			[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
			[#include "/console/include/pagination.ftl"]
		    [/@pagination]
		    </form>
			<div id="space"></div>		   
		<form id="inputForm">
			<ul id="tab" class="tab">
				<li>
					<input type="button" value="${message("admin.device.installDevice")}" id="installDeviceTab"/>
				</li>
				<li>
					<input type="button" value="${message("admin.device.installTotalDevice")}" id="installTotalDeviceTab"/>
				</li>
				
			</ul>
			<table class="input tabContent">
				<tr><td>
					<div class="chartContent">
		        		<div class="col1" id="example-content">
		        			<div id="installDeviceChart" class="example-chart" style="height:380px;width:1000px"></div>
		   	     		</div>
	       			</div>
				</td></tr>
			</table>
			<table class="input tabContent">
					<tr><td>
							<div class="chartContent">
		        				<div class="col1" id="example-content">
		        					<div id="installTotalDeviceChart" class="example-chart" style="height:380px;width:1000px"></div>
		   	     				</div>
	       					 </div>
					</td></tr>
			</table>
		</form>
	</body>
</html>
