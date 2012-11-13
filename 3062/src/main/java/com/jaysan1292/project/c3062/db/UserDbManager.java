package com.jaysan1292.project.c3062.db;

import com.jaysan1292.project.c3062.util.PlaceholderContent;
import com.jaysan1292.project.common.data.Program;
import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.data.beans.UserBean;
import com.jaysan1292.project.common.security.UserPasswordPair;

import java.util.ArrayList;

/** @author Jason Recillo */
@Deprecated
public class UserDbManager {
    private static UserDbManager sharedInstance;

    public static synchronized UserDbManager getSharedInstance() {
        if (sharedInstance == null) sharedInstance = new UserDbManager();
        return sharedInstance;
    }

    private UserDbManager() {}

    @Deprecated
    public synchronized User getUser(long id) {
        return PlaceholderContent.getUser(id);
    }

    @Deprecated
    public synchronized UserPasswordPair getPasswordForUser(long id) {
        return getPasswordForUser(getUser(id));
    }

    @Deprecated
    public synchronized UserPasswordPair getPasswordForUser(User user) {
        return PlaceholderContent.getPasswordPair(user);
    }

    @Deprecated
    public synchronized User getUserByStudentId(String studentId) {
        return PlaceholderContent.getUserByStudentId(studentId);
    }

    @Deprecated
    public synchronized UserBean getUserBean(long id) {
        UserBean user = new UserBean();
        user.setUser(getUser(id));
        user.setPassword(getPasswordForUser(id));

        return user;
    }

    @Deprecated
    public synchronized ArrayList<User> getUsersInProgram(Program program) {
        ArrayList<User> out = new ArrayList<User>();
        for (User user : PlaceholderContent.Users) {
            if (program.equals(user.getProgram())) out.add(user);
        }
        return out;
    }
}
