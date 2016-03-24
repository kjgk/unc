'use strict';

angular.module('app')

    .factory('dialog', function ($rootScope, $q, ngDialog) {

        var openModal = function (templateUrl, config) {
            var defer = $q.defer();
            var scope = $rootScope.$new();
            var defaultConfig = {title: '', content: ''};
            if (_.isObject(config)) {
                _.extend(scope, defaultConfig, config)
            } else {
                _.extend(scope, defaultConfig, {title: config})
            }
            ngDialog.open({
                closeByDocument: false,
                templateUrl: templateUrl,
                scope: scope
            }).closePromise.then(function (result) {
                    if (result.value == '$closeButton' || result.value == undefined) {
                        defer.reject();
                    } else {
                        defer.resolve(result.value);
                    }
                });
            return defer.promise;
        };

        var dialog_ = {
            alert: function (arg0) {
                return openModal('app/components/dialog/alert.html', arg0);
            },
            confirm: function (arg0) {
                return openModal('app/components/dialog/confirm.html', arg0);
            },
            prompt: function (arg0) {
                return openModal('app/components/dialog/prompt.html', arg0);
            },
            modal: function (templateUrl, arg0) {
                return openModal(templateUrl, arg0);
            }
        };

        return _.extend({
            confirmDelete: function () {
                return dialog_.confirm({
                    title: '确认删除？'
                });
            }
        }, dialog_);
    })
;


