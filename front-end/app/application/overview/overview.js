(function() {
	'use strict';


	angular.module('expensesApp')
		.config(function($stateProvider) {
			$stateProvider
				.state('overview', {
					abstract: true,
					templateUrl: 'application/overview/overview.html'
				});
		});
})();