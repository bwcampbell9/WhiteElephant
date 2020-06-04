package com.example.white_elephant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.white_elephant.models.Item;
import com.example.white_elephant.models.TradeModel;
import com.example.white_elephant.util.Storage;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {

    private List<TradeModel> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MatchesAdapter(Context context, List<TradeModel> data) {
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

        TradeModel trade = mData.get(position);

        holder.title1Text.setText(trade.getItem1().getName());
        holder.title2Text.setText(trade.getItem2().getName());;
        holder.value1Text.setText("Value: $" + trade.getItem1().getValue());
        holder.value2Text.setText("Value: $" + trade.getItem2().getValue());

        Storage.getInstance().getImage(trade.getItem1().getImageUrl(), holder.image1View, -1);
        Storage.getInstance().getImage(trade.getItem2().getImageUrl(), holder.image2View, -1);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title1Text;
        TextView title2Text;
        TextView value1Text;
        TextView value2Text;
        ImageView image1View;
        ImageView image2View;

        ViewHolder(View itemView) {
            super(itemView);
            title1Text = itemView.findViewById(R.id.item1title);
            value1Text = itemView.findViewById(R.id.item1value);
            image1View = itemView.findViewById(R.id.item1pic);
            title2Text = itemView.findViewById(R.id.item2title);
            value2Text = itemView.findViewById(R.id.item2value);
            image2View = itemView.findViewById(R.id.item2pic);

            itemView.setOnClickListener(this);
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
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}