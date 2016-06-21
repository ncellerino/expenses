(function() {
	'use strict';

	angular.module('expensesApp').factory('SessionMgr', function SessionMgr($location, store, constantValues, jwtHelper) {
		var currentUser = {};
			return {

				/**
				 * Delete access token and user info
				 */
				logout: function() {
					store.remove('token');
					currentUser = {};
					$location.path('/login');
				},

				storeUserData: function(token) {
					store.set('token', token);
					var tokenPayload = jwtHelper.decodeToken(token);
					currentUser.id = tokenPayload.userId;
					currentUser.role = tokenPayload.role;
				},

				getToken: function(){
					return store.get('token');
				},

				getCurrentUser: function() {
					return currentUser;
				}
			};

	});


})();