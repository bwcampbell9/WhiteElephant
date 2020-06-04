package com.example.white_elephant.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TradeModelTest2 {

    @Test
    public void isUser1Confirm() {
        TradeModel trade = new TradeModel(new Item(), new Item());
        assertEquals(false, trade.isUser1Confirm());
    }

    @Test
    public void isUser2Confirm() {
        TradeModel trade = new TradeModel(new Item(), new Item());
        trade.setUser2Confirm(true);
        assertEquals(true, trade.isUser2Confirm());
    }
}