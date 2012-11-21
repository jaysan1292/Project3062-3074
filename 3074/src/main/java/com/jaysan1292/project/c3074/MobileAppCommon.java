package com.jaysan1292.project.c3074;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import com.jaysan1292.project.c3074.activity.*;
import com.jaysan1292.project.c3074.db.AppSQLManager;
import com.jaysan1292.project.c3074.db.UserMetaManager;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.User;
import org.apache.log4j.Logger;

public class MobileAppCommon extends Application {
    public static final Logger log = Logger.getLogger("gbc.mc");
    private static MobileAppCommon instance;
    private static AppSQLManager dataManager;
    private static User loggedInUser;

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    public static AppSQLManager getDataManager() {
        return dataManager;
    }

    public static User getLoggedInUser() {
        if (loggedInUser == null) {
            loggedInUser = UserMetaManager.getLoggedInUser();
        }

        return loggedInUser;
    }

    public static void logout() {
        UserMetaManager.setLoggedInUser(User.NOT_LOGGED_IN);
        loggedInUser = null;
    }

    @Override
    public void onCreate() {
        instance = this;
        dataManager = new AppSQLManager(this);
        super.onCreate();
    }

    public static void startPostDetailActivity(Context context, Post post) {
        Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra(context.getPackageName() + ".PostData", post.writeJSON());
        context.startActivity(intent);
    }

    public static void startPostCommentActivity(Context context, Post post) {
        Intent intent = new Intent(context, PostCommentActivity.class);
        intent.putExtra(context.getPackageName() + ".PostData", post.writeJSON());
        context.startActivity(intent);
    }

    public static void startUserProfileActivity(Context context, User user) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        intent.putExtra(context.getPackageName() + ".UserInfo", user.writeJSON());
        context.startActivity(intent);
    }

    public static void startMainActivity(Context context, boolean clearTop) {
        Intent intent = new Intent(context, MainActivity.class);
        if (clearTop) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        context.startActivity(intent);
    }

    public static void startLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void startNewPostActivity(Context context) {
        Intent intent = new Intent(context, NewPostActivity.class);
        context.startActivity(intent);
    }
}
