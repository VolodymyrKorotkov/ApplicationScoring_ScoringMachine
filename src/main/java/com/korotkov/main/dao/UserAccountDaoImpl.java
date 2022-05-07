package com.korotkov.main.dao;

import com.korotkov.main.enums.ScoringModelStatus;
import com.korotkov.main.model.CommonPaymentModel;
import com.korotkov.main.model.UserAccount;
import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserAccountDaoImpl implements UserAccountDao {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(UserAccount userAccount){
        Session session = sessionFactory.getCurrentSession();
        session.persist(userAccount);
    }

    @Override
    public void update(UserAccount userAccount){
        Session session = sessionFactory.getCurrentSession();
        session.update(userAccount);
    }

    @Override
    public UserAccount getById(Long id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(UserAccount.class, id);
    }

    @Override
    public String getPasswordByUsername(String username){
        Session session = sessionFactory.getCurrentSession();
        return (String) session.createQuery("select ua.password from UserAccount as ua where ua.username = :username")
                .setParameter("username",username)
                .uniqueResult();

    }

    @Override
    public UserAccount findByUsername(String username){
        Session session = sessionFactory.getCurrentSession();
        return (UserAccount) session.createQuery("select a from UserAccount a where a.username = :username")
                .setParameter("username",username)
                .uniqueResult();
    }

    @Override
    public Integer getCountMaxPossibleSavedModel(String username){
        Session session = sessionFactory.getCurrentSession();
             return (Integer) session.createQuery("select role.countMaxSavedModels from UserAccount account left join account.role role where account.username = :username")
                    .setParameter("username",username)
                    .uniqueResult();

    }

    @Override
    public Integer getCountMaxPossibleSavedTest(String username){
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createQuery("select role.countMaxSavedTestsForModel from UserAccount ua left join ua.role role where ua.username = :username")
                .setParameter("username", username)
                .uniqueResult();
    }

    @Override
    public Long getCurrentCountSavedTests(String username){
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        Cache cache = sessionFactory.getCache();
        if (cache != null) {
            cache.evictAllRegions();
        }
        return (Long) session.createQuery("select count(tsm.id) from TestScoringModel tsm where tsm.userAccount.username = :username and tsm.scoringModel.status = :activeStatus")
                .setParameter("activeStatus", String.valueOf(ScoringModelStatus.ACTIVE))
                .setParameter("username", username)
                .uniqueResult();
    }

    @Override
    public Long getCurrentCountSavedModels(String username){
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        Cache cache = sessionFactory.getCache();
        if (cache != null) {
            cache.evictAllRegions();
        }
            return (Long) session.createQuery("select count(sm.id) from ScoringModel sm left join sm.userAccount where sm.userAccount.username = :username and sm.status <> :notFinishedStatus")
                    .setParameter("username",username)
                    .setParameter("notFinishedStatus", String.valueOf(ScoringModelStatus.NOT_FINISHED))
                    .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CommonPaymentModel> getAllSuccessPayments(Long userAccountId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select cpm from CommonPaymentModel cpm where cpm.userAccount.id = :userAccountId and cpm.statusResponse = :success and cpm.orderStatus = :approved and cpm.tranType = :purchase order by cpm.createdAt asc")
                .setParameter("userAccountId", userAccountId)
                .setParameter("success", "success")
                .setParameter("approved", "approved")
                .setParameter("purchase", "purchase")
                .list();
    }

}
