package com.example.white_elephant.util;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.white_elephant.models.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.List;

import static com.example.white_elephant.MainActivity.user;

/**
 *  Singleton design pattern used to deal with database operations
 */
public class Database {

    public interface ObjectCallback {
        void objectCallback(Object object);
    }

    public interface ItemCallback {
        void itemCallback(Item item);
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

    public void getItemsByTags(List<String> tags, final ItemCallback forEachDoc) {
        getDocsByProp("items", "tags", "array-contains-any", tags, (Object o) -> {forEachDoc.itemCallback(((Item) o));}, Item.class);
    }

    public void getItemsByPrice(double price, ObjectCallback forEachDoc, Class objType){
        double lowerBound = .70 * price;
        double upperBound = 1.30 * price;
        Query query = this.db.collection("items")
                .whereLessThanOrEqualTo("value", upperBound)
                .whereGreaterThanOrEqualTo("value", lowerBound);
        if(query == null) {
            Log.e("Error", "invalid comparison type");
            return;
        }
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Object item = document.toObject(objType);
                        if (!(((Item) item).getUser().equals(user.getEmail()))){
                            forEachDoc.objectCallback(item);
                        }
                    }
                } else {
                }
            }
        });
    }

    //Todo: add success and failure listeners and use add document
    public void pushItem(Item item) {
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

    public void getDocument(String path, final ObjectCallback docCB, Class objType) {
        DocumentReference docRef = this.db.document(path);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Object item = document.toObject(objType);
                        //item.id = document.getId();
                        docCB.objectCallback(item);
                    } else {
                        Log.e("Error", "No such document");
                    }
                } else {
                    Log.e("Error", "get failed with ", task.getException());
                }
            }
        });
    };

    public void getDocsByProp(String path, String prop, String compare, Object value, ObjectCallback forEachDoc, Class objType) {
        Query query = null;
        switch (compare) {
            case "==":
                query = this.db.collection(path).whereEqualTo(prop, value);
                break;
            case "<":
                query = this.db.collection(path).whereLessThan(prop, value);
                break;
            case ">":
                query = this.db.collection(path).whereGreaterThan(prop, value);
                break;
            case "<=":
                query = this.db.collection(path).whereLessThanOrEqualTo(prop, value);
                break;
            case ">=":
                query = this.db.collection(path).whereGreaterThanOrEqualTo(prop, value);
                break;
            case "array-contains":
                query = this.db.collection(path).whereArrayContains(prop, value);
                break;
            case "array-contains-any":
                query = this.db.collection(path).whereArrayContainsAny(prop, (List) value);
                break;
            case "in":
                query = this.db.collection(path).whereIn(prop, (List) value);
                break;
        }
        if(query == null) {
            Log.e("Error", "invalid comparison type");
            return;
        }
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Object item = document.toObject(objType);
                        //item.id = document.getId();
                        forEachDoc.objectCallback(item);
                    }
                } else {
                }
            }
        });
    };

    public void addDocument(String path, Object doc) {
        String id;
        CollectionReference colRef = this.db.collection(path);
        if (doc instanceof Item){
            db.collection("items").document(((Item) doc).getImageUrl()).set(doc);
        } else {
            colRef.add(doc);
        }
    };

    public void deleteDocument(String path) {
        DocumentReference docRef = this.db.document(path);
        docRef.delete();
    }

    public void updateDocument(String path, Object data) {
        DocumentReference docRef = this.db.document(path);
        docRef.set(data, SetOptions.merge());
    }

}
