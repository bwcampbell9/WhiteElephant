package com.example.white_elephant;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.white_elephant.models.ItemModel;
import com.example.white_elephant.models.TradeModel;
import com.example.white_elephant.views.MatchesAdapter;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MatchesFragment extends Fragment implements MatchesAdapter.ItemClickListener {

    MatchesAdapter adapter;

    public MatchesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matches, container, false);
        ArrayList<ItemModel> items = new ArrayList<>();
        ItemModel testImageItem = new ItemModel("Vans", "New white vans.", 50, new LinkedList<>());
        testImageItem.setImageUrl("1590202130003.webp");
        items.add(testImageItem);
        items.add(new ItemModel("Shirt", "My old shirt.", 22.0, new LinkedList<>()));
        items.add(new ItemModel("Pants", "These new pants.", .08, new LinkedList<>()));
        items.add(new ItemModel("Shoes", "Two shoes.", 10.0, new LinkedList<>()));
        items.add(new ItemModel("Hat", "A very cool hat.", 393.0, new LinkedList<>()));
        items.add(new ItemModel("Underwear", "For under your wear.", 7.5, new LinkedList<>()));
        items.add(new ItemModel("Socks", "Two socks. They don't match.", 54.99, new LinkedList<>()));
        items.add(new ItemModel("Belt", "Use responsibly.", 99.99, new LinkedList<>()));
        items.add(new ItemModel("Gloves", "A box of disposable gloves.", 22.23, new LinkedList<>()));
        ArrayList<TradeModel> trades = new ArrayList<>();
        trades.add(new TradeModel(items.get(1), items.get(2)));
        trades.add(new TradeModel(items.get(3), items.get(2)));
        trades.add(new TradeModel(items.get(4), items.get(5)));
        trades.add(new TradeModel(items.get(3), items.get(1)));
        trades.add(new TradeModel(items.get(6), items.get(4)));
        trades.add(new TradeModel(items.get(7), items.get(7)));
        RecyclerView recyclerView = view.findViewById(R.id.matches);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MatchesAdapter(getActivity(), trades);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void onItemClick(View view, int position){}


}
