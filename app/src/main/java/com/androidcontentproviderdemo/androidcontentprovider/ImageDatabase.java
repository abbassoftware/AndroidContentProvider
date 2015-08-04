package com.androidcontentproviderdemo.androidcontentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.sql.SQLException;

public class ImageDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ImagesDatabase.db";
    private static final String TABLE_NAME = "imagestore";
    private static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME +
            " (_id INTEGER PRIMARY KEY, IMAGETITLE TEXT , IMAGEURL TEXT , IMAGEDESC TEXT )";

    private static final String SQL_DROP = "DROP TABLE IS EXISTS " + TABLE_NAME ;


    ImageDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
        onCreate(db);
    }

    public long addNewImage(ContentValues values) throws SQLException {
        long id = getWritableDatabase().insert(TABLE_NAME , "" , values);
        if(id <=0 ) {
            throw new SQLException("Failed to add an image");
        }

        return id;
    }

    public Cursor getImages(String id, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqliteQueryBuilder = new SQLiteQueryBuilder();
        sqliteQueryBuilder.setTables(TABLE_NAME);

        if(id != null) {
            sqliteQueryBuilder.appendWhere("_id" + " = " + id);
        }

        if(sortOrder == null || sortOrder == "") {
            sortOrder = "IMAGETITLE";
        }
        Cursor cursor = sqliteQueryBuilder.query(getReadableDatabase(),
                      projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        return cursor;
    }
}
