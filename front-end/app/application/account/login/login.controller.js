(function() {
'use strict';

	angular.module('expensesApp').controller('LoginCtrl', function($scope, Auth, $location, $window) {
		$scope.user = {};
		$scope.errors = {};

		$scope.closeAlert = function(){
			$scope.errors.show = false; 
		}

		$scope.login = function(form) {
			$scope.submitted = true;
			if (form.$valid) {
				Auth.login({
					email: $scope.user.email,
					password: $scope.user.password
				}).then(function() {
					// Logged in, redirect to main view
					$location.path('/categories');
				}).catch(function(err) {
					$scope.errors.show = true;
					if(err.status == 401){
						$scope.errors.other = 'Wrong username or password';	
					}else {
						$scope.errors.other = err.message;
					}					
				});
			}

		};

	});

}());