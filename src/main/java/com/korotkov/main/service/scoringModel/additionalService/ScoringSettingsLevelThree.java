package com.korotkov.main.service.scoringModel.additionalService;

public interface ScoringSettingsLevelThree {
    public static final double minimumNeededIVForParameterOneLevelThree = 0.03;
    public static final double minimumNeededAverageIVForKeyOfParameterOneLevelThree = 0.001;
    public static final double minimumNeededIVForParameterTwoLevelThree = 0.09;
    public static final double minimumNeededAverageIVForKeyOfParameterTwoLevelThree = 0.007;
    public static final int maxRowsForInfluenceParameterTwoLevelThree = 20;
}
