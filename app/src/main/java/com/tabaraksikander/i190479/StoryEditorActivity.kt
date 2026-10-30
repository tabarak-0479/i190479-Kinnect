package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class StoryEditorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_editor)
        Ui.prepare(this)
        findViewById<View>(R.id.btnShareStory).setOnClickListener { getSharedPreferences("demo", MODE_PRIVATE).edit().putBoolean("story", true).apply(); startActivity(Intent(this, YourStoryActivity::class.java)); finish() }
        findViewById<View>(R.id.btnCloseFriends).setOnClickListener { getSharedPreferences("demo", MODE_PRIVATE).edit().putBoolean("story", true).apply(); startActivity(Intent(this, YourStoryActivity::class.java)); finish() }
        findViewById<View>(R.id.tool0).setOnClickListener { Ui.info(this, "Story tools", "Tap the caption to edit your story text") }
        findViewById<View>(R.id.tool1).setOnClickListener { Ui.info(this, "Story tools", "Sticker selected") }
        findViewById<View>(R.id.tool2).setOnClickListener { Ui.info(this, "Story tools", "Music selected") }
        findViewById<View>(R.id.tool3).setOnClickListener { Ui.info(this, "Story tools", "Effect selected") }
        findViewById<View>(R.id.storyCaption).setOnClickListener { val input = EditText(this); input.setText((it as TextView).text)
            androidx.appcompat.app.AlertDialog.Builder(this).setTitle("Story caption").setView(input).setPositiveButton("Save") { _, _ -> findViewById<TextView>(R.id.storyCaption).text = input.text }.setNegativeButton("Cancel", null).show() }

    }


}
