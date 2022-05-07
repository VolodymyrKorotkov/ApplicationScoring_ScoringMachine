package com.korotkov.main.dao;

import com.korotkov.main.model.TestScoringModelResult;

import java.util.List;

public interface TestScoringModelResultDao {
    void create(TestScoringModelResult testScoringModelResult);
    void update(TestScoringModelResult testScoringModelResult);
    void delete(TestScoringModelResult testScoringModelResult);
    TestScoringModelResult getById(Long id);
    TestScoringModelResult getByModelAndOrderRowNumber(Long idTestModel, Integer orderRowNumber);
    TestScoringModelResult getByTestModelIdAndUsernameTotalResult(String username, Long testModelId);
    List<TestScoringModelResult> getListTestResultsByModelIdAndUsername(String username, Long testModelId);
}
