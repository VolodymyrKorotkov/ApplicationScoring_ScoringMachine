package com.korotkov.main.controller;

import com.korotkov.main.api.fondy.service.PaymentService;
import com.korotkov.main.enums.PeriodSubscriptionEnum;
import com.korotkov.main.enums.TypePurchaseSubscription;
import com.korotkov.main.enums.UserRoleEnum;
import com.korotkov.main.model.UserAccount;
import com.korotkov.main.model.UserRole;
import com.korotkov.main.service.userAccount.UserAccountService;
import com.korotkov.main.service.userRole.UserRoleService;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SubscriptionsController {
    UserRoleService userRoleService;
    UserAccountService userAccountService;
    PaymentService paymentService;

    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Autowired
    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @RequestMapping(value = "/myprofile/mysubscription/renew/{currentRoleId}/{period}", method = RequestMethod.GET)
    public ModelAndView renewSubscriptionButton(@PathVariable(value = "currentRoleId") Long currentRoleId,
                                          @PathVariable(value = "period") String period,
                                          @AuthenticationPrincipal UserAccount userAccount) {
        ModelAndView modelAndView = new ModelAndView();
        if (!userAccountService.getById(userAccount.getId()).getRole().getId().equals(currentRoleId)) {
            modelAndView.setViewName("redirect:/myprofile/mysubscription?action=NOT_CURRENT_ROLE_RENEW");
            return modelAndView;
        }
        if (userRoleService.findByUserRoleName(String.valueOf(UserRoleEnum.LEVEL_ONE)).getId().equals(currentRoleId)) {
            modelAndView.setViewName("redirect:/myprofile/mysubscription?action=ROLE_ONE");
            return modelAndView;
        }
        if (!EnumUtils.isValidEnum(PeriodSubscriptionEnum.class, period)) {
            modelAndView.setViewName("redirect:/myprofile/mysubscription?action=NOT_CORRECT_PERIOD");
            return modelAndView;
        }
        //заглушка
        modelAndView.setViewName("soonWeStart");


        // дальше сервис именно для интеграции с Fondy
//        modelAndView.setViewName("redirect:" + paymentService.createNewPaymentRequestAndRedirect(currentRoleId,
//                userAccount, period, String.valueOf(TypePurchaseSubscription.RENEW)));
        return modelAndView;
    }

    @RequestMapping(value = "/myprofile/mysubscription/buy/{roleId}/{period}", method = RequestMethod.GET)
    public ModelAndView buySubscriptionButton(@PathVariable(value = "roleId") Long roleId,
                                        @PathVariable(value = "period") String period,
                                        @AuthenticationPrincipal UserAccount userAccount) {
        ModelAndView modelAndView = new ModelAndView();
        if (userAccountService.getById(userAccount.getId()).getRole().getId().equals(roleId)) {
            modelAndView.setViewName("redirect:/myprofile/mysubscription?action=NOT_BUY_CURRENT_ROLE");
            return modelAndView;
        }
        if (!EnumUtils.isValidEnum(PeriodSubscriptionEnum.class, period)) {
            modelAndView.setViewName("redirect:/myprofile/mysubscription?action=NOT_CORRECT_PERIOD");
            return modelAndView;
        }
        //заглушка
        modelAndView.setViewName("soonWeStart");

        // дальше сервис именно для интеграции с Fondy
//        modelAndView.setViewName("redirect:" + paymentService.createNewPaymentRequestAndRedirect(roleId, userAccount,
//                period, String.valueOf(TypePurchaseSubscription.PURCHASE)));
        return modelAndView;
    }


}
