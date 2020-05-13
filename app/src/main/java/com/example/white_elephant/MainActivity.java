package com.example.white_elephant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button profButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        profButton = (Button) findViewById(R.id.profilebutton);

        profButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchProfile();
            }
        });

    }
     //Launchprofile method
    private void launchProfile() {

        Intent intent = new Intent(this, ViewProfileActivity.class);
        startActivity(intent);
    }


}
