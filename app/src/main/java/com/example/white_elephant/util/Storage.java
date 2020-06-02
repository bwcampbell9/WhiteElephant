package com.example.white_elephant.util;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
    private FirebaseStorage mStorage;

    public Storage() {
        mStorage = FirebaseStorage.getInstance();
    }

    public static Storage getInstance() {
        if(singleton == null) {
            singleton = new Storage();
        }
        return singleton;
    }

    public UploadTask uploadImage(String imageName, Uri uri) {
        // Create a storage reference from our app
        StorageReference pathReference = mStorage.getReference().child("Item/" + imageName);

        InputStream stream = null;
        try {
            stream = MyApplication.getAppContext().getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (stream == null){
            return null;
        }
        return pathReference.putStream(stream);
    }

    public void getImage(String imageName, ImageView view, int size) {
        // Create a storage reference from our app
        StorageReference pathReference = mStorage.getReference().child("Item/" + imageName);

        if (size == -1){
            Glide.with(MyApplication.getAppContext())
                    .load(pathReference)
                    .into(view);
        } else{
            Glide.with(MyApplication.getAppContext())
                    .load(pathReference)
                    .apply(new RequestOptions().override(size, size))
                    .into(view);
        }



    }
}
