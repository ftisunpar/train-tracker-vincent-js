package com.example.hengky.proiftraintracker

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.jvm.javaClass;

/**
 * Created by Hengky Surya on 5/2/2018.
 */
class MainActTest {

    @get:Rule
    var mainActivityActivityTestRule = ActivityTestRule(MainActivity::class.java);

    /**
     * Init Stasiun List to make next button working before executing test annotation
     */
    @Before
     fun init() {
        mainActivityActivityTestRule.activity
    }

    /**
     * Test if the next button worked flawlessly
     */
    @Test
     fun testButtonNext() {
        mainActivityActivityTestRule.activity.selectedTrain = "not null"
        Espresso.onView(ViewMatchers.withId(R.id.btn_next)).perform(ViewActions.click())
        Assert.assertTrue(mainActivityActivityTestRule.activity.isFinishing)
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