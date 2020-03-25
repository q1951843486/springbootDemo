//格式化年
var dateFormatYear= java.text.SimpleDateFormat("yyyy");
var year =dateFormatYear.format(new java.util.Date());
//格式化月

var dateFormatMonth = java.text.SimpleDateFormat("MM");
var month = dateFormatMonth.format(new java.util.Date());
//格式化日
var dateFormatDay =java.text.SimpleDateFormat("dd");
var day = dateFormatDay.format(new java.util.Date());

//格式化小时
var dateFormatHour =java.text.SimpleDateFormat("HH");
var hour = dateFormatHour.format(new java.util.Date());

//格式化分钟
var dateForMatminute =java.text.SimpleDateFormat("mm");
var minute = dateForMatminute.format(new java.util.Date());
var sourcePath = "";

//202003131702api.csv
if(minute >= 35){
    var sourcePath =   year + month + day +hour + "01" + "api.csv";
}else {
    if(hour == 10){
        var sourcePath =   year + month + day + "0" + (hour - 1) + "02" + "api.csv";
    }else if(hour == 0){
        var sourcePath =   year + month + (day == 10?"0":"")+(day-1) + "23" + "02" + "api.csv";
    }else{
        var sourcePath =   year + month + day + (hour<=10?"0":"") + (hour - 1) + "02" + "api.csv";
    }

}
//判断是否到每年的第一天 第一个小时
if(month == 1 && day == 1 && hour == 0 && minute <35){
    sourcePath =   (year-1)  + "12" + "31"+ "23" + "02" + "api.csv";
}
//判断是否到每月的第一天第一个小时
if (month != 1 && day == 1 && hour == 0 && minute <35){
    var beforeMonth = 1;
    beforeMonth = month -1;
    var dayCount = 30;
    sourcePath = "1111";
    switch (beforeMonth) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            var sourcePath =  year  + (beforeMonth<10?"0":"") + beforeMonth + "31"+ "23"+ "02" + "api.csv";
            break;
        case 4:
        case 6:
        case 9:
            var sourcePath =  year  + (beforeMonth<10?"0":"") + beforeMonth + "30"+ "23"+ "02" + "api.csv";
            break;
        case 2:
            if (year % 4 == 0 && year % 100 !=0 || year % 400 == 0) {
                var sourcePath =  year  + (beforeMonth<10?"0":"") + beforeMonth + "29"+ "23"+ "02" + "api.csv";
            }else {
                var sourcePath =  year  + (beforeMonth<10?"0":"") + beforeMonth + "28"+ "23"+ "02" + "api.csv";
            }
            break;
    }
}



