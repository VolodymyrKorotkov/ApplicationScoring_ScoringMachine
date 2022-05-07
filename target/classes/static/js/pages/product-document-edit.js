$(document).ready(function() {


    // $('.by-application.hidden,.by-credit.hidden').find('select').prop('disabled', true);

    $('#generationTrigger').change(updateGenerationTrigger);
    $('#entityType').change(updateEntityType);


    updateEntityType();
    updateGenerationTrigger();


    $('[data-target="#deleteDocumentBinding"]').click(function () {
        $('#deleteDocumentBinding').modal();
        var id = $(this).parents('td').attr('data-id');
        var link = $('#deleteDocumentBinding').parents('form').attr('action').replace(/documentBinding.*\/delete/g, 'documentBinding/' + id + '/delete');
        $('#deleteDocumentBinding').parents('form').attr('action', link);
        return false;
    });



});

function updateGenerationTrigger() {
    let generationTrigger = $('#generationTrigger').val();

    if (generationTrigger === 'ENTITY_STATUS_CHANGE') {
        $('#block_generation_entity_status_change').show();
        $('#block_generation_code_request').hide();
    } else if (generationTrigger === 'CODE_REQUEST') {
        lockStatusBinding();
        $('#block_generation_entity_status_change').hide();
        $('#block_generation_code_request').show();
    } else {
        $('#block_generation_entity_status_change').hide();
        $('#block_generation_code_request').hide();
    }

}

function updateEntityType() {
    let entityType = $('#entityType').val();
    if (entityType === 'CREDIT') {
            $('#status_application').find('select').prop('disabled', true);
            $('#status_application').find('select').removeClass('required needsSelection');
            $('#status_credit').find('select').prop('disabled', false);
            $('#status_credit').find('select').addClass('required needsSelection');
        $('#status_application').hide();
        $('#status_credit').show();
        $('#block_generation_trigger').show();
    } else if (entityType === 'APPLICATION'){
            $('#status_application').find('select').prop('disabled', false);
            $('#status_application').find('select').addClass('required needsSelection');
            $('#status_credit').find('select').prop('disabled', true);
            $('#status_credit').find('select').removeClass('required needsSelection');
        $('#status_application').show();
        $('#status_credit').hide();

        $('#block_generation_trigger').show();
    } else {
        $('#status_application').hide();
        $('#status_credit').hide();
        lockStatusBinding()
        $('#block_generation_trigger').hide();
    }
}

function lockStatusBinding() {
    $('#status_application').find('select').prop('disabled', true);
    $('#status_application').find('select').removeClass('required needsSelection');
    $('#status_credit').find('select').prop('disabled', true);
    $('#status_credit').find('select').removeClass('required needsSelection');
}
