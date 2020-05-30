package com.example.white_elephant;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.white_elephant.models.Item;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewMyItem extends Fragment {

    public ViewMyItem() {
        // Required empty public constructor
    }

    public static ViewMyItem newInstance(Item item){
        ViewMyItem v = new ViewMyItem();
        Bundle args = new Bundle();
        args.putSerializable("a", item);
        v.setArguments(args);
        return v;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Item item = (Item)getArguments().getSerializable("a");
        View view = inflater.inflate(R.layout.fragment_view_my_item, container, false);
        ((TextView)view.findViewById(R.id.Title)).setText(item.getName());
        ((TextView)view.findViewById(R.id.Details)).setText(item.getDescription());
        return view;
    }
}
