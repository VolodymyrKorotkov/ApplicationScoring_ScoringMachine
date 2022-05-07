package com.korotkov.main.dao;


import com.korotkov.main.model.TestScoringModel;

import java.util.List;

public interface TestScoringModelDao {
    void create(TestScoringModel testScoringModel);
    void update(TestScoringModel testScoringModel);
    void delete(TestScoringModel testScoringModel);
    TestScoringModel getById(Long id);
    TestScoringModel findEarliestTestByModel(Long scoringModelId);
    TestScoringModel findLastTestByModel(Long scoringModelId);
    List<TestScoringModel> getAllTestingModels(String username, int page);
    List<TestScoringModel> getTestingModelsWithFilterTitleTestingModel(String username, int page, String titleTestingModel);
    List<TestScoringModel> getTestingModelsWithFilterTitleScoringModel(String username, int page, String titleScoringModel);
    List<TestScoringModel> getTestingModelWithFilterTitlesScoringAndTestingModel(String username, int page,
                                                                                 String titleTestingModel,
                                                                                 String titleScoringModel);
    Long getCountTestingModelsWithFilter(String username, String titleTestingModel, String titleScoringModel);
    TestScoringModel getByUsernameAndId(String username, Long testScoringModelId);
    List<TestScoringModel> getAllTestsForScoringModel(String username, Long scoringModelId);
}
