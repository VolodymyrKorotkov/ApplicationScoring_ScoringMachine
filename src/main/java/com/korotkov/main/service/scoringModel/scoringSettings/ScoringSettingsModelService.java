package com.korotkov.main.service.scoringModel.scoringSettings;

import com.korotkov.main.model.ScoringSettingsModel;
import com.korotkov.main.model.UserAccount;

public interface ScoringSettingsModelService {
    void create(ScoringSettingsModel scoringSettingsModel);
    void update(ScoringSettingsModel scoringSettingsModel);
    ScoringSettingsModel getById(Long id);
    ScoringSettingsModel findByUser(UserAccount userAccount);
    boolean updateScoringSettings(UserAccount userAccount, ScoringSettingsModel scoringSettingsModel);
    void restoreDefaultScoringSettings(UserAccount userAccount);
    void updateScoringAdvanceSettings(UserAccount userAccount, ScoringSettingsModel scoringSettingsModel);
    void restoreDefaultTestingModelSettings(UserAccount userAccount);
    void updateTestingModelSettings(UserAccount userAccount, ScoringSettingsModel scoringSettingsModel);
}
