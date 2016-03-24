'use strict';

angular.module('app')

    .controller('AccountCtrl', function ($scope, $uibModalInstance, $timeout, Users, user) {

        $scope.title = "设置账号";

        $scope.account = user.account || {};

        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };

        $scope.submit = function () {
            $scope.promise = Users.doPUT($scope.account, user.objectId + '/account').then(function () {
                Toaster.success("设置成功！");
                $uibModalInstance.close();
            });
        };

    })
;