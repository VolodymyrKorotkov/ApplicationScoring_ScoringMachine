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
public class LoginCheckController implements PortalConstants {
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


    @RequestMapping(value = "/security/login/not-confirmed-email", method = RequestMethod.GET)
    public ModelAndView notConfirmedEmailPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/security/login-not-confirmed-email");
        return modelAndView;
    }

    @RequestMapping(value = "/security/subscription-expired", method = RequestMethod.GET)
    public ModelAndView subscriptionExpiredPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/security/expiredSubscription");
        modelAndView.addObject("userAccountForm", new UserAccount());
        return modelAndView;
    }

    @RequestMapping(value = "/security/subscription-expired", method = RequestMethod.POST)
    public ModelAndView subscriptionExpiredPost(@ModelAttribute("userAccountForm")
                                                @Valid UserAccount userAccountForm,
                                                BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("/security/expiredSubscription");
            modelAndView.addObject("passwordError","loginPage.errorBadCredentials");
            return modelAndView;
        } if (!userAccountService.checkIsUserByUsername(userAccountForm)){
            modelAndView.setViewName("/security/expiredSubscription");
            modelAndView.addObject("passwordError","loginPage.errorBadCredentials");
            return modelAndView;
        } if (!userAccountService.checkUserPassword(userAccountForm)){
            modelAndView.setViewName("/security/expiredSubscription");
            modelAndView.addObject("passwordError","loginPage.errorBadCredentials");
            return modelAndView;
        }
        userAccountService.updateRoleToLevelOne(userAccountForm);
        modelAndView.setViewName("redirect:/security/login?accountexpired=true");
        return modelAndView;
    }

    @RequestMapping(value = "/security/reset-password", method = RequestMethod.GET)
    public ModelAndView resetPasswordPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/security/reset-password");
        modelAndView.addObject("changedPass","false");
        modelAndView.addObject("userAccountForm", new UserAccount());
        return modelAndView;
    }

    @RequestMapping(value = "/security/reset-password", method = RequestMethod.POST)
    public ModelAndView resetPasswordProcess(@ModelAttribute("userAccountForm")
                                             @Valid UserAccount userAccountForm,
                                             BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/security/reset-password");
        if(bindingResult.hasErrors()){
            modelAndView.addObject("changedPass","false");
            return modelAndView;
        }
        if(!userAccountService.checkIsUserByUsername(userAccountForm)){
            modelAndView.addObject("changedPass","false");
            modelAndView.addObject("usernameError", "resetPasswordPage.errorBadCredentials");
            return modelAndView;
        }
        //генерим новый пароль, ставим его обязательно сменить и отправляем имейлом
        Map<String,Object> emailModel = new HashMap<>();
        emailModel.put("from",EMAIL_FROM);
        emailModel.put("mainUrl",MAIN_DOMAIN_URL);
        emailModel.put("subject", "Confirm New Password");
        emailModel.put("to", userAccountForm.getUsername());
        emailModel.put("newPassword",userAccountService.resetPassword(userAccountForm));

        boolean result = emailService.sendEmail("resetPassword.vm",emailModel);
        if(!result){
            modelAndView.addObject("changedPass", "emailError");
            return modelAndView;
        }
        modelAndView.addObject("changedPass", "true");
        return modelAndView;
    }


    @RequestMapping(value = "/security/login/change-exp-password", method = RequestMethod.GET)
    public ModelAndView viewChangingPasswordPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/security/login-change-exp-password");
        modelAndView.addObject("changedPass", "false");
        modelAndView.addObject("userAccountForm", new UserAccount());
        return modelAndView;
    }

    @RequestMapping(value = "/security/login/change-exp-password", method = RequestMethod.POST)
    public ModelAndView updateChangingPasswordPage(@ModelAttribute("userAccountForm")
                                                   @Valid UserAccount userAccountForm,
                                                   BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/security/login-change-exp-password");
            if(bindingResult.hasErrors()){
                modelAndView.addObject("changedPass","false");
                return modelAndView;
            }

            if(!userAccountService.checkUserPassword(userAccountForm)){
                modelAndView.addObject("changedPass","false");
                modelAndView.addObject("credentialsError","loginChangePasswordPage.errorBadCredentials");
                return modelAndView;
            }

            if(!ValidationUtils.isValidPassword(userAccountForm.getNewPassword())){
                modelAndView.addObject("changedPass","false");
                modelAndView.addObject("newPasswordError","loginChangePasswordPage.errorIncorrectWithPolicyPassword");
                return modelAndView;
            }

            if(!userAccountForm.getNewPassword().equals(userAccountForm.getPasswordConfirm())){
                modelAndView.addObject("changedPass","false");
                modelAndView.addObject("newPasswordError", "loginChangePasswordPage.errorTextPasswordNotMatchedError");
                return modelAndView;
            }

            if(userAccountForm.getPassword().equals(userAccountForm.getNewPassword())){
                modelAndView.addObject("changedPass","false");
                modelAndView.addObject("newPasswordError","loginChangePasswordPage.errorTextNewPasswordSameAsOldPassword");
                return modelAndView;
            }

            if(!userAccountService.saveNewTemporaryPassword(userAccountForm)){
                modelAndView.addObject("changedPass","false");
                modelAndView.addObject("credentialsError","loginChangePasswordPage.errorBadCredentials");
                return modelAndView;
            }

        if(!userAccountService.generateNewVerificationCode(userAccountForm)){
            modelAndView.addObject("changedPass","false");
            modelAndView.addObject("credentialsError","loginChangePasswordPage.errorBadCredentials");
            return modelAndView;
        }
        Map<String,Object> emailModel = new HashMap<>();
            emailModel.put("from",EMAIL_FROM);
            emailModel.put("mainUrl",MAIN_DOMAIN_URL);
            emailModel.put("subject", "Confirm New Password");
            emailModel.put("to", userAccountForm.getUsername());
            emailModel.put("verifyurl",userAccountService.generateNewLinkConfirmPassword(userAccountForm));

            boolean result = emailService.sendEmail("verifyNewExpPassword.vm", emailModel);
            if(!result){
                modelAndView.addObject("changedPass", "emailError");
                return modelAndView;
            }

            modelAndView.addObject("changedPass", "needCode");
            return modelAndView;
    }
}
