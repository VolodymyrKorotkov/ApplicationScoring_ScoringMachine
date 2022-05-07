package com.korotkov.main.service.scoringModel.testScoringModel;

import com.korotkov.main.model.TestScoringModel;
import com.korotkov.main.model.UserAccount;
import com.korotkov.main.service.scoringModel.additionalService.additionalEntityForPortal.TestPortal;

import java.util.List;

public interface TestScoringModelService {
    TestScoringModel getById(Long id);
    boolean isPossibleBuildNewTest(UserAccount userAccount);
    List<TestScoringModel> getAllTestingModels(UserAccount userAccount, int page);
    List<TestScoringModel> getTestingModelsWithFilterTitleTestingModel(UserAccount userAccount, int page,
                                                                       String titleTestingModel);
    List<TestScoringModel> getTestingModelsWithFilterTitleScoringModel(UserAccount userAccount, int page,
                                                                       String titleScoringModel);
    List<TestScoringModel> getTestingModelWithFilterTitlesScoringAndTestingModel(UserAccount userAccount, int page,
                                                                                 String titleTestingModel,
                                                                                 String titleScoringModel);
    Long getCountTestingModelsWithFilter(UserAccount userAccount, String titleTestingModel, String titleScoringModel);
    TestScoringModel getByUserAndTestId(UserAccount userAccount, Long testScoringModelId);
    void updateTitleAndDescriptionForTestScoringModel(UserAccount userAccount, Long testScoringModelId,
                                                      TestScoringModel testScoringModel);
    void deleteTestScoringModel(UserAccount userAccount, Long testScoringModelId);
    List<TestPortal> getAllModelsForScoringModelPortal(UserAccount userAccount, Long scoringModelId);
}
