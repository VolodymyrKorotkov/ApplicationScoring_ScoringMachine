package com.korotkov.main.api.fondy.service;

import com.korotkov.main.model.CommonPaymentModel;
import com.korotkov.main.model.UserAccount;

public interface PaymentService {
    String createNewPaymentRequestAndRedirect(Long roleId, UserAccount userAccount, String period,
                                              String typePurchase);
    CommonPaymentModel getById(Long id);
    void update(CommonPaymentModel commonPaymentModel);
}
