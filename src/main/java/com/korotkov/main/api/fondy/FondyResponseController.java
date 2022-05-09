package com.korotkov.main.api.fondy;


import com.google.gson.Gson;
import com.korotkov.main.api.fondy.config.ApiFondyConstants;

import com.korotkov.main.api.fondy.model.PaymentFinalResponse;
import com.korotkov.main.api.fondy.service.CreateSignature;
import com.korotkov.main.api.fondy.service.PaymentService;
import com.korotkov.main.config.PortalConstants;
import com.korotkov.main.enums.TypePurchaseSubscription;
import com.korotkov.main.model.CommonPaymentModel;
import com.korotkov.main.model.UserAccount;
import com.korotkov.main.model.UserRole;
import com.korotkov.main.service.userAccount.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FondyResponseController implements ApiFondyConstants, PortalConstants {
    PaymentService paymentService;
    UserAccountService userAccountService;

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Autowired
    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }


    @RequestMapping(value = SERVER_CALLBACK_URL, method = RequestMethod.POST)
    public ResponseEntity responseFondyServer(@RequestBody String requestBody) {

        Gson gson = new Gson();
        PaymentFinalResponse paymentFinalResponse = gson.fromJson(requestBody, PaymentFinalResponse.class);

        Map<String, String> request = new HashMap<>();
        if (paymentFinalResponse.getOrder_id() != null) {
            request.put("order_id", paymentFinalResponse.getOrder_id());
        }
        if (paymentFinalResponse.getMerchant_id() != null) {
            request.put("merchant_id", paymentFinalResponse.getMerchant_id());
        }
        if (paymentFinalResponse.getAmount() != null) {
            request.put("amount", paymentFinalResponse.getAmount());
        }
        if (paymentFinalResponse.getCurrency() != null) {
            request.put("currency", paymentFinalResponse.getCurrency());
        }
        if (paymentFinalResponse.getOrder_status() != null) {
            request.put("order_status", paymentFinalResponse.getOrder_status());
        }
        if (paymentFinalResponse.getResponse_status() != null) {
            request.put("response_status", paymentFinalResponse.getResponse_status());
        }
        if (paymentFinalResponse.getSignature() != null) {
            request.put("signature", paymentFinalResponse.getSignature());
        }
        if (paymentFinalResponse.getTran_type() != null) {
            request.put("tran_type", paymentFinalResponse.getTran_type());
        }
        if (paymentFinalResponse.getSender_cell_phone() != null) {
            request.put("sender_cell_phone", paymentFinalResponse.getSender_cell_phone());
        }
        if (paymentFinalResponse.getSender_account() != null) {
            request.put("sender_account", paymentFinalResponse.getSender_account());
        }
        if (paymentFinalResponse.getMasked_card() != null) {
            request.put("masked_card", paymentFinalResponse.getMasked_card());
        }
        if (paymentFinalResponse.getCard_bin() != null) {
            request.put("card_bin", paymentFinalResponse.getCard_bin());
        }
        if (paymentFinalResponse.getCard_type() != null) {
            request.put("card_type", paymentFinalResponse.getCard_type());
        }
        if (paymentFinalResponse.getRrn() != null) {
            request.put("rrn", paymentFinalResponse.getRrn());
        }
        if (paymentFinalResponse.getApproval_code() != null) {
            request.put("approval_code", paymentFinalResponse.getApproval_code());
        }
        if (paymentFinalResponse.getResponse_code() != null) {
            request.put("response_code", paymentFinalResponse.getResponse_code());
        }
        if (paymentFinalResponse.getResponse_description() != null) {
            request.put("response_description", paymentFinalResponse.getResponse_description());
        }
        if (paymentFinalResponse.getReversal_amount() != null) {
            request.put("reversal_amount", paymentFinalResponse.getReversal_amount());
        }
        if (paymentFinalResponse.getSettlement_amount() != null) {
            request.put("settlement_amount", paymentFinalResponse.getSettlement_amount());
        }
        if (paymentFinalResponse.getSettlement_currency() != null) {
            request.put("settlement_currency", paymentFinalResponse.getSettlement_currency());
        }
        if (paymentFinalResponse.getOrder_time() != null) {
            request.put("order_time", paymentFinalResponse.getOrder_time());
        }
        if (paymentFinalResponse.getSettlement_date() != null) {
            request.put("settlement_date", paymentFinalResponse.getSettlement_date());
        }
        if (paymentFinalResponse.getEci() != null) {
            request.put("eci", paymentFinalResponse.getEci());
        }
        if (paymentFinalResponse.getFee() != null) {
            request.put("fee", paymentFinalResponse.getFee());
        }
        if (paymentFinalResponse.getPayment_system() != null) {
            request.put("payment_system", paymentFinalResponse.getPayment_system());
        }
        if (paymentFinalResponse.getSender_email() != null) {
            request.put("sender_email", paymentFinalResponse.getSender_email());
        }
        if (paymentFinalResponse.getPayment_id() != null) {
            request.put("payment_id", paymentFinalResponse.getPayment_id());
        }
        if (paymentFinalResponse.getActual_amount() != null) {
            request.put("actual_amount", paymentFinalResponse.getActual_amount());
        }
        if (paymentFinalResponse.getActual_currency() != null) {
            request.put("actual_currency", paymentFinalResponse.getActual_currency());
        }
        if (paymentFinalResponse.getProduct_id() != null) {
            request.put("product_id", paymentFinalResponse.getProduct_id());
        }
        if (paymentFinalResponse.getMerchant_data() != null) {
            request.put("merchant_data", paymentFinalResponse.getMerchant_data());
        }
        if (paymentFinalResponse.getVerification_status() != null) {
            request.put("verification_status", paymentFinalResponse.getVerification_status());
        }
        if (paymentFinalResponse.getRectoken() != null) {
            request.put("rectoken", paymentFinalResponse.getRectoken());
        }
        if (paymentFinalResponse.getRectoken_lifetime() != null) {
            request.put("rectoken_lifetime", paymentFinalResponse.getRectoken_lifetime());
        }
        if (paymentFinalResponse.getAdditional_info() != null) {
            request.put("additional_info", paymentFinalResponse.getAdditional_info());
        }



        CommonPaymentModel commonPaymentModel = paymentService.getById(Long.valueOf(request.get("order_id").split("-")[0]));
        if (!commonPaymentModel.getAmount().equals(Double.parseDouble(request.get("amount"))/100) ||
                !commonPaymentModel.getCurrency().equals(request.get("currency"))) {
            commonPaymentModel.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            commonPaymentModel.setStatusResponse("INTERNAL_ERROR");
            commonPaymentModel.setErrorMessage("Amount is not matched in response");
            return ResponseEntity.badRequest().body("Amount is not matched");
        }

        if (commonPaymentModel.getOrderStatus() != null) {
            if (commonPaymentModel.getOrderStatus().equals("approved") && request.get("order_status").equals("approved")) {
                return ResponseEntity.ok().build();
            }
        }

        if (!CreateSignature.isCorrectSignature(request)) {
            commonPaymentModel.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            commonPaymentModel.setStatusResponse("INTERNAL_ERROR");
            commonPaymentModel.setErrorMessage("Incorrect signature in response");
            return ResponseEntity.badRequest().body("Incorrect signature");
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        commonPaymentModel.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        if (request.get("response_status").length() != 0) {
            commonPaymentModel.setStatusResponse(request.get("response_status"));
        }
        if (request.get("order_status").length() != 0) {
            commonPaymentModel.setOrderStatus(request.get("order_status"));
        }
        if (request.get("tran_type").length() != 0) {
            commonPaymentModel.setTranType(request.get("tran_type"));
        }
        if (request.get("sender_cell_phone").length() != 0) {
            commonPaymentModel.setSenderCellPhone(request.get("sender_cell_phone"));
        }
        if (request.get("sender_account").length() != 0) {
            commonPaymentModel.setSenderAccount(request.get("sender_account"));
        }
        if (request.get("masked_card").length() != 0) {
            commonPaymentModel.setMaskedCard(request.get("masked_card"));
        }
        if (request.get("card_bin").length() != 0) {
            commonPaymentModel.setCardBin(Integer.valueOf(request.get("card_bin")));
        }
        if (request.get("card_type").length() != 0) {
            commonPaymentModel.setCardType(request.get("card_type"));
        }
        if (request.get("rrn").length() != 0) {
            commonPaymentModel.setRrn(request.get("rrn"));
        }
        if (request.get("approval_code").length() != 0) {
            commonPaymentModel.setApprovalCode(request.get("approval_code"));
        }
        if (request.get("response_code").length() != 0) {
            commonPaymentModel.setResponseCode(Integer.valueOf(request.get("response_code")));
        }
        if (request.get("response_description").length() != 0) {
            commonPaymentModel.setResponseDescription(request.get("response_description"));
        }
        if (request.get("reversal_amount").length() != 0) {
            commonPaymentModel.setReversalAmount(Double.parseDouble(request.get("reversal_amount"))/100);
        }
        if (request.get("settlement_amount").length() != 0) {
            commonPaymentModel.setSettlementAmount(Double.parseDouble(request.get("settlement_amount"))/100);
        }
        if (request.get("settlement_currency").length() != 0) {
            commonPaymentModel.setSettlementCurrency(request.get("settlement_currency"));
        }
        if (request.get("order_time").length() != 0) {
            commonPaymentModel.setOrderTime(LocalDateTime.parse(request.get("order_time"), dateTimeFormatter));
        }
        if (request.get("settlement_date").length() != 0) {
            commonPaymentModel.setSettlementDate(LocalDate.parse(request.get("settlement_date"), dateTimeFormatter));
        }
        if (request.get("eci").length() != 0) {
            commonPaymentModel.setEci(Integer.valueOf(request.get("eci")));
        }
        if (request.get("fee").length() != 0) {
            commonPaymentModel.setFee(Double.parseDouble(request.get("fee"))/100);
        }
        if (request.get("payment_system").length() != 0) {
            commonPaymentModel.setPaymentSystem(request.get("payment_system"));
        }
        if (request.get("sender_email").length() != 0) {
            commonPaymentModel.setSenderEmailResponse(request.get("sender_email"));
        }
        if (request.get("payment_id").length() != 0) {
            commonPaymentModel.setPaymentId(Integer.valueOf(request.get("payment_id")));
        }
        if (request.get("actual_amount").length() != 0) {
            commonPaymentModel.setActualAmount(Double.parseDouble(request.get("actual_amount"))/100);
        }
        if (request.get("actual_currency").length() != 0) {
            commonPaymentModel.setActualCurrency(request.get("actual_currency"));
        }
        if (request.get("rectoken").length() != 0) {
            commonPaymentModel.setRectoken(request.get("rectoken"));
        }
        if (request.get("rectoken_lifetime").length() != 0) {
            commonPaymentModel.setRectokenLifetime(LocalDateTime.parse(request.get("rectoken_lifetime"), dateTimeFormatter));
        }

        paymentService.update(commonPaymentModel);

        UserAccount userAccount = commonPaymentModel.getUserAccount();

        if (commonPaymentModel.getTypePurchase().equals(String.valueOf(TypePurchaseSubscription.RENEW))) {
            if (userAccount.getRole().getId().equals(commonPaymentModel.getUserRole().getId())) {
                userAccountService.renewSubscription(commonPaymentModel.getUserRole().getId(),
                        commonPaymentModel.getUserAccount().getId(), commonPaymentModel.getPeriodPurchase());
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        }

        if (commonPaymentModel.getTypePurchase().equals(String.valueOf(TypePurchaseSubscription.PURCHASE))) {
            if (!userAccount.getRole().getId().equals(commonPaymentModel.getUserRole().getId())) {
                updateAuthoritiesForCurrentSession(userAccountService.buySubscription(commonPaymentModel.getUserRole().getId(),
                        commonPaymentModel.getUserAccount().getId(), commonPaymentModel.getPeriodPurchase()));
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.badRequest().build();
    }


//    @RequestMapping(value = SERVER_CALLBACK_URL, method = RequestMethod.POST)
//    public ResponseEntity responseFondyServer(@RequestParam Map<String, String> request) {
//        CommonPaymentModel commonPaymentModel = paymentService.getById(Long.valueOf(request.get("order_id").split("-")[0]));
//        if (!commonPaymentModel.getAmount().equals(Double.parseDouble(request.get("amount"))/100) ||
//                !commonPaymentModel.getCurrency().equals(request.get("currency"))) {
//            commonPaymentModel.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
//            commonPaymentModel.setStatusResponse("INTERNAL_ERROR");
//            commonPaymentModel.setErrorMessage("Amount is not matched in response");
//            return ResponseEntity.badRequest().body("Amount is not matched");
//        }
//
//        if (commonPaymentModel.getOrderStatus() != null) {
//            if (commonPaymentModel.getOrderStatus().equals("approved") && request.get("order_status").equals("approved")) {
//                return ResponseEntity.ok().build();
//            }
//        }
//
//        if (!CreateSignature.isCorrectSignature(request)) {
//            commonPaymentModel.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
//            commonPaymentModel.setStatusResponse("INTERNAL_ERROR");
//            commonPaymentModel.setErrorMessage("Incorrect signature in response");
//            return ResponseEntity.badRequest().body("Incorrect signature");
//        }
//
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
//
//        commonPaymentModel.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
//        if (request.get("response_status").length() != 0) {
//            commonPaymentModel.setStatusResponse(request.get("response_status"));
//        }
//        if (request.get("order_status").length() != 0) {
//            commonPaymentModel.setOrderStatus(request.get("order_status"));
//        }
//        if (request.get("tran_type").length() != 0) {
//            commonPaymentModel.setTranType(request.get("tran_type"));
//        }
//        if (request.get("sender_cell_phone").length() != 0) {
//            commonPaymentModel.setSenderCellPhone(request.get("sender_cell_phone"));
//        }
//        if (request.get("sender_account").length() != 0) {
//            commonPaymentModel.setSenderAccount(request.get("sender_account"));
//        }
//        if (request.get("masked_card").length() != 0) {
//            commonPaymentModel.setMaskedCard(request.get("masked_card"));
//        }
//        if (request.get("card_bin").length() != 0) {
//            commonPaymentModel.setCardBin(Integer.valueOf(request.get("card_bin")));
//        }
//        if (request.get("card_type").length() != 0) {
//            commonPaymentModel.setCardType(request.get("card_type"));
//        }
//        if (request.get("rrn").length() != 0) {
//            commonPaymentModel.setRrn(request.get("rrn"));
//        }
//        if (request.get("approval_code").length() != 0) {
//            commonPaymentModel.setApprovalCode(request.get("approval_code"));
//        }
//        if (request.get("response_code").length() != 0) {
//            commonPaymentModel.setResponseCode(Integer.valueOf(request.get("response_code")));
//        }
//        if (request.get("response_description").length() != 0) {
//            commonPaymentModel.setResponseDescription(request.get("response_description"));
//        }
//        if (request.get("reversal_amount").length() != 0) {
//            commonPaymentModel.setReversalAmount(Double.parseDouble(request.get("reversal_amount"))/100);
//        }
//        if (request.get("settlement_amount").length() != 0) {
//            commonPaymentModel.setSettlementAmount(Double.parseDouble(request.get("settlement_amount"))/100);
//        }
//        if (request.get("settlement_currency").length() != 0) {
//            commonPaymentModel.setSettlementCurrency(request.get("settlement_currency"));
//        }
//        if (request.get("order_time").length() != 0) {
//            commonPaymentModel.setOrderTime(LocalDateTime.parse(request.get("order_time"), dateTimeFormatter));
//        }
//        if (request.get("settlement_date").length() != 0) {
//            commonPaymentModel.setSettlementDate(LocalDate.parse(request.get("settlement_date"), dateTimeFormatter));
//        }
//        if (request.get("eci").length() != 0) {
//            commonPaymentModel.setEci(Integer.valueOf(request.get("eci")));
//        }
//        if (request.get("fee").length() != 0) {
//            commonPaymentModel.setFee(Double.parseDouble(request.get("fee"))/100);
//        }
//        if (request.get("payment_system").length() != 0) {
//            commonPaymentModel.setPaymentSystem(request.get("payment_system"));
//        }
//        if (request.get("sender_email").length() != 0) {
//            commonPaymentModel.setSenderEmailResponse(request.get("sender_email"));
//        }
//        if (request.get("payment_id").length() != 0) {
//            commonPaymentModel.setPaymentId(Integer.valueOf(request.get("payment_id")));
//        }
//        if (request.get("actual_amount").length() != 0) {
//            commonPaymentModel.setActualAmount(Double.parseDouble(request.get("actual_amount"))/100);
//        }
//        if (request.get("actual_currency").length() != 0) {
//            commonPaymentModel.setActualCurrency(request.get("actual_currency"));
//        }
//        if (request.get("rectoken").length() != 0) {
//            commonPaymentModel.setRectoken(request.get("rectoken"));
//        }
//        if (request.get("rectoken_lifetime").length() != 0) {
//            commonPaymentModel.setRectokenLifetime(LocalDateTime.parse(request.get("rectoken_lifetime"), dateTimeFormatter));
//        }
//
//        paymentService.update(commonPaymentModel);
//
//        UserAccount userAccount = commonPaymentModel.getUserAccount();
//
//        if (commonPaymentModel.getTypePurchase().equals(String.valueOf(TypePurchaseSubscription.RENEW))) {
//            if (userAccount.getRole().getId().equals(commonPaymentModel.getUserRole().getId())) {
//                userAccountService.renewSubscription(commonPaymentModel.getUserRole().getId(),
//                        commonPaymentModel.getUserAccount().getId(), commonPaymentModel.getPeriodPurchase());
//                return ResponseEntity.ok().build();
//            }
//            return ResponseEntity.badRequest().build();
//        }
//
//        if (commonPaymentModel.getTypePurchase().equals(String.valueOf(TypePurchaseSubscription.PURCHASE))) {
//            if (!userAccount.getRole().getId().equals(commonPaymentModel.getUserRole().getId())) {
//                updateAuthoritiesForCurrentSession(userAccountService.buySubscription(commonPaymentModel.getUserRole().getId(),
//                        commonPaymentModel.getUserAccount().getId(), commonPaymentModel.getPeriodPurchase()));
//                return ResponseEntity.ok().build();
//            }
//            return ResponseEntity.badRequest().build();
//        }
//
//        return ResponseEntity.badRequest().build();
//    }

    @RequestMapping(value = RESPONSE_URL_POST, method = RequestMethod.POST)
    public ModelAndView responseFondyPortal(@RequestParam Map<String,String> request) {
        ModelAndView modelAndView = new ModelAndView();
        CommonPaymentModel commonPaymentModel = paymentService.getById(Long.valueOf(request.get("order_id").split("-")[0]));
        if (!commonPaymentModel.getAmount().equals(Double.parseDouble(request.get("amount"))/100) ||
        !commonPaymentModel.getCurrency().equals(request.get("currency"))) {
            commonPaymentModel.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            commonPaymentModel.setStatusResponse("INTERNAL_ERROR");
            commonPaymentModel.setErrorMessage("Amount is not matched in response");
            modelAndView.setViewName("redirect:" + RETURN_PORTAL_UNKNOWN_ERROR_URL);
            return modelAndView;
        }

        if (commonPaymentModel.getOrderStatus() != null) {
            if (commonPaymentModel.getOrderStatus().equals("approved") && request.get("order_status").equals("approved")) {
                modelAndView.setViewName("redirect:" + RETURN_PORTAL_UNKNOWN_ERROR_URL);
                return modelAndView;
            }
        }

        if (!CreateSignature.isCorrectSignature(request)) {
            commonPaymentModel.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            commonPaymentModel.setStatusResponse("INTERNAL_ERROR");
            commonPaymentModel.setErrorMessage("Incorrect signature in response");
            modelAndView.setViewName("redirect:" + RETURN_PORTAL_UNKNOWN_ERROR_URL);
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        commonPaymentModel.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        if (request.get("response_status").length() != 0) {
            commonPaymentModel.setStatusResponse(request.get("response_status"));
        }
        if (request.get("order_status").length() != 0) {
            commonPaymentModel.setOrderStatus(request.get("order_status"));
        }
        if (request.get("tran_type").length() != 0) {
            commonPaymentModel.setTranType(request.get("tran_type"));
        }
        if (request.get("sender_cell_phone").length() != 0) {
            commonPaymentModel.setSenderCellPhone(request.get("sender_cell_phone"));
        }
        if (request.get("sender_account").length() != 0) {
            commonPaymentModel.setSenderAccount(request.get("sender_account"));
        }
        if (request.get("masked_card").length() != 0) {
            commonPaymentModel.setMaskedCard(request.get("masked_card"));
        }
        if (request.get("card_bin").length() != 0) {
            commonPaymentModel.setCardBin(Integer.valueOf(request.get("card_bin")));
        }
        if (request.get("card_type").length() != 0) {
            commonPaymentModel.setCardType(request.get("card_type"));
        }
        if (request.get("rrn").length() != 0) {
            commonPaymentModel.setRrn(request.get("rrn"));
        }
        if (request.get("approval_code").length() != 0) {
            commonPaymentModel.setApprovalCode(request.get("approval_code"));
        }
        if (request.get("response_code").length() != 0) {
            commonPaymentModel.setResponseCode(Integer.valueOf(request.get("response_code")));
        }
        if (request.get("response_description").length() != 0) {
            commonPaymentModel.setResponseDescription(request.get("response_description"));
        }
        if (request.get("reversal_amount").length() != 0) {
            commonPaymentModel.setReversalAmount(Double.parseDouble(request.get("reversal_amount"))/100);
        }
        if (request.get("settlement_amount").length() != 0) {
            commonPaymentModel.setSettlementAmount(Double.parseDouble(request.get("settlement_amount"))/100);
        }
        if (request.get("settlement_currency").length() != 0) {
            commonPaymentModel.setSettlementCurrency(request.get("settlement_currency"));
        }
        if (request.get("order_time").length() != 0) {
            commonPaymentModel.setOrderTime(LocalDateTime.parse(request.get("order_time"), dateTimeFormatter));
        }
        if (request.get("settlement_date").length() != 0) {
            commonPaymentModel.setSettlementDate(LocalDate.parse(request.get("settlement_date"), dateTimeFormatter));
        }
        if (request.get("eci").length() != 0) {
            commonPaymentModel.setEci(Integer.valueOf(request.get("eci")));
        }
        if (request.get("fee").length() != 0) {
            commonPaymentModel.setFee(Double.parseDouble(request.get("fee"))/100);
        }
        if (request.get("payment_system").length() != 0) {
            commonPaymentModel.setPaymentSystem(request.get("payment_system"));
        }
        if (request.get("sender_email").length() != 0) {
            commonPaymentModel.setSenderEmailResponse(request.get("sender_email"));
        }
        if (request.get("payment_id").length() != 0) {
            commonPaymentModel.setPaymentId(Integer.valueOf(request.get("payment_id")));
        }
        if (request.get("actual_amount").length() != 0) {
            commonPaymentModel.setActualAmount(Double.parseDouble(request.get("actual_amount"))/100);
        }
        if (request.get("actual_currency").length() != 0) {
            commonPaymentModel.setActualCurrency(request.get("actual_currency"));
        }
        if (request.get("rectoken").length() != 0) {
            commonPaymentModel.setRectoken(request.get("rectoken"));
        }
        if (request.get("rectoken_lifetime").length() != 0) {
            commonPaymentModel.setRectokenLifetime(LocalDateTime.parse(request.get("rectoken_lifetime"), dateTimeFormatter));
        }

        paymentService.update(commonPaymentModel);


        if (request.get("tran_type").equals("reverse") || request.get("response_status").equals("failure") ||
                !request.get("order_status").equals("approved")) {
            modelAndView.setViewName("redirect:" + RETURN_PORTAL_UNKNOWN_ERROR_URL);
            return modelAndView;
        }

        UserAccount userAccount = commonPaymentModel.getUserAccount();

        if (commonPaymentModel.getTypePurchase().equals(String.valueOf(TypePurchaseSubscription.RENEW))) {
            if (userAccount.getRole().getId().equals(commonPaymentModel.getUserRole().getId())) {
                userAccountService.renewSubscription(commonPaymentModel.getUserRole().getId(),
                        commonPaymentModel.getUserAccount().getId(), commonPaymentModel.getPeriodPurchase());
                modelAndView.setViewName("redirect:/myprofile/mysubscription?action=SUCCESS_RENEW");
                return modelAndView;
            }
            modelAndView.setViewName("redirect:/myprofile/mysubscription?action=NOT_CURRENT_ROLE_RENEW");
            return modelAndView;
        }

        if (commonPaymentModel.getTypePurchase().equals(String.valueOf(TypePurchaseSubscription.PURCHASE))) {
            if (!userAccount.getRole().getId().equals(commonPaymentModel.getUserRole().getId())) {
                updateAuthoritiesForCurrentSession(userAccountService.buySubscription(commonPaymentModel.getUserRole().getId(),
                        commonPaymentModel.getUserAccount().getId(), commonPaymentModel.getPeriodPurchase()));
                modelAndView.setViewName("redirect:/myprofile/mysubscription?action=SUCCESS_PURCHASE");
                return modelAndView;
            }
            modelAndView.setViewName("redirect:/myprofile/mysubscription?action=NOT_BUY_CURRENT_ROLE");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:" + RETURN_PORTAL_UNKNOWN_ERROR_URL);
        return modelAndView;
    }



    private void updateAuthoritiesForCurrentSession(UserRole newRole) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>();
        updatedAuthorities.add(newRole);
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(),
                updatedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
