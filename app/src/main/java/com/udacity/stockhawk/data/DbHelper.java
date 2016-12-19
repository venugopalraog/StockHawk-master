package com.udacity.stockhawk.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.udacity.stockhawk.data.Contract.Quote;


public class DbHelper extends SQLiteOpenHelper {


    static final String NAME = "StockHawk.db";
    private static final int VERSION = 1;


    public DbHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String builder = "CREATE TABLE " + Quote.TABLE_NAME + " (" +
                Quote._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Quote.COLUMN_SYMBOL + " TEXT NOT NULL, " +
                Quote.COLUMN_PRICE + " REAL NOT NULL, " +
                Quote.COLUMN_ABSOLUTE_CHANGE + " REAL NOT NULL, " +
                Quote.COLUMN_PERCENTAGE_CHANGE + " REAL NOT NULL, " +
                Quote.COLUMN_HISTORY + " TEXT NOT NULL, " +
                Quote.COLUMN_ASK + " REAL, " +
                Quote.COLUMN_ASK_SIZE + " REAL, " +
                Quote.COLUMN_BID + " REAL, " +
                Quote.COLUMN_BID_SIZE + " REAL, " +
                Quote.COLUMN_LAST_TRADE_SIZE + " REAL, " +
                Quote.COLUMN_LAST_TRADE_DATE_STR + " TEXT, " +
                Quote.COLUMN_LAST_TRADE_TIME_STR + " TEXT, " +
                "UNIQUE (" + Quote.COLUMN_SYMBOL + ") ON CONFLICT REPLACE);";

        db.execSQL(builder);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + Quote.TABLE_NAME);

        onCreate(db);
    }
}
