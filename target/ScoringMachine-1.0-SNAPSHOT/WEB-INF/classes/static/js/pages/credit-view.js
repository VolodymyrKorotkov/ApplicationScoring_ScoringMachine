$(document).ready(function() {

    $('#form_credit_written_off').validate();
    $('#form_credit_cancellation').validate();
    $('#form_credit_payment').validate();

    $('.modal').on('shown.bs.modal', function() {
        $(this).closest('form').validate().resetForm();
    });

    $('#written_off_all_checkbox').change(function() {
        if(this.checked) {
            $(".hide-if-check-box-checked").addClass('hidden');
            $(".hide-if-check-box-unchecked").removeClass('hidden');
        }else{
            $(".hide-if-check-box-unchecked").addClass('hidden');
            $(".hide-if-check-box-checked").removeClass('hidden');
        }
    });
});
