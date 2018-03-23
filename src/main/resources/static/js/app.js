angular.module('trackerApp', ['chart.js','ngRoute', 'ngResource']).config(function(ChartJsProvider, $routeProvider, $locationProvider) {
	
	$routeProvider.when('/home/', {
		templateUrl: 'partials/home.html',
		controller: 'EmployeeController'
	}).when('/team/', {
		templateUrl: 'partials/team.html',
		controller: 'ManagerController'
	}).when('/login/', {
		templateUrl: 'partials/login.html',
		controller: 'loginController'
	}).when('/admin/', {
		templateUrl: 'partials/admin.html',
		controller: 'AdminController'
	}).when('/manager/excel/date/start/:startDate/end/:endDate/', {
		templateUrl: 'partials/manager-home.html',
		controller: 'ManagerController'
	}).otherwise({
		redirectTo: '/login'
	}).when('/week/', {
		templateUrl: 'partials/Pasthome.html',
		controller: 'EmployeeController'
	}).when('/month/', {
				templateUrl: 'partials/Pasthome.html',
				controller: 'EmployeeController'
	}).when('/three/', {
					templateUrl: 'partials/Pasthome.html',
					controller: 'EmployeeController' 

	});
	// use the HTML5 History API
    $locationProvider.html5Mode(true);
});
