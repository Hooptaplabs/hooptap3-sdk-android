package com.hooptap.sdkbrandclub.Models;

import com.hooptap.sdkbrandclub.model.InputLoginModel;

/**
 * Created by carloscarrasco on 23/2/16.
 */
public class HooptapLogin extends InputLoginModel {
    public transient String login;
    public transient String password ;

    public String getLogin() {
        login = super.getLogin();
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
        super.setLogin(login);
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
