package com.jaysan1292.project.c3074.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import org.intellij.lang.annotations.Language;

import java.util.Date;

/** @author Jason Recillo */
@SuppressWarnings("ALL")
public final class Schema {
    private Schema() {}

    //region Table creation SQL
    @Language("SQLite") // Stores unfinished posts
    protected static final String CREATE_DRAFTS_SQL =
            "CREATE TABLE IF NOT EXISTS " + Drafts.TABLE_NAME + "(" +
            "  " + Drafts.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  " + Drafts.POST_DATE + " INTEGER," +
            "  " + Drafts.POST_CONTENT + " TEXT" +
            ")";
//    @Language("SQLite") // Stores logged in user information
//    private static final String CREATE_USER_META_SQL =
//            "CREATE TABLE IF NOT EXISTS " + UserMeta.TABLE_NAME + "(" +
//            "  " + UserMeta.KEY + " TEXT," +
//            "  " + UserMeta.VALUE + " TEXT" +
//            ")";
//    @Language("SQLite")
//    // Caches loaded posts, so that most content won't have to be loaded from the web service every time
//    private static final String CREATE_POST_CACHE_SQL =
//            "CREATE TABLE IF NOT EXISTS " + PostCache.TABLE_NAME + "(" +
//            "  " + PostCache.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//            "  " + PostCache.POST_JSON + " TEXT," +
//            "  " + PostCache.EXPIRATION + " INTEGER" +
//            ")";
    //endregion

    //TODO: Make each of these classes have a nested Cursor class that extends SQLiteCursor

    public static final class Drafts {
        public static final String TABLE_NAME = "drafts";
        public static final String ID = "_id";
        public static final String POST_DATE = "post_date";
        public static final String POST_CONTENT = "post_content";

        public static class DraftsCursor extends SQLiteCursor {
            protected static final String QUERY = "SELECT * FROM " + TABLE_NAME;

            public DraftsCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
                super(db, driver, editTable, query);
            }

            public static class Factory implements SQLiteDatabase.CursorFactory {
                public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
                    return new DraftsCursor(db, driver, editTable, query);
                }
            }

            public long getId() { return getLong(getColumnIndexOrThrow(ID)); }

            public Date getPostDate() { return new Date(getLong(getColumnIndexOrThrow(POST_DATE))); }

            public String getPostContent() { return getString(getColumnIndexOrThrow(POST_CONTENT)); }
        }
    }

//    public static final class UserMeta {
//        public static final String TABLE_NAME = "user_meta";
//        public static final String KEY = "key";
//        public static final String VALUE = "value";
//
//        public static final class Values {
//            public static final String LOGGED_IN_USER_ID = "userId";
//            public static final String LOGGED_IN_USER_JSON = "userJson";
//        }
//
//        public static class UserMetaCursor extends SQLiteCursor {
//            protected static final String QUERY = "SELECT * FROM " + TABLE_NAME;
//
//            public UserMetaCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
//                super(db, driver, editTable, query);
//            }
//
//            public static class Factory implements SQLiteDatabase.CursorFactory {
//                public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
//                    return new UserMetaCursor(db, driver, editTable, query);
//                }
//            }
//
//            public String getKey() { return getString(getColumnIndexOrThrow(KEY)); }
//
//            public String getValue() { return getString(getColumnIndexOrThrow(VALUE)); }
//        }
//    }
//
//    public static final class PostCache {
//        public static final String TABLE_NAME = "postCache";
//        public static final String ID = "_id";
//        public static final String POST_JSON = "post_json";
//        public static final String EXPIRATION = "expiration";
//
//        public static class PostCacheCursor extends SQLiteCursor {
//            protected static final String QUERY = "SELECT * FROM " + TABLE_NAME;
//
//            public PostCacheCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
//                super(db, driver, editTable, query);
//            }
//
//            public static class Factory implements SQLiteDatabase.CursorFactory {
//                public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
//                    return new PostCacheCursor(db, driver, editTable, query);
//                }
//            }
//
//            public String getPosts() { return getString(getColumnIndexOrThrow(POST_JSON)); }
//
//            public Date getExpiration() { return new Date(getLong(getColumnIndexOrThrow(EXPIRATION))); }
//        }
//    }
}
