(function() {
	'use strict';

	angular.module('expensesApp').factory('IddleSession', function IddleSession($rootScope, Idle) {
		
		$rootScope.$on('IdleStart', function() {
				console.log('startttt');
			});

		return {

			
		};
	});

})();