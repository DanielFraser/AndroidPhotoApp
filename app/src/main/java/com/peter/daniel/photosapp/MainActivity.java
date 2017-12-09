package com.peter.daniel.photosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;
import com.peter.daniel.photosapp.adapter.AlbumAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

public class MainActivity extends AppCompatActivity {

    private Toolbar myToolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    private ArrayList<String> mDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.albumList);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Photos");
        setSupportActionBar(myToolbar);

        // Layout Managers:
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Item Decorator:
        recyclerView.addItemDecoration(new com.peter.daniel.photosapp.adapter.util.DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        //recyclerView.setItemAnimator(new FadeInLeftAnimator());

        // Adapter:
        String[] adapterData = new String[]{"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};
        mDataSet = new ArrayList<String>(Arrays.asList(adapterData));
        mAdapter = new AlbumAdapter(this, mDataSet);
        ((AlbumAdapter) mAdapter).setMode(Attributes.Mode.Single);
        recyclerView.setAdapter(mAdapter);

        /* Listeners */
        recyclerView.setOnScrollListener(onScrollListener);
    }

    /**
     * Substitute for our onScrollListener for RecyclerView
     */
    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.e("ListView", "onScrollStateChanged");
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // Could hide open views here if you wanted. //
        }
    };

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent myIntent = new Intent(this, AddEditAlbum.class);
                this.startActivity(myIntent);
                break;

            case R.id.action_search:
                Intent search_intent = new Intent(this, searchActivity.class);
                this.startActivity(search_intent);
                break;

            case R.id.action_help:
                displayPopupWindow();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void displayPopupWindow() {
        PopupWindow popup = new PopupWindow(this);
        View layout = getLayoutInflater().inflate(R.layout.help_popup, null);
        popup.setContentView(layout);
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);
    }
}
