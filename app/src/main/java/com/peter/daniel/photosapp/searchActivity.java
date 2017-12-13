package com.peter.daniel.photosapp;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class searchActivity extends AppCompatActivity {

    private EditText personInput;
    private EditText locationInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.colorTextWhite), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        ab.setDisplayHomeAsUpEnabled(true);

        personInput = (EditText) findViewById(R.id.PersonInput);
        locationInput = (EditText) findViewById(R.id.LocationInput);

        Button searchButton = (Button) findViewById(R.id.SearchButton);
        searchButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
               String person_value = personInput.getText().toString();
               String location_value = locationInput.getText().toString();

               User.temp = User.search(location_value,person_value);

               Intent myIntent = new Intent(searchActivity.this, searchResultActivity.class);
//               myIntent.putExtra("photo_results", temp);
               searchActivity.this.startActivity(myIntent);
            }
        });

    }
}
