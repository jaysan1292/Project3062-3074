package com.jaysan1292.project.c3062.util;

import com.jaysan1292.jdcommon.range.IntegerRange;
import com.jaysan1292.project.c3062.db.UserDbManager;
import com.jaysan1292.project.common.data.User;

import java.sql.SQLException;
import java.util.ArrayList;

/** @author Jason Recillo */
public class Tutorial {
    private Tutorial() {}

    public static String getUserIds(int count) {
        if (count <= 0) return "";
        StringBuilder sb = new StringBuilder();
        ArrayList<User> users = new ArrayList<User>(count);

        IntegerRange range = new IntegerRange(1, UserDbManager.getSharedInstance().getCount() - 1);
        for (int i = 0; i < count; i++) {
            try {
                users.add(UserDbManager.getSharedInstance().get(Integer.parseInt(range.getRandomValue().toString())));
            } catch (SQLException ignored) {}
        }

        for (User user : users) {
            sb.append(user.getStudentNumber()).append("<br/>");
        }
        return sb.toString().replaceAll("<br/>$", "");
    }
}
