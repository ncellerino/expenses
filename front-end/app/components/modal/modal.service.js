(function() {
	'use strict';

	angular.module('expensesApp').factory('Modal', function($rootScope, $uibModal) {

		function openModal(scope, modalClass) {
			var modalScope = $rootScope.$new();
			scope = scope || {};
			modalClass = modalClass || 'modal-default';
			angular.extend(modalScope, scope);

			return $uibModal.open({
				templateUrl: 'components/modal/modal.html',
				windowClass: modalClass,
				scope: modalScope
			});
		}

		//public API
		return {
			//confirmation modals
			confirm: {
				/**
				 * Create a function to open a delete confirmation modal.
				 * @param {Function} del - callback to run when delete is confirmed.  
				 * @return {Function} - the function to open the modal.
				 */
				delete: function(del) {
					del = del || angular.noop;

					/**
					 * Open a delete confirmation modal.
					 * @return {[type]} [description]
					 */
					return function() {
						var args = Array.prototype.slice.call(arguments),
							name = args.shift(),
							deleteModal;

						deleteModal = openModal({
								modal: {
									dismissable: true,
									title: 'Confirm delete',
									html: '<p>Are you sure you want to delete <strong>' + name + '</strong> ?</p>',
									buttons: [{
										classes: 'btn-danger',
										text: 'Delete',
										click: function(e) {
											deleteModal.close(e);
										}
									}, {
										classes: 'btn-default',
										text: 'Cancel',
										click: function(e) {
											deleteModal.dismiss(e);
										}
									}]

								}
							},
							'modal-danger');

						deleteModal.result.then(function(event) {
							del.apply(event, args);
						});
					};
				}
			}
		};
	});

})();