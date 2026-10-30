package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class VoiceCallActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_call)
        Ui.prepare(this)
        findViewById<View>(R.id.btnEndCall).setOnClickListener { finish() }
        Ui.toggle(this, R.id.btnMute, "Unmute", "Mute")
        Ui.toggle(this, R.id.btnSpeaker, "Speaker on", "Speaker")
        findViewById<View>(R.id.btnKeypad).setOnClickListener { Ui.info(this, "Keypad", "1  2  3\n4  5  6\n7  8  9\n*  0  #") }

    }


}
