(function() {
'use strict';

	angular.module('expensesApp').controller('SignupCtrl', function($scope, Auth, $location, $window) {
		$scope.user = {};
		$scope.errors = {};

		$scope.closeAlert = function(){
			$scope.errors.show = false; 
		}

		$scope.register = function(form) {
			$scope.submitted = true;
			if (form.$valid) {
				Auth.register($scope.user).then(function() {
					// Logged in, redirect to dashboard
					$location.path('/categories');
				}).catch(function(err) {
					$scope.errors.show = true;
					if(err.status == 409){
						$scope.errors.other = 'Email address already registered';
					}else{
						$scope.errors.other = err.message;
					}					
				});
			}

		};

	});

}());