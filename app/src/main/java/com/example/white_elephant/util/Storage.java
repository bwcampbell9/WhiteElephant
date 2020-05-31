package com.example.white_elephant.util;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.white_elephant.MyApplication;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 *  Singleton design pattern used to deal with database operations
 */
public class Storage {
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

    public UploadTask uploadImage(String imageName, Uri uri) {
        // Create a storage reference from our app
        StorageReference pathReference = storage.getReference().child("Item/" + imageName);

        InputStream stream = null;
        try {
            stream = MyApplication.getAppContext().getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return pathReference.putStream(stream);
    }

    public void getImage(String imageName, ImageView view) {
        // Create a storage reference from our app
        StorageReference pathReference = storage.getReference().child("Item/" + imageName);

        Glide.with(MyApplication.getAppContext())
                .load(pathReference)
                .into(view);
    }
}
