package com108104271860286811966hlru.google.httpsplus.seven_words;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Logic {
    DBHelper dbHelper;
    private final String DB_NAME = "english.db";
    final String tableName = "words";
    Cursor cursor;
    SQLiteDatabase database;
    Context context;
    ContentValues contentValues;
    String word[];
    String translation[];
    String date;

    Logic(Context context) {
        this.context = context;

    }

    void onCreateDBAPK(Context context, String name) {

        dbHelper = new DBHelper(context, name, null);
        try {
            dbHelper.createDb();
        } catch (Exception e) {
        }
        try {
            dbHelper.openDB();
        } catch (SQLException e) {
        }
        dbHelper.close();
    }


    void initialise() {
        if (checkThisDate()) {
            getThisDateData();
        } else {

            createThisDayData();
        }
    }

    boolean checkThisDate() {
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        date = dateFormat.format(currentDate);
        boolean check = false;
        dbHelper = new DBHelper(context, DB_NAME, null);
        database = dbHelper.getReadableDatabase();
        cursor = database.query(tableName, null, null, null, null, null, null);
        cursor.moveToFirst();
        String a;

        while (cursor.moveToNext()) {


            if (!cursor.getString(cursor.getColumnIndex("data")).equals("1")) {
                a = cursor.getString(cursor.getColumnIndex("data"));
                if (a.equals(date)) {
                    check = true;
                    cursor.close();
                    dbHelper.close();
                    database.close();
                    return check;

                } else check = false;
            }
        }
        cursor.close();
        dbHelper.close();
        database.close();
        return check;
    }

    void getThisDateData() {

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        date = dateFormat.format(currentDate);
        dbHelper = new DBHelper(context, DB_NAME, null);

        database = dbHelper.getReadableDatabase();
        cursor = database.query(tableName, new String[]{"_word", "_translate"}, "data = ?", new String[]{date}, null, null, null);
        cursor.moveToFirst();

        int i = 0;
        while (cursor.moveToNext()) {
            i++;

        }
        word = new String[i];
        translation = new String[i];


        cursor.moveToFirst();
        i = 0;
        while (cursor.moveToNext()) {
            word[i] = cursor.getString(cursor.getColumnIndex("_word"));
            translation[i] = cursor.getString(cursor.getColumnIndex("_translate"));


            i++;
        }
        database.close();
        dbHelper.close();
        cursor.close();


    }

    void createThisDayData() {

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        date = dateFormat.format(currentDate);

        contentValues = new ContentValues();
        int i = 0;
        Random random = new Random();
        int position = random.nextInt(700);
        contentValues.put("data", date);
        dbHelper = new DBHelper(context, DB_NAME, null);
        try {
            database = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            database = dbHelper.getReadableDatabase();
        }
        cursor = database.query(tableName, null, null, null, null, null, null);
cursor.moveToFirst();
        while (i <= 7) {
            cursor.moveToPosition(position);

            if (cursor.getString(cursor.getColumnIndex("data")).equals("1")) {
                String data = cursor.getString(cursor.getColumnIndex("_word"));
                database.update(tableName, contentValues, "_word = ?", new String[]{data});
                i++;
                position = random.nextInt(700);

            }


        }
        cursor = database.query(tableName, new String[]{"_word", "_translate"}, "data = ?", new String[]{date}, null, null, null);
        cursor.moveToFirst();
        i = 0;
        while (cursor.moveToNext()) {
            i++;
        }
        word = new String[i];
        translation = new String[i];


        cursor.moveToFirst();
        i = 0;
        while (cursor.moveToNext()) {
            word[i] = cursor.getString(cursor.getColumnIndex("_word"));
            translation[i] = cursor.getString(cursor.getColumnIndex("_translate"));
            i++;
        }

        dbHelper.close();
        database.close();
        cursor.close();

    }
}