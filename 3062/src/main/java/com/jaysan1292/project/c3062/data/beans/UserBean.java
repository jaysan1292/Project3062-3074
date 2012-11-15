package com.jaysan1292.project.c3062.data.beans;

import com.jaysan1292.project.common.data.Program;
import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.security.UserPasswordPair;

//TODO: Combine this into the regular User class as this is probably confusing and unnecessary

/** @author Jason Recillo */
public class UserBean {
    private User user;
    private UserPasswordPair password;

    public UserBean() {
        this.user = new User();
        this.password = new UserPasswordPair(user);
    }

    public UserBean(User user, UserPasswordPair password) {
        this.user = user;
        this.password = password;
    }

    public UserBean(User user) {
        this(user, new UserPasswordPair());
        this.password.setUser(user);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return user.getId();
    }

    public String getFullName() {
        return user.getFullName();
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    public String getLastName() {
        return user.getLastName();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getStudentNumber() {
        return user.getStudentNumber();
    }

    public Program getProgram() {
        return user.getProgram();
    }

    public void setId(long id) {
        user.setId(id);
    }

    public void setFirstName(String fn) {
        user.setFirstName(fn);
    }

    public void setLastName(String ln) {
        user.setLastName(ln);
    }

    public void setEmail(String em) {
        user.setEmail(em);
    }

    public void setStudentNumber(String studentNumber) {
        user.setStudentNumber(studentNumber);
    }

    public void setProgram(Program p) {
        user.setProgram(p);
    }

    public UserPasswordPair getPassword() {
        return password;
    }

    public String getPasswordString() {
        return password.getPassword();
    }

    public void setPassword(String plainPassword) {
        if (this.password == null) this.password = new UserPasswordPair(this.user);
        this.password.setPassword(plainPassword);
    }

    public void setPassword(UserPasswordPair password) {
        this.password = password;
    }
}
