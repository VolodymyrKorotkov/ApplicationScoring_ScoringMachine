package com.korotkov.main.service.userAccount;

import com.korotkov.main.config.PortalConstants;
import com.korotkov.main.dao.ScoringSettingsModelDao;
import com.korotkov.main.dao.UserAccountDao;
import com.korotkov.main.dao.UserRoleDao;
import com.korotkov.main.enums.PeriodSubscriptionEnum;
import com.korotkov.main.enums.ScoringSettingsQualityLevel;
import com.korotkov.main.enums.UserRoleEnum;
import com.korotkov.main.enums.UserStatus;
import com.korotkov.main.model.CommonPaymentModel;
import com.korotkov.main.model.ScoringSettingsModel;
import com.korotkov.main.model.UserAccount;
import com.korotkov.main.model.UserRole;
import com.korotkov.main.service.scoringModel.additionalService.ScoringDefaultSettingsLevelTwoMain;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class UserAccountServiceImpl implements UserAccountService, PortalConstants, ScoringDefaultSettingsLevelTwoMain {
    private UserAccountDao userAccountDao;
    private UserRoleDao userRoleDao;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    private ScoringSettingsModelDao scoringSettingsModelDao;


    @Autowired
    public void setUserAccountDao(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

    @Autowired
    public void setUserRoleDao(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void setScoringSettingsModelDao(ScoringSettingsModelDao scoringSettingsModelDao){
        this.scoringSettingsModelDao = scoringSettingsModelDao;
    }



    @Override
    @Transactional
    public void create(UserAccount userAccount) {
        userAccountDao.create(userAccount);
    }

    @Override
    @Transactional
    public void update(UserAccount userAccount) {
        userAccountDao.update(userAccount);
    }

    @Override
    @Transactional
    public UserAccount getById(Long id) {
        return userAccountDao.getById(id);
    }

    @Override
    @Transactional
    public String getPasswordByUsername(String username) {
        return userAccountDao.getPasswordByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountDao.findByUsername(username);
        if (userAccount == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return userAccount;
    }

    @Override
    @Transactional
    public UserAccount findByUsername(String username){
        return userAccountDao.findByUsername(username);
    }

    @Override
    @Transactional
    public boolean saveUser(UserAccount userAccount) {
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        if (userAccountFromDB != null) {
            if (userAccountFromDB.isEmailConfirmed()) {
                return false;
            }
            userAccountFromDB.setPassword(bCryptPasswordEncoder.encode(userAccount.getPassword()));
            userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            userAccountDao.update(userAccountFromDB);
            return true;
        }
        userAccount.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        userAccount.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        userAccount.setEmail(userAccount.getUsername());
        userAccount.setEmailConfirmed(false);
        userAccount.setPasswordChangeRequired(false);
        userAccount.setSuperUser(false);
        userAccount.setStatus(String.valueOf(UserStatus.INACTIVE));
        userAccount.setRole(userRoleDao.findByUserRoleName(String.valueOf(UserRoleEnum.LEVEL_ONE)));
        userAccount.setPassword(bCryptPasswordEncoder.encode(userAccount.getPassword()));
        userAccount.setPasswordExpiredAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)).plusMonths(COUNT_MONTH_FOR_EXPIRED_PASSWORD));
        userAccountDao.create(userAccount);
        ScoringSettingsModel scoringSettingsModel = new ScoringSettingsModel();
        scoringSettingsModel.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        scoringSettingsModel.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        scoringSettingsModel.setUserAccount(userAccountDao.findByUsername(userAccount.getUsername()));
        scoringSettingsModel.setGoodResult(goodResult);
        scoringSettingsModel.setBadResult(badResult);
        scoringSettingsModel.setMinimumNeededIVForParameterOne(minimumNeededIVForParameterOne);
        scoringSettingsModel.setMinimumNeededAverageIVForKeyOfParameterOne(minimumNeededAverageIVForKeyOfParameterOne);
        scoringSettingsModel.setMinimumNeededIVForParameterTwo(minimumNeededIVForParameterTwo);
        scoringSettingsModel.setMinimumNeededAverageIVForKeyOfParameterTwo(minimumNeededAverageIVForKeyOfParameterTwo);
        scoringSettingsModel.setMaxRowsForInfluenceParameterTwo(maxRowsForInfluenceParameterTwo);
        scoringSettingsModel.setFactor(factor);
        scoringSettingsModel.setOffset(offset);
        scoringSettingsModel.setNumberWishedRowsForCalcTestModel(numberWishedRowsForCalcTestModel);
        scoringSettingsModel.setModelQualityLevel(String.valueOf(ScoringSettingsQualityLevel.LEVEL_2));
        scoringSettingsModelDao.create(scoringSettingsModel);
        return true;
    }

    @Override
    @Transactional
    public boolean saveTempEmailForChange(UserAccount userAccount){
        UserAccount userAccountTempEmail = userAccountDao.findByUsername(userAccount.getTempEmailDuringChange());
        if(userAccountTempEmail != null){
            return false;
        }
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        userAccountFromDB.setTempEmailDuringChange(userAccount.getTempEmailDuringChange());
        userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        userAccountDao.update(userAccountFromDB);
        return true;
    }

    @Override
    @Transactional
    public boolean updateEmailConfirmedToTrue(Long iduser, String verificationCode) {
        UserAccount userAccountFromDB = userAccountDao.getById(iduser);
        if (userAccountFromDB == null) {
            return false;
        }
        if (userAccountFromDB.isEmailConfirmed()) {
            return true;
        }
        if (!verificationCode.equals(userAccountFromDB.getDynamicCode())) {
            return false;
        }
        userAccountFromDB.setEmailConfirmed(true);
        userAccountFromDB.setDynamicCode(null);
        if (userAccountFromDB.getStatus().equals(String.valueOf(UserStatus.INACTIVE))) {
            userAccountFromDB.setStatus(String.valueOf(UserStatus.ACTIVE));
        }
        userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        userAccountDao.update(userAccountFromDB);
        return true;
    }

    @Override
    @Transactional
    public boolean checkUserPassword(UserAccount userAccount) {
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        if (userAccountFromDB == null) {
            return false;
        }
        if (bCryptPasswordEncoder.matches(userAccount.getPassword(), userAccountFromDB.getPassword())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public UserAccount saveNewPasswordWithoutConfirm(UserAccount userAccount){
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        userAccountFromDB.setPasswordExpiredAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)).plusMonths(COUNT_MONTH_FOR_EXPIRED_PASSWORD));
        userAccountFromDB.setPassword(bCryptPasswordEncoder.encode(userAccount.getNewPassword()));
        userAccountFromDB.setPasswordChangeRequired(false);
        userAccountFromDB.setTempPasswordDuringChange(null);
        userAccountDao.update(userAccountFromDB);
        return userAccountFromDB;
    }

    @Override
    @Transactional
    public boolean saveNewTemporaryPassword(UserAccount userAccount) {
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        if (userAccountFromDB == null) {
            return false;
        }
        userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        userAccountFromDB.setTempPasswordDuringChange(bCryptPasswordEncoder.encode(userAccount.getNewPassword()));
        userAccountDao.update(userAccountFromDB);
        return true;
    }

    @Override
    @Transactional
    public boolean saveNewPasswordFinal(Long iduser, String verificationCode) {
        UserAccount userAccountFromDB = userAccountDao.getById(iduser);
        if (userAccountFromDB == null) {
            return false;
        }
        if (userAccountFromDB.getTempPasswordDuringChange() == null) {
            return false;
        }
        if (!verificationCode.equals(userAccountFromDB.getDynamicCode())) {
            return false;
        }
        userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        userAccountFromDB.setPassword(userAccountFromDB.getTempPasswordDuringChange());
        userAccountFromDB.setPasswordExpiredAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)).plusMonths(COUNT_MONTH_FOR_EXPIRED_PASSWORD));
        userAccountFromDB.setPasswordChangeRequired(false);
        userAccountFromDB.setTempPasswordDuringChange(null);
        userAccountFromDB.setDynamicCode(null);
        userAccountDao.update(userAccountFromDB);
        return true;
    }

    @Override
    @Transactional
    public boolean updateEmailAfterTempChange(Long iduser, String verificationCode) {
        UserAccount userAccountFromDB = userAccountDao.getById(iduser);
        if(userAccountFromDB == null) {
            return false;
        }
        if (userAccountFromDB.getTempEmailDuringChange() == null){
            return false;
        }
        if(!verificationCode.equals(userAccountFromDB.getDynamicCode())){
            return false;
        }
        userAccountFromDB.setDynamicCode(null);
        userAccountFromDB.setUsername(userAccountFromDB.getTempEmailDuringChange());
        userAccountFromDB.setEmail(userAccountFromDB.getTempEmailDuringChange());
        userAccountFromDB.setTempEmailDuringChange(null);
        userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        userAccountDao.update(userAccountFromDB);
        return true;
    }

    @Override
    @Transactional
    public boolean generateNewVerificationCode(UserAccount userAccount) {
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        if (userAccountFromDB == null) {
            return false;
        }
        userAccountFromDB.setDynamicCode(RandomStringUtils.randomAlphanumeric(50, 100));
        userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        userAccountDao.update(userAccountFromDB);
        return true;
    }

    @Override
    @Transactional
    public String generateNewLinkConfirmEmail(UserAccount userAccount) {
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        return MAIN_DOMAIN_URL + "/security/verification/email/" + userAccountFromDB.getId() + "/" +
                userAccountFromDB.getDynamicCode();
    }

    @Override
    @Transactional
    public String generateNewLinkConfirmNewChangedEmail(UserAccount userAccount) {
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        return MAIN_DOMAIN_URL + "/security/verification/new-email/" + userAccountFromDB.getId() + "/" +
                userAccountFromDB.getDynamicCode();
    }

    @Override
    @Transactional
    public String generateNewLinkConfirmPassword(UserAccount userAccount) {
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        return MAIN_DOMAIN_URL + "/security/verification/change-password/" + userAccountFromDB.getId() + "/" +
                userAccountFromDB.getDynamicCode();
    }

    @Override
    @Transactional
    public boolean checkIsUserByUsername(UserAccount userAccount) {
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        if (userAccountFromDB == null) {
            return false;
        }
        if (userAccountFromDB.getStatus().equals(String.valueOf(UserStatus.INACTIVE))) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public String resetPassword(UserAccount userAccount) {
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        String tempPassword = RandomStringUtils.randomAlphanumeric(12,15) + "!";
        userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        userAccountFromDB.setPasswordExpiredAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)).plusMonths(COUNT_MONTH_FOR_EXPIRED_PASSWORD));
        userAccountFromDB.setPasswordChangeRequired(true);
        userAccountFromDB.setPassword(bCryptPasswordEncoder.encode(tempPassword));
        userAccountDao.update(userAccountFromDB);
        return tempPassword;
    }

    @Override
    @Transactional
    public UserAccount changeUserAccountData(UserAccount userAccount){
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        userAccountFromDB.setFirstName(userAccount.getFirstName());
        userAccountFromDB.setLastName(userAccount.getLastName());
        userAccountFromDB.setDateOfBirth(userAccount.getDateOfBirth());
        userAccountFromDB.setGender(userAccount.getGender());
        userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        userAccountDao.update(userAccountFromDB);
        return userAccountFromDB;
    }






    @Override
    @Transactional
    public Integer getCountMaxPossibleSavedModel(UserAccount userAccount){
        return userAccountDao.getCountMaxPossibleSavedModel(userAccount.getUsername());
    }

    @Override
    @Transactional
    public Long getCurrentCountSavedModels(UserAccount userAccount){
        return userAccountDao.getCurrentCountSavedModels(userAccount.getUsername());
    }

    @Override
    @Transactional
    public Long getCurrentCountSavedTests(UserAccount userAccount){
        return userAccountDao.getCurrentCountSavedTests(userAccount.getUsername());
    }

    @Override
    @Transactional
    public Integer getCountMaxPossibleSavedTest(UserAccount userAccount){
        return userAccountDao.getCountMaxPossibleSavedTest(userAccount.getUsername());
    }

    @Override
    @Transactional
    public boolean isProfileFull(UserAccount userAccount){
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        return userAccountFromDB.getGender() == null || userAccountFromDB.getFirstName() == null ||
                userAccountFromDB.getLastName() == null || userAccountFromDB.getDateOfBirth() == null;
    }

    @Override
    @Transactional
    public void updateRoleToLevelOne(UserAccount userAccount){
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        userAccountFromDB.setRole(userRoleDao.findByUserRoleName(String.valueOf(UserRoleEnum.LEVEL_ONE)));
        userAccountFromDB.setSubscriptionExpiredAt(null);
        userAccountDao.update(userAccountFromDB);
    }

    @Override
    @Transactional
    public void renewSubscription(Long roleId, Long userId, String period) {
        if (EnumUtils.isValidEnum(PeriodSubscriptionEnum.class, period)) {
            UserAccount userAccountFromDB = userAccountDao.getById(userId);
            if (userAccountFromDB.getRole().getId().equals(roleId) &&
                    !userAccountFromDB.getRole().getId().equals(userRoleDao.findByUserRoleName(String.valueOf(UserRoleEnum.LEVEL_ONE)).getId())) {
                userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                if(period.equals(String.valueOf(PeriodSubscriptionEnum.PERIOD_ONE_MONTH))){
                    userAccountFromDB.setSubscriptionExpiredAt(userAccountFromDB.getSubscriptionExpiredAt()
                            .plusMonths(1));
                } else if (period.equals(String.valueOf(PeriodSubscriptionEnum.PERIOD_THREE_MONTHS))) {
                    userAccountFromDB.setSubscriptionExpiredAt(userAccountFromDB.getSubscriptionExpiredAt()
                            .plusMonths(3));
                } else if (period.equals(String.valueOf(PeriodSubscriptionEnum.PERIOD_SIX_MONTHS))) {
                    userAccountFromDB.setSubscriptionExpiredAt(userAccountFromDB.getSubscriptionExpiredAt()
                            .plusMonths(6));
                } else if (period.equals(String.valueOf(PeriodSubscriptionEnum.PERIOD_ONE_YEAR))) {
                    userAccountFromDB.setSubscriptionExpiredAt(userAccountFromDB.getSubscriptionExpiredAt()
                            .plusYears(1));
                }
                userAccountDao.update(userAccountFromDB);
            }
        }
    }

    @Override
    @Transactional
    public UserRole buySubscription(Long roleId, Long userId, String period) {
        UserAccount userAccountFromDB = userAccountDao.getById(userId);
        if (EnumUtils.isValidEnum(PeriodSubscriptionEnum.class, period)) {
            if (!userAccountFromDB.getRole().getId().equals(roleId) &&
                    !roleId.equals(userRoleDao.findByUserRoleName(String.valueOf(UserRoleEnum.LEVEL_ONE)).getId())) {
                userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                userAccountFromDB.setRole(userRoleDao.getById(roleId));
                if (period.equals(String.valueOf(PeriodSubscriptionEnum.PERIOD_ONE_MONTH))) {
                    userAccountFromDB.setSubscriptionExpiredAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)).plusMonths(1));
                } else if (period.equals(String.valueOf(PeriodSubscriptionEnum.PERIOD_THREE_MONTHS))) {
                    userAccountFromDB.setSubscriptionExpiredAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)).plusMonths(3));
                } else if (period.equals(String.valueOf(PeriodSubscriptionEnum.PERIOD_SIX_MONTHS))) {
                    userAccountFromDB.setSubscriptionExpiredAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)).plusMonths(6));
                } else if (period.equals(String.valueOf(PeriodSubscriptionEnum.PERIOD_ONE_YEAR))) {
                    userAccountFromDB.setSubscriptionExpiredAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)).plusYears(1));
                }
                userAccountDao.update(userAccountFromDB);
                return userAccountFromDB.getRole();
            }
        }
        return userAccountFromDB.getRole();
    }

    @Override
    @Transactional
    public List<CommonPaymentModel> getAllSuccessPayments(Long userAccountId) {
        return userAccountDao.getAllSuccessPayments(userAccountId);
    }

}
