'use strict';

angular.module('app')

    .config(function ($stateProvider) {
        $stateProvider
            .state('system.menu', {
                url: '/menu',
                displayName: '系统菜单',
                templateUrl: 'app/system/menu/menu.html',
                controller: 'MenuController'
            });
    })

    .factory('Menus', function ($http, Restangular) {
        return Restangular.all('system/menu');
    })

    .controller('MenuController', function ($scope, $uibModal, UicTree, focus, Menus) {

        $scope.tree = UicTree(function (params) {
            return Menus.doGET('tree', params)
        }, {defaultIcon: 'fa-list'});

        $scope.$watch('menuForm', function (value) {
            $scope.menuForm = value;
        });

        $scope.$watch('menu.objectId', function (value) {
            var menu = $scope.menu;
            if (value && menu.$snapshot) {
                $scope.promise = Menus.get(value).then(function (response) {
                    _.extend(menu, {
                        name: response.name,
                        url: response.url,
                        icon: response.icon,
                        orderNo: response.orderNo
                    });
                    delete menu.$snapshot;
                });
            }
        });

        var getMenuTitle = function (menu) {
            if (menu.parent) {
                return getMenuTitle(menu.parent) + ' / ' + menu.name;
            } else {
                return menu.name;
            }
        };

        $scope.getMenuTitle = function () {
            if (!$scope.menu) {
                return '';
            }
            if ($scope.menu.name == undefined) {
                return '新增系统菜单';
            } else {
                return (getMenuTitle($scope.menu) || '_');
            }
        };

        $scope.createMenu = function () {
            var parent = $scope.menu;
            var menu = {
                parent: parent,
                leaf: 1
            };
            if (parent.childList == undefined) {
                parent.childList = [];
            }
            parent.childList.push(menu);
            parent.leaf = 0;
            $scope.menuForm.$setPristine(true);
            $scope.menu = menu;
            focus('name');
        };

        $scope.saveMenu = function () {
            var menu = angular.copy($scope.menu);
            delete menu.childList;
            delete menu.leaf;
            delete menu.id;
            if (menu.parent) {
                menu.parent = {
                    objectId: menu.parent.objectId
                };
            }
            if (_.isEmpty(menu.objectId)) {
                $scope.promise = Menus.doPOST(menu).then(function (menu) {
                    $scope.menu.objectId = menu.objectId;
                    $scope.menu.childList = [];
                    Toaster.success("保存成功！");
                });
            } else {
                $scope.promise = Menus.doPUT(menu, menu.objectId).then(function () {
                    Toaster.success("保存成功！");
                });
            }
        };

        $scope.deleteMenu = function () {
            if (_.isEmpty($scope.menu.objectId)) {
                if ($scope.menu.parent == undefined) {
                    $scope.treeData = _.reject($scope.treeData, $scope.menu);
                } else {
                    $scope.menu.parent.childList = _.reject($scope.menu.parent.childList, $scope.menu);
                    $scope.menu.parent.leaf = $scope.menu.parent.childList.length == 0 ? 1 : 0;
                }
                $scope.menu = null;
            } else {
                Dialog.confirmDelete().then(function () {
                    Menus.doDELETE($scope.menu.objectId).then(function () {
                        Toaster.success("删除成功！");
                        if ($scope.menu.parent == undefined) {
                            $scope.treeData = _.reject($scope.treeData, $scope.menu);
                        } else {
                            $scope.menu.parent.childList = _.reject($scope.menu.parent.childList, $scope.menu);
                            $scope.menu.parent.leaf = $scope.menu.parent.childList.length == 0 ? 1 : 0;
                        }
                        $scope.menu = null;
                    });
                });
            }
        };
    })
;
