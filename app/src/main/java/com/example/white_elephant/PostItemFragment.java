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

import androidx.fragment.app.Fragment;

import com.example.white_elephant.util.Storage;
import com.google.firebase.storage.UploadTask;


public class PostItemFragment extends Fragment {

    private boolean uploaded = false;
    private boolean chose = false;

    EditText nameEditText;
    EditText descEditText;
    EditText valEditText;

    Button chooseBtn;
    Button uploadBtn;
    ImageView imageView;

    Button addItemBtn;

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

        nameEditText = (EditText) view.findViewById(R.id.userNameEditText);
        descEditText = (EditText) view.findViewById(R.id.userPhoneEditText);
        valEditText = (EditText) view.findViewById(R.id.userAddressEditText);
        addItemBtn = (Button) view.findViewById(R.id.editProfileBtn);

        chooseBtn.setOnClickListener((View v) ->{
            uploaded = false;
            chose = true;
            myFileChooser();
        });

        uploadBtn.setOnClickListener((View v) -> {
            if (uploadTask != null && uploadTask.isInProgress()){
                makeToast("Upload in progress");
            } else if (uploaded){
                makeToast("Item already uploaded");
            } else if (!chose){
                makeToast("Choose an image first");
            } else {
                myFileUploader();
            }
        });

        addItemBtn.setOnClickListener((View v) -> {
            String name = nameEditText.getText().toString().trim();
            String desc = descEditText.getText().toString().trim();
            Double val = Double.valueOf(0);
            try{
                val = Double.parseDouble(valEditText.getText().toString().trim());
                if (name.length() <= 0 || imageUrl.length() <= 0 || val < 0){
                    makeToast("Item Not Added, Try Again");
                } else{
                    MainActivity.getUser().addItem(name,desc,val,imageUrl);
                    makeToast("Item Added Successfully");
                }
            } catch (Exception e){
                makeToast("Item Not Added, Try Again");
            }
        });

        return view;
    }

    private void myFileChooser () {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }

    private void myFileUploader(){
        tempImageUrl = System.currentTimeMillis() + "." + getExtension(imageUri);

        Storage.getInstance().uploadImage(tempImageUrl, imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    makeToast("Image Uploaded Successfully");
                    imageUrl = tempImageUrl;
                    uploaded = true;
                })
                .addOnFailureListener(e -> makeToast(e.getMessage())
                );
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
