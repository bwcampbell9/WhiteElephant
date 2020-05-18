package com.example.white_elephant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity {

    private Button profButton;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_layout);

        navController = Navigation.findNavController(findViewById(R.id.nav_fragment));
    }

    private void launchProfile() {

        Intent intent = new Intent(this, ViewProfileFragment.class);
        startActivity(intent);
    }

    public void onClickProfile(View view) {

    }
    public void onClickTrading(View view) {

    }
    public void onClickPost(View view) {
        //NavDirections action =
        //navController.navigate(action);
    }


}
