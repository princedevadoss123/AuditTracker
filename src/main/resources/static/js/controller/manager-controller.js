angular.module('trackerApp').controller('ManagerController', ['$scope', '$rootScope', '$http', '$filter',
  function($scope, $rootScope, $http, $filter) {
    $scope.clickhour = function() {
        $("div.ibox-content-hour").slideToggle(300);
        $("span.hour").toggleClass("glyphicon-chevron-up").toggleClass("glyphicon-chevron-down");
        if ($("div.ibox-hourwise").attr('class').includes('init-back')) {
          setTimeout(function() {
            $("div.ibox-hourwise").toggleClass("init-back").toggleClass("next-back");
          }, 300);
        } else {
          $("div.ibox-hourwise").toggleClass("init-back").toggleClass("next-back");
        }
      },
      $scope.clickhourremove = function() {
        $rootScope.hourwise = true;
      },
      $scope.clickteamgraph = function() {
          $("div.ibox-content-team-graph").slideToggle(300);
          $("span.team-graph").toggleClass("glyphicon-chevron-up").toggleClass("glyphicon-chevron-down");
          if ($("div.ibox-team-graph").attr('class').includes('init-back')) {
            setTimeout(function() {
              $("div.ibox-team-graph").toggleClass("init-back").toggleClass("next-back");
            }, 300);
          } else {
            $("div.ibox-team-graph").toggleClass("init-back").toggleClass("next-back");
          }
        },
      $scope.clicktable = function() {
        $("div.ibox-content-table").slideToggle(300);
        $("span.table1").toggleClass("glyphicon-chevron-up").toggleClass("glyphicon-chevron-down");
        if ($("div.ibox-table").attr('class').includes('init-back')) {
          setTimeout(function() {
            $("div.ibox-table").toggleClass("init-back").toggleClass("next-back");
          }, 300);
        } else {
          $("div.ibox-table").toggleClass("init-back").toggleClass("next-back");
        }
      },
      $scope.clickleader = function() {
        $("div.ibox-content-leader").slideToggle(300);
        $("span.leader").toggleClass("glyphicon-chevron-up").toggleClass("glyphicon-chevron-down");
        if ($("div.ibox-leader").attr('class').includes('init-back')) {
          setTimeout(function() {
            $("div.ibox-leader").toggleClass("init-back").toggleClass("next-back");
          }, 300);
        } else {
          $("div.ibox-leader").toggleClass("init-back").toggleClass("next-back");
        }
      },
      $scope.setSelectedTab = function(selected) {
        $rootScope.selectedTab = selected;
      },
      $scope.goHome = function() {
    	window.location.href="/home";  
      },
      $scope.initControl = function() {
    	
    	if(localStorage.getItem("username") === null) {
      		window.location.href="/";
      	}
        if ($rootScope.selectedTab == null) {
          $rootScope.selectedTab = 'datetab';
        }
        
        $scope.tracker = [];
        if ($rootScope.selectedTab == 'datetab') {
          $scope.daterange = true;
          $rootScope.partdate=false;
          $scope.date = false;
          $scope.timerange = true;
          $rootScope.hourwise = true;
          $scope.timeError = true;
          var currentDate = $filter('date')(new Date(), 'yyyy-MM-dd');
          $scope.tracker.PDate = new Date();
          $('#lidaterangetabli').removeClass("active");
          $('#litimetabli').removeClass("active");
          $('#lidatetabli').addClass("active");
          $('#daterangetab').removeClass("active in");
          $('#timetab').removeClass("active in");
          $('#datetab').addClass("active in");
          $scope.managerDateQueryWithParams(currentDate);
        } else if ($rootScope.selectedTab == 'daterangetab') {
          $scope.dateData = [];
          $rootScope.partdate=true;
          $scope.datelabels = [];
          $scope.series = ["Date"];
          $scope.timerange = true;
          $scope.date = true;
          $scope.daterange = false;
          var currentDate = new Date();
          var fiveDaysOld = new Date();
          fiveDaysOld.setDate(fiveDaysOld.getDate() - 5);
          $('#lidaterangetabli').addClass("active");
          $('#litimetabli').removeClass("active");
          $('#lidatetabli').removeClass("active");
          $('#daterangetab').addClass("active in");
          $('#timetab').removeClass("active in");
          $('#datetab').removeClass("active in");
          $scope.tracker.startDate = fiveDaysOld;
          $scope.tracker.endDate = currentDate;
          var startDate = $filter('date')(fiveDaysOld, 'yyyy-MM-dd');
          var endDate = $filter('date')(currentDate, 'yyyy-MM-dd');
          $scope.managerDRQueryWithParams(startDate, endDate);
        } else if ($rootScope.selectedTab == 'timetab') {
          $scope.date = true;
          $scope.timerange = false;
          $scope.daterange = true;
          var currentTime = new Date();
          var nineHoursOld = new Date();
          nineHoursOld.setHours(nineHoursOld.getHours() - 9, nineHoursOld.getMinutes());
          $('#lidaterangetabli').removeClass("active");
          $('#litimetabli').addClass("active");
          $('#lidatetabli').removeClass("active");
          $('#daterangetab').removeClass("active in");
          $('#timetab').addClass("active in");
          $('#datetab').removeClass("active in");
          $scope.tracker.P1Date = currentTime;
          $scope.tracker.startTime = nineHoursOld;
          $scope.tracker.endTime = currentTime;
          var date = $filter('date')(currentTime, 'yyyy-MM-dd');
          var startTime = $filter('date')(nineHoursOld, 'HH:mm');
          var endTime = $filter('date')(currentTime, 'HH:mm');
          $scope.managerTRQueryWithParams(date, startTime, endTime);
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
      },
      $scope.managerDRQuery = function() {
        $scope.daterange = false;
        $scope.timerange = true;
        $scope.date = true;
       
        var startDate = $filter('date')($scope.tracker.startDate, 'yyyy-MM-dd');
        var endDate = $filter('date')($scope.tracker.endDate, 'yyyy-MM-dd');
        $scope.managerDRQueryWithParams(startDate, endDate);
      },
      $scope.managerDRQueryWithParams = function(startDate, endDate) {
    	$rootScope.hourwise=true;
        $scope.dateDataMgr = [];
        $scope.datelabelsMgr = [];
        $scope.seriesMgr = [];
        $scope.inumrange = [];
        $rootScope.partdate=true;
        var request = $http.get("/manager/"+localStorage.getItem("username")+"/date/start/" + startDate + "/end/" + endDate + "/");
        request.then(function(response) {
          $scope.trackers = angular.copy(response.data);
          var i;
          if ($scope.trackers.length != 0) {
            for (i = 0; i < $scope.trackers.length; i++) {
              $scope.datelabelsMgr[i] = $scope.trackers[i].date;
            }
            
            var j = 0;
            for (i = 0; i < $scope.trackers[0].activeTimeInfoTO.length; i++) {
              $scope.dateDataMgr[i] = [];
              $scope.inumrange[i] = $scope.trackers[0].activeTimeInfoTO[i].employeeId;
            }
            for (i = 0; i < $scope.trackers.length; i++) {
            	var total=0;
              for (j = 0; j < $scope.trackers[i].activeTimeInfoTO.length; j++) {
                total=total+$scope.trackers[i].activeTimeInfoTO[j].activeTime;
              }
              $scope.dateDataMgr[i] = total/60;
            }
            
            $scope.optionYaxis = {
              responsive: true,
              maintainAspectRatio: true,
              responsiveAnimationDuration: 2000,
              tooltips: {
                enabled: false
              },
              title: {
                display: "true",
                text: "",
                fontSize: 16
              },

              scales: {
                  xAxes: [{
                    scaleLabel: {
                      display: true,
                      labelString: 'Date',
                      fontSize: 16,
                      fontColor: '#000000'
                    }
                  }],
                  yAxes: [{
                    scaleLabel: {
                      display: true,
                      labelString: 'Active time in hours',
                      fontSize: 16,
                      fontColor: '#000000'
                    },
                    ticks: {
                        min: 0
                      }
                  }]},
              animation: {
                animateRotate: true,
                animateScale: true
              }
            };
          }
        });
        request.error(function(response) {});
      },
      $scope.managerDateQuery = function() {
        $('#team-date-hmd-container').html("");
        $scope.daterange = true;
        $rootScope.hourwise = true;
        $scope.date = false;
        $scope.timerange = true;
        $scope.mgrsingledate = $filter('date')($scope.tracker.PDate, 'yyyy-MM-dd');
        $scope.managerDateQueryWithParams($scope.mgrsingledate);
      },
      $scope.managerDateQueryWithParams = function(date) {
    	  $scope.date1=date;
          
    	  
        var request = $http.get("/manager/"+localStorage.getItem("username")+"/date/" + date + "/");
        request.then(function(response) {
          $scope.empDataDate = [];
          $scope.empLabelDate = [];
          $scope.seriesdate = [];
          $scope.activemin = [];
          $scope.loggedin = [];
          $scope.rank = [];
          $scope.employees = [];
          $scope.trackers = angular.copy(response.data);
          console.log($scope.trackers);
          $scope.colordet = [];
          var trackerSize = $scope.trackers.length;
          var i;
          for (i = 0; i < trackerSize; i++) {
            $scope.colordet[i] = 'rgb(' + Math.floor((Math.random() * 255) + 1) + ',' + Math.floor((Math.random() * 255) + 1) + ',' + Math.floor((Math.random() * 255) + 1) + ')';
          }
          var dateall;
          var labeling = [];
          for (var i = 0; i < trackerSize; i++) {
            var display = CommonUtils.computeInactiveTime($scope.trackers[i].activeTime);
            labeling.push("active hours : " + display);
            if ($scope.trackers[i].date != null) {
              dateall = $scope.trackers[i].date;
            }
          }
          $scope.datemgroptions = {
            tooltips: {
              enabled: true,
              callbacks: {
                label: function(tooltipItem) {
                  var label = labeling[tooltipItem.index];
                  return label;
                }
              }
            },
           
            responsive: true,
            maintainAspectRatio: true,
            responsiveAnimationDuration: 2000,
            title: {
              display: "true",
              text: "",
              fontSize: 16
            },
            scales: {
                xAxes: [{
                  scaleLabel: {
                    display: true,
                    labelString: 'Employee',
                    fontSize: 16,
                    fontColor: '#000000'
                  }
                }],
                yAxes: [{
                  scaleLabel: {
                    display: true,
                    labelString: 'Active time in hours',
                    fontSize: 16,
                    fontColor: '#000000'
                  }
                  ,
                  ticks: {
                    min: 0,
                    max: 12
                  }
                }]},
            animation: {
              animateRotate: true,
              animateScale: true
            }
          };
          $scope.inum = [];
          for (var i = 0; i < trackerSize; i++) {
            $scope.empDataDate[i] = $scope.trackers[i].activeTime/60;
            $scope.activemin[i] = CommonUtils.computeInactiveTime($scope.trackers[i].activeTime);
            $scope.empLabelDate[i] = $scope.trackers[i].firstName + " " + $scope.trackers[i].lastName;
            $scope.inum[i] = $scope.trackers[i].employeeId;
          }
          $scope.mgrrows = $scope.trackers;
          var dates = new Date();
          var gdate = $filter('date')(dates, 'yyyy-MM-dd');
          if (date == gdate) {
            for (var i = 0; i < trackerSize; i++) {
              var start = $scope.trackers[i].startTime;
              var curr = $filter('date')(dates, 'HH:mm');
              var end = $scope.trackers[i].endTime;
              if (CommonUtils.checkDiff(start, curr) < 0) {
                $scope.loggedin[i] = '';
              } else {
                if (CommonUtils.checkDiff(curr, end) > 0) {
                  var diff = CommonUtils.computeDiff(start, curr);
                } else {
                  var diff = CommonUtils.computeDiff(start, end);
                }
                $scope.loggedin[i] = CommonUtils.computeInactiveTime(diff);
              }
            }
          } else {
            for (var i = 0; i < trackerSize; i++) {
              var start = $scope.trackers[i].startTime;
              var end = $scope.trackers[i].endTime;
              $scope.loggedin[i] = CommonUtils.computeInactiveTime(CommonUtils.computeDiff(start, end));
            }
          }
          var act=[],emp=[];
          for(var i=0;i<trackerSize;i++)
        	  {
        	  act[i] = $scope.trackers[i].activeTime;
        	  emp[i]  = $scope.trackers[i].firstName+" "+$scope.trackers[i].lastName;
        	  }
          
          for (var i = 0; i < trackerSize; i++) {
            for (var j = 0; j < trackerSize; j++) {
              if (act[i] > act[j]) {
                var tempname = emp[i];
                var tempact= act[i];
                act[i] = act[j];
                emp[i]=emp[j];
                
                act[j] = tempact;
                emp[j]=tempname;
              }
            }
          }
        
          for (var i = 0; i < 3; i++) {
            $scope.rank[i] = i + 1;
            $scope.employees[i] = emp[i];
            
          }
        });
        request.error(function(response) {});
      },
      $scope.managerTRQuery = function() {
        $scope.daterange = true;
        $scope.date = true;
        $scope.timerange = false;
        var startTime = $filter('date')($scope.tracker.startTime, 'HH:mm');
        var endTime = $filter('date')
          ($scope.tracker.endTime, 'HH:mm');
        var date = $filter('date')($scope.tracker.P1Date, 'yyyy-MM-dd');
        $scope.managerTRQueryWithParams(date, startTime, endTime);
      },
      $scope.managerTRQueryWithParams = function(date, startTime, endTime) {
        var request = $http.get("/manager/"+localStorage.getItem("username")+"/date/" + date + "/time/start/" + startTime + "/end/" + endTime + "/");
        var total = CommonUtils.computeDiff(startTime, endTime);
        request.then(function(response) {
          $scope.trackers = angular.copy(response.data);
          if (total > 0) {
            $scope.rangeDataMgr = [];
            $scope.rangelabelsMgr = [];
            $scope.rangeoptionsMgr = [];
            $scope.options = {
              cutoutPercentage: 60,
              tooltips: {
                enabled: true
              },
              responsive: true,
              maintainAspectRatio: true,
              responsiveAnimationDuration: 3000,
              title: {
                display: "true",
                text: "Total Active Time"
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
              var inactive = total - $scope.trackers[i].activeTime;
              $scope.rangeDataMgr[i] = [
                $scope.trackers[i].activeTime,
                total - $scope.trackers[i].activeTime
              ];
              $scope.rangelabelsMgr[i] = ["Active Time : " + CommonUtils.computeActiveTime($scope.trackers[i].activeTime), "Inactive Time : " + CommonUtils.computeInactiveTime(inactive)];
              $scope.rangeoptionsMgr[i] = {
                cutoutPercentage: 60,
                responsive: false,
                maintainAspectRatio: false,
                title: {
                  display: "true",
                  text: $scope.trackers[i].firstName + " " + $scope.trackers[i].lastName + " (" + $scope.trackers[i].employeeId + ")"
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
          } else {
            $scope.timeError = false;
          }
        });
      }, $scope.daterangeprocessMgr = function() {
        $scope.date = true;
        $scope.daterange = false;
        $scope.timerange = true;
      }, $scope.dateprocessMgr = function() {
        $scope.date = false;
        $scope.daterange = true;
        $scope.timerange = true;
      }, $scope.timerangeprocessMgr = function() {
        $scope.date = true;
        $scope.daterange = true;
        $scope.timerange = false;
      }
  }
]);