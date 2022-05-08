package com.korotkov.main.service.scoringModel.testingCore;

import com.korotkov.main.config.PortalConstants;
import com.korotkov.main.dao.*;
import com.korotkov.main.model.*;
import com.korotkov.main.service.email.EmailService;
import com.korotkov.main.service.scoringModel.additionalService.ScoringDefaultSettingsLevelTwoMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class TestingCalculationServiceImpl implements TestingCalculationService, PortalConstants, ScoringDefaultSettingsLevelTwoMain {
    private static final Logger logger = LoggerFactory.getLogger(TestingCalculationServiceImpl.class);

    UserAccountDao userAccountDao;
    TestScoringModelDao testScoringModelDao;
    TestScoringModelResultDao testScoringModelResultDao;
    ScoringModelDao scoringModelDao;
    ScoringModelParameterDao scoringModelParameterDao;
    ScoringSettingsModelDao scoringSettingsModelDao;
    EmailService emailService;

    @Autowired
    public void setUserAccountDao(UserAccountDao userAccountDao){
        this.userAccountDao = userAccountDao;
    }

    @Autowired
    public void setTestScoringModelDao(TestScoringModelDao testScoringModelDao){
        this.testScoringModelDao = testScoringModelDao;
    }

    @Autowired
    public void setScoringModelDao(ScoringModelDao scoringModelDao){
        this.scoringModelDao = scoringModelDao;
    }

    @Autowired
    public void setScoringModelParameterDao(ScoringModelParameterDao scoringModelParameterDao){
        this.scoringModelParameterDao = scoringModelParameterDao;
    }

    @Autowired
    public void setScoringSettingsModelDao(ScoringSettingsModelDao scoringSettingsModelDao){
        this.scoringSettingsModelDao = scoringSettingsModelDao;
    }

    @Autowired
    public void setTestScoringModelResultDao(TestScoringModelResultDao testScoringModelResultDao){
        this.testScoringModelResultDao = testScoringModelResultDao;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }



    @Override
    @Transactional
    @Async("scoringExecutor")
    public void createNewTest(ArrayList<ArrayList<String>> listFromFileExcel, UserAccount userAccount){
        synchronized (userAccount.getUsername()) {
            Map<String,Object> emailModel = new HashMap<>();
            emailModel.put("from", EMAIL_FROM);
            emailModel.put("mainUrl",MAIN_DOMAIN_URL);
            emailModel.put("to", userAccount.getUsername());
            try {
                ScoringModel scoringModelActiveForTest = scoringModelDao.findActiveModelByUser(userAccount.getUsername());
                Long currentCountSavedTests = userAccountDao.getCurrentCountSavedTests(userAccount.getUsername());
                Long maxPossibleCountTests = Long.valueOf(userAccountDao.getCountMaxPossibleSavedTest(userAccount.getUsername()));
                if(currentCountSavedTests.compareTo(maxPossibleCountTests) >= 0){
                    long count = currentCountSavedTests - maxPossibleCountTests + 1;
                    for (int a = 0; a < count; a++) {
                        TestScoringModel testScoringModelForDelete =
                                testScoringModelDao.findEarliestTestByModel(scoringModelActiveForTest.getId());
                        testScoringModelDao.delete(testScoringModelForDelete);
                    }
                }
                TestScoringModel testScoringModelForSave = new TestScoringModel();
                testScoringModelForSave.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                testScoringModelForSave.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                testScoringModelForSave.setUserAccount(userAccountDao.findByUsername(userAccount.getUsername()));
                testScoringModelForSave.setScoringModel(scoringModelActiveForTest);
                testScoringModelForSave.setTitle("New Test of Scoring Model");
                testScoringModelDao.create(testScoringModelForSave);
                TestScoringModel testScoringModelSaved = testScoringModelDao.findLastTestByModel(scoringModelActiveForTest.getId());
                testScoringModelSaved.setGiniIndex(calculateAndSaveToDBTestingRowsReturnTotalGini(listFromFileExcel,
                        testScoringModelSaved.getScoringModel().getId(), userAccount.getUsername(), testScoringModelSaved));
                testScoringModelSaved.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                testScoringModelDao.update(testScoringModelSaved);
                emailModel.put("subject", "New Test was created");
                emailModel.put("linkCreated", MAIN_DOMAIN_URL + "/testing-model/test-scoring-model/" +
                        testScoringModelSaved.getId() + "?action=created-new");
                emailService.sendEmail("newTestWasCreated.vm", emailModel);
            }
            catch (Exception e) {
                logger.error("Error during building scoring model (client id: " + userAccount.getId() + "): " + e.getMessage());
                emailModel.put("subject", "New Test was NOT created");
                emailModel.put("linkNotCreated", MAIN_DOMAIN_URL + "/testing-model/creating-new-test?action=fileException");
                emailService.sendEmail("newTestWasNotCreated.vm", emailModel);
            }
        }
    }


    private double calculateAndSaveToDBTestingRowsReturnTotalGini(ArrayList<ArrayList<String>> listFromFileExcel, Long scoringModelId, String username, TestScoringModel testScoringModelSaved ){
        int[] scoreArray = new int[listFromFileExcel.size()];
        for(int a = 1; a < listFromFileExcel.get(0).size(); a++){
            if(scoringModelParameterDao.isAsScoringAttributeWithWholeName(scoringModelId,
                    listFromFileExcel.get(0).get(a))){
                for(int b = 1; b < listFromFileExcel.size(); b++){
                    Integer tempScoreForArray = scoringModelParameterDao.getScoreForAttributeValue(scoringModelId,
                            listFromFileExcel.get(0).get(a),listFromFileExcel.get(b).get(a));
                    if(tempScoreForArray != null && !tempScoreForArray.equals(0)){
                        scoreArray[b] += tempScoreForArray;
                    }
                }
            }
        }
        for(int a = 1; a < listFromFileExcel.get(0).size(); a++){
            for(int b = 2; b < listFromFileExcel.get(0).size() - 1; b++){
                if(!listFromFileExcel.get(0).get(a).equals(listFromFileExcel.get(0).get(b))){
                    if(scoringModelParameterDao.isAsScoringAttributeWithWholeName(scoringModelId,
                            listFromFileExcel.get(0).get(a) + parameterConnector +
                            listFromFileExcel.get(0).get(b))){
                        for(int c = 1; c < listFromFileExcel.size(); c++){
                            Integer tempScoreForArray = scoringModelParameterDao.getScoreForAttributeValue(scoringModelId,
                                    listFromFileExcel.get(0).get(a) + parameterConnector +
                                    listFromFileExcel.get(0).get(b), listFromFileExcel.get(c).get(a) +
                                    parameterConnector + listFromFileExcel.get(c).get(b));
                            if(tempScoreForArray != null && !tempScoreForArray.equals(0)){
                                scoreArray[c] += tempScoreForArray;
                            }
                        }
                    }
                }
            }
        }
        String[] arrayGoodBad = new String[listFromFileExcel.size()];
        for(int a = 0; a < listFromFileExcel.size(); a++){
            arrayGoodBad[a] = listFromFileExcel.get(a).get(0);
        }
        ScoringSettingsModel scoringSettingsModel = scoringSettingsModelDao.findByUser(username);
        int minScore = findMinScore(scoreArray);
        int maxScore = findMaxScore(scoreArray);
        int numberForEachStepMinAndMax = (maxScore - minScore) / scoringSettingsModel.getNumberWishedRowsForCalcTestModel();
        ArrayList<ArrayList<Integer>> resultGroupScore = new ArrayList<>();
        ArrayList<Integer> firstArray = new ArrayList<>();
        firstArray.add(minScore);
        firstArray.add(firstArray.get(0) + numberForEachStepMinAndMax);
        resultGroupScore.add(firstArray);
        while (resultGroupScore.get(resultGroupScore.size()-1).get(1) < maxScore){
            ArrayList<Integer> tempArray = new ArrayList<>();
            tempArray.add(resultGroupScore.get(resultGroupScore.size()-1).get(1)+1);
            if(tempArray.get(0) + numberForEachStepMinAndMax >= maxScore){
                tempArray.add(maxScore);
            } else {
                tempArray.add(tempArray.get(0) + numberForEachStepMinAndMax);
            }
            resultGroupScore.add(tempArray);
        }
        TestScoringModelResult testScoringModelResultTotal = new TestScoringModelResult();
        testScoringModelResultTotal.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        testScoringModelResultTotal.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        testScoringModelResultTotal.setTestScoringModel(testScoringModelSaved);
        testScoringModelResultTotal.setScore("Total");
        testScoringModelResultTotal.setTotal(true);
        testScoringModelResultTotal.setCountTotalItems(arrayGoodBad.length - 1);
        testScoringModelResultTotal.setCountGoodItems(countGoodBadItemsTotalForTestModel(arrayGoodBad,
                scoringSettingsModel.getGoodResult()));
        testScoringModelResultTotal.setCountBadItems(countGoodBadItemsTotalForTestModel(arrayGoodBad,
                scoringSettingsModel.getBadResult()));
        testScoringModelResultTotal.setBadRate(badRateForTestModelRow(testScoringModelResultTotal.getCountTotalItems(),
                testScoringModelResultTotal.getCountBadItems()));
        for(int a = 0; a < resultGroupScore.size(); a++){
            TestScoringModelResult testScoringModelResult = new TestScoringModelResult();
            testScoringModelResult.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            testScoringModelResult.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            testScoringModelResult.setTestScoringModel(testScoringModelSaved);
            testScoringModelResult.setOrderNumberRow(a);
            testScoringModelResult.setTotal(false);
            testScoringModelResult.setScore(resultGroupScore.get(a).get(0) + " - " + resultGroupScore.get(a).get(1));
            testScoringModelResult.setCountTotalItems(countTotalItemsForTestModelRow(scoreArray,
                    resultGroupScore.get(a).get(0),resultGroupScore.get(a).get(1)));
            testScoringModelResult.setCountGoodItems(countGoodBadItemsForTestModelRow(scoreArray,
                    arrayGoodBad, resultGroupScore.get(a).get(0), resultGroupScore.get(a).get(1),
                    scoringSettingsModel.getGoodResult()));
            testScoringModelResult.setCountBadItems(countGoodBadItemsForTestModelRow(scoreArray,
                    arrayGoodBad, resultGroupScore.get(a).get(0), resultGroupScore.get(a).get(1),
                    scoringSettingsModel.getBadResult()));
            testScoringModelResult.setBadRate(badRateForTestModelRow(testScoringModelResult.getCountTotalItems(),
                    testScoringModelResult.getCountBadItems()));
            testScoringModelResult.setCumTotalItemsCount(cumTotalItemsCountForTestModelRow(testScoringModelResult.getOrderNumberRow(),
                    testScoringModelResult.getCountTotalItems(), testScoringModelSaved.getId()));
            testScoringModelResult.setCumGoodItemsCount(cumGoodItemsCountForTestModelRow(testScoringModelResult.getOrderNumberRow(),
                    testScoringModelResult.getCountGoodItems(), testScoringModelSaved.getId()));
            testScoringModelResult.setCumBadItemsCount(cumBadItemsCountForTestModelRow(testScoringModelResult.getOrderNumberRow(),
                    testScoringModelResult.getCountBadItems(), testScoringModelSaved.getId()));
            testScoringModelResult.setCumTotalItemsPercent(cumItemsPercentForTestModelRow(testScoringModelResult
                    .getCumTotalItemsCount(), testScoringModelResultTotal.getCountTotalItems()));
            testScoringModelResult.setCumGoodItemsPercent(cumItemsPercentForTestModelRow(testScoringModelResult
                    .getCumGoodItemsCount(), testScoringModelResultTotal.getCountGoodItems()));
            testScoringModelResult.setCumBadItemsPercent(cumItemsPercentForTestModelRow(testScoringModelResult
                    .getCumBadItemsCount(), testScoringModelResultTotal.getCountBadItems()));
            testScoringModelResult.setGiniBiBiOne(giniBiBiOne(testScoringModelResult.getOrderNumberRow(),
                    testScoringModelResult.getCumBadItemsPercent(), testScoringModelSaved.getId()));
            testScoringModelResult.setGiniGiGiOne(giniGiGiOne(testScoringModelResult.getOrderNumberRow(),
                    testScoringModelResult.getCumGoodItemsPercent(), testScoringModelSaved.getId()));
            testScoringModelResult.setGiniResult(giniResultRow(testScoringModelResult.getGiniBiBiOne(),
                    testScoringModelResult.getGiniGiGiOne()));
            testScoringModelResultDao.create(testScoringModelResult);
        }
        double giniForResultTotal = 0.00;
        for(int a = 0; a < resultGroupScore.size(); a++){
            giniForResultTotal += testScoringModelResultDao.getByModelAndOrderRowNumber(testScoringModelSaved.getId(),
                    a).getGiniResult();
        }
        testScoringModelResultTotal.setGiniResult(1 - giniForResultTotal);
        testScoringModelResultDao.create(testScoringModelResultTotal);
        return testScoringModelResultTotal.getGiniResult();
    }

    private double giniResultRow(double giniBiBiOne, double giniGiGiOne){
        return giniBiBiOne * giniGiGiOne;
    }

    private double giniGiGiOne(int orderRowNumber, double cumGoodItemsPercent, Long testModelId){
        if(orderRowNumber == 0){
            return cumGoodItemsPercent;
        } else {
            return cumGoodItemsPercent + testScoringModelResultDao.getByModelAndOrderRowNumber(testModelId,
                    orderRowNumber - 1).getCumGoodItemsPercent();
        }
    }

    private double giniBiBiOne(int orderRowNumber, double cumBadItemsPercent, Long testModelId){
        if(orderRowNumber == 0){
            return cumBadItemsPercent;
        } else {
            return cumBadItemsPercent - testScoringModelResultDao.getByModelAndOrderRowNumber(testModelId,
                            orderRowNumber - 1).getCumBadItemsPercent();
        }
    }

    private double cumItemsPercentForTestModelRow(int cumCountThisRow, int totalCountTestModel){
        return ((double) cumCountThisRow / (double) totalCountTestModel);
    }

    private int cumTotalItemsCountForTestModelRow(int orderNumberRow, int totalCountThisRow, Long testModelId){
        if(orderNumberRow == 0){
            return totalCountThisRow;
        } else {
            return testScoringModelResultDao.getByModelAndOrderRowNumber(testModelId, orderNumberRow - 1)
                    .getCumTotalItemsCount() + totalCountThisRow;
        }
    }

    private int cumGoodItemsCountForTestModelRow(int orderNumberRow, int totalCountThisRow, Long testModelId){
        if(orderNumberRow == 0){
            return totalCountThisRow;
        } else {
            return testScoringModelResultDao.getByModelAndOrderRowNumber(testModelId, orderNumberRow - 1)
                    .getCumGoodItemsCount() + totalCountThisRow;
        }
    }

    private int cumBadItemsCountForTestModelRow(int orderNumberRow, int totalCountThisRow, Long testModelId){
        if(orderNumberRow == 0){
            return totalCountThisRow;
        } else {
            return testScoringModelResultDao.getByModelAndOrderRowNumber(testModelId, orderNumberRow - 1)
                    .getCumBadItemsCount() + totalCountThisRow;
        }
    }

    private double badRateForTestModelRow(int countTotalItems, int countBadItems){
        if (countTotalItems != 0){
            return (double) countBadItems / (double) countTotalItems;
        } else {
            return 0.00;
        }
    }

    private int countGoodBadItemsTotalForTestModel(String[] goodBadArray, String markedGoodBadResult){
        int countItems = 0;
        for(int a = 1; a < goodBadArray.length; a++){
            if(goodBadArray[a].equals(markedGoodBadResult)){
                countItems++;
            }
        }
        return countItems;
    }

    private int countGoodBadItemsForTestModelRow(int[] scoreArray, String[] goodBadArray, int firstScore, int lastScore, String markedGoodBadResult){
        int countClients = 0;
        for(int a = 1; a < scoreArray.length; a++){
            if(scoreArray[a] >= firstScore && scoreArray[a] <= lastScore && goodBadArray[a].equals(markedGoodBadResult)){
                countClients++;
            }
        }
        return countClients;
    }

    private int countTotalItemsForTestModelRow(int[] scoreArray, int firstScore, int lastScore){
        int countClients = 0;
        for(int a = 1; a < scoreArray.length; a++){
            if(scoreArray[a] >= firstScore && scoreArray[a] <= lastScore){
                countClients++;
            }
        }
        return countClients;
    }


    private int findMinScore(int[] resultOfCalculateScoreForList){
        int result = resultOfCalculateScoreForList[0];
        for(int a = 1; a < resultOfCalculateScoreForList.length; a++){
            if(resultOfCalculateScoreForList[a] < result){
                result = resultOfCalculateScoreForList[a];
            }
        }
        return result;
    }

    private int findMaxScore(int[] resultOfCalculateScoreForList){
        int result = resultOfCalculateScoreForList[0];
        for(int a = 1; a < resultOfCalculateScoreForList.length; a++){
            if(resultOfCalculateScoreForList[a] > result){
                result = resultOfCalculateScoreForList[a];
            }
        }
        return result;
    }
}
