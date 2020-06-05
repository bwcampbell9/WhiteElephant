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
public class TestItemLists {
    @Test
    public void getLikes() {
        Item item = new Item("test1", "desc", 12.5, new ArrayList<String>());

        assertEquals(0, item.getLiked().size());
    }

    @Test
    public void setLikes() {
        Item item = new Item("test1", "desc", 12.5, new ArrayList<String>());
        ArrayList<String> list = new ArrayList<String>();
        list.add("test");
        item.setLiked(list);

        assertEquals("test", item.getLiked().get(0));
    }

    @Test
    public void addLike() {
        Item item = new Item("test1", "desc", 12.5, new ArrayList<String>());
        item.addLiked("test");

        assertEquals("test", item.getLiked().get(0));
    }

    @Test
    public void addDislike() {
        Item item = new Item("test1", "desc", 12.5, new ArrayList<String>());
        item.addDisliked("test");

        assertEquals("test", item.getDisliked().get(0));
    }

}