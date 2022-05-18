package com.example.comp2100_assignment;

import static androidx.core.content.UnusedAppRestrictionsConstants.ERROR;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AccountSettingsActivityTests {
    //sets the activity before and after every test is run
    //this example sets to loginActivity directly

    @Rule
    public ActivityScenarioRule<LoginActivity> accountSettingsActivityTestRule = new ActivityScenarioRule<>(LoginActivity.class);

    //setup to get into queue state
    //this actually navigates the app to reach the activity
    //so major UI changes could break this
    @Before
    public void queueActivityTestSetup(){
        onView(withId(R.id.username)).perform(typeText("testUser"));
        onView(withId(R.id.username)).perform(pressBack());
        onView(withId(R.id.password)).perform(typeText("testUserPass"));
        onView(withId(R.id.username)).perform(pressBack());
        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.settingsButton)).perform(click());
    }

    //Tests that all key elements are displayed
    @Test
    public void allElementsDisplayed() {
        onView(withId(R.id.saveSettingsButton)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.editDisplayName)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.switchSports)).check(matches(isDisplayed()));
        onView(withId(R.id.switchFood)).check(matches(isDisplayed()));
        onView(withId(R.id.switchMusic)).check(matches(isDisplayed()));
        //travel is initially scrolled off, so scroll it on
        onView(withId(R.id.switchSports)).perform(swipeLeft());
        onView(withId(R.id.switchTravel)).check(matches(isDisplayed()));
        //language labels, these need to be changed if languages change
        onView(withId(R.id.languageLabel1)).check(matches(isDisplayed()));
        onView(withId(R.id.languageSpinner1)).check(matches(isDisplayed()));
        onView(withId(R.id.languageLabel2)).check(matches(isDisplayed()));
        onView(withId(R.id.languageSpinner2)).check(matches(isDisplayed()));
        onView(withId(R.id.languageLabel3)).check(matches(isDisplayed()));
        onView(withId(R.id.languageSpinner3)).check(matches(isDisplayed()));
        onView(withId(R.id.languageLabel4)).check(matches(isDisplayed()));
        onView(withId(R.id.languageSpinner4)).check(matches(isDisplayed()));
        onView(withId(R.id.languageLabel5)).check(matches(isDisplayed()));
        onView(withId(R.id.languageSpinner5)).check(matches(isDisplayed()));



    }

    //checks save button exits
    @Test
    public void saveButtonWorks(){
        Intents.init();
        onView(withId(R.id.saveSettingsButton)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
        Intents.release();
    }

    //checks all interactables are interactable
    @Test
    public void allInteractablesWork(){
        onView(withId(R.id.switchSports)).check(matches(isClickable()));
        onView(withId(R.id.switchFood)).check(matches(isClickable()));
        onView(withId(R.id.switchMusic)).check(matches(isClickable()));
        //travel is initially scrolled off, so scroll it on
        onView(withId(R.id.switchSports)).perform(swipeLeft());
        onView(withId(R.id.switchTravel)).check(matches(isClickable()));
        //language labels, these need to be changed if languages change
        onView(withId(R.id.languageSpinner1)).check(matches(isClickable()));
        onView(withId(R.id.languageSpinner2)).check(matches(isClickable()));
        onView(withId(R.id.languageSpinner3)).check(matches(isClickable()));
        onView(withId(R.id.languageSpinner4)).check(matches(isClickable()));
        onView(withId(R.id.languageSpinner5)).check(matches(isClickable()));
    }

    // tests that editing display name works
    // does NOT test if changing and saving correctly saves the display name
    @Test
    public void editDisplayNameWorks(){
        onView(withId(R.id.editDisplayName)).perform(replaceText("newUsername"));
        onView(withText("newUsername")).check(matches(isDisplayed()));
    }


}