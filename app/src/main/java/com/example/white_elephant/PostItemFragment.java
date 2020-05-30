package com.example.white_elephant;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.white_elephant.models.Item;
import com.example.white_elephant.util.Database;
import com.example.white_elephant.util.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostItemFragment extends Fragment {

    private boolean uploaded = false;
    private boolean chose = false;

    EditText nameEditText;
    EditText descEditText;
    EditText valEditText;
    EditText postErrorEditText;

    Button backBtn;
    Button chooseBtn;
    Button uploadBtn;
    ImageView imageView;

    Button addItemBtn;
    //DatabaseReference dbReff;
    Item item;

    StorageReference mStorageRef;
    private Uri imageUri;

    String tempImageUrl;
    String imageUrl = "";

    private UploadTask uploadTask;

    public PostItemFragment() {
        // required empty constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BlankFragment.
     */
    public static PostItemFragment newInstance() {
        return new PostItemFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_post_item, container, false);

        chooseBtn = (Button) view.findViewById(R.id.chooseBtn);
        uploadBtn = (Button) view.findViewById(R.id.uploadBtn);
        imageView = view.findViewById(R.id.imageView);

        nameEditText = (EditText) view.findViewById(R.id.nameEditText);
        descEditText = (EditText) view.findViewById(R.id.descEditText);
        valEditText = (EditText) view.findViewById(R.id.valEditText);

        item = new Item();
        addItemBtn = (Button) view.findViewById(R.id.addItemBtn);

        //dbReff = FirebaseDatabase.getInstance().getReference().child("Item");
        mStorageRef = Storage.getInstance().getRef("Item");

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
                } else if (uploaded){
                    makeToast("Item already uploaded");
                } else if (!chose){
                    makeToast("Choose an image first");
                } else
                    myFileUploader();
            }

        });

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String desc = descEditText.getText().toString().trim();
                Double val = Double.valueOf(0);
                Bundle bundle = getActivity().getIntent().getExtras();
                String user = bundle.getString("email");

                Item item = new Item();
                item.setName(name);
                item.setDescription(desc);
                item.setImageUrl(imageUrl);
                if (user != null){
                    item.setUser(bundle.getString("email"));
                }

                try{
                    val = Double.parseDouble(valEditText.getText().toString().trim());
                    item.setValue(val);
                    if (name.length() <= 0 || imageUrl.length() <= 0 || val < 0){
                        makeToast("Item Not Added, Try Again");
                    } else{
                        Database.getInstance().addDocument("items", item);
                        makeToast("Item Added Successfully");
                    }
                } catch (Exception e){
                    makeToast("Item Not Added, Try Again");
                }

            }
        });

        return view;
    }

    private void myFileChooser () {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }

    private void myFileUploader(){
        tempImageUrl = System.currentTimeMillis() + "." + getExtension(imageUri);
        StorageReference storageReff = mStorageRef.child(tempImageUrl);

        uploadTask = (UploadTask) storageReff.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        makeToast("Image Uploaded Successfully");
                        imageUrl = tempImageUrl;
                        uploaded = true;
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        makeToast(exception.getMessage());
                    }
                });


    }

    private String getExtension(Uri mUri){
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(mUri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK &&
                data != null && data.getData() != null){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void makeToast(String str){
        Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
    }
}
