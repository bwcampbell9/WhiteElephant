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

import java.util.ArrayList;
import java.util.LinkedList;


public class MyClosetFragment extends Fragment implements ClosetAdapter.ItemClickListener {

    ClosetAdapter adapter;

    public MyClosetFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_closet, container, false);
        ArrayList<Item> items = new ArrayList<>();
        Item testImageItem = new Item("Vans", "New white vans.", 50, new LinkedList<>());
        testImageItem.setImageUrl("1590202130003.webp");
        items.add(testImageItem);
        items.add(new Item("Shirt", "My old shirt.", 22.0, new LinkedList<>()));
        items.add(new Item("Pants", "These new pants.", .08, new LinkedList<>()));
        items.add(new Item("Shoes", "Two shoes.", 10.0, new LinkedList<>()));
        items.add(new Item("Hat", "A very cool hat.", 393.0, new LinkedList<>()));
        items.add(new Item("Underwear", "For under your wear.", 7.5, new LinkedList<>()));
        items.add(new Item("Socks", "Two socks. They don't match.", 54.99, new LinkedList<>()));
        items.add(new Item("Belt", "Use responsibly.", 99.99, new LinkedList<>()));
        items.add(new Item("Gloves", "A box of disposable gloves.", 22.23, new LinkedList<>()));

        RecyclerView recyclerView = view.findViewById(R.id.itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ClosetAdapter(getActivity(), items);
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
