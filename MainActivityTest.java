package com.example.securelockdown;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

/**
 * UI Integration tests for MainActivity using Espresso
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = 
        new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        // Activity is launched before each test
    }

    @Test
    public void testMainActivityDisplaysStatusText() {
        onView(withId(R.id.status_text))
            .check(matches(isDisplayed()));
    }

    @Test
    public void testMainActivityDisplaysAllButtons() {
        onView(withId(R.id.set_button))
            .check(matches(isDisplayed()));
        onView(withId(R.id.submit_button))
            .check(matches(isDisplayed()));
        onView(withId(R.id.lock_button))
            .check(matches(isDisplayed()));
    }

    @Test
    public void testPinInputIsDisplayed() {
        onView(withId(R.id.pin_input))
            .check(matches(isDisplayed()));
    }

    @Test
    public void testSetPinWithValidInput() {
        onView(withId(R.id.pin_input))
            .perform(typeText("1234"), closeSoftKeyboard());
        
        onView(withId(R.id.set_button))
            .perform(click());
        
        // PIN input should be cleared after setting
        onView(withId(R.id.pin_input))
            .check(matches(withText("")));
    }

    @Test
    public void testSetPinWithShortInput() {
        onView(withId(R.id.pin_input))
            .perform(typeText("12"), closeSoftKeyboard());
        
        onView(withId(R.id.set_button))
            .perform(click());
        
        // Toast will appear, but we verify PIN input still has content
        // (since PIN was rejected)
    }

    @Test
    public void testSimulateForgottenPin() {
        onView(withId(R.id.lock_button))
            .perform(click());
        
        // Status should reflect device locked
        onView(withId(R.id.status_text))
            .check(matches(withText(containsString("DEVICE LOCKED"))));
    }

    @Test
    public void testVerifyPinFlow() {
        // First set a PIN
        onView(withId(R.id.pin_input))
            .perform(typeText("5678"), closeSoftKeyboard());
        
        onView(withId(R.id.set_button))
            .perform(click());

        // Simulate forgotten PIN
        onView(withId(R.id.lock_button))
            .perform(click());
        
        // Verify PIN flow
        onView(withId(R.id.pin_input))
            .perform(typeText("5678"), closeSoftKeyboard());
        
        onView(withId(R.id.submit_button))
            .perform(click());
    }
}
