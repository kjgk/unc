'use strict';

angular.module('app')

    .directive('uicSelect', function ($http, $injector, $timeout) {
        return {
            templateUrl: 'app/components/select/select.html',
            restrict: 'EA',
            replace: true,
            require: 'ngModel',
            scope: {
                ngModel: '=',
                options: '=',
                ngModelProperty: '@',
                ngValidator: '@'
            },
            compile: function (elenemt, attrs) {
                var input = elenemt.find('input');
                input.attr('name', attrs.ngModel);
                if (attrs.ngValidator !== undefined) {
                    input.attr('validator', '{{ngValidator}}');
                }
                input.attr('placeholder', attrs.placeholder);

                return function (scope, element, attrs, ngModelCtrl) {

                    var config = attrs['uicSelect'];
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

                    scope.select = function (selectedItem) {
                        if (selectedItem) {
                            if (_.isEmpty(scope.ngModelProperty)) {
                                scope.ngModel = angular.copy(selectedItem);
                            } else {
                                scope.ngModel = selectedItem[scope.ngModelProperty];
                            }
                            scope.selectedItem = selectedItem;
                            elenemt.find('a.dropdown-toggle').dropdown('toggle');
                        }
                    };

                    scope.deselect = function () {
                        scope.ngModel = undefined;
                        scope.selectedItem = undefined;
                        elenemt.find('a.dropdown-toggle').dropdown('toggle');
                    };

                    scope.checkActive = function (item) {
                        if (_.isEmpty(scope.ngModelProperty)) {
                            return scope.ngModel && (item.objectId == scope.ngModel.objectId);
                        } else {
                            return item[scope.ngModelProperty] == scope.ngModel;
                        }
                    };

                    scope.checkShowClear = function () {
                        if (_.isEmpty(scope.ngModelProperty)) {
                            return scope.showClear && scope.ngModel && scope.ngModel.objectId;
                        } else {
                            return scope.showClear && scope.ngModel;
                        }
                    };

                    scope.showClear = attrs.showClear === 'true';

                    scope.$watch('ngModel', function (ngModel) {
                        scope.checkSelect(ngModel);
                    });

                    scope.checkSelect = function (ngModel) {
                        if (ngModel) {
                            var obj;
                            if (_.isEmpty(scope.ngModelProperty)) {
                                obj = {
                                    objectId: ngModel.objectId
                                }
                            } else {
                                obj = {
                                };
                                obj[scope.ngModelProperty] = scope.ngModel;
                            }
                            scope.selectedItem = _.filter(scope.items, obj)[0] || scope.ngModel;
                        } else {
                            scope.selectedItem = {};
                        }
                    };

                    scope.$watch('options', function (value) {
                        if (value) {
                            scope.items = value;
                            scope.checkSelect(scope.ngModel);
                        }
                    }, true);

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
                                _.forEach(scope.items, function (item) {
                                    if (item.objectId == scope.ngModel.objectId) {
                                        scope.selectedItem = item;
                                        return false;
                                    }
                                });
                            }
                        });
                    }
                }
            }
        }
    })
;
