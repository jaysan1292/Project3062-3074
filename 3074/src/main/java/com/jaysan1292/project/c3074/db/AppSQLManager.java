package com.jaysan1292.project.c3074.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.jaysan1292.project.c3074.MobileAppCommon;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;

import static com.jaysan1292.project.c3074.db.Schema.Drafts;

@SuppressWarnings("ClassWithoutNoArgConstructor")
public class AppSQLManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserData";
    private static final int DATABASE_VERSION = 1;

    public AppSQLManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        MobileAppCommon.log.debug("Creating database " + DATABASE_NAME + ".db");
        String[] createSql = {Schema.CREATE_DRAFTS_SQL/*, Schema.CREATE_POST_CACHE_SQL, Schema.CREATE_USER_META_SQL*/};
        try {

            ContentValues draft1, draft2;
            draft1 = new ContentValues();
            draft1.put(Drafts.POST_DATE, DateUtils.parseDate("2012 October 17, 17:26:12", "yyyy MMMM dd, HH:mm:ss").getTime());
            draft1.put(Drafts.POST_CONTENT, "This is a draft post.");
            draft2 = new ContentValues();
            draft2.put(Drafts.POST_DATE, DateUtils.parseDate("2012 November 3, 18:15:27", "yyyy MMMM dd, HH:mm:ss").getTime());
            draft2.put(Drafts.POST_CONTENT, "This is another draft post.");

            try {
                db.beginTransaction();
                {
                    execMultipleSQL(db, createSql);
                    insertMultiple(db, Drafts.TABLE_NAME, draft1, draft2);
                }
                db.setTransactionSuccessful();
            } catch (SQLException e) {
                MobileAppCommon.log.error("Error creating database tables.", e);
            } finally {
                db.endTransaction();
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not needed right now.
    }

    private static void execMultipleSQL(SQLiteDatabase db, String... sql) throws SQLException {
        for (String s : sql) {
            if (!s.trim().isEmpty()) {
                db.execSQL(s);
            }
        }
    }

    private static void insertMultiple(SQLiteDatabase db, String table, ContentValues... values) throws SQLException {
        for (ContentValues value : values) {
            db.insertOrThrow(table, null, value);
        }
    }
}
