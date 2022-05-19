package com.example.comp2100_assignment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.comp2100_assignment.ui.LoginActivity;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class FriendActivityTests {
    //sets the activity before and after every test is run
    //this example sets to loginActivity directly

    @Rule
    public ActivityScenarioRule<LoginActivity> friendActivityTestRule = new ActivityScenarioRule<>(LoginActivity.class);

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
        onView(withText("FRIENDS")).perform(click());
    }

    //Tests that all key elements are displayed
    @Test
    public void allElementsDisplayed() {
        onView(withId(R.id.request)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.add_friend)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.friend_id)).check(matches(isCompletelyDisplayed()));

    }

    //checks buttons are set to interactable
    @Test
    public void buttonsInteractable(){
        onView(withId(R.id.request)).check(matches(isClickable()));
        onView(withId(R.id.add_friend)).check(matches(isClickable()));
    }

    // tests that editing id of friend to add works
    // does NOT test if changing name adds friend with that name
    @Test
    public void editFriendIdWorks(){
        onView(withId(R.id.friend_id)).perform(replaceText("friendUsername"));
        onView(withText("friendUsername")).check(matches(isDisplayed()));
    }



}