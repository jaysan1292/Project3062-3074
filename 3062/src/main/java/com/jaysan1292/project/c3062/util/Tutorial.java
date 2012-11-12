package com.jaysan1292.project.c3062.util;

import com.jaysan1292.jdcommon.Range;
import com.jaysan1292.project.common.data.User;

import java.util.ArrayList;

/** @author Jason Recillo */
public class Tutorial {
    private Tutorial() {}

    public static String getUserIds(int count) {
        if (count <= 0) return "";
        StringBuilder sb = new StringBuilder();
        ArrayList<User> users = new ArrayList<User>(count);

        Range<Integer> range = new Range<Integer>(1, PlaceholderContent.Users.size() - 1);
        for (int i = 0; i < count; i++) {
            users.add(PlaceholderContent.getUser(Integer.parseInt(range.getRandomValue().toString())));
        }

        for (User user : users) {
            sb.append(user.getStudentNumber()).append("<br/>");
        }
        return sb.toString().replaceAll("<br/>$", "");
    }
}
