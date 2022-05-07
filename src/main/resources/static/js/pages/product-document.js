
$(document).ready(function() {

    $('#bindings_table').on('click', '[data-target="#deleteDocumentBinding"]', deleteCreditProductDocument);

});

function deleteCreditProductDocument() {

    let dialog = $('#deleteDocumentBinding');

    dialog.modal();
    let id = $(this).attr('data-id');

    let form = dialog.parents('form');
    let link = form.attr('action') + id;
    form.attr('action', link);
    return false;
}
