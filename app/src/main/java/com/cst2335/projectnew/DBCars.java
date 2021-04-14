package com.cst2335.projectnew;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Arrays;

public class DBCars extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DBCars";
    public static final int VERSION_NUM = 1;
    public final static String TABLE_NAME = "Cars";
    public final static String COL_ID = "_id";
    public final static String COL_MAKE_ID = "makeId";
    public final static String COL_MAKE_NAME = "makeName";
    public final static String COL_MODEL_ID = "modelId";
    public final static String COL_MODEL_NAME = "modelName";
    public final String[] columns = {COL_MODEL_NAME, COL_MODEL_ID, COL_MAKE_NAME, COL_MAKE_ID, COL_ID};
    private Object MessageType;


    public DBCars(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_MAKE_ID + " text,"
                + COL_MAKE_NAME + " text,"
                + COL_MODEL_ID + " text,"
                + COL_MODEL_NAME  + " text);");  // add or remove columns
    }
    /*public void onCreate(SQLiteDatabase SQLdb){
        SQLdb.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_MESSAGES + " text,"
                + COL_SENDORRECEIVE + " text);");
    }*/

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("Database Upgrade", "Old Version:" + oldVersion + " newVersion:"+newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("Database downgrade", "Old Version:" + oldVersion + " newVersion:"+newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}

