package com.example.white_elephant;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        return view;
        // View logic here and return view after
    }

    public void onClick(View v){
        ((MainActivity)getActivity()).setState(0);
        NavController navController = ((MainActivity)getActivity()).getNavController();
        NavDirections action = ViewProfileFragmentDirections.actionViewProfileFragmentToMyClosetFragment();
        navController.navigate(action);
    }


}
