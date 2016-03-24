'use strict';

angular.module('app')

    .directive('uicRadio', function ($http, $injector, $timeout, uuid4) {
        return {
            templateUrl: 'app/components/radio/radio.html',
            restrict: 'EA',
            replace: true,
            require: 'ngModel',
            scope: {
                ngModel: '=',
                options: '=',
                inline: '@'
            },
            link: function (scope, element, attrs, ngModelCtrl) {

                scope.innerModel = {};
                scope.ngModelName = attrs.ngModel;
                scope.radioClass = attrs.class;
                scope.inline = scope.inline !== 'false';

                var config = attrs['uicRadio'];
                var promise;
                if (config) {
                    if (config == 'Code') {
                        promise = $injector.get('Codes').doGET('tree', {
                            tag: attrs.tag
                        });
                    } else {
                        promise = $injector.get(config + 's').getList();
                    }
                }

                scope.$watch('options', function (value) {
                    scope.items = value;
                }, true);

                scope.$watch('innerModel.value', function (value) {
                    if (!_.isEmpty(value)) {
                        scope.ngModel = value;
                    }
                });

                if (promise) {
                    promise.then(function (result) {
                        scope.items = [];
                        _.forEach(result, function (item) {
                            var _item = {
                                objectId: item.objectId,
                                name: item.name
                            };
                            if (item.plain && _.isFunction(item.plain)) {
                                _item = item.plain();
                            }
                            scope.items.push(_item);
                            if (item.defaultValue == 1 && !scope.ngModel) {
                                scope.ngModel = _item;
                            }
                        });
                        if (scope.ngModel) {
                            scope.innerModel.value = scope.ngModel;
                        }
                    });
                }
            }
        }
    })
;
