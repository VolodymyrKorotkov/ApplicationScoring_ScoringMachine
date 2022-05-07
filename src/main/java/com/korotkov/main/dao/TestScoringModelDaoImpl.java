package com.korotkov.main.dao;

import com.korotkov.main.model.TestScoringModel;
import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestScoringModelDaoImpl implements TestScoringModelDao{
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(TestScoringModel testScoringModel){
        Session session = sessionFactory.getCurrentSession();
        session.persist(testScoringModel);
    }

    @Override
    public void update(TestScoringModel testScoringModel){
        Session session = sessionFactory.getCurrentSession();
        session.update(testScoringModel);
    }

    @Override
    public void delete(TestScoringModel testScoringModel){
        Session session = sessionFactory.getCurrentSession();
        session.delete(testScoringModel);
    }

    @Override
    public TestScoringModel getById(Long id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(TestScoringModel.class,id);
    }

    @Override
    public TestScoringModel findEarliestTestByModel(Long scoringModelId){
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        Cache cache = sessionFactory.getCache();
        if (cache != null) {
            cache.evictAllRegions();
        }
        return (TestScoringModel) session.createQuery("select tsm from TestScoringModel tsm where tsm.scoringModel.id = :scoringModelId order by tsm.createdAt asc")
                .setMaxResults(1)
                .setParameter("scoringModelId", scoringModelId)
                .uniqueResult();
    }

    @Override
    public TestScoringModel findLastTestByModel(Long scoringModelId){
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        Cache cache = sessionFactory.getCache();
        if (cache != null) {
            cache.evictAllRegions();
        }
        return (TestScoringModel) session.createQuery("select tsm from TestScoringModel tsm where tsm.scoringModel.id = :scoringModelId order by tsm.createdAt desc")
                .setMaxResults(1)
                .setParameter("scoringModelId", scoringModelId)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TestScoringModel> getAllTestingModels(String username, int page){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select tsm from TestScoringModel tsm where tsm.userAccount.username = :username order by tsm.lastModifiedAt desc")
                .setParameter("username", username)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TestScoringModel> getTestingModelsWithFilterTitleTestingModel(String username, int page, String titleTestingModel){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select tsm from TestScoringModel tsm where tsm.userAccount.username = :username and tsm.title like :titleTestingModel order by tsm.lastModifiedAt desc")
                .setParameter("username", username)
                .setParameter("titleTestingModel", "%" + titleTestingModel + "%")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TestScoringModel> getTestingModelsWithFilterTitleScoringModel(String username, int page, String titleScoringModel){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select tsm from TestScoringModel tsm where tsm.userAccount.username = :username and tsm.scoringModel.title like :titleScoringModel order by tsm.lastModifiedAt desc")
                .setParameter("username", username)
                .setParameter("titleScoringModel", "%" + titleScoringModel + "%")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TestScoringModel> getTestingModelWithFilterTitlesScoringAndTestingModel(String username, int page,
                                                                                        String titleTestingModel,
                                                                                        String titleScoringModel){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select tsm from TestScoringModel tsm where tsm.userAccount.username = :username and tsm.title like :titleTestingModel and tsm.scoringModel.title like :titleScoringModel order by tsm.lastModifiedAt desc")
                .setParameter("username", username)
                .setParameter("titleTestingModel", "%" + titleTestingModel + "%")
                .setParameter("titleScoringModel", "%" + titleScoringModel + "%")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountTestingModelsWithFilter(String username, String titleTestingModel, String titleScoringModel){
        Session session = sessionFactory.getCurrentSession();
        if(!titleTestingModel.equals("") && !titleScoringModel.equals("")){
            return (Long) session.createQuery("select count(tsm.id) from TestScoringModel tsm where tsm.userAccount.username = :username and tsm.title like :titleTestingModel and tsm.scoringModel.title like :titleScoringModel")
                    .setParameter("username", username)
                    .setParameter("titleTestingModel", "%" + titleTestingModel + "%")
                    .setParameter("titleScoringModel", "%" + titleScoringModel + "%")
                    .uniqueResult();
        } else if (!titleTestingModel.equals("")){
            return (Long) session.createQuery("select count(tsm.id) from TestScoringModel tsm where tsm.userAccount.username = :username and tsm.title like :titleTestingModel")
                    .setParameter("username", username)
                    .setParameter("titleTestingModel", "%" + titleTestingModel + "%")
                    .uniqueResult();
        } else if (!titleScoringModel.equals("")){
            return (Long) session.createQuery("select count(tsm.id) from TestScoringModel tsm where tsm.userAccount.username = :username and tsm.scoringModel.title like :titleScoringModel")
                    .setParameter("username", username)
                    .setParameter("titleScoringModel", "%" + titleScoringModel + "%")
                    .uniqueResult();
        } else {
            return (Long) session.createQuery("select count(tsm.id) from TestScoringModel tsm where tsm.userAccount.username = :username")
                    .setParameter("username", username)
                    .uniqueResult();
        }
    }

    @Override
    public TestScoringModel getByUsernameAndId(String username, Long testScoringModelId){
        Session session = sessionFactory.getCurrentSession();
        return (TestScoringModel) session.createQuery("select tsm from TestScoringModel tsm where tsm.userAccount.username = :username and tsm.id = :testScoringModelId")
                .setParameter("username", username)
                .setParameter("testScoringModelId", testScoringModelId)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TestScoringModel> getAllTestsForScoringModel(String username, Long scoringModelId){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select tsm from TestScoringModel tsm where tsm.userAccount.username = :username and tsm.scoringModel.id = :scoringModelId")
                .setParameter("username", username)
                .setParameter("scoringModelId", scoringModelId)
                .list();
    }
}
