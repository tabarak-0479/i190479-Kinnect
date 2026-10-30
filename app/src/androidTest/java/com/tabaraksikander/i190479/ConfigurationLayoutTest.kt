package com.tabaraksikander.i190479

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

/** Render real Android XML under tablet and large-font resource configurations.
 * These are configuration renders, not claims of physical-tablet testing. */
@RunWith(AndroidJUnit4::class)
class ConfigurationLayoutTest : DemoStateTest() {
    @Test fun tabletAndLargeFontLayoutsKeepPinnedControlsVisible() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val screens = listOf(
            "01-splash" to R.layout.activity_main, "02-login" to R.layout.activity_login,
            "03-signup" to R.layout.activity_signup, "04-home" to R.layout.activity_home,
            "05-reactions" to R.layout.activity_reaction, "06-comments" to R.layout.activity_comments,
            "07-post" to R.layout.activity_create_post, "08-picker" to R.layout.activity_photo_picker,
            "09-camera" to R.layout.activity_camera, "10-story-editor" to R.layout.activity_story_editor,
            "11-story-viewer" to R.layout.activity_story_viewer, "12-your-story" to R.layout.activity_your_story,
            "13-search" to R.layout.activity_search, "14-friends" to R.layout.activity_friends,
            "15-profile" to R.layout.activity_profile, "16-edit-profile" to R.layout.activity_edit_profile,
            "17-other-profile" to R.layout.activity_other_profile, "18-notifications" to R.layout.activity_notifications,
            "19-menu" to R.layout.activity_menu, "20-chats" to R.layout.activity_chats,
            "21-chat" to R.layout.activity_chat, "22-call" to R.layout.activity_voice_call,
            "23-marketplace" to R.layout.activity_marketplace)
        val configurations = listOf(
            Triple("tablet-portrait", 600 to 960, 1f), Triple("tablet-landscape", 960 to 600, 1f),
            Triple("large-font", 390 to 844, 1.3f), Triple("largest-font", 390 to 844, 2f))
        val output = File(context.getExternalFilesDir(null), "qa-v2/configurations").apply { mkdirs() }
        for ((label, size, font) in configurations) for ((name, layout) in screens) {
            var bitmap: Bitmap? = null
            instrumentation.runOnMainSync {
                val config = Configuration(context.resources.configuration).apply {
                    densityDpi = 160; screenWidthDp = size.first; screenHeightDp = size.second
                    smallestScreenWidthDp = minOf(size.first, size.second); fontScale = font
                    orientation = if (size.first > size.second) Configuration.ORIENTATION_LANDSCAPE else Configuration.ORIENTATION_PORTRAIT
                    uiMode = Configuration.UI_MODE_TYPE_NORMAL or Configuration.UI_MODE_NIGHT_NO
                }
                val themed = ContextThemeWrapper(context.createConfigurationContext(config), R.style.Theme_I190479)
                // Match AppCompatActivity's widget substitution, including app:tint and auto-size.
                val inflater = LayoutInflater.from(themed).cloneInContext(themed)
                inflater.factory2 = object : LayoutInflater.Factory2 {
                    override fun onCreateView(parent: View?, name: String, c: android.content.Context, attrs: android.util.AttributeSet): View? = onCreateView(name, c, attrs)
                    override fun onCreateView(name: String, c: android.content.Context, attrs: android.util.AttributeSet): View? = when (name) {
                        "TextView" -> androidx.appcompat.widget.AppCompatTextView(c, attrs)
                        "EditText" -> androidx.appcompat.widget.AppCompatEditText(c, attrs)
                        "ImageView" -> androidx.appcompat.widget.AppCompatImageView(c, attrs)
                        "ImageButton" -> androidx.appcompat.widget.AppCompatImageButton(c, attrs)
                        "Button" -> androidx.appcompat.widget.AppCompatButton(c, attrs)
                        "RadioButton" -> androidx.appcompat.widget.AppCompatRadioButton(c, attrs)
                        "CheckBox" -> androidx.appcompat.widget.AppCompatCheckBox(c, attrs)
                        "Spinner" -> androidx.appcompat.widget.AppCompatSpinner(c, attrs)
                        else -> null
                    }
                }
                val root = inflater.inflate(layout, null) as ViewGroup
                Ui.adaptWidgets(themed, root)
                val width = minOf(size.first, 720)
                root.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(size.second, View.MeasureSpec.EXACTLY))
                root.layout(0, 0, width, size.second)
                assertEquals("$label/$name root width", width, root.measuredWidth)
                val pinned = when (layout) {
                    R.layout.activity_comments -> intArrayOf(R.id.etReply, R.id.btnSend)
                    R.layout.activity_chat -> intArrayOf(R.id.etMessage, R.id.btnCall)
                    R.layout.activity_photo_picker -> intArrayOf(R.id.btnNext)
                    R.layout.activity_camera -> intArrayOf(R.id.btnShutter)
                    R.layout.activity_story_editor -> intArrayOf(R.id.btnShareStory)
                    R.layout.activity_voice_call -> intArrayOf(R.id.btnEndCall)
                    else -> intArrayOf()
                }
                for (id in pinned) {
                    val target = root.findViewById<View>(id)
                    assertNotNull("$label/$name missing pinned control $id", target)
                    val rect = Rect(0, 0, target.width, target.height); root.offsetDescendantRectToMyCoords(target, rect)
                    assertTrue("$label/$name pinned control below viewport: $rect", rect.top >= 0 && rect.bottom <= size.second)
                    assertTrue("$label/$name pinned control outside width: $rect", rect.left >= 0 && rect.right <= width)
                }
                bitmap = Bitmap.createBitmap(size.first, size.second, Bitmap.Config.ARGB_8888)
                Canvas(bitmap!!).apply { drawColor(if (layout in listOf(R.layout.activity_camera, R.layout.activity_story_editor, R.layout.activity_story_viewer, R.layout.activity_your_story)) android.graphics.Color.BLACK else android.graphics.Color.WHITE); translate((size.first - width)/2f, 0f); root.draw(this) }
            }
            bitmap!!.let { image -> File(output, "$label-$name.png").outputStream().use { image.compress(Bitmap.CompressFormat.PNG, 100, it) }; image.recycle() }
        }
    }
}
