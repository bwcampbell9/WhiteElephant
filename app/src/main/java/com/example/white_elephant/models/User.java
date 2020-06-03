package com.example.white_elephant.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.white_elephant.util.Database;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.ArrayList;

public class User extends DBItem implements Parcelable {
    private static final String TAG = "USERMODEL";
    private List<String> iidList;
    private final String ITEMSTEXT = "items";

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

    public void setIidList(List<String> iidList) {
        this.iidList = iidList;
    }

    public void addItem(String name, String description, double value, String imageurl) {
        Item newItem = new Item(name, description, value, new ArrayList<>());
        newItem.setUser(uid);
        newItem.setImageUrl(imageurl);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference dbItems = db.collection(ITEMSTEXT);
        dbItems
                .add(newItem)
                .addOnSuccessListener(documentReference -> {
                    iidList.add(documentReference.getId());
                    Log.d(TAG, "new item written with ID: " + documentReference.getId());
                    DocumentReference userRef = db.collection("users").document(uid);
                    userRef
                            .update("iidList", iidList)
                            .addOnSuccessListener(aVoid -> Log.d(TAG, "user iidlist successfully updated!"))
                            .addOnFailureListener(e -> Log.w(TAG, "Error updating document", e));
                })
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }

    public List<Item> grabItems() throws InterruptedException {
        List<Item> itemList = new ArrayList<>();

        Database.getInstance().getDocsByProp("items", "user", "==", uid, (Object item) -> {
            itemList.add((Item) item);
        }, Item.class).wait();

        Log.e("Items", String.valueOf(itemList));
        return itemList;
    }

    public void deleteItem(String anIID) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference itemRef = db.collection(ITEMSTEXT).document(anIID);
        itemRef
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
        DocumentReference userRef = db.collection("users").document(uid);
        userRef
                .update("iidList", iidList)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "item delete successful!");
                    }
                })
                .addOnFailureListener(e -> Log.w(TAG, "Error deleting item", e));

    }
}