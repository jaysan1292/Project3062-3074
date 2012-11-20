package com.jaysan1292.project.c3074.serviceclient;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.jaysan1292.project.c3074.MobileAppCommon;
import com.jaysan1292.project.c3074.R;
import com.jaysan1292.project.c3074.db.UserMetaManager;
import com.jaysan1292.project.common.data.Comment;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.util.SortedArrayList;
import org.intellij.lang.annotations.Language;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/** @author Jason Recillo */
public class Client {
    private Client() {}

    public static User authenticateUser(String username, String password) {
        //TODO: Actual authentication
        MobileAppCommon.log.debug("Given username: " + username + ", Given password: " + password);

        // For now, just authenticate them
        @Language("JSON") String userJson = "{\"id\":0,\"firstName\":\"Jason\",\"lastName\":\"Recillo\",\"email\":\"jaysan1292@example.com\",\"studentNumber\":\"100726948\",\"program\":{\"programCode\":\"T127\",\"programName\":\"Computer Programmer Analyst\",\"id\":0}}";

        try {
            User user = new User().readJSON(userJson);
            UserMetaManager.setLoggedInUser(user);
            return user;
        } catch (IOException e) {
            MobileAppCommon.log.error("User was not recognized.", e);
            return User.NOT_LOGGED_IN;
        }
    }

    public static User findUser(final long id) {
        // For now, just retrieve this from res/raw/users.json
        try {
            InputStream in = MobileAppCommon.getContext().getResources().openRawResource(R.raw.users);
            Set<User> users = User.readJSONSet(User.class, in);
            return Iterables.find(users, new Predicate<User>() {
                public boolean apply(User input) {
                    return input.getId() == id;
                }
            });
        } catch (IOException e) {
            MobileAppCommon.log.error("Error reading user.json", e);
            return null;
        }
    }

    public static SortedArrayList<Comment> getComments(Post post) {
        return null; //TODO: Get comments
    }
}
