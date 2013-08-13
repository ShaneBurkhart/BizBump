package com.bizbump.controller;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bizbump.R;
import com.bizbump.view.fragment.MyCards;
import com.bizbump.view.fragment.Settings;
import com.bizbump.view.fragment.find.Find;
import com.bizbump.view.fragment.find.FindByEmail;
import com.bizbump.view.fragment.find.FindNearMe;
import com.bizbump.view.fragment.find.FindByQR;
import com.bizbump.view.fragment.share.Share;

public class MainActivity extends ActionBarActivity {

    public static final int NONE = 0x0;
    public static final int MY_CARDS = 0x1;
    public int actionBarState = NONE;

    ActionBarDrawerToggle drawerToggle;
    ListView drawerList;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up drawer menu
        String[] drawerItems = getResources().getStringArray(R.array.drawer_items);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.drawer_list);
        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item, drawerItems));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        drawerToggle = new ActionBarDrawerToggle(this, drawer, R.drawable.ic_navigation_drawer, R.drawable.ic_navigation_drawer, R.drawable.ic_navigation_drawer);
        drawer.setDrawerListener(drawerToggle);

        //Check if container exists
        if(findViewById(R.id.fragment_container) != null){
            //Now add some fragment.
            //Return if already opened or something like that.
            if(savedInstanceState != null){
                return;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new MyCards()).commit();
        }
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    protected void onResume() {
        //Not going to work here since this is called every time activity opens
        /*drawerList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        TextView item = (TextView) drawerList.getChildAt(0);
        if(item != null){
            Log.d("Pos", drawerList.getFirstVisiblePosition() + "");
            item.setTextColor(getResources().getColor(android.R.color.white));
            item.setBackgroundColor(getResources().getColor(R.color.action_background));
        }
        drawerList.setSelection(0);*/
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        int id;
        switch (actionBarState) {
            case NONE:
                id = R.menu.none;
                break;
            case MY_CARDS:
                id = R.menu.my_cards;
                break;
            default:
                id = R.id.none;
                break;
        }
        inflater.inflate(id, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Fragment frag = getFragmentFromPosition(i);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frag).commit();
            drawer.closeDrawers();
        }
    }

    private Fragment getFragmentFromPosition(int pos){
        switch (pos){
            case 0:
                return new MyCards();
            case 1:
                return new Share();
            case 2:
                return new Find();
            case 3:
                return new Settings();
            default:
                return new MyCards();
        }
    }
}
