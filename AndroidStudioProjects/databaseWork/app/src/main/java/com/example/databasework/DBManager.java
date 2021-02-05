package com.example.databasework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

public class DBManager {
    private  SQLiteDatabase sqlDB;
    static final  String DBName = "Students";
    static final String TableName = "Login";
    static final String colUserName = "UserName";
    static final String colPassword = "Password";
    static final String colID = "ID";
    static final int DBVersion = 1;

    //create table Login(ID integer primary key autoincrement, UserName text Unique, Password text);
    static final String CreateTable = "Create table IF NOT EXISTS " + TableName + "(ID integer primary key autoincrement, "
            + colUserName + " text Unique, " + colPassword + " text);";

    static  class DatabaseHelper extends SQLiteOpenHelper{
        Context context;
        DatabaseHelper(Context context){
            super(context, DBName, null, DBVersion);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CreateTable);
            Toast.makeText(context, "Table is created", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop table if exists " + TableName);
            onCreate(db);
        }
    }

    public  DBManager(Context context){
        DatabaseHelper db = new DatabaseHelper(context);
        sqlDB = db.getWritableDatabase();
    }

    public  long Insert(ContentValues values){
        long ID = sqlDB.insert(TableName,"",values);// if fails to insert then ID = 0

        return ID;
    }

    //select username, password from login where ID = 1
    public Cursor query(String[] Projection, String Selection, String[] SelectionArgs, String SortOrder){
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TableName);

        Cursor cursor = qb.query(sqlDB, Projection, Selection, SelectionArgs, null, null, SortOrder);
        return  cursor;
    }
    public int Delete(String selection, String[] selectionArgs){
        int count = sqlDB.delete(TableName, selection, selectionArgs);
        return count;
    }
    public int Update(ContentValues values, String selection, String[] selectionArgs){
        int count = sqlDB.update(TableName, values, selection, selectionArgs);
        return  count;
    }
}
