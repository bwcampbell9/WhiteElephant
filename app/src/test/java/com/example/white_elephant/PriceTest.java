package com.example.white_elephant;

import com.example.white_elephant.models.Item;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * unit test will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PriceTest {
    @Test
    public void lowerBound() {
        assertEquals(35, Item.lowerBound(50), 0.001);
    }

    @Test
    public void upperBound() {
        assertEquals(65, Item.upperBound(50), 0.001);
    }
}