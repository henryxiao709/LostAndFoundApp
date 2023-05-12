package com.example.task71p;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DatabaseShuJuKuHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "lostandfound.db";

    static final int DATABASE_VERSION = 1;

    static final class ItemEntry implements BaseColumns {
        static final String TABLE_NAME = "items";

        static final String _ID = BaseColumns._ID;
        static final String COLUMN_NAME = "name";
        static final String COLUMN_PHONE = "phone";
        static final String COLUMN_DESCRIPTION = "description";
        static final String COLUMN_DATE = "date";
        static final String COLUMN_LOCATION = "location";
    }

    public DatabaseShuJuKuHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dbShuJuKu) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + ItemEntry.TABLE_NAME + " ("
                + ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ItemEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + ItemEntry.COLUMN_PHONE + " TEXT NOT NULL, "
                + ItemEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, "
                + ItemEntry.COLUMN_DATE + " TEXT NOT NULL, "
                + ItemEntry.COLUMN_LOCATION + " TEXT NOT NULL);";

        dbShuJuKu.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase dbShuJuKu, int oldVersion, int newVersion) {
        String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + ItemEntry.TABLE_NAME;

        dbShuJuKu.execSQL(SQL_DROP_TABLE);

        onCreate(dbShuJuKu);
    }
}


