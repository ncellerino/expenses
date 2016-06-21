(function() {
	'use strict';

	angular
		.module('expensesApp')
		.constant('constantValues', {
			HOST: "http://localhost:8090",
			CATEGORIES_URL: '/api/{$userId}/categories',
			USER_ID_PLACEHOLDER: '{$userId}',			
			getCategoriesUrl: function(userId) {
				return this.HOST + this.CATEGORIES_URL.replace(this.USER_ID_PLACEHOLDER, userId);
			},

		});
})();