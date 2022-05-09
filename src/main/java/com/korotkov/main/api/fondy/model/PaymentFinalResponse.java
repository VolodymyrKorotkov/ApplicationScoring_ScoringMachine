package com.korotkov.main.api.fondy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentFinalResponse {
    String order_id;
    String merchant_id;
    String amount;
    String currency;
    String order_status;
    String response_status;
    String signature;
    String tran_type;
    String sender_cell_phone;
    String sender_account;
    String masked_card;
    String card_bin;
    String card_type;
    String rrn;
    String approval_code;
    String response_code;
    String response_description;
    String reversal_amount;
    String settlement_amount;
    String settlement_currency;
    String order_time;
    String settlement_date;
    String eci;
    String fee;
    String payment_system;
    String sender_email;
    String payment_id;
    String actual_amount;
    String actual_currency;
    String product_id;
    String merchant_data;
    String verification_status;
    String rectoken;
    String rectoken_lifetime;
    String additional_info;

    public void setVerification_status(String verification_status) {
        this.verification_status = verification_status;
    }

    public void setTran_type(String tran_type) {
        this.tran_type = tran_type;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setSettlement_date(String settlement_date) {
        this.settlement_date = settlement_date;
    }

    public void setSettlement_currency(String settlement_currency) {
        this.settlement_currency = settlement_currency;
    }

    public void setSender_email(String sender_email) {
        this.sender_email = sender_email;
    }

    public void setSender_cell_phone(String sender_cell_phone) {
        this.sender_cell_phone = sender_cell_phone;
    }

    public void setSettlement_amount(String settlement_amount) {
        this.settlement_amount = settlement_amount;
    }

    public void setSender_account(String sender_account) {
        this.sender_account = sender_account;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public void setResponse_status(String response_status) {
        this.response_status = response_status;
    }

    public void setReversal_amount(String reversal_amount) {
        this.reversal_amount = reversal_amount;
    }

    public void setResponse_description(String response_description) {
        this.response_description = response_description;
    }

    public void setRectoken_lifetime(String rectoken_lifetime) {
        this.rectoken_lifetime = rectoken_lifetime;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public void setPayment_system(String payment_system) {
        this.payment_system = payment_system;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setRectoken(String rectoken) {
        this.rectoken = rectoken;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public void setMerchant_data(String merchant_data) {
        this.merchant_data = merchant_data;
    }

    public void setMasked_card(String masked_card) {
        this.masked_card = masked_card;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTran_type() {
        return tran_type;
    }

    public void setEci(String eci) {
        this.eci = eci;
    }

    public void setCard_bin(String card_bin) {
        this.card_bin = card_bin;
    }

    public void setApproval_code(String approval_code) {
        this.approval_code = approval_code;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public void setActual_currency(String actual_currency) {
        this.actual_currency = actual_currency;
    }

    public void setActual_amount(String actual_amount) {
        this.actual_amount = actual_amount;
    }

    public String getVerification_status() {
        return verification_status;
    }

    public String getSignature() {
        return signature;
    }

    public String getSettlement_date() {
        return settlement_date;
    }

    public String getSettlement_currency() {
        return settlement_currency;
    }

    public String getSender_email() {
        return sender_email;
    }

    public String getSender_cell_phone() {
        return sender_cell_phone;
    }

    public String getSender_account() {
        return sender_account;
    }

    public String getRrn() {
        return rrn;
    }

    public String getResponse_description() {
        return response_description;
    }

    public String getResponse_status() {
        return response_status;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getPayment_system() {
        return payment_system;
    }

    public String getRectoken_lifetime() {
        return rectoken_lifetime;
    }

    public String getRectoken() {
        return rectoken;
    }

    public String getOrder_time() {
        return order_time;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getMerchant_data() {
        return merchant_data;
    }

    public String getMasked_card() {
        return masked_card;
    }

    public String getCurrency() {
        return currency;
    }

    public String getApproval_code() {
        return approval_code;
    }

    public String getActual_currency() {
        return actual_currency;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getActual_amount() {
        return actual_amount;
    }

    public String getAmount() {
        return amount;
    }

    public String getCard_bin() {
        return card_bin;
    }

    public String getEci() {
        return eci;
    }

    public String getFee() {
        return fee;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public String getResponse_code() {
        return response_code;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public String getReversal_amount() {
        return reversal_amount;
    }

    public String getSettlement_amount() {
        return settlement_amount;
    }
}
