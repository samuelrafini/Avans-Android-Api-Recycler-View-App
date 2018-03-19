package com.example.snf.nasamarsrover;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.EventLogTags;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SNF on 13-3-2018.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private static final String TAG = "ListAdapter";

    private ArrayList<ListItem> listItems;
    private Context mContext;

    public ListAdapter(ArrayList<ListItem> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, " calls onCreateViewHolder ");

        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, " calls onBindViewHolder ");

        final ListItem listItem = listItems.get(position);
//        Picasso.with(holder.itemView.getContext()).load(listItem.getImageURL()).into(holder.imageView);

        Picasso.with(mContext).load(listItem.getImageURL()).into(holder.imageView);

        holder.textViewTitle.setText(String.valueOf("Image ID: " + listItem.getId()));
        holder.itemListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "OnClick clicked on");
                Toast.makeText(view.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();

                Intent itemDetailIntent = new Intent(view.getContext().getApplicationContext(), ItemDetailActivity.class);
                itemDetailIntent.putExtra("imageURL", listItem.getImageURL());
                itemDetailIntent.putExtra("cameraName", listItem.getCameraName());

                view.getContext().startActivity(itemDetailIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, " calls getItemCount ");

        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private static final String TAG = "ViewHolder";

        private ConstraintLayout itemListView;
        private TextView textViewTitle;
        private ImageView imageView;

        public ViewHolder(View itemView) {

            super(itemView);

            textViewTitle = itemView.findViewById(R.id.titleItem);
            imageView = itemView.findViewById(R.id.imgView);
            itemListView = itemView.findViewById(R.id.rowList) ;

        }
    }
}
