package com.gysttodomanager.gysttodomanager;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Yates on 4/1/14.
 */
public class TaskDetailFragment extends Fragment {
    Task task;
    Boolean editable;
    Calendar tempDate = new GregorianCalendar();

    EditText titleBox;
    EditText descriptionBox;
    DatePicker datePick;
    RatingBar priorityBox;


    public TaskDetailFragment(Task t, Boolean editable){
        if(t != null)
            task = t;
        else
            task = new Task();
        this.editable = editable;

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        //inflater.inflate(R.menu.detail_fragment_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

   /* public void onPrepareOptionsMenu(Menu menu){
        if(editable){
            menu.findItem(R.id.edit_button).setVisible(false);
            menu.findItem(R.id.delete_button).setVisible(false);
        }
        else {
            menu.findItem(R.id.edit_button).setVisible(true);
            menu.findItem(R.id.delete_button).setVisible(true);
        }
        getActivity().invalidateOptionsMenu();
    }*/
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.edit_button) {
            //getActivity().invalidateOptionsMenu();
            titleBox.setClickable(true);
            titleBox.setFocusableInTouchMode(true);
            titleBox.setEnabled(true);
            descriptionBox.setClickable(true);
            descriptionBox.setFocusableInTouchMode(true);
            descriptionBox.setEnabled(true);
            datePick.setClickable(true);
            datePick.setFocusableInTouchMode(true);
            datePick.setEnabled(true);
            priorityBox.setClickable(true);
            priorityBox.setFocusableInTouchMode(true);
            priorityBox.setEnabled(true);
            item.setEnabled(false);
            getActivity().invalidateOptionsMenu();
        }
        return super.onOptionsItemSelected(item);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.task_detail_fragment, container, false);

        //Get Items in Fragment
        titleBox = (EditText)rootView.findViewById(R.id.eventName);
        descriptionBox = (EditText)rootView.findViewById(R.id.eventDesc);
        datePick = (DatePicker)rootView.findViewById(R.id.datePicker);
        priorityBox = (RatingBar)rootView.findViewById(R.id.ratingBar);
        Button okButton = (Button)rootView.findViewById(R.id.okButton);
        Button cancelButton = (Button)rootView.findViewById(R.id.cancelButton);

        titleBox.setText(task.getTaskName());
        descriptionBox.setText(task.getDescription());
        datePick.init(task.getDateDue().get(Calendar.YEAR), task.getDateDue().get(Calendar.MONTH), task.getDateDue().get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tempDate = new GregorianCalendar(year, monthOfYear, dayOfMonth); //We will update the tasks date when the user presses OK.
            }
        });

        priorityBox.setRating(task.getPriority());

        //Disable editing if we aren't in edit mode.
        if(!editable){
            titleBox.setClickable(false);
            titleBox.setFocusableInTouchMode(false);
            descriptionBox.setClickable(false);
            descriptionBox.setFocusableInTouchMode(false);
            datePick.setClickable(false);
            datePick.setFocusableInTouchMode(false);
            datePick.setEnabled(false);
            priorityBox.setClickable(false);
            priorityBox.setFocusableInTouchMode(false);
            priorityBox.setEnabled(false);
        }



        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setTaskName(titleBox.getText().toString());
                task.setDescription(descriptionBox.getText().toString());
                task.setDateDue(tempDate);
                task.setPriority((int)priorityBox.getRating());
                task.setCompleted(false);

                OnTaskDetailListener mCallback = (OnTaskDetailListener) rootView.getContext();
                System.out.println(mCallback);
                mCallback.onTaskSaved(task);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("You clicked cancel");
                OnTaskDetailListener mCallback = (OnTaskDetailListener) rootView.getContext();
                mCallback.onTaskDetailCancel();
            }
        });
        return rootView;
    }


    public interface OnTaskDetailListener {
        public void onTaskSaved(Task t);
        public void onTaskDetailCancel();
    }

}