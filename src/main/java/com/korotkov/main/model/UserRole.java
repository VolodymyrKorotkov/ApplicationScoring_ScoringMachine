package com.korotkov.main.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_role")
public class UserRole implements GrantedAuthority {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "description")
    String description;

    @Column(name = "name", unique = true)
    String name;

    @Column(name = "count_max_saved_models")
    Integer countMaxSavedModels;

    @Column(name = "count_max_saved_tests_for_model")
    Integer countMaxSavedTestsForModel;

    @Column(name = "price_one_month")
    Integer priceOneMonth;

    @Column(name = "price_three_months")
    Integer priceThreeMonths;

    @Column(name = "price_six_months")
    Integer priceSixMonths;

    @Column(name = "price_one_year")
    Integer priceOneYear;


    public UserRole(){}

    public UserRole(Long id){this.id = id;}

    public UserRole(Long id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {return getName();}

    @Override
    public String toString(){
        return name;
    }

    public Long getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }

    public String getName(){return name;}

    public Integer getCountMaxSavedModels(){return countMaxSavedModels;}

    public Integer getCountMaxSavedTestsForModel() {
        return countMaxSavedTestsForModel;
    }

    public Integer getPriceOneMonth() {
        return priceOneMonth;
    }

    public Integer getPriceThreeMonths() {
        return priceThreeMonths;
    }

    public Integer getPriceSixMonths() {
        return priceSixMonths;
    }

    public Integer getPriceOneYear() {
        return priceOneYear;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountMaxSavedModels(Integer countMaxSavedModels) {
        this.countMaxSavedModels = countMaxSavedModels;
    }

    public void setCountMaxSavedTestsForModel(Integer countMaxSavedTestsForModel) {
        this.countMaxSavedTestsForModel = countMaxSavedTestsForModel;
    }

    public void setPriceOneMonth(Integer priceOneMonth) {
        this.priceOneMonth = priceOneMonth;
    }

    public void setPriceThreeMonths(Integer priceThreeMonths) {
        this.priceThreeMonths = priceThreeMonths;
    }

    public void setPriceSixMonths(Integer priceSixMonths) {
        this.priceSixMonths = priceSixMonths;
    }

    public void setPriceOneYear(Integer priceOneYear) {
        this.priceOneYear = priceOneYear;
    }
}
