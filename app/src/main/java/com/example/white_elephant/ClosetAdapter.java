package com.example.white_elephant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.white_elephant.models.ItemModel;
import com.example.white_elephant.util.GlideApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ClosetAdapter extends RecyclerView.Adapter<ClosetAdapter.ViewHolder> {

    private List<ItemModel> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    StorageReference mStorageRef;

    // data is passed into the constructor
    ClosetAdapter(Context context, List<ItemModel> data) {
        mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ItemModel item = mData.get(position);
        holder.nameTextView.setText(item.getName());
        holder.descTextView.setText(item.getDescription());
        holder.valueTextView.setText(Double.toString(item.getValue()));

        // attempts to retrieve image from firebase
        // still not working
        if (item.getImageUrl().length() > 0){
            mStorageRef = FirebaseStorage.getInstance().getReference("Item").child(item.getImageUrl());
            GlideApp.with(holder.itemView.getContext()).load(mStorageRef).into(holder.myImage);
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTextView;
        TextView descTextView;
        TextView valueTextView;
        ImageView myImage;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.listNameTextView);
            descTextView = itemView.findViewById(R.id.listDescTextView);
            valueTextView = itemView.findViewById(R.id.listValTextView);
            myImage = itemView.findViewById(R.id.listImageView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    ItemModel getItem(int id) {
       return mData.get(id);
     }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}