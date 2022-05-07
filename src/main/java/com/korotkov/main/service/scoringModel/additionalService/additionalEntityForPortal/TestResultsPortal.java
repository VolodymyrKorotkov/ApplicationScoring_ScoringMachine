package com.korotkov.main.service.scoringModel.additionalService.additionalEntityForPortal;

public class TestResultsPortal {
    private final String score;
    private final int countTotalItems;
    private final int countGoodItems;
    private final int countBadItems;
    private final double badRate;
    private final double giniResult;
    private final int orderNumberRow;

    public TestResultsPortal (String score, int countTotalItems, int countGoodItems, int countBadItems, double badRate,
                              double giniResult, int orderNumberRow){
        this.score = score;
        this.countTotalItems = countTotalItems;
        this.countGoodItems = countGoodItems;
        this.countBadItems = countBadItems;
        this.badRate = badRate;
        this.giniResult = giniResult;
        this.orderNumberRow = orderNumberRow;
    }

    public String getScore(){
        return score;
    }

    public int getCountTotalItems() {
        return countTotalItems;
    }

    public int getCountGoodItems() {
        return countGoodItems;
    }

    public int getCountBadItems() {
        return countBadItems;
    }

    public double getBadRate() {
        return badRate;
    }

    public double getGiniResult() {
        return giniResult;
    }

    public int getOrderNumberRow() {
        return orderNumberRow;
    }
}
