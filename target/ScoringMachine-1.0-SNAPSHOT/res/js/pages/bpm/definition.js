
$(document).ready(function() {

    $('#fileupload').fileupload({
        submit: function (e, data) {
            var $this = $(this);
            let fileSizeExceeded = false;
            let fileFormatExceeded = false;
            let fileSizeMin = false;
            const allowableEx = ['bpm', 'BPM', 'bpmn', 'BPMN'];
            for (let file of data.files) {
                if(file.size === 0 ){
                    fileSizeMin =true;
                    break;
                }
                if (file.size > 15 * 1024 * 1024) {
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
                toastr.error("Max size of being uploaded file must not exceed 15 mb");
            } else if (fileFormatExceeded) {
                toastr.error("File has a forbidden format. Allowed formats are 'bpm', 'bpmn'");
            } else if (fileSizeMin){
                toastr.error("Min size of being uploaded file must be > 0 kb");
            } else {
                $this.find('.fileupload-process').addClass('fileupload-processing');
                $this.find('.fileinput-button').addClass('hidden');

                //todo submit form instead of reload location to enable redirect with flash attributes
                $this.fileupload('send', data);
            }
            return false;
        },
        done: function (event, data) {
            location.reload();
        }
    });

});
