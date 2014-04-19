package com.gysttodomanager.gysttodomanager;

import java.util.Comparator;

/**
 * Created by Yates on 4/18/2014.
 */

/**
 * TaskSort
 * This is the defined comparison class used to sort the items contained in a TaskList
 * This current implementation sorts by three levels
 * Completness - uncompleted come before completed tasks
 * Priority - higher priority tasks come before lower priority tasks
 * Task Name - the last level of sorting is based upon the title of the task
 */
public class TaskSort implements Comparator<Task>{
    @Override
    public int compare(Task t1, Task t2){
        if(!t1.isCompleted() && t2.isCompleted()){
            return -1;
        }
        else if(t1.isCompleted() && !t2.isCompleted()){
            return 1;
        }
        else {
            if (t1.getPriority() < t2.getPriority()) {
                return 1;
            } else if (t1.getPriority() > t2.getPriority()) {
                return -1;
            } else {
                return t1.getTaskName().compareTo(t2.getTaskName());
            }
        }
    }
}
