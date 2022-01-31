package com.example.counter;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "counterDb";
    public static final String TABLE_COUNT = "counters";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_COUNT = "count";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_COUNT + "("
                + KEY_ID + " integer primary key,"
                + KEY_NAME + " text,"
                + KEY_COUNT + " integer"
                + ")");

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NAME, "Counter main");
        newValues.put(KEY_COUNT, 0);

        db.insert(TABLE_COUNT, null, newValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_COUNT);

        onCreate(db);
    }

    public int loadCount(SQLiteDatabase db, int id) {
        int result = 0;

        if (check(db, id)) {
            Cursor cursor = db.query(
                    TABLE_COUNT,
                    new String[]{KEY_COUNT},
                    KEY_ID + " = ?",
                    new String[]{Integer.toString(id)},
                    null, null, null
            );

            if (cursor.moveToFirst()) {
                result = cursor.getInt(cursor.getColumnIndex(KEY_COUNT));
                Log.d("myLog", "Значения счётчика успешно извлечены.");
            } else
                Log.d("myLog", "Не нашёл значение счётчика.");

        } else {
            Log.d("myLog", "No");
        }

        return result;
    }

    public String loadName(SQLiteDatabase db, int id) {
        String result = "";

        if (check(db, id)) {
            Cursor cursor = db.query(
                    TABLE_COUNT,
                    new String[]{KEY_NAME},
                    KEY_ID + " = ?",
                    new String[]{Integer.toString(id)},
                    null, null, null
            );

            if (cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                Log.d("myLog", "Имя счётчика успешно извлечено.");
            } else
                Log.d("myLog", "Не нашёл имени счётчика.");

        } else {
            Log.d("myLog", "No");
        }

        return result;
    }

    public String loadID(SQLiteDatabase db, String name) {
        String result = "";

        Cursor cursor = db.query(
                TABLE_COUNT,
                new String[]{KEY_ID},
                KEY_NAME + " = ?",
                new String[]{name},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            Log.d("myLog", "ID счётчика успешно извлечено.");
        } else
            Log.d("myLog", "Не нашёл ID счётчика.");


        return result;
    }

    public void saveCounter(SQLiteDatabase db, int id, String name, int count) {

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NAME, name);
        newValues.put(KEY_COUNT, count);

        if (id != 0 && check(db, id)) {
            String where = KEY_ID + " = " + id;
            db.update(TABLE_COUNT, newValues, where, null);
        } else {
            db.insert(TABLE_COUNT, null, newValues);
        }

        Log.d("myLog", "Значения счётчика успешно сохранены");
    }

    public void deleteCounter(SQLiteDatabase db, int id, String name, int count) {
        String where = KEY_ID + " = " + id;
        db.delete(TABLE_COUNT, where, null);

        Log.d("myLog", "Значения счётчика успешно удалено");
    }

    public boolean check(SQLiteDatabase db, int id) {
        boolean result = false;

        @SuppressLint("Recycle")
        Cursor cursor = null;
        cursor = db.query(TABLE_COUNT,
                null,
                KEY_ID + " = ?",
                new String[]{Integer.toString(id)},
                null, null, null);

        if (cursor.moveToFirst()) {
            if (cursor.getInt(cursor.getColumnIndex(KEY_ID)) != 0)
                result = true;
        }

        return result;
    }

}
