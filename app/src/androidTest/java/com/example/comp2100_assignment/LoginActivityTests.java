package com.example.comp2100_assignment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.comp2100_assignment.ui.LoginActivity;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
/**
 * @author William Loughton
 * UI tests for LoginActivity
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTests {
    //sets the activity before and after every test is run
    //this example sets to loginActivity directly
    @Rule
    public ActivityScenarioRule<LoginActivity> loginActivityTestRule = new ActivityScenarioRule<>(LoginActivity.class);

    //Tests that all elements are displayed
    @Test
    public void allElementsDisplayed() {
        onView(withId(R.id.appLogo)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.appTitle)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.username)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.password)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.login)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.register)).check(matches(isCompletelyDisplayed()));
    }

    //tests typing into username
    @Test
    public void usernameInput(){
        onView(withId(R.id.username)).perform(typeText("jeffery"));
        onView(withText("jeffery")).check(matches(isDisplayed()));
        onView(withId(R.id.username)).perform(typeText("123"));
        onView(withText("jeffery123")).check(matches(isDisplayed()));
        onView(withId(R.id.username)).perform(replaceText("jono"));
        onView(withText("jono")).check(matches(isDisplayed()));
    }

    //tests typing into password
    @Test
    public void passwordInput(){
        onView(withId(R.id.password)).perform(typeText("verysecurepassword123"));
        onView(withText("verysecurepassword123")).check(matches(isDisplayed()));
        onView(withId(R.id.password)).perform(typeText("4"));
        onView(withText("verysecurepassword1234")).check(matches(isDisplayed()));
        onView(withId(R.id.password)).perform(replaceText("evenbetterp4ssword"));
        onView(withText("evenbetterp4ssword")).check(matches(isDisplayed()));


    }

}