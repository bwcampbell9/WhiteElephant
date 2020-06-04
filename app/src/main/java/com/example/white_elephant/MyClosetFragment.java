package com.example.white_elephant;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.white_elephant.models.Item;
import com.example.white_elephant.util.Database;

import java.util.ArrayList;


public class MyClosetFragment extends Fragment implements ClosetAdapter.ItemClickListener {

    ArrayList<Item> items;
    private ClosetAdapter adapter;

    public MyClosetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_closet, container, false);
        items = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ClosetAdapter(getContext(), items);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        getArrayData();

        return view;
    }

    private void getArrayData() {
        Log.i("Info", "Getting items for user " + ((MainActivity) getActivity()).getUser().getUid());
        Database.getInstance().getDocsByProp("items", "user", "==",
                ((MainActivity) getActivity()).getUser().getUid(), (Object item) -> {
            Log.i("Found Item", ((Item) item).uid);
            items.add((Item) item);
            adapter.notifyDataSetChanged();
        }, Item.class);
    }

    @Override
    public void onItemClick(View view, int position) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        Fragment fragment = ViewMyItem.newInstance(adapter.getItem(position));
        ft.replace(R.id.Closet, fragment);
        ft.commit();
    }
}
