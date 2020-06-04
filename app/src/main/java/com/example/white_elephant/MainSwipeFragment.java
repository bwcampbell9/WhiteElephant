package com.example.white_elephant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.white_elephant.models.Item;
import com.example.white_elephant.util.Database;
import com.example.white_elephant.views.ItemSwipeView;
import com.example.white_elephant.views.ItemTileView;

import java.util.ArrayList;

public class MainSwipeFragment extends Fragment {

    private static String itemsCollection = "items/";
    private FragmentManager fragMan;
    private Item trading = null;

    ArrayList<com.example.white_elephant.views.ItemSwipeView> itemViewList;

    public MainSwipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BlankFragment.
     */
    public static MainSwipeFragment newInstance() {
        return new MainSwipeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_swipe_layout, container, false);

        fragMan = getChildFragmentManager();

        itemViewList = new ArrayList<>();

        FragmentTransaction fragTransaction = fragMan.beginTransaction();

        TextView text = view.findViewById(R.id.textView);

        trading = ((MainActivity)getActivity()).getTrading();
        if(trading != null) {
            getArrayData();
            text.setText(R.string.with_item);
            fragTransaction.add(R.id.trading_with_view, ItemTileView.newInstance(trading));
        } else {
            text.setText(R.string.without_item);
        }

        if (itemViewList.size() > 1) {
            fragTransaction.add(R.id.swipe_cards_layout, (Fragment) itemViewList.get(1));
        }
        if (!itemViewList.isEmpty()) {
            fragTransaction.add(R.id.swipe_cards_layout, itemViewList.get(0));
        }
        fragTransaction.commit();

        return view;
    }

    private ItemSwipeView popTopItem() {
        if(itemViewList.isEmpty()) {
            return null;
        }
        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        fragTransaction.remove(itemViewList.get(0));
        if(itemViewList.size() > 2) {
            fragTransaction.detach(itemViewList.get(1));
            fragTransaction.add(R.id.swipe_cards_layout, itemViewList.get(2));
            fragTransaction.attach(itemViewList.get(1));
        }
        fragTransaction.commit();

        return itemViewList.remove(0);
    }

    private void pushNewItem(Object obj) {
        ItemSwipeView view = ItemSwipeView.newInstance((Item) obj);
        itemViewList.add(view);
        if(itemViewList.size() > 2) {
            return;
        }
        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        fragTransaction.add(R.id.swipe_cards_layout, view);
        if(itemViewList.size() == 2) {
            fragTransaction.detach(itemViewList.get(0));
            fragTransaction.attach(itemViewList.get(0));
        }
        fragTransaction.commit();
    }

    public void likeItem() {
        MainActivity activity = ((MainActivity) getActivity());
        ItemSwipeView item = popTopItem();
        activity.getTrading().addLiked(item.item.getUid());
        if(item.item.getLiked().contains(activity.getTrading().getUid())) {
            // Match!
            activity.getTrading().addMatch(item.item.getUid());
            item.item.addMatch(activity.getTrading().getUid());
            makeToast("It's a match!");
        }
        Database.getInstance().updateDocument(itemsCollection + activity.getTrading().getUid(),  activity.getTrading());
        Database.getInstance().updateDocument(itemsCollection + item.item.getUid(), item.item);
    }

    public void dislikeItem() {
        MainActivity activity = ((MainActivity) getActivity());
        ItemSwipeView item = popTopItem();
        activity.getTrading().addDisliked(item.item.getUid());
        Database.getInstance().updateDocument(itemsCollection + activity.getTrading().getUid(),  activity.getTrading());
    }
    public void saveItem() {
        MainActivity activity = ((MainActivity) getActivity());
        ItemSwipeView item = popTopItem();
        activity.getTrading().addSaved(item.item.getUid());
        Database.getInstance().updateDocument(itemsCollection + activity.getTrading().getUid(),  activity.getTrading());
    }

    private void getArrayData() {
        Database.getInstance().getItemsByItem(trading, this::pushNewItem);
    }

    private void makeToast(String str){
        Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
    }

}