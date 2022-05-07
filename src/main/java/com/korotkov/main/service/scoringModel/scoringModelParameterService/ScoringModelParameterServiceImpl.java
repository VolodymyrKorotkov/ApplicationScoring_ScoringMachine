package com.korotkov.main.service.scoringModel.scoringModelParameterService;

import com.korotkov.main.config.PortalConstants;
import com.korotkov.main.dao.ScoringModelDao;
import com.korotkov.main.dao.ScoringModelParameterDao;
import com.korotkov.main.model.ScoringModel;
import com.korotkov.main.model.ScoringModelParameter;
import com.korotkov.main.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class ScoringModelParameterServiceImpl implements ScoringModelParameterService, PortalConstants {
    ScoringModelParameterDao scoringModelParameterDao;
    ScoringModelDao scoringModelDao;

    @Autowired
    public void setScoringModelParameterDao(ScoringModelParameterDao scoringModelParameterDao){
        this.scoringModelParameterDao = scoringModelParameterDao;
    }

    @Autowired
    public void setScoringModelDao(ScoringModelDao scoringModelDao){
        this.scoringModelDao = scoringModelDao;
    }

    @Override
    @Transactional
    public ScoringModelParameter getModelAttributeValue(UserAccount userAccount, Long modelId, Long idInDataBase){
        return scoringModelParameterDao.getModelAttributeValue(userAccount.getUsername(), modelId, idInDataBase);
    }

    @Override
    @Transactional
    public void setChangedScoreInAttributeValue(UserAccount userAccount, Long modelId, Long idInDataBase,
                                                ScoringModelParameter scoringModelParameter){
        ScoringModelParameter scoringModelParameterFromDB =
                scoringModelParameterDao.getModelAttributeValue(userAccount.getUsername(), modelId, idInDataBase);
        ScoringModel scoringModelFromDB = scoringModelDao.getScoringModelByIdAndUsername(userAccount.getUsername(), modelId);
        scoringModelFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        scoringModelParameterFromDB.setScore(scoringModelParameter.getScore());
        scoringModelParameterFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        scoringModelDao.update(scoringModelFromDB);
        scoringModelParameterDao.update(scoringModelParameterFromDB);
    }
}
