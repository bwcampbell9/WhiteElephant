package com.example.white_elephant.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class TradeModelTest {

    @Test
    public void getItem1() {
        TradeModel trade = new TradeModel(new Item(), new Item());
        assertEquals(trade.getItem1().getDescription(), "");
    }

    @Test
    public void getItem2() {
        TradeModel trade = new TradeModel(new Item(), new Item());
        assertEquals(trade.getItem2().getValue(), -1, 0.0001);
    }
}