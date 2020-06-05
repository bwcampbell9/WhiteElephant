package com.example.white_elephant;

import com.example.white_elephant.models.Item;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * unit test will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LowerBoundTest {

    // LOOP TESTING 1
    @Test
    public void lowerBound1() {
        // loop executed exactly once
        assertEquals(6, Item.lowerBound(15), 0.001);
    }
    @Test
    public void lowerBound2(){
        // loop executed exactly twice
        assertEquals(25, Item.lowerBound(50), 0.001);
    }
    @Test
    public void lowerBound3(){
        // loop executed "typical" number of times (3 times)
        assertEquals(60, Item.lowerBound(100), 0.001);
    }
    @Test
    public void lowerBound4() {
        // loop executed exactly n - 1 times (4 times)
        assertEquals(350, Item.lowerBound(500), 0.001);
    }
    @Test
    public void lowerBound5() {
        // loop executed exactly n times (5 times)
        assertEquals(800, Item.lowerBound(1000), 0.001);
    }
    @Test
    public void lowerBoundLoopNotExecuted(){
        // loop not executed
        assertEquals(1800, Item.lowerBound(2000), 0.001);
    }

}