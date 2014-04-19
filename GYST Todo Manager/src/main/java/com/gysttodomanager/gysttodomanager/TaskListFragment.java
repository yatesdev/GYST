package com.gysttodomanager.gysttodomanager;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Yates on 4/15/2014.
 */

/**
 * TaskListFragment
 * Essentially a container for the TaskArrayAdapter definition
 * The interface defined is passing from the TaskArrayAdapter through this fragment back up to the activity
 */
public class TaskListFragment extends ListFragment {

    private TaskList taskList;
    private int currentSection;

    /**
     * Constructor
     *
     * @param t              the TaskList object contained in the activity
     * @param visibleSection the currently active tab and therefore the list we need to grab from the taskList
     */
    public TaskListFragment(TaskList t, int visibleSection) {
        taskList = t;
        currentSection = visibleSection;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.task_list, container, false);

        TaskArrayAdapter adapter = new TaskArrayAdapter(getActivity(),
                R.layout.list_task_item,
                R.id.list_item_title,
                taskList.getCurrentList(currentSection));
        setListAdapter(adapter);
        return rootView;
    }

    public interface OnTaskSelectedListener {
        public void onTaskSelected(Task t1, Boolean b); //The boolean determines if we are in editable mode or just view mode
    }
}
