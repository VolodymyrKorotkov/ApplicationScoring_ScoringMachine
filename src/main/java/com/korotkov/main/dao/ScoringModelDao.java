package com.korotkov.main.dao;

import com.korotkov.main.model.ScoringModel;
import com.korotkov.main.model.ScoringModelParameter;

import java.util.List;

public interface ScoringModelDao {
    void create(ScoringModel scoringModel);
    void update(ScoringModel scoringModel);
    void delete(ScoringModel scoringModel);
    ScoringModel getById(Long id);
    ScoringModel findEarliestModelByUser(String username);
    ScoringModel findActiveModelByUser(String username);
    ScoringModel findLastCreatedModelByUser(String username);
    List<ScoringModel> getAllModels(String username, int page);
    List<ScoringModel> findModelsWithStatus(String username, String status);
    List<ScoringModel> getModelsWithFilter(String username, int page, String scoringModelTitle,
                                           String scoringModelStatus);
    List<ScoringModel> getModelsWithFilter(String username, int page, String scoringModelTitle);
    List<ScoringModel> getModelsWithFilter(String username, String scoringModelStatus, int page);
    Long getCountModelsWithFilter(String username, String scoringModelTitle, String scoringModelStatus);
    ScoringModel getScoringModelByIdAndUsername(String username, Long modelId);
    List<ScoringModelParameter> getScoringModelRecommendedParametersList(String username, Long modelId,
                                                                         String scoringParameterType);
    List<ScoringModelParameter> getScoringModelAllParametersTotalList(String username, Long modelId,
                                                                 String scoringParameterType);
    boolean isHavingActiveModelUser(String username);
}
