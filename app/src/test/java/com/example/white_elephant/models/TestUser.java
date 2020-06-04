package com.example.white_elephant.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestUser {

    @Test
    public void testUserUIDCorrect() {
        User temp = new User("blahblah");
        assertEquals("blahblah",temp.getUid());
    }

    @Test
    public void testUserIIdList() {
        User temp = new User("100");
        assertTrue(temp.getIidList().isEmpty());
    }
}