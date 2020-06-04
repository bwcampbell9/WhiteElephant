package com.example.white_elephant;

import com.example.white_elephant.models.TestUser;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

    // Runs all unit tests.
    @RunWith(Suite.class)
    @Suite.SuiteClasses({TestUser.class,
            TestViewMyTrade.class})
    public class WkmcintyTestSuite {}

