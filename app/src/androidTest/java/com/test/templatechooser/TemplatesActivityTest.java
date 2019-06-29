package com.test.templatechooser;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.test.templatechooser.presentation.templates.TemplatesActivity;
import com.test.templatechooser.utils.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class TemplatesActivityTest {

    @Rule
    public ActivityTestRule<TemplatesActivity> mActivityRule
            = new ActivityTestRule<>(TemplatesActivity.class);

    @Before
    public void setUp() {
        IdlingRegistry
                .getInstance()
                .register(EspressoIdlingResource.get());
    }

    @After
    public void tearDown() {
        IdlingRegistry
                .getInstance()
                .unregister(EspressoIdlingResource.get());
    }

    @Test
    public void show_templates() {
        onView(withId(R.id.templatesPager))
                .perform(
                        swipeLeft(),
                        swipeLeft(),
                        swipeLeft(),
                        swipeLeft(),
                        swipeLeft(),
                        swipeLeft(),
                        swipeLeft(),
                        swipeLeft()
                );
    }
}
