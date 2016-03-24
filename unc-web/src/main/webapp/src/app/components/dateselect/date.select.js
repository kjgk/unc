'use strict';

angular.module('app')

/**
 *  依赖 angular-date-time-input #0.1.0
 *      angular-bootstrap-datetimepicker
 */
    .directive('uicDateSelect', function (uuid4) {
        return {
            templateUrl: 'app/components/dateselect/date.select.html',
            restrict: 'EA',
            replace: true,
            scope: {
                ngModel: '=',
                ngValidator: '@',
                dateBefore: '=',
                dateAfter: '='
            },
            compile: function (elenemt, attrs) {

                var format = attrs.format || 'YYYY-MM-DD';
                var minView = attrs.minView;
                if (minView === undefined) {
                    if (format == 'YYYY-MM-DD HH:mm') {
                        minView = 'minute'
                    } else if (format == 'YYYY-MM-DD HH') {
                        minView = 'hour'
                    } else if (format == 'YYYY-MM-DD') {
                        minView = 'day'
                    }
                }

                var gid = uuid4.generate();
                var datetimePickerConfig = {
                    dropdownSelector: '#' + gid,
                    minView: minView
                };

                if (attrs.ngValidator !== undefined) {
                    elenemt.find('input[date-time-input]').attr('validator', '{{ngValidator}}')
                        .attr('daterange-error-message', attrs.daterangeErrorMessage);
                }
                elenemt.find('a.dropdown-toggle').attr('id', gid);
                elenemt.find('input[date-time-input]')
                    .attr('placeholder', attrs.placeholder)
                    .attr('date-time-input', format)
                    .attr('name', attrs.ngModel);
                elenemt.find('datetimepicker').attr('datetimepicker-config', angular.toJson(datetimePickerConfig));

                return function (scope, element, attrs, ngModelCtrl) {

                    scope.showClear = attrs.showClear === 'true';

                    scope.dropdownMenuRight = !_.isEmpty(attrs.$attr.dropdownMenuRight);

                    scope.deselect = function () {
                        scope.ngModel = undefined;
                        elenemt.find('a.dropdown-toggle').dropdown('toggle');
                    };
                }
            }
        }
    })

;