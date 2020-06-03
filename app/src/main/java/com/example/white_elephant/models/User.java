package com.example.white_elephant.models;

import android.os.Parcel;
import android.os.Parcelable;
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

public class User implements Parcelable {
    private static final String TAG = "USERMODEL";
    private String uid;
    private List<String> iidList;
    private static final String ITEMS = "items";

    public User() {
    }

    public User(String uid) {
        this.uid = uid;
        this.iidList = new ArrayList<>();
    }

    protected User(Parcel in) {
        uid = in.readString();
        iidList = in.createStringArrayList();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeStringList(iidList);
    }

    public String getUid() {
        return uid;
    }

    public List<String> getIidList() {
        return iidList;
    }

    public void addItem(String name, String description, double value, String imageurl) {
        Item newItem = new Item(name, description, value, new ArrayList<>());
        newItem.setUser(uid);
        newItem.setImageUrl(imageurl);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference dbItems = db.collection(ITEMS);
        dbItems
                .add(newItem)
                .addOnSuccessListener(documentReference -> {
                    iidList.add(documentReference.getId());
                    Log.d(TAG, "new item written with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
        DocumentReference userRef = db.collection("users").document(uid);
        userRef
                .update("iidList", iidList)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "user iidlist successfully updated!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error updating document", e));
    }

    public Item[] grabItems() {
        List<Item> itemList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference itemsRef = db.collection(ITEMS);
        for (String anIID : iidList) {
            DocumentReference docRef = itemsRef.document(anIID);
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        itemList.add(document.toObject(Item.class));
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
        DocumentReference itemRef = db.collection(ITEMS).document(anIID);
        itemRef
                .delete()
                .addOnSuccessListener(aVoid -> {
                    iidList.remove(anIID);
                    Log.d(TAG, "DocumentSnapshot successfully deleted!");
                })
                .addOnFailureListener(
                    (@NonNull Exception e) ->
                        Log.w(TAG, "Error deleting document", e));
        DocumentReference userRef = db.collection("users").document(uid);
        userRef
                .update("iidList", iidList)
                .addOnSuccessListener(
                   (Void aVoid) ->
                        Log.d(TAG, "item delete successful!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error deleting item", e));

    }
}