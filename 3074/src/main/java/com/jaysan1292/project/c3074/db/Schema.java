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
    //endregion

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
}
