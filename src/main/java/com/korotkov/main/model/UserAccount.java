package com.korotkov.main.model;



import com.korotkov.main.enums.UserRoleEnum;
import com.korotkov.main.enums.UserStatus;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_account")
public class UserAccount implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "created_at")
    LocalDateTime createdAt;


    @Column(name = "last_modified_at")
    LocalDateTime lastModifiedAt;


    @Column(name = "email")
    String email;

    @Column(name = "email_confirmed")
    boolean emailConfirmed;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "locked_until")
    LocalDateTime lockedUntil;

    @Column(name = "login", unique = true, nullable = false)
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "temp_password_during_change")
    String tempPasswordDuringChange;

    @Column(name = "password_expired_at")
    LocalDateTime passwordExpiredAt;


    @Column(name = "password_change_required", nullable = false)
    boolean passwordChangeRequired;

    @Transient
    String passwordConfirm;

    @Transient
    String newPassword;

    @Column(name = "dynamic_code")
    String dynamicCode;

    @Column(name = "status")
    String status;

    @Column(name = "super_user")
    boolean superUser;

    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "role_id")
    @ManyToOne(fetch = FetchType.EAGER)
    UserRole role;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_birth")
    LocalDate dateOfBirth;

    @Column(name = "gender")
    String gender;

    @Column(name = "last_payment_at")
    LocalDateTime lastPaymentAt;

    @Column(name = "subscription_expired_at")
    LocalDateTime subscriptionExpiredAt;

    @Column(name = "temp_email_during_change")
    String tempEmailDuringChange;


    public UserAccount(){
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        Set<UserRole> authorities = new HashSet<UserRole>();
        authorities.add(getRole());
        return authorities;
    }

    @Override
    public String getUsername() {return username;}

    @Override
    public boolean isAccountNonExpired(){
        if(getRole().getName().equals(String.valueOf(UserRoleEnum.LEVEL_ONE))){
            return true;
        } else {
            try {
                if (getSubscriptionExpiredAt().isBefore(LocalDateTime.now(ZoneId.of("Europe/Kiev")))){
                    return false;
                } else {
                    return true;
                }
            } catch (NullPointerException nullPointerException){
                return false;
            }
        }
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        if(isPasswordChangeRequired() || getPasswordExpiredAt().isBefore(LocalDateTime.now(ZoneId.of("Europe/Kiev")))){
            return false;
        }
        return true;
    }

    @Override
    public boolean isEnabled(){
        if(getStatus().equals(String.valueOf(UserStatus.INACTIVE))){
            return false;
        }
        return true;
    }



    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }

    public String getDynamicCode() {return dynamicCode;}
    public Long getId(){return id;}
    public LocalDateTime getCreatedAt(){return createdAt;}
    public LocalDateTime getLastModifiedAt(){return lastModifiedAt;}
    public String getEmail(){return email;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public LocalDateTime getLockedUntil(){return lockedUntil;}
    public String getPasswordConfirm(){return passwordConfirm;}
    public LocalDateTime getPasswordExpiredAt() {return passwordExpiredAt;}
    public String getNewPassword() {return newPassword;}

    @Override
    public String getPassword(){return password;}
    public boolean isPasswordChangeRequired(){return passwordChangeRequired;}
    public String getStatus(){return status;}
    public boolean isSuperUser(){return superUser;}
    public UserRole getRole(){return role;}
    public String getTempPasswordDuringChange(){return tempPasswordDuringChange;}
    public LocalDate getDateOfBirth(){return dateOfBirth;}
    public String getGender(){return gender;}
    public LocalDateTime getLastPaymentAt(){return lastPaymentAt;}
    public LocalDateTime getSubscriptionExpiredAt(){return subscriptionExpiredAt;}
    public String getTempEmailDuringChange(){return tempEmailDuringChange;}


    public void setDynamicCode(String dynamicCode) {
        this.dynamicCode = dynamicCode;
    }

    public void setEmailConfirmed(boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
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
    public void setEmail(String email){
        this.email = email;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setLockedUntil(LocalDateTime lockedUntil){
        this.lockedUntil = lockedUntil;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordChangeRequired(boolean passwordChangeRequired) {
        this.passwordChangeRequired = passwordChangeRequired;
    }

    public void setPasswordExpiredAt(LocalDateTime passwordExpiredAt) {
        this.passwordExpiredAt = passwordExpiredAt;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setTempPasswordDuringChange(String tempPasswordDuringChange) {
        this.tempPasswordDuringChange = tempPasswordDuringChange;
    }

    public void setPasswordConfirm(String passwordConfirm){
        this.passwordConfirm = passwordConfirm;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setSuperUser(boolean superUser){this.superUser = superUser;}
    public void setRole(UserRole role){this.role = role;}

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLastPaymentAt(LocalDateTime lastPaymentAt) {
        this.lastPaymentAt = lastPaymentAt;
    }

    public void setSubscriptionExpiredAt(LocalDateTime subscriptionExpiredAt){
        this.subscriptionExpiredAt = subscriptionExpiredAt;
    }

    public void setTempEmailDuringChange(String tempEmailDuringChange) {
        this.tempEmailDuringChange = tempEmailDuringChange;
    }

}
