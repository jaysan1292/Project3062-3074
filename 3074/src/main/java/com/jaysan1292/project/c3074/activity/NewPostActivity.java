package com.jaysan1292.project.c3074.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Selection;
import android.view.*;
import android.widget.*;
import com.jaysan1292.project.c3074.MobileAppCommon;
import com.jaysan1292.project.c3074.R;
import com.jaysan1292.project.c3074.db.DraftManager;
import com.jaysan1292.project.c3074.db.PostProvider;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.User;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;

/*
Activity flow:
Activity is created:
---- check if there are drafts saved, and if so, ask the user if they would like to use one
Activity is closed:
---- check if the user made changes (i.e., post is not empty, or post has changed since it was restored from a draft)
---- if so, ask the user if they would like to save their changes
-------- if yes, save the changes, overwriting draft if necessary, and then finish()
-------- if no, do nothing
 */

/** @author Jason Recillo */
public class NewPostActivity extends Activity {
    private String originalState = "";
    private ArrayList<Post> drafts;
    private boolean usingDraft;
    private Post post;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
        setContentView(R.layout.new_post);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        MobileAppCommon.log.info(savedInstanceState);

        drafts = DraftManager.getSharedInstance().getDrafts();
        MobileAppCommon.log.debug("NewPostActivity onStart");
        if (savedInstanceState == null) {
            if (!drafts.isEmpty()) {
                MobileAppCommon.log.debug("Drafts list has items, showing dialog prompt.");

                new AlertDialog.Builder(this)
                        .setTitle("You have drafts saved.")
                        .setPositiveButton("Choose draft", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                showDraftsMenu();
                            }
                        })
                        .setNegativeButton("New post", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
            if (user == null) {
                MobileAppCommon.log.debug("user is null");
                user = MobileAppCommon.getLoggedInUser();
            }

            usingDraft = false;
            post = new Post();
            post.setPostAuthor(user);
        } else {
            MobileAppCommon.log.info("Restoring instance state.");
            ((TextView) findViewById(R.id.edit_text_new_post)).setText(savedInstanceState.getString("PostData"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MobileAppCommon.log.info("NewPostActivity onSaveInstanceState");

        EditText content = (EditText) findViewById(R.id.edit_text_new_post);
        outState.putString("PostData", content.getText().toString());
        outState.putInt("PostSelectionStart", Selection.getSelectionStart(content.getText()));
        outState.putInt("PostSelectionEnd", Selection.getSelectionEnd(content.getText()));

        MobileAppCommon.log.info(outState.toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        MobileAppCommon.log.info("NewPostActivity onRestoreInstanceState");

        EditText content = (EditText) findViewById(R.id.edit_text_new_post);
        content.setText(savedInstanceState.getString("PostData"));
        Selection.setSelection(content.getText(),
                               savedInstanceState.getInt("PostSelectionStart"),
                               savedInstanceState.getInt("PostSelectionEnd"));

        MobileAppCommon.log.info(savedInstanceState.toString());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MobileAppCommon.log.info("Back button pressed");
        checkPostAndExit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_post_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                checkPostAndExit();
                return true;
            case R.id.menu_post_submit_post:
                MobileAppCommon.log.info("Post button clicked");
                submitPost();
                return true;
            case R.id.menu_post_drafts:
                MobileAppCommon.log.info("Show drafts button clicked");
                showDraftsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDraftsMenu() {
        drafts = DraftManager.getSharedInstance().getDrafts();

        if (!drafts.isEmpty()) {
            MobileAppCommon.log.info("Showing drafts window.");
            final Dialog dialog = new Dialog(this);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.draft_list, null, false);
            dialog.setContentView(v);
            dialog.setTitle("Select a draft.");

            ListView draftList = (ListView) dialog.findViewById(android.R.id.list);
            // Set the drafts instance variable as the backing array for the ArrayAdapter
            ArrayAdapter<Post> adapter = new ArrayAdapter<Post>(this, R.layout.draft_list_item, drafts) {
                // Override getView here because we don't want to display our items by calling toString()
                // on them; that just displays them as a JSON object.
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View vi = super.getView(position, convertView, parent);
                    ((TextView) vi.findViewById(R.id.draft_list_item_content)).setText(getItem(position).getPostContent());
                    return vi;
                }
            };
            draftList.setAdapter(adapter);
            draftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Post clickedPost = (Post) parent.getItemAtPosition(position);
                    MobileAppCommon.log.debug(clickedPost);

                    String postContent = clickedPost.getPostContent();
                    NewPostActivity.this.post = clickedPost;
                    NewPostActivity.this.originalState = postContent;
                    NewPostActivity.this.usingDraft = true;
                    EditText content = ((EditText) NewPostActivity.this.findViewById(R.id.edit_text_new_post));
                    content.setText(postContent);
                    Selection.setSelection(content.getText(), content.length());
                    dialog.hide();
                }
            });
            dialog.show();
        } else {
            MobileAppCommon.log.info("There are no drafts to show.");
            new AlertDialog.Builder(this)
                    .setMessage("You have no drafts.")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        }
    }

    private void submitPost() {
        MobileAppCommon.log.info("Submitting post");
        if (usingDraft) {
            // if we're submitting a draft that was previously saved, now remove it from the database.
            DraftManager.getSharedInstance().removeDraft(post);
        }
        post.setPostContent(((TextView) findViewById(R.id.edit_text_new_post)).getText().toString());
        post.setPostDate(new Date());
        PostProvider.getInstance().submitPost(post);
        exitActivity();
    }

    private void checkPostAndExit() {
        String postContent = ((TextView) findViewById(R.id.edit_text_new_post)).getText().toString();
        post.setPostContent(StringUtils.normalizeSpace(postContent));
        post.setPostDate(new Date());
        if (!StringUtils.strip(originalState).equals(StringUtils.strip(postContent))) {
            new AlertDialog.Builder(this)
                    /*.setMessage("Would you like to save your message for later?")*/
                    .setTitle("Save as draft?")
                    .setNeutralButton("Cancel", null)
                    .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (usingDraft) {
                                MobileAppCommon.log.debug("This post is a draft; removing it from database");
                                DraftManager.getSharedInstance().removeDraft(post);
                            }
                            exitActivity();
                        }
                    })
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (usingDraft) {
                                MobileAppCommon.log.debug("This post is a draft; updating it in the database");
                                DraftManager.getSharedInstance().updateDraft(post);
                            } else {
                                MobileAppCommon.log.debug("Saving draft to database.");
                                DraftManager.getSharedInstance().addDraft(post);
                            }
                            exitActivity();
                        }
                    })
                    .show();
        } else {
            exitActivity();
        }
    }

    private void exitActivity() {
        finish();
        overridePendingTransition(R.anim.slide_in_rev, R.anim.slide_out_rev);
    }
}
