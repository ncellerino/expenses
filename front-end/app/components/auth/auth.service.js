angular.module('expensesApp').factory('Auth', function Auth($http, SessionMgr, $q, constantValues, jwtHelper) {
	'use strict';

	return {
		/**
		 * Authenticate user and save token
		 *
		 * @param  {Object}   user     - login info
		 * @param  {Function} callback - optional
		 * @return {Promise}
		 */
		login: function(user, callback) {
			var cb = callback || angular.noop;
			var deferred = $q.defer();

			$http.post(constantValues.HOST + '/login', {
				email: user.email,
				password: user.password
			}).then(function(response) {	
				SessionMgr.storeUserData(response.data.token);			
				deferred.resolve(response.data);
				return cb();
			},function(err) {
				deferred.reject(err);
				return cb(err);
			}.bind(this));

			return deferred.promise;
		},

		/**
		 * Register a new user
		 *
		 * @param  {Object}   user     - user info
		 * @param  {Function} callback - optional
		 * @return {Promise}
		 */
		register: function(user, callback) {
			var cb = callback || angular.noop;
			var deferred = $q.defer();

			$http.post(constantValues.HOST + '/register', user).then(function(response) {
				SessionMgr.storeUserData(response.data.token);
				deferred.resolve(response);
				return cb();
			}, function(err) {
				deferred.reject(err);
				return cb(err);
			}.bind(this));

			return deferred.promise;
		},

		/**
		 * Waits for currentUser to resolve before checking if user is logged in
		 */
		isLoggedInAsync: function(cb) {
			if (SessionMgr.getCurrentUser().hasOwnProperty('$promise')) {
				currentUser.$promise.then(function() {
					cb(true);
				}).catch(function() {
					cb(false);
				});
			} else if (SessionMgr.getCurrentUser().hasOwnProperty('role')) {
				cb(true);
			} else {
				cb(false);
			}
		},

		/**
		 * Gets all available info on authenticated user
		 *
		 * @return {Object} user
		 */
		getCurrentUser: function() {
			return currentUser;
		},

		/**
		 * Get auth token
		 */
		getToken: function() {
			return store.get('token');
		}
	};
});