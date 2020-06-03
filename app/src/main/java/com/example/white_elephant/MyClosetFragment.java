package com.example.white_elephant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.white_elephant.models.Item;
import com.example.white_elephant.models.User;
import java.util.ArrayList;
import java.util.Arrays;


public class MyClosetFragment extends Fragment implements ClosetAdapter.ItemClickListener {

    ClosetAdapter adapter;
    ArrayList<Item> items = new ArrayList<>();

    public MyClosetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_closet, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ClosetAdapter(getActivity(), Arrays.asList(user.grabItems()));
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        Fragment fragment = ViewMyItem.newInstance(adapter.getItem(position));
        ft.replace(R.id.Closet, fragment);
        ft.commit();
    }

}
