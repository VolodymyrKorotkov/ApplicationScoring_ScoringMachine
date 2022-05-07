package com.korotkov.main.api.fondy.dao;

import com.korotkov.main.model.CommonPaymentModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommonPaymentDaoImpl implements CommonPaymentDao{
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(CommonPaymentModel commonPaymentModel) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(commonPaymentModel);
    }

    @Override
    public void update(CommonPaymentModel commonPaymentModel) {
        Session session = sessionFactory.getCurrentSession();
        session.update(commonPaymentModel);
    }

    @Override
    public void delete(CommonPaymentModel commonPaymentModel) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(commonPaymentModel);
    }

    @Override
    public CommonPaymentModel getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(CommonPaymentModel.class,id);
    }

    @Override
    public CommonPaymentModel findLastCreatedPaymentByUser(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        return (CommonPaymentModel) session.createQuery("select cpm from CommonPaymentModel cpm where cpm.userAccount.id = :userId order by cpm.createdAt desc")
                .setMaxResults(1)
                .setParameter("userId", userId)
                .uniqueResult();
    }
}
