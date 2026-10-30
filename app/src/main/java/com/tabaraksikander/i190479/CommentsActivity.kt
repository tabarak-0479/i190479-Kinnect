package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class CommentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        Ui.prepare(this)
        findViewById<View>(R.id.btnCamera).setOnClickListener { startActivity(Intent(this, CameraActivity::class.java)) }
        findViewById<View>(R.id.btnSend).setOnClickListener { val input = findViewById<EditText>(R.id.etReply)
            if (input.text.isNotBlank()) {
                val t = TextView(this); t.text = "Jacob West\n" + input.text; t.textSize = 15f; t.setPadding(24, 18, 24, 18)
                findViewById<LinearLayout>(R.id.newComments).addView(t); input.text.clear()
            } }
        findViewById<View>(R.id.btnAction).setOnClickListener { Ui.info(this, "Comment order", "Most relevant comments are shown first.") }

    }


}
