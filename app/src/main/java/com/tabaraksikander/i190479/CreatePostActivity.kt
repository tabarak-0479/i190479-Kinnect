package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class CreatePostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        Ui.prepare(this)
        findViewById<View>(R.id.btnPhoto).setOnClickListener { startActivity(Intent(this, PhotoPickerActivity::class.java)) }
        findViewById<View>(R.id.btnCamera).setOnClickListener { startActivity(Intent(this, CameraActivity::class.java)) }
        findViewById<View>(R.id.btnPost).setOnClickListener { val value = findViewById<EditText>(R.id.etPost).text.toString()
            if (value.isNotBlank()) {
                getSharedPreferences("demo", MODE_PRIVATE).edit().putString("post", value).apply()
                startActivity(Intent(this, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)); finish()
            } }
        findViewById<View>(R.id.color0).setOnClickListener { findViewById<EditText>(R.id.etPost).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")) }
        findViewById<View>(R.id.color1).setOnClickListener { findViewById<EditText>(R.id.etPost).setBackgroundColor(android.graphics.Color.parseColor("#DDEEEE")) }
        findViewById<View>(R.id.color2).setOnClickListener { findViewById<EditText>(R.id.etPost).setBackgroundColor(android.graphics.Color.parseColor("#F7E8DF")) }
        findViewById<View>(R.id.color3).setOnClickListener { findViewById<EditText>(R.id.etPost).setBackgroundColor(android.graphics.Color.parseColor("#E0DFF0")) }
        findViewById<View>(R.id.color4).setOnClickListener { findViewById<EditText>(R.id.etPost).setBackgroundColor(android.graphics.Color.parseColor("#E2EEE5")) }
        findViewById<View>(R.id.color5).setOnClickListener { findViewById<EditText>(R.id.etPost).setBackgroundColor(android.graphics.Color.parseColor("#FFF0CA")) }
        findViewById<View>(R.id.color6).setOnClickListener { findViewById<EditText>(R.id.etPost).setBackgroundColor(android.graphics.Color.parseColor("#EFDDEB")) }
        findViewById<View>(R.id.color7).setOnClickListener { findViewById<EditText>(R.id.etPost).setBackgroundColor(android.graphics.Color.parseColor("#DDDAD7")) }
        findViewById<View>(R.id.btnTag).setOnClickListener { Ui.info(this, "Tag people", "Aisha Khan, Omar Farooq and Lina Marsh") }
        findViewById<View>(R.id.btnFeeling).setOnClickListener { Ui.info(this, "Feeling/activity", "Feeling happy") }
        findViewById<View>(R.id.btnCheckin).setOnClickListener { Ui.info(this, "Check in", "Karachi, Pakistan") }
        findViewById<View>(R.id.btnLive).setOnClickListener { Ui.info(this, "Live video", "Live streaming is outside the local UI assignment.") }
        findViewById<View>(R.id.btnEvent).setOnClickListener { Ui.info(this, "Create event", "Saturday plans") }

    }


}
