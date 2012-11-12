package com.jaysan1292.project.c3074.db;

public class UserProvider {
    private static UserProvider ourInstance;

    public static UserProvider getInstance() {
        if (ourInstance == null) ourInstance = new UserProvider();
        return ourInstance;
    }

    private UserProvider() {
    }
}
