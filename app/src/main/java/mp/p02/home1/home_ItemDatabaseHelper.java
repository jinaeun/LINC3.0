package mp.p02.home1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class home_ItemDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "items.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "items";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_IMAGE_URI = "image_uri";
    private static final String COLUMN_HEART_STATE = "is_filled";

    public home_ItemDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_CONTENT + " TEXT, "
                + COLUMN_IMAGE_URI + " TEXT, "
                + COLUMN_HEART_STATE + " INTEGER DEFAULT 0)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_HEART_STATE + " INTEGER DEFAULT 0");
        }
        // Add future migration logic here if necessary
    }

    public void addItem(home_Item item) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, item.getTitle());
            values.put(COLUMN_CONTENT, item.getContent());
            values.put(COLUMN_IMAGE_URI, item.getImageUri() != null ? item.getImageUri().toString() : null);
            values.put(COLUMN_HEART_STATE, item.isFavorite() ? 1 : 0);

            db.insert(TABLE_NAME, null, values);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public List<home_Item> getAllItems() {
        List<home_Item> itemList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getReadableDatabase();
            cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_ID + " DESC");

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                    String content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT));
                    String imageUriString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URI));
                    int heartState = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HEART_STATE));
                    Uri imageUri = imageUriString != null ? Uri.parse(imageUriString) : null;

                    home_Item item = new home_Item(id, imageUri, title, content, heartState == 1);
                    itemList.add(item);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return itemList;
    }

    public boolean doesItemExist(int itemId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getReadableDatabase();
            cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID}, COLUMN_ID + " = ?",
                    new String[]{String.valueOf(itemId)}, null, null, null);

            return cursor != null && cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    public boolean deleteItem(int itemId) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            int rowsAffected = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(itemId)});
            return rowsAffected > 0;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public boolean updateHeartState(int id, boolean isFavorite) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_HEART_STATE, isFavorite ? 1 : 0);

            int rowsAffected = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
            return rowsAffected > 0;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }
}
