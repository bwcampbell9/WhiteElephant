package com.example.white_elephant.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.white_elephant.R;
import com.example.white_elephant.models.Item;
import com.example.white_elephant.util.Storage;

public class ItemTileView extends Fragment {

    /**
     * Create a new instance of ItemSwipeView, initialized to
     * show the view of the provided Item.
     */
    public static ItemTileView newInstance(Item model) {
        ItemTileView f = new ItemTileView();

        // Supply model input as an argument.
        Bundle args = new Bundle();
        args.putParcelable("model", model);
        f.setArguments(args);

        return f;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.item_tile_layout, container, false);

        TextView textView = (TextView) view.findViewById(R.id.titleText);
        TextView valueView = (TextView) view.findViewById(R.id.valueText);
        ImageView imageView = view.findViewById(R.id.imageView);

        Item model = getModel();

        textView.setText(model.getName());
        valueView.setText("Value: $" + model.getValue());

        Storage.getInstance().getImage(model.getImageUrl(), imageView, -1);

        return view;
    }


    public Item getModel() {
        return getArguments().getParcelable("model");
    }
}
