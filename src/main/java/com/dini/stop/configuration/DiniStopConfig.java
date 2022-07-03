package com.dini.stop.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "dini.stop")
public class DiniStopConfig {

    private String jwtsecret;
    private String smsusername;
    private String smspassword;
    private String smsphone;

    public String getJwtsecret() {
        return jwtsecret;
    }

    public void setJwtsecret(String jwtsecret) {
        this.jwtsecret = jwtsecret;
    }

    public String getSmsusername() {
        return smsusername;
    }

    public void setSmsusername(String smsusername) {
        this.smsusername = smsusername;
    }

    public String getSmspassword() {
        return smspassword;
    }

    public void setSmspassword(String smspassword) {
        this.smspassword = smspassword;
    }

    public String getSmsphone() {
        return smsphone;
    }

    public void setSmsphone(String smsphone) {
        this.smsphone = smsphone;
    }

    @Override
    public String toString() {
        return "DiniStopConfig{" +
                "jwtsecret='" + jwtsecret + '\'' +
                ", smsusername='" + smsusername + '\'' +
                ", smspassword='" + smspassword + '\'' +
                ", smsphone='" + smsphone + '\'' +
                '}';
    }
}
