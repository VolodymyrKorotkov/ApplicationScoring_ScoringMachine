package com.korotkov.main.service.scoringModel.scoringCore;

import com.korotkov.main.model.ScoringModel;
import com.korotkov.main.model.ScoringModelParameter;
import com.korotkov.main.model.UserAccount;

import java.util.ArrayList;
import java.util.List;

public interface ScoringCalculationService {
    void createScoringModel(ScoringModel scoringModel);
    void updateScoringModel(ScoringModel scoringModel);
    void deleteScoringModel(ScoringModel scoringModel);
    ScoringModel getByIdScoringModel(Long id);
    ScoringModel findEarliestModelByUser(UserAccount userAccount);
    ScoringModel findActiveModelByUser(UserAccount userAccount);
    void createScoringModelParameter(ScoringModelParameter scoringModelParameter);
    void updateScoringModelParameter(ScoringModelParameter scoringModelParameter);
    void deleteScoringModelParameter(ScoringModelParameter scoringModelParameter);
    ScoringModelParameter getByIdScoringModelParameter(Long id);
    List<ScoringModelParameter> findAllParametersByModel(ScoringModel scoringModel);
    void deleteAllParametersByModel(ScoringModel scoringModel);
    void createNewScoringModel(ArrayList<ArrayList<String>> listFromFileExcel, UserAccount userAccount);
}
