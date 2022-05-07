package com.korotkov.main.service.scoringModel.additionalService.additionalEntityForPortal.EntityForExportFile;

import com.korotkov.main.model.ScoringModelParameter;
import com.korotkov.main.service.scoringModel.additionalService.additionalEntityForPortal.ScoringParameterPortalCommon;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class ScoringModelExportFile {
    private LocalDateTime createdAt;
    private String titleModel;
    private List<ScoringModelParameter> allParametersInfluenceOneTotal;
    private List<ScoringParameterPortalCommon> scoringModelRecommendedParametersListOne;
    private List<ScoringParameterPortalCommon> scoringModelRecommendedParametersListTwo;

    public ScoringModelExportFile(LocalDateTime createdAt, String titleModel,
                           List<ScoringModelParameter> allParametersInfluenceOneTotal,
                           List<ScoringParameterPortalCommon> scoringModelRecommendedParametersListOne,
                           List<ScoringParameterPortalCommon> scoringModelRecommendedParametersListTwo){
        this.createdAt = createdAt;
        this.titleModel = titleModel;
        if (!allParametersInfluenceOneTotal.isEmpty()) {
            allParametersInfluenceOneTotal.sort(Comparator.comparing(ScoringModelParameter::getIv).reversed());
        }
        this.allParametersInfluenceOneTotal = allParametersInfluenceOneTotal;
        if (!scoringModelRecommendedParametersListOne.isEmpty()) {
            scoringModelRecommendedParametersListOne.sort(Comparator.comparing(ScoringParameterPortalCommon::getIvTotal).reversed());
        }
        this.scoringModelRecommendedParametersListOne = scoringModelRecommendedParametersListOne;
        if(!scoringModelRecommendedParametersListTwo.isEmpty()) {
            scoringModelRecommendedParametersListTwo.sort(Comparator.comparing(ScoringParameterPortalCommon::getIvTotal).reversed());
        }
        this.scoringModelRecommendedParametersListTwo = scoringModelRecommendedParametersListTwo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitleModel() {
        return titleModel;
    }

    public void setTitleModel(String titleModel) {
        this.titleModel = titleModel;
    }

    public List<ScoringModelParameter> getAllParametersInfluenceOneTotal() {
        return allParametersInfluenceOneTotal;
    }

    public void setAllParametersInfluenceOneTotal(List<ScoringModelParameter> allParametersInfluenceOneTotal) {
        this.allParametersInfluenceOneTotal = allParametersInfluenceOneTotal;
    }

    public List<ScoringParameterPortalCommon> getScoringModelRecommendedParametersListOne() {
        return scoringModelRecommendedParametersListOne;
    }

    public void setScoringModelRecommendedParametersListOne(List<ScoringParameterPortalCommon> scoringModelRecommendedParametersListOne) {
        this.scoringModelRecommendedParametersListOne = scoringModelRecommendedParametersListOne;
    }

    public List<ScoringParameterPortalCommon> getScoringModelRecommendedParametersListTwo() {
        return scoringModelRecommendedParametersListTwo;
    }

    public void setScoringModelRecommendedParametersListTwo(List<ScoringParameterPortalCommon> scoringModelRecommendedParametersListTwo) {
        this.scoringModelRecommendedParametersListTwo = scoringModelRecommendedParametersListTwo;
    }
}
