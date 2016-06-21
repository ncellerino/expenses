(function() {
	'use strict';

	angular.module('expensesApp').controller('SaveCategoryCtrl', function($uibModalInstance) {

		var SaveCategoryCtrl = function($uibModalInstance) {
			var vm = this;
			vm.hasError = false;

			var sucessCallback = function(result) {
				$uibModalInstance.close(true);
			};

			var errorCallback = function(result) {
				vm.error = 'There were an error. Please try again.';
				vm.hasError = true;
			};

			vm.saveCategory = function() {
				vm.form.$submitted = true;
				if (vm.form.$valid) {
					backendService.saveCategory(vm.category, sucessCallback, errorCallback);
				}
			};

			vm.closeAlert = function() {
				vm.error = '';
				vm.hasError = false;
			};

			vm.cancel = function() {
				$uibModalInstance.dismiss('cancel');
			};
		};
	});



}());