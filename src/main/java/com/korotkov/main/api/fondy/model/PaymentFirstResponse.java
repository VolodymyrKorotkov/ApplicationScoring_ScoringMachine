package com.korotkov.main.api.fondy.model;



public class PaymentFirstResponse {
    String response_status;
    String checkout_url;
    int payment_id;
    int error_code;
    String error_message;

    public String getResponse_status() {
        return response_status;
    }

    public String getCheckout_url() {
        return checkout_url;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public int getError_code() {
        return error_code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setResponse_status(String response_status) {
        this.response_status = response_status;
    }

    public void setCheckout_url(String checkout_url) {
        this.checkout_url = checkout_url;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
