package com.sanket.androidtestingrepo

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers

object EspressoUtils {

    fun isViewDisplayed(id: Int) {
        Espresso.onView(ViewMatchers.withId(id)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    fun enterTextInEditText(text: String, id: Int) {
        Espresso.onView(ViewMatchers.withId(id))
            .perform(ViewActions.clearText(), ViewActions.typeText(text))
        Espresso.closeSoftKeyboard()
    }

    fun replaceTextInEditText(text: String, id: Int) {
        Espresso.onView(ViewMatchers.withId(id))
            .perform(ViewActions.clearText(), ViewActions.replaceText(text))
        Espresso.closeSoftKeyboard()
    }

    fun performClick(id: Int) {
        Espresso.onView(ViewMatchers.withId(id)).perform(ViewActions.click())
    }

    fun isActivityOpen(simpleName: String) {
        Intents.intended(IntentMatchers.hasComponent(simpleName))
    }

}