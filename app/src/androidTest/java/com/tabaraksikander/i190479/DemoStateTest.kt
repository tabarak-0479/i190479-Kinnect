package com.tabaraksikander.i190479

import android.content.Context
import android.content.Intent
import android.app.Activity
import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.UiController
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before

/** Keep QA independent without discarding the student's existing local demo data. */
open class DemoStateTest {
    protected val context get() = InstrumentationRegistry.getInstrumentation().targetContext
    private var backup: Map<String, *> = emptyMap<String, Any>()
    @Before fun isolateDemoState() {
        val prefs = context.getSharedPreferences("demo", Context.MODE_PRIVATE)
        backup = HashMap(prefs.all)
        prefs.edit().clear().commit()
    }
    @After fun restoreDemoState() {
        val edit = context.getSharedPreferences("demo", Context.MODE_PRIVATE).edit().clear()
        for ((key, value) in backup) when (value) {
            is String -> edit.putString(key, value)
            is Boolean -> edit.putBoolean(key, value)
            is Int -> edit.putInt(key, value)
            is Long -> edit.putLong(key, value)
            is Float -> edit.putFloat(key, value)
            is Set<*> -> edit.putStringSet(key, value.filterIsInstance<String>().toSet())
        }
        edit.commit()
    }
    protected fun launch(screen: Class<out Activity>): ActivityScenario<Activity> = ActivityScenario.launch(
        Intent(context, screen).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK })
    protected fun settle() = object : ViewAction {
        override fun getConstraints(): Matcher<View> = isRoot()
        override fun getDescription() = "Let Activity transition and result delivery finish"
        override fun perform(ui: UiController, view: View) { ui.loopMainThreadForAtLeast(500) }
    }
    protected fun ready() { onView(isRoot()).perform(settle()) }
}
