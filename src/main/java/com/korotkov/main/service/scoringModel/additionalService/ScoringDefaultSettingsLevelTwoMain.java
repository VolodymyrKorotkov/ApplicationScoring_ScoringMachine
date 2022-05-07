package com.korotkov.main.service.scoringModel.additionalService;

public interface ScoringDefaultSettingsLevelTwoMain {
    public final static String parameterConnector = " & ";

    public final static String goodResult = "GOOD";
    public final static String badResult = "BAD";
    public final static Integer factor = 50;
    public final static Integer offset = 5;
    public static final double minimumNeededIVForParameterOne = 0.025;
    public static final double minimumNeededAverageIVForKeyOfParameterOne = 0.001;
    public static final double minimumNeededIVForParameterTwo = 0.07;
    public static final double minimumNeededAverageIVForKeyOfParameterTwo = 0.007;
    public static final int maxRowsForInfluenceParameterTwo = 20;
    public static final int numberWishedRowsForCalcTestModel = 21;
}
