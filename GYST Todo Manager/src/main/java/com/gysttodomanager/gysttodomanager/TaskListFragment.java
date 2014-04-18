package com.gysttodomanager.gysttodomanager;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Yates on 4/15/2014.
 */
public class TaskListFragment extends ListFragment {

    private TaskList taskList;
    private int currentSection;
    private OnTaskSelectedListener listener;

    public TaskListFragment(TaskList t,int visibleSection) {
        taskList = t;
        currentSection = visibleSection;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.task_list, container, false);

        TaskArrayAdapter adapter = new TaskArrayAdapter(getActivity(),
                R.layout.list_task_item,
                R.id.list_item_title,
                taskList.getCurrentList(currentSection));
        System.out.println(adapter.getCount());
        setListAdapter(adapter);
        return rootView;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (OnTaskSelectedListener) activity;
    }

    public interface OnTaskSelectedListener {
        public void onTaskSelected(Task t1,Boolean b); //The boolean determines if we are in editable mode or just view mode
    }
}
