package com.korotkov.main.service.scoringModel.additionalService.additionalEntityForPortal;

public class ScoringParameterPortalRow {
    private final String title;
    private final int goodCount;
    private final int badCount;
    private final double goodRate;
    private final double badRate;
    private final int totalCount;
    private final double goodPopulationPercent;
    private final double badPopulationPercent;
    private final double totalPopulationPercent;
    private final double giG;
    private final double biB;
    private final double pgPb;
    private final double woe;
    private final double iv;
    private final int score;
    private final Long idInDataBase;

    public ScoringParameterPortalRow(String title, int goodCount, int badCount, double goodRate, double badRate,
                                     int totalCount, double goodPopulationPercent, double badPopulationPercent,
                                     double totalPopulationPercent, double giG, double biB, double pgPb, double woe,
                                     double iv, int score, Long idInDataBase){
        this.title = title;
        this.goodCount = goodCount;
        this.badCount = badCount;
        this.goodRate = goodRate;
        this.badRate = badRate;
        this.totalCount = totalCount;
        this.goodPopulationPercent = goodPopulationPercent;
        this.badPopulationPercent = badPopulationPercent;
        this.totalPopulationPercent = totalPopulationPercent;
        this.giG = giG;
        this.biB = biB;
        this.pgPb = pgPb;
        this.woe = woe;
        this.iv = iv;
        this.score = score;
        this.idInDataBase = idInDataBase;
    }

    public String getTitle() {
        return title;
    }

    public int getGoodCount() {
        return goodCount;
    }

    public int getBadCount() {
        return badCount;
    }

    public double getGoodRate() {
        return goodRate;
    }

    public double getBadRate() {
        return badRate;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public double getGoodPopulationPercent() {
        return goodPopulationPercent;
    }

    public double getBadPopulationPercent() {
        return badPopulationPercent;
    }

    public double getTotalPopulationPercent() {
        return totalPopulationPercent;
    }

    public double getGiG() {
        return giG;
    }

    public double getBiB() {
        return biB;
    }

    public double getPgPb() {
        return pgPb;
    }

    public double getWoe() {
        return woe;
    }

    public double getIv() {
        return iv;
    }

    public int getScore() {
        return score;
    }

    public Long getIdInDataBase(){
        return idInDataBase;
    }
}
