package com.jaysan1292.project.c3062.util;

import com.jaysan1292.jdcommon.Range;
import com.jaysan1292.project.c3062.db.UserManager;
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

        Range<Integer> range = new Range<Integer>(1, UserManager.getSharedInstance().getCount() - 1);
        for (int i = 0; i < count; i++) {
            try {
                users.add(UserManager.getSharedInstance().get(Integer.parseInt(range.getRandomValue().toString())));
            } catch (SQLException ignored) {}
        }

        for (User user : users) {
            sb.append(user.getStudentNumber()).append("<br/>");
        }
        return sb.toString().replaceAll("<br/>$", "");
    }
}
