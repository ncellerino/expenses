'use strict';
(function() {

    var injectParams = ['$cookies', '$location', 'backendService'];

    var HeaderController = function($cookies, $location, backendService) {
        var vm = this;

        vm.checkIsLogged = function() {
            return backendService.isUserLogged();
        };

        function redirectToLogin() {
            var path = '/login';
            $location.path(path);
        }

        vm.logout = function() {
            backendService.logout(null, 'userAction');
        }


        vm.loginOut = function() {
            var isAuthenticated = backendService.user.isAuthenticated;
            if (isAuthenticated) { //logout 
                authService.logout().then(function() {
                    $location.path('/');
                    return;
                });
            }
            redirectToLogin();
        };

    };

    HeaderController.$inject = injectParams;


    angular
        .module('yapp')
        .directive('ngHeader', function() {
            return {
                templateUrl: 'views/header.html',
                restrict: 'EA',
                controller: HeaderController,
                controllerAs: 'vm'
            };
        });


})();