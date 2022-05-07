package com.korotkov.main.api.fondy;


import com.korotkov.main.api.fondy.config.ApiFondyConstants;

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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public ResponseEntity responseFondyServer(@RequestParam Map<String, String> request) {
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
