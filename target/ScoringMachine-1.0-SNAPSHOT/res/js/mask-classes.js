$(document).ready(function () {

    var fioMask = "^[А-Я Ёа-яё]{0,32}$";
    var promoCodeTitle = "^[a-zA-Z0-9]{2,12}$";
    var middleNameMask = "^[^@#\\$%^&*}{\\][\":';\\/?!><,.)(]{0,100}$";
    var documentIdNumberMask = "^(\\d{9})$";
    var text100Mask = "^[^@#\\$%^*}{\\]\\[\":;\\/?!><,._ &')(]{0,100}$";
    var t100Mask = "^[^@#\\$%^*}{\\]\\[\":;\\/?!><)(]{0,100}$";
    var login = "^[a-zA-Z\\d@#\\$%^*}{\\]\\[\":;\\/?!><,._&')(]{1,32}$";
    var creditProductCode = "^.{0,20}$";

    $('textarea, input[type=text], input[type=password]').blur(function (event) {
        var element = $(event.target);
        $(element).val($.trim($(element).val()));
    });

    $.validator.addMethod("needsSelection", function (value, element) {
        return $(element).val().length > 0;
    });

    $.validator.addMethod("regex", function (value, element, regexpr) {
        return regexpr.test(value);
    }, translations.validator.messages.regex);

    $.validator.addMethod('required-checkbox', function (value) {
        return value === "true"
    }, translations.validator.messages.requiredCheckbox);

    $.validator.methods.range = function (value, element, param) {
        var globalizedValue = value.replace(",", ".");
        return this.optional(element) || (globalizedValue >= param[0] && globalizedValue <= param[1]);
    };

    $.validator.methods.number = function (value, element) {
        return this.optional(element) || /^-?(?:\d+|\d{1,3}(?:[\s,]\d{3})+)(?:[,]\d+)?$/.test(value);
    };

    $.validator.methods.email = function (value, element) {
        return this.optional(element) || /^[_A-Za-z0-9-+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$/.test(value);
    };

    $.validator.addMethod('document-id-number', function (value, element) {
        return this.optional(element) || /^(\d{9})$/.test(value);
    }, translations.validator.messages.documentIdNumber);

    $.validator.addMethod('amount', function (value, element) {
        return this.optional(element) || /^[0-9]*[.,]?[0-9]+$/.test(value);
    }, translations.validator.messages.amount);

    $.validator.addMethod('promoCodeTitle', function (value, element) {
        return this.optional(element) || /^[a-zA-Z0-9]{2,12}$/.test(value);
    }, translations.validator.messages.promoCodeTitle);

    $.validator.addMethod('v-amount', function (value, element) {
        return this.optional(element) || /^[0-9]{0,9}$/.test(value);
    }, translations.validator.messages.vAmount);

    $.validator.addMethod('cron', function (value, element) {
        return this.optional(element) || /^\s*($|#|\w+\s*=|(\?|\*|(?:[0-5]?\d)(?:(?:-|\/|\\,)(?:[0-5]?\d))?(?:,(?:[0-5]?\d)(?:(?:-|\/|\\,)(?:[0-5]?\d))?)*)\s+(\?|\*|(?:[0-5]?\d)(?:(?:-|\/|\\,)(?:[0-5]?\d))?(?:,(?:[0-5]?\d)(?:(?:-|\/|\\,)(?:[0-5]?\d))?)*)\s+(\?|\*|(?:[01]?\d|2[0-3])(?:(?:-|\/|\\,)(?:[01]?\d|2[0-3]))?(?:,(?:[01]?\d|2[0-3])(?:(?:-|\/|\\,)(?:[01]?\d|2[0-3]))?)*)\s+(\?|\*|(?:0?[1-9]|[12]\d|3[01])(?:(?:-|\/|\\,)(?:0?[1-9]|[12]\d|3[01]))?(?:,(?:0?[1-9]|[12]\d|3[01])(?:(?:-|\/|\\,)(?:0?[1-9]|[12]\d|3[01]))?)*)\s+(\?|\*|(?:[1-9]|1[012])(?:(?:-|\/|\\,)(?:[1-9]|1[012]))?(?:L|W)?(?:,(?:[1-9]|1[012])(?:(?:-|\/|\\,)(?:[1-9]|1[012]))?(?:L|W)?)*|\?|\*|(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(?:(?:-)(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?(?:,(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(?:(?:-)(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?)*)\s+(\?|\*|(?:[0-6])(?:(?:-|\/|\,|#)(?:[0-6]))?(?:L)?(?:,(?:[0-6])(?:(?:-|\/|\,|#)(?:[0-6]))?(?:L)?)*|\?|\*|(?:MON|TUE|WED|THU|FRI|SAT|SUN)(?:(?:-)(?:MON|TUE|WED|THU|FRI|SAT|SUN))?(?:,(?:MON|TUE|WED|THU|FRI|SAT|SUN)(?:(?:-)(?:MON|TUE|WED|THU|FRI|SAT|SUN))?)*)(|\s)+(\?|\*|(?:|\d{4})(?:(?:-|\/|\\,)(?:|\d{4}))?(?:,(?:|\d{4})(?:(?:-|\/|\\,)(?:|\d{4}))?)*))$/
            .test(value);
    }, translations.validator.messages.cron);

    $.validator.addMethod('text-pattern', function (value, element) {
        return this.optional(element) || /^[a-zA-Z0-9-\s.]+$/.test(value);
    }, translations.validator.messages.textPattern);

    $.validator.addMethod('text-pattern-qoutes', function (value, element) {
        return this.optional(element) || /^[a-zA-Z0-9-\s."]+$/.test(value);
    }, translations.validator.messages.textPatternQoutes);

    $.validator.addMethod('text-pattern-extended', function (value, element) {
        return this.optional(element) || /^[a-zA-Z0-9-\s./]+$/.test(value);
    }, translations.validator.messages.textPatternExtended);

    $.validator.addMethod('country-code', function (value, element) {
        return this.optional(element) || /^[a-zA-Z]{3}$/.test(value);
    }, translations.validator.messages.countryCode);

    $.validator.addMethod('min-value-50000', function (value, element) {
        return value <= 50000;
    }, translations.validator.messages.minValue50000);

    $.validator.addMethod('min-value', function (value, element) {
        return value <= 1000;
    }, translations.validator.messages.minValue);

    $.validator.addMethod('min-value-100', function (value, element) {
        return value <= 100;
    }, translations.validator.messages.minValue100);

    $.validator.addMethod('min-value-prolongation', function (value, element) {
        return value > 0;
    }, translations.validator.messages.minValueProlongation);

    $.validator.addMethod('max-grace-period', function (value, element) {
        return value <= 60;
    }, translations.validator.messages.maxGracePeriod);

    $.validator.addMethod('max-term', function (value, element) {
        return value <= 30;
    }, translations.validator.messages.maxTerm);

    $.validator.addMethod('max-prolongation-term', function (value, element) {
        if ($('#maxTerm').val() !== "")
            return value <= parseInt($('#maxTerm').val());
        return true;
    }, function () {
        return translations.validator.messages.maxProlongationTerm;
    });

    $.validator.addMethod('phone-mask', function (value, element) {
        return this.optional(element) || /^7(700|701|702|703|704|705|706|707|708|709|747|750|751|760|761|762|763|764|771|775|776|777|778)\d{7}$/.test(value);
    }, translations.validator.messages.incorrectPhone);

    $.validator.addMethod('occupationType', function (value, element) {
        return $('.occupation-type-input :selected').val() !== "";
    }, translations.validator.messages.occupationType);

    $.validator.addMethod('not-empty', function (value, element) {
        return this.optional(element) || $.trim(value);
    }, translations.validator.messages.notEmpty);

    $.validator.addMethod('fio-mask', function (value, element) {
        return /^[А-Яа-я.-]{0,50}$/.test(value);
    }, translations.validator.messages.fio);

    $.validator.addMethod('fio-contact-mask', function (value, element) {
        return /^[А-Яа-я .-]{0,255}$/.test(value);
    }, translations.validator.messages.contactFio);

    $.validator.addMethod('password-mask', function (value, element) {
        return /^[a-zA-Z\d!"#$%&'()*+,-./:;<=>?@\[\]\\^_`{|}~]{4,16}$/.test(value);
    }, translations.validator.messages.invalidValue);

    $.validator.addMethod('50-char-spec', function (value, element) {
        return /^[А-Яа-я0-9 .-]{0,50}$/.test(value);
    }, translations.validator.messages.invalidValue);

    $.validator.addMethod('15-char-spec', function (value, element) {
        return /^[А-Яа-я0-9 .-]{0,15}$/.test(value);
    }, translations.validator.messages.invalidValue);

    $.validator.addMethod('code', function (value, element) {
        return /^[a-zA-Z\d._]{0,64}$/.test(value);
    }, translations.validator.messages.code);

    $.validator.addMethod('max-payment-amount', function (value, element) {
        return value <= 100000000;
    }, translations.validator.messages.maxPaymentAmount);

    $.validator.addMethod('email-mask', function (value, element) {
        if (value.length > 0) {
            return /^(?!.{101})[_A-Za-z0-9-+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$/.test(value);
        } else {
            return true;
        }
    }, translations.validator.messages.email);


    $.validator.addMethod('document-id-number-mask', function (value, element) {
        return /^(\d{9})$/.test(value);
    }, translations.validator.messages.documentIdNumber);

    $('.middle-name-mask').inputmask("Regex", {regex: middleNameMask});
    $('.text-100-mask').inputmask("Regex", {regex: text100Mask});
    $('.login').inputmask("Regex", {regex: login});
    $('.t100Mask').inputmask("Regex", {regex: t100Mask});
    $('.fioMask').inputmask("Regex", {regex: fioMask});
    $('.promoCodeTitle').inputmask("Regex", {regex: promoCodeTitle});
    $('.digits-19').inputmask('9{1,19}');
    $('.digits-10').inputmask('9{1,10}');
    $('.digits-2').inputmask('9{1,2}');
    $('.credit-product-code').inputmask("Regex", {regex: creditProductCode})
    $('.document-id-number-mask').inputmask({
        alias: "decimal",
        mask: "999999999",
        rightAlign: false,
        integerDigits: 9,
        placeholder: "_",
        allowMinus: false,
        autoUnmask: true
    });

    $('.mask-months').inputmask('Regex', {
        regex: '[0-9]{1,4}}'
    });

    $('.per-cent').inputmask({
        alias: "decimal",
        rightAlign: false,
        integerDigits: 5,
        digits: 2,
        radixPoint: ".",
        placeholder: "0",
        allowMinus: false,
        groupSeparator: " ",
        groupSize: 3,
        autoGroup: true,
        autoUnmask: true
    });

    $('.days, .years').inputmask({
        alias: "decimal",
        rightAlign: false,
        integerDigits: 5,
        digits: 0,
        radixPoint: ".",
        placeholder: "0",
        allowMinus: false,
        groupSeparator: " ",
        groupSize: 3,
        autoGroup: true,
        autoUnmask: true
    });

    $('.vnd').inputmask({
        alias: "decimal",
        max: 50000000,
        radixPoint: ".",
        groupSeparator: " ",
        rightAlign: false,
        integerDigits: 8,
        digits: 0,
        allowMinus: false,
        groupSize: 3,
        autoGroup: true,
        autoUnmask: true
    });

    $('.number').inputmask({
        alias: "decimal",
        rightAlign: false,
        integerDigits: 15,
        digits: 0,
        allowMinus: false,
        groupSeparator: " ",
        groupSize: 3,
        autoGroup: true,
        autoUnmask: true
    });

    $('.salary').inputmask({
        alias: "decimal",
        rightAlign: false,
        integerDigits: 10,
        digits: 0,
        allowMinus: false,
        groupSeparator: " ",
        groupSize: 3,
        autoGroup: true,
        autoUnmask: true
    });

    $('.integer-number').inputmask({
        alias: "integer",
        rightAlign: false,
        integerDigits: 9,
        digits: 0,
        allowMinus: false,
        groupSeparator: " ",
        groupSize: 3,
        autoGroup: true,
        autoUnmask: true
    });

    $('.term-number').inputmask({
        alias: "integer",
        rightAlign: false,
        integerDigits: 5,
        digits: 0,
        allowMinus: false,
        groupSeparator: " ",
        autoUnmask: true
    });

    $('.contract-number').inputmask({
        mask: "*{1,14}"
    });

    $('.amount-mask').inputmask({
        alias: "decimal",
        rightAlign: false,
        integerDigits: 9,
        digits: 0,
        allowMinus: false,
        groupSeparator: " ",
        groupSize: 3,
        autoGroup: true,
        autoUnmask: true
    });

    $('.double').inputmask({
        alias: "decimal",
        rightAlign: false,
        integerDigits: 15,
        digits: 2,
        allowMinus: false,
        groupSeparator: " ",
        groupSize: 3,
        autoGroup: true,
        autoUnmask: true
    });

    $('.double-4').inputmask({
        alias: "decimal",
        rightAlign: false,
        integerDigits: 15,
        digits: 4,
        allowMinus: false,
        groupSeparator: " ",
        groupSize: 3,
        autoGroup: true,
        autoUnmask: true
    });

    $('.date-mask').inputmask({
        mask: "1.2.y",
        leapday: "29.02.",
        separator: ".",
        alias: "dd.mm.yyyy",
        format: 'dd.mm.yyyy',
        placeholder: "_"
    });

    $('.datetime-mask').inputmask("datetime", {
        mask: "1.2.y h:s:s",
        placeholder: "dd.mm.yyyy hh:mm:ss",
        leapday: "29.02.",
        separator: ".",
        alias: "datetime"
    });

    $('.date-mask').on('change focusout', function () {
        if (!(/^[0-9]{2}\.[0-9]{2}\.[0-9]{4}$/.test($(this).val())) && $(this).val().length !== 0) {
            var today = new Date();
            var dd = today.getDate();
            var mm = today.getMonth() + 1; //January is 0!
            var yyyy = today.getFullYear();
            if (dd < 10) {
                dd = '0' + dd
            }
            if (mm < 10) {
                mm = '0' + mm
            }
            today = dd + '.' + mm + '.' + yyyy;
            $(this).val(today)
        }
    });
    //////////////////////////////////

    $('.phone-mask').inputmask({
        mask: '+63 99-9999-9999',
        placeholder: '_',
        clearMaskOnLostFocus: false,
        removeMaskOnSubmit: true,
        autoUnmask: true,
        onUnMask: function (value) {
            var value = $.trim(value).replace(/\D/g, '');
            if (value.length === 1) {
                value = '';
            }
            return value;
        },
        onBeforeMask: function (value) {
            return value.replace(/\+63|63/, "")
        }
    });
    $('.contract-mask').inputmask({
        mask: 'a-999999-999999',
        placeholder: '_',
        clearMaskOnLostFocus: false,
        removeMaskOnSubmit: true,
        autoUnmask: true,
        onUnMask: function (value) {
            var value = $.trim(value).replace(/.*/g, '');
            if (value.length === 1) {
                value = '';
            }
            return value;
        },
        onBeforeMask: function (value) {
            return value.replace(/.*/, "")
        }
    });
    $('.sss-number-mask').inputmask({
        mask: '99-9999999-9',
        placeholder: '_',
        clearMaskOnLostFocus: false,
        removeMaskOnSubmit: true,
        autoUnmask: true,
        onUnMask: function (value) {
            var value = $.trim(value);
            if (value.length === 1) {
                value = '';
            }
            return value;
        },
        onBeforeMask: function (value) {
            return value.replace(/.*/, "")
        }
    });

    $.validator.addMethod("validDate", function (value, element) {
        return this.optional(element) || (value.length == 10 && moment(value, "DD/MM/YYYY").isValid());
    }, translations.validator.messages.validDate);

    $.validator.addMethod("product-min-amount", function (value, element) {
        return value >= 5000;
    }, translations.validator.messages.validProductMinAmount);

    $.validator.addMethod("after-issue-datetime", function (value, element) {
        var minDateString = $(element).attr('issue-date-time'),
            minDateTime = moment(minDateString, "DD.MM.YYYY HH:mm:ss"),
            enteredDateTime = moment(value, "DD.MM.yyyy HH:mm:ss");
        return this.optional(element) || enteredDateTime.isSameOrAfter(minDateTime);
    }, translations.validator.messages.beforeIssueDateTime);

    $.validator.addMethod('not-future-date-time', function (value, element) {
        var now = new Date(),
            myDate = new Date(moment(value, 'DD.MM.YYYY HH:mm:ss'));
        return this.optional(element) || myDate <= now;
    }, translations.validator.messages.notFutureDateTime);

    $.validator.addMethod('not-past-date-time', function (value, element) {
        var now = new Date(),
            myDate = new Date(moment(value, 'DD.MM.YYYY HH:mm:ss'));
        return this.optional(element) || myDate >= now;
    }, translations.validator.messages.notPastDateTime);

    //////////////////////////////////

    <!-- Audit Settings-->
    $('body').on('click', function (e) {
        $('[rel="popover"]').each(function () {

            if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
                $(this).popover('hide');
            }
        });
    });

    $(".show-popover").popover({
        trigger: 'click',
        html: true,
        container: 'body',
        content: function () {
            return $(this).find('.audit-js').html();
        }
    });

    $('.fa-history').hover(function () {
            $(this).css("color", "#0080ff");
        }, function () {
            $(this).css("color", "");
        }
    );

    <!-- Audit Settings end-->

})
;
