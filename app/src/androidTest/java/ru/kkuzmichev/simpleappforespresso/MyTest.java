package ru.kkuzmichev.simpleappforespresso;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.content.Intent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import ru.kkuzmichev.simpleappforespresso.res.EspressoIdlingResources;


@RunWith(AllureAndroidJUnit4.class)
public class MyTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityTestRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void registerIdlingResources() {
        IdlingRegistry.getInstance().register(EspressoIdlingResources.idlingResource);
    }
    @After
    public void unregisterIdlingResources() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.idlingResource);
    }


    @Test
    public void isTextValid() {
        ViewInteraction mainText = onView(withId(R.id.text_home));
        mainText.check(matches(withText("This is home fragment")));
    }

    @Test
    public void isTextSlideshowValid() {
        ViewInteraction appCompatImageButton = onView(CustomViewMatcher.withPositionInParent(R.id.toolbar, 1));
        appCompatImageButton.perform(click());
        ViewInteraction navigationMenuItemView = onView(allOf(withId(R.id.nav_slideshow)));
        navigationMenuItemView.perform(click());
        ViewInteraction textView = onView(allOf(withId(R.id.text_slideshow), withText("This is slideshow fragment"),
                withParent(withParent(withId(R.id.nav_host_fragment_content_main))), isDisplayed()));
        textView.check(matches(withText("This is slideshow fragment")));
    }

    @Test
    public void testIntentBrowser() {
        ViewInteraction appSettingsButton = onView(CustomViewMatcher.withPositionInParent(R.id.toolbar, 2));
//        ViewInteraction appSettingsButton = onView(allOf(withContentDescription("More options")));
        appSettingsButton.perform(click());
        ViewInteraction settingsButton = onView(allOf(withId(R.id.title)));
        settingsButton.check(matches(isDisplayed()));
        Intents.init();
        settingsButton.perform(click());
        intended(allOf(hasAction(Intent.ACTION_VIEW), hasData("https://google.com")));
        Intents.release();
    }

    @Test
    public void testIdlingResourcesAndCustomsMatcherAndAssertion() {
        ViewInteraction appCompatImageButton = onView(CustomViewMatcher.withPositionInParent(R.id.toolbar, 1));
        appCompatImageButton.perform(click());
        ViewInteraction galleryButton = onView(allOf(withId(R.id.nav_gallery)));
        galleryButton.check(matches(isDisplayed()));
        galleryButton.perform(click());
        ViewInteraction listGallery = onView(allOf(withId(R.id.recycle_view)));
        listGallery.check(matches(isDisplayed()));
        listGallery.check(matches(CustomViewMatcher.recyclerViewSizeMatcher(10)));
        listGallery.check(CustomViewAssertions.isRecyclerView());
        ViewInteraction element = onView(allOf(withId(R.id.item_number), withText("7")));
        element.check(matches(withText("7")));
    }
}
