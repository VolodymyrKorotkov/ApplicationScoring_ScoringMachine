$(document).ready(function() {
    updateChargeFineFields();
});


function updateChargeFineFields() {
    let oneTimeChargeContainer = $('#fine_settings_ONE_TIME_CHARGE_SCHEME');
    let periodicalChargeContainer = $('#fine_settings_PERIODICAL_CHARGE_SCHEME');

        oneTimeChargeContainer.show();
        periodicalChargeContainer.hide();
}
