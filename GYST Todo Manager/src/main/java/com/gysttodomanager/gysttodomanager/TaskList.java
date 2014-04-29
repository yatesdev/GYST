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
    private ArrayList<Task> taskList;
    /**
     * Constructor that sets up the arrayLists and dummy data.
     */
    public TaskList(){
        taskList = new ArrayList<Task>();
    }

    /**
     * This returns the list needed based upon the current contextual state of the program.
     * @param section The current section being displayed on screen.
     * @return The proper arraylist for the context.
     */
    public ArrayList getCurrentList(int section){
        ArrayList<Task> tempList = new ArrayList<Task>();
        Calendar tempCal = new GregorianCalendar();
        Calendar currentDay = new GregorianCalendar(tempCal.get(Calendar.YEAR),tempCal.get(Calendar.MONTH),tempCal.get(Calendar.DAY_OF_MONTH));
        Calendar tomorrowDay = new GregorianCalendar(tempCal.get(Calendar.YEAR),tempCal.get(Calendar.MONTH),tempCal.get(Calendar.DAY_OF_MONTH));
        tomorrowDay.add(Calendar.DAY_OF_MONTH,1); //Calendar why you gotta be so stupid sometimes??

        if(section == 0){
            for(Task t : taskList){
                Calendar taskDay = new GregorianCalendar(t.getDateDue().get(Calendar.YEAR),t.getDateDue().get(Calendar.MONTH),t.getDateDue().get(Calendar.DAY_OF_MONTH));
                if(taskDay.compareTo(currentDay) <= 0){
                    tempList.add(t);
                }
            }
            sort();
            return tempList;
        }
        else if (section == 1) {
            for(Task t : taskList){
                Calendar taskDay = new GregorianCalendar(t.getDateDue().get(Calendar.YEAR),t.getDateDue().get(Calendar.MONTH),t.getDateDue().get(Calendar.DAY_OF_MONTH));
                if(taskDay.compareTo(tomorrowDay) == 0){
                    tempList.add(t);
                }
            }
            sort();
            return tempList;
        }
        else if (section == 2) {
            for(Task t : taskList){
                Calendar taskDay = new GregorianCalendar(t.getDateDue().get(Calendar.YEAR),t.getDateDue().get(Calendar.MONTH),t.getDateDue().get(Calendar.DAY_OF_MONTH));
                if(taskDay.compareTo(tomorrowDay) > 0){
                    tempList.add(t);
                }
            }
            sort();
            return tempList;
        }
        else if(section == 3) { //Not necessary in this version, however there was a plan to have an all tasks tab.
            return taskList;
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
     * @param t The new task we want to add
     */
    public void add(Task t){
        Boolean found = false;
        for (Task task : taskList){
            if(task.getUniqueID().equals(t.getUniqueID())) {
                //Since the task already exists, we can just update it
                task.setTaskName(t.getTaskName());
                task.setDescription(t.getDescription());
                task.setDateDue(t.getDateDue());
                task.setPriority(t.getPriority());
                found = true;
            }
        }
        //So the task doesn't exist yet, so now we have to add it
        if(!found) {
            taskList.add(t);
            sort();
        }
    }

    /**
     * Delete task from taskList
     * If the UniqueId's match, then delete the offending item
     */
    public void delete(Task t) {
        for(int i=0; i< taskList.size(); i++) {
            if(taskList.get(i).getUniqueID().equals(t.getUniqueID())){
                taskList.remove(i);

            }
        }
        sort();
    }

    /**
     * Sorts the arrayLists using the TaskSort Comparator
     * which sorts by completedness, priority, then task name, in that order
     */
    public void sort() {
        Collections.sort(taskList,new TaskSort());
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }
}
