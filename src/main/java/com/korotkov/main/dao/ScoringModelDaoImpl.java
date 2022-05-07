package com.korotkov.main.dao;

import com.korotkov.main.enums.ScoringModelStatus;
import com.korotkov.main.model.ScoringModel;
import com.korotkov.main.model.ScoringModelParameter;
import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ScoringModelDaoImpl implements ScoringModelDao{
    private SessionFactory sessionFactory;

    @Autowired
    private void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(ScoringModel scoringModel){
        Session session = sessionFactory.getCurrentSession();
        session.persist(scoringModel);
    }

    @Override
    public void update(ScoringModel scoringModel){
        Session session = sessionFactory.getCurrentSession();
        session.update(scoringModel);
    }

    @Override
    public void delete(ScoringModel scoringModel){
        Session session = sessionFactory.getCurrentSession();
        session.delete(scoringModel);
    }

    @Override
    public ScoringModel getById(Long id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(ScoringModel.class,id);
    }

    @Override
    public ScoringModel findEarliestModelByUser(String username){
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        Cache cache = sessionFactory.getCache();
        if (cache != null) {
            cache.evictAllRegions();
        }
        return (ScoringModel) session.createQuery("select sm from ScoringModel sm where sm.userAccount.username = :username order by sm.createdAt asc")
                .setMaxResults(1)
                .setParameter("username",username)
                .uniqueResult();
    }

    @Override
    public ScoringModel findLastCreatedModelByUser(String username){
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        Cache cache = sessionFactory.getCache();
        if (cache != null) {
            cache.evictAllRegions();
        }
        return (ScoringModel) session.createQuery("select sm from ScoringModel sm where sm.userAccount.username = :username order by sm.createdAt desc")
                .setMaxResults(1)
                .setParameter("username",username)
                .uniqueResult();
    }

    @Override
    public ScoringModel findActiveModelByUser(String username){
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        Cache cache = sessionFactory.getCache();
        if (cache != null) {
            cache.evictAllRegions();
        }
        return (ScoringModel) session.createQuery("select sm from ScoringModel sm where sm.userAccount.username = :username and sm.status = :activeStatus")
                .setParameter("username",username)
                .setParameter("activeStatus", String.valueOf(ScoringModelStatus.ACTIVE))
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScoringModel> findModelsWithStatus(String username, String status){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select sm from ScoringModel sm where sm.userAccount.username = :username and sm.status = :status")
                .setParameter("username", username)
                .setParameter("status", status)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScoringModel> getAllModels(String username, int page){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select sm from ScoringModel sm where sm.userAccount.username = :username and sm.status <> :notFinishedStatus order by sm.lastModifiedAt desc")
                .setParameter("username", username)
                .setParameter("notFinishedStatus", String.valueOf(ScoringModelStatus.NOT_FINISHED))
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScoringModel> getModelsWithFilter(String username, int page, String scoringModelTitle,
                                                  String scoringModelStatus){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select sm from ScoringModel sm where sm.userAccount.username = :username and sm.status = :scoringModelStatus and sm.title like :modelTitle order by sm.lastModifiedAt desc")
                .setParameter("username", username)
                .setParameter("scoringModelStatus", scoringModelStatus)
                .setParameter("modelTitle","%" + scoringModelTitle + "%")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScoringModel> getModelsWithFilter(String username, int page, String scoringModelTitle){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select sm from ScoringModel sm where sm.userAccount.username = :username and sm.status <> :notFinishedStatus and sm.title like :modelTitle order by sm.lastModifiedAt desc")
                .setParameter("username", username)
                .setParameter("notFinishedStatus", String.valueOf(ScoringModelStatus.NOT_FINISHED))
                .setParameter("modelTitle","%" + scoringModelTitle + "%")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScoringModel> getModelsWithFilter(String username, String scoringModelStatus, int page){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select sm from ScoringModel sm where sm.userAccount.username = :username and sm.status = :scoringModelStatus order by sm.lastModifiedAt desc")
                .setParameter("username", username)
                .setParameter("scoringModelStatus", scoringModelStatus)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountModelsWithFilter(String username, String scoringModelTitle, String scoringModelStatus){
        Session session = sessionFactory.getCurrentSession();
        if((!scoringModelTitle.equals("") && !scoringModelTitle.equals("NaN")) && !scoringModelStatus.equals("NaN")){
            return (Long) session.createQuery("select count(sm.id) from ScoringModel sm left join sm.userAccount where sm.userAccount.username = :username and sm.status = :statusModel and sm.title like :titleModel")
                    .setParameter("username",username)
                    .setParameter("statusModel", scoringModelStatus)
                    .setParameter("titleModel","%" + scoringModelTitle + "%")
                    .uniqueResult();
        } else if(!scoringModelTitle.equals("") && !scoringModelTitle.equals("NaN")){
            return (Long) session.createQuery("select count(sm.id) from ScoringModel sm left join sm.userAccount where sm.userAccount.username = :username and sm.status <> :notFinishedStatus and sm.title like :titleModel")
                    .setParameter("username",username)
                    .setParameter("notFinishedStatus", String.valueOf(ScoringModelStatus.NOT_FINISHED))
                    .setParameter("titleModel","%" + scoringModelTitle + "%")
                    .uniqueResult();
        } else if(!scoringModelStatus.equals("NaN")){
            return (Long) session.createQuery("select count(sm.id) from ScoringModel sm left join sm.userAccount where sm.userAccount.username = :username and sm.status = :statusModel")
                    .setParameter("username",username)
                    .setParameter("statusModel", scoringModelStatus)
                    .uniqueResult();
        } else {
            return (Long) session.createQuery("select count(sm.id) from ScoringModel sm left join sm.userAccount where sm.userAccount.username = :username and sm.status <> :notFinishedStatus")
                    .setParameter("username",username)
                    .setParameter("notFinishedStatus", String.valueOf(ScoringModelStatus.NOT_FINISHED))
                    .uniqueResult();
        }
    }

    @Override
    public ScoringModel getScoringModelByIdAndUsername(String username, Long modelId){
        Session session = sessionFactory.getCurrentSession();
        return (ScoringModel) session.createQuery("select sm from ScoringModel sm where sm.userAccount.username = :username and sm.id = :modelId")
                .setParameter("username", username)
                .setParameter("modelId", modelId)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScoringModelParameter> getScoringModelRecommendedParametersList(String username, Long modelId,
                                                                                String scoringParameterType){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select smp from ScoringModelParameter smp where smp.scoringModel.userAccount.username = :username and smp.scoringModel.id = :modelId and smp.recommended = true and smp.typeParameter = :scoringParameterType")
                .setParameter("username", username)
                .setParameter("modelId", modelId)
                .setParameter("scoringParameterType", scoringParameterType)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScoringModelParameter> getScoringModelAllParametersTotalList(String username, Long modelId,
                                                                        String scoringParameterType){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select smp from ScoringModelParameter smp where smp.scoringModel.userAccount.username = :username and smp.scoringModel.id = :modelId and smp.typeParameter = :scoringParameterType and smp.total = :booleanTotal")
                .setParameter("username", username)
                .setParameter("modelId", modelId)
                .setParameter("scoringParameterType", scoringParameterType)
                .setParameter("booleanTotal", true)
                .list();
    }


    @Override
    public boolean isHavingActiveModelUser(String username){
        Session session = sessionFactory.getCurrentSession();
        return !session.createQuery("select sm from ScoringModel sm where sm.userAccount.username = :username and sm.status = 'ACTIVE'")
                .setParameter("username", username)
                .list().isEmpty();
    }

}
