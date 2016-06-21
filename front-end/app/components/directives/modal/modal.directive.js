(function() {
	'use strict';

	angular.module('expensesApp').directive('modal', function() {
		return {
			restrict: 'EA',
			scope: {
				title: '=modalTitle',
				header: '=modalHeader',
				body: '=modalBody',
				footer: '=modalFooter',
				callbackbuttonleft: '&ngClickLeftButton',
				callbackbuttonright: '&ngClickRightButton',
				handler: '=lolo'
			},
			templateUrl: 'components/directives/modal/templateModal.html',
			transclude: true,
			controller: function($scope) {
				$scope.handler = 'pop';
			},
		};
	});
})();