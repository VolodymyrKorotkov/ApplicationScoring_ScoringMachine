package com.korotkov.main.api.fondy.dao;

import com.korotkov.main.model.CommonPaymentModel;

public interface CommonPaymentDao {
    void create(CommonPaymentModel commonPaymentModel);
    void update(CommonPaymentModel commonPaymentModel);
    void delete(CommonPaymentModel commonPaymentModel);
    CommonPaymentModel getById(Long id);
    CommonPaymentModel findLastCreatedPaymentByUser(Long userId);
}
