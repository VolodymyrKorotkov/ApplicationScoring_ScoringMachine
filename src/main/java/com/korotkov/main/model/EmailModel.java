package com.korotkov.main.model;

public class EmailModel {

    private String name;
    private String email;
    private String password;
    private String message;

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "EmailModel{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
