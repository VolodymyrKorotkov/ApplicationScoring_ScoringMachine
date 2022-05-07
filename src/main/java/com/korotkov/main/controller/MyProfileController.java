package com.korotkov.main.controller;

import com.korotkov.main.config.PortalConstants;
import com.korotkov.main.enums.UserGender;
import com.korotkov.main.model.UserAccount;
import com.korotkov.main.service.email.EmailService;
import com.korotkov.main.service.userAccount.UserAccountService;
import com.korotkov.main.service.userRole.UserRoleService;
import com.korotkov.main.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MyProfileController implements PortalConstants {
    UserAccountService userAccountService;
    UserRoleService userRoleService;
    EmailService emailService;

    @Autowired
    public void setUserAccountService(UserAccountService userAccountService){
        this.userAccountService = userAccountService;
    }

    @Autowired
    public void setEmailService(EmailService emailService){
        this.emailService = emailService;
    }

    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }


    @RequestMapping(value = "/myprofile/profile/view", method = RequestMethod.GET)
    public ModelAndView profilePageView(@AuthenticationPrincipal UserAccount user){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/myProfile/myProfileView");
        modelAndView.addObject("userAccount", userAccountService.findByUsername(user.getUsername()));
        return modelAndView;
    }

    @RequestMapping(value = "/myprofile/profile/edit", method = RequestMethod.GET)
    public ModelAndView profilePageEdit(@AuthenticationPrincipal UserAccount user,
                                        @RequestParam(defaultValue = "NaN") String action){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("myProfile/myProfileEdit");
        modelAndView.addObject("userAccount",userAccountService.findByUsername(user.getUsername()));
        if(action.equals("notFullProfile")){
            modelAndView.addObject("message", "myProfilePage.messageNotFullProfile");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/myprofile/profile/edit", method = RequestMethod.POST)
    public ModelAndView profilePageEditPost(@ModelAttribute("userAccount")
                                            @Valid UserAccount userAccount,
                                            BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("/myProfile/myProfileEdit");
            return modelAndView;
        }
        if(!ValidationUtils.isCyrillicOrLatin(userAccount.getFirstName())){
            modelAndView.setViewName("/myProfile/myProfileEdit");
            modelAndView.addObject("firstNameError", "myProfilePage.errorName");
            return modelAndView;
        }
        if(!ValidationUtils.isCyrillicOrLatin((userAccount.getLastName()))){
            modelAndView.setViewName("/myProfile/myProfileEdit");
            modelAndView.addObject("lastNameError", "myProfilePage.errorName");
            return modelAndView;
        }
        if(!ValidationUtils.isValidDateOfBirth(userAccount.getDateOfBirth())){
            modelAndView.setViewName("/myProfile/myProfileEdit");
            modelAndView.addObject("birthDateError", "myProfilePage.errorBirthDate");
            return modelAndView;
        }
        if(!userAccount.getGender().equals(String.valueOf(UserGender.MALE)) && !userAccount.getGender().equals(String.valueOf(UserGender.FEMALE))){
            modelAndView.setViewName("/myProfile/myProfileEdit");
            modelAndView.addObject("genderError", "myProfilePage.errorGender");
            return modelAndView;
        }

        modelAndView.setViewName("/myProfile/myProfileView");
        modelAndView.addObject("userAccount", userAccountService.changeUserAccountData(userAccount));
        modelAndView.addObject("message","myProfilePage.messageSuccessChangeCommonData");
        return modelAndView;
    }

    @RequestMapping(value = "/myprofile/profile/change-email", method = RequestMethod.GET)
    public ModelAndView pageMyProfileChangeEmail(@AuthenticationPrincipal UserAccount userAccount){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/myProfile/myProfileEditEmail");
        UserAccount newUserAccount = new UserAccount();
        newUserAccount.setUsername(userAccount.getUsername());
        modelAndView.addObject("userAccount", newUserAccount);
        return modelAndView;
    }

    @RequestMapping(value = "/myprofile/profile/change-password",method = RequestMethod.GET)
    public ModelAndView pageMyProfileChangePassword(@AuthenticationPrincipal UserAccount userAccount){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/myProfile/myProfileEditPassword");
        UserAccount newUserAccount = new UserAccount();
        newUserAccount.setUsername(userAccount.getUsername());
        modelAndView.addObject("userAccount", newUserAccount);
        return modelAndView;
    }

    @RequestMapping(value = "/myprofile/profile/change-email", method = RequestMethod.POST)
    public ModelAndView pageMyProfileChangeEmailPost(@ModelAttribute("userAccount")
                                                     @Valid UserAccount userAccount,
                                                     BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("/myProfile/myProfileEditEmail");
            return modelAndView;
        }
        if(!userAccountService.checkUserPassword(userAccount)){
            modelAndView.setViewName("/myProfile/myProfileEditEmail");
            modelAndView.addObject("errorBadPassword","myProfilePage.errorBadPassword");
            return modelAndView;
        }
        if(!ValidationUtils.isValidEmail(userAccount.getTempEmailDuringChange())){
            modelAndView.setViewName("/myProfile/myProfileEditEmail");
            modelAndView.addObject("usernameError", "registrationPage.errorIncorrectEmail");
            return modelAndView;
        }
        if(!userAccountService.saveTempEmailForChange(userAccount)){
            modelAndView.setViewName("/myProfile/myProfileEditEmail");
            modelAndView.addObject("usernameError","registrationPage.errorTextUsernameErrorSameUserAlreadyExists");
            return modelAndView;
        }
        if(!userAccountService.generateNewVerificationCode(userAccount)){
            modelAndView.setViewName("/myProfile/myProfileEditEmail");
            modelAndView.addObject("errorBadPassword", "myProfilePage.errorOther");
            return modelAndView;
        }
        Map<String, Object> emailModel = new HashMap<>();
        emailModel.put("from",EMAIL_FROM);
        emailModel.put("mainUrl",MAIN_DOMAIN_URL);
        emailModel.put("logoUrl",LOGO_URL);
        emailModel.put("subject", "Verify and Confirm new email");
        emailModel.put("to", userAccount.getTempEmailDuringChange());
        emailModel.put("verifyurl", userAccountService.generateNewLinkConfirmNewChangedEmail(userAccount));
        boolean result = emailService.sendEmail("verifyNewEmailAfterChange.vm", emailModel);
        if(!result){
            modelAndView.setViewName("/myProfile/myProfileEditEmail");
            modelAndView.addObject("errorBadPassword", "myProfilePage.errorOther");
            return modelAndView;
        }
        modelAndView.setViewName("/myProfile/myProfileView");
        modelAndView.addObject("userAccount", userAccountService.findByUsername(userAccount.getUsername()));
        modelAndView.addObject("message", "myProfilePage.messageSuccessChangeTempEmail");
        return modelAndView;
    }

    @RequestMapping(value = "/myprofile/profile/change-password", method = RequestMethod.POST)
    public ModelAndView pageMyProfileChangePasswordPost(@ModelAttribute("userAccount")
                                                        @Valid UserAccount userAccount,
                                                        BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("/myProfile/myProfileEditPassword");
            return modelAndView;
        }
        if(!userAccountService.checkUserPassword(userAccount)){
            modelAndView.setViewName("/myProfile/myProfileEditPassword");
            modelAndView.addObject("errorBadPassword","myProfilePage.errorBadPassword");
            return modelAndView;
        }
        if(!ValidationUtils.isValidPassword(userAccount.getNewPassword())){
            modelAndView.setViewName("/myProfile/myProfileEditPassword");
            modelAndView.addObject("newPasswordError", "loginChangePasswordPage.errorIncorrectWithPolicyPassword");
            return modelAndView;
        }
        if(!userAccount.getNewPassword().equals(userAccount.getPasswordConfirm())){
            modelAndView.setViewName("/myProfile/myProfileEditPassword");
            modelAndView.addObject("newPasswordError", "loginChangePasswordPage.errorTextPasswordNotMatchedError");
            return modelAndView;
        }
        if(userAccount.getPassword().equals(userAccount.getNewPassword())){
            modelAndView.setViewName("myProfile/myProfileEditPassword");
            modelAndView.addObject("newPasswordError", "loginChangePasswordPage.errorTextNewPasswordSameAsOldPassword");
            return modelAndView;
        }
        modelAndView.setViewName("/myProfile/myProfileView");
        modelAndView.addObject("userAccount", userAccountService.saveNewPasswordWithoutConfirm(userAccount));
        modelAndView.addObject("message", "myProfilePage.messageSuccessChangePassword");
        return modelAndView;
    }

    @RequestMapping(value = "/myprofile/mysubscription", method = RequestMethod.GET)
    public ModelAndView mySubscriptionPageView(@AuthenticationPrincipal UserAccount userAccount,
                                               @RequestParam(defaultValue = "NaN") String action,
                                               @RequestParam(defaultValue = "0") int error){
        ModelAndView modelAndView = new ModelAndView();
        if (userAccountService.isProfileFull(userAccount)) {
            modelAndView.setViewName("redirect:/myprofile/profile/edit?action=notFullProfile");
            return modelAndView;
        }
        UserAccount userAccountFromDB = userAccountService.getById(userAccount.getId());
        modelAndView.addObject("currentRole", userAccountFromDB.getRole());
        modelAndView.addObject("listOtherRoles", userRoleService.getAllRolesWithoutCurrent(userAccountFromDB));
        modelAndView.addObject("subscriptionActiveUntil", userAccountFromDB.getSubscriptionExpiredAt());
        modelAndView.setViewName("/myProfile/mySubscription");
        if (action.equals("NOT_CURRENT_ROLE_RENEW")) {
            modelAndView.addObject("badMessage", "mySubscriptionPage.badMessageRenewNotCurrentRole");
        } else if (action.equals("ROLE_ONE")) {
            modelAndView.addObject("badMessage", "mySubscriptionPage.badMessageRoleOne");
        } else if (action.equals("NOT_CORRECT_PERIOD")) {
            modelAndView.addObject("badMessage", "mySubscriptionPage.badMessageNotCorrectPeriod");
        } else if (action.equals("NOT_BUY_CURRENT_ROLE")) {
            modelAndView.addObject("badMessage", "mySubscriptionPage.badMessageNotBuyCurrentRole");
        } else if (action.equals("SUCCESS_RENEW")) {
            modelAndView.addObject("goodMessage", "mySubscriptionPage.goodMessageSuccessRenew");
        } else if (action.equals("SUCCESS_PURCHASE")) {
            modelAndView.addObject("goodMessage", "mySubscriptionPage.goodMessageSuccessPurchase");
        } else if (action.equals("API_FONDY_ERROR_UNKNOWN")) {
            modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageErrorUnknown");
        } else if (action.equals("API_FONDY_ERROR")) {
            if (error == 1003) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1003");
            } else if (error == 1004) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1004");
            } else if (error == 1005) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1005");
            } else if (error == 1015) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1015");
            } else if (error == 1024) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1024");
            } else if (error == 1025) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1025");
            } else if (error == 1037) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1037");
            } else if (error == 1040) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1040");
            } else if (error == 1043) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1043");
            } else if (error == 1044) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1044");
            } else if (error == 1047) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1047");
            } else if (error == 1048) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1048");
            } else if (error == 1057) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1057");
            } else if (error == 1058) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1058");
            } else if (error == 1063) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1063");
            } else if (error == 1064) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1064");
            } else if (error == 1065) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1065");
            } else if (error == 1067) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1067");
            } else if (error == 1073) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1073");
            } else if (error == 1079) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1079");
            } else if (error == 1097) {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageError1097");
            } else {
                modelAndView.addObject("badMessage", "mySubscriptionPage.paymentMessageErrorUnknown");
            }
        }
        return modelAndView;
    }


}
