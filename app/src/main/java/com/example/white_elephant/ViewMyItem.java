package com.example.white_elephant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;

import com.example.white_elephant.models.Item;
import com.example.white_elephant.util.Storage;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewMyItem extends Fragment implements View.OnClickListener
{

    Item item;

    public ViewMyItem() {
        // Required empty public constructor
    }

    public static ViewMyItem newInstance(Item item){
        ViewMyItem v = new ViewMyItem();
        Bundle args = new Bundle();
        args.putSerializable("item", item);
        v.setArguments(args);
        return v;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        item = (Item)getArguments().getSerializable("item");
        View view = inflater.inflate(R.layout.fragment_view_my_item, container, false);
        ((TextView)view.findViewById(R.id.Title)).setText(item.getName());
        ((TextView)view.findViewById(R.id.Details)).setText(item.getDescription());
        Button goBack = view.findViewById(R.id.buttonClose);
        Button select = view.findViewById(R.id.selectItemButton);
        Storage.getInstance().getImage(item.getImageUrl(), view.findViewById(R.id.imageView), -1);
        goBack.setOnClickListener(this);
        select.setOnClickListener(this);
        return view;
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.buttonClose:
                ((MainActivity) getActivity()).setState(0);
                NavController navController = ((MainActivity) getActivity()).getNavController();
                NavDirections action = MyClosetFragmentDirections.actionMyClosetFragmentSelf();
                navController.navigate(action);
                break;
            case R.id.selectItemButton:
                ((MainActivity) getActivity()).trading = item;
                makeToast("Item Selected for Trading");
                break;
        }
    }

    private void makeToast(String str){
        Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
    }

}
