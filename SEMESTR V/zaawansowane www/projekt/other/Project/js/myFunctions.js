function setWeatherNow($var) {
    $("#now").html($var);
}

function setWeatherTomorrow($var) {
    $("#tomorrow").html($var);
}

function setWeatherTwoDay($var) {
    $("#twoDay").html($var);
}


//CHART FUNCTION

function getTemperatureToChart($data) {

    var temperatures = [$data.length];

    for (var i = 0, len = $data.length; i < len; i++) {
        temperatures[i] = $data[i].main.temp - 273.15;
    }

    return temperatures;
}

function getTimeToChart($data) {

    var time = [$data.length];

    for (var i = 0, len = $data.length; i < len; i++) {
        time[i] = $data[i].dt_txt;
    }

    return time;
}

function getGrndLevelToChart($data) {

    var GrndLevel = [$data.length];

    for (var i = 0, len = $data.length; i < len; i++) {
        GrndLevel[i] = $data[i].main.grnd_level;
    }
    return GrndLevel;
}

function getHumidityToChart($data) {

    var humidity = [$data.length];

    for (var i = 0, len = $data.length; i < len; i++) {
        humidity[i] = $data[i].main.humidity;
    }
    return humidity;
}

function getPressureToChart($data) {

    var pressure = [$data.length];

    for (var i = 0, len = $data.length; i < len; i++) {
        pressure[i] = $data[i].main.pressure;
    }
    return pressure;
}

function getSeaLevelToChart($data) {

    var seaLevel = [$data.length];

    for (var i = 0, len = $data.length; i < len; i++) {
        seaLevel[i] = $data[i].main.sea_level;
    }
    return seaLevel;
}

function getTempKfToChart($data) {

    var tempKf = [$data.length];

    for (var i = 0, len = $data.length; i < len; i++) {
        tempKf[i] = $data[i].main.temp_kf;
    }
    return tempKf;
}

function getTempMaxToChart($data) {

    var tempMax = [$data.length];

    for (var i = 0, len = $data.length; i < len; i++) {
        tempMax[i] = $data[i].main.temp_max - 273.15;
        ;
    }
    return tempMax;
}

function getTempMinToChart($data) {

    var tempMin = [$data.length];

    for (var i = 0, len = $data.length; i < len; i++) {
        tempMin[i] = $data[i].main.temp_min - 273.15;
    }
    return tempMin;
}

function getRainToChart($data){

    var rain    = [$data.length];
    var raining = false;
    for (var i = 0, len = $data.length; i < len; i++) {

        rain[i] = 0;
        if($data[i].rain){
            $.each( $data[i].rain, function( key, value ) {
                rain[i] = value;
            });
        }
    }
    return rain;
}

function getWeatherDescriptionToChart($data) {

    var WeatherDescription = [$data.length];

    for (var i = 0, len = $data.length; i < len; i++) {
        WeatherDescription[i] = $data[i].weather[0].description;
    }
    return WeatherDescription;
}

function getWeatherMainToChart($data) {

    var WeatherDescription = [$data.length];

    for (var i = 0, len = $data.length; i < len; i++) {
        WeatherDescription[i] = $data[i].weather[0].main;
    }
    return WeatherDescription;
}

function getWeatherIconToChart($data) {

    var WeatherDescription = [$data.length];

    for (var i = 0, len = $data.length; i < len; i++) {
        WeatherDescription[i] = $data[i].weather[0].id;
    }
    return WeatherDescription;
}

function getSpeedWindToChart($data) {

    var SpeedWind = [$data.length];

    for (var i = 0, len = $data.length; i < len; i++) {
        SpeedWind[i] = $data[i].wind.speed;
    }
    return SpeedWind;
}

function getDegreeWindToChart($data) {

    var DegreeWind = [$data.length];

    for (var i = 0, len = $data.length; i < len; i++) {
        DegreeWind[i] = $data[i].wind.deg;
    }
    return DegreeWind;
}

//END CHART FUNCTION

function Round(n, k) {
    var factor = Math.pow(10, k);
    return Math.round(n*factor)/factor;
}

function getTextToWeather($temperature, $conditions) {
    return "Temperature: " + $temperature + "<br /> Conditions: " + $conditions;
}

function getWeather($city) {


//            GrndLevel
//            Humidity
//            Pressure
//            SeaLevel
//            TempKf
//            TempMax
//            TempMin
//            WeatherDescription
//            SpeedWind
//            DegreeWind

    var chartsShow = ["Temperature", "Pressure", "TempMin", "SpeedWind"];

    Weather.getForecast($city, 7, function (forecast) {

        //var temperature = Weather.kelvinToCelsius(forecast.high());
        //var conditions = forecast.conditions();

        var weather = forecast.data.list;


        $("div#weatherSection").remove();
        $("div#weatherRow").append("<div id=\"weatherSection\"></div>");

        $("div#row").remove();
        $("div.section#charts").append("<div class=\"row\" id=\"row\"> </div>");

        getWeatherHorizontal(weather);
        getTimeWether(weather);
        getAllWeatherOnOneChart(weather);

        //getShowChart(getTemperatureToChart(weather), getTimeToChart(weather), chartsShow[0]);
        //getShowChart(getPressureToChart(weather), getTimeToChart(weather), chartsShow[1]);
        //getShowChart(getTempMinToChart(weather), getWeatherDescriptionToChart(weather), chartsShow[2]);
        //getShowChart(getSpeedWindToChart(weather), getTimeToChart(weather), chartsShow[3]);

    });


}


function buttonClick() {

    var searchValue = $("#search").val();

    getWeather(searchValue);
}

function getShowChart($data, $date, $nameChart) {

    var $sData = "";
    var $sDate = "";

    for (var i = 0, len = $data.length, lenn = $date.length; i < lenn && i < len; i++) {
        if ($data[i] != "undefined" && $date[i] != "undefined") {
            $sData += $data[i] + ", ";
            $sDate += "\"" + $date[i] + "\", ";
        }
    }

    var script = "<script>" +
        "   $(function() {" +
        "       var ctx = document.getElementById(\"" + $nameChart + "\");" +
        "       " +
        "       var myChart = new Chart(ctx, {" +
        "           type: 'line'," +
        "           " +
        "           data: {" +
        "               labels: [" + $sDate + "]," +
        "               datasets: [{" +
        "                   label: '# Value'," +
        "                   data: [" + $sData + "]," +
        "                   backgroundColor: \"rgba(00,150,136,0.8)\"," +
        "                   " +
        "               }]" +
        "           }," +
        "       " +
        "           options: {" +
        "               scales: {" +
        "                   yAxes: [{" +
        "                       ticks: {" +
        "                           beginAtZero: true" +
        "                       }" +
        "                   }]" +
        "               }" +
        "           }" +
        "       });" +
        "   });" +
        "<\/script>";


    $("div.section#charts div#row").append("<h5 class=\"center\">" + $nameChart + "</h5><canvas id=\"" + $nameChart + "\" width=\"400\" height=\"200\"></canvas>");
    $("canvas#" + $nameChart).after(script);

}


function getWeatherHorizontal($weather){

    var GrndLevel           = getGrndLevelToChart($weather);
    var Humidity            = getHumidityToChart($weather);
    var Pressure            = getPressureToChart($weather);
    var SeaLevel            = getSeaLevelToChart($weather);
    var TempKf              = getTempKfToChart($weather);
    var TempMax             = getTempMaxToChart($weather);
    var TempMin             = getTempMinToChart($weather);
    var WeatherDescription  = getWeatherMainToChart($weather);
    var WeatherIcon         = getWeatherIconToChart($weather);
    var SpeedWind           = getSpeedWindToChart($weather);
    var DegreeWind          = getDegreeWindToChart($weather);
    var Rain                = getRainToChart($weather);

    var $date               = getTimeToChart($weather);

    var $whatShow           = [1,1,1,1,1,1,1,1,1,1,1];
    var $chartLabels        = ["Rain","GrndLevel","Humidity","Pressure","SeaLevel","TempKf","TempMax","TempMin","WeatherDescription","SpeedWind","DegreeWind"];
    var $arrWeather         = [Rain,GrndLevel, Humidity, Pressure, SeaLevel, TempKf, TempMax, TempMin, WeatherDescription, SpeedWind, DegreeWind];
    var $arrColor           = ["00,150,136","197,34,169","95,212,209","95,241,147","238,236,108","54,147,193","89,169,70","223,121,153","45,21,88","80,135,143","157,122,18","59,239,202","89,169,70"];

    var $sData              = "";
    var $sDate              = "";

    for (var i = 0, len = $date.length; i < len; i++) {
        $sDate += "\"" + $date[i].substring(5, 16) + "\", ";
    }


    var script = "" +
        "<ul class=\"collapsible\" data-collapsible=\"accordion\">" +
        "";

    for(var i = 0, len = $date.length; i < len; i++) {
        script +=
            "<li>" +
            "   <div class=\"collapsible-header\">" + $date[i] + " <i class=\"owf owf-" + WeatherIcon[i] + " owf-3x \"></i> " + WeatherDescription[i].toUpperCase() + " " + Round(TempMax[i], 0) + "&#186C</div>" +
            "       <div class=\"collapsible-body\">";

        for(var j = 0, lenJ = $arrWeather.length; j < lenJ; j++){
            $arrWeatherTmp = $arrWeather[j];
            if($whatShow[j] == 1){

                script +=
                    "<p>" + $chartLabels[j] + " = " + $arrWeatherTmp[i] + "</p>";
            }
        }

        script +=
            "   </div>" +
            "</li>";
    }
    script += "</ul>" +
        " <script> " +
        "$(document).ready(function(){" +
        "$('.collapsible').collapsible({" +
        "accordion : false " +// A setting that changes the collapsible behavior to expandable instead of the default accordion style
        "});" +
        "});" +
        "</script>";

    $("div#weatherSection").append(script);
}

function getTimeWether($weather){

    var GrndLevel           = getGrndLevelToChart($weather);
    var Humidity            = getHumidityToChart($weather);
    var Pressure            = getPressureToChart($weather);
    var SeaLevel            = getSeaLevelToChart($weather);
    var TempKf              = getTempKfToChart($weather);
    var TempMax             = getTempMaxToChart($weather);
    var TempMin             = getTempMinToChart($weather);
    var WeatherDescription  = getWeatherMainToChart($weather);
    var WeatherIcon         = getWeatherIconToChart($weather);
    var SpeedWind           = getSpeedWindToChart($weather);
    var DegreeWind          = getDegreeWindToChart($weather);
    var Rain                = getRainToChart($weather);

    var $date               = getTimeToChart($weather);

    var $whatShow           = [1,1,1,1,1,1,1,1,1,1,1];
    var $chartLabels        = ["Rain","GrndLevel","Humidity","Pressure","SeaLevel","TempKf","TempMax","TempMin","WeatherDescription","SpeedWind","DegreeWind"];
    var $arrWeather         = [Rain,GrndLevel, Humidity, Pressure, SeaLevel, TempKf, TempMax, TempMin, WeatherDescription, SpeedWind, DegreeWind];
    var $arrColor           = ["00,150,136","197,34,169","95,212,209","95,241,147","238,236,108","54,147,193","89,169,70","223,121,153","45,21,88","80,135,143","157,122,18","59,239,202","89,169,70"];

    var $sData              = "";
    var $sDate              = "";

    for (var i = 0, len = $date.length; i < len; i++) {
        $sDate += "\"" + $date[i].substring(5, 16) + "\", ";
    }

    var script = "";

    for(var i = 0, len = $date.length; i < len; i++) {
        script +=
            "<div class=\"cd-timeline-block\">" +
            "   <div class=\"cd-timeline-img cd-picture\">" +
            "       <i class=\"owf owf-" + WeatherIcon[i] + " owf-3x owf-pull-left owf-border\"></i>" +
            //"       <img src=\"img/SVG/"+WeatherDescription[i]+".SVG\" alt=\"Picture\">" +
            "   </div>" +
            "   " +
            "   <div class=\"cd-timeline-content\">" +
            "       <h2>" + WeatherDescription[i].toUpperCase() + " " + Round(TempMax[i], 0) + "&#186C</h2>";

        for(var j = 0, lenJ = $arrWeather.length; j < lenJ; j++){
            $arrWeatherTmp = $arrWeather[j];
            if($whatShow[j] == 1){

                //console.log($arrWeatherTmp + $chartLabels + " = " + $arrWeatherTmp[i]);
                script +=
                    "<p>" + $chartLabels[j] + " = " + $arrWeatherTmp[i] + "</p>";
            }
        }

        script +=
            "       <a class=\"cd-read-more\">Read more</a>" +
            "       <span class=\"cd-date\"><h5>" + $date[i] + "</h5></span>" +
            "   </div>" +
            "</div>";
    }

    $("div#weatherSection").append(script);
}

function getAllWeatherOnOneChart($weather){

    var GrndLevel           = getGrndLevelToChart($weather);
    var Humidity            = getHumidityToChart($weather);
    var Pressure            = getPressureToChart($weather);
    var SeaLevel            = getSeaLevelToChart($weather);
    var TempKf              = getTempKfToChart($weather);
    var TempMax             = getTempMaxToChart($weather);
    var TempMin             = getTempMinToChart($weather);
    var WeatherDescription  = getWeatherMainToChart($weather);
    var SpeedWind           = getSpeedWindToChart($weather);
    var DegreeWind          = getDegreeWindToChart($weather);
    var Rain                = getRainToChart($weather);

    var $date               = getTimeToChart($weather);

    var $whatShow           = [1,0,0,0,0,0,1,0,0,0,0];
    var $chartLabels        = ["Rain",      "GrndLevel",    "Humidity",     "Pressure",     "SeaLevel",     "TempKf",       "TempMax",      "TempMin",      "WeatherDescription",   "SpeedWind",    "DegreeWind"];
    var $arrWeather         = [Rain,        GrndLevel,      Humidity,       Pressure,       SeaLevel,       TempKf,         TempMax,        TempMin,        WeatherDescription,     SpeedWind,      DegreeWind];
    var $arrColor           = ["00,150,136","197,34,169",   "95,212,209",   "95,241,147",   "238,236,108",  "54,147,193",   "157,122,18",   "89,169,70",    "45,21,88",             "80,135,143",   "223,121,153",   "59,239,202","89,169,70"];

    var $sData              = "";
    var $sDate              = "";

    for (var i = 0, len = $date.length; i < len; i++) {
        $sDate += "\"" + $date[i].substring(5, 16) + "\", ";
    }


    var script = "<script>" +
        "   $(function() {" +
        "       var ctx = document.getElementById(\"ALLCHARTS\");" +
        "       " +
        "       var myChart = new Chart(ctx, {" +
        "           type: 'line'," +
        "           " +
        "           data: {" +
        "               labels: [" + $sDate + "]," +
        "               datasets: [";

    for(var i = 0, lenI = $arrWeather.length; i < lenI; i++){
        if($whatShow[i] == 1){
            $sData = "";
            $tmpArrWeather = $arrWeather[i];
            //console.log($tmpArrWeather);
            //console.log(i);
            for (var j = 0, lenJ = $tmpArrWeather.length; j < lenJ; j++) {
                $sData += $tmpArrWeather[j] + ", ";
            }

    script +=
            "                   {" +
            "                       label: '" + $chartLabels[i] + "'," +
            "                       data: [" + $sData + "]," +
            "                       backgroundColor: \"rgba(" + $arrColor[i] + ", 0.6)\",";
        if(i != 0)
    script +=
            "                       showLines: \"false\",";
    script +=
            "                   },";
        }
    }
    script +=
        "               ]" +
        "           }," +
        "       " +
        "           options: {" +
        "               scales: {" +
        "                   yAxes: [{" +
        "                       ticks: {" +
        "                           beginAtZero: true" +
        "                       }" +
        "                   }]" +
        "               }" +
        "           }" +
        "       });" +
        "   });" +
        "<\/script>";



    $("div.section#charts div#row").append("<h5 class=\"center\"> ALLCHARTS </h5><canvas id=\"ALLCHARTS\" width=\"400\" height=\"200\"></canvas>");
    $("canvas#ALLCHARTS").after(script);

}


jQuery(document).ready(function($){
    var timelineBlocks = $('.cd-timeline-block'),
        offset = 0.8;

    //hide timeline blocks which are outside the viewport
    hideBlocks(timelineBlocks, offset);

    //on scolling, show/animate timeline blocks when enter the viewport
    $(window).on('scroll', function(){
        (!window.requestAnimationFrame)
            ? setTimeout(function(){ showBlocks(timelineBlocks, offset); }, 100)
            : window.requestAnimationFrame(function(){ showBlocks(timelineBlocks, offset); });
    });

    function hideBlocks(blocks, offset) {
        blocks.each(function(){
            ( $(this).offset().top > $(window).scrollTop()+$(window).height()*offset ) && $(this).find('.cd-timeline-img, .cd-timeline-content').addClass('is-hidden');
        });
    }

    function showBlocks(blocks, offset) {
        blocks.each(function(){
            ( $(this).offset().top <= $(window).scrollTop()+$(window).height()*offset && $(this).find('.cd-timeline-img').hasClass('is-hidden') ) && $(this).find('.cd-timeline-img, .cd-timeline-content').removeClass('is-hidden').addClass('bounce-in');
        });
    }
});



