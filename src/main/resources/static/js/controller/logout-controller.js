var app = angular.module('trackerApp').controller('logoutController', ['$scope', '$rootScope', '$http', '$filter', '$timeout',
  function($scope, $rootScope, $http, $filter, $timeout) {
	$scope.initControl = function(){
		$timeout(function(){
		if(window.location.href.indexOf("login") >= 0){
			$scope.isLogin = true;
		}else {
			$scope.isLogin = false;
		}
		});
	},
	$scope.logout = function(){
		localStorage.removeItem("username");
        localStorage.removeItem("firstname");
        localStorage.removeItem("lastname");
        localStorage.removeItem("role");
        window.location.href="/";
	}
}
]);