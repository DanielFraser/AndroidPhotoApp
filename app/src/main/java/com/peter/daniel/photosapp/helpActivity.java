package com.peter.daniel.photosapp;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;


/**
 * Created by peterlaskai on 12/9/17.
 */

public class helpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.colorTextWhite), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        setTitle("Help");

        ab.setDisplayHomeAsUpEnabled(true);

        Button add = findViewById(R.id.btnPhoto);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable[] = {getDrawable(R.drawable.add), getDrawable(R.drawable.back),
                        getDrawable(R.drawable.magnifier)};
                String names[] = {"add","back","magnifier"};
                Bitmap bitmap;
                for(int i = 0; i < drawable.length; i++)
                {
                    Log.d("gallery", names[i]);
                    bitmap = ((BitmapDrawable)drawable[i]).getBitmap();

                    MediaStore.Images.Media.insertImage(
                            getContentResolver(),
                            bitmap,
                            names[i],
                            ""
                    );
                }
                for(int i = 0; i < 10; i++)
                {
                    Log.d("gallery", String.valueOf(i));
                    bitmap = ((BitmapDrawable)getPhotos("a"+String.valueOf(i))).getBitmap();

                    MediaStore.Images.Media.insertImage(
                            getContentResolver(),
                            bitmap,
                            "a"+i,
                            ""
                    );
                }
                //this.Resources.GetDrawable(imageResourceId);
            }
        });
    }

    private Drawable getPhotos(String name)
    {
        Resources resources = this.getResources();
        final int resourceId = resources.getIdentifier(name, "drawable",
                this.getPackageName());
        return resources.getDrawable(resourceId);
    }
}
