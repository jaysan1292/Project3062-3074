package com.jaysan1292.project.c3074.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.jaysan1292.project.c3074.MobileAppCommon;
import com.jaysan1292.project.c3074.R;
import com.jaysan1292.project.c3074.db.CommentProvider;
import com.jaysan1292.project.c3074.utils.CommentListAdapter;
import com.jaysan1292.project.common.data.Comment;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class PostCommentActivity extends Activity implements View.OnClickListener {
    private Post parentPost;
    private ArrayList<Comment> postComments;
    private ListView _commentListView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.post_comment_view);
        MobileAppCommon.log.debug("PostCommentActivity");

        getActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            parentPost = new Post().readJSON(getIntent().getStringExtra(getPackageName() + ".PostData"));
        } catch (IOException e) {
            parentPost = new Post();
            parentPost.setPostContent("Sorry, there was a problem loading the post.");
        }

        postComments = CommentProvider.getInstance().getComments(parentPost.getId());

        Collections.sort(postComments);

        _commentListView = (ListView) findViewById(android.R.id.list);
        _commentListView.setAdapter(new CommentListAdapter(PostCommentActivity.this, postComments));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.profile_image):
                User user = (User) v.getTag(R.id.comment_author);
                MobileAppCommon.log.debug("User ID " + user.getId() + " clicked");
                MobileAppCommon.startUserProfileActivity(PostCommentActivity.this, user);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_rev, R.anim.slide_out_rev);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
