package com.korotkov.main.dao;

import com.korotkov.main.model.ScoringModelParameter;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ScoringModelParameterDaoImpl implements ScoringModelParameterDao{
    SessionFactory sessionFactory;

    @Autowired
    private void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(ScoringModelParameter scoringModelParameter){
        Session session = sessionFactory.getCurrentSession();
        session.persist(scoringModelParameter);
    }

    @Override
    public void update(ScoringModelParameter scoringModelParameter){
        Session session = sessionFactory.getCurrentSession();
        session.update(scoringModelParameter);
    }

    @Override
    public void delete(ScoringModelParameter scoringModelParameter){
        Session session = sessionFactory.getCurrentSession();
        session.delete(scoringModelParameter);
    }

    @Override
    public ScoringModelParameter getById(Long id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(ScoringModelParameter.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScoringModelParameter> findAllByModel(Long scoringModelId){
        Session session = sessionFactory.getCurrentSession();
        return (List<ScoringModelParameter>) session.createQuery("select smp from ScoringModelParameter smp where smp.scoringModel.id = :scoringModelId")
                .setParameter("scoringModelId",scoringModelId);
    }

    @Override
    public void deleteAllByModel(Long scoringModelId){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from ScoringModelParameter where scoringModel.id =:scoringModelId")
                .setParameter("scoringModelId", scoringModelId);
    }

    @Override
    public boolean isScoringParameterWithPartOfTitle(Long scoringModelId, String partOfTitle){
        Session session = sessionFactory.getCurrentSession();
        return !session.createQuery("select smp from ScoringModelParameter smp where smp.scoringModel.id = :scoringModelId and smp.nameParameter like :partOfTitle and smp.total <> :booleanTotal")
                .setParameter("scoringModelId",scoringModelId)
                .setParameter("partOfTitle","%" + partOfTitle + "%")
                .setParameter("booleanTotal", true)
                .list().isEmpty();
    }

    @Override
    public boolean isAsScoringAttributeWithWholeName(Long scoringModelId, String attributeName){
        Session session = sessionFactory.getCurrentSession();
        return !session.createQuery("select smp from ScoringModelParameter smp where smp.scoringModel.id = :scoringModelId and smp.nameParameter = :attributeName and smp.total <> :booleanTotal and smp.recommended = :booleanRecommend")
                .setParameter("scoringModelId", scoringModelId)
                .setParameter("attributeName", attributeName)
                .setParameter("booleanTotal", true)
                .setParameter("booleanRecommend", true)
                .list().isEmpty();
    }

    @Override
    public ScoringModelParameter getModelAttributeValue(String username, Long modelId, Long idInDataBase){
        Session session = sessionFactory.getCurrentSession();
        return (ScoringModelParameter) session.createQuery("select smp from ScoringModelParameter smp where smp.scoringModel.userAccount.username = :username and smp.scoringModel.id = :modelId and smp.id = :idInDataBase")
                .setParameter("username", username)
                .setParameter("modelId", modelId)
                .setParameter("idInDataBase", idInDataBase)
                .uniqueResult();
    }

    @Override
    public Integer getScoreForAttributeValue(Long scoringModelId, String attributeName, String attributeValue){
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createQuery("select smp.score from ScoringModelParameter smp where smp.total <> :booleanTotal and smp.scoringModel.id = :scoringModelId and smp.nameParameter = :attributeName and smp.title = :attributeValue")
                .setParameter("booleanTotal", true)
                .setParameter("scoringModelId", scoringModelId)
                .setParameter("attributeName", attributeName)
                .setParameter("attributeValue", attributeValue)
                .uniqueResult();
    }

}
