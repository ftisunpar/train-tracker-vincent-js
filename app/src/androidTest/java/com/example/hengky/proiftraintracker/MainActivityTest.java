package com.example.hengky.proiftraintracker;

import android.support.test.espresso.*;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Init Stasiun List to make next button working before executing test annotation
     */
    @Before
    public void init(){
        mainActivityActivityTestRule.getActivity();
    }

    /**
     * Test if the next button worked flawlessly
     */
    @Test
    public void testButtonNext() {
        mainActivityActivityTestRule.getActivity().selectedTrain = "not null";
        Espresso.onView(ViewMatchers.withId(R.id.btn_next)).perform(ViewActions.click());
        Assert.assertTrue(mainActivityActivityTestRule.getActivity().isFinishing());
    }

//    @Test
//    public void searchButtonTest(){
//        Espresso.onView(ViewMatchers.withId(R.id.btn_search)).perform(ViewActions.click());
//        ArrayList<String> train = mainActivityActivityTestRule.getActivity().namaKereta;
//
//        //TODO change expected to size when "parahyangan" entered as search query
//        Assert.assertEquals(13,train.size()-1);
//    }
}