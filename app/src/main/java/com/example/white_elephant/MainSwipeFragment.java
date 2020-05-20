package com.example.white_elephant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.white_elephant.models.ItemModel;

import java.util.ArrayList;

import com.example.white_elephant.views.ItemSwipeView;

public class MainSwipeFragment extends Fragment {

    private FragmentManager fragMan;

    ArrayList<ItemSwipeView> itemViewList;

    public MainSwipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BlankFragment.
     */
    public static MainSwipeFragment newInstance() {
        return new MainSwipeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_swipe_layout, container, false);

        fragMan = getChildFragmentManager();

        RelativeLayout parentView = (RelativeLayout) view.findViewById(R.id.swipe_cards_layout);

        itemViewList = new ArrayList<>();

        getArrayData();

        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        if (itemViewList.size() > 1) {
            fragTransaction.add(R.id.swipe_cards_layout, (Fragment) itemViewList.get(1));
        }
        if (!itemViewList.isEmpty()) {
            fragTransaction.add(R.id.swipe_cards_layout, itemViewList.get(0));
        }
        fragTransaction.commit();

        return view;
    }

    private ItemSwipeView popTopItem() {
        if(itemViewList.isEmpty()) {
            return null;
        }
        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        fragTransaction.remove(itemViewList.get(0));
        if(itemViewList.size() > 2) {
            fragTransaction.detach(itemViewList.get(1));
            fragTransaction.add(R.id.swipe_cards_layout, itemViewList.get(2));
            fragTransaction.attach(itemViewList.get(1));
        }
        fragTransaction.commit();

        return itemViewList.remove(0);
    }

    public void likeItem() {
        popTopItem();
    }

    public void dislikeItem() {
        popTopItem();
    }
    public void saveItem() {
        popTopItem();
    }

    private void getArrayData() {
        for(int i = 0; i < 5; i++) {
            ItemModel model = new ItemModel("Neat Shoes", "These are my super neat shoes", i * 10.0);
            ItemSwipeView view = ItemSwipeView.newInstance(model);
            itemViewList.add(view);
        }
    }
}