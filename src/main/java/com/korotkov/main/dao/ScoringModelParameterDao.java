package com.korotkov.main.dao;

import com.korotkov.main.model.ScoringModel;
import com.korotkov.main.model.ScoringModelParameter;
import com.korotkov.main.model.UserAccount;

import java.util.List;

public interface ScoringModelParameterDao {
    void create(ScoringModelParameter scoringModelParameter);
    void update(ScoringModelParameter scoringModelParameter);
    void delete(ScoringModelParameter scoringModelParameter);
    ScoringModelParameter getById(Long id);
    List<ScoringModelParameter> findAllByModel(Long scoringModelId);
    void deleteAllByModel(Long scoringModelId);
    boolean isScoringParameterWithPartOfTitle(Long scoringModelId, String partOfTitle);
    ScoringModelParameter getModelAttributeValue(String username, Long modelId, Long idInDataBase);
    boolean isAsScoringAttributeWithWholeName(Long scoringModelId, String attributeName);
    Integer getScoreForAttributeValue(Long scoringModelId, String attributeName, String attributeValue);
}
