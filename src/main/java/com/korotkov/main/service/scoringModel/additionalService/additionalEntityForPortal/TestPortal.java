package com.korotkov.main.service.scoringModel.additionalService.additionalEntityForPortal;

import java.time.LocalDateTime;
import java.util.List;

public class TestPortal {
    private final Long id;
    private final LocalDateTime createdAt;
    private final String title;
    private final int countTotalItems;
    private final int countGoodItems;
    private final int countBadItems;
    private final double badRate;
    private final double giniResult;
    private final List<TestResultsPortal> testResultsPortalList;

    public TestPortal(Long id, LocalDateTime createdAt, String title, int countTotalItems, int countGoodItems,
                      int countBadItems, double badRate, double giniResult, List<TestResultsPortal> testResultsPortalList){
        this.id = id;
        this.createdAt = createdAt;
        this.title = title;
        this.countTotalItems = countTotalItems;
        this.countGoodItems = countGoodItems;
        this.countBadItems = countBadItems;
        this.badRate = badRate;
        this.giniResult = giniResult;
        this.testResultsPortalList = testResultsPortalList;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getTitle() {
        return title;
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

    public List<TestResultsPortal> getTestResultsPortalList() {
        return testResultsPortalList;
    }
}
