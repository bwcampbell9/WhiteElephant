package com.example.white_elephant;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.white_elephant.Models.ItemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QueryDbActivity extends AppCompatActivity {

    List<ItemModel> itemList;
    ListView itemsListView;
    DatabaseReference reff;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_db);

        itemsListView = (ListView) findViewById(R.id.itemsListView);
        reff = FirebaseDatabase.getInstance().getReference().child("Item");

        itemList = new ArrayList<>();

        // select * from items
        reff.addListenerForSingleValueEvent(valueEventListener);


        // select * from items where name = "Guitar"
        Query query = reff
                .orderByChild("name")
                .equalTo("Guitar");
//        query.addListenerForSingleValueEvent(valueEventListener);

        // select * from items limit 2
        Query query2 = reff.limitToFirst(2);
        //query2.addListenerForSingleValueEvent(valueEventListener);

        // select * from items where value <= 30
        Query query3 = reff
                .orderByChild("value")
                .endAt(30);
        // query3.addListenerForSingleValueEvent(valueEventListener);

    }

    ValueEventListener valueEventListener = new ValueEventListener() {

        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            itemList.clear();
            if (dataSnapshot.exists()){
                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()){
                    ItemModel item = itemSnapshot.getValue(ItemModel.class);
                    itemList.add(item);
                }

                ItemList adapter = new ItemList(QueryDbActivity.this, itemList);
                itemsListView.setAdapter(adapter);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(QueryDbActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    /*
    @Override
    protected void onStart() {
        super.onStart();

        reff.addValueEventListener(valueEventListener);
    } */
}
