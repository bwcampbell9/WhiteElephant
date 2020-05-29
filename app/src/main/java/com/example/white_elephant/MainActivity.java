package com.example.white_elephant;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.white_elephant.models.TradeModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public String userEmail;

    private enum State {
        SWIPE,
        PROFILE,
        POST,
        CLOSET,
        MATCHES,
        TRADES
    }

    private State state;
    NavController navController;

    public void setState(int i){
        State[] states = new State[]{State.CLOSET, State.MATCHES, State.TRADES};
        state = states[i];
    }

    public NavController getNavController(){
        return navController;
    }

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
        }else if (state == State.CLOSET){
            NavDirections action = MyClosetFragmentDirections.actionMyClosetFragmentToViewProfileFragment3();
            navController.navigate(action);
        }else if (state == State.MATCHES){
            NavDirections action = MatchesFragmentDirections.actionMatchesFragmentToViewProfileFragment();
            navController.navigate(action);
        }
        else if (state == State.TRADES){
            NavDirections action = MyTradesDirections.actionMyTradesToViewProfileFragment();
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
        }else if (state == State.CLOSET){
            NavDirections action = MyClosetFragmentDirections.actionMyClosetFragmentToMainSwipeView2();
            navController.navigate(action);
        }else if (state == State.MATCHES) {
            NavDirections action = MatchesFragmentDirections.actionMatchesFragmentToMainSwipeView();
            navController.navigate(action);
        }else if (state == State.TRADES){
            NavDirections action = MyTradesDirections.actionMyTradesToMainSwipeView();
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
        }else if (state == State.CLOSET){
            NavDirections action = MyClosetFragmentDirections.actionMyClosetFragmentToPostItemFragment();
            navController.navigate(action);
        }else if (state == State.MATCHES) {
            NavDirections action = MatchesFragmentDirections.actionMatchesFragmentToPostItemFragment();
            navController.navigate(action);
        }else if (state == State.TRADES){
            NavDirections action = MyTradesDirections.actionMyTradesToPostItemFragment();
            navController.navigate(action);
        }
        state = State.POST;
    }

    public String getUserEmail() {
        return userEmail;
    }


}
