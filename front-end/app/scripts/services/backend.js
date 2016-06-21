'use strict';

/**
 * @ngdoc function
 * @name yapp.service:BackendService
 * @description
 * # BackendService
 * Service of yapp
 */
(function() {

	var injectParams = ['$http', '$cookies'];


	var backendFactory = function($http, $cookies) {
		var HOST = 'http://localhost:8090'
		var USERNAME_PLACEHOLDER = '{username}';
		var LOGIN_URL = HOST + '/login';
		var REGISTER_URL = HOST + '/register';
		var CATEGORIES_URL = HOST + '/api/users/{username}/categories';
		var username = $cookies.get('username');
		var factory = {
			loginPath: '/login',
			user: {
				isAuthenticated: false,
				roles: null
			}
		};

		factory.login = function(userToLogin, successCallback, errorCallback) {
			$http.post(LOGIN_URL, userToLogin).then(successCallback, errorCallback);
		};

		factory.register = function(userToSave, successCallback, errorCallback) {
			$http.post(REGISTER_URL, userToSave).then(successCallback, errorCallback);
		};

		factory.getCategories = function(successCallback, errorCallback) {
			secureGet(getCategoriesUrl(), successCallback, errorCallback);
		};

		factory.saveCategory = function(category, successCallback, errorCallback) {			
			securePost(getCategoriesUrl(username), category, successCallback, errorCallback);
		};

		factory.updateCategory = function(category, successCallback, errorCallback) {
			$http.put(getCategoriesUrl(username), category, successCallback, errorCallback);
		};

		factory.deleteCategory = function(id, successCallback, errorCallback) {
			secureDelete(getCategoriesUrl(username) + '/' + id, successCallback, errorCallback);
		};

		var secureGet = function(url, successCallback, errorCallback) {
			setAuthHeader();
			$http.get(url).then(successCallback, errorCallback);
		};

		var securePost = function(url, data, successCallback, errorCallback) {
			setAuthHeader();
			$http.post(url, data).then(successCallback, errorCallback);
		};

		var securePut = function(url, data, successCallback, errorCallback) {
			setAuthHeader();
			$http.put(url, data).then(successCallback, errorCallback);
		};

		var sercureDelete = function(url, successCallback, errorCallback) {
			setAuthHeader();
			$http.delete(url).then(successCallback, errorCallback);
		};

		var setAuthHeader = function() {
			$http.defaults.headers.common.Authorization = 'Bearer ' + $cookies.get('token');
		};

		var getCategoriesUrl = function() {
			return CATEGORIES_URL.replace(USERNAME_PLACEHOLDER, username);
		};

		return factory;
	};

	backendFactory.$inject = injectParams;

	angular.module('yapp')
		.factory('backendService', backendFactory);
}());