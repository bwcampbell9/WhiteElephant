package com.example.white_elephant.util;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.white_elephant.models.ItemModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 *  Singleton design pattern used to deal with database operations
 */
public class Database {

    public interface ForEachItem {
        void forEachItem(ItemModel item);
    }

    private static Database singleton;
    private FirebaseFirestore db;

    public Database() {
        db = FirebaseFirestore.getInstance();
    }

    public static Database getInstance() {
        if(singleton == null) {
            singleton = new Database();
        }
        return singleton;
    }

    public void getItemsByTags(List<String> tags, final ForEachItem forEachDoc) {
        Query query = db.collection("items").whereArrayContainsAny("tags", tags);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ItemModel item = document.toObject(ItemModel.class);
                        forEachDoc.forEachItem(item);
                    }
                } else {
                }
            }
        });
    }

    //Todo: add success and failure listeners
    public void pushItem(ItemModel item) {
        Log.e("Info", "pushing item");
        db.collection("items").add(item)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.e("Info", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Info", "Error adding document", e);
                    }
                });;
    }

}
