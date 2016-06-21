angular.module('expensesApp').factory('User', function($resource) {
	'use strict';

	return $resource('http://localhost:8090/api/users/:id', {
		id: '@id'
	}, 
	{
		get: {
			method: 'GET',
			params: {
				id: 'me'
			}
		}
	});
});