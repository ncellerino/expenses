'use strict';

/**
 * @ngdoc function
 * @name yapp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of yapp
 */
angular.module('yapp')
  .controller('LoginCtrl', function($scope, $location) {

  	var vm = this;

    vm.submit = function() {

    	if(vm.form.$valid){
     		$location.path('/dashboard');		
    	}
      

      return false;
    }

  });
