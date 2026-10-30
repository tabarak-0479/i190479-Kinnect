package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class CameraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        Ui.prepare(this)
        findViewById<View>(R.id.btnGallery).setOnClickListener { startActivity(Intent(this, PhotoPickerActivity::class.java)) }
        findViewById<View>(R.id.btnShutter).setOnClickListener { startActivity(Intent(this, StoryEditorActivity::class.java)) }
        Ui.toggle(this, R.id.btnFlash, "Flash on", "ϟ")
        findViewById<View>(R.id.btnFlip).setOnClickListener { Toast.makeText(this, "Camera preview flipped", Toast.LENGTH_SHORT).show() }
        findViewById<View>(R.id.btnEffects).setOnClickListener { Ui.info(this, "Effects", "Natural landscape effect selected") }

    }


}
