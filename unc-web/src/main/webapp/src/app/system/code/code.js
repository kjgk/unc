'use strict';

angular.module('app')

    .config(function ($stateProvider) {
        $stateProvider
            .state('system.code', {
                url: '/code',
                displayName: '字典',
                templateUrl: 'app/system/code/code.html',
                controller: 'CodeController'
            });
    })

    .factory('Codes', function ($http, Restangular) {
        return Restangular.all('system/code');
    })

    .controller('CodeController', function ($scope, $uibModal, UicTree, focus, Codes) {

        $scope.tree = UicTree(function (params) {
            return Codes.doGET('tree', params)
        }, {defaultIcon: 'fa-list'});

        $scope.$watch('codeForm', function (value) {
            $scope.codeForm = value;
        });

        $scope.$watch('code.objectId', function (value) {
            var code = $scope.code;
            if (value && code.$snapshot) {
                $scope.promise = Codes.get(value).then(function (response) {
                    _.extend(code, {
                        name: response.name,
                        description: response.description,
                        tag: response.tag,
                        orderNo: response.orderNo
                    });
                    delete code.$snapshot;
                });
            }
        });

        var getCodeTitle = function (code) {
            if (code.parent) {
                return getCodeTitle(code.parent) + ' / ' + code.name;
            } else {
                return code.name;
            }
        };

        $scope.getCodeTitle = function () {
            if (!$scope.code) {
                return '';
            }
            if ($scope.code.name == undefined) {
                return '新增字典';
            } else {
                return (getCodeTitle($scope.code) || '_');
            }
        };

        $scope.createCode = function () {
            var parent = $scope.code;
            var code = {
                parent: parent,
                leaf: 1
            };
            if (parent.childList == undefined) {
                parent.childList = [];
            }
            parent.childList.push(code);
            parent.leaf = 0;
            $scope.codeForm.$setPristine(true);
            $scope.code = code;
            focus('name');
        };

        $scope.saveCode = function () {
            var code = angular.copy($scope.code);
            delete code.childList;
            delete code.leaf;
            delete code.id;
            if (code.parent) {
                code.parent = {
                    objectId: code.parent.objectId
                };
            }
            if (_.isEmpty(code.objectId)) {
                $scope.promise = Codes.doPOST(code).then(function (code) {
                    $scope.code.objectId = code.objectId;
                    $scope.code.childList = [];
                    Toaster.success("保存成功！");
                });
            } else {
                $scope.promise = Codes.doPUT(code, code.objectId).then(function () {
                    Toaster.success("保存成功！");
                });
            }
        };

        $scope.deleteCode = function () {
            if (_.isEmpty($scope.code.objectId)) {
                if ($scope.code.parent == undefined) {
                    $scope.treeData = _.reject($scope.treeData, $scope.code);
                } else {
                    $scope.code.parent.childList = _.reject($scope.code.parent.childList, $scope.code);
                    $scope.code.parent.leaf = $scope.code.parent.childList.length == 0 ? 1 : 0;
                }
                $scope.code = null;
            } else {
                Dialog.confirmDelete().then(function () {
                    Codes.doDELETE($scope.code.objectId).then(function () {
                        Toaster.success("删除成功！");
                        if ($scope.code.parent == undefined) {
                            $scope.treeData = _.reject($scope.treeData, $scope.code);
                        } else {
                            $scope.code.parent.childList = _.reject($scope.code.parent.childList, $scope.code);
                            $scope.code.parent.leaf = $scope.code.parent.childList.length == 0 ? 1 : 0;
                        }
                        $scope.code = null;
                    });
                });
            }
        };
    })
;
