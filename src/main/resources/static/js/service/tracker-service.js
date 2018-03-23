var module = angular.module('trackerApp');
module.factory('managerService', function($resource) {
	// http://localhost:8080/tracker/employee/{employee_id}/startDate/{startDate}/endDate/{endDate}
	return $resource(
			'/employee/I328242/startDate/:startDate/endDate/:endDate',
			{
				startDate : '@startDate',
				endDate : '@endDate'
			}, {
				stripTrailingSlashes : false
				});
});