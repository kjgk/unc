(function () {
    'use strict';

    angular
        .module('app')
        .config(config);

    /** @ngInject */
    function config($logProvider, $httpProvider, $urlRouterProvider, $uibModalProvider, toastrConfig, cfpLoadingBarProvider, RestangularProvider) {

        $logProvider.debugEnabled(true);

        toastrConfig.allowHtml = true;
        toastrConfig.timeOut = 2000;
        toastrConfig.positionClass = 'toast-top-right';
        toastrConfig.preventDuplicates = false;
        toastrConfig.progressBar = false;
        toastrConfig.closeButton = true;

        $uibModalProvider.options.backdrop = 'static';

        cfpLoadingBarProvider.includeSpinner = false;

        $httpProvider.interceptors.push(function ($q, $location, $filter, cgBusyMessage) {
            return {
                'request': function (request) {

                    if (_.isObject(request.data)) {
                        request.data = JSOG.encode(request.data);
                    }
                    if (request.method == 'GET') {
                        cgBusyMessage.setMessage('正在加载，请稍候...');
                    } else {
                        cgBusyMessage.setMessage('正在处理，请稍候...');
                    }
                    return request || $q.when(request);
                },
                'response': function (response) {

                    if (_.isObject(response.data)) {
                        response.data = JSOG.decode(response.data);
                    }
                    return response || $q.when(response);
                },
                'responseError': function (rejection) {

                    var error = rejection.data.error;
                    var status = rejection.status;
                    if (status == 403) {
                        window.location.href = PageContext.path + '/login';
                    }
                    if (error) {
                        Toaster.error(error);
                    }
                    return $q.reject(rejection);
                }
            };
        });

        RestangularProvider.setBaseUrl(PageContext.path + '/api');

        RestangularProvider.addResponseInterceptor(function (data, operation, what, url, response, deferred) {

            var extractedData;
            if (operation === "getList") {
                extractedData = response.data.content;
                extractedData.meta = response.data;
            } else {
                extractedData = response.data;
            }
            return extractedData;

        });

        RestangularProvider.setRestangularFields({
            id: 'objectId'
        });

        $urlRouterProvider.when('/', '/home');
        $urlRouterProvider.otherwise('/home');
    }

})();
