package com.korotkov.main.controller;

import com.korotkov.main.service.email.EmailService;
import com.korotkov.main.service.userAccount.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class VerificationController {
    UserAccountService userAccountService;
    EmailService emailService;

    @Autowired
    public void setUserAccountService(UserAccountService userAccountService){
        this.userAccountService = userAccountService;
    }

    @Autowired
    public void setEmailService(EmailService emailService){
        this.emailService = emailService;
    }


    @RequestMapping(value = "/security/verification/email/{iduser}/{code}",method = RequestMethod.GET)
    public ModelAndView verificationEmail(@PathVariable(value = "iduser") Long iduser,
                                          @PathVariable(value = "code") String code){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/security/registration");
        if(!userAccountService.updateEmailConfirmedToTrue(iduser, code)){
            modelAndView.addObject("needDynamicCode","error");
            return modelAndView;
        }
        modelAndView.addObject("needDynamicCode", "false");
        return modelAndView;
    }

    @RequestMapping(value = "/security/verification/change-password/{iduser}/{code}", method = RequestMethod.GET)
    public ModelAndView verificationChangePassword(@PathVariable(value = "iduser") Long iduser,
                                                   @PathVariable(value = "code") String code){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/security/login-change-exp-password");
        if(!userAccountService.saveNewPasswordFinal(iduser, code)){
            modelAndView.addObject("changedPass", "error");
            return modelAndView;
        }
        modelAndView.addObject("changedPass", "true");
        return modelAndView;
    }

    @RequestMapping(value = "/security/verification/new-email/{iduser}/{code}", method = RequestMethod.GET)
    public ModelAndView verificationChangeEmail(@PathVariable(value = "iduser") Long iduser,
                                                @PathVariable(value = "code") String code){
        ModelAndView modelAndView = new ModelAndView();
        if(!userAccountService.updateEmailAfterTempChange(iduser, code)){
            modelAndView.setViewName("/security/not-confirm-new-email");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/security/logout");
        return modelAndView;
    }
}
