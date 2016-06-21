(function() {
	'use strict';

	angular.module('expensesApp').directive('ngHeader', function() {
		return {
			templateUrl: 'components/directives/header/header.html',
			restrict: 'EA',
			controller: headerCtrl,
			link: link
		};
	});

	function headerCtrl($scope, SessionMgr) {
		$scope.isHidden = true;
		$scope.isLoggedIn = SessionMgr.isLoggedIn;
		$scope.currentUser = SessionMgr.currentUser;

		$scope.logout = function () {
			$scope.isLoggedIn = false;
			SessionMgr.logout();
		};
	}



	function link(scope, element, attr) {
		var nav = element.find('.navbar-auto-hiding');
		if (nav.length > 0) {
			nav.autoHidingNavbar();
		}
	}

})();