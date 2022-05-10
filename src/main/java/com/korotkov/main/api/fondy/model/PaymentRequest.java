package com.korotkov.main.api.fondy.model;


public class PaymentRequest {
    String order_id;
    Integer merchant_id;
    String order_desc;
    String signature;
    Integer amount;
    String currency;
    String response_url;
    String server_callback_url;
    String sender_email;
    String product_id;
    String required_rectoken;
    String merchant_data;

    public String getMerchant_data() {
        return merchant_data;
    }

    public String getOrder_id() {
        return order_id;
    }

    public Integer getMerchant_id() {
        return merchant_id;
    }

    public String getOrder_desc() {
        return order_desc;
    }

    public String getSignature() {
        return signature;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getResponse_url() {
        return response_url;
    }

    public String getServer_callback_url() {
        return server_callback_url;
    }

    public String getSender_email() {
        return sender_email;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getRequired_rectoken() {
        return required_rectoken;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setMerchant_id(Integer merchant_id) {
        this.merchant_id = merchant_id;
    }

    public void setOrder_desc(String order_desc) {
        this.order_desc = order_desc;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setResponse_url(String response_url) {
        this.response_url = response_url;
    }

    public void setServer_callback_url(String server_callback_url) {
        this.server_callback_url = server_callback_url;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setRequired_rectoken(String required_rectoken) {
        this.required_rectoken = required_rectoken;
    }

    public void setSender_email(String sender_email) {
        this.sender_email = sender_email;
    }

    public void setMerchant_data(String merchant_data) {
        this.merchant_data = merchant_data;
    }
}
