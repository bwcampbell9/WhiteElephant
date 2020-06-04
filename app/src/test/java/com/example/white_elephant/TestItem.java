package com.example.white_elephant;

import com.example.white_elephant.models.Item;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * unit test will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestItem {
    @Test
    public void getName() {
        Item item = new Item("test1", "desc", 12.5, new ArrayList<String>());

        assertEquals("test1", item.getName());
    }

    @Test
    public void getValue() {
        Item item = new Item("test1", "desc", 12.5, new ArrayList<String>());

        assertEquals(12.5, item.getValue(), .0001);
    }
    @Test
    public void getDescription() {
        Item item = new Item("test1", "desc", 12.5, new ArrayList<String>());

        assertEquals("desc", item.getDescription());
    }
    @Test
    public void setValue() {
        Item item = new Item("test1", "desc", 12.5, new ArrayList<String>());
        item.setValue(1.23);
        assertEquals(1.23, item.getValue(), .0001);
    }
    @Test
    public void setName() {
        Item item = new Item("test1", "desc", 12.5, new ArrayList<String>());
        item.setName("new_name");
        assertEquals("new_name", item.getName());
    }

}