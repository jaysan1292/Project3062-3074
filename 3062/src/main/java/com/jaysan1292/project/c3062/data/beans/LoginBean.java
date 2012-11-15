package com.jaysan1292.project.c3062.data.beans;

import com.jaysan1292.project.common.data.User;

/** @author Jason Recillo */
public class LoginBean {
    private User user;
    private boolean loggedIn;

    public User getUser() {
        return user;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
