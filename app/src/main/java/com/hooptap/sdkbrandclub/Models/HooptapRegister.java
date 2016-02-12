package com.hooptap.sdkbrandclub.Models;

/**
 * Created by carloscarrasco on 12/2/16.
 */

import com.hooptap.brandclub.model.UserModel;

import java.math.BigDecimal;

/**
 * Created by root on 21/12/15.
 */
public class HooptapRegister extends UserModel {

    public transient String username;
    public transient String surname;
    public transient String email;
    public transient String birth;
    public transient String postal_code;
    public transient String phone_number;
    public transient BigDecimal gender;
    public transient String password;

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
        postal_code = super.getPostalCode();
        return super.getPostalCode();
    }

    public void setPostalCode(String postalCode) {
        this.postal_code = postalCode;
        super.setPostalCode(postalCode);
    }

    public String getPhoneNumber() {
        phone_number = super.getPhoneNumber();
        return super.getPhoneNumber();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phone_number = phoneNumber;
        super.setPhoneNumber(phoneNumber);
    }

    public BigDecimal getGender() {
        gender = super.getGender();
        return super.getGender();
    }

    public void setGender(BigDecimal gender) {
        this.gender = gender;
        super.setGender(gender);
    }

    public String getPassword() {
        password = super.getPassword();
        return super.getPassword();
    }

    public void setPassword(String password) {
        this.password = password;
        super.setPassword(password);
    }

}