package com.example.hengky.proiftraintracker

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Hengky on 5/2/2018.
 */

@RunWith(AndroidJUnit4::class)
class FinishActTest {

    @get:Rule
    var finishActivityActivityTestRule = ActivityTestRule(FinishActivity::class.java)

    @Before
    fun init() {
        //TODO implement required action before activity started
    }

    /**
     * Test if the finish button actually finish the activity
     */
    @Test
    fun testFinishingActivity() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_homepage)).perform(ViewActions.click())
        Assert.assertTrue(finishActivityActivityTestRule.activity.isFinishing)
    }
}
