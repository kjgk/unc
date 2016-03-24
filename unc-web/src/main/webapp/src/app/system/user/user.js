'use strict';

angular.module('app')

    .config(function ($stateProvider) {
        $stateProvider
            .state('system.user', {
                url: '/user',
                displayName: '用户管理',
                templateUrl: 'app/system/user/user.list.html',
                controller: 'UserCtrl'
            });
    })

    .factory('Users', function ($http, Restangular) {
        return Restangular.all('system/user');
    })

    .controller('UserCtrl', function ($scope, $uibModal, Users, UicTable) {

        $scope.grid = UicTable(Users.getList);

        $scope.createUser = function () {
            var modalInstance = $uibModal.open({
                templateUrl: 'app/system/user/user.form.html',
                controller: 'UserCreateCtrl'
            });
            modalInstance.result.then(function (result) {
                $scope.grid.refresh();
            });
        };

        $scope.updateUser = function (user) {
            var modalInstance = $uibModal.open({
                templateUrl: 'app/system/user/user.form.html',
                controller: 'UserUpdateCtrl',
                resolve: {
                    user: function () {
                        return user;
                    }
                }
            });
            modalInstance.result.then(function (result) {
                $scope.grid.refresh();
            });
        };

        $scope.deleteUser = function (user) {
            Dialog.confirmDelete().then(function () {
                user.remove().then(function () {
                    $scope.grid.refresh();
                    Toaster.success("删除成功！");
                });
            });
        };
        $scope.setAccount = function (user) {
            var modalInstance = $uibModal.open({
                templateUrl: 'app/system/user/account/account.html',
                controller: 'AccountCtrl',
                resolve: {
                    user: function () {
                        return user;
                    }
                }
            });
            modalInstance.result.then(function (result) {
                $scope.grid.refresh();
            });
        };
    })

    .controller('UserCreateCtrl', function ($scope, $uibModalInstance, Users, FileUploader) {

        $scope.user = {
            userRoleList: []
        };
        $scope.title = '新增用户';

        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };

        $scope.submit = function () {
            $scope.promise = Users.doPOST($scope.user).then(function () {
                Toaster.success("保存成功！");
                $uibModalInstance.close();
            });
        };
    })

    .controller('UserUpdateCtrl', function ($scope, $uibModalInstance, FileUploader, user, Users) {

        $scope.promise = Users.get(user.objectId).then(function (user) {
            $scope.user = user;
        });

        $scope.title = '修改用户';

        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };

        $scope.submit = function () {
            $scope.promise = $scope.user.save().then(function () {
                Toaster.success("保存成功！");
                $uibModalInstance.close();
            });
        };
    })
;
