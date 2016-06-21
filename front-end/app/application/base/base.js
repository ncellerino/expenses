(function() {
	'use strict';


	angular.module('expensesApp')
		.config(function($stateProvider) {
			$stateProvider
				.state('base', {
					url: '/base',
					templateUrl: 'application/base/base.html',
					//controller: 'LoginCtrl'
				});
		});
})();
