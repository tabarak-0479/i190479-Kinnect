package com.tabaraksikander.i190479

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

/** Complements the two rubric tests with the other specified navigation branches. */
@RunWith(AndroidJUnit4::class)
class NavigationCoverageTest : DemoStateTest() {
    @Test fun splashAutomaticallyOpensLogin() {
        launch(MainActivity::class.java).use {
            onView(isRoot()).perform(object : androidx.test.espresso.ViewAction {
                override fun getConstraints(): org.hamcrest.Matcher<android.view.View> = isRoot()
                override fun getDescription() = "Wait for the 1.5 second splash"
                override fun perform(ui: androidx.test.espresso.UiController, view: android.view.View) { ui.loopMainThreadForAtLeast(2000) }
            })
            onView(withId(R.id.btnLogin)).perform(scrollTo()).check(matches(isDisplayed()))
        }
    }
    @Test fun mainTabsReturnThroughPreviousScreens() {
        launch(HomeActivity::class.java).use {
            for (id in intArrayOf(R.id.navPeople, R.id.navMarketplace, R.id.navNotifications, R.id.navMenu)) {
                onView(withId(id)).perform(click()); ready()
            }
            onView(withId(R.id.btnLogout)).perform(scrollTo()).check(matches(isDisplayed()))
            pressBack(); ready(); onView(withId(R.id.btnPrevious)).perform(scrollTo()).check(matches(isDisplayed()))
            pressBack(); ready(); onView(withId(R.id.btnSell)).perform(scrollTo()).check(matches(isDisplayed()))
            pressBack(); ready(); onView(withId(R.id.btnYourFriends)).check(matches(isDisplayed()))
            pressBack(); ready(); onView(withId(R.id.btnCreatePost)).perform(scrollTo()).check(matches(isDisplayed()))
        }
    }
    @Test fun chatsCallAndBackKeepTheirConversation() {
        launch(HomeActivity::class.java).use {
            onView(withId(R.id.btnChats)).perform(scrollTo(), click()); ready()
            onView(withId(R.id.chatRow3)).perform(scrollTo(), click()); ready()
            onView(withId(R.id.chatName)).check(matches(withText("Omar Farooq")))
            onView(withId(R.id.btnCall)).perform(click()); ready()
            onView(withId(R.id.callName)).check(matches(withText("Omar Farooq")))
            onView(withId(R.id.btnEndCall)).perform(click()); ready()
            onView(withId(R.id.chatName)).check(matches(withText("Omar Farooq")))
            pressBack(); ready(); onView(withId(R.id.etChatSearch)).check(matches(isDisplayed()))
            pressBack(); ready(); onView(withId(R.id.btnCreatePost)).perform(scrollTo()).check(matches(isDisplayed()))
        }
    }
    @Test fun authenticationAndOtherHomeBranchesOpenAndReturn() {
        launch(LoginActivity::class.java).use {
            onView(withId(R.id.btnCreateAccount)).perform(scrollTo(), click()); ready()
            onView(withId(R.id.etSignupEmail)).perform(scrollTo()).check(matches(isDisplayed()))
            pressBack(); ready(); onView(withId(R.id.btnLogin)).perform(scrollTo(), click()); ready()
            onView(withId(R.id.btnStory)).perform(scrollTo(), click()); ready()
            onView(withId(R.id.storyPerson)).check(matches(withText("Omar Farooq")))
            pressBack(); ready(); onView(withId(R.id.btnLike)).perform(scrollTo(), click()); ready()
            onView(withId(R.id.reaction1)).perform(click()); ready()
            onView(withId(R.id.btnLikeLabel)).perform(scrollTo()).check(matches(withText("Love")))
            onView(withId(R.id.btnOwnProfile)).perform(scrollTo(), click()); ready()
            onView(withId(R.id.btnSecondary)).perform(scrollTo(), click()); ready()
            onView(withId(R.id.etName)).perform(scrollTo()).check(matches(isDisplayed()))
            pressBack(); ready(); onView(withId(R.id.profileName)).perform(scrollTo()).check(matches(isDisplayed()))
        }
    }
}

