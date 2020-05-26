package com.example.white_elephant.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.white_elephant.MyApplication;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 *  Singleton design pattern used to deal with database operations
 */
public class Storage {

    final long ONE_MEGABYTE = 1024 * 1024;

    public interface ObjectCallback {
        void objectCallback(Object object);
    }

    public interface DrawableCallback {
        void drawableCallback(Drawable str);
    }

    private static Storage singleton;
    private FirebaseStorage storage;

    public Storage() {
        storage = FirebaseStorage.getInstance();
    }

    public static Storage getInstance() {
        if(singleton == null) {
            singleton = new Storage();
        }
        return singleton;
    }

    public void getImageURL(String imageName, DrawableCallback cb) {
       // Create a storage reference from our app
       StorageReference storageRef = storage.getReference();

       // Create a reference with an initial file path and name
       StorageReference pathReference = storageRef.child("Item/" + imageName);

       pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
           @Override
           public void onSuccess(Uri uri) {
               try {
                   InputStream is = MyApplication.getAppContext().getContentResolver().openInputStream(uri);
                   Drawable d = Drawable.createFromStream(is, "src name");;
                   cb.drawableCallback(d);
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception exception) {
               exception.printStackTrace();
           }
       });
   }
}
