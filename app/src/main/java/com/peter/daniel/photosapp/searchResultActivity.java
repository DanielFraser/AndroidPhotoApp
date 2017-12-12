package com.peter.daniel.photosapp;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.daimajia.swipe.util.Attributes;
import com.peter.daniel.photosapp.adapter.PhotoAdapter;
import com.peter.daniel.photosapp.adapter.SearchAdapter;

import java.util.ArrayList;

/**
 * Created by peterlaskai on 12/11/17.
 */

public class searchResultActivity extends AppCompatActivity {

    private GridView gridView;
    private SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        ArrayList<Photo> result = User.temp;

        //Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.colorTextWhite), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        ab.setDisplayHomeAsUpEnabled(true);

        gridView = (GridView)findViewById(R.id.search_grid);
        adapter = new SearchAdapter(this, result);
        adapter.setMode(Attributes.Mode.Multiple);
        gridView.setAdapter(adapter);
        gridView.setSelected(false);


    }
}