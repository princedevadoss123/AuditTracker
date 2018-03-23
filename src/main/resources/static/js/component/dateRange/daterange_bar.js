var app=angular.module('trackerApp');
app.controller('RangeDateController', ['$scope', '$rootScope', '$http', '$filter', RangeDateController]);

function RangeDateController($scope, $rootScope, $http, $filter) {
	 $scope.individualrange = function(points, env) {
   	  $rootScope.partdate=false;
   	  console.log(points[0]._index);
   	  $('#team-dr-hmd-container').html("");
       $scope.date1=$scope.datelabelsMgr[points[0]._index];
       $rootScope.touch = points[0]._index;
       $scope.managerDateQueryWithParams($scope.datelabelsMgr[points[0]._index]);
       
     }
   
}