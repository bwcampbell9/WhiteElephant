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

    private enum State {
        SWIPE,
        PROFILE,
        POST
    }

    private State state;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_layout);

        navController = Navigation.findNavController(findViewById(R.id.nav_fragment));

        state = State.SWIPE;
    }

    public void onClickProfile(View view) {
        if(state == State.SWIPE) {
            NavDirections action = MainSwipeFragmentDirections.actionMainSwipeViewToViewProfileFragment();
            navController.navigate(action);
        } else if (state == State.POST)  {
            NavDirections action = PostItemFragmentDirections.actionPostItemFragmentToViewProfileFragment();
            navController.navigate(action);
        }
        state = State.PROFILE;
    }
    public void onClickTrading(View view) {
        if(state == State.PROFILE) {
            NavDirections action = ViewProfileFragmentDirections.actionViewProfileFragmentToMainSwipeView();
            navController.navigate(action);
        } else if (state == State.POST)  {
            NavDirections action = PostItemFragmentDirections.actionPostItemFragmentToMainSwipeView();
            navController.navigate(action);
        }
        state = State.SWIPE;
    }
    public void onClickPost(View view) {
        if(state == State.PROFILE) {
            NavDirections action = ViewProfileFragmentDirections.actionViewProfileFragmentToPostItemFragment();
            navController.navigate(action);
        } else if (state == State.SWIPE)  {
            NavDirections action = MainSwipeFragmentDirections.actionMainSwipeViewToPostItemFragment();
            navController.navigate(action);
        }
        state = State.POST;
    }


}
