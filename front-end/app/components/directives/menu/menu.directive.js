(function() {
	'use strict';

	angular.module('expensesApp').directive('ngMenu', function() {
		return {
			templateUrl: 'components/directives/menu/menu.html',
			restrict: 'EA',
			controller: menuCtrl
		};
	});

	function menuCtrl($scope, SessionMgr) {				
		$scope.role = SessionMgr.getCurrentUser().role;
	}

})();