package com.gysttodomanager.gysttodomanager;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Yates on 4/15/2014.
 */
public class TaskArrayAdapter extends ArrayAdapter<Task> {

    int resource;
    String response;
    Context context;
    public TaskArrayAdapter(Context context, int resource, int textViewResourceId, List<Task> objects) {
        super(context, resource, textViewResourceId, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LinearLayout taskView;
        //Get the current task object
        final Task t1 = getItem(position);

        //Inflate the view
        if(convertView==null) {
            taskView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, taskView, true);
        }
        else {
            taskView = (LinearLayout) convertView;
        }
        //Get the text boxes from the list_item.xml file
        final CheckBox taskDoneBox = (CheckBox)taskView.findViewById(R.id.list_item_checkbox);
        final TextView taskTitleArea = (TextView)taskView.findViewById(R.id.list_item_title);

        //Initial setup of task items
        if(t1.isCompleted()){
            taskDoneBox.setChecked(true);
            taskTitleArea.setPaintFlags(taskTitleArea.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            taskTitleArea.setText(t1.getTaskName());
        }
        else if(!t1.isCompleted()) {
            taskDoneBox.setChecked(false);
            taskTitleArea.setPaintFlags(taskTitleArea.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            taskTitleArea.setText(t1.getTaskName());
        }

        //OnClick -- When you click the checkbox you toggle the task's completed state as well as alter the strikethrough representation
        taskDoneBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t1.isCompleted()) {
                    taskDoneBox.setChecked(false);
                    t1.setCompleted(false);
                    taskTitleArea.setPaintFlags(taskTitleArea.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    v.invalidate();
                    System.out.println(t1.isCompleted());
                }
                else {
                    taskDoneBox.setChecked(true);
                    t1.setCompleted(true);
                    taskTitleArea.setPaintFlags(taskTitleArea.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    v.invalidate();
                    System.out.println(t1.isCompleted());
                }
            }
        });

        //OnClick -- go to detail view of clicked task.
        taskTitleArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskListFragment.OnTaskSelectedListener mCallback = (TaskListFragment.OnTaskSelectedListener) getContext();
                mCallback.onTaskSelected(t1,false);
            }
        });
        return taskView;
    }

}
