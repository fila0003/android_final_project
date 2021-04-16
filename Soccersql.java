package com.cst2335.Cinthia040976686;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/*

@description extends an SQL lite
@author Cinthia
 */
public class Soccersql extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "AlbumDB";
    protected final static int VERSION_NUM = 3;
    public final static String TABLE_NAME = "Soccer";
    public final static String COL_ID = "_id";
    public final static String COL_MAGE = "image";
    public final static String COL_TEXT = "text";


    public Soccersql(Context ctx) {
        super( ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    /*
    @return void
    @descriptionon oncreate method create a database
     @param SQLiteDatabase db
    @author Cinthia
     */

    @Override
    public void onCreate(SQLiteDatabase db) { // this is database on create the first database on device
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_MAGE + " TEXT,"
                + COL_TEXT +  " INTEGER);"
                );  // add or remove columns regular sql statement for creation the database
    }

    //this function gets called if the database version on your device is lower than VERSION_NUM
        /*
    @return void
    @description onUpgrade  calls old database
    @param SQLiteDatabase db, int oldVersion, int newVersion
    @author Cinthia
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }
    /*
 @return void
 @descriptioncreate a new db database
 @param SQLiteDatabase db, int oldVersion, int newVersion
 @author Cinthia
  */
    //this function gets called if the database version on your device is higher than VERSION_NUM
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }
}
