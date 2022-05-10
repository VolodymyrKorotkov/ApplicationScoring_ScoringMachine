package com.korotkov.main.api.fondy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.korotkov.main.api.fondy.config.ApiFondyConstants;
import com.korotkov.main.api.fondy.dao.CommonPaymentDao;
import com.korotkov.main.api.fondy.model.FondyFirstResponse;
import com.korotkov.main.api.fondy.model.PaymentFirstResponse;
import com.korotkov.main.model.CommonPaymentModel;
import com.korotkov.main.api.fondy.model.PaymentRequest;
import com.korotkov.main.config.PortalConstants;
import com.korotkov.main.dao.UserAccountDao;
import com.korotkov.main.dao.UserRoleDao;
import com.korotkov.main.enums.PaymentStatusRequestEnum;
import com.korotkov.main.enums.PeriodSubscriptionEnum;
import com.korotkov.main.enums.TypePurchaseSubscription;
import com.korotkov.main.enums.UserRoleEnum;
import com.korotkov.main.model.UserAccount;
import com.korotkov.main.model.UserRole;
import org.apache.commons.lang3.EnumUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class PaymentServiceImpl implements PaymentService, PortalConstants, ApiFondyConstants {

    private CommonPaymentDao commonPaymentDao;
    private UserAccountDao userAccountDao;
    private UserRoleDao userRoleDao;
    private RestTemplate restTemplate;


    @Autowired
    public void setCommonPaymentDao(CommonPaymentDao commonPaymentDao) {
        this.commonPaymentDao = commonPaymentDao;
    }

    @Autowired
    public void setUserAccountDao(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

    @Autowired
    public void setUserRoleDao(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @Transactional
    public String createNewPaymentRequestAndRedirect(Long roleId, UserAccount userAccount, String period,
                                                     String typePurchase) {
        if (!EnumUtils.isValidEnum(PeriodSubscriptionEnum.class, period)) {
            return "/myprofile/mysubscription?action=NOT_CORRECT_PERIOD";
        }
        if (!EnumUtils.isValidEnum(TypePurchaseSubscription.class, typePurchase)) {
            throw new RuntimeException("Not valid subscription purchase type, client id: " + userAccount.getId());
        }

        UserAccount userAccountFromDB = userAccountDao.getById(userAccount.getId());

        if (userRoleDao.findByUserRoleName(String.valueOf(UserRoleEnum.LEVEL_ONE)).getId()
                .equals(userAccountFromDB.getRole().getId()) &&
                typePurchase.equals(String.valueOf(TypePurchaseSubscription.RENEW))) {
            return "/myprofile/mysubscription?action=ROLE_ONE";
        }

        if (typePurchase.equals(String.valueOf(TypePurchaseSubscription.RENEW))) {
            if (!userAccountFromDB.getRole().getId().equals(roleId)) {
                return "/myprofile/mysubscription?action=NOT_CURRENT_ROLE_RENEW";
            }
        } else if (typePurchase.equals(String.valueOf(TypePurchaseSubscription.PURCHASE))) {
            if (userAccountFromDB.getRole().getId().equals(roleId)) {
                return "/myprofile/mysubscription?action=NOT_BUY_CURRENT_ROLE";
            }
        }

        UserRole userRolePurchase = userRoleDao.getById(roleId);

        String orderDesc = "Subscription to " + MAIN_DOMAIN_URL + ". Type purchase: " + typePurchase +
                ". Level of Subscription: " + userRolePurchase.getDescription() + ". Period of Subscription: " +
                period + ".";

        CommonPaymentModel commonPaymentModel = new CommonPaymentModel();
        commonPaymentModel.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        commonPaymentModel.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        commonPaymentModel.setUserAccount(userAccountFromDB);
        commonPaymentModel.setUserRole(userRolePurchase);
        commonPaymentModel.setMerchantId(MERCHANT_ID);
        commonPaymentModel.setOrderDescription(orderDesc);
        if (period.equals(String.valueOf(PeriodSubscriptionEnum.PERIOD_ONE_MONTH))) {
            commonPaymentModel.setAmount(userRolePurchase.getPriceOneMonth().doubleValue());
        } else if (period.equals(String.valueOf(PeriodSubscriptionEnum.PERIOD_THREE_MONTHS))) {
            commonPaymentModel.setAmount(userRolePurchase.getPriceThreeMonths().doubleValue());
        } else if (period.equals(String.valueOf(PeriodSubscriptionEnum.PERIOD_SIX_MONTHS))) {
            commonPaymentModel.setAmount(userRolePurchase.getPriceSixMonths().doubleValue());
        } else if (period.equals(String.valueOf(PeriodSubscriptionEnum.PERIOD_ONE_YEAR))) {
            commonPaymentModel.setAmount(userRolePurchase.getPriceOneYear().doubleValue());
        }
        commonPaymentModel.setCurrency(CURRENCY);
        commonPaymentModel.setResponseUrl(MAIN_DOMAIN_URL + RESPONSE_URL_POST);
        commonPaymentModel.setServerCallbackUrl(MAIN_DOMAIN_URL + SERVER_CALLBACK_URL);
        commonPaymentModel.setSenderEmailRequest(userAccountFromDB.getEmail());
        commonPaymentModel.setPeriodPurchase(period);
        commonPaymentModel.setTypePurchase(typePurchase);
        commonPaymentModel.setStatusRequest(String.valueOf(PaymentStatusRequestEnum.INITIAL));
        commonPaymentDao.create(commonPaymentModel);

        CommonPaymentModel commonPaymentModelSaved =
                commonPaymentDao.findLastCreatedPaymentByUser(userAccountFromDB.getId());

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrder_id(String.valueOf(commonPaymentModelSaved.getId()) + "-ScoringMachine");
        paymentRequest.setMerchant_data(String.valueOf(commonPaymentModelSaved.getId()));
        paymentRequest.setMerchant_id(commonPaymentModelSaved.getMerchantId());
        paymentRequest.setOrder_desc(commonPaymentModelSaved.getOrderDescription());
        paymentRequest.setAmount((int) (commonPaymentModelSaved.getAmount() * 100));
        paymentRequest.setCurrency(commonPaymentModelSaved.getCurrency());
        paymentRequest.setResponse_url(commonPaymentModelSaved.getResponseUrl());
        paymentRequest.setServer_callback_url(commonPaymentModelSaved.getServerCallbackUrl());
        paymentRequest.setSender_email(commonPaymentModelSaved.getSenderEmailRequest());
        paymentRequest.setProduct_id(commonPaymentModelSaved.getUserRole().getName());
        paymentRequest.setRequired_rectoken(REQUIRED_RECTOKEN);
        paymentRequest.setSignature(CreateSignature.createNewSignature(paymentRequest));


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject paymentRequestJsonObject = new JSONObject();
        JSONObject requestJsonObject = new JSONObject();
        try {
            paymentRequestJsonObject.put("order_id",paymentRequest.getOrder_id());
            paymentRequestJsonObject.put("merchant_id",paymentRequest.getMerchant_id());
            paymentRequestJsonObject.put("order_desc",paymentRequest.getOrder_desc());
            paymentRequestJsonObject.put("signature",paymentRequest.getSignature());
            paymentRequestJsonObject.put("amount",paymentRequest.getAmount());
            paymentRequestJsonObject.put("currency",paymentRequest.getCurrency());
            paymentRequestJsonObject.put("response_url",paymentRequest.getResponse_url());
            paymentRequestJsonObject.put("server_callback_url",paymentRequest.getServer_callback_url());
            paymentRequestJsonObject.put("merchant_data", paymentRequest.getMerchant_data());
            paymentRequestJsonObject.put("sender_email",paymentRequest.getSender_email());
            paymentRequestJsonObject.put("product_id",paymentRequest.getProduct_id());
            paymentRequestJsonObject.put("required_rectoken",paymentRequest.getRequired_rectoken());

            requestJsonObject.put("request",paymentRequestJsonObject);
        } catch (Exception e) {
            commonPaymentModelSaved.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            commonPaymentModelSaved.setStatusRequest(String.valueOf(PaymentStatusRequestEnum.ERROR));
            commonPaymentModelSaved.setErrorRequest(e.getMessage());
            commonPaymentDao.update(commonPaymentModelSaved);
            return RETURN_PORTAL_UNKNOWN_ERROR_URL;
        }

        HttpEntity<String> request = new HttpEntity<String>(requestJsonObject.toString(),headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(REQUEST_URL_TO_FONDY, request, String.class);

        if (responseEntity.getStatusCodeValue() != 200) {
            commonPaymentModelSaved.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            commonPaymentModelSaved.setStatusRequest(String.valueOf(PaymentStatusRequestEnum.ERROR));
            commonPaymentModelSaved.setErrorRequest("Response code for request: " + responseEntity.getStatusCodeValue());
            commonPaymentDao.update(commonPaymentModelSaved);
            return RETURN_PORTAL_UNKNOWN_ERROR_URL;
        }

        Gson gson = new Gson();

        FondyFirstResponse paymentFirstResponse = gson.fromJson(responseEntity.getBody(), FondyFirstResponse.class);

        if (paymentFirstResponse == null) {
            commonPaymentModelSaved.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            commonPaymentModelSaved.setStatusRequest(String.valueOf(PaymentStatusRequestEnum.ERROR));
            commonPaymentModelSaved.setErrorRequest("Error api Fondy: paymentFirstResponse is null");
            commonPaymentDao.update(commonPaymentModelSaved);
            return RETURN_PORTAL_UNKNOWN_ERROR_URL;
        }

        commonPaymentModelSaved.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        commonPaymentModelSaved.setTempResponseStatus(paymentFirstResponse.getResponse().getResponse_status());

        if (paymentFirstResponse.getResponse().getResponse_status().equals("success")) {
            commonPaymentModelSaved.setStatusRequest(String.valueOf(PaymentStatusRequestEnum.SUCCESS));
            commonPaymentModelSaved.setTempPaymentId(paymentFirstResponse.getResponse().getPayment_id());
            commonPaymentModelSaved.setTempCheckoutUrl(paymentFirstResponse.getResponse().getCheckout_url());
            commonPaymentDao.update(commonPaymentModelSaved);
            return paymentFirstResponse.getResponse().getCheckout_url();
        } else if (paymentFirstResponse.getResponse().getResponse_status().equals("failure")) {
            commonPaymentModelSaved.setStatusRequest(String.valueOf(PaymentStatusRequestEnum.SUCCESS));
            commonPaymentModelSaved.setErrorCode(paymentFirstResponse.getResponse().getError_code());
            commonPaymentModelSaved.setErrorMessage(paymentFirstResponse.getResponse().getError_message());
            commonPaymentDao.update(commonPaymentModelSaved);
            return RETURN_PORTAL_ERROR_PART_URL + paymentFirstResponse.getResponse().getError_code();
        }
        commonPaymentModelSaved.setStatusRequest(String.valueOf(PaymentStatusRequestEnum.ERROR));
        commonPaymentModelSaved.setErrorRequest("Incorrect status response");
        commonPaymentDao.update(commonPaymentModelSaved);
        return RETURN_PORTAL_UNKNOWN_ERROR_URL;
    }

    @Override
    @Transactional
    public CommonPaymentModel getById(Long id) {
        return commonPaymentDao.getById(id);
    }

    @Override
    @Transactional
    public void update(CommonPaymentModel commonPaymentModel) {
        commonPaymentDao.update(commonPaymentModel);
    }





    private void updatePaymentModelToErrorRequest(CommonPaymentModel commonPaymentModel, String errorMessage) {
        commonPaymentModel.setStatusRequest(String.valueOf(PaymentStatusRequestEnum.ERROR));
        commonPaymentModel.setErrorRequest(errorMessage);
        commonPaymentModel.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        commonPaymentDao.update(commonPaymentModel);
    }

}
