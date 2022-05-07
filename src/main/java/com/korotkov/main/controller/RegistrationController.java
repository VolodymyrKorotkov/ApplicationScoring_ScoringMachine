package com.korotkov.main.controller;

import com.korotkov.main.config.PortalConstants;
import com.korotkov.main.model.UserAccount;
import com.korotkov.main.service.email.EmailService;
import com.korotkov.main.service.userAccount.UserAccountService;
import com.korotkov.main.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@Controller
public class RegistrationController implements PortalConstants {


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

    @RequestMapping(value = "/security/registration", method = RequestMethod.GET)
    public ModelAndView registrationPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/security/registration");
        modelAndView.addObject("needDynamicCode","null");
        modelAndView.addObject("userAccountForm",new UserAccount());
        return modelAndView;
    }

    @RequestMapping(value = "/security/registration", method = RequestMethod.POST)
    public ModelAndView registrationNewUserAccount(@ModelAttribute("userAccountForm")
                                                   @Valid UserAccount userAccountForm,
                                                   BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
            if(bindingResult.hasErrors()){
                modelAndView.setViewName("/security/registration");
                modelAndView.addObject("needDynamicCode","null");
                return modelAndView;
            }
            if(!ValidationUtils.isValidEmail(userAccountForm.getUsername())){
                modelAndView.setViewName("/security/registration");
                modelAndView.addObject("needDynamicCode","null");
                modelAndView.addObject("usernameError", "registrationPage.errorIncorrectEmail");
                return modelAndView;
            }
            if(!ValidationUtils.isValidPassword(userAccountForm.getPassword())){
                modelAndView.setViewName("/security/registration");
                modelAndView.addObject("needDynamicCode","null");
                modelAndView.addObject("passwordError", "registrationPage.errorIncorrectWithPolicyPassword");
                return modelAndView;
            }
            if(!userAccountForm.getPassword().equals(userAccountForm.getPasswordConfirm())){
                modelAndView.setViewName("/security/registration");
                modelAndView.addObject("needDynamicCode","null");
                modelAndView.addObject("passwordError", "registrationPage.errorTextPasswordNotMatchedError");
                return modelAndView;
            } if(!userAccountService.saveUser(userAccountForm)){
                modelAndView.setViewName("/security/registration");
                modelAndView.addObject("needDynamicCode","null");
                modelAndView.addObject("usernameError","registrationPage.errorTextUsernameErrorSameUserAlreadyExists");
                return modelAndView;
            }
            modelAndView.setViewName("/security/registration");
            if(!userAccountService.generateNewVerificationCode(userAccountForm)){
                modelAndView.addObject("needDynamicCode","null");
                modelAndView.addObject("usernameError","registrationPage.errorTextPasswordNotMatchedError");
            }
            Map<String,Object> emailModel = new HashMap<>();
            emailModel.put("from",EMAIL_FROM);
            emailModel.put("mainUrl",MAIN_DOMAIN_URL);
            emailModel.put("logoUrl",LOGO_URL);
            emailModel.put("subject", "Verify and Confirm email");
            emailModel.put("to", userAccountForm.getUsername());
            emailModel.put("verifyurl",userAccountService.generateNewLinkConfirmEmail(userAccountForm));
            boolean result = emailService.sendEmail("verifyEmail.vm",emailModel);
            if(!result){
                modelAndView.addObject("needDynamicCode","emailError");
                return modelAndView;
            }
                modelAndView.addObject("needDynamicCode","true");
                return modelAndView;
    }
}
