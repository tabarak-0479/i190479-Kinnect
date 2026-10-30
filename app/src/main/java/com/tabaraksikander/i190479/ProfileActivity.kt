package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        Ui.prepare(this)
        findViewById<View>(R.id.btnPrimary).setOnClickListener { startActivity(Intent(this, CameraActivity::class.java)) }
        findViewById<View>(R.id.btnSecondary).setOnClickListener { startActivity(Intent(this, EditProfileActivity::class.java)) }

    }

    override fun onResume() {
        super.onResume()
        val p = getSharedPreferences("demo", MODE_PRIVATE)
        findViewById<TextView>(R.id.profileName).text = p.getString("name", "Jacob West")
        findViewById<TextView>(R.id.profileBio).text = p.getString("bio", "Digital goodies designer. Everything is designed.")
    }
}
