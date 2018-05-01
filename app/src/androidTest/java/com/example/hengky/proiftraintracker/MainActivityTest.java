package com.example.hengky.proiftraintracker;

import android.service.chooser.ChooserTargetService;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.base.IdlingResourceRegistry;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.ActionMenuView;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule=new ActivityTestRule<MainActivity>(MainActivity.class);

    @Rule
    public ActivityTestRule<ChooseDestination> chooseDestinationActivityTestRule=new ActivityTestRule<ChooseDestination>(ChooseDestination.class);
    CountDownLatch countDownLatch=new CountDownLatch(100);

    //@Rule
   // public ActivityTestRule<MapsActivity> mapsActivityActivityTestRule=new ActivityTestRule<MapsActivity>(MapsActivity.class);

 //   IdlingResourceRegistry idlingResourceRegistry=new IdlingResourceRegistry(MainActivity.getListStasiun());

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void moveToAnotherActivity() {
    }

    @Test
    public void initializeFirebaseListStasiun() {
    }

    @Test
    public void getListStasiun() {
    }

    @Test
    public void onClick() throws InterruptedException {
        Thread.sleep(700);
        Espresso.onView(withId(R.id.btn_search)).perform(click());
     mainActivityActivityTestRule.getActivity().initializeFirebaseListStasiun();
        countDownLatch.await();
     mainActivityActivityTestRule.getActivity().getListStasiun();
        Thread.sleep(1000);
     ViewActions.typeText("Argo");
        Thread.sleep(1000);
        ViewActions.closeSoftKeyboard();
        Thread.sleep(1000);
        Espresso.pressBack();

        Thread.sleep(1000);
    mainActivityActivityTestRule.getActivity().selectedTrain="Argo Wilis";
     Thread.sleep(1000);
        Espresso.onView(withId(R.id.btn_next)).perform(click());
       Thread.sleep(1000);
       Espresso.onData(anything()).inAdapterView(withId(R.id.spinner_start_stasiun)).atPosition(0).perform(click());
        Thread.sleep(1000);
        Espresso.onData(anything()).inAdapterView(withId(R.id.spinner_end_stasiun)).atPosition(0).perform(click());
        Thread.sleep(1000);
        Espresso.onView(withId(R.id.btnOpenMap)).perform(click());

    }


}