package com.jaysan1292.project.common.data.beans;

import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.security.UserPasswordPair;

/** @author Jason Recillo */
public class UserBean {
    private User user;
    private UserPasswordPair password;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserPasswordPair getPassword() {
        return password;
    }

    public void setPassword(UserPasswordPair password) {
        this.password = password;
    }
}
