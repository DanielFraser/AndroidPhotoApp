package com.peter.daniel.photosapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.peter.daniel.photosapp.Photo;
import com.peter.daniel.photosapp.R;

import java.util.ArrayList;

public class PhotoAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private ArrayList<Photo> photos;

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
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;

        // get layout from mobile.xml
        gridView = inflater.inflate(R.layout.photoitem, null);


        return gridView;
        //return LayoutInflater.from(mContext).inflate(R.layout.photoitem, null);
    }

    @Override
    public void fillValues(int position, View convertView) {
        // set value into textview
        ImageView iv = convertView.findViewById(R.id.imageView);
        int imgResId = mContext.getResources().getIdentifier(photos.get(position).getLocation(),
                "drawable", "com.peter.daniel.photosapp");
        iv.setImageResource(imgResId);
        //Log.d("test", photos.get(position).getLocation());
        SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipe);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, swipeLayout.findViewById(R.id.bottom_wrapper1));
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, swipeLayout.findViewById(R.id.bottom_wrapper2));
        /*TextView t = (TextView)convertView.findViewById(R.id.position);
        t.setText((position + 1 )+".");*/
        //ImageView iv = convertView.findViewById(R.id.view);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent viewAlbum = new Intent(mContext, SlideShow.class);
                viewAlbum.putExtra("album",view.iv.getText().toString());
                //Log.d("debug", viewHolder.textViewData.getText().toString());
                mContext.startActivity(viewAlbum);*/
            }
        });
    }

    @Override
    public int getCount() {
        return photos.size();
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
