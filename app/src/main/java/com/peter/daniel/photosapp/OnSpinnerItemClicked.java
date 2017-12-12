package com.peter.daniel.photosapp;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by dukey on 12/12/2017.
 */

public class OnSpinnerItemClicked implements AdapterView.OnItemSelectedListener {

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Toast.makeText(parent.getContext(), "Clicked : " +
                parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();


    }

    @Override
    public void onNothingSelected(AdapterView parent) {
        // Do nothing.
    }
}
