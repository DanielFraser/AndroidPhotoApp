package com.peter.daniel.photosapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.peter.daniel.photosapp.Photo;
import com.peter.daniel.photosapp.R;

import java.util.ArrayList;

public class PhotoAdapter extends BaseSwipeAdapter {

    private Context mContext;
    ArrayList<Photo> photos;

    public PhotoAdapter(Context mContext, ArrayList<Photo> photos) {
        this.mContext = mContext;
        this.photos = photos;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(R.layout.photoitem, null);
    }

    @Override
    public void fillValues(int position, View convertView) {
        TextView t = (TextView)convertView.findViewById(R.id.position);
        t.setText((position + 1 )+".");
        ImageView iv = (ImageView) convertView.findViewById(R.id.trash);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(getClass().getSimpleName(), "onItemSelected: " + textViewData.getText().toString());
                Toast.makeText(view.getContext(), "onItemSelected!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
