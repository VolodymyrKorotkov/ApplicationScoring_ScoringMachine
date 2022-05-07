package com.korotkov.main.service.scoringModel.additionalService.additionalEntityForPortal;

import java.util.List;

public class ScoringParameterPortalCommon {
    private String nameParameter;
    private int goodCountTotal;
    private int badCountTotal;
    private int totalCountTotal;
    private double ivTotal;

    private double goodRateTotal;
    private double badRateTotal;

    private List<ScoringParameterPortalRow> scoringParameterPortalRows;

    public ScoringParameterPortalCommon(String nameParameter, int goodCountTotal, int badCountTotal, int totalCountTotal,
                                        double ivTotal, double goodRateTotal, double badRateTotal,
                                        List<ScoringParameterPortalRow> scoringParameterPortalRows){
        this.nameParameter = nameParameter;
        this.goodCountTotal = goodCountTotal;
        this.badCountTotal = badCountTotal;
        this.totalCountTotal = totalCountTotal;
        this.ivTotal = ivTotal;
        this.goodRateTotal = goodRateTotal;
        this.badRateTotal = badRateTotal;
        this.scoringParameterPortalRows = scoringParameterPortalRows;
    }

    public ScoringParameterPortalCommon(String nameParameter){
        this.nameParameter = nameParameter;
    }

    public void setNameParameter(String nameParameter) {
        this.nameParameter = nameParameter;
    }

    public void setGoodCountTotal(int goodCountTotal) {
        this.goodCountTotal = goodCountTotal;
    }

    public void setBadCountTotal(int badCountTotal) {
        this.badCountTotal = badCountTotal;
    }

    public void setTotalCountTotal(int totalCountTotal) {
        this.totalCountTotal = totalCountTotal;
    }

    public void setIvTotal(double ivTotal) {
        this.ivTotal = ivTotal;
    }

    public void setGoodRateTotal(double goodRateTotal) {
        this.goodRateTotal = goodRateTotal;
    }

    public void setBadRateTotal(double badRateTotal) {
        this.badRateTotal = badRateTotal;
    }

    public void setScoringParameterPortalRows(List<ScoringParameterPortalRow> scoringParameterPortalRows) {
        this.scoringParameterPortalRows = scoringParameterPortalRows;
    }

    public String getNameParameter(){
        return nameParameter;
    }

    public int getGoodCountTotal(){
        return goodCountTotal;
    }

    public int getBadCountTotal() {
        return badCountTotal;
    }

    public double getGoodRateTotal() {
        return goodRateTotal;
    }

    public double getBadRateTotal() {
        return badRateTotal;
    }

    public int getTotalCountTotal() {
        return totalCountTotal;
    }

    public double getIvTotal() {
        return ivTotal;
    }

    public List<ScoringParameterPortalRow> getScoringParameterPortalRows() {
        return scoringParameterPortalRows;
    }
}
