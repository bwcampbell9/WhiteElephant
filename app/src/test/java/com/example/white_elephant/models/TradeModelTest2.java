package com.example.white_elephant.models;

import org.junit.Test;

import com.example.white_elephant.models.TradeModel;

import static org.junit.Assert.*;

public class TradeModelTest2 {

    @Test
    public void isUser1Confirm() {
        TradeModel trade = new TradeModel(new Item(), new Item());
        assertEquals(trade.isUser1Confirm(), false);
    }

    @Test
    public void isUser2Confirm() {
        TradeModel trade = new TradeModel(new Item(), new Item());
        trade.setUser2Confirm(true);
        assert(trade.isUser2Confirm());
    }
}