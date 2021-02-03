package com.muzzmatch.interview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.muzzmatch.interview.data.entities.User
import com.muzzmatch.interview.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
@HiltAndroidTest
class MainActivityTest {

    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    var rule = RuleChain.outerRule(HiltAndroidRule(this)).around(activityScenarioRule)

    @Test
    fun checkUserNameIsDisplayed() {
        onView(withId(R.id.title_user_name)).check(matches(isDisplayed()))
        onView(withId(R.id.title_user_name)).check(matches(withText(User().userName)));
    }

    @Test
    fun checkHintTextInput() {
        onView(withId(R.id.chat_edit_text)).check(matches(withHint("It's a muzmatch !!")))
    }

    @Test
    fun checkSubmitButtonIsEnabled() {
        onView(withId(R.id.chat_edit_text)).perform(typeText("How are you"))
        onView(withId(R.id.chat_enter_button)).check(matches(isEnabled()))
        onView(withId(R.id.chat_edit_text)).perform(clearText())
        onView(withId(R.id.chat_enter_button)).check(matches(not(isEnabled())))
    }
}

class ScrollToBottomAction : ViewAction {
    override fun getDescription(): String {
        return "scroll RecyclerView to bottom"
    }

    override fun getConstraints(): Matcher<View> {
        return allOf<View>(isAssignableFrom(RecyclerView::class.java), isDisplayed())
    }

    override fun perform(uiController: UiController?, view: View?) {
        val recyclerView = view as RecyclerView
        val itemCount = recyclerView.adapter?.itemCount
        val position = itemCount?.minus(1) ?: 0
        recyclerView.scrollToPosition(position)
        uiController?.loopMainThreadUntilIdle()
    }
}