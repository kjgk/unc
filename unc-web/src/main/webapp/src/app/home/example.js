'use strict';

angular.module('app')

    .config(function ($stateProvider) {
        $stateProvider
            .state('example', {
                parent: 'root',
                url: '/example',
                templateUrl: 'app/home/example.html',
                controller: 'ExampleController'
            })
    })
    .controller('ExampleController', function ($scope, $state, $timeout, FileUploader) {

        $scope.example = {
            "checkbox2": ["5a27ee5b-2f20-41f6-8346-8ea0a9b62ce0", "4d09c686-279c-4216-ba94-a697b5509ded"],
            "radio2": "6acc25ee-d357-487c-b17a-390a89aa0477"};


        $scope.options = [
            {'objectId': '1', 'name': '选择1'},
            {'objectId': '2', 'name': '选择2'},
            {'objectId': '3', 'name': '选择3'}
        ];


        var uploader = $scope.uploader = new FileUploader({
            url: PageContext.path + '/api/v1/system/file/upload',
            alias: 'attachment',
            removeAfterUpload: true,
            autoUpload: true
        });
        uploader.onProgressItem = function (fileItem, progress) {
            $scope.progress = progress;
        };
        uploader.onSuccessItem = function (fileItem, response, status, headers) {
//            $scope.apkVersion.filename = response.tempFilename;
//            $scope.apkVersion.fileSize = fileItem.file.size;
        };

        $scope.progress = 0;

        $scope.getProgressText = function () {
            if ($scope.progress > 0) {
                if ($scope.progress == 100) {
                    return '上传成功！';
                }
                return $scope.progress + '%';
            }
        };
    });