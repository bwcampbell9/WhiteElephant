package com.example.white_elephant;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.white_elephant.models.Item;
import com.example.white_elephant.models.TradeModel;
import com.example.white_elephant.util.Database;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MatchesFragment extends Fragment implements MatchesAdapter.ItemClickListener {

    private MatchesAdapter adapter;
    ArrayList<TradeModel> trades;
    private RecyclerView recyclerView;

    public MatchesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matches, container, false);
        ArrayList<Item> items = new ArrayList<>();

        trades = new ArrayList<>();

        recyclerView = view.findViewById(R.id.matches);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MatchesAdapter(getContext(), trades);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        getMatches();

        return view;
    }

    public void onItemClick(View view, int position){
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        Fragment fragment = ViewMyTrade.newInstance(adapter.getTrade(position), 0, false);
        ft.replace(R.id.Matches, fragment);
        ft.commit();
    }

    public void getMatches() {
        Database.getInstance().getDocsByProp("items", "user", "==",
                ((MainActivity) getActivity()).getUser().getUid(), (Object item) -> {
                    if (((Item)item).getMatches() != null ) {
                        Log.e("Matches", "Matches found for " + ((Item)item).getName());
                        for (String id : ((Item) item).getMatches()) {
                            Log.e("Matches", "Matches is " + id);
                            Database.getInstance().getDocument("items/" + id, (Object other) -> {
                                Log.e("Matches", "Match retrieved " + ((Item)other).getName());
                                trades.add(new TradeModel((Item) item, (Item) other));
                                adapter.notifyDataSetChanged();
                            }, Item.class);
                        }
                    }
                }, Item.class);
    }
}
