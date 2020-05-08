package com.example.white_elephant.Views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.white_elephant.Models.ItemModel;
import com.example.white_elephant.R;

public class ItemSwipeView extends Fragment implements BaseView {

    /**
     * Create a new instance of ItemSwipeView, initialized to
     * show the view of the provided itemModel.
     */
    public static ItemSwipeView newInstance(ItemModel model) {
        ItemSwipeView f = new ItemSwipeView();

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
        View view = inflater.inflate(R.layout.item_swipe_layout, container, false);

        TextView textView = (TextView) view.findViewById(R.id.titleText);
        TextView descriptionView = (TextView) view.findViewById(R.id.descriptionText);

        ItemModel model = getModel();

        textView.setText(model.getName() + ", $" + model.getValue());
        descriptionView.setText(model.getDescription());

        return view;
    }


    public ItemModel getModel() {
        return getArguments().getParcelable("model");
    }


    @Override
    public void updateAll() {

    }
}
