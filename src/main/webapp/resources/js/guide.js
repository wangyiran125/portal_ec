	//input非空验证
	function paramsValidate(args){
		var count = 0;
		if(args!=null&& args.length>0){
			for(var i in args){
				if(args[i].attr("name") != "licenseImagePath" && args[i].val()<1){
					validatePrompt("add",args[i],'必填')
					count++;
				}else if(args[i].attr("name") == "licenseImagePath" && args[i].val()<1){
					count = checkImage();
				}else{
					validatePrompt("remove",args[i],'')
				}
				
			}
		}
		return count;
	}
	
	//图片非空验证
	function checkImage(){
		var count = 0;
		var image = $("input[name='licenseImagePath']");
		if(image.val()<1){
			$("#uploadLicenseImageBtn").next().addClass("fieldError").html("必填");
			count++;
		}
		return count;
	}
	
	//邮箱验证
	function validateEmail(email){
		var expr = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
		return expr.test(email);
	};
	
		//身份证验证
		function validateIdCardNo(num) {
		    var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
		    var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
		    var varArray = new Array();
		    var intValue;
		    var lngProduct = 0;
		    var intCheckDigit;
		    var intStrLen = num.length;
		    var idNumber = num;
		    // initialize
		    if ((intStrLen != 15) && (intStrLen != 18)) {
		        return false;
		    }
		    // check and set value
		    for (i = 0; i < intStrLen; i++) {
		        varArray[i] = idNumber.charAt(i);
		        if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
		            return false;
		        } else if (i < 17) {
		            varArray[i] = varArray[i] * factorArr[i];
		        }
		    }
	
		    if (intStrLen == 18) {
		        //check date
		        var date8 = idNumber.substring(6, 14);
		        if (isDate8(date8) == false) {
		            return false;
		        }
		        // calculate the sum of the products
		        for (i = 0; i < 17; i++) {
		            lngProduct = lngProduct + varArray[i];
		        }
		        // calculate the check digit
		        intCheckDigit = parityBit[lngProduct % 11];
		        // check last digit
		        if (varArray[17] != intCheckDigit) {
		            return false;
		        }
		    }
		    else {        //length is 15
		        //check date
		        var date6 = idNumber.substring(6, 12);
		        if (isDate6(date6) == false) {
		            return false;
		        }
		    }
		    return true;
	};
		function isDate6(sDate) {
	    if (!/^[0-9]{6}$/.test(sDate)) {
	        return false;
	    }
	    var year, month, day;
	    year = sDate.substring(0, 4);
	    month = sDate.substring(4, 6);
	    if (year < 1700 || year > 2500) return false;
	    if (month < 1 || month > 12) return false;
	    return true;
	};
	function isDate8(sDate) {
	    if (!/^[0-9]{8}$/.test(sDate)) {
	        return false;
	    }
	    var year, month, day;
	    year = sDate.substring(0, 4);
	    month = sDate.substring(4, 6);
	    day = sDate.substring(6, 8);
	    var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	    if (year < 1700 || year > 2500) return false;
	    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1] = 29;
	    if (month < 1 || month > 12) return false;
	    if (day < 1 || day > iaMonthDays[month - 1]) return false;
	    return true;
	}
	//验证提示信息
	function validatePrompt(type,$obj,content){
		if(type =="add"){
			 $($obj).addClass("fieldError").next().addClass("fieldError").html(content);
		}
		if(type== "remove"){
			 $($obj).removeClass("fieldError").next().removeClass("fieldError").html("");
		}
	}
