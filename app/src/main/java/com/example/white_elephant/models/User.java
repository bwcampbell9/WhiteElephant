package com.example.white_elephant.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.ArrayList;

public class User {
    private static final String TAG = "USERMODEL";
    public String uid;
    public List<String> iidList;

    public String name;
    public String email;
    public String address;
    public String phoneNumber;
    public String birthday;

    public User() {
        this.email = "";
    }

    public User(String uid) {
        this.uid = uid;
        this.iidList = new ArrayList<String>();
    }
    public String getUid() {return uid;}

    public List<String> getIidList() {return iidList;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void addItem(String name, String description, double value, List<String> tags) {
        Item newItem = new Item(name,description,value,tags);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("items")
                .add(newItem)
                .addOnSuccessListener(documentReference -> {
                    iidList.add(documentReference.getId());
                    Log.d(TAG, "new item written with ID: " + documentReference.getId());
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
        DocumentReference userRef = db.collection("items").document(uid);
        userRef
                .update("iidList", iidList)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "user iidlist successfully updated!");
                    }
                })
                .addOnFailureListener(e -> Log.w(TAG, "Error updating document", e));
    }

    public Item[] grabItems() {
        List<Item> itemList = new ArrayList<Item>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference itemsRef = db.collection("items");
        for (String anIID : iidList) {
            DocumentReference docRef = itemsRef.document(anIID);
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        itemList.add((Item) document.getData());
                    } else {
                        Log.w(TAG, "document not found. should not happen!");
                    }
                } else {
                    Log.w(TAG, "Error deleting document", task.getException());
                }
            });
        }
        return itemList.toArray(new Item[0]);
    }

    public void deleteItem(String anIID) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("items").document(anIID)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    iidList.remove(anIID);
                    Log.d(TAG, "DocumentSnapshot successfully deleted!");
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });

    }
}
