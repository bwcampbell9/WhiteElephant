package com.example.white_elephant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavAction;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.white_elephant.Views.MainSwipeViewDirections;

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

        Intent intent = new Intent(this, ViewProfileActivity.class);
        startActivity(intent);
    }

    public void onClickProfile() {

    }
    public void onClickTrading() {

    }
    public void onClickPost() {
        //NavDirections action = MainSwipeViewDirections.actionMainSwipeViewToPostItemActivity();
        //navController.navigate(action);
    }


}
