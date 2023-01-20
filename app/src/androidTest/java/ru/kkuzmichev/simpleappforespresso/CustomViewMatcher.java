package ru.kkuzmichev.simpleappforespresso;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withParentIndex;
import static org.hamcrest.CoreMatchers.allOf;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class CustomViewMatcher {

    public static Matcher<View> withPositionInParent(int parentViewId, int position) {
        return allOf(withParent(withId(parentViewId)), withParentIndex(position));
    }

    public static Matcher<View> recyclerViewSizeMatcher(final int matcherSize) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {


            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {
                return matcherSize == recyclerView.getAdapter().getItemCount();
            }

            @Override
            public void describeTo(Description description) { // Доп. описание ошибки
                description.appendText("Item count: " + matcherSize);
            }
        };
    }
}
