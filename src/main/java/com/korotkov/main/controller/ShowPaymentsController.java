package com.korotkov.main.controller;

import com.korotkov.main.model.CommonPaymentModel;
import com.korotkov.main.model.UserAccount;
import com.korotkov.main.service.userAccount.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ShowPaymentsController {
    UserAccountService userAccountService;

    @Autowired
    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @RequestMapping(value = "/myprofile/payments", method = RequestMethod.GET)
    public ModelAndView showUserPaymentsList(@AuthenticationPrincipal UserAccount userAccount) {
        ModelAndView modelAndView = new ModelAndView();
        List<CommonPaymentModel> commonPaymentModelList = userAccountService.getAllSuccessPayments(userAccount.getId());
        modelAndView.setViewName("/myProfile/payments");
        modelAndView.addObject("paymentList", commonPaymentModelList);
        modelAndView.addObject("listSize", commonPaymentModelList.size());
        return modelAndView;
    }
}
