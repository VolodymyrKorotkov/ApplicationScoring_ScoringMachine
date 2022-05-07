$(document).ready(function () {

    $('#documents_table').on('click', '[data-target="#deleteDocument"]', deleteCreditDocument);

    $('#upload-button').click(e=> {
        if ($('#documents_table tbody').children().length >= documentSizeLimit) {
            e.stopPropagation();
            e.preventDefault();
            toastr.error(translations.validator.messages.fileLimit);
        }
    });

    $('#fileupload').fileupload({
        submit: function (e, data) {
            var $this = $(this);
            let fileSizeExceeded = false;
            let fileFormatExceeded = false;
            let fileSizeMin = false;
            const allowableEx = ['png', 'PNG', 'pdf', 'PDF'];
            for (let file of data.files) {
                if (file.size === 0) {
                    fileSizeMin = true;
                    break;
                }
                if (file.size > 5 * 1024 * 1024) {
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
                toastr.error(translations.validator.messages.documentSizeExceeded);
            } else if (fileFormatExceeded) {
                toastr.error(translations.validator.messages.fileFormatExceeded);
            } else if (fileSizeMin){
                toastr.error(translations.validator.messages.fileSizeMin);
            } else {
                $this.find('.fileupload-process').addClass('fileupload-processing');
                $this.find('.fileinput-button').addClass('hidden');

                $this.fileupload('send', data);
            }
            return false;
        },
        done: function (event, data) {
            location.reload();
        }
    });

});

function deleteCreditDocument() {
    let dialog = $('#deleteDocument');

    dialog.modal();
    let id = $(this).attr('data-id');

    let form = dialog.parents('form');
    let link = form.attr('action') + id;
    form.attr('action', link);
    return false;
}
