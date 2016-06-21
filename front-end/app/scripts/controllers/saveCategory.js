(function() {

	var injectParams = ['$uibModalInstance', 'backendService'];

	var SaveCategoryCtrl = function($uibModalInstance, backendService) {
		var vm = this;
		vm.hasError = false;

		var sucessCallback = function(result) {
			$uibModalInstance.close(true);
		};

		var errorCallback = function(result) {
			vm.error = 'There were an error. Please try again.'
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

	SaveCategoryCtrl.$inject = injectParams;

	angular.module('yapp').controller('SaveCategoryCtrl', SaveCategoryCtrl);

}());