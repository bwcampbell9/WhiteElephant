package com.example.white_elephant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.white_elephant.Models.ItemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainSwipeView extends AppCompatActivity {


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.white_elephant.models.ItemModel;

import java.util.ArrayList;

import com.example.white_elephant.views.ItemSwipeView;

public class MainSwipeFragment extends Fragment {


    private FragmentManager fragMan;

    ArrayList<ItemModel> itemList;
    DatabaseReference reff;

    ArrayList<com.example.white_elephant.views.ItemSwipeView> itemViewList;

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

        reff = FirebaseDatabase.getInstance().getReference().child("Item");

        itemList = new ArrayList<>();

        // select * from items
        reff.addListenerForSingleValueEvent(valueEventListener);

        displayListings(itemList);

        //getArrayData();

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

    private com.example.white_elephant.views.ItemSwipeView popTopItem() {
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
            com.example.white_elephant.views.ItemSwipeView view = com.example.white_elephant.views.ItemSwipeView.newInstance(model);
            itemViewList.add(view);
        }
    }

    private void displayListings(ArrayList<ItemModel> itemList) {
        for(ItemModel item: itemList) {
            com.example.white_elephant.views.ItemSwipeView view = com.example.white_elephant.views.ItemSwipeView.newInstance(item);
            itemViewList.add(view);
        }
    }

    ValueEventListener valueEventListener = new ValueEventListener() {

        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Toast.makeText(MainSwipeView.this, "Here", Toast.LENGTH_LONG).show();


            itemList.clear();
            if (dataSnapshot.exists()){
                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()){
                    ItemModel item = itemSnapshot.getValue(ItemModel.class);
                    itemList.add(item);
                }

//                ItemList adapter = new ItemList(MainSwipeView.this, itemList);
//                itemsListView.setAdapter(adapter);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(MainSwipeView.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
        }
    };


}