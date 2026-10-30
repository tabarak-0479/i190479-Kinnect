package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class StoryViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_viewer)
        Ui.prepare(this)
        findViewById<View>(R.id.btnStoryLike).setOnClickListener { Toast.makeText(this, "Story liked", Toast.LENGTH_SHORT).show() }
        findViewById<View>(R.id.btnStorySmile).setOnClickListener { Toast.makeText(this, "Reaction sent", Toast.LENGTH_SHORT).show() }
        findViewById<View>(R.id.btnOptions).setOnClickListener { Ui.info(this, "Story", "Omar Farooq · 5 hours ago") }

    }


}
