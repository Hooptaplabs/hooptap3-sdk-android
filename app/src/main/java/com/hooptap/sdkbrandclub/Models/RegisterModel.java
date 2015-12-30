package com.hooptap.sdkbrandclub.Models;

import com.hooptap.brandclub.model.UserModel;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by root on 21/12/15.
 */
public class RegisterModel extends UserModel {


    public String get_userName() {
        return getUsername();
    }

    public void set_userName(String userName) {
        setUsername(userName);
    }

    public String get_surname() {
        return getSurname();
    }

    public void set_surname(String surname) {
        setSurname(surname);
    }

    public String get_email() {
        return getEmail();
    }

    public void set_email(String email) {
        setEmail(email);
    }

    public String get_birth() {
        return getBirth();
    }

    public void set_birth(String birth) {
        setBirth(birth);
    }

    public String get_postalCode() {
        return getPostalCode();
    }

    public void set_postalCode(String postalCode) {
        setPostalCode(postalCode);
    }

    public String get_phoneNumber() {
        return getPhoneNumber();
    }

    public void set_phoneNumber(String phoneNumber) {
        setPhoneNumber(phoneNumber);
    }

    public BigDecimal get_gender() {
        return getGender();
    }

    public void set_gender(BigDecimal gender) {
        setGender(gender);
    }

    public String get_password() {
        return getPassword();
    }

    public void set_password(String password) {
        setPassword(password);
    }

    public ArrayList<String> getParams() {
        ArrayList<String> lista = new ArrayList<>();
        lista.add("UserName");
        lista.add("Surname");
        lista.add("Email");
        lista.add("Birth");
        lista.add("Postal Code");
        lista.add("Phone Number");
        lista.add("Gender");
        lista.add("Password");
        return  lista;
    }

}
