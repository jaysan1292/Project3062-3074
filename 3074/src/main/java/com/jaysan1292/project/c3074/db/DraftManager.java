package com.jaysan1292.project.c3074.db;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.jaysan1292.project.c3074.MobileAppCommon;
import com.jaysan1292.project.common.data.Post;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * Date: 03/11/12
 * Time: 9:43 PM
 *
 * @author Jason Recillo
 */
public class DraftManager {
    private static DraftManager sharedInstance;
    private final AppSQLManager manager;

    public static DraftManager getSharedInstance() {
        if (sharedInstance == null) sharedInstance = new DraftManager();
        return sharedInstance;
    }

    private DraftManager() {
        manager = MobileAppCommon.getDataManager();
    }

    public void addDraft(Post draft) {
        SQLiteDatabase db = manager.getWritableDatabase();
        try {
            db.beginTransaction();
            {
                ContentValues values = new ContentValues();
                values.put(Schema.Drafts.POST_DATE, draft.getPostDate().getTime());
                values.put(Schema.Drafts.POST_CONTENT, draft.getPostContent());
                db.insert(Schema.Drafts.TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public ArrayList<Post> getDrafts() {
        String sql = Schema.Drafts.DraftsCursor.QUERY;
        SQLiteDatabase db = manager.getReadableDatabase();
        Schema.Drafts.DraftsCursor cursor = (Schema.Drafts.DraftsCursor) db.rawQueryWithFactory(new Schema.Drafts.DraftsCursor.Factory(), sql, null, null);

        ArrayList<Post> drafts = new ArrayList<Post>();
        while (cursor.moveToNext()) {
            Post post = new Post();
            post.setId(cursor.getId());
            post.setPostAuthor(MobileAppCommon.getLoggedInUser());
            post.setPostContent(cursor.getPostContent());
            post.setPostDate(cursor.getPostDate());
            drafts.add(post);
        }
        return drafts;
    }

    public void updateDraft(Post draft) {
        SQLiteDatabase db = manager.getWritableDatabase();
        try {
            db.beginTransaction();
            {
                ContentValues value = new ContentValues();
                value.put(Schema.Drafts.POST_CONTENT, draft.getPostContent());
                value.put(Schema.Drafts.POST_DATE, draft.getPostDate().getTime());

                db.update(Schema.Drafts.TABLE_NAME, value, Schema.Drafts.ID + "=?", new String[]{String.valueOf(draft.getId())});
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            MobileAppCommon.log.error(e.getMessage(), e);
            throw e;
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void removeDraft(Post draft) {
        SQLiteDatabase db = manager.getWritableDatabase();
        try {
            db.beginTransaction();
            {
                db.delete(Schema.Drafts.TABLE_NAME, Schema.Drafts.ID + "=?", new String[]{String.valueOf(draft.getId())});
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
