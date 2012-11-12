package com.jaysan1292.project.c3074.serviceclient;

import com.jaysan1292.project.c3074.MobileAppCommon;
import com.jaysan1292.project.c3074.R;
import com.jaysan1292.project.c3074.db.UserMetaManager;
import com.jaysan1292.project.common.data.Comment;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.util.SortedArrayList;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
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
            InputStream input = MobileAppCommon.getContext().getResources().openRawResource(R.raw.users);
            Set<User> users = User.readJSONSet(User.class, input);
            return (User) CollectionUtils.find(users, new Predicate() {
                public boolean evaluate(Object object) {
                    User usr = (User) object;
                    return usr.getId() == id;
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
