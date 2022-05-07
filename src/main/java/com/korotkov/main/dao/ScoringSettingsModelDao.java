package com.korotkov.main.dao;

import com.korotkov.main.model.ScoringSettingsModel;
import com.korotkov.main.model.UserAccount;

public interface ScoringSettingsModelDao {
    void create(ScoringSettingsModel scoringSettingsModel);
    void update(ScoringSettingsModel scoringSettingsModel);
    ScoringSettingsModel getById(Long id);
    ScoringSettingsModel findByUser(String username);
}
