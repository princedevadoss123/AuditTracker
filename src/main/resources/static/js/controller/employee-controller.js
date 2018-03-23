var app = angular.module('trackerApp').controller('EmployeeController', ['$scope', '$rootScope', '$http', '$filter',
  function($scope, $rootScope, $http, $filter) {
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
    $scope.employeeDRQuery = function() {
        $scope.daterangedaily = true;
        $scope.dateData = [];
        $scope.datelabels = [];
        $scope.dateseries = [];
        $scope.date = true;
        $scope.daterange = false;
        var startDate = $filter('date')($scope.tracker.startDate, 'yyyy-MM-dd');
        var endDate = $filter('date')($scope.tracker.endDate, 'yyyy-MM-dd');
        $scope.employeeDRQueryWithParams(startDate, endDate);
      },
      $scope.past = function(){
    	  console.log("hello");
    	  window.location = "/week";
      }, 

      $scope.individual = function(points, evt) {
        var date = $scope.datelabels[
          (points[0]._index)];
        $scope.employeeDateQueryWithParams(date, "employee-dr-hmd-container");
      },
      $scope.week = function(points, evt) {
        $('#week-range').addClass("active");
        $('#month-range').removeClass("active");
        $('#three-month-range').removeClass("active");
        $scope.calculateDateRange(7);
      },
      $scope.month = function(points, evt) {
        $('#week-range').removeClass("active");
        $('#month-range').addClass("active");
        $('#three-month-range').removeClass("active");
        $scope.calculateDateRange(30);
      },
      $scope.threeMonth = function(points, evt) {
        $('#week-range').removeClass("active");
        $('#month-range').removeClass("active");
        $('#three-month-range').addClass("active");
        $scope.calculateDateRange(90);
      },
      $scope.calculateDateRange = function(range) {
        $('#employee-dr-hmd-container').html("");
        $scope.dateData = [];
        $scope.datelabels = [];
        $scope.series = ["Date"];
        $scope.date = true;
        $scope.daterange = false;
        var currentDate = new Date();
        var xDaysOld = new Date();
        xDaysOld.setDate(xDaysOld.getDate() - range);
        var startDate = $filter('date')(xDaysOld, 'yyyy-MM-dd');
        var endDate = $filter('date')(currentDate, 'yyyy-MM-dd');
        $scope.employeeDRQueryWithParams(startDate, endDate);
      },
      $scope.employeeDRQueryWithParams = function(startDate, endDate) {
        var request = $http.get("/employee/"+localStorage.getItem("username")+"/date/start/" + startDate + "/end/" + endDate + "/");
        request.then(function(response) {
          $scope.trackers = angular.copy(response.data);
          var currentDate = $filter('date')(new Date(), 'yyyy-MM-dd');
  
          if($scope.trackers.length==1&&$scope.trackers[0].date==currentDate&&$scope.trackers[0].activeTime==0)
    	  {
        	  $('#RangeActiveDate').html("");
        	  $('#RangeActiveDate').width(0);
        	  $('#RangeActiveDate').height(0);
        	  $('#employee-dr-hmd').html("Data not Available");
        	  $('#employee-dr-hmd').css("color","red");
        	  $('#employee-dr-hmd').css("padding-top","250px");
        	  $('#employee-dr-hmd').css("padding-left","450px");
        	  $('#employee-dr-hmd').css("padding-top","250px");
        	  $('#employee-dr-hmd').css("font-size","30px");
        	  $('#employee-dr-hmd').width(550);
        	  $('#employee-dr-hmd').height(250);
        	  
        	  
              
    	  }
          else{
        	  $('#RangeActiveDate').width(1000);
        	  $('#RangeActiveDate').height(500);
        	  $('#employee-dr-hmd').html("");
        	  $('#employee-dr-hmd').css("padding-top","0px");
        	  $('#employee-dr-hmd').css("padding-left","0px");
        	  $('#employee-dr-hmd').width(0);
        	  $('#employee-dr-hmd').height(0);
        	  console.log($scope.trackers);
          var labeling = [];
          for (var i = 0; i < $scope.trackers.length; i++) {
            var display = CommonUtils.computeActiveTime($scope.trackers[i].activeTime);
            labeling.push(display);
          }
          if ($scope.trackers.length > 0) {
            var datas = [];
            for (var i = 0; i < $scope.trackers.length; i++) {
              datas[i] = $scope.trackers[i].activeTime / 60;
              $scope.datelabels[i] = $scope.trackers[i].date + "";
            }
            $scope.dateData = [
              datas
            ];
            var temp = [];
            if ($scope.trackers.firstName == undefined) {
              temp.push($scope.trackers[0].firstName + ' ' + $scope.trackers[0].lastName);
            } else {
              temp.push($scope.trackers.firstName + ' ' + $scope.trackers.lastName);
            }
            $scope.dateseries = temp;
            $scope.dateoptions = {
              cutoutPercentage: 60,
              tooltips: {
                callbacks: {
                  title: function(tooltipItem) {
                    var label = labeling[tooltipItem.index];
                    return
                    label;
                  },
                  label: function(tooltipItem) {
                    var label = labeling[tooltipItem.index];
                    if ($scope.trackers.firstName == undefined) {
                      return
                      label;
                    }
                    return
                    label;
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
              animation: {
                animateRotate: true,
                animateScale: true
              },
              title: {       
                  display: "true",
                  text: $scope.trackers[0].firstName+" "+$scope.trackers[0].lastName+" ( "+startDate+"  to  "+endDate+" )",
                  fontSize:16      
                },
              scales: {
                xAxes: [{
                  scaleLabel: {
                    display: true,
                    labelString: 'Date',
                    fontSize: 16,
                    fontColor: '#000000'
                  },
                  
                }],
                yAxes: [{
                  scaleLabel: {
                    display: true,
                    labelString: 'Active time in hours',
                    fontSize: 16,
                    fontColor: '#000000'
                  },
                  ticks: {
                      min: 0,
                      max: 12}
                }]
              }
              
            };
          }
        }
        });
        request.error(function(response) {});
      },
      $scope.employeeDateQuery = function() {
        $scope.daterangedaily = true;
        $scope.daterange = true;
        $scope.timerange = true;
        $scope.date = false;
        var date = $filter('date')($scope.tracker.Date, 'yyyy-MM-dd');
        $scope.employeeDateQueryWithParams(date, "employee-date-hmd-container");
      },
      $scope.employeeDateQueryWithParams = function(date, divId) {    
        var request = $http.get("/employee/"+localStorage.getItem("username")+"/date/" + date + "/");    
        request.then(function(response) {     
          var hours = [];     
          $scope.hourDataList = [];     
          $scope.hourLabelList = [];     
          var totalActiveTime = 0;     
          $scope.options1 = [];     
          $scope.options = {      
            cutoutPercentage: 60,
                  tooltips: {       
              enabled: true      
            },
                  responsive: true,
                  
            maintainAspectRatio: true,
                  
            responsiveAnimationDuration: 2000,
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
                     position: "bottom",
                     labels: {        
                fontColor: 'rgb(0, 0, 0)'       
              }      
            }     
          };     
          $scope.trackers = angular.copy(response.data);           
          var trackerSize = $scope.trackers.activeTime.length;     
          applyTheme($scope.trackers, divId,'#97bbcd','employee');     
          for (var i = 0; i < trackerSize; i++) {      
            totalActiveTime = totalActiveTime + $scope.trackers.activeTime[i].activeTime;     
          }
          var totalTime = CommonUtils.computeDiff($scope.trackers.actualStartTime, $scope.trackers.actualEndTime);
          var inactive = totalTime - totalActiveTime;     
          $scope.labels = ["Active Time : " + CommonUtils.computeActiveTime(totalActiveTime), "InActive Time : " + CommonUtils.computeInactiveTime(inactive)];     
          $scope.totalData = [
            totalActiveTime,
            inactive
          ];    
        });    
        request.error(function(response) {});
      },
      $scope.goTeam = function(){
    	window.location.href="/team";  
      },
      /** Page default Initializations START **/
      $scope.initControl = function() {
    	if(localStorage.getItem("username") === null) {
    		window.location.href="/";
    	}  
    	if(localStorage.getItem("role") === "manager") {
    		console.log("sa");
    		$scope.isValid = true;
    	} else {
    		$scope.isValid = false;
    	}
        $scope.tracker = [];
        $scope.daterange = true;
        $scope.date = false;
        var currentDate = $filter('date')(new Date(), 'yyyy-MM-dd');
        $scope.tracker.Date = new Date();
        $scope.employeeDateQueryWithParams(currentDate, "employee-date-hmd-container");
        $scope.managerDateQueryWithParams(currentDate);
      },
      $scope.initControlPast = function() {
          $scope.tracker = [];
          $scope.daterange = false;
          $scope.date = true;
          console.log(window.location);  
           if(window.location.pathname=="/week/")
          $scope.week();
           if(window.location.pathname=="/month/")
               $scope.month();
           if(window.location.pathname=="/three/")
               $scope.threeMonth();
            }, 

      $scope.daterangeprocess = function() {
        $scope.daterange = true;
        $scope.date = true;
      },
      $scope.dateprocess = function() {
        $scope.daterange = true;
        $scope.date = true;
      },
  $scope.managerDateQueryWithParams = function(date) {
    
	var request = $http.get("/employee/"+localStorage.getItem("username")+"/leadership/" + date + "/");
    
    request.then(function(response) {
      
      $scope.rank = [];
      $scope.employees = [];
      $scope.tempsorts=[];
      $scope.trackers = angular.copy(response.data);
      console.log($scope.trackers);
      
      var trackerSize=$scope.trackers.length;
      
      var tempsort = $scope.trackers;
      
      for (var i = 0; i < trackerSize; i++) {
        for (var j = 0; j < trackerSize; j++) {
          if (tempsort[i].activeTime > tempsort[j].activeTime) {
            var temp = tempsort[i];
            tempsort[i] = tempsort[j];
            tempsort[j] = temp;
          }
        }
      }
      $scope.tempsorts = [];
      if(tempsort.length>1)
    	  {
      for (var i = 0; i < 3; i++) {
        $scope.rank[i] = i + 1;
        $scope.employees[i] = tempsort[i].firstName + " " + tempsort[i].lastName;
        $scope.tempsorts[i] = tempsort[i];
      }
    	  }
    });
    
    request.error(function(response) {});
  
  }
}
]);