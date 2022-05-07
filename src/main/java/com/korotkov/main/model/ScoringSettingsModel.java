package com.korotkov.main.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "scoring_settings")
public class ScoringSettingsModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "last_modified_at")
    LocalDateTime lastModifiedAt;

    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "client_id")
    @ManyToOne(fetch = FetchType.EAGER)
    UserAccount userAccount;

    @Column(name = "good_result")
    String goodResult;

    @Column(name = "bad_result")
    String badResult;

    @Column(name = "minimum_needed_iv_for_parameter_one")
    Double minimumNeededIVForParameterOne;

    @Column(name = "minimum_needed_average_iv_for_key_of_parameter_one")
    Double minimumNeededAverageIVForKeyOfParameterOne;

    @Column(name = "minimum_needed_iv_for_parameter_two")
    Double minimumNeededIVForParameterTwo;

    @Column(name = "minimum_needed_average_iv_for_key_of_parameter_two")
    Double minimumNeededAverageIVForKeyOfParameterTwo;

    @Column(name = "max_rows_for_influence_parameter_two")
    Integer maxRowsForInfluenceParameterTwo;

    @Column(name = "factor")
    Integer factor;

    @Column(name = "off_set")
    Integer offset;

    @Column(name = "number_wished_rows_for_calc_test_model")
    Integer numberWishedRowsForCalcTestModel;

    @Column(name = "model_quality_level")
    String modelQualityLevel;

    public ScoringSettingsModel(){

    }

    public Long getId(){
        return id;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public LocalDateTime getLastModifiedAt(){
        return lastModifiedAt;
    }

    public UserAccount getUserAccount(){
        return userAccount;
    }

    public String getGoodResult(){
        return goodResult;
    }

    public String getBadResult(){
        return badResult;
    }

    public Double getMinimumNeededIVForParameterOne(){
        return minimumNeededIVForParameterOne;
    }

    public Double getMinimumNeededAverageIVForKeyOfParameterOne(){
        return minimumNeededAverageIVForKeyOfParameterOne;
    }

    public Double getMinimumNeededIVForParameterTwo(){
        return minimumNeededIVForParameterTwo;
    }

    public Double getMinimumNeededAverageIVForKeyOfParameterTwo(){
        return minimumNeededAverageIVForKeyOfParameterTwo;
    }

    public Integer getMaxRowsForInfluenceParameterTwo(){
        return maxRowsForInfluenceParameterTwo;
    }

    public Integer getFactor(){
        return factor;
    }

    public Integer getOffset(){
        return offset;
    }

    public Integer getNumberWishedRowsForCalcTestModel(){
        return numberWishedRowsForCalcTestModel;
    }

    public String getModelQualityLevel(){
        return modelQualityLevel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public void setGoodResult(String goodResult) {
        this.goodResult = goodResult;
    }

    public void setBadResult(String badResult) {
        this.badResult = badResult;
    }

    public void setMinimumNeededAverageIVForKeyOfParameterOne(Double minimumNeededAverageIVForKeyOfParameterOne) {
        this.minimumNeededAverageIVForKeyOfParameterOne = minimumNeededAverageIVForKeyOfParameterOne;
    }

    public void setMinimumNeededIVForParameterOne(Double minimumNeededIVForParameterOne) {
        this.minimumNeededIVForParameterOne = minimumNeededIVForParameterOne;
    }

    public void setMinimumNeededIVForParameterTwo(Double minimumNeededIVForParameterTwo) {
        this.minimumNeededIVForParameterTwo = minimumNeededIVForParameterTwo;
    }

    public void setMinimumNeededAverageIVForKeyOfParameterTwo(Double minimumNeededAverageIVForKeyOfParameterTwo) {
        this.minimumNeededAverageIVForKeyOfParameterTwo = minimumNeededAverageIVForKeyOfParameterTwo;
    }

    public void setMaxRowsForInfluenceParameterTwo(Integer maxRowsForInfluenceParameterTwo) {
        this.maxRowsForInfluenceParameterTwo = maxRowsForInfluenceParameterTwo;
    }

    public void setFactor(Integer factor) {
        this.factor = factor;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public void setNumberWishedRowsForCalcTestModel(Integer numberWishedRowsForCalcTestModel) {
        this.numberWishedRowsForCalcTestModel = numberWishedRowsForCalcTestModel;
    }

    public void setModelQualityLevel(String modelQualityLevel) {
        this.modelQualityLevel = modelQualityLevel;
    }
}
