package com.peter.daniel.photosapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.peter.daniel.photosapp.R;
import com.peter.daniel.photosapp.SlideShow;
import com.peter.daniel.photosapp.User;
import com.peter.daniel.photosapp.ViewAlbum;
import com.peter.daniel.photosapp.searchActivity;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerSwipeAdapter<AlbumAdapter.SimpleViewHolder> {

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView textViewPos;
        TextView textViewData;
        ImageView buttonDelete;
        ImageView buttonEdit;
        ImageView buttonView;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            //textViewPos = (TextView) itemView.findViewById(R.id.position);
            textViewData = (TextView) itemView.findViewById(R.id.text_data);
            buttonDelete = (ImageView) itemView.findViewById(R.id.delete);
            buttonEdit = (ImageView) itemView.findViewById(R.id.edit);
            buttonView = (ImageView) itemView.findViewById(R.id.view);
            swipeLayout.addDrag(SwipeLayout.DragEdge.Left, swipeLayout.findViewById(R.id.bottom_wrapper));
            swipeLayout.addDrag(SwipeLayout.DragEdge.Right, swipeLayout.findViewById(R.id.bottom_wrapper_2));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(getClass().getSimpleName(), "onItemSelected: " + textViewData.getText().toString());
                    //Toast.makeText(view.getContext(), "onItemSelected: " + textViewData.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private Context mContext;
    private ArrayList<String> mDataset;

    //protected SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

    public AlbumAdapter(Context context, ArrayList<String> objects) {
        this.mContext = context;
        this.mDataset = objects;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        String item = mDataset.get(position);
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                //YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        viewHolder.swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                mDataset.remove(position);
                User.deleteAlbum(viewHolder.textViewData.getText().toString());
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mDataset.size());
                mItemManger.closeAllItems();
                Toast.makeText(view.getContext(), "Deleted " + viewHolder.textViewData.getText().toString() + "!", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "edited " + viewHolder.textViewData.getText().toString() + "!", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Title");

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
                        boolean b = User.setAlbumName(input.getText().toString(),viewHolder.textViewData.getText().toString());
                        if(b)
                            Toast.makeText(v.getContext(), "Sucessfully changed name to: " + input.getText().toString() + "!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(v.getContext(), "Album name: " + input.getText().toString() + " already exists!", Toast.LENGTH_SHORT).show();
                    mDataset = User.getAlbumNames();
                    notifyDataSetChanged();
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
        viewHolder.buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewAlbum = new Intent(mContext, ViewAlbum.class);
                viewAlbum.putExtra("album",viewHolder.textViewData.getText().toString());
                Log.d("debug", viewHolder.textViewData.getText().toString());
                mContext.startActivity(viewAlbum);
            }
        });
        //viewHolder.textViewPos.setText((position + 1) + ".");
        viewHolder.textViewData.setText(item);
        mItemManger.bind(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public void updateList(ArrayList<String> list)
    {
        mDataset = list;
    }
}
