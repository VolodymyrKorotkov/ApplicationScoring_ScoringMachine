$(document).ready(function() {
    $('#triggerType').change(function () {
        var triggerType = $(this).val();
        if (triggerType === 'CRON_SCHEDULE') {
            $('#system_event_config_section').hide();
            $('#cron_schedule_config_section').show();
        } else if (triggerType === 'SYSTEM_EVENT') {
            $('#system_event_config_section').show();
            $('#cron_schedule_config_section').hide();
        } else {
            $('#system_event_config_section').hide();
            $('#cron_schedule_config_section').hide();
        }
    });
});
