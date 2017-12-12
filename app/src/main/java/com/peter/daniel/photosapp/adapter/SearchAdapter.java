package com.peter.daniel.photosapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.peter.daniel.photosapp.Photo;
import com.peter.daniel.photosapp.R;
import com.peter.daniel.photosapp.SlideShow;
import com.peter.daniel.photosapp.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by peterlaskai on 12/12/17.
 */

public class SearchAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private ArrayList<Photo> photos;
    private String name;
    //private int pos = 2;

    public SearchAdapter(Context mContext, ArrayList<Photo> photos) {
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
        Log.d("poss2", "onClick2: " + position);
        ImageView iv = convertView.findViewById(R.id.imageView);
        iv.setTag(Integer.valueOf(position));
        String imageFileName = User.temp.get(position).getLocation();
        File imgFile = new File(imageFileName);
        if(imgFile.exists())
        {
            InputStream imageStream = null;
            try {
                imageStream = mContext.getContentResolver().openInputStream(Uri.fromFile(imgFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            int nh = (int) ( selectedImage.getHeight() * (512.0 / selectedImage.getWidth()) );
            Bitmap scaled = Bitmap.createScaledBitmap(selectedImage, 512, nh, true);
            iv.setImageBitmap(scaled);
        }

        //Log.d("test", photos.get(position).getLocation());
        SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipe);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, swipeLayout.findViewById(R.id.bottom_wrapper1));
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, swipeLayout.findViewById(R.id.bottom_wrapper2));

        ImageView iv2 = convertView.findViewById(R.id.view);
        iv2.setTag(Integer.valueOf(position));
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewAlbum = new Intent(mContext, SlideShow.class);
//                viewAlbum.putExtra("album",name);
                viewAlbum.putExtra("pos",(Integer)(view.findViewById(R.id.view)).getTag());
                mContext.startActivity(viewAlbum);
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
