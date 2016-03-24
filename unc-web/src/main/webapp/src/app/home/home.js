'use strict';

angular.module('app')

    .config(function ($stateProvider) {
        $stateProvider
            .state('home', {
                parent: 'root',
                url: '/home',
                templateUrl: 'app/home/home.html',
                controller: 'HomeController'
            })
    })
    .controller('HomeController', function ($scope, $state) {

        $state.transitionTo('system.user');

//        $scope.$watch('PageContext.currentUser.role.tag', function (value) {
//            if (value) {
//                if (value == 'Admin') {
//
//                }
//            }
//        });
    });