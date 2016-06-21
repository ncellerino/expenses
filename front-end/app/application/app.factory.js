(function() {
	'use strict';
	angular.module('expensesApp').factory('authInterceptor', authInterceptor).run(runApp);

	function authInterceptor($rootScope, $q, SessionMgr, $location) {
		return {
			// Add authorization token to headers
			request: function(config) {
				config.headers = config.headers || {};
				var token = SessionMgr.getToken();
				if (token) {
					config.headers.Authorization = 'Bearer ' + token;
				}
				return config;
			},
			responseError: function(rejection) {
				if (rejection.status === 401) {
					SessionMgr.logout();
				} else {
					rejection.message = 'An error ocurred while processing your request. Please try again.';
				}

				return $q.reject(rejection);
			}
		};
	}

	function runApp($rootScope, $location, Auth, Idle) {
		Idle.watch();
		// Redirect to login if route requires auth and you're not logged in
		$rootScope.$on('$stateChangeStart', function(event, next) {
			Auth.isLoggedInAsync(function(loggedIn) {
				if (next.authenticate && !loggedIn) {
					event.preventDefault();
					$location.path('/login');
				}
			});
		});
	}
})();