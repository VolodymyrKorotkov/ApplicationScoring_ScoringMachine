package com.korotkov.main.service.scoringModel.scoringModelService;

import com.korotkov.main.config.PortalConstants;
import com.korotkov.main.dao.ScoringModelDao;
import com.korotkov.main.enums.ScoringModelStatus;
import com.korotkov.main.model.ScoringModel;
import com.korotkov.main.model.ScoringModelParameter;
import com.korotkov.main.model.UserAccount;
import com.korotkov.main.service.scoringModel.additionalService.additionalEntityForPortal.ScoringParameterPortalCommon;
import com.korotkov.main.service.scoringModel.additionalService.additionalEntityForPortal.ScoringParameterPortalRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ScoringModelServiceImpl implements ScoringModelService, PortalConstants {
    ScoringModelDao scoringModelDao;

    @Autowired
    public void setScoringModelDao(ScoringModelDao scoringModelDao){
        this.scoringModelDao = scoringModelDao;
    }

    @Override
    @Transactional
    public List<ScoringModel> getAllModels(UserAccount userAccount, int page){
        return scoringModelDao.getAllModels(userAccount.getUsername(), page);
    }

    @Override
    @Transactional
    public List<ScoringModel> getModelsWithFilter(UserAccount userAccount, int page, String scoringModelTitle,
                                                 String scoringModelStatus){
        return scoringModelDao.getModelsWithFilter(userAccount.getUsername(), page, scoringModelTitle,
                scoringModelStatus);
    }

    @Override
    @Transactional
    public List<ScoringModel> getModelsWithFilter(UserAccount userAccount, int page, String scoringModelTitle){
        return scoringModelDao.getModelsWithFilter(userAccount.getUsername(), page, scoringModelTitle);
    }

    @Override
    @Transactional
    public List<ScoringModel> getModelsWithFilter(UserAccount userAccount, String scoringModelStatus, int page){
        return scoringModelDao.getModelsWithFilter(userAccount.getUsername(), scoringModelStatus, page);
    }

    @Override
    @Transactional
    public Long getCountModelsWithFilter(UserAccount userAccount, String scoringModelTitle, String scoringModelStatus){
        return scoringModelDao.getCountModelsWithFilter(userAccount.getUsername(), scoringModelTitle, scoringModelStatus);
    }

    @Override
    @Transactional
    public ScoringModel getScoringModelByIdAndUser(UserAccount userAccount, Long modelId){
        return scoringModelDao.getScoringModelByIdAndUsername(userAccount.getUsername(), modelId);
    }

    @Override
    @Transactional
    public List<ScoringParameterPortalCommon> getScoringModelRecommendedParametersList(UserAccount userAccount, Long modelId,
                                                                                String scoringParameterType){
        List<ScoringModelParameter> scoringModelParameterList =
                scoringModelDao.getScoringModelRecommendedParametersList(userAccount.getUsername(), modelId,
                scoringParameterType);
        Set<String> scoringModelParameterNameList = new HashSet<>();
        for(int a = 0; a < scoringModelParameterList.size(); a++){
            scoringModelParameterNameList.add(scoringModelParameterList.get(a).getNameParameter());
        }
        List<ScoringParameterPortalCommon> scoringParameterPortalCommonList = new ArrayList<>();
        for (String key: scoringModelParameterNameList) {
            ScoringParameterPortalCommon scoringParameterPortalCommon = new ScoringParameterPortalCommon(key);
            List<ScoringParameterPortalRow> scoringParameterPortalRowList = new ArrayList<>();
            for(int a = 0; a < scoringModelParameterList.size(); a++){
                if(key.equals(scoringModelParameterList.get(a).getNameParameter())){
                    if(scoringModelParameterList.get(a).isTotal()){
                        scoringParameterPortalCommon.setGoodCountTotal(scoringModelParameterList.get(a).getGoodCount());
                        scoringParameterPortalCommon.setBadCountTotal(scoringModelParameterList.get(a).getBadCount());
                        scoringParameterPortalCommon.setTotalCountTotal(scoringModelParameterList.get(a).getTotalCount());
                        scoringParameterPortalCommon.setIvTotal(scoringModelParameterList.get(a).getIv());
                        scoringParameterPortalCommon.setGoodRateTotal(scoringModelParameterList.get(a).getGoodRate());
                        scoringParameterPortalCommon.setBadRateTotal(scoringModelParameterList.get(a).getBadRate());
                    } else {
                        scoringParameterPortalRowList.add(new ScoringParameterPortalRow(
                                scoringModelParameterList.get(a).getTitle(),
                                scoringModelParameterList.get(a).getGoodCount(),
                                scoringModelParameterList.get(a).getBadCount(),
                                scoringModelParameterList.get(a).getGoodRate(),
                                scoringModelParameterList.get(a).getBadRate(),
                                scoringModelParameterList.get(a).getTotalCount(),
                                scoringModelParameterList.get(a).getGoodPopulationPercent(),
                                scoringModelParameterList.get(a).getBadPopulationPercent(),
                                scoringModelParameterList.get(a).getTotalPopulationPercent(),
                                scoringModelParameterList.get(a).getGiG(),
                                scoringModelParameterList.get(a).getBiB(),
                                scoringModelParameterList.get(a).getPgPb(),
                                scoringModelParameterList.get(a).getWoe(),
                                scoringModelParameterList.get(a).getIv(),
                                scoringModelParameterList.get(a).getScore(),
                                scoringModelParameterList.get(a).getId()));
                    }
                }
            }
            scoringParameterPortalCommon.setScoringParameterPortalRows(scoringParameterPortalRowList);
            scoringParameterPortalCommonList.add(scoringParameterPortalCommon);
        }
        return scoringParameterPortalCommonList;
    }

    @Override
    @Transactional
    public List<ScoringModelParameter> getScoringModelAllParametersTotalList(UserAccount userAccount, Long modelId,
                                                                        String scoringParameterType){
        return scoringModelDao.getScoringModelAllParametersTotalList(userAccount.getUsername(), modelId, scoringParameterType);
    }

    @Override
    @Transactional
    public void updateTitleAndDescriptionInScoringModel(UserAccount userAccount, Long modelId, ScoringModel scoringModel){
        ScoringModel scoringModelFromDB = scoringModelDao.getScoringModelByIdAndUsername(userAccount.getUsername(),
                modelId);
        if(!scoringModelFromDB.getTitle().equals(scoringModel.getTitle()) ||
                !scoringModelFromDB.getDescription().equals(scoringModel.getDescription())){
            scoringModelFromDB.setTitle(scoringModel.getTitle());
            scoringModelFromDB.setDescription(scoringModel.getDescription());
            scoringModelFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            scoringModelDao.update(scoringModelFromDB);
        }
    }

    @Override
    @Transactional
    public void deleteScoringModel(UserAccount userAccount, Long modelId){
        scoringModelDao.delete(scoringModelDao.getScoringModelByIdAndUsername(userAccount.getUsername(),
                modelId));
    }

    @Override
    @Transactional
    public void activateScoringModel(UserAccount userAccount, Long modelId){
        ScoringModel scoringModelFromDB = scoringModelDao.getScoringModelByIdAndUsername(userAccount.getUsername(),
                modelId);
        if(!scoringModelFromDB.getStatus().equals(String.valueOf(ScoringModelStatus.ACTIVE))){
            List<ScoringModel> scoringModelList =
                    scoringModelDao.findModelsWithStatus(userAccount.getUsername(), String.valueOf(ScoringModelStatus.ACTIVE));
            for (ScoringModel sm : scoringModelList) {
                sm.setStatus(String.valueOf(ScoringModelStatus.INACTIVE));
                sm.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                scoringModelDao.update(sm);
            }
            scoringModelFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            scoringModelFromDB.setStatus(String.valueOf(ScoringModelStatus.ACTIVE));
            scoringModelDao.update(scoringModelFromDB);
        }
    }

    @Override
    @Transactional
    public void deactivateScoringModel(UserAccount userAccount, Long modelId){
        ScoringModel scoringModelFromDB = scoringModelDao.getScoringModelByIdAndUsername(userAccount.getUsername(),
                modelId);
        if(!scoringModelFromDB.getStatus().equals(String.valueOf(ScoringModelStatus.INACTIVE))){
            scoringModelFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            scoringModelFromDB.setStatus(String.valueOf(ScoringModelStatus.INACTIVE));
            scoringModelDao.update(scoringModelFromDB);
        }
    }

    @Override
    @Transactional
    public ScoringModel findActiveModelByUser(UserAccount userAccount){
        return scoringModelDao.findActiveModelByUser(userAccount.getUsername());
    }
}
