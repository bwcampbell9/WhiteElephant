package com.example.white_elephant;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_swipe_layout);

        Toast.makeText(MainActivity.this, "Firebase Connection Succes", Toast.LENGTH_LONG).show();
    }
}
