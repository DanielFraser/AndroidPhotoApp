package com.peter.daniel.photosapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SlideShow extends FragmentActivity {
    static final int NUM_ITEMS = 6;
    ImageFragmentPagerAdapter imageFragmentPagerAdapter;
    ViewPager viewPager;
    String album = getIntent().getStringExtra("album");
    ArrayList<Photo> photos = User.getPhotos(album);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slideshow);
        imageFragmentPagerAdapter = new ImageFragmentPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(imageFragmentPagerAdapter);
    }

    public static class ImageFragmentPagerAdapter extends FragmentPagerAdapter {
        public ImageFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
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


            View swipeView = inflater.inflate(R.layout.slideshowphoto, container, false);
            ImageView imageView = (ImageView) swipeView.findViewById(R.id.imageView);
            TextView location = (TextView) swipeView.findViewById(R.id.location);
            TextView person = (TextView) swipeView.findViewById(R.id.person);

            Bundle bundle = getArguments();
            int position = bundle.getInt("position");
            String imageFileName = User.temp.get(position).getLocation();
            location.setText(User.temp.get(position).getLocationTag());
            person.setText(User.temp.get(position).getPersonTag());
            int imgResId = getResources().getIdentifier(imageFileName, "drawable", "com.javapapers.android.swipeimageslider");
            imageView.setImageResource(imgResId);
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
}