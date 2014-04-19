package com.gysttodomanager.gysttodomanager;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Created by Yates on 4/10/14.
 */

/**
 * Task
 * The container for the information stored by the program
 */
public class Task {
    private String taskName;
    private Calendar dateDue;
    private int priority; //3=High 2=Medium 1=Low
    private String description;
    private boolean isCompleted;
    private String uniqueID;


    /**
     * This is the full constructor, only used for debug and dummy data definitions.
     */
    public Task(String task, Calendar due, int pri, String desc, boolean complete) {
        taskName = task;
        dateDue = due;
        priority = pri;
        description = desc;
        isCompleted = complete;
        uniqueID = UUID.randomUUID().toString();
    }

    /**
     * This is the blank constructor called when we are adding a new item
     */
    public Task() {
        taskName = "";
        dateDue = new GregorianCalendar();
        priority = 1;
        description = "";
        isCompleted = false;
        uniqueID = UUID.randomUUID().toString();
    }


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Calendar getDateDue() {
        return dateDue;
    }

    public void setDateDue(Calendar dateDue) {
        this.dateDue = dateDue;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public String toString() {
        String str = "";
        str += getTaskName();
        str += getDescription();
        return str;
    }

}
