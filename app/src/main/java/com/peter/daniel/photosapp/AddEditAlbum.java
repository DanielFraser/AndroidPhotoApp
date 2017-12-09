package com.peter.daniel.photosapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by peterlaskai on 12/7/17.
 */

public class AddEditAlbum extends AppCompatActivity {

    private Toolbar myToolbar;
    private Button add_Button;
    private EditText album_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_album);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.colorTextWhite), PorterDuff.Mode.SRC_ATOP);

        ab.setHomeAsUpIndicator(upArrow);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Create an Album");

        add_Button = (Button) findViewById(R.id.add_button);

        album_input = (EditText) findViewById(R.id.album_name);

        add_Button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v("EditText", album_input.getText().toString());
                String album_name = album_input.getText().toString();
                User.addAlbum(album_name);
                album_input.setText("");
                finish();


            }
        });



    }


}
