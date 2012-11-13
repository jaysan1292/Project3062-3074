package com.jaysan1292.project.common.security;

import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.util.EncryptionUtils;
import org.apache.commons.lang3.StringUtils;

/** @author Jason Recillo */
public class UserPasswordPair implements Comparable<UserPasswordPair> {
    private User user;
    private String password;

    public UserPasswordPair() {}

    public UserPasswordPair(User user) {
        this.user = user;
        this.password = "(null)";
    }

    public UserPasswordPair(User user, String plainPassword) {
        this.user = user;
        this.password = EncryptionUtils.encryptPassword(plainPassword);
    }

    public User getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPassword(String plainPassword) {
        this.password = EncryptionUtils.encryptPassword(plainPassword);
    }

    public void setPasswordDirect(String encryptedPassword) {
        if (encryptedPassword.length() != 64) {
            throw new IllegalArgumentException("The given password doesn't appear to be encrypted! Input: " + encryptedPassword);
        }

        this.password = encryptedPassword;
    }

    public boolean comparePassword(String plainPassword) {
        return EncryptionUtils.getPasswordEncryptor().checkPassword(plainPassword, this.password);
    }

    public int compareTo(UserPasswordPair o) {
        return user.compareTo(o.user);
    }

    @Override
    public String toString() {
        return "User/Password Pair: " + user.getStudentNumber() + ", " + StringUtils.abbreviate(password, 16);
    }

    public static String encryptPassword(String plainPassword) {
        return EncryptionUtils.encryptPassword(plainPassword);
    }
}
