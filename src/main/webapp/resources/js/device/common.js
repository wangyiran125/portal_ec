var cLib = { };  //定义公共库全局变量
//全局变量
var map = {}, //地图
    ajaxDic = {}, //搜索ajax字典
    allDevices = [], //设备sn数组
    ajaxLib = {};


function createMarkIcon(options) {
    var markIcon = null,
        mark = null;
    if(options.start){
        markIcon = new BMap.Icon("../../resources/images/icon_begin.png", new BMap.Size(70, 90), { imageOffset: new BMap.Size(0, 0)});
    }else if(options.end){
        markIcon = new BMap.Icon("../../resources/images/icon_end.png", new BMap.Size(70, 90), { imageOffset: new BMap.Size(0, 0)});
    }else{
        markIcon = new BMap.Icon("../../resources/images/icon_car_alert.png", new BMap.Size(70, 90), { imageOffset: new BMap.Size(0, 0)});
    }
    mark = new BMap.Marker(options.point, {icon: markIcon});
    var label = new BMap.Label(
            '<div class="markLabel"><span class="labelName" title="'+options.deviceSn+ '">'+options.deviceSn+ '<br />' +cLib.base.longToDate(options.receive)+'</span><div class="labelArrow"></div></div>',
        {offset: new BMap.Size(-22, -50)}
    );
    label.setStyle({
        'backgroundColor': 'transparent',
        'border': 'none'
    });
    if (mark) {
        mark.setLabel(label);
        mark.lableInstance = label;
    }
    return mark;
}
function ajaxCallback(keys, cb) {
    var flag = true;
    for (var i = 0; i < keys.length; i++) {
        if (!ajaxLib[keys[i]]) {
            flag = false;
            break;
        } else if (!ajaxLib[keys[i]].status) {
            flag = false;
            break;
        } else {
            ajaxLib[keys[i]] = null;
        }
    }
    if (flag) {
        cb();
    } else {
        setTimeout(function () {
            ajaxCallback(keys, cb);
        }, 500);
    }
    return flag;
}


(function (lib) {
    base = {
        //浏览器判定
        browVer: typeof($) != 'undefined' ? $.support.version : '',
        isMozilla: (typeof document.implementation != 'undefined') && (typeof document.implementation.createDocument != 'undefined') && (typeof HTMLDocument != 'undefined'),
        isIE: window.ActiveXObject ? true : false,
        isFirefox: (navigator.userAgent.toLowerCase().indexOf("firefox") != -1),
        isSafari: (navigator.userAgent.toLowerCase().indexOf("safari") != -1),
        isOpera: (navigator.userAgent.toLowerCase().indexOf("opera") != -1),
        isSupportJquery: typeof($) != 'undefined' && $ != null,
        //设置Cookie变量
        //@param    c_name        String    cookie名称
        //@param    value         String    cookie值
        //@param    expiredays    Int       过期时间(天)
        //@param    path          String    cookie路径
        setCookie: function (c_name, value, expiredays, path) {
            var exdate = new Date();
            exdate.setDate(exdate.getDate() + expiredays);
            document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString()) + ((path == null) ? "" : ";path=" + path);
        },

        //获取Cookie变量
        //@param    c_name    String    cookie名
        getCookie: function (c_name) {
            if (document.cookie.length > 0) {
                var c_start = document.cookie.indexOf(c_name + "=");
                if (c_start != -1) {
                    c_start = c_start + c_name.length + 1;
                    var c_end = document.cookie.indexOf(";", c_start);
                    if (c_end == -1) {
                        c_end = document.cookie.length;
                    }
                    return unescape(document.cookie.substring(c_start, c_end));
                }
            }
            return "";
        },
        //获取URL参数
        //@param    name    String    参数名
        getQueryString: function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = (window.location.search || window.location.hash).substr(1).match(reg);
            if (r != null) return decodeURI(r[2]);
            return null;
        },
        //清除字符串左右空格字符
        //@param    text    String
        trim: function (text) {
            if (typeof(text) == "string") {
                return text.replace(/^\s*|\s*$/g, "");
            } else {
                return text;
            }
        },
        //判断值是否为空
        //@param    text    String || object || array
        isEmpty: function (val) {
            switch (typeof(val)) {
                case 'string':
                    return val.replace(/^\s\s*/, '').replace(/\s\s*$/, '').length == 0 ? true : false;
                case 'object':
                    return val == null;
                case 'array':
                    return val.length == 0;
                default:
                    return true;
            }
        },
        weekday: {
            0: '日',
            1: '一',
            2: '二',
            3: '三',
            4: '四',
            5: '五',
            6: '六'
        },
        //增加天数后的日期
        addDays: function (date, days) {
            var nd = new Date(date);
            nd = nd.valueOf();
            nd = nd + days * 24 * 60 * 60 * 1000;
            nd = new Date(nd);
            return nd;
        },
        //减少天数后的日期
        subDays: function (date, days) {
            var nd = new Date(date);
            nd = nd.valueOf();
            nd = nd - days * 24 * 60 * 60 * 1000;
            nd = new Date(nd);
            return nd;
        },
        isEndDay: function (date) {
            if (typeof date == 'string') date = new Date(date.replace('-', '/').replace('-', '/'));
            var hh = date.getHours() < 10 ? '0' + date.getHours() : date.getHours(),
                mm = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes(),
                ss = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();
            if (hh == '23' && mm == '59' && ss == '59') return true;
            if (hh == '00' && mm == '00' && ss == '00') return true;
            return false;
        },
        formatDate: function (date, dateFormat, blurDay, blurTime) {
            if (typeof date == 'string') {
                if (date == '') return '';
                date = new Date(date.replace('-', '/').replace('-', '/'));
            }
            var result = '';
            if (blurDay) {
                switch (base.compareDay(new Date(), date)) {
                    case -1:
                        result = '昨天 ';
                        break;
                    case 0:
                        if (blurTime) {
                            result = $.timeago(date);
                            return result;
                        } else {
                            result = '今天 ';
                        }
                        break;
                    case 1:
                        result = '明天 ';
                        break;
                    default :
                        break;
                }
            }
            if (dateFormat) {
                dateFormat = dateFormat.replace('yyyy', date.getFullYear());
                dateFormat = dateFormat.replace('MM', (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1));
                dateFormat = dateFormat.replace('dd', date.getDate() < 10 ? '0' + date.getDate() : date.getDate());
                dateFormat = dateFormat.replace('hh', date.getHours() < 10 ? '0' + date.getHours() : date.getHours());
                dateFormat = dateFormat.replace('mm', date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes());
                dateFormat = dateFormat.replace('ss', date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
                dateFormat = dateFormat.replace('ww', base.weekday[date.getDay()]);
                result += dateFormat;
            } else {
                result += date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + date.getDate();
            }
            return result;
        },
        compareDay: function (startDateStr, endDateStr) {
            var dateSenonds = base.compareDateBase(startDateStr, endDateStr);
            dateSenonds = dateSenonds - 2000;
            /*计算出相差天数*/
            var days = Math.floor(dateSenonds / (24 * 3600 * 1000));
            return days + 1;
        },
        compareDateBase: function (startDateStr, endDateStr) {
            if (typeof startDateStr == 'string') {
                startDateStr = new Date(startDateStr.replace(/-/g, '/'));
            }
            if (typeof endDateStr == 'string') {
                endDateStr = new Date(endDateStr.replace(/-/g, '/'));
            }
            var startDT = startDateStr;
            var endDT = endDateStr;
            var startDate = new Date(startDT);
            var endDate = new Date(endDT);

            var dateSenonds = endDate.getTime() - startDate.getTime();
            /*时间差的毫秒数*/
            return dateSenonds;
        },
        fiterTime: function (time) {
            var result;
            if (typeof time == 'string') {
                if (time.split(' ').length > 1) result = time.split(' ')[1];
            }
            return result;
        },
        millisecondToTime: function (msd) {
            var time = parseFloat(msd) / 1000;
            if (null != time && "" != time) {
                if (time > 60 && time < 60 * 60) {
                    time = parseInt(time / 60.0) + "分钟" + parseInt((parseFloat(time / 60.0) - parseInt(time / 60.0)) * 60) + "秒";
                } else if (time >= 60 * 60 && time < 60 * 60 * 24) {
                    time = parseInt(time / 3600.0) + "小时" + parseInt((parseFloat(time / 3600.0) -
                        parseInt(time / 3600.0)) * 60) + "分钟" +
                        parseInt((parseFloat((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60) -
                            parseInt((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60)) * 60) + "秒";
                } else {
                    time = parseInt(time) + "秒";
                }
            } else {
                time = "0 时 0 分0 秒";
            }
            return time;
        },
        longToDate: function (lmsd) {
            if (lmsd != null) {
                var date = new Date();
                date.setTime(lmsd);
                var day = date.getDate().toString();
                day = day.length == 1 ? '0' + day : day;
                var month = (date.getMonth() + 1).toString();
                month = month.length == 1 ? '0' + month : month;
                var year = date.getFullYear();
                var hour = date.getHours().toString();
                hour = hour.length == 1 ? '0' + hour : hour;
                var min = date.getMinutes().toString();
                min = min.length == 1 ? '0' + min : min;
                var second = date.getSeconds().toString();
                second = second.length == 1 ? '0' + second : second;
                return(year + '-' + month + '-' + day + ' ' + hour + ':' + min + ':' + second);
            } else {
                return('无');
            }
        },
        longToDay: function (lmsd) {
            if (lmsd != null) {
                var date = new Date();
                date.setTime(lmsd);
                var day = date.getDate().toString();
                day = day.length == 1 ? '0' + day : day;
                var month = (date.getMonth() + 1).toString();
                month = month.length == 1 ? '0' + month : month;
                var year = date.getFullYear();
                var hour = date.getHours().toString();
                hour = hour.length == 1 ? '0' + hour : hour;
                var min = date.getMinutes().toString();
                min = min.length == 1 ? '0' + min : min;
                var second = date.getSeconds().toString();
                second = second.length == 1 ? '0' + second : second;
                return(day);
            } else {
                return('无');
            }
        },
        getDateTime: function (nMiliSeconds) {
            if (nMiliSeconds != null) {
                var date = new Date();
                date.setTime(nMiliSeconds);
                var day = date.getDate();
                var month = date.getMonth() + 1;
                var year = date.getFullYear();
                var hour = date.getHours();
                var min = date.getMinutes();
                var second = date.getSeconds();
                var _hour = parseInt(hour);
                var _min = parseInt(min);
                var _second = parseInt(second);
                if (_hour.length == 1) {
                    _hour = '0' + _hour;
                }
                if (_min.length == 1) {
                    _min = '0' + _min;
                }
                if (_second.length == 1) {
                    _second = '0' + _second;
                }

                var dateObj = {};
                dateObj.year = year;
                dateObj.month = month;
                dateObj.day = day;
                dateObj.hour = _hour;
                dateObj.min = _min;
                dateObj.second = _second;
                return dateObj;
            } else {
                return null;
            }
        },
        validateDate: function (oBeginTime, oEndTime) {
            if (oBeginTime == null) {
                cLib.base.showTip({msg: '请选择开始时间'});
                return false
            }

            if (oEndTime == null) {
                cLib.base.showTip({msg: '请选择结束时间'});
                return false;
            }

            var beginDate = oBeginTime.getTime();
            var endDate = oEndTime.getTime();
            var todayDate = new Date();


            if (endDate > todayDate) {
                cLib.base.showTip({msg: '所选结束日期已超过当日，请重新选择'});
                return false;
            }
            if (beginDate > endDate) {
                cLib.base.showTip({msg: '结束日期需大于开始日期，请重新选择'});
                return false;
            }
            return true;
        },
        msecondToTime: function (nMSecond) {
            var s = parseInt(nMSecond / 1000);
            var h = parseInt(s / 3600);
            s = s - h * 3600;
            if (s > 60) {
                var m = parseInt(s / 60);
                s = s - m * 60;
            } else {
                m = 0;
            }

            h = h == 0 ? '' : h + '时';


            return h + m + '分' + s + '秒';
        },
        secondToTime: function (nSecond) {
            var day = parseInt(nSecond / 3600 / 24) == 0 ? '' : parseInt(nSecond / 3600 / 24) + '天';
            var h = parseInt(nSecond / 3600 % 24) == 0 ? '' : parseInt(nSecond / 3600 % 24) + '小时';
            var m = parseInt(nSecond / 60) > 60 ? parseInt(nSecond / 60) % 60 : parseInt(nSecond / 60);
            m = m == 0 ? '' : m + '分';
            var s = nSecond % 60 == 0 ? '' : nSecond % 60 + '秒';

            return day + h + m + s;
        },
        getNowMillisecond: function () {
            var date = new Date().getTime();
            return date;
        },
        getToday0millisecond: function () {
            var today = new Date();
            today.setHours(0);
            today.setMinutes(0);
            today.setSeconds(0);
            today.setMilliseconds(0);
            return today.getTime();
        },
//        获取距今天之前0点的时间戳
        subDaysFromToday: function (nDay) {
            var today = new Date();
            today.setHours(0);
            today.setMinutes(0);
            today.setSeconds(0);
            today.setMilliseconds(0);
            return today.setDate(new Date().getDate() - nDay);
        },
        getBeginAndEndOfThisWeek: function () {
            var today = new Date();
            var day = today.getDay();
            var begin = new Date();
            var end = new Date();
            switch (day) {
                case 0://sunday
                    begin.setDate(today.getDate() - 6);
                    end.setDate(today.getDate());
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    begin.setDate(today.getDate() - (day - 1));
                    end.setDate(today.getDate() + (7 - day));
                    break;
                default:
                    break;
            }
            begin.setHours(0, 0, 0, 0);
            end.setHours(23, 59, 0, 0);
            return {begin: begin, end: end};
        },
        getWeekDic: function () {
            var result = {};
            var WebCalendar = {};
            WebCalendar.thisYear = new Date().getFullYear(); // 定义年的变量的初始值
            WebCalendar.thisMonth = new Date().getMonth(); // 定义月的变量的初始值
            WebCalendar.thisDay = new Date().getDate(); // 定义日的变量的初始值
            WebCalendar.daysMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
            WebCalendar.weekArray = new Array(); //定义周数组
            WebCalendar.today = new Date();
            var yearFirstDate, yearFirstWeekDay, yearDayCount;
            var weekStartDate, weekEndDate;
            var y = WebCalendar.thisYear;
            var m = WebCalendar.thisMonth;
            var d = WebCalendar.thisDay;
            if (!(y <= 9999 && y >= 1000 && parseInt(m, 10) > 0 && parseInt(m, 10) < 13 && parseInt(d, 10) > 0)) {
                WebCalendar.thisYear = new Date().getFullYear();
                WebCalendar.thisMonth = new Date().getMonth() + 1;
                WebCalendar.thisDay = new Date().getDate();
                y = WebCalendar.thisYear;
                m = WebCalendar.thisMonth;
                d = WebCalendar.thisDay;

            }
            WebCalendar.daysMonth[1] = (0 == y % 4 && (y % 100 != 0 || y % 400 == 0)) ? 29 : 28;//闰年二月为29天

            if (WebCalendar.daysMonth[1] == 28)// 获取一年的天数
                yearDayCount = 365
            else
                yearDayCount = 366
            yearFirstDate = new Date(parseInt(WebCalendar.thisYear), 0, 1, 0, 0, 0, 0)// 获取一年的第一天
            yearFirstWeekDay = yearFirstDate.getDay()// 获取第一天为星期几

            WebCalendar.weekcount = 1;
            WebCalendar.weeknum = 0;
            var n = 0;
            while (n < yearDayCount) {
                //考虑第一周各种情况
                if (WebCalendar.weekcount == 1) {
                    if (yearFirstWeekDay == 0 || yearFirstWeekDay > 4) {
                        if (yearFirstWeekDay == 0) { //当为星期天时
                            weekStartDate = _getNextNumDate(yearFirstDate, 1);
                            n = n + 7;
                        } else {//当为星期五或星期六时
                            weekStartDate = _getNextNumDate(yearFirstDate, 8 - yearFirstWeekDay);
                            n = n + 7 - yearFirstWeekDay;
                        }
                    } else {//当为星期一至星期四时
                        weekStartDate = _getNextNumDate(yearFirstDate, -(yearFirstWeekDay - 1));
                        n = n + (7 - yearFirstWeekDay);
                    }
                } else {
                    weekStartDate = _getNextNumDate(weekEndDate, 1);
                    n = n + 7;
                }

                weekEndDate = _getNextNumDate(weekStartDate, 6);

                //获得当前周数
                if ((weekStartDate <= WebCalendar.today) && (weekEndDate >= WebCalendar.today)) {
                    WebCalendar.weeknum = WebCalendar.weekcount;
                }

                //考虑最后一周时，判断是否超过4天
                if (n - yearDayCount >= 4) {
                    break;
                }

                var startm = parseInt(weekStartDate.getMonth()) + 1;
                var start_m = startm >= 10 ? startm : "0" + startm;
                var start_d = weekStartDate.getDate() >= 10 ? weekStartDate.getDate() : "0" + weekStartDate.getDate();
                var endm = parseInt(weekEndDate.getMonth()) + 1;
                var end_m = endm >= 10 ? endm : "0" + endm;
                var end_d = weekEndDate.getDate() >= 10 ? weekEndDate.getDate() : "0" + weekEndDate.getDate();
                var w = WebCalendar.weekcount >= 10 ? WebCalendar.weekcount : "0" + WebCalendar.weekcount;
                result[w] = { 'start': weekStartDate.getFullYear() + '/' + start_m + '/' + start_d, 'end': weekStartDate.getFullYear() + '/' + end_m + "/" + end_d };
                if (n >= yearDayCount) {
                    break;
                }
                WebCalendar.weekcount++;
            }
            result.weeknum = WebCalendar.weeknum;
            result.weekcount = WebCalendar.weekcount;

            return result;

            function _getNextNumDate(nowDate_, num_) {
                var y_ = nowDate_.getFullYear();
                var m_ = nowDate_.getMonth();
                var d_ = nowDate_.getDate();

                var ry_, rm_, rd_;  //返回结果的年月日
                var num = num_ + d_;
                ry_ = y_, rm_ = m_;
                rd_ = d_;
                if (num > WebCalendar.daysMonth[m_]) {
                    if ((m_ + 1) > 11) {  //当月份大于11时
                        rm_ = m_ + 1 - 12;
                        ry_ = y_ + 1;
                    } else {
                        rm_ = m_ + 1;
                    }
                    rd_ = num - WebCalendar.daysMonth[m_];
                } else {
                    //当日期推移的日期未超过本月时
                    rd_ = num_ + d_;
                }

                return new Date(parseInt(ry_), parseInt(rm_), parseInt(rd_), 0, 0, 0);

            }

        },
        curStr: function (obj, length) {
            var maxwidth = length,
                $obj = $(obj);
            if ($obj.text().length > maxwidth) {
                $obj.attr('title', $obj.text());
                $obj.text($obj.text().substring(0, maxwidth));
                $obj.html($obj.html() + '...');
            }
        },
        getStringWidth: function (str) {
            var width = len = str.length;
            for (var i = 0; i < len; i++) {
                if (str.charCodeAt(i) >= 255) {
                    width++;
                }
            }
            return width;
        },
        //获取鼠标当前位置
        getMousePos: function (event) {
            var e = event || window.event;
            var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
            var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
            var x = e.pageX || e.clientX + scrollX;
            var y = e.pageY || e.clientY + scrollY;
            return { 'x': x, 'y': y };
        },
        srcollto: function (selector, offset) {
            var top = $.isNumeric(selector) ? selector : $(selector).offset().top + (offset || 0);
            $('html,body').animate({'scrollTop': top, easing: 'easeOutExpo'});
        },
        addInputTip: function (obj, val) {
//            var $obj = $(obj);
//            if((!val || val == '') && $obj.val() != '') val = $obj.val();
//            $obj.val('');
//            $obj.attr('placeholder',val);
//            $obj.placeholder();
            var isPsw = false;
            var $obj = $(obj);
            if (!val || val == '') val = $obj.val();
//            alert($obj.val())
            $obj.val(val);
            if ($obj.attr('type') == 'password') {
                $obj.attr('type', 'text');
                isPsw = true;
            }
            $obj.focus(function () {
                if (this.getAttribute('data-val') == this.value) {
                    this.value = '';
                }
                $obj.removeClass('focus');
                this.className += ' focus';
                if (isPsw) $obj.attr('type', 'password');
            }).blur(function () {
                if (this.value == '') {
                    this.value = this.getAttribute('data-val');
                }
                this.className = this.className.replace('focus', '');
            })
        },
        emptyTip: function (obj, tip, remove) {
            var $obj = $(obj);
            if (base.isEmpty($obj.html())) {
                if (typeof  tip == 'function') {
                    tip($obj);
                } else {
                    $obj.addClass('emptyTip');
                    $obj.html('<div class="emptyTip">' + tip + '</div>');
                }
            } else {
                if (typeof  remove == 'function') {
                    remove($obj);
                } else {
                    if (!base.isEmpty($obj.not('.emptyTip'))) {
                        $('.emptyTip', $obj).remove();
                        $obj.removeClass('emptyTip');
                    }
                }
            }
        },
        removeEmptyTip: function (obj) {
            var $obj = $(obj);
            if (!base.isEmpty($obj.not('.emptyTip'))) {
                $obj.find('.emptyTip').remove();
                $obj.removeClass('emptyTip');
            }
        },
        fileTypeSupport: function (type) {
            var spTypes = ["jpg", "jpeg", "png", "Png"];
            for (var each in spTypes) {
                if (spTypes[each]) {
                    if (spTypes[each] == type) return true;
                }
            }
            return false;
        },
        getIconClass: function (type) {
            var result = 'iconF ';
            type = type.toLowerCase();
            switch (type) {
                case 'png':
                case 'jpg':
                case 'gif':
                case 'rtf':
                    result += 'picture_orange';
                    break;
                case 'pdf':
                    result += 'pdf_red';
                    break;
                case 'txt':
                    result += 'text_blue';
                    break;
                case 'doc':
                case 'docx':
                    result += 'word_blue';
                    break;
                case 'ppt':
                    result += 'graph_red';
                    break;
                case 'xls':
                case 'xlsx':
                    result += 'spreadsheet_green';
                    break;
                default :
                    result += 'text_blue';
                    break;
            }
            return result;
        },
        getIconClassM: function (type) {
            var result = 'icon ';
            type = type.toLowerCase();
            switch (type) {
                case 'png':
                case 'jpg':
                case 'gif':
                case 'rtf':
                    result += 'picture';
                    break;
                case 'pdf':
                    result += 'pdf_icon';
                    break;
                case 'txt':
                    result += 'text_icon';
                    break;
                case 'doc':
                case 'docx':
                    result += 'word_icon';
                    break;
                case 'ppt':
                    result += 'ppt_icon';
                    break;
                case 'xls':
                case 'xlsx':
                    result += 'exe_icon';
                    break;
                default :
                    result += 'text_icon';
                    break;
            }
            return result;
        },
        getUserPhoto: function (userId, name, path, size) {
            var result;
            var url = path;
            if (url == '' || url == null) {
                switch (size) {
                    case 32:
                        url = '/images/icon_user_m.gif';
                        break;
                    case 50:
                        url = '/images/icon_user_normal.gif';
                        break;
                    default :
                        url = '/images/icon_user_normal.gif';
                        break;
                }
            }
            result = '<img data-toggle="tooltip" title="' + name + '" class="userPhoto" data-target="' + userId + '" src="' + url + '" width="' + size + '" height="' + size + '" />';
            return result;
        },
        //使对象居中
        setCenter: function (obj) {
            var $t = $(obj);
            $t.css('top', ($t.offset().top + $(document).scrollTop()) + 'px');
        },

        toggleClass: function (obj, classA, classB) {
            var $obj = $(obj);
            var classA_hover = classA + '_hover',
                classB_hover = classB + '_hover';
            if ($obj.hasClass(classA) || $obj.hasClass(classA_hover)) {
                $obj.removeClass(classA).removeClass(classA_hover).addClass(classB);
            } else {
                $obj.removeClass(classB).removeClass(classB_hover).addClass(classA);
            }
        },
        //验证电子邮箱
        //@param    text    String
        isEmail: function (text) {
            var reg = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)/;
            return reg.test(text);
        },
        //验证密码 6-20个字符
        //@param    text    String
        isPassword: function (text) {
            var reg = /^(\w){6,20}$/;
            return reg.test(text)
        },

        addPriority: function (options) {
            var result = '';
            options.priority = parseInt(options.priority);
            if (options.priority > 0) {
                var prioriyStr = options.str.substring(0, options.priority),
                    str = options.str.substring(options.priority, options.str.length);
                result = '<em class="prioriy">' + prioriyStr + '</em>' + str;
            } else {
                result = options.str;
            }
            return result;
        },
        md5: function (psw) {
            return $.md5(psw.toUpperCase()).toUpperCase();
        }
    },
        ajax = {
            get: function (options) {

                ajaxLib[options.url] = $.ajax({
                    'url': options.url,
                    'dataType': 'jsonp',
                    'data': options.data,
                    'beforeSend': function (jqXHR, settings) {

                    },
                    'success': function (response) {
                        if (options.codeDefine) {
                            options.success(response);
                        } else {
                            if (response) {
                                switch (response.ret) {
                                    //没有权限
                                    case 12:
                                        alert("没有权限");
                                        break;
                                    //请求成功
                                    case 1:
                                        options.success(response);
                                        break;
                                    case 2:
                                        alert('msg' + ":" + response.desc);
                                        break;
                                    case 3:
                                        alert('msg' + ":" + response.msg);
                                        break;
                                }
                                if (options.repexcute) options.repexcute(response);
                            }
                        }
                    },
                    'complete': function (jqXHR, textStatus) {
                        if (options.loading) $(options.loading).hideLoading();
                        if (options.complete) options.complete(jqXHR, textStatus);
                    },
                    'error': function (jqXHR, textStatus, errorThrown) {
                        if (options.error) options.error(jqXHR, textStatus, errorThrown);
                    }
                });
                return ajaxLib[options.url];
            },
            post: function (options) {
                ajaxLib[options.url] = $.ajax({
                    'url': options.url,
                    'dataType': 'jsonp',
                    'data': options.data,
                    'beforeSend': function (jqXHR, settings) {
                        if (options.loading) $(options.loading).showLoading()
                    },
                    'success': function (response) {
                        if (options.codeDefine) {
                            options.success(response);
                        } else {
                            if (response) {
                                switch (response.ret) {
                                    //没有权限
                                    case 12:
                                    	$.message("warn","没有权限");
                                        break;
                                    //请求成功
                                    case 1:
                                        options.success(response);
                                        break;
                                    //请求失败
                                    case 2:
                                    	$.message("warn",response.desc);
                                        break;
                                    case 3:
                                    	$.message("warn",response.desc);
                                        break;
                                }
                                if (options.repexcute) options.repexcute(response);
                            }
                        }
                    },
                    'complete': function (jqXHR, textStatus) {
                        if (options.loading) $(options.loading).hideLoading();
                        if (options.complete) options.complete(jqXHR, textStatus);
                    },
                    'error': function (jqXHR, textStatus, errorThrown) {
                        if (options.error) options.error(jqXHR, textStatus, errorThrown);
                    }
                });
                return ajaxLib[options.url];
            }
        },
        //信息调试
        log = {
            info: function (msg) {
            }
        }
    lib.log = log;
    lib.base = base;
    lib.ajax = ajax;
})(cLib)

/*
* 以下为坐标转换的方法
*/
var x_pi = 3.14159265358979324 * 3000.0 / 180.0;

var casm_rr = 0;
var casm_t1 = 0;
var casm_t2 = 0;
var casm_x1 = 0;
var casm_y1 = 0;
var casm_x2 = 0;
var casm_y2 = 0;
var casm_f = 0;
/**
 * Convert GCJ02 to BD09
 *
 * @param gg_lat
 * @param gg_lon
 * @return
 */
function convertGcj02ToBd09(gg_lon, gg_lat) {
    var x_pi = 3.14159265358979324 * 3000.0 / 180.0;
    var x = gg_lon, y = gg_lat;
    var z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
    var theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
    var p = {};
    p.longitude = z * Math.cos(theta) + 0.0065;
    p.latitude = z * Math.sin(theta) + 0.006;
    return p;
}

/**
 * Convert WGS to GCJ02
 * @param x
 * @param y
 * @return
 */
function convertWgsToGcj02(x, y) {
    var x1, tempx, y1, tempy;
    x1 = x * 3686400.0;
    y1 = y * 3686400.0;
    var gpsWeek = 0;
    var gpsWeekTime = 0;
    var gpsHeight = 0;

    var point = wgtochina_lb(1, Math.floor(x1), Math.floor(y1), Math.floor(gpsHeight),
        Math.floor(gpsWeek), Math.floor(gpsWeekTime));
    if(point == null) {
        return false
    } else {
        tempx = point.x;
        tempy = point.y;
        tempx = tempx / 3686400.0;
        tempy = tempy / 3686400.0;

        point.longitude = tempx;
        point.latitude = tempy;
        return point;
    }
}


function random_yj() {
    var t;
    var casm_a = 314159269;
    var casm_c = 453806245;
    casm_rr = casm_a * casm_rr + casm_c;
    t = Math.floor(casm_rr / 2);
    casm_rr = casm_rr - t * 2;
    casm_rr = casm_rr / 2;
    return (casm_rr);
}


function yj_sin2(x) {
    var tt;
    var ss;
    var ff;
    var s2;
    var cc;
    ff = 0;
    if (x < 0) {
        x = -x;
        ff = 1;
    }

    cc = Math.floor(x / 6.28318530717959);

    tt = x - cc * 6.28318530717959;
    if (tt > 3.1415926535897932) {
        tt = tt - 3.1415926535897932;
        if (ff == 1) {
            ff = 0;
        } else if (ff == 0) {
            ff = 1;
        }
    }
    x = tt;
    ss = x;
    s2 = x;
    tt = tt * tt;
    s2 = s2 * tt;
    ss = ss - s2 * 0.166666666666667;
    s2 = s2 * tt;
    ss = ss + s2 * 8.33333333333333E-03;
    s2 = s2 * tt;
    ss = ss - s2 * 1.98412698412698E-04;
    s2 = s2 * tt;
    ss = ss + s2 * 2.75573192239859E-06;
    s2 = s2 * tt;
    ss = ss - s2 * 2.50521083854417E-08;
    if (ff == 1) {
        ss = -ss;
    }
    return ss;
}

function IniCasm(w_time, w_lng, w_lat) {
    var tt;
    casm_t1 = w_time;
    casm_t2 = w_time;
    tt = Math.floor(w_time / 0.357);
    casm_rr = w_time - tt * 0.357;
    if (w_time == 0)
        casm_rr = 0.3;
    casm_x1 = w_lng;
    casm_y1 = w_lat;
    casm_x2 = w_lng;
    casm_y2 = w_lat;
    casm_f = 3;
}
function wgtochina_lb(wg_flag, wg_lng, wg_lat, wg_heit, wg_week, wg_time) {
    var x_add;
    var y_add;
    var h_add;
    var x_l;
    var y_l;
    var casm_v;
    var t1_t2;
    var x1_x2;
    var y1_y2;
    var point = null;
    if (wg_heit > 5000) {
        return point;
    }
    x_l = wg_lng;
    x_l = x_l / 3686400.0;
    y_l = wg_lat;
    y_l = y_l / 3686400.0;
    if (x_l < 72.004) {
        return point;
    }
    if (x_l > 137.8347) {
        return point;
    }
    if (y_l < 0.8293) {
        return point;
    }
    if (y_l > 55.8271) {
        return point;
    }
    if (wg_flag == 0) {
        IniCasm(wg_time, wg_lng, wg_lat);
        point = {};
        point.latitude = wg_lng;
        point.longitude = wg_lat;
        return point;
    }
    casm_t2 = wg_time;
    t1_t2 = (casm_t2 - casm_t1) / 1000.0;
    if (t1_t2 <= 0) {
        casm_t1 = casm_t2;
        casm_f = casm_f + 1;
        casm_x1 = casm_x2;
        casm_f = casm_f + 1;
        casm_y1 = casm_y2;
        casm_f = casm_f + 1;
    } else {
        if (t1_t2 > 120) {
            if (casm_f == 3) {
                casm_f = 0;
                casm_x2 = wg_lng;
                casm_y2 = wg_lat;
                x1_x2 = casm_x2 - casm_x1;
                y1_y2 = casm_y2 - casm_y1;
                casm_v = Math.sqrt(x1_x2 * x1_x2 + y1_y2 * y1_y2) / t1_t2;
                if (casm_v > 3185) {
                    return (point);
                }
            }
            casm_t1 = casm_t2;
            casm_f = casm_f + 1;
            casm_x1 = casm_x2;
            casm_f = casm_f + 1;
            casm_y1 = casm_y2;
            casm_f = casm_f + 1;
        }
    }
    x_add = Transform_yj5(x_l - 105, y_l - 35);
    y_add = Transform_yjy5(x_l - 105, y_l - 35);
    h_add = wg_heit;
    x_add = x_add + h_add * 0.001
        + yj_sin2(wg_time * 0.0174532925199433) + random_yj();
    y_add = y_add + h_add * 0.001
        + yj_sin2(wg_time * 0.0174532925199433) + random_yj();
    point = {};
    point.x = (x_l + Transform_jy5(y_l, x_add)) * 3686400;
    point.y = (y_l + Transform_jyj5(y_l, y_add)) * 3686400;
    return point;
}

function Transform_yj5(x, y) {
    var tt;
    tt = 300 + x + 2 * y + 0.1 * x * x + 0.1 * x * y + 0.1
        * Math.sqrt(Math.sqrt(x * x));
    tt = tt
        + (20 * yj_sin2(18.849555921538764 * x) + 20 * yj_sin2(6.283185307179588 * x)) * 0.6667;
    tt = tt
        + (20 * yj_sin2(3.141592653589794 * x) + 40 * yj_sin2(1.047197551196598 * x)) * 0.6667;
    tt = tt
        + (150 * yj_sin2(0.2617993877991495 * x) + 300 * yj_sin2(0.1047197551196598 * x)) * 0.6667;
    return tt;
}

function Transform_yjy5(x, y) {
    var tt;
    tt = -100 + 2 * x + 3 * y + 0.2 * y * y + 0.1 * x * y + 0.2
        * Math.sqrt(Math.sqrt(x * x));
    tt = tt
        + (20 * yj_sin2(18.849555921538764 * x) + 20 * yj_sin2(6.283185307179588 * x)) * 0.6667;
    tt = tt
        + (20 * yj_sin2(3.141592653589794 * y) + 40 * yj_sin2(1.047197551196598 * y)) * 0.6667;
    tt = tt
        + (160 * yj_sin2(0.2617993877991495 * y) + 320 * yj_sin2(0.1047197551196598 * y)) * 0.6667;
    return tt;
}

function Transform_jy5(x, xx) {
    var n;
    var a;
    var e;
    a = 6378245;
    e = 0.00669342;
    n = Math.sqrt(1 - e * yj_sin2(x * 0.0174532925199433)
        * yj_sin2(x * 0.0174532925199433));
    n = (xx * 180) / (a / n * Math.cos(x * 0.0174532925199433) * 3.1415926);
    return n;
}

function Transform_jyj5(x, yy) {
    var m;
    var a;
    var e;
    var mm;
    a = 6378245;
    e = 0.00669342;
    mm = 1 - e * yj_sin2(x * 0.0174532925199433)
        * yj_sin2(x * 0.0174532925199433);
    m = (a * (1 - e)) / (mm * Math.sqrt(mm));
    return (yy * 180) / (m * 3.1415926);
}

