package com.jaysan1292.project.c3074.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.jaysan1292.project.c3074.MobileAppCommon;
import com.jaysan1292.project.c3074.R;
import com.jaysan1292.project.c3074.db.UserMetaManager;
import com.jaysan1292.project.c3074.serviceclient.Client;
import com.jaysan1292.project.c3074.tween.ImageViewAccessor;
import com.jaysan1292.project.c3074.utils.Log4jConfigurator;
import com.jaysan1292.project.common.data.User;

/** @author Jason Recillo */
public class LoginActivity extends Activity implements View.OnClickListener {
    private final TweenManager tweenManager = new TweenManager();
    private boolean _animationRunning = true;

    public void onCreate(Bundle savedInstanceState) {
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
        findViewById(R.id.login_logo).setScaleX(1.1f);
        findViewById(R.id.login_logo).setScaleY(1.1f);

        float amount = findViewById(R.id.login_logo).getScaleX() * 1.05f;
        Tween.registerAccessor(ImageView.class, new ImageViewAccessor());
        Tween.to(findViewById(R.id.login_logo), ImageViewAccessor.SCALE, 1.0f)
             .target(amount, amount)
             .ease(TweenEquations.easeInOutSine)
             .repeatYoyo(Tween.INFINITY, 0f)
             .start(tweenManager);
    }

    @SuppressWarnings("ObjectAllocationInLoop")
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            public void run() {
                MobileAppCommon.log.debug("Starting tween loop");
                while (_animationRunning) {
                    if (lastMillis > 0) {
                        long currentMillis = System.currentTimeMillis();
                        final float delta = (currentMillis - lastMillis) / 1000f;

                        runOnUiThread(new Runnable() {
                            public void run() {
                                tweenManager.update(delta);
                            }
                        });

                        lastMillis = currentMillis;
                    } else {
                        lastMillis = System.currentTimeMillis();
                    }

                    try {
                        Thread.sleep(1000 / 60);
                    } catch (InterruptedException ignored) {}
                }
                MobileAppCommon.log.debug("Ending tween loop");
            }

            private long lastMillis = -1;
        }).start();
    }

    protected void onPause() {
        super.onPause();
        _animationRunning = false;
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
