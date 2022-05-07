package com.korotkov.main.service.scoringModel.testScoringModelResult;

import com.korotkov.main.model.TestScoringModelResult;
import com.korotkov.main.model.UserAccount;

import java.util.List;

public interface TestScoringModelResultService {
    TestScoringModelResult getTestScoringModelResultTotal(UserAccount userAccount, Long testModelId);
    List<TestScoringModelResult> getListTestResultsWithoutTotal(UserAccount userAccount, Long testModelId);
}
