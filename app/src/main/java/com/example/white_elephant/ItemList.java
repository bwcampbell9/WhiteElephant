package com.example.white_elephant;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.white_elephant.Models.ItemModel;

import java.util.List;

public class ItemList extends ArrayAdapter<ItemModel> {

    private Activity context;
    private List<ItemModel> itemList;

    TextView listNameTextView;
    TextView listDescTextView;
    TextView listValTextView;

    public ItemList(Activity context, List<ItemModel> itemList){

        super(context, R.layout.list_layout, itemList);
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        listNameTextView = (TextView) listViewItem.findViewById(R.id.listNameTextView);
        listDescTextView = (TextView) listViewItem.findViewById(R.id.listDescTextView);
        listValTextView = (TextView) listViewItem.findViewById(R.id.listValTextView);

        ItemModel item = itemList.get(position);

        listNameTextView.setText(item.getName());
        listDescTextView.setText(item.getDescription());
        listValTextView.setText(item.getValue() + "");

        return listViewItem;

    }
}
