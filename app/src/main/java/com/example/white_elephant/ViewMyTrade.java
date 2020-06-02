package com.example.white_elephant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;

import com.example.white_elephant.models.TradeModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewMyTrade extends Fragment implements View.OnClickListener{

    private NavDirections action;
    private TradeModel trade;
    private TextView status;
    private boolean confirmed;

    public ViewMyTrade() {
        // Required empty public constructor
    }

    public static ViewMyTrade newInstance(TradeModel trade,int i, boolean confirmed){
        ViewMyTrade v = new ViewMyTrade();
        Bundle args = new Bundle();
        args.putSerializable("a", trade);
        v.trade = trade;
        v.setArguments(args);
        if (i == 0){
            v.action = MatchesFragmentDirections.actionMatchesFragmentSelf();
        }
        else if (i == 1){
            v.action = MyTradesDirections.actionMyTradesSelf();
        }
        v.confirmed = confirmed;
        return v;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_my_trade, container, false);
        ((TextView)(view.findViewById(R.id.item1title))).setText(trade.getItem1().getName());
        ((TextView)(view.findViewById(R.id.item2title))).setText(trade.getItem2().getName());
        ((TextView)(view.findViewById(R.id.item1valuetr))).setText(Double.toString(trade.getItem1().getValue()));
        ((TextView)(view.findViewById(R.id.item1desc))).setText(trade.getItem1().getDescription());
        ((TextView)(view.findViewById(R.id.item2valuetr))).setText(Double.toString(trade.getItem2().getValue()));
        ((TextView)(view.findViewById(R.id.item2desc))).setText(trade.getItem2().getDescription());
        status = view.findViewById(R.id.textView5);
        if (confirmed){
            status.setText("Status: Waiting for trading partner to confirm");
        }
        else{
            status.setText("Status: not confirmed by you");
        }

        Button goBack = view.findViewById(R.id.buttonCloseMT);
        goBack.setOnClickListener(this);
        Button confirm = view.findViewById(R.id.buttonToggleConfirm);
        confirm.setOnClickListener(this);

        return view;
    }

    public void onClick(View v){
        if (v.getId() == R.id.buttonCloseMT){
            NavController navController = ((MainActivity) getActivity()).getNavController();
            navController.navigate(action);
        } else if (v.getId() == R.id.buttonToggleConfirm){
            if (confirmed){
                status.setText("Status: not confirmed by you");
                confirmed = false;
            }
            else{
                status.setText("Status: Waiting for trading partner to confirm");
                confirmed = true;
                //FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                //int j = 1;
                //if (action.equals(MatchesFragmentDirections.actionMatchesFragmentSelf())){
                //    j =0;
                //}
                //Fragment fragment = ViewMyTrade.newInstance(this.trade, j, !confirmed);
                //ft.replace(R.id.TradeViewLayout, fragment);
                //ft.commit();
            }
        }
    }
}
