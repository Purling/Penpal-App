package com.example.comp2100_assignment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.comp2100_assignment.ui.QueueActivity;
import com.example.comp2100_assignment.ui.AccountSettingsActivity;
import com.example.comp2100_assignment.ui.FriendActivity;
import com.example.comp2100_assignment.ui.LoginActivity;
import com.example.comp2100_assignment.ui.SearchActivity;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTests {
    //sets the activity before and after every test is run
    //this example sets to loginActivity directly

    @Rule
    public ActivityScenarioRule<LoginActivity> mainActivityTestRule = new ActivityScenarioRule<>(LoginActivity.class);

    //setup to get into main activity
    //this actually navigates the app to reach the activity
    //so major UI changes could break this
    @Before
    public void mainActivityTestSetup(){
        onView(withId(R.id.username)).perform(typeText("testUser"));
        onView(withId(R.id.username)).perform(pressBack());
        onView(withId(R.id.password)).perform(typeText("testUserPass"));
        onView(withId(R.id.username)).perform(pressBack());
        onView(withId(R.id.login)).perform(click());
    }

    //Tests that all elements are displayed
    @Test
    public void allElementsDisplayed() {
        onView(withId(R.id.profile_picture)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.title_app)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.input_text)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.search)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.my_lv)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.match)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.settingsButton)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.friend)).check(matches(isCompletelyDisplayed()));
    }

    //checks search button works
    @Test
    public void searchQueueButtonWorks(){
        Intents.init();
        onView(withId(R.id.search)).perform(click());
        intended(hasComponent(SearchActivity.class.getName()));
        Intents.release();
    }

    //checks match button works
    @Test
    public void matchButtonWorks(){
        Intents.init();
        onView(withId(R.id.match)).perform(click());
        intended(hasComponent(QueueActivity.class.getName()));
        Intents.release();
    }

    //checks settings button works
    @Test
    public void settingsButtonWorks(){
        Intents.init();
        onView(withId(R.id.settingsButton)).perform(click());
        intended(hasComponent(AccountSettingsActivity.class.getName()));
        Intents.release();
    }

    //checks friends button works
    @Test
    public void friendsButtonWorks(){
        Intents.init();
        onView(withId(R.id.friend)).perform(click());
        intended(hasComponent(FriendActivity.class.getName()));
        Intents.release();
    }



}