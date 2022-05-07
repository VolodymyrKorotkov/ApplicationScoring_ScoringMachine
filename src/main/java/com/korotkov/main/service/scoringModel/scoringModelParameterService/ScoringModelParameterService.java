package com.korotkov.main.service.scoringModel.scoringModelParameterService;

import com.korotkov.main.model.ScoringModelParameter;
import com.korotkov.main.model.UserAccount;

public interface ScoringModelParameterService {
    ScoringModelParameter getModelAttributeValue(UserAccount userAccount, Long modelId, Long idInDataBase);
    void setChangedScoreInAttributeValue(UserAccount userAccount, Long modelId, Long idInDataBase,
                                         ScoringModelParameter scoringModelParameter);
}
