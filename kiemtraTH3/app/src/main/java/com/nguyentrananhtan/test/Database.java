package com.nguyentrananhtan.test;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public static final String DB_NAME = "db_qldatxe.sqlite";
    public static final int DB_VERSION = 1;
    public static final String TBL_NAME = "DonThue";
    public static final String COL_MADON = "MaDon";
    public static final String COL_HANGXE = "HangXe";
    public static final String COL_GIATHUE = "GiaThue";
    public static final String COL_HINHANH = "HinhAnh";
    public static final String COL_DIADIEM = "DiaDiem";
    public static final String COL_LOAIXE = "LoaiXe";
    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TBL_NAME + " ( " + COL_MADON + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_HANGXE + " VARCHAR(50), " + COL_GIATHUE + " REAL, " + COL_HINHANH + " VARCHAR(50), " + COL_DIADIEM + " VARCHAR(50), " + COL_LOAIXE + " VARCHAR(50))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_NAME);
        onCreate(db);
    }

    //SELECT
    public Cursor queryData(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    //INSERT
    public void execSql(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL(sql);
    }

    public int getNumbOfRows() {
        Cursor cursor = queryData("SELECT * FROM " + TBL_NAME);
        int numbOfRows = cursor.getCount();
        cursor.close();
        return numbOfRows;
    }

    public void createSampleData() {
        if (getNumbOfRows() == 0) {
            try {
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Hang xe 1', 1000000, 'img1.png', 'Thu Duc', 4)");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Hang xe 1', 1500000, 'img2.png', 'Tan Phu', 7)");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Hang xe 2', 1500000, 'img3.png', 'Thu Duc', 7)");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Hang xe 1', 2000000, 'img4.png', 'Go Vap', 16)");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Hang xe 2', 1000000, 'img5.png', 'Go Vap', 4)");
            } catch (Exception e) {
                Log.e("Error ", e.getMessage().toString());
            }

        }
    }
}
