package com.hooptap.sdkbrandclub.Models;

/**
 * Created by carloscarrasco on 12/2/16.
 */

import com.hooptap.brandclub.model.UserUpdateModel;

import java.math.BigDecimal;

/**
 * Created by root on 21/12/15.
 */
public class HooptapUpdateModel extends UserUpdateModel {

    public transient String username;
    public transient String surname;
    public transient String email;
    public transient String birth;
    public transient String postalCode;
    public transient String phoneNumber;
    public transient String image;
    public transient BigDecimal gender;

    public String getUsername() {
        username = super.getUsername();
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        super.setUsername(username);
    }

    public String getSurname() {
        surname = super.getSurname();
        return super.getSurname();
    }

    public void setSurname(String surname) {
        this.surname = surname;
        super.setSurname(surname);
    }

    public String getEmail() {
        email = super.getEmail();
        return super.getEmail();
    }

    public void setEmail(String email) {
        this.email = email;
        super.setEmail(email);
    }

    public String getBirth() {
        birth = super.getBirth();
        return super.getBirth();
    }

    public void setBirth(String birth) {
        this.birth = birth;
        super.setBirth(birth);
    }

    public String getPostalCode() {
        postalCode = super.getPostalCode();
        return super.getPostalCode();
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        super.setPostalCode(postalCode);
    }

    public String getPhoneNumber() {
        phoneNumber = super.getPhoneNumber();
        return super.getPhoneNumber();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        super.setPhoneNumber(phoneNumber);
    }

    public String getImage() {
        image = super.getImage();
        return super.getImage();
    }

    public void setImage(String image) {
        this.image = image;
        super.setImage(image);
    }


    public BigDecimal getGender() {
        gender = super.getGender();
        return super.getGender();
    }

    public void setGender(BigDecimal gender) {
        this.gender = gender;
        super.setGender(gender);
    }

}