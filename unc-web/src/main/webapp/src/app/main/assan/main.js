(function () {
    'use strict';

    if (!$('html').hasClass('assan')) {
        return
    }

    angular.module('app')
        .config(function ($stateProvider, $urlRouterProvider) {

            $urlRouterProvider.when('', '/home');

            $stateProvider
                .state('root', {
                    url: '',
                    views: {
                        '': {
                            templateUrl: 'app/main/assan/main.html',
                            controller: 'MainController'
                        },
                        'header-wrapper@root': {
                            templateUrl: 'app/main/assan/header-wrapper.html'
                        },
                        'content-wrapper@root': {
                            templateUrl: 'app/main/assan/content-wrapper.html'
                        },
                        'content-header@root': {
                            templateUrl: 'app/main/assan/content-header.html'
                        }
                    }
                })
            ;
        })

        .controller('MainController', function ($scope, $rootScope, $state, $timeout, $interval, $http, $window, $uibModal, PageContext) {

            $interval(function () {
                $scope.dateString = moment().format('YYYY年M月D日 dddd');
            }, 1000);

            var allStates = [];             // 所有后台配置的菜单
            var roleStates = [];            // 当前用户可用的菜单

            // 加载登录用户信息
            $http({
                url: PageContext.path + '/getApplicationInfo',
                method: 'GET'
            }).then(function (response) {
                _.extend(PageContext, response.data);
                // 加载菜单
                $http({
                    url: PageContext.path + '/api/system/menu/tree',
                    method: 'GET'
                }).then(function (response) {
                    var menuTree = response.data;
                    $http({
                        url: PageContext.path + '/api/system/user/' + PageContext.currentUser.objectId + '/menu',
                        method: 'GET'
                    }).then(function (response) {
                        var rootMenu = {childList: []};
                        var userMenus = response.data;
                        var buildMenuTree = function (parent, menus) {
                            _.forEach(menus, function (menu) {
                                if (!_.includes(userMenus, menu.objectId)) {
                                    return true;
                                }
                                var item = {
                                    objectId: menu.objectId,
                                    name: menu.name,
                                    url: menu.url,
                                    icon: menu.icon,
                                    childList: []
                                };
                                roleStates.push(menu.url);
                                parent.childList.push(item);
                                buildMenuTree(item, menu.childList);
                            });
                        };
                        buildMenuTree(rootMenu, menuTree);
                        $scope.menuTree = rootMenu.childList;

                        var fetchAllStates = function (menus) {
                            _.forEach(menus, function (menu) {
                                allStates.push(menu.url);
                                fetchAllStates(menu.childList);
                            });
                        };
                        fetchAllStates(menuTree);

                    })['finally'](function () {
                        $scope.rendering = false;
                        $timeout(function () {
                            $rootScope.refreshMenuTabs({}, $rootScope.$state.current);
                        }, 20);
                    });
                });

                // 同步服务器时间
                $interval(function () {
                    PageContext.serverTime += 60 * 1000;
                }, 60 * 1000);
            });

            $scope.modifyPassword = function () {
                var modalInstance = $uibModal.open({
                    templateUrl: 'app/main/assan/modify-password-form.html',
                    controller: function ($scope, $uibModalInstance, $http) {
                        $scope.cancel = function () {
                            $uibModalInstance.dismiss();
                        };
                        $scope.submit = function () {
                            $scope.promise = $http({
                                url: PageContext.path + '/api/v1/system/account/updatePassword',
                                method: 'PUT',
                                data: {
                                    originPassword: $scope.originPassword,
                                    newPassword: $scope.newPassword
                                }
                            }).then(function (response) {
                                $uibModalInstance.close();
                            })
                        };
                    }
                });
                modalInstance.result.then(function (result) {
                    Toaster.success("密码修改成功！");
                });
            };

            $scope.exitSystem = function () {
                Dialog.confirm("确认退出系统？")
                    .then(function () {
                        window.location.href = PageContext.path + '/logout';
                    })
            };

            $rootScope.refreshMenuTabs = function (event, state) {
                $scope.menuTabs = [];
                _.forEach($scope.menuTree, function (menu) {
                    if ($scope.menuTabs.length == 0) {
                        _.forEach(menu.childList, function (subMenu) {
                            if (state.name.indexOf(subMenu.url) >= 0) {
                                if (subMenu.childList.length == 0) {
                                    $scope.menuTabs = [subMenu];
                                } else {
                                    $scope.menuTabs = subMenu.childList || [subMenu];
                                }
                                return false;
                            }
                        });
                    }
                });
                $rootScope.windowResize();
            };

            $rootScope.sideHide = false;
            $rootScope.headerHide = false;

            // 自适应窗口大小
            $rootScope.windowResize = function () {
                $timeout(function () {
                    var windowHeight = document.documentElement.clientHeight;
                    var menuHeight = windowHeight - ($rootScope.headerHide
                        ? angular.element('.header-bar').outerHeight() : angular.element('#header-wrapper').outerHeight());
                    var contentHeight = menuHeight - (angular.element('.content-header').outerHeight()) - 0;
                    var panelHeight = contentHeight - 30;
                    PageContext.windowHeight = windowHeight;
                    PageContext.contentHeight = contentHeight;
                    PageContext.menuHeight = menuHeight;
                    PageContext.panelHeight = panelHeight;
                    $scope.$apply(PageContext);
                });
            };

            $rootScope.$on('$stateChangeSuccess', $rootScope.refreshMenuTabs);

            $rootScope.$on('$stateChangeStart', function (event, state) {
//            if (roleStates.length == 0) {   // 页面第一次加载时 roleStates 还未加载完成
//                event.preventDefault();
//                $timeout(function () {
//                    $state.transitionTo('home');
//                }, 200);
//                return;
//            }
                // 过滤掉那些 后台配置的并且当前用户没有的菜单
                if (!_.includes(roleStates, state.name) && _.includes(allStates, state.name)) {
//                    event.preventDefault();
                }
            });

            angular.element($window).bind('resize', function () {
                $rootScope.windowResize();
            });

            $scope.toggleSide = function () {
                $rootScope.sideHide = !$rootScope.sideHide;
                $rootScope.windowResize();
            };

            $scope.toggleHeader = function () {
                $rootScope.headerHide = !$rootScope.headerHide;
                $rootScope.windowResize();
            };
        })
    ;
})();