package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class PhotoPickerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_picker)
        Ui.prepare(this)
        findViewById<View>(R.id.btnTakePhoto).setOnClickListener { startActivity(Intent(this, CameraActivity::class.java)) }
        findViewById<View>(R.id.btnVideo).setOnClickListener { startActivity(Intent(this, CameraActivity::class.java)) }
        findViewById<View>(R.id.btnGallery).setOnClickListener { Toast.makeText(this, "Recents", Toast.LENGTH_SHORT).show() }
        findViewById<View>(R.id.btnMultiple).setOnClickListener { Toast.makeText(this, "Tap images to select multiple", Toast.LENGTH_SHORT).show() }
        val selected = mutableSetOf(0, 5)
        val imageIds = intArrayOf(R.id.photo0,R.id.photo1,R.id.photo2,R.id.photo3,R.id.photo4,R.id.photo5,R.id.photo6,R.id.photo7,R.id.photo8,R.id.photo9,R.id.photo10,R.id.photo11,R.id.photo12,R.id.photo13,R.id.photo14,R.id.photo15)
        val marks = intArrayOf(R.id.selected0,R.id.selected1,R.id.selected2,R.id.selected3,R.id.selected4,R.id.selected5,R.id.selected6,R.id.selected7,R.id.selected8,R.id.selected9,R.id.selected10,R.id.selected11,R.id.selected12,R.id.selected13,R.id.selected14,R.id.selected15)
        for (i in imageIds.indices) findViewById<View>(imageIds[i]).setOnClickListener {
            if (!selected.add(i)) selected.remove(i)
            findViewById<TextView>(R.id.btnNext).text = "Next (${selected.size})"
            findViewById<TextView>(marks[i]).text = if (i in selected) "✓" else "○"
        }
        findViewById<View>(R.id.btnNext).setOnClickListener { getSharedPreferences("demo", MODE_PRIVATE).edit().putInt("photos", selected.size).apply(); finish() }

    }


}
