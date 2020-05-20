package com.example.white_elephant.Views;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.white_elephant.Models.ItemModel;
import com.example.white_elephant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainSwipeView extends AppCompatActivity {

    public RelativeLayout parentView;
    private FragmentManager fragMan;
    private Context context;

    ArrayList<ItemModel> itemList;
    DatabaseReference reff;

    ArrayList<ItemSwipeView> itemViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_swipe_layout);

        fragMan = getFragmentManager();

        context = MainSwipeView.this;

        parentView = (RelativeLayout) findViewById(R.id.swipe_cards_layout);

        itemViewList = new ArrayList<>();

        reff = FirebaseDatabase.getInstance().getReference().child("Item");

        itemList = new ArrayList<>();

        // select * from items
        reff.addListenerForSingleValueEvent(valueEventListener);

        displayListings(itemList);

        //getArrayData();

        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        if (itemViewList.size() > 1) {
            fragTransaction.add(R.id.swipe_cards_layout, itemViewList.get(1));
        }
        if (itemViewList.size() > 0) {
            fragTransaction.add(R.id.swipe_cards_layout, itemViewList.get(0));
        }
        fragTransaction.commit();


    }

    private ItemSwipeView popTopItem() {
        if(itemViewList.size() == 0) {
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
            ItemModel model = new ItemModel("Neat Shoes", "These are my super neat shoes", i * 10);
            ItemSwipeView view = ItemSwipeView.newInstance(model);
            itemViewList.add(view);
        }
    }

    private void displayListings(ArrayList<ItemModel> itemList) {
        for(ItemModel item: itemList) {
            ItemSwipeView view = ItemSwipeView.newInstance(item);
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