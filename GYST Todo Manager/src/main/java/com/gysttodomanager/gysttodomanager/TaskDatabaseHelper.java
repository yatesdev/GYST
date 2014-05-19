package com.gysttodomanager.gysttodomanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yates on 4/26/2014.
 */
public class TaskDatabaseHelper extends SQLiteOpenHelper{
    public static final String TABLE_TASKS = "Tasks";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DUE_DATE_YEAR = "due_date_year";
    public static final String COLUMN_DUE_DATE_MONTH = "due_date_month";
    public static final String COLUMN_DUE_DATE_DAY = "due_date_day";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_COMPLETED = "completed";
    public static final String COLUMN_UUID = "uuid";

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table Tasks(id integer primary key autoincrement unique, name text, description text, "+
            "due_date_year integer, due_date_month integer, due_date_day integer, priority integer, completed integer,uuid text);";

    public TaskDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

}
