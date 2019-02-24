package com108104271860286811966hlru.google.httpsplus.seven_words;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper {
    String TAG="MyLog";
    private String DB_PATH="/data/data/com108104271860286811966hlru.google.httpsplus.seven_words/databases/";
    private final String DB_NAME="english.db";
    Context context;
    SQLiteDatabase database;

    public DBHelper(Context context, String name,SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, 1);
        this.context=context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createDb(){
        boolean dbExist=checkDB();
        if (dbExist){}
        else {this.getReadableDatabase();
        try {copyDatabase();}
        catch (IOException e){}}
    }

    private void copyDatabase() throws IOException {
        InputStream input=context.getAssets().open(DB_NAME);
        String outFileName=DB_PATH+DB_NAME;
        OutputStream outputStream= new FileOutputStream(outFileName);
        byte[]buffer=new byte[1024];
        int length;
        while ((length=input.read(buffer))>0){
            outputStream.write(buffer,0,length);
        }
        outputStream.flush();outputStream.close();input.close();
    }

    private boolean checkDB()  {
        SQLiteDatabase checkDB=null;
        try {
            String path=DB_PATH+DB_NAME;
            checkDB=SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);
        }catch (SQLException e){}
        if (checkDB!=null){checkDB.close();}
        return checkDB!=null;
    }

    public void openDB() throws SQLException{
        String path=DB_PATH+DB_NAME;
        database=SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close(){
        if (database!=null){database.close();}
        super.close();
    }

}
