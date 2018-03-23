var app = angular.module('trackerApp').controller('loginController', ['$scope', '$rootScope', '$http', '$filter',
  function($scope, $rootScope, $http, $filter) {
	$scope.login = function(){
		var request = $http.get("/employee/"+$scope.user+"/"+$scope.passwd);
        request.then(function(response) {
        	localStorage.setItem("username", response.data.employeeId);
        	localStorage.setItem("firstname", response.data.firstName);
        	localStorage.setItem("lastname", response.data.lastName);
        	localStorage.setItem("role", response.data.role);
        	if(localStorage.getItem("username") !== "invalid"){
        		console.log("valid");
        		if(localStorage.getItem("role") === "manager"){
            		window.location.href = "/team";
            		console.log("manager");
            	}
        		else{
        			window.location.href = "/home";
        		}
        	}
        	else{
        		alert("invalid Username and password");
        	}
        });
	}
}
]);