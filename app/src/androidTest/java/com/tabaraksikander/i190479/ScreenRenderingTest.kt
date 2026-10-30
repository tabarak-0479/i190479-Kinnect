package com.tabaraksikander.i190479

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.ViewAction
import androidx.test.espresso.UiController
import android.view.View
import org.hamcrest.Matcher
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

/** Extra visual QA: inflate every screen and save app-only screenshots for inspection. */
@RunWith(AndroidJUnit4::class)
class ScreenRenderingTest : DemoStateTest() {
    private fun renderSettle() = object : ViewAction {
        override fun getConstraints(): Matcher<View> = isRoot()
        override fun getDescription() = "Wait for rotation and window layout"
        override fun perform(ui: UiController, view: View) { ui.loopMainThreadForAtLeast(700) }
    }
    @Test fun allScreensRender() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val context = instrumentation.targetContext
        val screens = listOf(LoginActivity::class.java, SignupActivity::class.java, HomeActivity::class.java,
            ReactionActivity::class.java, CommentsActivity::class.java, CreatePostActivity::class.java,
            PhotoPickerActivity::class.java, CameraActivity::class.java, StoryEditorActivity::class.java,
            StoryViewerActivity::class.java, YourStoryActivity::class.java, SearchActivity::class.java,
            FriendsActivity::class.java, ProfileActivity::class.java, EditProfileActivity::class.java,
            OtherProfileActivity::class.java, NotificationsActivity::class.java, MenuActivity::class.java,
            ChatsActivity::class.java, ChatActivity::class.java, VoiceCallActivity::class.java, MarketplaceActivity::class.java)
        val output = File(context.getExternalFilesDir(null), "qa-v2/phone").apply { mkdirs() }
        for (screen in screens) {
            val intent = Intent(context, screen).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }
            ActivityScenario.launch<Activity>(intent).use { scenario ->
                onView(isRoot()).perform(renderSettle()).check(matches(isDisplayed()))
                instrumentation.uiAutomation.takeScreenshot()?.let { image ->
                    File(output, screen.simpleName + ".png").outputStream().use { image.compress(Bitmap.CompressFormat.PNG, 100, it) }
                    image.recycle()
                }
                if (screen in listOf(HomeActivity::class.java, ProfileActivity::class.java, ChatActivity::class.java, LoginActivity::class.java, VoiceCallActivity::class.java, CameraActivity::class.java)) {
                    scenario.onActivity { it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE }
                    onView(isRoot()).perform(renderSettle()).check(matches(isDisplayed()))
                    instrumentation.uiAutomation.takeScreenshot()?.let { image ->
                        File(output, screen.simpleName + "-landscape.png").outputStream().use { image.compress(Bitmap.CompressFormat.PNG, 100, it) }
                        image.recycle()
                    }
                    scenario.onActivity { it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED }
                    onView(isRoot()).perform(renderSettle())
                }
            }
        }
    }
}
