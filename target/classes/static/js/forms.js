$(document).ready(function () {

    $('.input-readonly').prop("readonly", true);
    $('.input-disabled').prop("disabled", true);
    $('.input-hidden').hide();

    var elements = document.querySelectorAll('.js-switch');
    for (var i = 0; i < elements.length; ++i) {
        new Switchery(elements[i], {color: '#1AB394'});
    }

    $('.btn-back').click(function (e) {
        e.preventDefault();
        var href = $(this).attr('href');
        if (href) {
            window.location.href = href;
        } else {
            $('.wrapper-content').hide();
            window.history.back();
        }
    });

    $('.btn-clear-filter').click(function () {
        if (!window.location.origin) {
            window.location.origin = window.location.protocol + "//" + (window.location.port ? ':' + window.location.port : '');
        }
        window.location.href = window.location.origin + window.location.pathname;
    });

    $('.i-checks').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green'
    });
    $('.chosen-select').chosen({
        width: "100%",
        disable_search_threshold: 5,
        placeholder_text_single: translations.chosen.placeholder,
        no_results_text: translations.chosen.noResultTextMsg,
        placeholder_text_multiple: translations.chosen.placeholder
    });

    $('.tags-input').tagsinput({
        tagClass: 'label label-primary'
    });


    $.validator.setDefaults({
        errorPlacement: function (error, element) {
            if (element.hasClass('chosen-select')) {
                error.insertAfter($(element).siblings('.chosen-container'));
            } else if (element.hasClass('date-mask') || element.hasClass('datetime-mask')) {
                error.insertAfter(element.parent());
            } else {
                element.after(error);
            }
        },

        highlight: function(element, errorClass, validClass) {
            var $element = $(element),
                beingHighlightedParentEl = $(element).parent();

            if ($element.hasClass('chosen-select')) {
                var selectContainerId = $element.attr('id').replace(/\./g, '_') + '_chosen';
                $('#' + selectContainerId + '>a.chosen-single').addClass('chosen-select__error');
            } else {
                $(element).addClass(errorClass).removeClass(validClass);
            }

            if (beingHighlightedParentEl.hasClass('datetime')) {
                beingHighlightedParentEl.addClass('has-error');
            }
        },

        unhighlight: function(element, errorClass, validClass) {
            var $element = $(element);

            if ($element.hasClass('chosen-select')) {
                var selectContainerId = $element.attr('id').replace(/\./g, '_') + '_chosen';
                $('#' + selectContainerId + '>a.chosen-single').removeClass('chosen-select__error');
            } else {
                $( element ).removeClass( errorClass ).addClass( validClass );
            }
            $element.parent().removeClass('has-error');
        },

        submitHandler: handleModalDialogFormBeforeSubmitAndSubmit,
        ignore: ':hidden:not(".needsSelection")' // Tells the validator to check the hidden select
    });

    $('select.needsSelection').on('change', function(event, params) {
        $(this).valid();
    });

    $.validator.messages.needsSelection = translations.validator.messages.needsSelection;
    $.validator.messages.maxlength = $.validator.format(translations.validator.messages.maxlength);
    $.validator.messages.email = translations.validator.messages.email;

    $.validator.addMethod('le', function (value, element, param) {
        return this.optional(element) || value <= +($(param).val());
    }, translations.validator.messages.le);

    $.validator.addMethod('ge', function (value, element, param) {
        return this.optional(element) || value >= +($(param).val());
    }, translations.validator.messages.ge);

    $.validator.addMethod('lt', function (value, element, param) {
        return this.optional(element) || value < +($(param).val());
    }, translations.validator.messages.lt);

    $.validator.addMethod('gt', function (value, element, param) {
        return this.optional(element) || value > +($(param).val());
    }, translations.validator.messages.gt);

    $('form.borrower-edit-form-js').validate();
    $('form.promocode-form').validate();
    $('form.contact-person-form').validate();
    $('form.product-document').validate();
    $('form.change-amount').validate();
    $('form.change-term').validate();
    $('form.template-form').validate();
    $('form.reject-application').each(function (idx, rejectApplicationForm) {
        $(rejectApplicationForm).validate();
    });
    $('form.send-app-to-client-for-revision').validate();
    $('form.creditProduct').validate();

    $('input.disabled').prop('disabled', true);
    $('input.readonly').prop('readonly', true);

    $('.readonly.form-group').find(".chosen-select").chosen().chosenReadonly(true);



    $(document).ready(function() {
        $('.summernote').summernote({
            height: 450,
            lang: userLanguage,
            toolbar: [
                ['style', ['style']],
                ['font', ['bold', 'underline', 'clear']],
                ['fontname', ['fontname']],
                ['color', ['color']],
                ['para', ['ul', 'ol', 'paragraph']],
                ['table', ['table']],
                ['insert', ['link', 'picture', 'video']],
                ['view', ['codeview', 'help']],
            ],
        });
    });





    $('td,a').tooltip({
        selector: "[data-toggle='tooltip']",
        container: "body"
    });
    <!-- Check box which show or hide related with this checkbox div block -->

    $('.checkbox-show-hide').each(function () {
        $(this).find('.i-checks').each(function () {
            var checkBoxClass = "." + $(this).find('input[type="checkbox"]').attr('data-checkbox-class');
            var relatedFieldClass = "." + $(this).find('input[type="checkbox"]').attr('data-related-field-class');
            var isChecked = $(this).find('input[type="checkbox"]').attr('data-is-checked-init-value');
            if (isChecked === "true") {
                $(relatedFieldClass).css("display", "block");
                $(relatedFieldClass).find(":input").each(function () {
                    $(this).addClass("required");
                    $(this).removeAttr("disabled");

                })
            } else {
                $(relatedFieldClass).css("display", "none");
                $(relatedFieldClass).find(":input").each(function () {
                    $(this).removeClass("required");
                    $(this).attr("disabled", "disabled");
                    $(this).val('');
                })
            }
            $(checkBoxClass).find('.iCheck-helper').click(function () {

                if ($(this).parent(".icheckbox_square-green").hasClass('checked')) {
                    $(relatedFieldClass).find(":input").each(function () {
                        $(this).addClass("required");
                        $(this).removeAttr("disabled");
                    });
                    $(relatedFieldClass).css("display", "block");
                } else {
                    $(relatedFieldClass).css("display", "none");
                    $(relatedFieldClass).find(":input").each(function () {
                        $(this).removeClass("required");
                        $(this).attr("disabled", "disabled");
                        $(this).val('');
                    });
                }
            });
        });
    });

    <!--END Check box which show or hide related with this checkbox div block -->

    <!-- Check box which show or hide related with this checkbox div block  and fill all input fields located in this div by values from related resources div block-->

    $('.checkbox-show-hide-fill').each(function () {
        $(this).find('.i-checks').each(function () {
            var checkBoxClass = "." + $(this).find('input[type="checkbox"]').attr('data-checkbox-class');
            var relatedFieldClassGetter = "." + $(this).find('input[type="checkbox"]').attr('data-related-field-resource-getter');
            var relatedFieldClassSetter = "." + $(this).find('input[type="checkbox"]').attr('data-related-field-resource-setter');
            var isChecked = $(this).find('input[type="checkbox"]').attr('data-is-checked-init-value');
            if (isChecked === "true") {
                $(relatedFieldClassGetter).css("display", "block");
                $(relatedFieldClassGetter).removeAttr("disabled");
            } else {
                $(relatedFieldClassGetter).css("display", "none");
                $(relatedFieldClassGetter).attr("disabled", "disabled");
            }
            $(checkBoxClass).find('.iCheck-helper').click(function () {

                if ($(this).parent(".icheckbox_square-green").hasClass('checked')) {
                    $(relatedFieldClassGetter).css("display", "block");
                    $(relatedFieldClassGetter).find(":input").each(function () {
                        $(this).val('');
                    });
                } else {
                    $(this).removeAttr("disabled");
                    $(relatedFieldClassGetter).css("display", "none");
                    var setterValues = [];
                    var idx = 0;
                    $(relatedFieldClassSetter).find(":input").each(function () {
                        setterValues[idx] = $(this).val();
                        idx++;
                    });
                    idx = 0;
                    $(relatedFieldClassGetter).find(":input").each(function () {
                        $(this).val(setterValues[idx]);
                        idx++;
                    });
                    var setterValues2 = [];
                    var idx = 0;
                    $(relatedFieldClassGetter).find(":input").each(function () {
                        setterValues2[idx] = $(this).val();
                        idx++;
                    });

                }
            });
        });
    });


    $(function () {
        $('[id^="client-document"]').fileupload({
            singleFileUploads: false,
            add: function (e, data) {
                const allowableEx = ['png', 'jpeg', 'jpg', 'JPEG', 'JPG', "PNG"];
                var $this = $(this);
                $this.find('.max-file-size').addClass('hidden');
                $this.find('.max-files-length').addClass('hidden');
                $this.find('.format-not-supported').addClass('hidden');
                data.files.map((file) => file.size).reduce((total, num) => total + num);
                let fileSizeExceeded = false;
                let fileFormatExceeded = false;
                for (let file of data.files) {
                    if (file.size > 10 * 1024 * 1024) {
                        fileSizeExceeded = true;
                        break;
                    }
                }
                for (let file of data.files) {
                    const ex = file.name.split('.').pop();
                    if (allowableEx.indexOf(ex) === -1) {
                        fileFormatExceeded = true;
                        break;
                    }
                }
                if (fileSizeExceeded) {
                    $this.find('.max-file-size').removeClass('hidden');
                } else if (data.files.length > 5) {
                    $this.find('.max-files-length').removeClass('hidden');
                } else if (fileFormatExceeded) {
                    $this.find('.format-not-supported').removeClass('hidden');
                } else {
                    $this.find('.fileupload-process').addClass('fileupload-processing');
                    $this.find('.fileinput-button').addClass('hidden');
                    $this.fileupload('send', data);
                }
                return false;
            },
            done: function (event, data) {
                $(this).find('.fileupload-process').removeClass('fileupload-processing');
                $(this).find('.fileinput-button').removeClass('hidden');
                let contractName = data.response().result;
                $(this).parents('.client-document-data').find('.view-data-key').text(contractName);
            }
        });
    });

    $('#start-underwriting').click(function () {
        $(this).siblings('form').submit();
    });

    $('form.application-commentary').validate();

    if (window.location.hash) {
        var hash = window.location.hash.substring(1);
        $(`[href="#${hash}"]`).click();
    }

    $('form.addclient-toblacklist').validate({
        rules: {
            'comment': {
                required: true
            }
        },
        errorPlacement: function (error, element) {
            element.after(error);
        }
    });

    $('form.black-list').validate({
        rules: {
            'lastName': {
                maxlength: 50
            },
            'middleName': {
                maxlength: 50
            },
            'firstName': {
                maxlength: 50
            },
            'comment': {
                required: true,
                maxlength: 255
            },
            'activityType': {
                maxlength: 255
            }
        },
        messages: {
        },
        errorPlacement: function (error, element) {
            if (element.hasClass('date-mask')) {
                error.insertAfter(element.parent());
            } else {
                element.after(error);
            }
        },
        highlight: function (element, errorClass, validClass) {
            var beingHighlightedEl = $(element);
            var beingHighlightedParentEl = $(element).parent();
            beingHighlightedEl.addClass(errorClass).removeClass(validClass);
            if (beingHighlightedParentEl.hasClass('date')) {
                beingHighlightedParentEl.addClass('has-error');
            }
        },
        unhighlight: function (element, errorClass, validClass) {
            var beingUnhighlightedEl = $(element);
            var beingUnhighlightedParentEl = $(element).parent();
            beingUnhighlightedEl.removeClass(errorClass).addClass(validClass);
            if (beingUnhighlightedParentEl.hasClass('date')) {
                beingUnhighlightedParentEl.removeClass('has-error');
            }
        }
    });

    $('form.notification-template').validate({});

    $('form.role').validate({});

    $('.edit-user-form-js').validate({
        lang: userLanguage,
        errorPlacement: function (error, element) {
            element.after(error);
        },
        rules: {
            password: {
                required: true,
                minlength: 4
            },
            passwordConfirmation: {
                required: true,
                equalTo: "#password"
            }
        }
    });

    $('input[name=sendCallback]').on('ifChanged', function () {
        if ($(this).is(':checked')) {
            $('#callbackUrl').rules('add', {
                required: true
            });
            $('#callbackType').rules('add', {
                required: true
            });
        } else {
            $('#callbackUrl').rules('add', {
                required: false
            });
            $('#callbackType').rules('add', {
                required: false
            });
        }
    });

    // moment.js setting up
    moment.updateLocale('en', {
        week: {
            dow: 1,
            doy: 4
        }
    });
    moment.locale(userLanguage);

    // datetime picker setting up
    $('.datetime').datetimepicker({
        icons: {
            time: "fa fa-clock-o",
            date: "fa fa-calendar",
            up: "fa fa-arrow-up",
            down: "fa fa-arrow-down",
            previous: 'fa fa-chevron-left',
            next: 'fa fa-chevron-right',
            today: 'fa fa-dot-circle-o',
            clear: 'fa fa-trash',
            close: 'fa fa-times'
        },
        locale: userLanguage,
        format: 'DD.MM.YYYY HH:mm:ss'
    });

    // date picker setting up
    $.fn.datepicker.dates.vi = {
        days: moment.localeData('vi').weekdays(),
        daysShort: moment.localeData('vi').weekdaysShort(),
        daysMin: moment.localeData('vi').weekdaysMin(),
        months: moment.localeData('vi').months(),
        monthsShort: moment.localeData('vi').monthsShort(),
        today: "Ngày hôm nay",
        clear: "Rõ ràng"
    };

    $.fn.datepicker.defaults.format = 'dd.mm.yyyy';
    $.fn.datepicker.defaults.autoclose = true;
    $.fn.datepicker.defaults.language = userLanguage;
    // make Monday as a datepicker first day for English language
    $.fn.datepicker.defaults.weekStart = 1;

    $('.input-group.date').datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: false
    });


    $('form#affiliateForm').validate({
        rules: {
            websiteUrl: {
                required: true,
                url: true
            },
            callbackUrl: {
                required: false,
                url: true
            },
            "callbackParams[0].key": {
                required: false
            },
            "callbackParams[1].key": {
                required: false
            },
            "callbackParams[2].key": {
                required: false
            },
            "callbackParams[3].key": {
                required: false
            },
            "callbackParams[0].value": {
                required: false
            },
            "callbackParams[1].value": {
                required: false
            },
            "callbackParams[2].value": {
                required: false
            },
            "callbackParams[3].value": {
                required: false
            }
        },
        messages: {
            websiteUrl: {
                url: translations.validator.messages.validUrl
            },
            callbackUrl: {
                url: translations.validator.messages.validUrl
            }
        },
        errorPlacement: function (error, element) {
            debugger;
            if (element.hasClass('chosen-select')) {
                error.insertAfter($(element).siblings('.chosen-container'));
            } else if (element.hasClass('date-mask')) {
                error.insertAfter(element.parent());
            } else {
                element.after(error);
            }
        }
    });

    // $('.valid').each(function () {
    //     $(this).validate({
    //         onfocusout: function element(element) {
    //             this.element(element);
    //         }
    //     });
    // });

    $('.nav-tabs a[data-toggle="tab"]').click(function () {
        if (window.location.href.includes('#')) {
            window.location.href = window.location.href.replace(/#.*/g, $(this).attr('href'));
        } else {
            window.location.href = window.location.href + $(this).attr('href');
        }
    });

    $('[data-target="#editPhone"]').click(function () {
        $('#editPhone').modal();
        return false;
    });

    $('.edit-phone button[type="submit"]').click(function () {
        let form = $(this).parents('form');
        if (form.valid()) {
            $('#mobilePhone-error-not-unique').addClass('hidden');
            $('#mobilePhone-error-not-unique-with-contact').addClass('hidden');
            var url = form.attr('action');
            $.ajax({
                url: url,
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({mobilePhone: $('#mobilePhone').val()})
            }).done(function (success) {
                window.location.reload();
            }).fail(function (error) {
                if (error.status === 400) {
                    toastr.error(translations.validator.messages.notUniqueNumber, translations.validator.messages.error);
                    $('#mobilePhone-error-not-unique').removeClass('hidden');
                } else {
                    if (error.status === 409){
                        $('#mobilePhone-error-not-unique-with-contact').removeClass('hidden');
                    } else {
                        toastr.error(translations.validator.messages.phoneChangingError, translations.validator.messages.error);
                    }
                }
            });
        }

        return false;
    });

    $('.js-chosen-select-remove-all').click(function (e) {
        e.preventDefault();
        let selectId = $(this).attr('data-select-id');
        if (selectId) {
            let $select = $('#' + selectId);
            let $selectedOpts = $select.find('option:selected');
            //some data selected, so deselect all
            if ($selectedOpts.length > 0) {
                $selectedOpts.removeAttr('selected');
            } else {
                $select.find('option').attr('selected', 'selected');
            }
            $($select).trigger('chosen:updated');
        }
    });
});
function handleModalDialogFormBeforeSubmitAndSubmit(form) {
    $(form).find('button[type=submit]').prop('disabled', true);
    var modalDialogEl = $(form).find('.modal[role=dialog]');

    if (modalDialogEl.length > 0) {
        modalDialogEl.on('shown.bs.modal', enableSubmitButtonAndRemoveListener);
    }

    form.submit();

    function enableSubmitButtonAndRemoveListener() {
        modalDialogEl.find('button[type=submit]').prop('disabled', false);
        modalDialogEl.off('shown.bs.modal', enableSubmitButtonAndRemoveListener);
    }
}

function triggerVgSelectUpdate(selectEl) {
    if (selectEl) {
        selectEl.trigger('chosen:updated');
    }
}
