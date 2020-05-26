package com.example.white_elephant.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
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

    public void getImage(String imageName, ImageView view) {
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        // Create a reference with an initial file path and name
        StorageReference pathReference = storageRef.child("Item/" + imageName);

        Glide.with(MyApplication.getAppContext())
                .load(pathReference)
                .into(view);
    }
}
