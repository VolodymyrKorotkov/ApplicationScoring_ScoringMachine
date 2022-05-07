package com.korotkov.main.dao;

import com.korotkov.main.model.ScoringSettingsModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ScoringSettingsModelDaoImpl implements ScoringSettingsModelDao{
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(ScoringSettingsModel scoringSettingsModel){
        Session session = sessionFactory.getCurrentSession();
        session.persist(scoringSettingsModel);
    }

    @Override
    public void update(ScoringSettingsModel scoringSettingsModel){
        Session session = sessionFactory.getCurrentSession();
        session.update(scoringSettingsModel);
    }

    @Override
    public ScoringSettingsModel getById(Long id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(ScoringSettingsModel.class, id);
    }

    @Override
    public ScoringSettingsModel findByUser(String username){
        Session session = sessionFactory.getCurrentSession();
        return (ScoringSettingsModel) session.createQuery("select ssm from ScoringSettingsModel ssm where ssm.userAccount.username = :username")
                .setParameter("username",username)
                .uniqueResult();
    }

}
