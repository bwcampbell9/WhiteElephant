package com.example.white_elephant.Views;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.white_elephant.Models.ItemModel;
import com.example.white_elephant.R;

import java.util.ArrayList;

public class MainSwipeView extends AppCompatActivity {

    public RelativeLayout parentView;
    private FragmentManager fragMan;
    private Context context;

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

        getArrayData();

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
}