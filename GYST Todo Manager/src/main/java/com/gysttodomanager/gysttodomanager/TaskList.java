package com.gysttodomanager.gysttodomanager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import java.util.GregorianCalendar;


/**
 * Created by Yates on 4/10/14.
 */

/**
 * This is the overarching container for the tasks. Essentially functions just like an arrayList within the context
 * of the rest of the program but in my mind it made more sense to separate each list out rather than pull from a subset
 * from a larger combined list each time I needed data. In hindsight this is probably stupid.
 */
public class TaskList {
    private ArrayList<Task> todayList;
    private ArrayList<Task> tomorrowList;
    private ArrayList<Task> upcomingList;

    /**
     * Constructor that sets up the arrayLists and dummy data.
     */
    public TaskList(){
        todayList = new ArrayList<Task>();
        tomorrowList = new ArrayList<Task>();
        upcomingList = new ArrayList<Task>();

        //This is the dummy data I added to the lists for testing purposes
        todayList.add(new Task("Test",new GregorianCalendar(),1,"This is a test",false));
        todayList.add(new Task("Test #2",new GregorianCalendar(),2,"This is also a test",true));
        tomorrowList.add(new Task("Tomorrow Test",new GregorianCalendar(),1,"Test Tomorrow",false));
        upcomingList.add(new Task("Upcoming Test",new GregorianCalendar(),1,"Test Upcoming",false));
        sort();
    }

    /**
     * This returns the list needed based upon the current contextual state of the program.
     * @param section The current section being displayed on screen.
     * @return The proper arraylist for the context.
     */
    public ArrayList getCurrentList(int section){
        sort();
        if(section == 0){
            return todayList;
        }
        else if (section == 1) {
            return tomorrowList;
        }
        else if (section == 2) {
            return upcomingList;
        }
        else if(section == 3) { //Not necessary in this version, however there was a plan to have an all tasks tab.
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
     * Add Task to taskList -- also called when updating a previously created task
     * Compares the UniqueID of the new task and the old tasks, if they are equivalent then update
     * otherwise add the new task to the list.
     *
     * This might need to be bug checked as I think there is an edge case where bad things happen.
     * (Something like changing the due date on an old task. If it is found in today but the new due date is
     * tomorrow, it won't move to the new list because it technically already exists)
     * @param t The new task we want to add
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
            }
        }
        if(!found) {
            for (Task task : tomorrowList) {
                if (task.getUniqueID().equals(t.getUniqueID())) {
                    //Since the task already exists, we can just update it
                    task.setTaskName(t.getTaskName());
                    task.setDescription(t.getDescription());
                    task.setDateDue(t.getDateDue());
                    task.setPriority(t.getPriority());
                    found = true;
                }
            }
        }
        if(!found) {
            for (Task task : upcomingList) {
                if (task.getUniqueID().equals(t.getUniqueID())) {
                    //Since the task already exists, we can just update it
                    task.setTaskName(t.getTaskName());
                    task.setDescription(t.getDescription());
                    task.setDateDue(t.getDateDue());
                    task.setPriority(t.getPriority());
                    found = true;
                }
            }
        }
        //So the new task doesn't already exist, so now we have to add it
        if(!found){
            Calendar tempCal = new GregorianCalendar();
            Calendar currentDay = new GregorianCalendar(tempCal.get(Calendar.YEAR),tempCal.get(Calendar.MONTH),tempCal.get(Calendar.DAY_OF_MONTH));
            Calendar tomorrowDay = new GregorianCalendar(tempCal.get(Calendar.YEAR),tempCal.get(Calendar.MONTH),tempCal.get(Calendar.DAY_OF_MONTH));
            tomorrowDay.add(Calendar.DAY_OF_MONTH,1); //Calendar why you gotta be so stupid sometimes??
            Calendar taskDay = new GregorianCalendar(t.getDateDue().get(Calendar.YEAR),t.getDateDue().get(Calendar.MONTH),t.getDateDue().get(Calendar.DAY_OF_MONTH));

            //Decide which list to add to.
            //TodayList check
            if(taskDay.compareTo(currentDay) <= 0){
                todayList.add(t);
            }
            //TomorrowList Check
            else if(taskDay.compareTo(tomorrowDay) == 0) {
                tomorrowList.add(t);
            }
            //UpcomingList Check
            else {
                upcomingList.add(t);
            }
        }
        sort();
    }

    /**
     * Delete task from taskList
     * If the UniqueId's match, then delete the offending item
     */
    public void delete(Task t) {
        for(int i=0; i< todayList.size(); i++) {
            if(todayList.get(i).getUniqueID().equals(t.getUniqueID())){
                todayList.remove(i);
            }
        }
        for(int i=0; i< tomorrowList.size(); i++) {
            if(tomorrowList.get(i).getUniqueID().equals(t.getUniqueID())){
                tomorrowList.remove(i);
            }
        }
        for(int i=0; i< upcomingList.size(); i++) {
            if(upcomingList.get(i).getUniqueID().equals(t.getUniqueID())){
                upcomingList.remove(i);
            }
        }
        sort();
    }

    /**
     * Sorts the arrayLists using the TaskSort Comparator
     * which sorts by completedness, priority, then task name, in that order
     */
    public void sort() {
        Collections.sort(todayList,new TaskSort());
        Collections.sort(tomorrowList,new TaskSort());
        Collections.sort(upcomingList,new TaskSort());
    }
}
