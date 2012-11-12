package com.jaysan1292.project.c3074.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.jaysan1292.project.c3074.MobileAppCommon;
import com.jaysan1292.project.c3074.R;
import com.jaysan1292.project.c3074.db.UserMetaManager;
import com.jaysan1292.project.c3074.serviceclient.Client;
import com.jaysan1292.project.c3074.utils.Log4jConfigurator;
import com.jaysan1292.project.common.data.User;

/** @author Jason Recillo */
public class LoginActivity extends Activity implements View.OnClickListener {
    public void onCreate(Bundle savedInstanceState) {
        //TODO: sexy splash animation thing with Java Universal Tween Engine
        super.onCreate(savedInstanceState);
        Log4jConfigurator.configure();

        // Check if the user is logged in, and if so,
        // send them to the main activity
        MobileAppCommon.log.info("Checking user login status...");
        if (!UserMetaManager.getLoggedInUser().equals(User.NOT_LOGGED_IN)) {
            MobileAppCommon.log.info("User is logged in; sending to main activity.");
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return;
        }

        MobileAppCommon.log.info("User is not logged in, show login screen.");

        setContentView(R.layout.login);
        setTitle(R.string.act_login);
        findViewById(R.id.login_sign_in_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MobileAppCommon.log.debug("Sign in button clicked!");
        String username = ((EditText) findViewById(R.id.login_student_id)).getText().toString();
        String password = ((EditText) findViewById(R.id.login_password)).getText().toString();

        User user = Client.authenticateUser(username, password);
        if (user != User.NOT_LOGGED_IN) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            //TODO: Tell the user there was an error
        }
    }
}
