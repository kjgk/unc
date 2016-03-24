'use strict';

angular.module('app')
    .constant('dateFormat', {
        DAY: 'YYYY-MM-DD',
        HOUR: 'YYYY-MM-DD HH',
        MINUTE: 'YYYY-MM-DD HH:mm',
        SECOND: 'YYYY-MM-DD HH:mm:ss',
        TIMESTAMP: 'YYYY-MM-DD HH:mm:ss.SSS'
    })
    .directive('stopPropagation', function () {
        return {
            restrict: 'A',
            link: function (scope, element) {
                element.bind('click', function (event) {
                    event.stopPropagation();
                    event.preventDefault();
                });
            }
        }
    })
    .directive('panelFitHeight', [function () {
        return {
            restrict: 'A',
            link: function (scope, element) {
                var panelBody = element.find('.panel-body').css({overflow: 'auto', minHeight: 180});
                scope.$watch(function () {
                    return PageContext.contentHeight;
                }, function (value) {
                    panelBody.css({
                        height: value - 46 - 30
                    });
                });
            }
        }
    }])
    .directive('specialCode', [function () {
        return {
            restrict: 'EA',
            link: function (scope, element) {
                element.addClass("special-code");
            }
        }
    }])
;