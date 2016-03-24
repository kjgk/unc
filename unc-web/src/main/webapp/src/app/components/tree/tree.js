'use strict';

angular.module('app')

    .directive('uicTree', function () {
        return {
            replace: false,
            scope: {
                uicTree: '=',
                selectedNode: '='
            },
            templateUrl: 'app/components/tree/tree.html',

            link: function (scope, elenemt, attrs) {

                scope.childList = [];
                var uicTree = scope.uicTree || {};

                var options = uicTree.options;

                var root = options.root || 'Root';

                var defaultNodeRender = function (node, collapsed) {
                    var iconHtml = '';
                    if (options.defaultIcon) {
                        iconHtml = '<i class="fa ' + options.defaultIcon + '"></i>&nbsp;'
                    }
                    if (node.icon) {
                        var icon = node.icon;
                        if (icon == 'fa-folder-o') {
                            icon = collapsed ? icon : 'fa-folder-open-o';
                        }
                        iconHtml = '<i class="fa ' + icon + '"></i>&nbsp;'
                    }
                    return iconHtml + ((node.title || node.name) || '');
                };

                if (options.nodeRender) {
                    scope.nodeRender = options.nodeRender;
                } else {
                    scope.nodeRender = defaultNodeRender;
                }

                scope.selectNode = function (node) {
                    if (options.selectable === false) {
                        return;
                    }
                    if (options.branchSelect === false && node.leaf != 1) {
                        return;
                    }
                    if (options.allowSelectType) {
                        var allowSelectTypes = options.allowSelectType.split(',');
                        if (!_.contains(allowSelectTypes, node.type)) {
                            return;
                        }
                    }
                    scope.selectedNode = node;
                };

                if (options.treeHandleTemplate) {
                    scope.treeHandleTemplate = options.treeHandleTemplate;
                } else {
                    scope.treeHandleTemplate = 'app/components/tree/tree.handle.content.html';
                }

                scope.checkDisabled = function (node) {
                    if (!options.disabledNode || _.isEmpty(options.disabledNode)) {
                        return;
                    }
                    return _.pluck(options.disabledNode, '$$hashKey').indexOf(node.$$hashKey) >= 0;
                };

                scope.buildHandleClass = function (node, collapsed) {
                    if (node.leaf == 1) {
                        return '';
                    } else {
                        if (node.childList === undefined || node.childList.length == 0) {
                            return 'fa fa-plus-square-o';
                        } else {
                            return collapsed ? 'fa fa-plus-square-o' : 'fa fa-minus-square-o';
                        }
                    }
                };

                scope.toggleNode = function (scope, node) {
                    if (node.childList === undefined || node.childList.length == 0) {
                        uicTree.load(node.id);
                    } else {
                        scope.toggle();
                    }
                };

                var findnode = function (id) {
                    var fn = function (childList) {
                        var result = null;
                        _.forEach(childList, function (node) {
                            if (id == node.id) {
                                result = node;
                                return false;
                            }
                        });
                        if (result == null) {
                            _.forEach(childList, function (node) {
                                if (node.childList && node.childList.length > 0) {
                                    result = fn(node.childList);
                                    if (result != null) {
                                        return false;
                                    }
                                }
                            });
                        }
                        return result;
                    };
                    return fn(scope.childList);
                };

                scope.$watch(function () {
                    return uicTree.$nodeCache._t;
                }, function () {
                    var id = uicTree.$nodeCache.id;
                    if (id == undefined || id == root) {
                        scope.childList = uicTree.$nodeCache.childList;
                    } else {
                        var parent = findnode(id);
                        parent.childList = uicTree.$nodeCache.childList;
                        _.forEach(parent.childList, function (node) {
                            node.parent = parent;
                            node.$snapshot = true;
                        });
                        parent.leaf = parent.childList.length == 0 ? 1 : 0;
                    }
                });

                uicTree.load(root);
            }
        };
    })

    .provider('UicTree', function () {
        this.$get = function ($rootScope) {
            return  function SimpleTree(fetchFn, options) {
                if (!(this instanceof SimpleTree)) {
                    return new SimpleTree(fetchFn, options);
                }

                var me = this;
                options = options || {};

                var self = {
                    load: function (id) {
                        return me.promise = fetchFn({id: id}).then(function (childList) {
                            me.$nodeCache = {
                                id: id,
                                childList: childList,
                                _t: new Date().getTime()
                            };
                        })['finally'](function () {
                            me.loading = false;
                        });
                    },
                    $nodeCache: {},
                    options: options
                };

                _.extend(this, self);
            };
        };
    })
;
