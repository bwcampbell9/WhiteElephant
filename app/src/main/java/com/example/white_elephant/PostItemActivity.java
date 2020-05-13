package com.example.white_elephant;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostItemActivity extends AppCompatActivity {

    EditText nameEditText, descEditText, valEditText;
    Button addItemBtn;
    DatabaseReference reff;
    Item item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        descEditText = (EditText) findViewById(R.id.descEditText);
        valEditText = (EditText) findViewById(R.id.valEditText);
        item = new Item();
        addItemBtn = (Button) findViewById(R.id.addItemBtn);

        reff = FirebaseDatabase.getInstance().getReference().child("Item");

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String desc = descEditText.getText().toString().trim();
                int val = Integer.parseInt(valEditText.getText().toString().trim());

                item.setName(name);
                item.setDesc(desc);
                item.setApproxVal(val);

                reff.push().setValue(item);

                Toast.makeText(PostItemActivity.this, "Item Added Successfully", Toast.LENGTH_LONG).show();

            }
        });

    }
}
