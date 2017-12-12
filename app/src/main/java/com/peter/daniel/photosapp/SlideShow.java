package com.peter.daniel.photosapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SlideShow extends FragmentActivity {
    static final int NUM_ITEMS = 6;
    ImageFragmentPagerAdapter imageFragmentPagerAdapter;
    ViewPager viewPager;
    String album;
    ArrayList<Photo> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slideshow);
        //album = getIntent().getStringExtra("album");
        int pos = getIntent().getIntExtra("pos", 0);
        Log.d("int", "pos: " + pos);
        photos = User.temp;
        imageFragmentPagerAdapter = new ImageFragmentPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(imageFragmentPagerAdapter);
        viewPager.setCurrentItem(pos);
    }

    public static class ImageFragmentPagerAdapter extends FragmentPagerAdapter {
        public ImageFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return User.temp.size();
        }

        @Override
        public Fragment getItem(int position) {
            SwipeFragment fragment = new SwipeFragment();
            return SwipeFragment.newInstance(position);
        }
    }

    public static class SwipeFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            final View swipeView = inflater.inflate(R.layout.slideshowphoto, container, false);
            ImageView imageView = swipeView.findViewById(R.id.imageView);
            final TextView location = swipeView.findViewById(R.id.location);
            final TextView person = swipeView.findViewById(R.id.person);
            Button rmPerson = swipeView.findViewById(R.id.rmPerson);
            Button rmLocation = swipeView.findViewById(R.id.rmLocation);
            Button personAE = swipeView.findViewById(R.id.personBtn);
            Button locationAE = swipeView.findViewById(R.id.locationBtn);

            Bundle bundle = getArguments();
            final int position = bundle.getInt("position");
            //person add/edit tag
            personAE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Add/Edit person tag");

                    // Set up the input
                    final EditText input = new EditText(view.getContext());
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    final View v = view;

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            User.temp.get(position).setPersonTag(input.getText().toString());
                            Toast.makeText(v.getContext(), "Changed person tag to: " + input.getText().toString(), Toast.LENGTH_SHORT).show();
                            person.setText(input.getText().toString());
                            person.setVisibility(View.VISIBLE);
                            swipeView.findViewById(R.id.uPerson).setVisibility(View.VISIBLE);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            });
            //location add/edit tag
            locationAE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Add/Edit location tag");

                    // Set up the input
                    final EditText input = new EditText(view.getContext());
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    final View v = view;

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(v.getContext(), "Changed location tag to: " + input.getText().toString(), Toast.LENGTH_SHORT).show();
                            User.temp.get(position).setLocationTag(input.getText().toString());
                            location.setText(input.getText().toString());
                            location.setVisibility(View.VISIBLE);
                            swipeView.findViewById(R.id.uLocation).setVisibility(View.VISIBLE);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            });
            //person delete tag
            rmPerson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Deleted person tag!", Toast.LENGTH_SHORT).show();
                    User.temp.get(position).setPersonTag("");
                    person.setVisibility(View.INVISIBLE);
                    swipeView.findViewById(R.id.uPerson).setVisibility(View.INVISIBLE);
                }
            });
            //location delete tag
            rmLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Deleted location tag!", Toast.LENGTH_SHORT).show();
                    User.temp.get(position).setLocationTag("");
                    location.setVisibility(View.INVISIBLE);
                    swipeView.findViewById(R.id.uLocation).setVisibility(View.INVISIBLE);
                }
            });
            String imageFileName = User.temp.get(position).getLocation();
            File imgFile = new File(imageFileName);
            if(imgFile.exists())
            {
                InputStream imageStream = null;
                try {
                    imageStream = swipeView.getContext().getContentResolver().openInputStream(Uri.fromFile(imgFile));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                int nh = (int) ( selectedImage.getHeight() * (512.0 / selectedImage.getWidth()) );
                Bitmap scaled = Bitmap.createScaledBitmap(selectedImage, 512, nh, true);
                imageView.setImageBitmap(scaled);
            }

            if (User.temp.get(position).getLocationTag().equals("")) {
                location.setVisibility(View.INVISIBLE);
                swipeView.findViewById(R.id.uLocation).setVisibility(View.INVISIBLE);
            } else
                location.setText(User.temp.get(position).getLocationTag());
            if (User.temp.get(position).getPersonTag().equals("")) {
                person.setVisibility(View.INVISIBLE);
                swipeView.findViewById(R.id.uPerson).setVisibility(View.INVISIBLE);
            } else
                person.setText(User.temp.get(position).getPersonTag());

            //int imgResId = getResources().getIdentifier(imageFileName, "drawable", "com.peter.daniel.photosapp");

            return swipeView;
        }

        static SwipeFragment newInstance(int position) {
            SwipeFragment swipeFragment = new SwipeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            swipeFragment.setArguments(bundle);
            return swipeFragment;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        User.saveAll();
    }
}