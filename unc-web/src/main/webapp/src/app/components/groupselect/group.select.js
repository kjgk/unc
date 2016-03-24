'use strict';

angular.module('app')

    .directive('uicGroupSelect', function ($injector, $timeout) {

        return {
            restrict: 'EA',
            replace: true,
            templateUrl: 'app/components/groupselect/group.select.html',
            require: 'ngModel',
            scope: {
                ngModel: '=',
                ngValidator: '@',
                dropdownWidth: '@',
                groupSelectable: '@'
            },

            compile: function (elenemt, attrs) {
                var inputField = elenemt.find('input');
                inputField.attr('name', attrs.ngModel);
                if (attrs.ngValidator !== undefined) {
                    inputField.attr('validator', '{{ngValidator}}');
                }
                inputField.attr('placeholder', attrs.placeholder);
                return function (scope, elenemt, attrs, ctrl) {

                    var config = attrs['uicGroupSelect'];
                    var promise;
                    if (config == 'Code') {
                        promise = $injector.get('Codes').doGET('tree', {
                            tag: attrs.tag,
                            fetchChild: true
                        });
                    } else {
                        promise = $injector.get(config + 's').doGET('tree');
                    }

                    scope.ngModel = scope.ngModel || {};

                    scope.editable = !!attrs.$attr.editable;

                    scope.select = function (item, group) {
                        scope.ngModel = {objectId: item.objectId, name: group.name + ' / ' + item.name};
                        scope.candidate = null;
                        scope.candidateGroup = null;
                        elenemt.find('a.dropdown-toggle').dropdown('toggle');
                    };

                    scope.deselect = function () {
                        scope.ngModel = undefined;
                        elenemt.find('a.dropdown-toggle').dropdown('toggle');
                    };

                    scope.$watch('ngModel', function (value) {
                        if (value) {
                            scope.input = value.name;
                        } else {
                            scope.input = '';
                        }
                    });

                    scope.showClear = attrs.showClear === 'true';

                    scope.onKeyDown = function (e) {
                        var keycode = window.event ? e.keyCode : e.which;
                        if (keycode == 13) {            // 回车
                            e.stopPropagation();
                            e.preventDefault();
                            if (scope.candidate) {
                                scope.select(scope.candidate, scope.candidateGroup);
                            }
                            return;
                        }
                        if (keycode == 9) {             // Tab
                            $timeout(function () {
                                if (elenemt.hasClass('open')) {
                                    elenemt.find('a.dropdown-toggle').dropdown('toggle');
                                }
                            }, 150);
                            return;
                        }
                        $timeout(function () {
                            var keyword = $(e.currentTarget).val();
                            if (keyword != '') {
                                _.forEach(scope.groups, function (group) {
                                    var flag = true;
                                    _.forEach(group.childList, function (item) {
                                        if (item.tag == keyword || item.code == keyword) {
                                            scope.candidate = item;
                                            scope.candidateGroup = group;
                                            flag = false;
                                            return false;
                                        }
                                    });
                                    return flag;
                                });
                            }
                        });
                    };

                    scope.onFocus = function (e) {
                        var inputField = e.currentTarget;
                        inputField.select();
                        $timeout(function () {
                            if (!elenemt.hasClass('open')) {
                                elenemt.find('a.dropdown-toggle').dropdown('toggle');
                            }
                        }, 250);
                    };

                    promise.then(function (childList) {
                        scope.groups = [];
                        _.forEach(childList, function (item) {
                            if (scope.groupSelectable) {
                                item.childList.push({name: '全部', objectId: item.objectId});
                            }
                            scope.groups.push(item);
                            if (scope.ngModel) {
                                _.forEach(item.childList, function (subItem) {
                                    if (subItem.objectId == scope.ngModel.objectId) {
                                        scope.input = item.name + ' / ' + subItem.name;
                                        return false;
                                    }
                                });
                            }
                        });
                    });
                }
            }
        };
    })
;