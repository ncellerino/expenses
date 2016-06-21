(function() {
	'use strict';


	angular.module('expensesApp')
		.config(function($stateProvider) {
			$stateProvider
				.state('overview.categories', {
					url: '/categories',
					templateUrl: 'application/categories/categories.html',
					controller: 'CategoryCtrl'
				});
		});
})();