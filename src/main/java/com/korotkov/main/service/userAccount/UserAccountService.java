package com.korotkov.main.service.userAccount;

import com.korotkov.main.model.CommonPaymentModel;
import com.korotkov.main.model.UserAccount;
import com.korotkov.main.model.UserRole;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserAccountService extends UserDetailsService {
    void create(UserAccount userAccount);
    void update(UserAccount userAccount);
    UserAccount getById(Long id);
    String getPasswordByUsername(String username);
    UserDetails loadUserByUsername(String s);
    boolean saveUser(UserAccount userAccount);
    boolean updateEmailConfirmedToTrue(Long iduser, String verificationCode);
    boolean checkUserPassword(UserAccount userAccount);
    boolean saveNewTemporaryPassword(UserAccount userAccount);
    boolean saveNewPasswordFinal(Long iduser, String verificationCode);
    boolean generateNewVerificationCode(UserAccount userAccount);
    String generateNewLinkConfirmEmail(UserAccount userAccount);
    String generateNewLinkConfirmPassword(UserAccount userAccount);
    boolean checkIsUserByUsername(UserAccount userAccount);
    String resetPassword(UserAccount userAccount);
    UserAccount findByUsername(String username);
    UserAccount changeUserAccountData(UserAccount userAccount);
    UserAccount saveNewPasswordWithoutConfirm(UserAccount userAccount);
    boolean saveTempEmailForChange(UserAccount userAccount);
    String generateNewLinkConfirmNewChangedEmail(UserAccount userAccount);
    boolean updateEmailAfterTempChange(Long iduser, String verificationCode);
    Integer getCountMaxPossibleSavedModel(UserAccount userAccount);
    Long getCurrentCountSavedModels(UserAccount userAccount);
    Long getCurrentCountSavedTests(UserAccount userAccount);
    Integer getCountMaxPossibleSavedTest(UserAccount userAccount);
    boolean isProfileFull(UserAccount userAccount);
    void updateRoleToLevelOne(UserAccount userAccount);
    void renewSubscription(Long roleId, Long userId, String period);
    UserRole buySubscription(Long roleId, Long userId, String period);
    List<CommonPaymentModel> getAllSuccessPayments(Long userAccountId);
}
