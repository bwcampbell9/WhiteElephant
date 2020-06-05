package com.example.white_elephant;

import com.example.white_elephant.models.Item;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * unit test will execute on the development machine (host)
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class UpperBoundTest {

    // LOOP TESTING 2
    @Test
    public void upperBound1() {
        // loop executed exactly once
        assertEquals(24, Item.upperBound(15), 0.001);
    }
    @Test
    public void upperBound2(){
        // loop executed exactly twice
        assertEquals(75, Item.upperBound(50), 0.001);
    }
    @Test
    public void upperBound3(){
        // loop executed "typical" number of times (3 times)
        assertEquals(140, Item.upperBound(100), 0.001);
    }
    @Test
    public void upperBound4() {
        // loop executed exactly n - 1 times (4 times)
        assertEquals(650, Item.upperBound(500), 0.001);
    }
    @Test
    public void upperBound5() {
        // loop executed exactly n times (5 times)
        assertEquals(1200, Item.upperBound(1000), 0.001);
    }
    @Test
    public void upperBoundLoopNotExecuted1(){
        // loop not executed
        assertEquals(16, Item.upperBound(0), 0.001);
    }
    @Test
    public void upperBoundLoopNotExecuted2(){
        // loop not executed
        assertEquals(2200, Item.upperBound(2000), 0.001);
    }
}