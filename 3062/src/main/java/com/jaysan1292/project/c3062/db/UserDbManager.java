package com.jaysan1292.project.c3062.db;

import com.jaysan1292.project.c3062.util.PlaceholderContent;
import com.jaysan1292.project.common.data.Program;
import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.data.beans.UserBean;
import com.jaysan1292.project.common.security.UserPasswordPair;

import java.util.ArrayList;

/** @author Jason Recillo */
public class UserDbManager {
    private static UserDbManager sharedInstance;

    public static synchronized UserDbManager getSharedInstance() {
        if (sharedInstance == null) sharedInstance = new UserDbManager();
        return sharedInstance;
    }

    private UserDbManager() {}

    public synchronized User getUser(long id) {
        return PlaceholderContent.getUser(id);
    }

    public synchronized UserPasswordPair getPasswordForUser(long id) {
        return getPasswordForUser(getUser(id));
    }

    public synchronized UserPasswordPair getPasswordForUser(User user) {
        return PlaceholderContent.getPasswordPair(user);
    }

    public synchronized User getUserByStudentId(String studentId) {
        return PlaceholderContent.getUserByStudentId(studentId);
    }

    public synchronized UserBean getUserBean(long id) {
        UserBean user = new UserBean();
        user.setUser(getUser(id));
        user.setPassword(getPasswordForUser(id));

        return user;
    }

    public synchronized ArrayList<User> getUsersInProgram(Program program) {
        ArrayList<User> out = new ArrayList<User>();
        for (User user : PlaceholderContent.Users) {
            if (program.equals(user.getProgram())) out.add(user);
        }
        return out;
    }
}
