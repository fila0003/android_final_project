package com.cst2335.projectnew;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "AlbumDB";
    protected final static int VERSION_NUM = 3;
    public final static String TABLE_NAME = "Song";
    public final static String COL_ID = "_id";
    public final static String COL_NAME = "name";
    public final static String COL_ARTIST_ID = "artist_id";
    public final static String COL_ARTIST_NAME = "artist_name";

    public MyDBOpener(Context ctx) {
        super( ctx, DATABASE_NAME, null, VERSION_NUM);
    }



    @Override
    public void onCreate(SQLiteDatabase db) { // this is database on create the first database on device
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT,"
                + COL_ARTIST_ID +  " INTEGER,"
                + COL_ARTIST_NAME + " TEXT);" );  // add or remove columns regular sql statement for creation the database
    }

    //this function gets called if the database version on your device is lower than VERSION_NUM
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }

    //this function gets called if the database version on your device is higher than VERSION_NUM
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }
}

