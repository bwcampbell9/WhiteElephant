package com.example.white_elephant.util;

import android.util.Log;

import com.example.white_elephant.models.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.List;


/**
 *  Singleton design pattern used to deal with database operations
 */
public class Database {

    private static final String ITEMCOLLECTION = "items";
    private static final String ERR = "Error";

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
        getDocsByProp(ITEMCOLLECTION, "tags", "array-contains-any", tags, (Object o) -> forEachDoc.itemCallback(((Item) o)), Item.class);
    }

    public void getItemsByPrice(double price, ObjectCallback forEachDoc, Class objType){
        double lowerBound = .70 * price;
        double upperBound = 1.30 * price;
        Query query = this.db.collection(ITEMCOLLECTION)
                .whereLessThanOrEqualTo("value", upperBound)
                .whereGreaterThanOrEqualTo("value", lowerBound);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Object item = document.toObject(objType);
                    if (!(((Item) item).getUser().equals(FirebaseAuth.getInstance().getUid()))){
                        forEachDoc.objectCallback(item);
                    }
                }
            }
        });
    }

    // add success and failure listeners and use add document
    public void pushItem(Item item) {
        db.collection(ITEMCOLLECTION).add(item)
                .addOnSuccessListener(docRef ->
                    Log.e("Info", "DocumentSnapshot added with ID: " + docRef.getId())
                )
                .addOnFailureListener(e ->
                    Log.e("Info", "Error adding document", e)
                );
    }

    public void getDocument(String path, final ObjectCallback docCB, Class objType) {
        DocumentReference docRef = this.db.document(path);
        docRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Object item = document.toObject(objType);
                            docCB.objectCallback(item);
                        } else {
                            Log.e(ERR, "No such document");
                        }
                    } else {
                        Log.e(ERR, "get failed with ", task.getException());
                    }
                });
    }

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
            default:
                Log.e(ERR, "query not executed");
        }
        if(query == null) {
            Log.e(ERR, "invalid comparison type");
            return;
        }
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Object item = document.toObject(objType);
                    forEachDoc.objectCallback(item);
                }
            }
        });
    }

    public void addDocument(String path, Object doc) {
        CollectionReference colRef = this.db.collection(path);
        if (doc instanceof Item){
            db.collection(ITEMCOLLECTION).document(((Item) doc).getImageUrl()).set(doc);
        } else {
            colRef.add(doc);
        }
    }
    public void deleteDocument(String path) {
        DocumentReference docRef = this.db.document(path);
        docRef.delete();
    }

    public void updateDocument(String path, Object data) {
        DocumentReference docRef = this.db.document(path);
        docRef.set(data, SetOptions.merge());
    }

}
