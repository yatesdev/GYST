package com.gysttodomanager.gysttodomanager;

import android.app.Activity;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, TaskListFragment.OnTaskSelectedListener,TaskDetailFragment.OnTaskDetailListener {

    /**
     * The ArrayLists that contain the tasks.
     */
    private TaskList taskList = new TaskList();
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private TaskListFragment mtaskListFragment = null;
    private TaskDetailFragment mtaskDetailFragment = null;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
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
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        mtaskListFragment = new TaskListFragment(taskList,position);
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, mtaskListFragment)
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section4);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

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
    @Override
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.add_button) {
            invalidateOptionsMenu();
            onTaskSelected(null, true); //Go to add task_detail fragment, with editing on
        }
        if(id == R.id.edit_button) { //Probably not the best to call everything directly but I was having issues otherwise
            System.out.println("Edit Button Pressed");
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
        if(id == R.id.delete_button) {
            System.out.println("Delete Button Pressed");
            taskList.delete(mtaskDetailFragment.getTask());
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, mtaskListFragment)
                    .commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTaskSelected(Task t1,Boolean b) {
        mtaskDetailFragment = new TaskDetailFragment(t1,b);
        //Go to detail view
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, mtaskDetailFragment)
                .commit();
    }

    @Override
    public void onTaskSaved(Task t) {
        System.out.println("I should be saving the task now");
        taskList.add(t);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, mtaskListFragment)
                .commit();
    }

    @Override
    public void onTaskDetailCancel() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, mtaskListFragment)
                .commit();
    }
}
