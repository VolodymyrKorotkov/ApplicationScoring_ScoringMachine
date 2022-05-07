$(document).ready(function() {
    updateEditor();
    $('#format').change(function () {
        updateEditor();
    });

    updateCodeField();
    $('input[name="hasCode"]').on('ifChanged', updateCodeField);
    $('#hasCode').change(function () {
        updateCodeField();
    });
});

function updateCodeField() {
    let hasCode = $('input[name="hasCode"]').iCheck('update')[0].checked;
    if (hasCode) {
        $('#code').parent().parent().show();
    } else {
        $('#code').parent().parent().hide();
    }

}

function updateEditor() {
    let format = $('#format').val();
    let editor = $('#templateEditor');
    let container = $('#templateEditorContainer');
    if (format === 'HTML') {
        container.show();
        editor.summernote({
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
    } else if (format === 'TEXT') {
        container.show();
        editor.summernote('destroy');
    } else {
        container.hide();
    }
}
