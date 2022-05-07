package com.korotkov.main.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "scoring_model")
public class ScoringModel {
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

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    @Column(name = "status")
    String status;

    public ScoringModel(){

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

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt){
        this.lastModifiedAt = lastModifiedAt;
    }

    public void setUserAccount(UserAccount userAccount){
        this.userAccount = userAccount;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


