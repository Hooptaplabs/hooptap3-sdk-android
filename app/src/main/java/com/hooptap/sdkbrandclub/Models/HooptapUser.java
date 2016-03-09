package com.hooptap.sdkbrandclub.Models;

/**
 * Created by carloscarrasco on 9/2/16.
 */
public class HooptapUser {
    private String username;
    private String surname;
    private String birth;
    private String email;
    private String phoneNumber;
    private String image;
    private String postalCode;
    private int gender;
    private String externalId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPhone_number() {
        return phoneNumber;
    }

    public void setPhone_number(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getPostal_code() {
        return postalCode;
    }

    public void setPostal_code(String postalCode) {
        this.postalCode = postalCode;
    }
}
