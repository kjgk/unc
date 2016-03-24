(function () {
    'use strict';

    angular
        .module('app')
        .value('PageContext', PageContext)
        .run(runBlock);

    /** @ngInject */
    function runBlock($rootScope, $state, $stateParams, PageContext, toastr, dialog, dateFormat) {

        $rootScope.PageContext = PageContext;
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;
        $rootScope.DateFormat = dateFormat;

        $rootScope.$watch(function () {
            return $('.content-page > .content').outerHeight();
        }, function (v) {
            PageContext.contentHeight = v;
        });

        window.Toaster = toastr;
        window.Dialog = dialog;
    }
})();
