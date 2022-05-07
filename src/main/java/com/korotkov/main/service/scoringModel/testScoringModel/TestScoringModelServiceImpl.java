package com.korotkov.main.service.scoringModel.testScoringModel;

import com.korotkov.main.config.PortalConstants;
import com.korotkov.main.dao.ScoringModelDao;
import com.korotkov.main.dao.TestScoringModelDao;
import com.korotkov.main.dao.TestScoringModelResultDao;
import com.korotkov.main.model.TestScoringModel;
import com.korotkov.main.model.TestScoringModelResult;
import com.korotkov.main.model.UserAccount;
import com.korotkov.main.service.scoringModel.additionalService.additionalEntityForPortal.TestPortal;
import com.korotkov.main.service.scoringModel.additionalService.additionalEntityForPortal.TestResultsPortal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestScoringModelServiceImpl implements TestScoringModelService, PortalConstants {
    ScoringModelDao scoringModelDao;
    TestScoringModelDao testScoringModelDao;
    TestScoringModelResultDao testScoringModelResultDao;

    @Autowired
    public void setScoringModelDao(ScoringModelDao scoringModelDao){
        this.scoringModelDao = scoringModelDao;
    }

    @Autowired
    public void setTestScoringModelDao(TestScoringModelDao testScoringModelDao){
        this.testScoringModelDao = testScoringModelDao;
    }

    @Autowired
    public void setTestScoringModelResultDao(TestScoringModelResultDao testScoringModelResultDao){
        this.testScoringModelResultDao = testScoringModelResultDao;
    }

    @Override
    @Transactional
    public TestScoringModel getById(Long id){
        return testScoringModelDao.getById(id);
    }

    @Override
    @Transactional
    public boolean isPossibleBuildNewTest(UserAccount userAccount){
        return scoringModelDao.isHavingActiveModelUser(userAccount.getUsername());
    }

    @Override
    @Transactional
    public List<TestScoringModel> getAllTestingModels(UserAccount userAccount, int page){
        return testScoringModelDao.getAllTestingModels(userAccount.getUsername(), page);
    }

    @Override
    @Transactional
    public List<TestScoringModel> getTestingModelsWithFilterTitleTestingModel(UserAccount userAccount, int page,
                                                                              String titleTestingModel){
        return testScoringModelDao.getTestingModelsWithFilterTitleTestingModel(userAccount.getUsername(), page,
                titleTestingModel);
    }

    @Override
    @Transactional
    public List<TestScoringModel> getTestingModelsWithFilterTitleScoringModel(UserAccount userAccount, int page,
                                                                              String titleScoringModel){
        return testScoringModelDao.getTestingModelsWithFilterTitleScoringModel(userAccount.getUsername(), page,
                titleScoringModel);
    }

    @Override
    @Transactional
    public List<TestScoringModel> getTestingModelWithFilterTitlesScoringAndTestingModel(UserAccount userAccount, int page,
                                                                                        String titleTestingModel,
                                                                                        String titleScoringModel){
        return testScoringModelDao.getTestingModelWithFilterTitlesScoringAndTestingModel(userAccount.getUsername(), page,
                titleTestingModel, titleScoringModel);
    }

    @Override
    @Transactional
    public Long getCountTestingModelsWithFilter(UserAccount userAccount, String titleTestingModel, String titleScoringModel){
        return testScoringModelDao.getCountTestingModelsWithFilter(userAccount.getUsername(), titleTestingModel,
                titleScoringModel);
    }

    @Override
    @Transactional
    public TestScoringModel getByUserAndTestId(UserAccount userAccount, Long testScoringModelId){
        return testScoringModelDao.getByUsernameAndId(userAccount.getUsername(), testScoringModelId);
    }

    @Override
    @Transactional
    public void updateTitleAndDescriptionForTestScoringModel(UserAccount userAccount, Long testScoringModelId,
                                                             TestScoringModel testScoringModel){
        TestScoringModel testScoringModelFromDB = testScoringModelDao.getByUsernameAndId(userAccount.getUsername(),
                testScoringModelId);
        if(!testScoringModelFromDB.getTitle().equals(testScoringModel.getTitle()) ||
                !testScoringModelFromDB.getDescription().equals(testScoringModel.getDescription())){
            testScoringModelFromDB.setTitle(testScoringModel.getTitle());
            testScoringModelFromDB.setDescription(testScoringModel.getDescription());
            testScoringModelFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            testScoringModelDao.update(testScoringModelFromDB);
        }
    }

    @Override
    @Transactional
    public void deleteTestScoringModel(UserAccount userAccount, Long testScoringModelId){
        testScoringModelDao.delete(testScoringModelDao.getByUsernameAndId(userAccount.getUsername(), testScoringModelId));
    }

    @Override
    @Transactional
    public List<TestPortal> getAllModelsForScoringModelPortal(UserAccount userAccount, Long scoringModelId){
        List<TestScoringModel> testScoringModelList = testScoringModelDao.getAllTestsForScoringModel(userAccount.getUsername(),
                scoringModelId);
        if(testScoringModelList.isEmpty()){
            return new ArrayList<>();
        } else {
            List<TestPortal> testPortalList = new ArrayList<>();
            for (int a = 0; a < testScoringModelList.size(); a++){
                List<TestResultsPortal> testResultsPortalList = new ArrayList<>();
                List<TestScoringModelResult> testScoringModelResultList = testScoringModelResultDao
                        .getListTestResultsByModelIdAndUsername(userAccount.getUsername(),
                                testScoringModelList.get(a).getId());
                for(int b = 0; b < testScoringModelResultList.size(); b++){
                   testResultsPortalList.add(new TestResultsPortal(testScoringModelResultList.get(b).getScore(),
                           testScoringModelResultList.get(b).getCountTotalItems(),
                           testScoringModelResultList.get(b).getCountGoodItems(),
                           testScoringModelResultList.get(b).getCountBadItems(),
                           testScoringModelResultList.get(b).getBadRate(),
                           testScoringModelResultList.get(b).getGiniResult(),
                           testScoringModelResultList.get(b).getOrderNumberRow()));
                }
                TestScoringModelResult testScoringModelResultTotal = testScoringModelResultDao
                        .getByTestModelIdAndUsernameTotalResult(userAccount.getUsername(), testScoringModelList.get(a).getId());
                testPortalList.add(new TestPortal(testScoringModelList.get(a).getId(),
                        testScoringModelList.get(a).getCreatedAt(),
                        testScoringModelList.get(a).getTitle(),
                        testScoringModelResultTotal.getCountTotalItems(),
                        testScoringModelResultTotal.getCountGoodItems(),
                        testScoringModelResultTotal.getCountBadItems(),
                        testScoringModelResultTotal.getBadRate(),
                        testScoringModelResultTotal.getGiniResult(),
                        testResultsPortalList));
            }
            return testPortalList;
        }
    }
}
