package com.korotkov.main.service.scoringModel.additionalService;

public interface ScoringSettingsLevelOne {
    public static final double minimumNeededIVForParameterOneLevelOne = 0.01;
    public static final double minimumNeededAverageIVForKeyOfParameterOneLevelOne = 0.0001;
    public static final double minimumNeededIVForParameterTwoLevelOne = 0.03;
    public static final double minimumNeededAverageIVForKeyOfParameterTwoLevelOne = 0.002;
    public static final int maxRowsForInfluenceParameterTwoLevelOne = 30;
}
