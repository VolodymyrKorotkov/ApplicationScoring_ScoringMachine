package com.korotkov.main.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "scoring_model_parameters")
public class ScoringModelParameter {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "last_modified_at")
    LocalDateTime lastModifiedAt;

    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "model_id")
    @ManyToOne(fetch = FetchType.EAGER)
    ScoringModel scoringModel;

    @Column(name = "title")
    String title;

    @Column(name = "name_parameter")
    String nameParameter;

    @Column(name = "good_count")
    Integer goodCount;

    @Column(name = "bad_count")
    Integer badCount;

    @Column(name = "good_rate")
    Double goodRate;

    @Column(name = "bad_rate")
    Double badRate;

    @Column(name = "total_count")
    Integer totalCount;

    @Column(name = "good_population_percent")
    Double goodPopulationPercent;

    @Column(name = "bad_population_percent")
    Double badPopulationPercent;

    @Column(name = "total_population_percent")
    Double totalPopulationPercent;

    @Column(name = "gi_g")
    Double giG;

    @Column(name = "bi_b")
    Double biB;

    @Column(name = "pg_pb")
    Double pgPb;

    @Column(name = "woe")
    Double woe;

    @Column(name = "iv")
    Double iv;

    @Column(name = "score")
    Integer score;

    @Column(name = "type_parameter")
    String typeParameter;

    @Column(name = "recommended")
    boolean recommended;

    @Column(name = "total")
    boolean total;

    public ScoringModelParameter(){

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

    public ScoringModel getScoringModel(){
        return scoringModel;
    }

    public String getTitle(){
        return title;
    }

    public String getNameParameter(){
        return nameParameter;
    }

    public Integer getGoodCount(){
        return goodCount;
    }

    public Integer getBadCount(){
        return badCount;
    }

    public Double getGoodRate(){
        return goodRate;
    }

    public Double getBadRate(){
        return badRate;
    }

    public Integer getTotalCount(){
        return totalCount;
    }

    public Double getGoodPopulationPercent(){
        return goodPopulationPercent;
    }

    public Double getBadPopulationPercent(){
        return badPopulationPercent;
    }

    public Double getTotalPopulationPercent(){
        return totalPopulationPercent;
    }

    public Double getGiG(){
        return giG;
    }

    public Double getBiB(){
        return biB;
    }

    public Double getPgPb(){
        return pgPb;
    }

    public Double getWoe(){
        return woe;
    }

    public Double getIv(){
        return iv;
    }

    public Integer getScore(){
        return score;
    }

    public String getTypeParameter(){return typeParameter;}

    public boolean isRecommended() {
        return recommended;
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

    public void setScoringModel(ScoringModel scoringModel) {
        this.scoringModel = scoringModel;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNameParameter(String nameParameter) {
        this.nameParameter = nameParameter;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public void setBadCount(Integer badCount) {
        this.badCount = badCount;
    }

    public void setGoodRate(Double goodRate) {
        this.goodRate = goodRate;
    }

    public void setBadRate(Double badRate) {
        this.badRate = badRate;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public void setGoodPopulationPercent(Double goodPopulationPercent) {
        this.goodPopulationPercent = goodPopulationPercent;
    }

    public void setBadPopulationPercent(Double badPopulationPercent) {
        this.badPopulationPercent = badPopulationPercent;
    }

    public void setTotalPopulationPercent(Double totalPopulationPercent) {
        this.totalPopulationPercent = totalPopulationPercent;
    }

    public void setGiG(Double giG) {
        this.giG = giG;
    }

    public void setBiB(Double biB) {
        this.biB = biB;
    }

    public void setPgPb(Double pgPb) {
        this.pgPb = pgPb;
    }

    public void setWoe(Double woe) {
        this.woe = woe;
    }

    public void setIv(Double iv) {
        this.iv = iv;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setTypeParameter(String typeParameter){
        this.typeParameter = typeParameter;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    public void setTotal(boolean total) {
        this.total = total;
    }
}
