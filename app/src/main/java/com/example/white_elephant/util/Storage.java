package com.example.white_elephant.util;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Storage {

    private static Storage singleton;
    private FirebaseStorage fs;

    public Storage() {
        fs = FirebaseStorage.getInstance();
    }

    public static Storage getInstance() {
        if(singleton == null) {
            singleton = new Storage();
        }
        return singleton;
    }

    public StorageReference getRef(String child){
        StorageReference reff = fs.getReference(child);
        return reff;
    }
}
