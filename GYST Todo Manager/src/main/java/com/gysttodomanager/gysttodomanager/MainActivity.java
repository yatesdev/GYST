package com.gysttodomanager.gysttodomanager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.SQLException;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, TaskListFragment.OnTaskSelectedListener, TaskDetailFragment.OnTaskDetailListener, AppInfoFragment.onAppInfoClickListener {

    private TaskDatabase database;
    private TaskList taskList = new TaskList(); //Where all of the task data for the app is stored

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private TaskListFragment mtaskListFragment = null;
    private TaskDetailFragment mtaskDetailFragment = null;

    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        database = new TaskDatabase(this);
        try {database.open();} catch (SQLException e) {e.printStackTrace();}
        taskList.setTaskList(database.getAllTasks());
        taskList.sort();
    }

    @Override
    protected void onPause() {
        super.onPause();
        for(Task t: taskList.getTaskList()){
            database.createTask(t);
        }
    }

    /**
     * When the navDrawer item is selected, switch to the TaskListFragment which will display the proper taskList
     *
     * @param position Which navDrawer item was selected
     */
    public void onNavigationDrawerItemSelected(int position) {
        mtaskListFragment = new TaskListFragment(taskList, position);
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, mtaskListFragment)
                .commit();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    /**
     * This is the actionBar button controller. Based upon the current state of the program, certain buttons are
     * enable or disabled. I.E, disable the addButton when we are currently creating a new task anyway.
     *
     * @param menu
     * @return
     */
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu != null && menu.findItem(R.id.add_button) != null && menu.findItem(R.id.edit_button) != null && menu.findItem(R.id.delete_button) != null) {
            menu.findItem(R.id.add_button).setVisible(true);
            menu.findItem(R.id.edit_button).setVisible(true);
            menu.findItem(R.id.delete_button).setVisible(true);
            if (mtaskDetailFragment != null) {
                if (this.mtaskDetailFragment.isVisible() && !mtaskDetailFragment.getEditable()) {
                    menu.findItem(R.id.add_button).setVisible(false);
                    menu.findItem(R.id.edit_button).setVisible(true);
                    menu.findItem(R.id.delete_button).setVisible(true);
                } else if (this.mtaskDetailFragment.isVisible() && mtaskDetailFragment.getEditable()) {
                    menu.findItem(R.id.add_button).setVisible(false);
                    menu.findItem(R.id.edit_button).setVisible(false);
                    menu.findItem(R.id.delete_button).setVisible(false);
                }
            }
            if (mtaskListFragment != null) {
                if (this.mtaskListFragment.isVisible()) {
                    menu.findItem(R.id.add_button).setVisible(true);
                    menu.findItem(R.id.edit_button).setVisible(false);
                    menu.findItem(R.id.delete_button).setVisible(false);

                }
            }

        }
        return true;
    }

    /**
     * Hides the actionBar buttons when the drawer is open
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //If we want to create a new task.
        if (id == R.id.add_button) {
            invalidateOptionsMenu();
            onTaskSelected(null, true); //Go to add task_detail fragment, with editing on
        }

        //If we want to edit a currently viewed task.
        if (id == R.id.edit_button) { //Probably not the best to call everything directly but I was having issues otherwise
            mtaskDetailFragment.setEditable(!mtaskDetailFragment.getEditable());
            mtaskDetailFragment.titleBox.setClickable(true);
            mtaskDetailFragment.titleBox.setFocusableInTouchMode(true);
            mtaskDetailFragment.titleBox.setEnabled(true);
            mtaskDetailFragment.descriptionBox.setClickable(true);
            mtaskDetailFragment.descriptionBox.setFocusableInTouchMode(true);
            mtaskDetailFragment.descriptionBox.setEnabled(true);
            mtaskDetailFragment.datePick.setClickable(true);
            mtaskDetailFragment.datePick.setFocusableInTouchMode(true);
            mtaskDetailFragment.datePick.setEnabled(true);
            mtaskDetailFragment.priorityBox.setClickable(true);
            mtaskDetailFragment.priorityBox.setFocusableInTouchMode(true);
            mtaskDetailFragment.priorityBox.setEnabled(true);
            invalidateOptionsMenu();
        }
        //If we want to delete the currently viewed task.
        if (id == R.id.delete_button) {
            taskList.delete(mtaskDetailFragment.getTask());
            database.deleteTask(mtaskDetailFragment.getTask()); //Also delete from the database as well
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, mtaskListFragment)
                    .commit();
        }
        //If we want to view the appInfo screen or the credits
        if (id == R.id.action_appinfo) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new AppInfoFragment())
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTaskSelected(Task t1, Boolean b) {
        mtaskDetailFragment = new TaskDetailFragment(t1, b);
        //Go to detail view
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, mtaskDetailFragment)
                .commit();
    }

    /**
     * Adds the passed task to the taskList and then returns to the taskListFragment
     *
     * @param t
     */
    public void onTaskSaved(Task t) {
        taskList.add(t);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, mtaskListFragment)
                .commit();
    }

    /**
     * Because we didn't have any action occur, return to the taskListFragment
     */
    public void onTaskDetailCancel() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, mtaskListFragment)
                .commit();
    }

    /**
     * Return to the taskListFragment after viewing the appInfo screen.
     */
    public void onAppInfoClick() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, mtaskListFragment)
                .commit();
    }
}
