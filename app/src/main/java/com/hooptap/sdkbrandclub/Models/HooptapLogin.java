package com.hooptap.sdkbrandclub.Models;

import com.google.gson.annotations.SerializedName;
import com.hooptap.brandclub.model.InputLoginModel;

/**
 * Created by carloscarrasco on 23/2/16.
 */
public class HooptapLogin extends InputLoginModel {
    public transient String email;
    public transient String password ;

    public String getEmail() {
        email = super.getEmail();
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        super.setEmail(email);
    }

    public String getPassword() {
        password = super.getPassword();
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        super.setPassword(password);
    }
}
