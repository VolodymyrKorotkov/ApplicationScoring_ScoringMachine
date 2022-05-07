package com.korotkov.main.service.scoringModel.scoringSettings;

import com.korotkov.main.config.PortalConstants;
import com.korotkov.main.dao.ScoringSettingsModelDao;
import com.korotkov.main.enums.ScoringSettingsQualityLevel;
import com.korotkov.main.model.ScoringSettingsModel;
import com.korotkov.main.model.UserAccount;
import com.korotkov.main.service.scoringModel.additionalService.*;
import com.korotkov.main.service.scoringModel.scoringSettings.ScoringSettingsModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class ScoringSettingsModelServiceImpl implements ScoringSettingsModelService, PortalConstants,
        ScoringDefaultSettingsLevelTwoMain, ScoringSettingsLevelOne, ScoringSettingsLevelThree,
        ScoringSettingsLevelFour, ScoringSettingsLevelFive {
    private ScoringSettingsModelDao scoringSettingsModelDao;

    @Autowired
    public void setScoringSettingsModelDao(ScoringSettingsModelDao scoringSettingsModelDao){
        this.scoringSettingsModelDao = scoringSettingsModelDao;
    }

    @Override
    @Transactional
    public void create(ScoringSettingsModel scoringSettingsModel){
        scoringSettingsModelDao.create(scoringSettingsModel);
    }

    @Override
    @Transactional
    public void update(ScoringSettingsModel scoringSettingsModel){
        scoringSettingsModelDao.update(scoringSettingsModel);
    }

    @Override
    @Transactional
    public ScoringSettingsModel getById(Long id){
        return scoringSettingsModelDao.getById(id);
    }

    @Override
    @Transactional
    public ScoringSettingsModel findByUser(UserAccount userAccount){
        return scoringSettingsModelDao.findByUser(userAccount.getUsername());
    }

    @Override
    @Transactional
    public boolean updateScoringSettings(UserAccount userAccount, ScoringSettingsModel scoringSettingsModel){
        ScoringSettingsModel scoringSettingsModelFromDB = scoringSettingsModelDao.findByUser(userAccount.getUsername());
        scoringSettingsModelFromDB.setGoodResult(scoringSettingsModel.getGoodResult());
        scoringSettingsModelFromDB.setBadResult(scoringSettingsModel.getBadResult());
        scoringSettingsModelFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        if(scoringSettingsModel.getModelQualityLevel().equals(String.valueOf(ScoringSettingsQualityLevel.LEVEL_1))){
            scoringSettingsModelFromDB.setModelQualityLevel(String.valueOf(ScoringSettingsQualityLevel.LEVEL_1));
            scoringSettingsModelFromDB.setMinimumNeededIVForParameterOne(minimumNeededIVForParameterOneLevelOne);
            scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterOne(minimumNeededAverageIVForKeyOfParameterOneLevelOne);
            scoringSettingsModelFromDB.setMinimumNeededIVForParameterTwo(minimumNeededIVForParameterTwoLevelOne);
            scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterTwo(minimumNeededAverageIVForKeyOfParameterTwoLevelOne);
            scoringSettingsModelFromDB.setMaxRowsForInfluenceParameterTwo(maxRowsForInfluenceParameterTwoLevelOne);
            scoringSettingsModelDao.update(scoringSettingsModelFromDB);
            return true;
        }
        if(scoringSettingsModel.getModelQualityLevel().equals(String.valueOf(ScoringSettingsQualityLevel.LEVEL_2))){
            scoringSettingsModelFromDB.setModelQualityLevel(String.valueOf(ScoringSettingsQualityLevel.LEVEL_2));
            scoringSettingsModelFromDB.setMinimumNeededIVForParameterOne(minimumNeededIVForParameterOne);
            scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterOne(minimumNeededAverageIVForKeyOfParameterOne);
            scoringSettingsModelFromDB.setMinimumNeededIVForParameterTwo(minimumNeededIVForParameterTwo);
            scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterTwo(minimumNeededAverageIVForKeyOfParameterTwo);
            scoringSettingsModelFromDB.setMaxRowsForInfluenceParameterTwo(maxRowsForInfluenceParameterTwo);
            scoringSettingsModelDao.update(scoringSettingsModelFromDB);
            return true;
        }
        if(scoringSettingsModel.getModelQualityLevel().equals(String.valueOf(ScoringSettingsQualityLevel.LEVEL_3))){
            scoringSettingsModelFromDB.setModelQualityLevel(String.valueOf(ScoringSettingsQualityLevel.LEVEL_3));
            scoringSettingsModelFromDB.setMinimumNeededIVForParameterOne(minimumNeededIVForParameterOneLevelThree);
            scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterOne(minimumNeededAverageIVForKeyOfParameterOneLevelThree);
            scoringSettingsModelFromDB.setMinimumNeededIVForParameterTwo(minimumNeededIVForParameterTwoLevelThree);
            scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterTwo(minimumNeededAverageIVForKeyOfParameterTwoLevelThree);
            scoringSettingsModelFromDB.setMaxRowsForInfluenceParameterTwo(maxRowsForInfluenceParameterTwoLevelThree);
            scoringSettingsModelDao.update(scoringSettingsModelFromDB);
            return true;
        }
        if(scoringSettingsModel.getModelQualityLevel().equals(String.valueOf(ScoringSettingsQualityLevel.LEVEL_4))){
            scoringSettingsModelFromDB.setModelQualityLevel(String.valueOf(ScoringSettingsQualityLevel.LEVEL_4));
            scoringSettingsModelFromDB.setMinimumNeededIVForParameterOne(minimumNeededIVForParameterOneLevelFour);
            scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterOne(minimumNeededAverageIVForKeyOfParameterOneLevelFour);
            scoringSettingsModelFromDB.setMinimumNeededIVForParameterTwo(minimumNeededIVForParameterTwoLevelFour);
            scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterTwo(minimumNeededAverageIVForKeyOfParameterTwoLevelFour);
            scoringSettingsModelFromDB.setMaxRowsForInfluenceParameterTwo(maxRowsForInfluenceParameterTwoLevelFour);
            scoringSettingsModelDao.update(scoringSettingsModelFromDB);
            return true;
        }
        if(scoringSettingsModel.getModelQualityLevel().equals(String.valueOf(ScoringSettingsQualityLevel.LEVEL_5))){
            scoringSettingsModelFromDB.setModelQualityLevel(String.valueOf(ScoringSettingsQualityLevel.LEVEL_5));
            scoringSettingsModelFromDB.setMinimumNeededIVForParameterOne(minimumNeededIVForParameterOneLevelFive);
            scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterOne(minimumNeededAverageIVForKeyOfParameterOneLevelFive);
            scoringSettingsModelFromDB.setMinimumNeededIVForParameterTwo(minimumNeededIVForParameterTwoLevelFive);
            scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterTwo(minimumNeededAverageIVForKeyOfParameterTwoLevelFive);
            scoringSettingsModelFromDB.setMaxRowsForInfluenceParameterTwo(maxRowsForInfluenceParameterTwoLevelFive);
            scoringSettingsModelDao.update(scoringSettingsModelFromDB);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    @Transactional
    public void updateScoringAdvanceSettings(UserAccount userAccount, ScoringSettingsModel scoringSettingsModel){
        ScoringSettingsModel scoringSettingsModelFromDB = scoringSettingsModelDao.findByUser(userAccount.getUsername());
        scoringSettingsModelFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        scoringSettingsModelFromDB.setGoodResult(scoringSettingsModel.getGoodResult());
        scoringSettingsModelFromDB.setBadResult(scoringSettingsModel.getBadResult());
        scoringSettingsModelFromDB
                .setMinimumNeededIVForParameterOne(scoringSettingsModel.getMinimumNeededIVForParameterOne());
        scoringSettingsModelFromDB
                .setMinimumNeededAverageIVForKeyOfParameterOne(scoringSettingsModel
                        .getMinimumNeededAverageIVForKeyOfParameterOne());
        scoringSettingsModelFromDB
                .setMinimumNeededIVForParameterTwo(scoringSettingsModel
                        .getMinimumNeededIVForParameterTwo());
        scoringSettingsModelFromDB
                .setMinimumNeededAverageIVForKeyOfParameterTwo(scoringSettingsModel
                        .getMinimumNeededAverageIVForKeyOfParameterTwo());
        scoringSettingsModelFromDB
                .setMaxRowsForInfluenceParameterTwo(scoringSettingsModel.getMaxRowsForInfluenceParameterTwo());
        scoringSettingsModelFromDB
                .setFactor(scoringSettingsModel.getFactor());
        scoringSettingsModelFromDB.setOffset(scoringSettingsModel.getOffset());
        scoringSettingsModelFromDB
                .setModelQualityLevel(String.valueOf(ScoringSettingsQualityLevel.LEVEL_CUSTOM));
        scoringSettingsModelDao.update(scoringSettingsModelFromDB);
    }

    @Override
    @Transactional
    public void updateTestingModelSettings(UserAccount userAccount, ScoringSettingsModel scoringSettingsModel){
        ScoringSettingsModel scoringSettingsModelFromDB = scoringSettingsModelDao.findByUser(userAccount.getUsername());
        scoringSettingsModelFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        scoringSettingsModelFromDB.setGoodResult(scoringSettingsModel.getGoodResult());
        scoringSettingsModelFromDB.setBadResult(scoringSettingsModel.getBadResult());
        scoringSettingsModelFromDB.setNumberWishedRowsForCalcTestModel(scoringSettingsModel
                .getNumberWishedRowsForCalcTestModel());
        scoringSettingsModelDao.update(scoringSettingsModelFromDB);
    }

    @Override
    @Transactional
    public void restoreDefaultScoringSettings(UserAccount userAccount){
        ScoringSettingsModel scoringSettingsModelFromDB = scoringSettingsModelDao.findByUser(userAccount.getUsername());
        scoringSettingsModelFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        scoringSettingsModelFromDB.setGoodResult(goodResult);
        scoringSettingsModelFromDB.setBadResult(badResult);
        scoringSettingsModelFromDB.setMinimumNeededIVForParameterOne(minimumNeededIVForParameterOne);
        scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterOne(minimumNeededAverageIVForKeyOfParameterOne);
        scoringSettingsModelFromDB.setMinimumNeededIVForParameterTwo(minimumNeededIVForParameterTwo);
        scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterTwo(minimumNeededAverageIVForKeyOfParameterTwo);
        scoringSettingsModelFromDB.setMaxRowsForInfluenceParameterTwo(maxRowsForInfluenceParameterTwo);
        scoringSettingsModelFromDB.setFactor(factor);
        scoringSettingsModelFromDB.setOffset(offset);
        scoringSettingsModelFromDB.setModelQualityLevel(String.valueOf(ScoringSettingsQualityLevel.LEVEL_2));
        scoringSettingsModelDao.update(scoringSettingsModelFromDB);
    }

    @Override
    @Transactional
    public void restoreDefaultTestingModelSettings(UserAccount userAccount){
        ScoringSettingsModel scoringSettingsModelFromDB = scoringSettingsModelDao.findByUser(userAccount.getUsername());
        scoringSettingsModelFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        scoringSettingsModelFromDB.setGoodResult(goodResult);
        scoringSettingsModelFromDB.setBadResult(badResult);
        scoringSettingsModelFromDB.setNumberWishedRowsForCalcTestModel(numberWishedRowsForCalcTestModel);
        scoringSettingsModelDao.update(scoringSettingsModelFromDB);
    }

}
