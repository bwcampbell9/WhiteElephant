package com.example.white_elephant;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.white_elephant.Models.ItemModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostItemActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText descEditText;
    EditText valEditText;
    EditText postErrorEditText;

    Button addItemBtn;
    DatabaseReference reff;
    ItemModel item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        descEditText = (EditText) findViewById(R.id.descEditText);
        valEditText = (EditText) findViewById(R.id.valEditText);
        postErrorEditText = (EditText) findViewById(R.id.postErrorEditText);
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

<<<<<<< HEAD
                Toast.makeText(PostItemActivity.this, "Item Added Successfully", Toast.LENGTH_LONG).show();
=======
                try{
                    val = Double.parseDouble(valEditText.getText().toString().trim());
                    item.setValue(val);
                    if (name.length() <= 0 || val < 0){
                        postErrorEditText.setText("Item Not Added: Invalid Input");
                    } else{
                        reff.push().setValue(item);
                        Toast.makeText(PostItemActivity.this, "Item Added Successfully", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e){
                    postErrorEditText.setText("Item Not Added: Invalid Input");
                }
>>>>>>> 6f669bd... Added error checking to post item activity

            }
        });

    }
}
