'use strict';

/**
 * @ngdoc function
 * @name yapp.controller:UserCtrl
 * @description
 * # MainCtrl
 * Controller of yapp
 */
(function() {

	var injectParams = ['$uibModal', '$location', '$cookies', 'backendService'];

	var CategoryCtrl = function($uibModal, $location, $cookies, backendService) {
		var vm = this;
		vm.hasError = false;

		var sucessCallback = function(result) {
			vm.categories = result.data;
		};

		var errorCallback = function(result) {
			vm.error = 'There were an error. Please try again.'
			vm.hasError = true;
		};

		vm.getCategories = function() {
			backendService.getCategories(sucessCallback, errorCallback);
		};

		vm.closeAlert = function() {
			vm.error = '';
			vm.hasError = false;
		};

		vm.openModal = function(size) {

			var modalInstance = $uibModal.open({
				animation: true,
				templateUrl: 'views/dashboard/category-new.html',
				controller: 'SaveCategoryCtrl',
				controllerAs: 'vm',
				size: size
			});

			modalInstance.result.then(function(updateCategories) {
				if (updateCategories) {
					vm.getCategories();
				}
			}, function() {

			});
		};

		vm.getCategories();

	};

	CategoryCtrl.$inject = injectParams;

	angular.module('yapp').controller('CategoryCtrl', CategoryCtrl);

}());