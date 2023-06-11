package com.online.reservationsystem.model;

import java.io.InputStream;

public class AuthInformation {

    private final int userId;
    private final String username , email , gender , firstName ,
            lastName,phoneCode ,phone , createdDate;

    public AuthInformation(int userId, String username, String email, String gender,
                           String firstName, String lastName, String phoneCode, String phone,
                           String createdDate) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneCode = phoneCode;
        this.phone = phone;
        this.createdDate = createdDate;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getCreatedDate() {
        return createdDate;
    }
}
