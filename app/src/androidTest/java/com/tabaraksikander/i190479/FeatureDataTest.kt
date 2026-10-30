package com.tabaraksikander.i190479

import android.content.Context
import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/** Regression tests exercise user-visible outcomes rather than just handler execution. */
@RunWith(AndroidJUnit4::class)
class FeatureDataTest : DemoStateTest() {
    @Test fun everyProfileFieldIsSavedAndReopened() {
        val values = listOf(R.id.etName to "QA Person", R.id.etUsername to "qa.person", R.id.etWebsite to "https://example.com",
            R.id.etBio to "Profile persistence check", R.id.etEmail to "qa@example.com", R.id.etPhone to "+923001112233", R.id.etGender to "Custom")
        launch(EditProfileActivity::class.java).use {
            for ((id, value) in values) onView(withId(id)).perform(scrollTo(), replaceText(value), closeSoftKeyboard())
            onView(withId(R.id.btnSave)).perform(click())
        }
        launch(EditProfileActivity::class.java).use {
            for ((id, value) in values) onView(withId(id)).perform(scrollTo()).check(matches(withText(value)))
        }
        launch(ProfileActivity::class.java).use {
            onView(withId(R.id.profileName)).perform(scrollTo()).check(matches(withText("QA Person")))
            onView(withId(R.id.profileBio)).perform(scrollTo()).check(matches(withText("Profile persistence check")))
        }
    }

    @Test fun selectedImagesReturnToPostAndArePublished() {
        launch(CreatePostActivity::class.java).use {
            onView(withId(R.id.btnPhoto)).perform(scrollTo(), click()); ready()
            onView(withId(R.id.photo0)).perform(scrollTo(), click())
            onView(withId(R.id.photo5)).perform(scrollTo(), click())
            onView(withId(R.id.btnNext)).check(matches(withText("Next (2)"))).perform(click()); ready()
            onView(withId(R.id.postAttachments)).check { view, error ->
                if (error != null) throw error
                assertEquals(2, (view as LinearLayout).childCount)
            }
            onView(withId(R.id.btnPost)).perform(click()); ready()
            assertEquals("0,5", context.getSharedPreferences("demo", Context.MODE_PRIVATE).getString("postImages", null))
            onView(withId(R.id.publishedPhotos)).check { view, error -> if (error != null) throw error; assertEquals(2, (view as LinearLayout).childCount) }
        }
    }

    @Test fun profilePhotoResultUsesSelectedImageIdentity() {
        launch(EditProfileActivity::class.java).use {
            onView(withId(R.id.btnEditAvatarLabel)).perform(scrollTo(), click()); ready()
            onView(withId(R.id.photo3)).perform(scrollTo(), click())
            onView(withId(R.id.btnNext)).check(matches(withText("Next (1)"))).perform(click()); ready()
            onView(withId(R.id.editAvatarPhoto)).check(matches(isDisplayed()))
            onView(withId(R.id.btnSave)).perform(click())
            assertEquals(3, context.getSharedPreferences("demo", Context.MODE_PRIVATE).getInt("avatar", -1))
        }
        launch(ProfileActivity::class.java).use { onView(withId(R.id.profileAvatarPhoto)).perform(scrollTo()).check(matches(isDisplayed())) }
        launch(EditProfileActivity::class.java).use {
            onView(withId(R.id.btnEditCoverLabel)).perform(scrollTo(), click()); ready()
            onView(withId(R.id.photo4)).perform(scrollTo(), click())
            onView(withId(R.id.btnNext)).perform(click()); ready()
            onView(withId(R.id.btnSave)).perform(click())
            assertEquals(4, context.getSharedPreferences("demo", Context.MODE_PRIVATE).getInt("cover", -1))
        }
    }

    @Test fun filteringAndPersonIdentityReachTheirOwnConversation() {
        launch(SearchActivity::class.java).use {
            onView(withId(R.id.etSearch)).perform(replaceText("Omar Tariq"), closeSoftKeyboard())
            onView(withId(R.id.searchPerson0)).check(matches(not(isDisplayed())))
            onView(withId(R.id.searchPerson2)).perform(scrollTo(), click()); ready()
            onView(withId(R.id.profileName)).perform(scrollTo()).check(matches(withText("Omar Tariq")))
            onView(withId(R.id.btnSecondary)).perform(scrollTo(), click()); ready()
            onView(withId(R.id.chatName)).check(matches(withText("Omar Tariq")))
            onView(withId(R.id.etMessage)).perform(replaceText("A message for Omar Tariq"), closeSoftKeyboard())
            onView(withId(R.id.btnSend)).perform(click())
            assertEquals("A message for Omar Tariq", context.getSharedPreferences("demo", Context.MODE_PRIVATE).getString("message:Omar Tariq:0", null))
        }
    }

    @Test fun sharedStoryKeepsCaptionAndBackReturnsToEditor() {
        launch(HomeActivity::class.java).use {
            onView(withId(R.id.btnCreateStory)).perform(scrollTo(), click()); ready()
            onView(withId(R.id.btnShutter)).perform(click()); ready()
            onView(withId(R.id.storyCaption)).perform(click())
            onView(isAssignableFrom(android.widget.EditText::class.java)).perform(replaceText("QA Saturday story"), closeSoftKeyboard())
            onView(withText("Save")).perform(click())
            onView(withId(R.id.btnShareStory)).perform(click()); ready()
            onView(withId(R.id.storyCaption)).check(matches(withText("QA Saturday story")))
            pressBack(); ready()
            onView(withId(R.id.btnShareStory)).check(matches(isDisplayed()))
            onView(withId(R.id.storyCaption)).check(matches(withText("QA Saturday story")))
            pressBack(); ready(); onView(withId(R.id.btnShutter)).check(matches(isDisplayed()))
            pressBack(); ready(); onView(withId(R.id.btnCreatePost)).perform(scrollTo()).check(matches(isDisplayed()))
        }
    }
    @Test fun pickerRetainsSelectionAcrossRecreation() {
        launch(PhotoPickerActivity::class.java).use { scenario ->
            onView(withId(R.id.photo1)).perform(scrollTo(), click())
            onView(withId(R.id.btnNext)).check(matches(withText("Next (3)")))
            scenario.recreate(); ready()
            onView(withId(R.id.btnNext)).check(matches(withText("Next (3)")))
        }
    }
}

