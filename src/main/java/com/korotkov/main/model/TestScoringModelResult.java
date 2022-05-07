package com.korotkov.main.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "test_scoring_model_results")
public class TestScoringModelResult {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "last_modified_at")
    LocalDateTime lastModifiedAt;

    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "test_scoring_model_id")
    @ManyToOne(fetch = FetchType.EAGER)
    TestScoringModel testScoringModel;

    @Column(name = "score")
    String score;

    @Column(name = "count_total_items")
    Integer countTotalItems;

    @Column(name = "count_good_items")
    Integer countGoodItems;

    @Column(name = "count_bad_items")
    Integer countBadItems;

    @Column(name = "bad_rate")
    Double badRate;

    @Column(name = "cum_total_items_count")
    Integer cumTotalItemsCount;

    @Column(name = "cum_total_items_percent")
    Double cumTotalItemsPercent;

    @Column(name = "cum_good_items_count")
    Integer cumGoodItemsCount;

    @Column(name = "cum_good_items_percent")
    Double cumGoodItemsPercent;

    @Column(name = "cum_bad_items_count")
    Integer cumBadItemsCount;

    @Column(name = "cum_bad_items_percent")
    Double cumBadItemsPercent;

    @Column(name = "gini_bi_bi_one")
    Double giniBiBiOne;

    @Column(name = "gini_gi_gi_one")
    Double giniGiGiOne;

    @Column(name = "gini_result")
    Double giniResult;

    @Column(name = "order_number_row")
    Integer orderNumberRow;

    @Column(name = "total")
    boolean total;

    public TestScoringModelResult(){

    }

    public Long getId(){
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastModifiedAt(){
        return lastModifiedAt;
    }

    public TestScoringModel getTestScoringModel() {
        return testScoringModel;
    }

    public String getScore() {
        return score;
    }

    public Integer getCountTotalItems(){
        return countTotalItems;
    }

    public Integer getCountGoodItems(){
        return countGoodItems;
    }

    public Integer getCountBadItems(){
        return countBadItems;
    }

    public Double getBadRate(){
        return badRate;
    }

    public Integer getCumTotalItemsCount(){
        return cumTotalItemsCount;
    }

    public Double getCumTotalItemsPercent(){
        return cumTotalItemsPercent;
    }

    public Integer getCumGoodItemsCount(){
        return cumGoodItemsCount;
    }

    public Double getCumGoodItemsPercent(){
        return cumGoodItemsPercent;
    }

    public Integer getCumBadItemsCount(){
        return cumBadItemsCount;
    }

    public Double getCumBadItemsPercent(){
        return cumBadItemsPercent;
    }

    public Double getGiniBiBiOne(){
        return giniBiBiOne;
    }

    public Double getGiniGiGiOne(){
        return giniGiGiOne;
    }

    public Double getGiniResult(){
        return giniResult;
    }

    public Integer getOrderNumberRow() {
        return orderNumberRow;
    }

    public boolean isTotal() {
        return total;
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

    public void setTestScoringModel(TestScoringModel testScoringModel){
        this.testScoringModel = testScoringModel;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setCountTotalItems(Integer countTotalItems) {
        this.countTotalItems = countTotalItems;
    }

    public void setCountGoodItems(Integer countGoodItems) {
        this.countGoodItems = countGoodItems;
    }

    public void setCountBadItems(Integer countBadItems) {
        this.countBadItems = countBadItems;
    }

    public void setBadRate(Double badRate) {
        this.badRate = badRate;
    }

    public void setCumTotalItemsCount(Integer cumTotalItemsCount) {
        this.cumTotalItemsCount = cumTotalItemsCount;
    }

    public void setCumTotalItemsPercent(Double cumTotalItemsPercent) {
        this.cumTotalItemsPercent = cumTotalItemsPercent;
    }

    public void setCumGoodItemsCount(Integer cumGoodItemsCount) {
        this.cumGoodItemsCount = cumGoodItemsCount;
    }

    public void setCumGoodItemsPercent(Double cumGoodItemsPercent) {
        this.cumGoodItemsPercent = cumGoodItemsPercent;
    }

    public void setCumBadItemsCount(Integer cumBadItemsCount) {
        this.cumBadItemsCount = cumBadItemsCount;
    }

    public void setCumBadItemsPercent(Double cumBadItemsPercent) {
        this.cumBadItemsPercent = cumBadItemsPercent;
    }

    public void setGiniBiBiOne(Double giniBiBiOne) {
        this.giniBiBiOne = giniBiBiOne;
    }

    public void setGiniGiGiOne(Double giniGiGiOne) {
        this.giniGiGiOne = giniGiGiOne;
    }

    public void setGiniResult(Double giniResult) {
        this.giniResult = giniResult;
    }

    public void setOrderNumberRow(Integer orderNumberRow) {
        this.orderNumberRow = orderNumberRow;
    }

    public void setTotal(boolean total) {
        this.total = total;
    }
}
