package com.korotkov.main.service.scoringModel.testScoringModelResult;

import com.korotkov.main.dao.TestScoringModelResultDao;
import com.korotkov.main.model.TestScoringModelResult;
import com.korotkov.main.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TestScoringModelResultServiceImpl implements TestScoringModelResultService{
    TestScoringModelResultDao testScoringModelResultDao;

    @Autowired
    public void setTestScoringModelResultDao(TestScoringModelResultDao testScoringModelResultDao){
        this.testScoringModelResultDao = testScoringModelResultDao;
    }

    @Override
    @Transactional
    public TestScoringModelResult getTestScoringModelResultTotal(UserAccount userAccount, Long testModelId){
        return testScoringModelResultDao.getByTestModelIdAndUsernameTotalResult(userAccount.getUsername(), testModelId);
    }

    @Override
    @Transactional
    public List<TestScoringModelResult> getListTestResultsWithoutTotal(UserAccount userAccount, Long testModelId){
        return testScoringModelResultDao.getListTestResultsByModelIdAndUsername(userAccount.getUsername(), testModelId);
    }
}
