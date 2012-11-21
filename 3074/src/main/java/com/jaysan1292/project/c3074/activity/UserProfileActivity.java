package com.jaysan1292.project.c3074.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.jaysan1292.project.c3074.MobileAppCommon;
import com.jaysan1292.project.c3074.R;
import com.jaysan1292.project.c3074.db.PostProvider;
import com.jaysan1292.project.c3074.utils.Disposable;
import com.jaysan1292.project.c3074.utils.UserPostListAdapter;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.User;

import java.io.IOException;
import java.util.ArrayList;

public class UserProfileActivity extends Activity implements AdapterView.OnItemClickListener, Disposable {
    private ProgressDialog progressDialog;
    private ListView postListView;
    private User user;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.user_profile);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            user = new User().readJSON(getIntent().getStringExtra(getPackageName() + ".UserInfo"));
        } catch (IOException e) {
            user = new User();
        }

        ImageView profileImage = (ImageView) findViewById(R.id.up_profile_picture);
        TextView profileFullName = (TextView) findViewById(R.id.up_user_fullname);
        TextView profileProgram = (TextView) findViewById(R.id.up_user_program);

        profileImage.setImageResource(R.drawable.user);
        profileFullName.setText(user.getFullName());
        profileProgram.setText(user.getProgram().toString());

        profileProgram.setSelected(true);

        new LoadPostTask().execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_rev, R.anim.slide_out_rev);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MobileAppCommon.log.debug("Post ID " + view.getTag(R.id.post_id) + " clicked");

        long postId = (Long) view.getTag(R.id.post_id);

        Post post = PostProvider.getInstance().getPost(postId);

        MobileAppCommon.startPostDetailActivity(UserProfileActivity.this, post);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.up_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_rev, R.anim.slide_out_rev);
                return true;
            case R.id.menu_up_email:
                MobileAppCommon.log.info("Send email to " + user.getEmail());
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("message/rfc822");
                intent.setData(Uri.parse("mailto:" + user.getEmail()));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                intent.putExtra(Intent.EXTRA_TEXT, "Type your email body.");
                MobileAppCommon.log.debug(intent);
                MobileAppCommon.log.debug(intent.getExtras());
                startActivity(Intent.createChooser(intent, "Send mail..."));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void dispose() {
        MobileAppCommon.log.trace("UserProfileActivity dispose");
        ((UserPostListAdapter) postListView.getAdapter()).dispose();
    }

    private class LoadPostTask extends AsyncTask<Void, Void, ArrayList<Post>> {

        @Override
        public void onPreExecute() {
            MobileAppCommon.log.info("Loading posts...");
            progressDialog = ProgressDialog.show(UserProfileActivity.this, "", String.format(getResources().getString(R.string.loading_user_posts), user.getFirstName()), true, false);
            postListView = (ListView) findViewById(android.R.id.list);
        }

        @Override
        protected ArrayList<Post> doInBackground(Void... params) {
            MobileAppCommon.log.debug("loading posts in background");
            return new ArrayList<Post>(PostProvider.getInstance().getPosts(user));
        }

        @Override
        public void onPostExecute(ArrayList<Post> result) {
            MobileAppCommon.log.info("Loading posts complete!");
            postListView.setAdapter(new UserPostListAdapter(UserProfileActivity.this, result));
            postListView.setOnItemClickListener(UserProfileActivity.this);
            progressDialog.dismiss();
        }
    }
}
