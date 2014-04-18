package com.gysttodomanager.gysttodomanager;

import java.util.Comparator;

/**
 * Created by Yates on 4/18/2014.
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
