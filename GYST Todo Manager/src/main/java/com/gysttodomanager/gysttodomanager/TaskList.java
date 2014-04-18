package com.gysttodomanager.gysttodomanager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Yates on 4/10/14.
 */
public class TaskList {

    private ArrayList<Task> todayList;
    private ArrayList<Task> tomorrowList;
    private ArrayList<Task> upcomingList;


    public TaskList(){
        todayList = new ArrayList<Task>();
        tomorrowList = new ArrayList<Task>();
        upcomingList = new ArrayList<Task>();

        todayList.add(new Task("Test",new GregorianCalendar(),1,"This is a test",false));
        todayList.add(new Task("Test #2",new GregorianCalendar(),2,"This is also a test",true));
        tomorrowList.add(new Task("Tomorrow Test",new GregorianCalendar(),1,"Test Tomorrow",false));
        upcomingList.add(new Task("Upcoming Test",new GregorianCalendar(),1,"Test Upcoming",false));
    }

    public ArrayList getCurrentList(int section){
        if(section == 0){
            return todayList;
        }
        else if (section == 1) {
            return tomorrowList;
        }
        else if (section == 2) {
            return upcomingList;
        }
        else if(section == 3) {
            ArrayList<Task> temp = new ArrayList();
            temp.addAll(todayList);
            temp.addAll(tomorrowList);
            temp.addAll(upcomingList);
            return temp;
        }
        else {
            return new ArrayList();
        }
    }
    /**
     * Add Task to taskList -- is also called when updating a previously created task
     */
    public void add(Task t){
        Boolean found = false;
        for (Task task : todayList){
            if(task.getUniqueID().equals(t.getUniqueID())) {
                //Since the task already exists, we can just update it
                task.setTaskName(t.getTaskName());
                task.setDescription(t.getDescription());
                task.setDateDue(t.getDateDue());
                task.setPriority(t.getPriority());
                found = true;
                System.out.println("This task already exists in the current form");
            }
        }
        if(found == false) {
            for (Task task : tomorrowList) {
                if (task.getUniqueID().equals(t.getUniqueID())) {
                    //Since the task already exists, we can just update it
                    task.setTaskName(t.getTaskName());
                    task.setDescription(t.getDescription());
                    task.setDateDue(t.getDateDue());
                    task.setPriority(t.getPriority());
                    found = true;
                    System.out.println("This task already exists in the current form");
                }
            }
        }
        if(found == false) {
            for (Task task : upcomingList) {
                if (task.getUniqueID().equals(t.getUniqueID())) {
                    //Since the task already exists, we can just update it
                    task.setTaskName(t.getTaskName());
                    task.setDescription(t.getDescription());
                    task.setDateDue(t.getDateDue());
                    task.setPriority(t.getPriority());
                    found = true;
                    System.out.println("This task already exists in the current form");
                }
            }
        }
        if(found == false){
            Calendar tempCal = new GregorianCalendar();
            Calendar currentDay = new GregorianCalendar(tempCal.get(Calendar.YEAR),tempCal.get(Calendar.MONTH),tempCal.get(Calendar.DAY_OF_MONTH));
            Calendar tomorrowDay = new GregorianCalendar(tempCal.get(Calendar.YEAR),tempCal.get(Calendar.MONTH),tempCal.get(Calendar.DAY_OF_MONTH));
            tomorrowDay.add(Calendar.DAY_OF_MONTH,1); //Calendar why you gotta be so stupid sometimes??
            Calendar taskDay = new GregorianCalendar(t.getDateDue().get(Calendar.YEAR),t.getDateDue().get(Calendar.MONTH),t.getDateDue().get(Calendar.DAY_OF_MONTH));

            //Decide which list to add to.
            //TodayList check
            if(taskDay.compareTo(currentDay) <= 0){
                System.out.println("Added to todayList");
                todayList.add(t);
            }

            //TomorrowList Check
            else if(taskDay.compareTo(tomorrowDay) == 0) {
                System.out.println("Added to TomorrowList");
                tomorrowList.add(t);
            }
            //UpcomingList Check
            else {
                System.out.println("Added to UpcomingList");
                upcomingList.add(t);
            }
        }
    }

    /**
     * Delete task from taskList
     */
    public void delete(Task t) {

    }
}
