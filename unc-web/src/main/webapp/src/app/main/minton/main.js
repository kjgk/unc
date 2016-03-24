(function () {
    'use strict';

    if (!$('html').hasClass('minton')) {
        return
    }

    angular
        .module('app')
        .config(function ($stateProvider, $urlRouterProvider) {

            $urlRouterProvider.when('', '/home');

            $stateProvider
                .state('root', {
                    url: '',
                    views: {
                        '': {
                            templateUrl: 'app/main/minton/main.html',
                            controller: 'MainController'
                        },
                        'content-header@main': {
                            templateUrl: 'app/main/minton/content.header.html',
                            controller: function ($scope) {

                            }
                        }
                    }
                })
            ;
        })
        .controller('MainController', function ($rootScope, $scope, PageContext, $http, $window, $timeout, $interval) {

            $rootScope.getSideMenuHeight = function () {
                if ($("#wrapper").hasClass("enlarged")) {
                    return Math.max(PageContext.mainHeight, PageContext.contentHeight);
                } else {
                    return PageContext.mainHeight;
                }
            };

            // 自适应窗口大小
            function windowResize() {
                $timeout(function () {
                    var windowHeight = document.documentElement.clientHeight;
                    var windowWidth = document.documentElement.clientWidth;
                    var mainHeight = windowHeight - 70;
                    PageContext.windowHeight = windowHeight;
                    PageContext.windowWidth = windowWidth;
                    PageContext.mainHeight = mainHeight;
                    $rootScope.$apply(PageContext);
                });
                changeptype();
            }

            function initSideMeu() {
                !function ($) {
                    "use strict";

                    var Sidemenu = function () {
                        this.$body = $("body"),
                            this.$openLeftBtn = $(".open-left"),
                            this.$menuItem = $("#sidebar-menu a");
                        this.$body.addClass('fixed-left');
                    };
                    Sidemenu.prototype.openLeftBar = function () {
                        $("#wrapper").toggleClass("enlarged");
                        $("#wrapper").addClass("forced");

                        if ($("#wrapper").hasClass("enlarged") && $("body").hasClass("fixed-left")) {
                            $("body").removeClass("fixed-left").addClass("fixed-left-void");
                        } else if (!$("#wrapper").hasClass("enlarged") && $("body").hasClass("fixed-left-void")) {
                            $("body").removeClass("fixed-left-void").addClass("fixed-left");
                        }

                        if ($("#wrapper").hasClass("enlarged")) {
                            $(".left ul").removeAttr("style");
                        } else {
                            $(".subdrop").siblings("ul:first").show();
                        }

                        toggleSideMenuScroll(".side-menu-inner");
                        $("body").trigger("resize");
                    },
                        //menu item click
                        Sidemenu.prototype.menuItemClick = function (e) {

                            if (!$("#wrapper").hasClass("enlarged")) {
                                if ($(this).parent().hasClass("has_sub")) {

                                }
                                if (!$(this).hasClass("subdrop")) {
                                    // hide any open menus and remove all other classes
                                    $("ul", $(this).parents("ul:first")).slideUp(350);
                                    $("a", $(this).parents("ul:first")).removeClass("subdrop");
                                    $("#sidebar-menu .pull-right i").removeClass("md-remove").addClass("md-add");

                                    // open our new menu and add the open class
                                    $(this).next("ul").slideDown(350);
                                    $(this).addClass("subdrop");
                                    $(".pull-right i", $(this).parents(".has_sub:last")).removeClass("md-add").addClass("md-remove");
                                    $(".pull-right i", $(this).siblings("ul")).removeClass("md-remove").addClass("md-add");
                                } else if ($(this).hasClass("subdrop")) {
                                    $(this).removeClass("subdrop");
                                    $(this).next("ul").slideUp(350);
                                    $(".pull-right i", $(this).parent()).removeClass("md-remove").addClass("md-add");
                                }
                            }
                        },

                        //init sidemenu
                        Sidemenu.prototype.init = function () {
                            var $this = this;

                            var ua = navigator.userAgent,
                                event = (ua.match(/iP/i)) ? "touchstart" : "click";

                            //bind on click
                            this.$openLeftBtn.on(event, function (e) {
                                e.stopPropagation();
                                $this.openLeftBar();
                            });

                            // LEFT SIDE MAIN NAVIGATION
                            $this.$menuItem.on(event, $this.menuItemClick);
                        },

                        //init Sidemenu
                        $.Sidemenu = new Sidemenu, $.Sidemenu.Constructor = Sidemenu

                }(window.jQuery);

                $.Sidemenu.init();
            }

            function toggleSideMenuScroll(item) {
                if ($("#wrapper").hasClass("enlarged")) {
                    $(item).css("overflow", "inherit").parent().css("overflow", "visible");
                } else {
                    $(item).css("overflow", "inherit").parent().css("overflow", "hidden");
                }
            }

            function changeptype() {
                var w, h, dw, dh;
                w = $(window).width();
                h = $(window).height();
                dw = $(document).width();
                dh = $(document).height();

                if (!$("#wrapper").hasClass("forced")) {
                    if (w > 990) {
                        $("body").removeClass("smallscreen").addClass("widescreen");
                        $("#wrapper").removeClass("enlarged");
                    } else {
                        $("body").removeClass("widescreen").addClass("smallscreen");
                        $("#wrapper").addClass("enlarged");
                        $(".left ul").removeAttr("style");
                    }
                    if ($("#wrapper").hasClass("enlarged") && $("body").hasClass("fixed-left")) {
                        $("body").removeClass("fixed-left").addClass("fixed-left-void");
                    } else if (!$("#wrapper").hasClass("enlarged") && $("body").hasClass("fixed-left-void")) {
                        $("body").removeClass("fixed-left-void").addClass("fixed-left");
                    }
                    toggleSideMenuScroll(".side-menu-inner");
                }
            }

            $timeout(Waves.init);

            $timeout(windowResize);

            angular.element($window).bind('resize', windowResize);

            $rootScope.windowResize = windowResize;

            var allStates = [];             // 所有后台配置的菜单
            var roleStates = [];            // 当前用户可用的菜单

            $rootScope.refreshMenuTabs = function (event, state) {
                $scope.currentState = $rootScope.$state.$current;
                $scope.menuTabs = [];
                _.forEach($scope.menuTree, function (menu) {
                    if ($scope.menuTabs.length == 0) {
                        _.forEach(menu.childList, function (subMenu) {
                            if (state.name.indexOf(subMenu.url) >= 0) {
                                if (subMenu.childList.length == 0) {
                                    $scope.menuTabs = [subMenu];
                                } else {
                                    $scope.menuTabs = subMenu.childList || [subMenu];
                                }
                                return false;
                            }
                        });
                    }
                });
                $rootScope.windowResize();
            };

            // 加载登录用户信息
            $http({
                url: PageContext.path + '/getApplicationInfo',
                method: 'GET'
            }).then(function (response) {
                _.extend(PageContext, response.data);
                // 加载菜单
                $http({
                    url: PageContext.path + '/api/system/menu/tree',
                    method: 'GET'
                }).then(function (response) {
                    var menuTree = response.data;
                    $http({
                        url: PageContext.path + '/api/system/user/' + PageContext.currentUser.objectId + '/menu',
                        method: 'GET'
                    }).then(function (response) {
                        var rootMenu = {childList: []};
                        var userMenus = response.data;
                        var buildMenuTree = function (parent, menus) {
                            _.forEach(menus, function (menu) {
                                if (!_.includes(userMenus, menu.objectId)) {
                                    return true;
                                }
                                var item = {
                                    objectId: menu.objectId,
                                    name: menu.name,
                                    url: menu.url,
                                    icon: menu.icon,
                                    childList: []
                                };
                                roleStates.push(menu.url);
                                parent.childList.push(item);
                                buildMenuTree(item, menu.childList);
                            });
                        };
                        buildMenuTree(rootMenu, menuTree);
                        $scope.menuTree = rootMenu.childList;

                        var fetchAllStates = function (menus) {
                            _.forEach(menus, function (menu) {
                                allStates.push(menu.url);
                                fetchAllStates(menu.childList);
                            });
                        };
                        fetchAllStates(menuTree);

                    })['finally'](function () {
                        $timeout(function () {
                            $rootScope.refreshMenuTabs({}, $rootScope.$state.current);
                            initSideMeu();
                        }, 20);
                    });
                });

                // 同步服务器时间
                $interval(function () {
                    PageContext.serverTime += 60 * 1000;
                }, 60 * 1000);
            });
        });
})();
