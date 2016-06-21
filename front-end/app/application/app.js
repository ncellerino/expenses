(function() {
  'use strict';

  angular
    .module('expensesApp', [
      'ui.router',
      'ngResource',
      'ngAnimate',
      'ngCookies',
      'ui.bootstrap',
      'angular-storage',
      'angular-jwt',
      'ngIdle'
    ]);
})();