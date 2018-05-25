package com.example.hengky.proiftraintracker

import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Hengky on 5/2/2018.
 */

class GMapTest {
    @get:Rule
    internal var gMapFragmentActivityTestRule = ActivityTestRule(MapsActivity::class.java)

    //Initialize GMapFragment
    private var mapFragment: GMapFragment? = null

    @Before
    fun init() {
        mapFragment = GMapFragment()
        gMapFragmentActivityTestRule.activity.supportFragmentManager.beginTransaction()
                .add(1, mapFragment, null).commit()
    }

    @Test
    fun isFragmentInitialized() {
        Espresso.onView(ViewMatchers.withId(R.id.map)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
