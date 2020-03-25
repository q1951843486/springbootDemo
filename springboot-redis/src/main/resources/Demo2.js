//Script here

var dateFormatYearAndMonth= java.text.SimpleDateFormat("yyyyMM");

var dateFormatDay =java.text.SimpleDateFormat("dd");

var yearAndMonth =dateFormatYearAndMonth.format(new java.util.Date());



var day = dateFormatDay.format(new java.util.Date());
var dateFormatHour =java.text.SimpleDateFormat("HH");
var hour = dateFormatHour.format(new java.util.Date());
var dateForMatminute =java.text.SimpleDateFormat("mm");
var minute = dateForMatminute.format(new java.util.Date());



//202003131702api.csv
if(minute >= 35){
    var sourcePath =   yearAndMonth + day +hour + "01" + "api.csv";
}else {
    if(hour == 10){
        var sourcePath =   yearAndMonth + day + "0" + (hour - 1) + "02" + "api.csv";
    }else if(hour == 0){
        var sourcePath =   yearAndMonth + day + "23" + "02" + "api.csv";
    }else {
        var sourcePath =   yearAndMonth + day +  (hour - 1) + "02" + "api.csv";
    }
}
