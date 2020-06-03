package com.example.white_elephant;

import com.example.white_elephant.models.Item;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * unit test will execute on the development machine (host)
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class PriceExceptionTest {
    @Test
    public void lowerBoundException() {
        assertEquals(160, Item.lowerBound(200), 0.001);
    }

    @Test
    public void upperBoundException1() {
        assertEquals(20, Item.upperBound(0), 0.001);
    }

    @Test
    public void upperBoundException2() {
        assertEquals(240, Item.upperBound(200), 0.001);
    }
}