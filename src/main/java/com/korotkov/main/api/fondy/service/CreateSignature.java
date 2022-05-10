package com.korotkov.main.api.fondy.service;


import com.korotkov.main.api.fondy.config.ApiFondyConstants;
import com.korotkov.main.api.fondy.model.PaymentRequest;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;

public abstract class CreateSignature implements ApiFondyConstants {
    private static final String password = PASSWORD;
    private static final String connector = "|";

    public static String createNewSignature(PaymentRequest paymentRequest) {
        Map<String, Object> map = new HashMap<>();
        if (paymentRequest.getOrder_id() != null) {
            map.put("order_id", paymentRequest.getOrder_id());
        }
        if (paymentRequest.getMerchant_id() != null) {
            map.put("merchant_id", paymentRequest.getMerchant_id());
        }
        if (paymentRequest.getOrder_desc() != null) {
            map.put("order_desc", paymentRequest.getOrder_desc());
        }
        if (paymentRequest.getAmount() != null) {
            map.put("amount", paymentRequest.getAmount());
        }
        if (paymentRequest.getCurrency() != null) {
            map.put("currency", paymentRequest.getCurrency());
        }
        if (paymentRequest.getResponse_url() != null) {
            map.put("response_url", paymentRequest.getResponse_url());
        }
        if (paymentRequest.getServer_callback_url() != null) {
            map.put("server_callback_url", paymentRequest.getServer_callback_url());
        }
        if (paymentRequest.getSender_email() != null) {
            map.put("sender_email", paymentRequest.getSender_email());
        }
        if (paymentRequest.getProduct_id() != null) {
            map.put("product_id", paymentRequest.getProduct_id());
        }
        if (paymentRequest.getRequired_rectoken() != null) {
            map.put("required_rectoken", paymentRequest.getRequired_rectoken());
        }
        if (paymentRequest.getMerchant_data() != null) {
            map.put("merchant_data", paymentRequest.getMerchant_data());
        }

        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);

        StringBuilder signature = new StringBuilder(password);

        for (int a = 0; a < keys.size(); a++) {
            signature.append(connector);
            signature.append(map.get(keys.get(a)));
        }

        return DigestUtils.sha1Hex(String.valueOf(signature));
    }

    public static boolean isCorrectSignature(Map<String, String> parameters) {
        Map<String, String> parametersForSignature = new HashMap<>();
        for (String key: parameters.keySet()) {
            if (!key.equals("response_signature_string") && !key.equals("signature")) {
                if (parameters.get(key) != null && !parameters.get(key).equals("")) {
                    parametersForSignature.put(key, parameters.get(key));
                }
            }
        }
        List<String> keys = new ArrayList<>(parametersForSignature.keySet());
        Collections.sort(keys);

        StringBuilder signature = new StringBuilder(password);

        for (int a = 0; a < keys.size(); a++) {
            signature.append(connector);
            signature.append(parametersForSignature.get(keys.get(a)));
        }

        return DigestUtils.sha1Hex(String.valueOf(signature)).equals(parameters.get("signature"));
    }
}
