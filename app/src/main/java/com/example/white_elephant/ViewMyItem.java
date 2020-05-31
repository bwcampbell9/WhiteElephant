package com.example.white_elephant;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.white_elephant.models.Item;
import com.example.white_elephant.models.TradeModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewMyItem extends Fragment implements View.OnClickListener
 {
     private Button goBack;

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
        goBack = view.findViewById(R.id.buttonClose);
        goBack.setOnClickListener(this);
        return view;
    }

    public void onClick(View v){
        //getActivity().getFragmentManager().beginTransaction().remove(this).commit();
        ((MainActivity)getActivity()).setState(0);
                NavController navController = ((MainActivity)getActivity()).getNavController();
                NavDirections action = MyClosetFragmentDirections.actionMyClosetFragmentSelf();
                navController.navigate(action);
    }


}
