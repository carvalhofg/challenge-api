package com.api.challenge.challengeapi.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm {
    
    private String usrName;
    private String pass;

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public UsernamePasswordAuthenticationToken converter() {
        return new UsernamePasswordAuthenticationToken(usrName, pass);
    }
}

