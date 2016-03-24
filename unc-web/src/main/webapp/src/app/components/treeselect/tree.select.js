'use strict';

angular.module('app')

    .directive('uicTreeSelect', function (uuid4, $injector, $http, $timeout, UicTree) {
        return {
            templateUrl: 'app/components/treeselect/tree.select.html',
            restrict: 'EA',
            replace: true,
            require: 'ngModel',
            scope: {
                ngModel: '=',
                branchSelect: '=',
                allowSelectType: '@',
                ngValidator: '@',
                dropdownWidth: '@'
            },

            compile: function (elenemt, attrs) {
                var input = elenemt.find('input');
                input.attr('name', attrs.ngModel);
                if (attrs.ngValidator !== undefined) {
                    input.attr('validator', '{{ngValidator}}');
                }
                input.attr('placeholder', attrs.placeholder);

                return function (scope, elenemt, attrs, ngModelCtrl) {

                    var config = attrs['uicTreeSelect'];
                    var api = $injector.get(config + 's');
                    scope.tree = UicTree(function (params) {
                        return api.doGET('tree', _.extend({fetchRoot: false}, params));
                    }, {defaultIcon: 'fa-sitemap', branchSelect: scope.branchSelect, allowSelectType: scope.allowSelectType});

                    scope.editable = !!attrs.$attr.editable;

                    scope.showClear = attrs.showClear === 'true';

                    scope.$watch('selectedNode', function (selectedNode) {
                        $timeout(function () {
                            if (selectedNode) {
                                scope.ngModel = {
                                    objectId: selectedNode.objectId,
                                    name: selectedNode.name,
                                    getAttribute: function (key) {
                                        return selectedNode[key];
                                    }
                                };
                                elenemt.find('a.dropdown-toggle').dropdown('toggle');
                            }
                        });
                    }, false);

                    scope.deselect = function () {
                        scope.ngModel = undefined;
                        scope.selectedNode = undefined;
                        elenemt.find('a.dropdown-toggle').dropdown('toggle');
                    };

                    scope.onKeyDown = function (e) {
                        var keyCode = window.event ? e.keyCode : e.which;
                        if (keyCode == 13) {            // 回车
                            e.stopPropagation();
                            e.preventDefault();

                            $timeout(function () {
                                var keyword = $(e.currentTarget).val();
                                if (keyword != '') {
                                    api.doGET('findByKeyword', {keyword: keyword})
                                        .then(function (result) {
                                            if (result[0]) {
                                                scope.ngModel = {
                                                    objectId: result[0].objectId,
                                                    name: result[0].name
                                                };
                                            }
                                        });
                                }
                            });
                            return;
                        }
                        if (keyCode == 9) {             // Tab
                            $timeout(function () {
                                if (elenemt.hasClass('open')) {
                                    elenemt.find('a.dropdown-toggle').dropdown('toggle');
                                }
                            }, 150);
                            return;
                        }
                    };

                    scope.onFocus = function (e) {
                        var inputField = e.currentTarget;
                        inputField.select();
                    };

                    scope.onBlur = function (e) {
                        if (scope.ngModel && !scope.ngModel.objectId) {
                            scope.ngModel = {};
                        }
                    };
                }
            }
        }
    })

;