(function() {
	'use strict';


	angular.module('expensesApp')
		.config(function($stateProvider) {
			$stateProvider
				.state('login', {
					url: '/login',
					templateUrl: 'application/account/login/login.html',
					controller: 'LoginCtrl'
				})
				.state('signup', {
					url: '/signup',
					templateUrl: 'application/account/signup/signup.html',
					controller: 'SignupCtrl'
				});
		});
})();