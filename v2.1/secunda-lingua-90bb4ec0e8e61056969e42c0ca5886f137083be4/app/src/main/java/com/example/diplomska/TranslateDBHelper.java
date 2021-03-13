package com.example.diplomska;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.diplomska.translateExercise.*;
public class TranslateDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "translateExercises.db";
    public static final int DATABASE_VERSION = 1;
    public TranslateDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TRANSLATE_EXERCISE_TABLE = "CREATE TABLE " +
                TranslateEntry.TABLE_NAME + " (" +
                TranslateEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TranslateEntry.COLUMN_SENTENCE + " TEXT NOT NULL, " +
                TranslateEntry.COLUMN_TRANSLATION + " TEXT NOT NULL, " +
                TranslateEntry.COLUMN_ALT_TRANSLATION + " TEXT NOT NULL" +
                ");";
        db.execSQL(SQL_CREATE_TRANSLATE_EXERCISE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TranslateEntry.TABLE_NAME);
        onCreate(db);
    }


}
