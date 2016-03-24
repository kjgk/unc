'use strict';

angular.module('app')
    .config(function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.when('/system', '/system/user');

        $stateProvider.state('system', {
            parent: 'root',
            url: '/system',
            displayName: '系统管理',
            template: '<div ui-view></div>'
        });
    });
