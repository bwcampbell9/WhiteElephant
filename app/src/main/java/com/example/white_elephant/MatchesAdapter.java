package com.example.white_elephant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.white_elephant.models.TradeModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {

    private List<TradeModel> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    StorageReference mStorageRef;

    // data is passed into the constructor
    public MatchesAdapter(Context context, List<TradeModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.trade_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TradeModel item = mData.get(position);
        holder.item1name.setText(item.getItem1().getName());
        holder.item2name.setText(item.getItem2().getName());
        holder.item1value.setText(Double.toString(item.getItem1().getValue()));
        holder.item2value.setText(Double.toString(item.getItem2().getValue()));



        // attempts to retrieve image from firebase
        // still not working
        // duplicate for item2 if you get it working
        if (item.getItem1().getImageUrl().length() > 0){
            mStorageRef = FirebaseStorage.getInstance().getReference("Item").child(item.getItem1().getImageUrl());
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView item1name;
        TextView item2name;
        TextView item1value;
        TextView item2value;
        ImageView item1pic;
        ImageView item2pic;

        ViewHolder(View tradeView) {
            super(tradeView);
            item1name = tradeView.findViewById(R.id.item1title);
            item2name = tradeView.findViewById(R.id.item2title);
            item1value = tradeView.findViewById(R.id.item1value);
            item2value = tradeView.findViewById(R.id.item2value);
            item1pic = tradeView.findViewById(R.id.item1pic);
            item2pic = tradeView.findViewById(R.id.item2pic);
            tradeView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    TradeModel getTrade(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
