'use strict';

angular.module('app')

    .config(function ($stateProvider) {
        $stateProvider
            .state('system.role', {
                url: '/role',
                displayName: '权限管理',
                templateUrl: 'app/system/role/role.list.html',
                controller: 'RoleCtrl'
            });
    })

    .factory('Roles', function (Restangular) {
        return Restangular.all('system/role');
    })

    .controller('RoleCtrl', function ($scope, $uibModal, UicTable, Roles) {

        $scope.grid = UicTable(Roles.getList);

        $scope.createRole = function () {
            var modalInstance = $uibModal.open({
                templateUrl: 'app/system/role/role.form.html',
                controller: 'RoleCreateCtrl'
            });
            modalInstance.result.then(function (result) {
                $scope.grid.refresh();
            });
        };
        $scope.updateRole = function (role) {
            var modalInstance = $uibModal.open({
                templateUrl: 'app/system/role/role.form.html',
                controller: 'RoleUpdateCtrl',
                resolve: {
                    role: function () {
                        return role;
                    }
                }
            });
            modalInstance.result.then(function (result) {
                $scope.grid.refresh();
            });
        };
        $scope.deleteRole = function (role) {
            Dialog.confirmDelete().then(function () {
                role.remove().then(function () {
                    $scope.grid.refresh();
                    Toaster.success("删除成功！");
                });
            });
        };

        $scope.assignRoleMenu = function (role) {
            var modalInstance = $uibModal.open({
                templateUrl: 'app/system/role/role.menu.html',
                controller: 'RoleMenuCtrl',
                resolve: {
                    role: function () {
                        return role;
                    }
                }
            });
            modalInstance.result.then(function (result) {
                $scope.grid.refresh();
            });
        };
    })

    .controller('RoleCreateCtrl', function ($scope, $uibModalInstance, Roles) {

        $scope.role = {};

        $scope.title = '新增角色';

        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };

        $scope.submit = function () {
            $scope.promise = Roles.doPOST($scope.role).then(function () {
                Toaster.success("保存成功！");
                $uibModalInstance.close();
            });
        };
    })

    .controller('RoleUpdateCtrl', function ($scope, $uibModalInstance, Roles, role) {

        $scope.promise = Roles.get(role.objectId).then(function (role) {
            $scope.role = role;
        });

        $scope.title = '修改角色';

        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };

        $scope.submit = function () {
            $scope.promise = $scope.role.save().then(function () {
                Toaster.success("保存成功！");
                $uibModalInstance.close();
            });
        };
    })

    .controller('RoleMenuCtrl', function ($scope, $uibModalInstance, $timeout, Menus, Roles, role) {

        $scope.title = '分配菜单';
        $scope.treeConfig = {
            selectable: false,
            treeHandleTemplate: 'app/components/statictree/checkbox-tree-handler.html',
            checkedList: {},
            change: function (item) {
                toggleChange(item, true, true)
            }
        };

        var toggleChange = function (item, cascadeParent, cascadeChild) {
            var checked = $scope.treeConfig.checkedList[item.objectId];
            // 处理parent
            if (cascadeParent && item.parent) {
                var toggleParent = true;
                if (!checked) {
                    _.forEach(item.parent.childList, function (child) {
                        if ($scope.treeConfig.checkedList[child.objectId]) {
                            toggleParent = false;
                            return false;
                        }
                    });
                }
                if (toggleParent) {
                    $scope.treeConfig.checkedList[item.parent.objectId] = checked;
                    toggleChange(item.parent, true, false);
                }
            }
            // 处理child
            if (cascadeChild && item.childList && item.childList.length > 0) {
                _.forEach(item.childList, function (child) {
                    $scope.treeConfig.checkedList[child.objectId] = checked;
                    toggleChange(child, false, true)
                });
            }
        };

        $scope.promise = role.doGET('menu');

        $scope.promise.then(function (roleMenuList) {
            _.forEach(roleMenuList, function (item) {
                $scope.treeConfig.checkedList[item.menu.objectId] = true;
            });
        });

        Menus.doGET('tree').then(function (treeData) {
            var initMenuData = function (parent, data) {
                _.forEach(data, function (item) {
                    item.parent = parent;
                    initMenuData(item, item.childList);
                });
            };
            initMenuData(undefined, treeData);
            $scope.treeData = treeData;
        });

        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };

        $scope.submit = function () {
            var menus = [];
            _.forIn($scope.treeConfig.checkedList, function (value, key) {
                if (value) {
                    menus.push(key);
                }
            });

            $scope.promise = role.post('menu', menus, {}).then(function () {
                Toaster.success("保存成功！");
                $uibModalInstance.close();
            });
        };
    })

;
