package com.jaysan1292.project.c3074.activity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.jaysan1292.project.c3074.MobileAppCommon;
import com.jaysan1292.project.c3074.R;
import com.jaysan1292.project.c3074.db.CommentProvider;
import com.jaysan1292.project.c3074.db.PostProvider;
import com.jaysan1292.project.c3074.utils.PostListAdapter;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.User;

import java.util.ArrayList;

//TODO: Cache

public class MainActivity extends ListActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ProgressDialog _progressDialog;
    private ListView _postListView;
    private boolean _shouldRestoreState;
    private Bundle _savedState;
    private boolean _isLoadingPosts;
    private boolean _isLaunching;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        _isLoadingPosts = true;

        MobileAppCommon.log.info("MainActivity onCreate");

        if (savedInstanceState != null) {
            _shouldRestoreState = true;
            _savedState = savedInstanceState;
        }

        new LoadPostTask().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!_isLoadingPosts && !_isLaunching) {
            MobileAppCommon.log.info("MainActivity onResume");
            refreshPosts();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveScrollPosition(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreScrollPosition(savedInstanceState);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.profile_image):
                User user = (User) v.getTag(R.id.post_author);

                MobileAppCommon.log.debug("User ID " + user.getId() + " clicked");

                //go to user's profile
                MobileAppCommon.startUserProfileActivity(MainActivity.this, user);
                break;
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MobileAppCommon.log.debug("Post ID " + view.getTag(R.id.post_id) + " clicked");

        long postId = (Long) view.getTag(R.id.post_id);

        Post post = PostProvider.getInstance().getPost(postId);

        MobileAppCommon.startPostDetailActivity(MainActivity.this, post);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_post_new:
                MobileAppCommon.log.debug("new post button clicked");
                MobileAppCommon.startNewPostActivity(this);
                return true;
            case R.id.menu_main_refresh:
                MobileAppCommon.log.debug("refresh button clicked");
                refreshPosts();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class LoadPostTask extends AsyncTask<Void, Void, ArrayList<Post>> {

        @Override
        public void onPreExecute() {
            _progressDialog = new ProgressDialog(MainActivity.this);
            _progressDialog.setMessage(getResources().getString(R.string.loading_feed_posts));
            _progressDialog.setIndeterminate(true);
            _progressDialog.setCancelable(false);
            _progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            _progressDialog.setProgressNumberFormat(null);
            _progressDialog.setProgressPercentFormat(null);
            _progressDialog.show();
            _postListView = (ListView) findViewById(android.R.id.list);
        }

        @Override
        protected ArrayList<Post> doInBackground(Void... params) {
            _isLoadingPosts = true;
            //Initialize post and comments arraylist
            PostProvider.getInstance();
            CommentProvider.getInstance();
            return new ArrayList<Post>(PostProvider.getInstance().getPosts());
        }

        @Override
        public void onPostExecute(ArrayList<Post> result) {
            _postListView.setAdapter(new PostListAdapter(MainActivity.this, result));
            _postListView.setOnItemClickListener(MainActivity.this);
            _progressDialog.dismiss();

            if (_shouldRestoreState) restoreScrollPosition(_savedState);
            _isLoadingPosts = false;
            _isLaunching = false;
        }
    }

    private void saveScrollPosition(Bundle outState) {
        MobileAppCommon.log.debug("Saving scroll position.");

        int index = _postListView.getFirstVisiblePosition();
        View v = _postListView.getChildAt(0);
        int top = (v == null) ? 0 : v.getTop();

        outState.putInt("index", index);
        outState.putInt("top", top);
    }

    private void restoreScrollPosition(Bundle savedState) {
        MobileAppCommon.log.debug("Restoring saved scroll position.");

        int index = savedState.getInt("index", 0);
        int top = savedState.getInt("top", 0);
        _postListView.setSelectionFromTop(index, top);

        savedState = null;
    }

    private void refreshPosts() {
        MobileAppCommon.log.info("Refreshing posts.");
        _postListView.setAdapter(new PostListAdapter(MainActivity.this, PostProvider.getInstance().getPosts()));
        ((PostListAdapter) _postListView.getAdapter()).notifyDataSetChanged();
    }
}
