var app = angular.module('trackerApp').controller(
    'AdminController', ['$scope', '$rootScope', '$http', '$filter', function($scope, $rootScope, $http, $filter) {

        $scope.setSelectedTab = function(selected) {
                $rootScope.selectedTab = selected;
            },

            $scope.employeeDRQuery = function() {

                $scope.dateData = [];
                $scope.datelabels = [];

                $scope.series = ["Date"];
                $scope.timerange = true;
                $scope.date = true;
                $scope.daterange = false;
                var employee_id = $scope.E_id;
                var startDate = $filter('date')(
                    $scope.tracker.sDate, 'yyyy-MM-dd');
                var endDate = $filter('date')($scope.tracker.eDate,
                    'yyyy-MM-dd');
                console.log("-------------");
                console.log(employee_id);
                $scope.employeeDRQueryWithParams(startDate, endDate,employee_id);
            },


            $scope.employeeDRQueryWithParams = function(startDate, endDate ,employee_id) {
                var request = $http.get("/tracker/admin/" + employee_id + "/employee/date/start/" + startDate + "/end/" + endDate + "/");

                request.then(function(response) {
                    $scope.trackers = angular.copy(response.data);
                    if ($scope.trackers.length == 0) {
                        $scope.datecorrect = true;
                    } else {

                        $scope.dateerror = true;
                        $scope.datecorrect = false;
                        
                        

                        $scope.dateoptions = {
                            cutoutPercentage: 60,
                            tooltips: {
                                enabled: true
                            },
                            responsive: false,
                            maintainAspectRatio: false,
                            responsiveAnimationDuration: 7000,
                            title: {
                                display: "true",
                                text: "Employee ID : " + $scope.trackers[0].employeeId,
                                fontSize: 24
                            },
                            animation: {
                                animateRotate: true,
                                animateScale: true
                            },
                            scales: {
                                yAxes: [{
                                    type: "linear",
                                    label: "y-axis-1",
                                    display: true,
                                    position: "left",
                                    
                                    ticks: {
                                    	min:0,
                                    	stepSize:10
                                    }
                                }]
                            },
                            legend: {
                                display: true,
                                position: "bottom"
//                                labels: {
//
//                                    fontColor: 'rgb(0, 0, 0)'
//                                }
                            }
                        };

                        
                        for (var i = 0; i < $scope.trackers.length; i++) {
                            $scope.dateData[i] = $scope.trackers[i].activeTime ;
                          
                            $scope.datelabels[i] = $scope.trackers[i].date + "" ;
                        }
                        
                        
                        console.log($scope.dateData);
                    }
                    
                });

                request.error(function(response) {

                });
            },
            $scope.employeeTRQuery = function() {
                $scope.date = true;
                $scope.timerange = false;
                $scope.daterange = true;
                var employee_id1=$scope.tracker.employee_id1;
                var Tdate = $filter('date')(
                    $scope.tracker.TDate, 'yyyy-MM-dd');
                var emp_startTime = $filter('date')(
                    $scope.tracker.emp_startTime, 'HH:mm');
                var emp_endTime = $filter('date')(
                    $scope.tracker.emp_endTime, 'HH:mm');
               console.log("-------");
               console.log(employee_id1 +" " + Tdate +" " +emp_startTime+ " " +  emp_endTime);
                $scope.employeeTRQueryWithParams(Tdate, emp_startTime, emp_endTime, employee_id1);
                
            },

            $scope.employeeTRQueryWithParams = function(Tdate, emp_startTime, emp_endTime, employee_id1) {
                var request = $http.get("/tracker/admin/" + employee_id1 +"/employee/date/" + Tdate + "/time/start/" + emp_startTime + "/end/" + emp_endTime + "/");
                var sttime = emp_startTime + "";
                var edtime = emp_endTime + "";
                

                var start_time = parseInt(sttime.substring(3, 5)) + parseInt(sttime.substring(0, 2)) * 60;
                var end_time = parseInt(edtime.substring(3, 5)) + parseInt(edtime.substring(0, 2)) * 60;
                

                var total = end_time - start_time;
                console.log(total);
                request.then(function(response) {
                    $scope.trackers = angular.copy(response.data);

                    var inactive = total - $scope.trackers.activeTime;
                    console.log(inactive);
                    if (total > 0) {
                        $scope.timeerror = true;
                        $scope.timecorrect = false;
                        $scope.rangecolors = ["#2980B9", "#666666"];

                        $scope.rangeoptions = {
                            cutoutPercentage: 60,
                            tooltips: {
                                enabled: true
                            },
                            responsive: false,
                            maintainAspectRatio: false,
                            responsiveAnimationDuration: 7000,
                            title: {
                                display: "true",
                                text: "Active Minutes for given Time Range"
                            },
                            animation: {
                                animateRotate: true,
                                animateScale: true
                            },
                            legend: {

                                display: true,
                                position: "bottom",

                                labels: {

                                    fontColor: 'rgb(0, 0, 0)'
                                }
                            }
                        };
                        $scope.rangeData = [$scope.trackers.activeTime, total - $scope.trackers.activeTime];
                        console.log("164" + $scope.rangeData);
                        console.log($scope.trackers);
                        if ($scope.trackers.activeTime > 60) {
                            var hour_data = $scope.trackers.activeTime / 60;
                            var min_data = $scope.trackers.activeTime % 60;
                            if (hour_data / 10 == 0) {
                                hour_data = "0" + hour_data;
                            }
                            if (min_data / 10 == 0) {
                                min_data = "0" + min_data;
                            }
                            var display = hour_data + ":" + min_data;
                            console.log("175" + display);
                            $scope.rangelabels = ["Active Time : " + $scope.trackers.activeTime 
                                                  + " mins",  "Inactive Time : " + display + " hours"];
                        }
                        if (inactive > 60) {
                            var hour_data = (inactive) / 60;
                            console.log(Math.floor(hour_data));
                            var min_data = inactive % 60;
                            if (hour_data / 10 == 0) {
                                hour_data = "0" + hour_data;
                            }
                            if (min_data / 10 == 0) {
                                min_data = "0" + min_data;
                            }

                            var display = Math.floor(hour_data) + " hrs : " + min_data + " mins";
                            $scope.rangelabels = ["Active Time : " + $scope.trackers.activeTime 
                                                  + " mins",  "Inactive Time : " + display];

                        } else {
                            $scope.rangelabels = ["Active Time : " + $scope.trackers.activeTime + " mins",  "Inactive Time : " 
                                                  + (total - $scope.trackers.activeTime) + " mins"];
                        }
                        request.error(function(response) {

                        });
                    } else {
                        $scope.timeerror = false;
                        $scope.timecorrect = true;
                    }
                });
            },
            $scope.employeeDateQuery = function() {
                $scope.daterange = true;
                $scope.timerange = true;
                $scope.date = false;
                var Employee_id=$scope.tracker.e_id;
                console.log(Employee_id);
                		var date = $filter('date')(
                    $scope.tracker.Pdate, 'yyyy-MM-dd');
                $scope.employeeDateQueryWithParams(date,Employee_id);
            },

            $scope.employeeDateQueryWithParams = function(Pdate,e_id) {
            	
                var request = $http.get("/tracker/admin/" + e_id + "/employee/date/" + Pdate + "/");

             

                request.then(function(response) {
                   var hours = [];
                    $scope.hourDataList = [];
                    $scope.hourLabelList = [];
                    var  totalActiveTime = 0;
                   
                    $scope.options1 = [];
                    $scope.options = {
                        cutoutPercentage: 60,
                        tooltips: {
                            enabled: true
                        },
                        responsive: false,
                        maintainAspectRatio: false,
                        responsiveAnimationDuration: 7000,
                        title: {
                            display: "true",
                            text: "Active Minutes for given Employee"
                        },
                        animation: {
                            animateRotate: true,
                            animateScale: true
                        },
                        legend: {

                            display: true,
                            position: "bottom",

                            labels: {

                                fontColor: 'rgb(0, 0, 0)'
                            }
                        }
                    };
                    $scope.trackers = angular.copy(response.data);
                    console.log($scope.trackers);
                    var  trackerSize = $scope.trackers.activeTime.length;
                    for (var  i = 0; i < trackerSize; i++) {
                        
                        totalActiveTime = totalActiveTime + $scope.trackers.activeTime[i].activeTime;
                    }
                    $scope.labels = ["Active Time : " + totalActiveTime + " mins",  "Inactive Time : " 
                                     + ((trackerSize * 15) - totalActiveTime) + " mins"];
                    $scope.totalData = [totalActiveTime, (trackerSize * 15) - totalActiveTime];
                    console.log($scope.trackers);
                    Highcharts.createElement('link', {
                        href: 'https://fonts.googleapis.com/css?family=Signika:400,700',
                        rel: 'stylesheet',
                        type: 'text/css'
                    }, null, document.getElementsByTagName('head')[0]);

                    // Add the background image to the container
                    var finalize=[];
                    var final_data=[];
                    for(var i=0;i<4;i++)
                        {
                        final_data[i]=[];
                        for(var j=0;j<24;j++)
                        {
                              final_data[i][j]=0;
                        }
                        }
                    for(var j=0;j<96;j++)
                        {
                        var x=parseInt($scope.trackers.activeTime[j].min);
                        var y=parseInt($scope.trackers.activeTime[j].hour);
                          console.log(x/15,y);
                          
                        final_data[(x/15)-1][y]=$scope.trackers.activeTime[j].activeTime;
                        }
                    var count=0;
                    for(var i=0;i<24;i++)
                        {
                        
                        for(var j=0,h=3;j<4;j++,h--)
                              {
                              finalize[count]=[];
                                                finalize[count][0]=i;
                                                finalize[count][1]=j;
                                                finalize[count][2]=final_data[h][i];
                                                count++;
                                          
                                    
                              }
                        }
            
                    theme(finalize);

                });
                request.error(function(response) {

                });
            },
            
            
            
            $scope.teamDRQuery = function() {
            	console.log("--------hello-------");
                $scope.DateRange = false;
                $scope.TimeRange = true;
                $scope.Date = true;
                
                var startDate = $filter('date')($scope.tracker.teamstartDate, 'yyyy-MM-dd');
                var endDate = $filter('date')($scope.tracker.teamendDate, 'yyyy-MM-dd');
                var manager_id=$scope.tracker.team_manager_id;
                $scope.teamDRQueryWithParams(startDate, endDate, manager_id);
            },
            
            $scope.teamDRQueryWithParams = function(teamstartDate, teamendDate, team_manager_id) {
                $scope.dateDataMgr = [];
                $scope.datelabelsMgr = [];
                $scope.seriesMgr = [];
                $scope.optionYaxis = {
                		
                    scales: {
                        yAxes: [{


                            ticks: {
                                min: 0,
                                stepSize: 20,
                            }

                        }]
                    }

                };

               
                var request = $http.get("/tracker/admin/" +team_manager_id+"/team/date/start/"+teamstartDate+"/end/"+teamendDate+ "/");
                request.then(function(response) {

                    $scope.trackers = angular.copy(response.data);
                    console.log("----------");
                    console.log($scope.trackers);
                    var uniqueId = [];
                    var uniqueName = [];
                    for (var i = 0; i < $scope.trackers.length; i++) {
                        var flag = 0;
                        for (var j = 0; j < uniqueId.length; j++) {


                            if ($scope.trackers[i].employeeId == uniqueId[j]) {
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 0) {
                            uniqueId.push($scope.trackers[i].employeeId);
                            uniqueName.push($scope.trackers[i].firstName+" "+$scope.trackers[i].lastName);
                        }
                    }

                    console.log(uniqueId);
                    console.log(uniqueName);

                    var uniqueDate = [];
                    for (var i = 0; i < $scope.trackers.length; i++) {
                        var flag = 0;
                        for (var j = 0; j < uniqueDate.length; j++) {


                            if ($scope.trackers[i].date == uniqueDate[j]) {
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 0) {
                            uniqueDate.push($scope.trackers[i].date);
                        }
                    }

                    uniqueDate.sort();
                    console.log(uniqueDate);

                    $scope.seriesMgr = uniqueId;
                    $scope.datelabelsMgr = uniqueDate;

                    console.log($scope.seriesMgr);
                    console.log($scope.datelabelsMgr);


                    for (var i = 0; i < $scope.seriesMgr.length; i++) {
                        var a = [];
                        for (var j = 0; j < $scope.datelabelsMgr.length; j++) {
                            for (var k = 0; k < $scope.trackers.length; k++) {
                                if ($scope.datelabelsMgr[j] == $scope.trackers[k].date && $scope.seriesMgr[i] == $scope.trackers[k].employeeId) {
                                    a.push($scope.trackers[k].activeTime);
                                }
                            }
                        }
                        console.log(a);
                        $scope.dateDataMgr.push(a);
                    }
                    
                    $scope.seriesMgr = uniqueName;
                });

                request.error(function(response) {
                    alert("ERROR");
                    console.log("ERROR");
                });

            },
            
            $scope.teamDateQuery = function() {
            	$scope.DateRange = true;
                $scope.TimeRange = true;
                $scope.Date = false;
                
                var date = $filter('date')(
                    $scope.tracker.teamDate, 'yyyy-MM-dd');
                var manager_id=$scope.tracker.team_mgr_id;
                
                $scope.teamDateQueryWithParams(date,manager_id);
                
            },
            
            $scope.teamDateQueryWithParams = function(teamDate,team_mgr_id) {
                
                var request = $http.get("/tracker/admin/" +team_mgr_id+ "/team/date/" +teamDate+ "/");


                request.then(function(response) {

                    $scope.empDataList = [];
                    $scope.empLabelList = [];

                    $scope.colors = ["#2980B9", "#666666"];
                    $scope.options1 = [];
                    $scope.options = {
                        cutoutPercentage: 60,
                        tooltips: {
                            enabled: true
                        },
                        responsive: false,
                        maintainAspectRatio: false,
                        responsiveAnimationDuration: 7000,
                        title: {
                            display: "true",
                            text: "Active Minutes for given Employee"
                        },
                        animation: {
                            animateRotate: true,
                            animateScale: true
                        },
                        legend: {

                            display: true,
                            position: "top",

                            labels: {

                                fontColor: 'rgb(0, 0, 0)'
                            }
                        }
                    };
                    $scope.trackers = angular.copy(response.data);
                    var trackerSize = $scope.trackers.length;


                    for (var i = 0; i < trackerSize; i++) {



                        $scope.empDataList[i] = [$scope.trackers[i].activeTime, (60 * 8) - $scope.trackers[i].activeTime];
                        $scope.empLabelList[i] = ["Active Time : " + $scope.trackers[i].activeTime + " mins", "Inactive Time : " + ((60 * 8) - $scope.trackers[i].activeTime) + " mins"];

                        $scope.options1[i] = {
                            cutoutPercentage: 60,
                            responsive: false,
                            maintainAspectRatio: false,
                            title: {
                                display: "true",
                                text: $scope.trackers[i].firstName+" "+$scope.trackers[i].lastName+" ("+$scope.trackers[i].employeeId+")"
                            },
                            animation: {
                                animateRotate: true,
                                animateScale: true
                            },
                            legend: {

                                display: true,
                                position: "bottom",

                                labels: {
                                    fontSize: 10,
                                    fontColor: 'rgb(0, 0, 0)'
                                }
                            }
                        };

                    }
                    console.log($scope.trackers);
                });
                request.error(function(response) {
                    alert("ERROR");
                    console.log("ERROR");
                });

            },
            
            
            $scope.teamTRQuery = function() {
                $scope.DateRange = true;
                $scope.Date = true;
                $scope.TimeRange = false;
                	
                var startTime = $filter('date')(
                    $scope.tracker.team_startTime, 'HH:mm');
                var endTime = $filter('date')(
                    $scope.tracker.team_endTime, 'HH:mm');
                var date = $filter('date')(
                    $scope.tracker.team_Date, 'yyyy-MM-dd');
                var employee_id=$scope.tracker.teamemployeeid;
                //console.log("----------------hello");
               // console.log($scope.employee_id);
                $scope.teamTRQueryWithParams(date, startTime, endTime,employee_id);
            },
            
            $scope.teamTRQueryWithParams = function(date, startTime, endTime, employee_id) {
                

                var request = $http.get("/tracker/admin/"+employee_id+"/team/date/"+date+"/time/start/"+startTime+"/end/"+endTime+"/");

                var sttime = startTime + "";
                var edtime = endTime + "";

                var start_time = parseInt(sttime.substring(3, 5)) + parseInt(sttime.substring(0, 2)) * 60;
                var end_time = parseInt(edtime.substring(3, 5)) + parseInt(edtime.substring(0, 2)) * 60;

                var total = end_time - start_time;
                //console.log("---------total");
               // console.log(total);



                request.then(function(response) {
                    $scope.trackers = angular.copy(response.data);


                    if (total > 0) {

                        $scope.rangeDataMgr = [];
                        $scope.rangelabelsMgr = [];

                        $scope.rangecolorsMgr = ["#2980B9", "#666666"];
                        $scope.rangeoptionsMgr = [];
                        $scope.options = {
                            cutoutPercentage: 60,
                            tooltips: {
                                enabled: true
                            },
                            responsive: false,
                            maintainAspectRatio: false,
                            responsiveAnimationDuration: 7000,
                            title: {
                                display: "true",
                                text: "Active Minutes for given Employee"
                            },
                            animation: {
                                animateRotate: true,
                                animateScale: true
                            },
                            legend: {

                                display: true,
                                position: "top",

                                labels: {

                                    fontColor: 'rgb(0, 0, 0)'
                                }
                            }
                        };
                        $scope.trackers = angular.copy(response.data);

                        var trackerSize = $scope.trackers.length;

                        for (var i = 0; i < trackerSize; i++) {

                    console.log("---------activeTime");
                          console.log($scope.trackers[i].activeTime);
                            var inactive = total - $scope.trackers[i].activeTime;
                           console.log("---------inactiveTime");
                          console.log(inactive);


                            $scope.rangeDataMgr[i] = [$scope.trackers[i].activeTime, total - $scope.trackers[i].activeTime];

                            if ($scope.trackers[i].activeTime > 60) {
                                var hour_data = $scope.trackers[i].activeTime / 60;
                                var min_data = $scope.trackers[i].activeTime % 60;
                                if (hour_data / 10 == 0) {
                                    hour_data = "0" + hour_data;
                                }
                                if (min_data / 10 == 0) {
                                    min_data = "0" + min_data;
                                }
                                var display = hour_data + ":" + min_data;
                                console("------active time>60" + display);
                                $scope.rangelabelsMgr[i] = ["Active Time : " + $scope.trackers[i].activeTime + " mins", "Inactive Time : " + display + " hours"];
                            }
                            if (inactive > 60) {
                                var hour_data = (inactive) / 60;
                                console.log(Math.floor(hour_data));
                                var min_data = inactive % 60;
                                if (hour_data / 10 == 0) {
                                    hour_data = "0" + hour_data;
                                }
                                if (min_data / 10 == 0) {
                                    min_data = "0" + min_data;
                                }

                                var display = Math.floor(hour_data) + " hrs : " + min_data + " mins";
                                $scope.rangelabelsMgr[i] = ["Active Time : " + $scope.trackers[i].activeTime + " mins", "Inactive Time : " + display];

                            } else {

                                $scope.rangelabelsMgr[i] = ["Active Time : " + $scope.trackers[i].activeTime + " mins", "Inactive Time : " + (total - $scope.trackers[i].activeTime) + " mins"];
                            }


                            $scope.rangeoptionsMgr[i] = {
                                cutoutPercentage: 60,
                                responsive: false,
                                maintainAspectRatio: false,
                                title: {
                                    display: "true",
                                    text: $scope.trackers[i].firstName+" "+$scope.trackers[i].lastName+" ("+$scope.trackers[i].employeeId+")"
                                },
                                animation: {
                                    animateRotate: true,
                                    animateScale: true
                                },
                                legend: {

                                    display: true,
                                    position: "bottom",

                                    labels: {
                                        fontSize: 10,
                                        fontColor: 'rgb(0, 0, 0)'
                                    }
                                }
                            };

                        }
                        console.log($scope.trackers);

                    } else {
                        $scope.timeError = false;

                    }
                });
            },


            
            
            
            
           
            
            
            /** Page default Initializations START **/
            $scope.initControl = function() {
            	console.log($rootScope.selectedTab);
                if ($rootScope.selectedTab == null) {
                    $rootScope.selectedTab = 'datetab';
                }
                $scope.tracker = [];
                if ($rootScope.selectedTab == 'datetab') {
                    $scope.daterange = true;
                    $scope.date = false;
                    $scope.timerange = true;
                    $scope.timeerror = true;
                    $scope.timecorrect = true;
                    $scope.dateerror = true;
                    $scope.datecorrect = true;
                    $scope.DateRange = true;
                    $scope.TimeRange = true;
                    $scope.Date = true;

                    var currDate = new Date();
                    var currentDate=$filter('date')(currDate, 'yyyy-MM-dd');
                    var employee_id = $scope.employee_id;
                    $scope.tracker.Date = new Date();
                    $('#lidaterangetabli').removeClass("active");
                    $('#litimetabli').removeClass("active");
                    $('#lidatetabli').addClass("active");

                    $('#daterangetab').removeClass("active in");
                    $('#timetab').removeClass("active in");
                    $('#datetab').addClass("active in");

                    $scope.employeeDateQueryWithParams(currentDate,employee_id);

                } else if ($rootScope.selectedTab == 'daterangetab') {
                    $scope.dateData = [];
                    $scope.datelabels = [];

                    $scope.series = ["Date"];
                    $scope.timerange = true;
                    $scope.date = true;
                    $scope.daterange = false;

                    var currentDate = new Date();
                    var fiveDaysOld = new Date();
                    var employee_id = $scope.employee_id;
                    fiveDaysOld.setDate(fiveDaysOld.getDate() - 5);

                    $('#lidaterangetabli').addClass("active");
                    $('#litimetabli').removeClass("active");
                    $('#lidatetabli').removeClass("active");

                    $('#daterangetab').addClass("active in");
                    $('#timetab').removeClass("active in");
                    $('#datetab').removeClass("active in");

                    $scope.tracker.startDate = fiveDaysOld;
                    $scope.tracker.endDate = currentDate;

                    var sDate = $filter('date')(fiveDaysOld, 'yyyy-MM-dd');
                    var eDate = $filter('date')(currentDate,
                        'yyyy-MM-dd');
                    $scope.employeeDRQueryWithParams(sDate, eDate,employee_id);
                    
                } else if ($rootScope.selectedTab == 'timetab') {
                    $scope.date = true;
                    $scope.timerange = false;
                    $scope.daterange = true;

                    var currentTime = new Date();
                    var nineHoursOld = new Date();
                   // var emp_id=$scope.tracker.employee_id1;
                    nineHoursOld.setHours(nineHoursOld.getHours() - 9, nineHoursOld.getMinutes());

                    $('#lidaterangetabli').removeClass("active");
                    $('#litimetabli').addClass("active");
                    $('#lidatetabli').removeClass("active");

                    $('#daterangetab').removeClass("active in");
                    $('#timetab').addClass("active in");
                    $('#datetab').removeClass("active in");
                 //   Tdate, emp_startTime, emp_endTime, emp_id
                    $scope.tracker.TDate = currentTime;
                    $scope.tracker.emp_startTime = nineHoursOld;
                    $scope.tracker.emp_endTime = currentTime;

                    var date = $filter('date')(
                        currentTime, 'yyyy-MM-dd');
                    var startTime = $filter('date')(
                        nineHoursOld, 'HH:mm');
                    var endTime = $filter('date')(
                        currentTime, 'HH:mm');
                    var emp_id=$scope.tracker.employee_id1;
                    $scope.employeeTRQueryWithParams(date, startTime, endTime,emp_id);
                }
                else if ($rootScope.selectedTab == 'daterangeteamtab') {
                    $scope.dateData = [];
                    $scope.datelabels = [];

                    $scope.series = ["Date"];
                    $scope.TimeRange = true;
                    $scope.Date = true;
                    $scope.DateRange = false;

                    var currentDate = new Date();
                    var fiveDaysOld = new Date();
                    
                    fiveDaysOld.setDate(fiveDaysOld.getDate() - 5);

                    $('#lidaterangeteamtabli').addClass("active");
                    $('#litimeteamtabli').removeClass("active");
                    $('#lidateteamtabli').removeClass("active");

                    $('#daterangeteamtab').addClass("active in");
                    $('#timeteamtab').removeClass("active in");
                    $('#dateteamtab').removeClass("active in");

                    $scope.tracker.startDate = fiveDaysOld;
                    $scope.tracker.endDate = currentDate;
                    var manager_id=$scope.tracker.team-manager_id;
                    var startDate = $filter('date')(fiveDaysOld, 'yyyy-MM-dd');
                    var endDate = $filter('date')(currentDate,
                        'yyyy-MM-dd');
                    
                    $scope.teamDRQueryWithParams(startDate, endDate,manager_id);
            }
                else if  ($rootScope.selectedTab == 'dateteamtab') {
                    $scope.dateData = [];
                    $scope.datelabels = [];

                    $scope.series = ["Date"];
                    $scope.TimeRange = true;
                    $scope.Date = false;
                    $scope.DateRange = true;;

                    var currentDate = new Date();
                    var fiveDaysOld = new Date();
                    fiveDaysOld.setDate(fiveDaysOld.getDate() - 5);

                    $('#lidaterangeteamtabli').addClass("active");
                    $('#litimeteamtabli').removeClass("active");
                    $('#lidateteamtabli').removeClass("active");

                    $('#daterangeteamtab').addClass("active in");
                    $('#timeteamtab').removeClass("active in");
                    $('#dateteamtab').removeClass("active in");

                    $scope.tracker.startDate = fiveDaysOld;
                    $scope.tracker.endDate = currentDate;

                    var manager_id=$scope.tracker.team-mgr_id;

                    var currentDate = $filter('date')(new Date(), 'yyyy-MM-dd');

                    $scope.teamDateQueryWithParams(currentDate,manager_id);
                }
                else if ($rootScope.selectedTab == 'timeteamtab') {
                    $scope.Date = true;
                    $scope.TimeRange = false;
                    $scope.DateRange = true;

                    var currentTime = new Date();
                    var nineHoursOld = new Date();
                    nineHoursOld.setHours(nineHoursOld.getHours() - 9, nineHoursOld.getMinutes());

                    $('#lidaterangeteamtabli').removeClass("active");
                    $('#litimeteamtabli').addClass("active");
                    $('#lidateteamtabli').removeClass("active");

                    $('#daterangeteamtab').removeClass("active in");
                    $('#timeteamtab').addClass("active in");
                    $('#dateteamtab').removeClass("active in");

                    $scope.tracker.teamDate = currentTime;
                    $scope.tracker.team_startTime = nineHoursOld;
                    $scope.tracker.team_endTime = currentTime;

                    var date = $filter('date')(
                        currentTime, 'yyyy-MM-dd');
                    var startTime = $filter('date')(
                        nineHoursOld, 'HH:mm');
                    var endTime = $filter('date')(
                        currentTime, 'HH:mm');
                    var employee_id=$scope.tracker.team-employee_id;

                    $scope.teamTRQueryWithParams(date, startTime, endTime,employee_id);

                }
            },
            
          $scope.daterangeprocess = function() {
                $scope.daterange = true;
                $scope.date = true;
                $scope.timerange = true;
            },
            $scope.dateprocess = function() {
                $scope.daterange = true;
                $scope.date = true;
                $scope.timerange = true;
            },
            $scope.timerangeprocess = function() {
                $scope.daterange = true;
                $scope.date = true;
                $scope.timerange = true;
            }
    }]
);