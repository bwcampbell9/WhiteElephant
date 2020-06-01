package com.example.white_elephant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;

public class ViewProfileFragment extends Fragment implements  View.OnClickListener {

    public ViewProfileFragment() {
        // required empty constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BlankFragment.
     */
    public static ViewProfileFragment newInstance() {
        return new ViewProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_view_profile, container, false);
        final Button viewCloset = view.findViewById(R.id.MyCloset);
        viewCloset.setOnClickListener(this);
        final Button viewMatches = view.findViewById(R.id.myMatches);
        viewMatches.setOnClickListener(this);
        final Button viewTrades = view.findViewById(R.id.myTrades);
        viewTrades.setOnClickListener(this);
        final Button viewProfile = view.findViewById(R.id.myProfile);
        viewProfile.setOnClickListener(this);
        return view;
        // View logic here and return view after
    }

    public void onClick(View v){
        NavController navController = ((MainActivity) getActivity()).getNavController();
        NavDirections action;
        switch (v.getId()) {
            case R.id.MyCloset:
                ((MainActivity) getActivity()).setState(0);
                action = ViewProfileFragmentDirections.actionViewProfileFragmentToMyClosetFragment();
                navController.navigate(action);
                break;
            case R.id.myMatches:
                ((MainActivity) getActivity()).setState(1);
                action = ViewProfileFragmentDirections.actionViewProfileFragmentToMatchesFragment();
                navController.navigate(action);
                break;
            case R.id.myTrades:
                ((MainActivity) getActivity()).setState(2);
                action = ViewProfileFragmentDirections.actionViewProfileFragmentToMyTrades();
                navController.navigate(action);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
        }
    }


