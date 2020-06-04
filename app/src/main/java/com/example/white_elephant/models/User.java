package com.example.white_elephant.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.white_elephant.util.Database;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class User extends DBItem implements Parcelable {
    private static final String TAG = "USERMODEL";
    private List<String> iidList;
    private static final String ITEMS = "items";

    public User() {
    }

    public User(String uid) {
        this.setUid(uid);
        this.iidList = new ArrayList<>();
    }

    protected User(Parcel in) {
        this.setUid(in.readString());
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
        dest.writeString(this.getUid());
        dest.writeStringList(iidList);
    }

    public List<String> getIidList() {
        return iidList;
    }

    public void setIidList(List<String> iidList) {
        this.iidList = iidList;
    }

    public void addItem(String name, String description, double value, String imageurl) {
        Item newItem = new Item(name, description, value, new ArrayList<>());
        newItem.setUser(this.getUid());
        newItem.setImageUrl(imageurl);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference dbItems = db.collection(ITEMS);
        dbItems
                .add(newItem)
                .addOnSuccessListener(documentReference -> {
                    iidList.add(documentReference.getId());
                    Log.d(TAG, "new item written with ID: " + documentReference.getId());
                    DocumentReference userRef = db.collection("users").document(this.getUid());
                    userRef
                            .update("iidList", iidList)
                            .addOnSuccessListener(aVoid -> Log.d(TAG, "user iidlist successfully updated!"))
                            .addOnFailureListener(e -> Log.w(TAG, "Error updating document", e));
                })
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }

    public List<Item> grabItems() {
        List<Item> itemList = new ArrayList<>();

        Database.getInstance().getDocsByProp(ITEMS, "user", "==", this.getUid(), (Object item) ->
            itemList.add((Item) item), Item.class);

        Log.e(ITEMS, String.valueOf(itemList));
        return itemList;
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
        DocumentReference userRef = db.collection("users").document(this.getUid());
        userRef
                .update("iidList", iidList)
                .addOnSuccessListener(
                   (Void aVoid) ->
                        Log.d(TAG, "item delete successful!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error deleting item", e));

    }
}