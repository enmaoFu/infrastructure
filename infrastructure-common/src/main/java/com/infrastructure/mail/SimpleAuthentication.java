package com.infrastructure.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 简易身份验证
 *
 * @author tyq
 * @data 2011/11
 */
public class SimpleAuthentication extends Authenticator {

    private String username;

    private String password;

    public SimpleAuthentication(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
