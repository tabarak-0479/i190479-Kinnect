package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class YourStoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_story)
        Ui.prepare(this)
        findViewById<View>(R.id.btnCreate).setOnClickListener { startActivity(Intent(this, CameraActivity::class.java)) }
        findViewById<View>(R.id.btnSend).setOnClickListener { startActivity(Intent(this, ChatsActivity::class.java)) }
        findViewById<View>(R.id.btnSeen).setOnClickListener { Ui.info(this, "Seen by 24", "Aisha Khan, Lina Marsh, Omar Farooq and 21 others") }
        findViewById<View>(R.id.btnHighlight).setOnClickListener { (it as TextView).text = "Highlighted" }
        findViewById<View>(R.id.btnOptions).setOnClickListener { Ui.info(this, "Your story", "Shared with close friends") }

    }


}
