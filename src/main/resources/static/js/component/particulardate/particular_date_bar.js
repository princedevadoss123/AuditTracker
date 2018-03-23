var app=angular.module('trackerApp');
app.controller('DateController', ['$scope', '$rootScope', '$http', '$filter', DateController]);

function DateController($scope, $rootScope, $http, $filter) {
	$scope.clickgraph = function() {
        $("div.ibox-content-graph").slideToggle(300);
        $("span.graph").toggleClass("glyphicon-chevron-up").toggleClass("glyphicon-chevron-down");
        if ($("div.ibox-graph").attr('class').includes('init-back')) {
          setTimeout(function() {
            $("div.ibox-graph").toggleClass("init-back").toggleClass("next-back");
          }, 300);
        } else {
          $("div.ibox-graph").toggleClass("init-back").toggleClass("next-back");
        }
      },
      
     $scope.individual = function(points, env) {
   	  if($scope.daterange==false)
   		  {
   		  console.log($scope.daterange+"yyyyyyyyyy");
   		  $scope.partdate=false;
   	        var date1 = $scope.datelabelsMgr[$rootScope.touch];
   	        var empid = $scope.inumrange[points[0]._index];
   	        console.log(date1+" "+empid+" "+$scope.touch);
   	        $rootScope.hourwise = false;
   	          var request = $http.get("/manager/"+localStorage.getItem("username")+"/emp/" + empid + "/date/" + date1);
   	        request.then(function(response) {
   	          $scope.trackers = angular.copy(response.data);
   	          $scope.empname = $scope.trackers.firstName + " " + $scope.trackers.lastName;
   	          applyTheme($scope.trackers, "team-dr-hmd-container", $scope.colordet[points[0]._index], 'team');
   	        });
   	      	
   		  }
   	  else if($scope.date==false)
   		 {
   		  
   		  var date1 = $filter('date')($scope.tracker.PDate, 'yyyy-MM-dd');
   	      var empid = $scope.inum[points[0]._index];
   	      $scope.date1=date1;
   	    console.log($rootScope.hourwise); 
   	   $rootScope.hourwise = false;
   	   
   	      var request = $http.get("/manager/"+localStorage.getItem("username")+"/emp/" + empid + "/date/" + date1);
 	        request.then(function(response) {
 	          $scope.trackers = angular.copy(response.data);
 	          $scope.empname = $scope.trackers.firstName + " " + $scope.trackers.lastName;
 	          applyTheme($scope.trackers, "team-date-hmd-container", $scope.colordet[points[0]._index], 'team');
 	        });
 	    
   		  }
   	}     
};
