package com.korotkov.main.api.fondy.config;

public interface ApiFondyConstants {
    public static final int MERCHANT_ID = 1396424;
    public static final String PASSWORD = "test";
    public static final String CURRENCY = "USD";
    public static final String REQUIRED_RECTOKEN = "Y";
    public static final String RESPONSE_URL_POST = "/api/v1/fondy/portal/response";
    public static final String SERVER_CALLBACK_URL = "/api/v1/fondy/server/callback";

    public static final String REQUEST_URL_TO_FONDY = "https://pay.fondy.eu/api/checkout/url/";

    public static final String RETURN_PORTAL_UNKNOWN_ERROR_URL = "/myprofile/mysubscription?action=API_FONDY_ERROR_UNKNOWN";
    public static final String RETURN_PORTAL_ERROR_PART_URL = "/myprofile/mysubscription?action=API_FONDY_ERROR&error=";
}
