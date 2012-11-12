package com.jaysan1292.project.c3074.db;

import android.content.SharedPreferences;
import com.jaysan1292.project.c3074.MobileAppCommon;
import com.jaysan1292.project.common.data.User;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Date: 03/11/12
 * Time: 9:41 PM
 *
 * @author Jason Recillo
 */
public class UserMetaManager {
    private static final String PREF_NAME = "user_meta";
    public static final String META_USER_ID = "user_id";
    public static final String META_USER_JSON = "user_json";

    private UserMetaManager() {}

    public static Map<String, ?> getUserMeta() {
        return getSharedPreferences().getAll();
    }

    public static String getMetaValue(String key) {
        return String.valueOf(getUserMeta().get(key));
    }

    public static void setMetaValue(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        {
            editor.putBoolean(key, value);
        }
        editor.commit();
    }

    public static void setMetaValue(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        {
            editor.putString(key, value);
        }
        editor.commit();
    }

    public static void setMetaValue(String key, float value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        {
            editor.putFloat(key, value);
        }
        editor.commit();
    }

    public static void setMetaValue(String key, int value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        {
            editor.putInt(key, value);
        }
        editor.commit();
    }

    public static void setMetaValue(String key, long value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        {
            editor.putLong(key, value);
        }
        editor.commit();
    }

    public static void setMetaValue(String key, Set<String> value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        {
            editor.putStringSet(key, value);
        }
        editor.commit();
    }

    public static User getLoggedInUser() {
        MobileAppCommon.log.info("Retrieving the currently logged on user.");
        String userJson = getMetaValue(META_USER_JSON);

        if (userJson.equals("null")) return User.NOT_LOGGED_IN;

        try {
            User user = new User().readJSON(userJson);
            MobileAppCommon.log.info(user.getFullName() + " is currently logged in.");
            return user;
        } catch (IOException e) {
            MobileAppCommon.log.error(e.getMessage(), e);
            return User.NOT_LOGGED_IN;
        }
    }

    public static void setLoggedInUser(User user) {
        MobileAppCommon.log.info("Saving '" + user.getFullName() + "' as the currently logged on user.");
        setMetaValue(META_USER_ID, user.getId());
        setMetaValue(META_USER_JSON, user.toString());
    }

    private static SharedPreferences getSharedPreferences() {
        return MobileAppCommon.getContext().getSharedPreferences(PREF_NAME, 0);
    }
}
