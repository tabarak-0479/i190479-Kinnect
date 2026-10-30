package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        Ui.prepare(this)
        findViewById<View>(R.id.btnCall).setOnClickListener { startActivity(Intent(this, VoiceCallActivity::class.java)) }
        findViewById<View>(R.id.btnVideoCall).setOnClickListener { startActivity(Intent(this, VoiceCallActivity::class.java)) }
        findViewById<View>(R.id.btnCamera).setOnClickListener { startActivity(Intent(this, CameraActivity::class.java)) }
        findViewById<View>(R.id.btnPhoto).setOnClickListener { startActivity(Intent(this, PhotoPickerActivity::class.java)) }
        findViewById<View>(R.id.btnSend).setOnClickListener { val input = findViewById<EditText>(R.id.etMessage)
            if (input.text.isNotBlank()) {
                val t = TextView(this); t.text = input.text.toString(); t.textSize = 15f; t.setTextColor(android.graphics.Color.WHITE); t.setBackgroundResource(R.drawable.bubble_out); t.setPadding(24, 18, 24, 18)
                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); params.gravity = android.view.Gravity.END; params.setMargins(36, 12, 24, 12)
                findViewById<LinearLayout>(R.id.newMessages).addView(t, params); input.text.clear()
                findViewById<ScrollView>(R.id.chatScroll).post { findViewById<ScrollView>(R.id.chatScroll).fullScroll(View.FOCUS_DOWN) }
            } }

    }


}
