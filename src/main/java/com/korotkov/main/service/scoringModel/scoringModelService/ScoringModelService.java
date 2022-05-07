package com.korotkov.main.service.scoringModel.scoringModelService;

import com.korotkov.main.model.ScoringModel;
import com.korotkov.main.model.ScoringModelParameter;
import com.korotkov.main.model.UserAccount;
import com.korotkov.main.service.scoringModel.additionalService.additionalEntityForPortal.ScoringParameterPortalCommon;

import java.util.List;

public interface ScoringModelService {
    List<ScoringModel> getAllModels(UserAccount userAccount, int page);
    List<ScoringModel> getModelsWithFilter(UserAccount userAccount, int page, String scoringModelTitle,
                                           String scoringModelStatus);
    List<ScoringModel> getModelsWithFilter(UserAccount userAccount, int page, String scoringModelTitle);
    List<ScoringModel> getModelsWithFilter(UserAccount userAccount, String scoringModelStatus, int page);
    Long getCountModelsWithFilter(UserAccount userAccount, String scoringModelTitle, String scoringModelStatus);
    ScoringModel getScoringModelByIdAndUser(UserAccount userAccount, Long modelId);
    List<ScoringParameterPortalCommon> getScoringModelRecommendedParametersList(UserAccount userAccount, Long modelId,
                                                                                String scoringParameterType);
    List<ScoringModelParameter> getScoringModelAllParametersTotalList(UserAccount userAccount, Long modelId,
                                                                 String scoringParameterType);
    void updateTitleAndDescriptionInScoringModel(UserAccount userAccount, Long modelId, ScoringModel scoringModel);
    void deleteScoringModel(UserAccount userAccount, Long modelId);
    void activateScoringModel(UserAccount userAccount, Long modelId);
    void deactivateScoringModel(UserAccount userAccount, Long modelId);
    ScoringModel findActiveModelByUser(UserAccount userAccount);
}
