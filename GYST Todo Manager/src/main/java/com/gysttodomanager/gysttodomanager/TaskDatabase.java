package com.gysttodomanager.gysttodomanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Yates on 4/26/2014.
 */
public class TaskDatabase {

    //Database Fields//
    private SQLiteDatabase database;
    private TaskDatabaseHelper dbHelper;
    private String[] allColumns = {"id,name,description,due_date_year,due_date_month,due_date_day,priority,completed,uuid"};

    public TaskDatabase(Context context){
        dbHelper = new TaskDatabaseHelper(context);
    }
    public SQLiteDatabase getDatabase(){ return database;}

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Task createTask(String uuid,String taskName,String taskDesc,Calendar dueDate,int priority,boolean completed) {
        ContentValues values = new ContentValues();
        values.put(TaskDatabaseHelper.COLUMN_NAME,taskName);
        values.put(TaskDatabaseHelper.COLUMN_DESCRIPTION,taskDesc);
        values.put(TaskDatabaseHelper.COLUMN_DUE_DATE_YEAR, dueDate.get(Calendar.YEAR));
        values.put(TaskDatabaseHelper.COLUMN_DUE_DATE_MONTH,dueDate.get(Calendar.MONTH));
        values.put(TaskDatabaseHelper.COLUMN_DUE_DATE_DAY, dueDate.get(Calendar.DAY_OF_MONTH));
        values.put(TaskDatabaseHelper.COLUMN_PRIORITY, priority);
        values.put(TaskDatabaseHelper.COLUMN_COMPLETED, completed? 1:0); //the last parameter is converting the boolean into an int
        values.put(TaskDatabaseHelper.COLUMN_UUID, uuid);

        int rowsAffected = database.update(TaskDatabaseHelper.TABLE_TASKS,values,TaskDatabaseHelper.COLUMN_ID + " = " + uuid,null);
        if(rowsAffected == 0) {
            long insertID = database.insert(TaskDatabaseHelper.TABLE_TASKS, null, values);
            Cursor cursor = database.query(TaskDatabaseHelper.TABLE_TASKS,allColumns,TaskDatabaseHelper.COLUMN_ID + " = " + insertID,null,null,null,null,null);
            cursor.moveToFirst();
            Task newTask = cursorToTask(cursor);
            return newTask;
        }
        return new Task();
    }

    public Task createTask(Task t) {
        ContentValues values = new ContentValues();
        values.put(TaskDatabaseHelper.COLUMN_NAME,t.getTaskName());
        values.put(TaskDatabaseHelper.COLUMN_DESCRIPTION,t.getDescription());
        values.put(TaskDatabaseHelper.COLUMN_DUE_DATE_YEAR, t.getDateDue().get(Calendar.YEAR));
        values.put(TaskDatabaseHelper.COLUMN_DUE_DATE_MONTH,t.getDateDue().get(Calendar.MONTH));
        values.put(TaskDatabaseHelper.COLUMN_DUE_DATE_DAY, t.getDateDue().get(Calendar.DAY_OF_MONTH));
        values.put(TaskDatabaseHelper.COLUMN_PRIORITY, t.getPriority());
        values.put(TaskDatabaseHelper.COLUMN_COMPLETED, t.isCompleted()? 1:0); //the last parameter is converting the boolean into an int
        values.put(TaskDatabaseHelper.COLUMN_UUID, t.getUniqueID());

        int rowsAffected = database.update(TaskDatabaseHelper.TABLE_TASKS,values,TaskDatabaseHelper.COLUMN_ID + " = " + t.getId(),null);
        if(rowsAffected == 0) {
            long insertID = database.insert(TaskDatabaseHelper.TABLE_TASKS, null, values);
            Cursor cursor = database.query(TaskDatabaseHelper.TABLE_TASKS,allColumns,TaskDatabaseHelper.COLUMN_ID + " = " + insertID,null,null,null,null,null);
            cursor.moveToFirst();
            Task newTask = cursorToTask(cursor);
            return newTask;
        }
        return new Task();
    }

    public void deleteTask(Task t){
        String id = t.getUniqueID();
        database.delete(TaskDatabaseHelper.TABLE_TASKS,TaskDatabaseHelper.COLUMN_ID +" = " + id,null);
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        Cursor cursor = database.query(TaskDatabaseHelper.TABLE_TASKS,allColumns,null,null,null,null,null);
        cursor.moveToFirst();
        
        while(!cursor.isAfterLast()) {
            Task t = cursorToTask(cursor);
            tasks.add(t);
            cursor.moveToNext();
        }
        cursor.close();
        return tasks;
    }

    private Task cursorToTask(Cursor cursor) {
        Task t = new Task();
        for(int i = 0; i < cursor.getColumnCount(); i++)
            System.out.println(cursor.getString(i));
        t.setUniqueID(cursor.getString(0));
        t.setTaskName(cursor.getString(1));
        t.setDescription(cursor.getString(2));
        Calendar tempCal = new GregorianCalendar(cursor.getInt(3),cursor.getInt(4),cursor.getInt(5));
        t.setDateDue(tempCal);
        t.setPriority(cursor.getInt(6));
        t.setCompleted(cursor.getInt(7) == 1);
        return t;
    }
}
