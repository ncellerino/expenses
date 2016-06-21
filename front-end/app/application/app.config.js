(function() {
	'use strict';
	angular
		.module('expensesApp')
		.config(configuration);

	function configuration($stateProvider,
		$urlRouterProvider,
		$locationProvider,
		$httpProvider,
		IdleProvider,
		KeepaliveProvider,
		constantValues) {

		//session timeout config
		IdleProvider.idle(5);
		IdleProvider.timeout(5);
		KeepaliveProvider.interval(10);

		//removing # from urls
		$locationProvider.html5Mode(true);

		$urlRouterProvider.otherwise('/login');

		$httpProvider.interceptors.push('authInterceptor');
	}

})();