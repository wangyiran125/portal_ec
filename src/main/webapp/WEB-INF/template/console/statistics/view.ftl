<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.main.statisticsNav")}</title>
<link href="${base}/resources/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/css/jquery.jqplot.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/excanvas.min.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.jqplot.min.js"></script>
<script type="text/javascript" src="${base}/resources/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/js/jqplot.categoryAxisRenderer.min.js"></script>
<script type="text/javascript" src="${base}/resources/js/jqplot.barRenderer.min.js"></script>
<script type="text/javascript" src="${base}/resources/js/jqplot.pointLabels.min.js"></script>
<style type="text/css">
	html{
		padding-left: 20px;
		padding-right: 15px;
	}
#chart { 
	MARGIN: auto;
}
#overview {
	MARGIN-top:50px;
	
}
</style>
<script type="text/javascript">
$().ready(function() {

    $('#chart').jqplot([${overview}], {
        title:{
        	text:'${message("admin.statistics.overview")}',
        	textColor:'#0066FF',
        },
        seriesDefaults:{
            renderer:$.jqplot.BarRenderer,
            rendererOptions: {
                varyBarColor: true,
                barMargin: 40   //柱状体组之间间隔
            },
            pointLabels: {show: true}
        },
        axes:{
            xaxis:{
            	renderer: $.jqplot.CategoryAxisRenderer,
            	ticks:['${message("admin.overview.userAcounts")}', '${message("admin.overview.equipmentAcounts")}']
            },
            yaxis:{
            	min:0
             }
        }
    });
});
</script>
</head>
<body>
	<div class="path">
		${message("common.current.position")}：${message("admin.main.statisticsNav")} &raquo; ${message("admin.main.statisticsView")}
	</div>
		
	<div id="overview">
		 <div class="colmask leftmenu">
			<div class="colleft">
		        <div class="col1" id="example-content">
		        	<div id="chart" class="example-chart" style="height:550px;width:420px"></div>
		        </div>
	        </div>
		</div>
	</div>
</body>
</html>