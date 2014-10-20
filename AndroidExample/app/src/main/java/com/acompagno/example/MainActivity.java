package com.acompagno.example;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.acompagno.example.fragments.Example1Fragment;
import com.acompagno.example.fragments.Example2Fragment;
import com.acompagno.example.fragments.Example3Fragment;
import com.acompagno.example.fragments.Example4Fragment;
import com.acompagno.example.fragments.Example5Fragment;
import com.acompagno.example.fragments.MainPageFragment;

public class MainActivity extends ActionBarActivity {

    private String[] mDrawerTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mOpenTitle;
    private CharSequence mFragmentTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//		getSupportActionBar().setIcon(R.drawable.ic_launcher_trans);
        //Set the fragment that will first appear in content frame
        this.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new MainPageFragment()).commit();
        //Set mFragmentTitle (this value is used later on, it keeps track of what the )
        mFragmentTitle = getResources().getString(R.string.app_name);

        //set the open title. this will be the title whenever the drawer is opened
        mOpenTitle = "Choose An Example";

        //Drawer layout setup
        //This array holds the text for each element in the drawer
        mDrawerTitles = new String[]{"Home", "Example 1", "Example 2", "Example 3", "Example 4", "Example 5"};
        //get the drawer layout from the xml file
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //get thelistview from the xml file
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view. the adapter sets whats going to be in the listview
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mDrawerTitles));

        // Set the list's click listener. the method for this is defined later on
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {
        //chooses which fragment will be pushed to content_frame and switches it
        final Fragment newFragment;
        switch (position) {
            case 1:
                newFragment = new Example1Fragment();
                mFragmentTitle = mDrawerTitles[position];
                break;
            case 2:
                newFragment = new Example2Fragment();
                mFragmentTitle = mDrawerTitles[position];
                break;
            case 3:
                newFragment = new Example3Fragment();
                mFragmentTitle = mDrawerTitles[position];
                break;
            case 4:
                newFragment = new Example4Fragment();
                mFragmentTitle = mDrawerTitles[position];
                break;
            case 5:
                newFragment = new Example5Fragment();
                mFragmentTitle = mDrawerTitles[position];
                break;
            default:
                newFragment = new MainPageFragment();
                mFragmentTitle = getResources().getString(R.string.app_name);
        }
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, newFragment)
                .addToBackStack("hi")
                .commit();
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);

        //Sets the title in the actionbar
        if (position == 0) {
            setTitle(getResources().getString(R.string.app_name));
        } else {
            setTitle(mDrawerTitles[position]);
        }
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    //Drawer layout methods
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    //Override what happens whent the back button is pressed
    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else if (this.getSupportFragmentManager().getBackStackEntryCount() > 0) {
            this.getSupportFragmentManager().popBackStack();
        } else {
            // make sure our application gets pushed back to the application stack
            this.moveTaskToBack(true);
            super.onBackPressed();
        }
    }

    //allows us to change the title on the action bar from a fragment
    public void changeTitleFromFragment(CharSequence title) {
        mFragmentTitle = title;
        setTitle(title);
    }
}
