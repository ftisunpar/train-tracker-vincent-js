package com.example.hengky.proiftraintracker;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Hengky on 5/2/2018.
 */

public class GMapFragmentTest {
    @Rule
    ActivityTestRule<MapsActivity> gMapFragmentActivityTestRule = new ActivityTestRule<>(MapsActivity.class);

    //Initialize GMapFragment
    private GMapFragment mapFragment;

    @Before
    public void init(){
        mapFragment = new GMapFragment();
        gMapFragmentActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                .add(1,mapFragment,null).commit();
    }

    @Test
    public void isFragmentInitialized(){
        Espresso.onView(ViewMatchers.withId(R.id.map)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
