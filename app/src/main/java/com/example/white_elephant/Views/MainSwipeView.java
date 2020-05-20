package com.example.white_elephant.Views;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
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
import java.util.List;

public class MainSwipeView extends AppCompatActivity {

    public RelativeLayout parentView;
    private FragmentManager fragMan;
    private Context context;

    List<ItemModel> itemList;
    ListView itemsListView;
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

        itemList = new ArrayList<>();

        reff = FirebaseDatabase.getInstance().getReference().child("Item");

        // get items from database
        // select * from items
        reff.addListenerForSingleValueEvent(valueEventListener);

        getArrayData();

//        displayListings(itemList);

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

    private void displayListings(List<ItemModel> itemsList) {
        for(ItemModel item: itemsList) {
            ItemSwipeView view = ItemSwipeView.newInstance(item);
            itemViewList.add(view);
        }
    }

    private void getArrayData() {
        for(int i = 0; i < 10; i ++) {
            ItemModel model = new ItemModel("Neat Shoes", "My Neat Shoes", i *= 10);
            ItemSwipeView view = ItemSwipeView.newInstance(model);
            itemViewList.add(view);
        }
    }

    ValueEventListener valueEventListener = new ValueEventListener() {

        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

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