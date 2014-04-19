package com.gysttodomanager.gysttodomanager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Yates on 4/1/14.
 */

/**
 * TaskDetailFragment
 * Shows the information regarding a singular task. With options to edit or delete the task.
 */
public class TaskDetailFragment extends Fragment {
    EditText titleBox;
    EditText descriptionBox;
    DatePicker datePick;
    RatingBar priorityBox;
    Button okButton;
    Button cancelButton;
    private Task task;
    private Boolean editable;
    private Calendar tempDate = new GregorianCalendar();

    /**
     * Constructor for TaskDetailFragment
     * If we are creating a new task, the passing arguments are null,true
     * That way we start with a blank slate and can edit it
     *
     * @param t        the task we are currently viewing
     * @param editable Boolean defining if we are in editing mode or not.
     */
    public TaskDetailFragment(Task t, Boolean editable) {
        if (t != null)
            task = t;
        else
            task = new Task();
        this.editable = editable;

    }

    /**
     * This allows the main activity to alter the actionBar for this fragment
     *
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //This is the key line of code here.
    }

    /**
     * This is the initial setup of the page, setting the text values and other UI elements.
     * Also sets up UI elements based upon whether or not we are in editable mode.
     * Defines the button actions.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.task_detail_fragment, container, false);

        //Get Items in Fragment
        titleBox = (EditText) rootView.findViewById(R.id.eventName);
        descriptionBox = (EditText) rootView.findViewById(R.id.eventDesc);
        datePick = (DatePicker) rootView.findViewById(R.id.datePicker);
        priorityBox = (RatingBar) rootView.findViewById(R.id.ratingBar);
        okButton = (Button) rootView.findViewById(R.id.okButton);
        cancelButton = (Button) rootView.findViewById(R.id.cancelButton);

        //Display the task's information
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
        if (!editable) {
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

        //Listens for the button Click. Updates the task to the new values, then calls the activity
        //to continue the process of saving the task.
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setTaskName(titleBox.getText().toString());
                task.setDescription(descriptionBox.getText().toString());
                task.setDateDue(tempDate);
                task.setPriority((int) priorityBox.getRating());
                task.setCompleted(false);
                OnTaskDetailListener mCallback = (OnTaskDetailListener) rootView.getContext();
                mCallback.onTaskSaved(task);
            }
        });
        //Listens for the button Click. Nothing needs to be done within the fragment, so it passes
        //the button click back up to the activity for processing.
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnTaskDetailListener mCallback = (OnTaskDetailListener) rootView.getContext();
                mCallback.onTaskDetailCancel();
            }
        });
        return rootView;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean b) {
        editable = b;
    }

    public Task getTask() {
        return task;
    }

    /**
     * This is the interface that the activity implements when either the okButton or cancelButtons are clicked
     */
    public interface OnTaskDetailListener {
        public void onTaskSaved(Task t); //okButton

        public void onTaskDetailCancel();//cancelButton
    }

}