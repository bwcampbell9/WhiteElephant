package com.example.white_elephant;

import com.example.white_elephant.models.TradeModel;

import org.junit.Test;
import com.example.white_elephant.models.Item;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

public class TestViewMyTrade {

    @Test
    public void testNewInstanceSimpleInstanceI2() {
        ViewMyTrade temp = new ViewMyTrade();
        Item tempitem1 = new Item("a","b",1.0,new ArrayList<>());
        Item tempitem2 = new Item("z","b",1.0,new ArrayList<>());
        TradeModel temptrade1 = new TradeModel(tempitem1,tempitem2);
        TradeModel temptrade2 = new TradeModel(tempitem1,tempitem2);
        temp.setTrade(temptrade1);
        temp.setArguments(temp.argBundling(temptrade1));
        temp.setConfirmed(true);
        assertEquals(temp.getConfirmed(),
                ViewMyTrade.newInstance(temptrade2,2,true).getConfirmed());
    }

    @Test
    public void testNewInstanceSimpleInstanceI1() {
        ViewMyTrade temp = new ViewMyTrade();
        Item tempitem1 = new Item("a","b",1.0,new ArrayList<>());
        Item tempitem2 = new Item("z","b",1.0,new ArrayList<>());
        TradeModel temptrade1 = new TradeModel(tempitem1,tempitem2);
        TradeModel temptrade2 = new TradeModel(tempitem1,tempitem2);
        temp.setTrade(temptrade1);
        temp.setArguments(temp.argBundling(temptrade1));
        temp.setAction(MatchesFragmentDirections.actionMatchesFragmentSelf());
        temp.setConfirmed(true);
        assertEquals(temp.getConfirmed(),
                ViewMyTrade.newInstance(temptrade2,0,true).getConfirmed());
    }

}
