package com.tabaraksikander.i190479

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.Espresso.pressBackUnconditionally
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import android.view.View
import androidx.test.espresso.ViewAction
import androidx.test.espresso.UiController
import org.hamcrest.Matcher
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/** Critical multi-step flows; these run on an emulator or connected device. */
@RunWith(AndroidJUnit4::class)
class NavigationTest {
    private fun settle() = object : ViewAction {
        override fun getConstraints(): Matcher<View> = isRoot()
        override fun getDescription() = "Wait for Activity transition to finish"
        override fun perform(ui: UiController, view: View) { ui.loopMainThreadForAtLeast(700) }
    }
    @Test fun homeCommentsBackReturnsToHome() {
        ActivityScenario.launch(HomeActivity::class.java).use {
            onView(withId(R.id.btnComments)).perform(scrollTo(), click())
            onView(withId(R.id.etReply)).check(matches(isDisplayed()))
            pressBack()
            onView(withId(R.id.btnCreatePost)).perform(scrollTo()).check(matches(isDisplayed()))
        }
    }

    @Test fun loginMenuLogoutClearsAuthenticatedStack() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val context = instrumentation.targetContext
        val intent = Intent(context, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        ActivityScenario.launch<LoginActivity>(intent).use {
            onView(withId(R.id.btnLogin)).perform(scrollTo(), click())
            onView(withId(R.id.navMenu)).perform(scrollTo(), click())
            onView(isRoot()).perform(settle())
            onView(withId(R.id.btnLogout)).perform(scrollTo(), click())
            onView(isRoot()).perform(settle())
            onView(withId(R.id.btnLogin)).check(matches(isDisplayed()))
            pressBackUnconditionally()
            instrumentation.waitForIdleSync()
            // Inspect our own task: Home must not remain behind the logged-out Login.
            val manager = context.getSystemService(android.app.ActivityManager::class.java)
            assertTrue(manager.appTasks.none {
                val top = it.taskInfo?.topActivity
                top?.packageName == context.packageName
            })
        }
    }
}
