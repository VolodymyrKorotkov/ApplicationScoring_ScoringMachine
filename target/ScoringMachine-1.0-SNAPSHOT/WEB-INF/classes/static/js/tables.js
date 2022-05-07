$(document).ready(function () {

    var urlParams = getPageParams();

    setupTables();
    setupFilters(urlParams);
    handleFormSubmission();
    setupSorting(urlParams);

    $('.footable').footable();

});

function handleFormSubmission() {
    $('.pull-right select[name="size"]').change(function () {
        $('form.table-filter-form').submit();
    });

    $('.pull-right .table-filter-btn').click(function () {
        var page = $(this).attr('data-page');
        $('.pull-right input[name=page]').val(+page + 1);
        $('form.table-filter-form').submit();
    });

    $('form.table-filter-form').submit(function () {
        var self = $(this);
        var filtersPanelVisible = $('.table-filter-container').is(":visible");
        var pageSize = $('.pull-right select[name="size"]').val();
        var pageNumber = $('.pull-right input[name=page]').val() - 1;
        putHiddenParams(self, "sf", filtersPanelVisible);
        putHiddenParams(self, "size", pageSize);
        putHiddenParams(self, "page", pageNumber);
    });

    $('form.table-filter-form').on('keypress', function (e) {
        if (e.key === 'Enter') {
            $("#lastName").val($("#lastName").val().trim());
            $('form.table-filter-form').submit();
        }
    });
}

function setupTables() {
    $('.table-filter-button').click(function () {
        $('.table-filter-container').toggle();
        if ($('.table-filter-container').is(":visible")) {
            $('.table-filter-button').text('Hide')
        } else {
            $('.table-filter-button').text('Search')
        }
    });

    $(".clickable").click(function (event) {
        if (!$(event.target).hasClass('non-clickable')) {
            var href = $(this).attr("href");

            if (href == undefined) {
                if (!window.location.origin) {
                    window.location.origin = window.location.protocol + "//" + (window.location.port ? ':' + window.location.port : '');
                }
                window.location.href = window.location.origin + window.location.pathname + '/' + $(this).attr("formtarget");
            } else {
                if ($(this).hasClass('blank')) {
                    window.open(href, '_blank');
                } else {
                    window.document.location = href;
                }
            }
        }
    });
}

function setupFilters(params) {

    for (var key in params) {
        putParam($('form.table-filter-form'), key, params[key]);
    }
    // are filters were previously shown?
    if (params['showFilters'] == 'true') {
        filters.removeClass('invisible');
    }
}

function putParam(form, name, value) {
    value = [].concat(value);
    for (var i = value.length; i >= 0; i--) {
        var v1 = form.find('input[name=' + name + ']');
        var v = value[i];
        if ($.type(v) === "string" && v != undefined) {
            v1.val(v.replace(/\+/g, ' '));
        }
        var v2 = form.find('select[name=' + name + ']')
        v2.find('option[value="' + value[i] + '"]').prop('selected', true);
        if (!v1.length && !v2.length) {
            $('<input />').attr('type', 'hidden').attr('name', name).attr('value', value).appendTo(form);
        }
    }
}


function setupSorting(params) {
    var form = $('form.table-filter-form');
    var sort = params['sort'];
    var sortField, sortOrder;
    if (sort) {
        sort = sort[0];
        var delimiterIdx = sort.indexOf(',');
        if (sort && delimiterIdx > 0) {
            sortField = sort.substring(0, delimiterIdx);
            sortOrder = sort.substring(delimiterIdx + 1, sort.length);
        } else {
            sortField = null;
            sortOrder = sort;
        }
    }
    if (sortField) {
        var th = $("th[data-sortField='" + sortField + "']");
        th.addClass("sorting_" + sortOrder);
    }

    var sortableHeaders = $('th[data-sortField]');

    sortableHeaders.addClass("sorting");

    sortableHeaders.click(function (e) {
        e.preventDefault();

        var prevSort = params['sort'];
        var prevSortField, prevSortOrder;
        if (prevSort) {
            prevSort = prevSort[0];
            var delimiterIdx = prevSort.indexOf(',');
            if (prevSort && delimiterIdx > 0) {
                prevSortField = prevSort.substring(0, delimiterIdx);
                prevSortOrder = prevSort.substring(delimiterIdx + 1, prevSort.length);
            } else {
                prevSortOrder = null;
                prevSortField = prevSort;
            }
        }

        var sortField = $(this).attr("data-sortField");
        var sortOrder;

        if (sortField == prevSortField) {
            if (!prevSortOrder || prevSortOrder == "desc") {
                sortOrder = "asc";
            }
            if (prevSortOrder == "asc") {
                sortOrder = "desc";
            }
        } else {
            sortOrder = "asc";
        }
        if (sortField) {
            putHiddenParams(form, "sort", sortField + ',' + sortOrder);
        }

        submitSorting = true;
        form.submit();
    });
}

function putHiddenParams(form, name, value) {
    if ($(form).find('input[type=hidden][name=' + name + ']').length > 0) {
        $(form).find('input[type=hidden][name=' + name + ']').val(value);
    }
    else {
        $('<input />').attr('type', 'hidden').attr('name', name).attr('value', value).appendTo(form);
    }
}

function getPageParams() {
    var params = window.location.search.substr(1);
    return params != null && params != "" ? toMap(params) : {};
}

function toMap(params) {
    var map = {};
    var arr = params.split("&");
    for (var i = 0; i < arr.length; i++) {
        var pair = arr[i].split("=");
        var key = decodeURIComponent(pair[0]);
        var value = decodeURIComponent(pair[1]);
        if (value) {
            if (map[key]) {
                map[key].push(value);
            } else {
                map[key] = [value];
            }
        }
    }
    return map;
}

