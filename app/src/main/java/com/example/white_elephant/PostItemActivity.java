package com.example.white_elephant;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.white_elephant.Models.ItemModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class PostItemActivity extends AppCompatActivity {

    public boolean uploaded = false;
    public boolean chose = false;

    EditText nameEditText;
    EditText descEditText;
    EditText valEditText;

    Button chooseBtn, uploadBtn;
    ImageView imageView;
    StorageReference mStorageRef;
    public Uri imageUri;

    Button addItemBtn;
    DatabaseReference reff;
    ItemModel item;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item);

        mStorageRef = FirebaseStorage.getInstance().getReference("Images");

        chooseBtn = (Button) findViewById(R.id.chooseBtn);
        uploadBtn = (Button) findViewById(R.id.uploadBtn);
        imageView = (ImageView) findViewById(R.id.imageView);

        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploaded = false;
                chose = true;
                myFileChooser();
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadTask != null && uploadTask.isInProgress()){
                    makeToast("Upload in progress");
                } else if (uploaded == true){
                    makeToast("Item already uploaded");
                } else if (chose == false){
                    makeToast("Choose an image first");
                } else{
                    System.out.println("here");
                    myFileUploader();
                }

            }
        });

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        descEditText = (EditText) findViewById(R.id.descEditText);
        valEditText = (EditText) findViewById(R.id.valEditText);
        item = new ItemModel();
        addItemBtn = (Button) findViewById(R.id.addItemBtn);

        reff = FirebaseDatabase.getInstance().getReference().child("Item");


        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String desc = descEditText.getText().toString().trim();
                Double val = Double.valueOf(0);

                item.setName(name);
                item.setDescription(desc);

                try{
                    val = Double.parseDouble(valEditText.getText().toString().trim());
                    item.setValue(val);
                    if (name.length() <= 0 || val < 0){
                        makeToast("Item Not Added, Try Again");
                    } else{
                        reff.push().setValue(item);
                        makeToast("Item Added Successfully");
                    }
                } catch (Exception e){
                    makeToast("Item Not Added, Try Again");
                }

            }
        });

    }

    private void myFileChooser () {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }

    private void myFileUploader(){
        StorageReference reff = mStorageRef.child(System.currentTimeMillis() + "." + getExtension(imageUri));
        Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        StorageReference riversRef = mStorageRef.child("images/rivers.jpg");

        uploadTask = reff.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl()
                        Toast.makeText(PostItemActivity.this,
                                "Image Uploaded Successfully", Toast.LENGTH_LONG).show();
                        uploaded = true;
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });


    }

    private String getExtension(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(mUri));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK &&
                data != null && data.getData() != null){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void makeToast(String str){
        Toast.makeText(PostItemActivity.this, str, Toast.LENGTH_LONG).show();
    }

}
