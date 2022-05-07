package com.korotkov.main.service.scoringModel.additionalService.additionalEntityForPortal.EntityForExportFile;

import com.korotkov.main.model.TestScoringModelResult;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class TestOfModelExportFile {
    private final String titleTest;
    private final LocalDateTime createdAtTest;
    private final String titleScoringModel;
    private final Double giniIndex;
    private final TestScoringModelResult testScoringModelResultTotal;
    private final List<TestScoringModelResult> listTestResultsRowsWithoutTotal;

    public TestOfModelExportFile(String titleTest, LocalDateTime createdAtTest, String titleScoringModel,
                                 Double giniIndex, TestScoringModelResult testScoringModelResultTotal,
                                 List<TestScoringModelResult> listTestResultsRowsWithoutTotal){
        this.titleTest = titleTest;
        this.createdAtTest = createdAtTest;
        this.titleScoringModel = titleScoringModel;
        this.giniIndex = giniIndex;
        this.testScoringModelResultTotal = testScoringModelResultTotal;
        if (!listTestResultsRowsWithoutTotal.isEmpty()){
            listTestResultsRowsWithoutTotal.sort(Comparator.comparing(TestScoringModelResult::getOrderNumberRow));
        }
        this.listTestResultsRowsWithoutTotal = listTestResultsRowsWithoutTotal;
    }

    public String getTitleTest() {
        return titleTest;
    }

    public String getTitleScoringModel() {
        return titleScoringModel;
    }

    public LocalDateTime getCreatedAtTest() {
        return createdAtTest;
    }

    public TestScoringModelResult getTestScoringModelResultTotal() {
        return testScoringModelResultTotal;
    }

    public Double getGiniIndex() {
        return giniIndex;
    }

    public List<TestScoringModelResult> getListTestResultsRowsWithoutTotal() {
        return listTestResultsRowsWithoutTotal;
    }
}
