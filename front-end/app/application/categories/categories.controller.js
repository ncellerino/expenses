/**
 * @ngdoc function
 * @name expenseApp.controller:UserCtrl
 * @description
 * # MainCtrl
 * Controller of expenseApp
 */
(function() {
	'use strict';

	angular.module('expensesApp').controller('CategoryCtrl', function($scope, $http, $location, $uibModal, constantValues, SessionMgr, Modal) {
		$scope.hasError = false;
		$scope.header = 'Put here your header';
		$scope.body = '<h1>bodyyyy</h1>';
		$scope.footer = 'Put here your footer';

		$scope.myRightButton = function(bool) {
			alert('!!! first function call!');
		};

		var sucessCallback = function(result) {
			$scope.categories = result.data;
		};

		var errorCallback = function(result) {
			$scope.error = 'There were an error. Please try again.';
			$scope.hasError = true;
		};

		$scope.getCategories = function() {
			var url = constantValues.getCategoriesUrl(SessionMgr.getCurrentUser().userId);
			$http.get(url).then(sucessCallback, errorCallback);
		};

		$scope.closeAlert = function() {
			$scope.error = '';
			$scope.hasError = false;
		};

		$scope.openModal = function(size) {

		//	var modal = Modal.confirm.delete(angular.noop, 'Category')
		//	modal();

			var modalInstance = $uibModal.open({
				animation: true,
				templateUrl: 'application/categories/save-edit/category-new.html',
				controller: 'SaveCategoryCtrl',
				controllerAs: 'vm',
				size: size
			});

			modalInstance.result.then(function(updateCategories) {
				if (updateCategories) {
					$scope.getCategories();
				}
			}, function() {

			});
			
		};

		$scope.getCategories();

	});


}());