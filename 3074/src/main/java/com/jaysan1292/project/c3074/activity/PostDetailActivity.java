package com.jaysan1292.project.c3074.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.jaysan1292.project.c3074.MobileAppCommon;
import com.jaysan1292.project.c3074.R;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.util.DateUtils;

import java.io.IOException;

public class PostDetailActivity extends Activity implements View.OnClickListener {
    private Post post;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.post_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            post = new Post().readJSON(getIntent().getStringExtra(getPackageName() + ".PostData"));
        } catch (IOException e) {
            post = new Post();
            post.setPostContent("Sorry, there was a problem loading the post.");
        }

        ImageView profile = (ImageView) findViewById(R.id.pd_profile_picture);
        TextView postAuthor = (TextView) findViewById(R.id.pd_user_fullname);
        TextView authorProgram = (TextView) findViewById(R.id.pd_user_program);
        TextView postTime = (TextView) findViewById(R.id.pd_post_time);
        TextView postContent = (TextView) findViewById(R.id.pd_post_content);

        profile.setImageResource(R.drawable.user);
        postAuthor.setText(post.getPostAuthor().getFullName());
        authorProgram.setText(post.getPostAuthor().getProgram().toString());
        postTime.setText(DateUtils.getRelativeDateString(post.getPostDate()));
        postContent.setText(post.getPostContent());

        authorProgram.setSelected(true);

        profile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MobileAppCommon.startUserProfileActivity(PostDetailActivity.this, post.getPostAuthor());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_rev, R.anim.slide_out_rev);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pd_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_rev, R.anim.slide_out_rev);
                return true;
            case R.id.menu_pd_new_comment:
                MobileAppCommon.log.debug("new comment button clicked");
                MobileAppCommon.startPostCommentActivity(this, post);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
