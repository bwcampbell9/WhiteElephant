package com.example.white_elephant;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.white_elephant.models.Item;
import com.example.white_elephant.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN";
    private User user;
    private Item trading = null;


    private ImageView profileView;
    private ImageView swipeView;
    private ImageView postView;

    public User getUser(){return user;}

    public Item getTrading() {
        return trading;
    }

    public void setTrading(Item trading) {
        this.trading = trading;
    }

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
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(mAuth.getUid());
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    user = document.toObject(User.class);
                } else {
                    Log.d(TAG, "No such document");
                    Toast.makeText(this, "User not found", Toast.LENGTH_LONG).show();
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
                Toast.makeText(this, "User not found", Toast.LENGTH_LONG).show();
            }
        });

        profileView = findViewById(R.id.accountButton);
        swipeView = findViewById(R.id.tradeButton);
        postView = findViewById(R.id.postButton);
        swipeView.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
        navController = Navigation.findNavController(findViewById(R.id.nav_fragment));
        state = State.SWIPE;
    }

    public void onClickProfile(View view) {
        profileView.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
        swipeView.setColorFilter(ContextCompat.getColor(this, R.color.black));
        postView.setColorFilter(ContextCompat.getColor(this, R.color.black));
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
        } else if (state == State.TRADES){
            NavDirections action = MyTradesDirections.actionMyTradesToViewProfileFragment();
            navController.navigate(action);
        }
        state = State.PROFILE;
    }
    public void onClickTrading(View view) {
        profileView.setColorFilter(ContextCompat.getColor(this, R.color.black));
        swipeView.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
        postView.setColorFilter(ContextCompat.getColor(this, R.color.black));
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
        profileView.setColorFilter(ContextCompat.getColor(this, R.color.black));
        swipeView.setColorFilter(ContextCompat.getColor(this, R.color.black));
        postView.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
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

}
