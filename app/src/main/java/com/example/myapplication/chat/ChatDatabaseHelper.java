package com.example.myapplication.chat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "chat.db";
    public static final int DATABASE_VERSION = 2; // 버전을 2로 증가

    public static final String TABLE_NAME = "chat_history";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SENDER = "sender";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_ROOM_NAME = "room_name"; // 추가된 열

    public ChatDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SENDER + " TEXT, " +
                COLUMN_MESSAGE + " TEXT, " +
                COLUMN_TIMESTAMP + " INTEGER, " +
                COLUMN_ROOM_NAME + " TEXT)"; // room_name 열 추가
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // 버전 2에서 room_name 열 추가
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_ROOM_NAME + " TEXT");
        }
    }
}
