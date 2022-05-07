package com.korotkov.main.dao;

import com.korotkov.main.model.CommonPaymentModel;
import com.korotkov.main.model.UserAccount;

import java.util.List;

public interface UserAccountDao {
    void create(UserAccount userAccount);
    void update(UserAccount userAccount);
    UserAccount getById(Long id);
    String getPasswordByUsername(String username);
    UserAccount findByUsername(String username);
    Integer getCountMaxPossibleSavedModel(String username);
    Long getCurrentCountSavedModels(String username);
    Integer getCountMaxPossibleSavedTest(String username);
    Long getCurrentCountSavedTests(String username);
    List<CommonPaymentModel> getAllSuccessPayments(Long userAccountId);
}
