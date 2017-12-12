package com.peter.daniel.photosapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.peter.daniel.photosapp.OnSpinnerItemClicked;
import com.peter.daniel.photosapp.Photo;
import com.peter.daniel.photosapp.R;
import com.peter.daniel.photosapp.SlideShow;
import com.peter.daniel.photosapp.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhotoAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private ArrayList<Photo> photos;
    private String name;
    //private int pos = 2;

    public PhotoAdapter(Context mContext, ArrayList<Photo> photos, String name) {
        this.mContext = mContext;
        this.photos = photos;
        this.name = name;
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
                viewAlbum.putExtra("album",name);
                viewAlbum.putExtra("pos",(Integer)(view.findViewById(R.id.view)).getTag());
                mContext.startActivity(viewAlbum);
            }
        });

        ImageView delete = convertView.findViewById(R.id.trash2);
        delete.setTag(Integer.valueOf(position));
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.getAlbum(name).removePhoto((Integer)(view.findViewById(R.id.trash2)).getTag());
                modifyList(User.getPhotos(name));
                Log.d("album:", Arrays.toString(User.getAlbum(name).getPhotos().toArray()));
                Log.d("trash2", "removed:" + (view.findViewById(R.id.trash2)).getTag());
            }
        });

        final ImageView move = convertView.findViewById(R.id.move);
        move.setTag(Integer.valueOf(position));
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int i = User.getAlbum(name).getPhotos().get((Integer)(view.findViewById(R.id.move)).getTag());
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                LinearLayout layout       = new LinearLayout(mContext);
                final Spinner choice = new Spinner(mContext);
                final ArrayList<String> names = User.getAlbumNames();
                names.remove(name);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext,
                        android.R.layout.simple_spinner_item, names);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                choice.setAdapter(dataAdapter);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(choice);
                //layout.addView(etInput);
                layout.setPadding(50, 40, 50, 10);

                builder.setView(layout);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    User.getAlbum(name).removePhoto((Integer) move.getTag());
                    User.getAlbum(choice.getSelectedItem().toString()).addPhoto(i);
                    modifyList(User.getPhotos(name));
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.create().show();
                //User.getAlbum("name2").addPhoto(i);
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

    public void modifyList(ArrayList<Photo> photos)
    {
        this.photos = photos;
        notifyDatasetChanged();
    }
}
