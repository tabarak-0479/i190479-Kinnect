package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        Ui.prepare(this)
        findViewById<View>(R.id.btnEditAvatar).setOnClickListener { startActivity(Intent(this, PhotoPickerActivity::class.java)) }
        findViewById<View>(R.id.btnEditCover).setOnClickListener { startActivity(Intent(this, PhotoPickerActivity::class.java)) }
        val prefs = getSharedPreferences("demo", MODE_PRIVATE)
        findViewById<EditText>(R.id.etName).setText(prefs.getString("name", "Jacob West"))
        findViewById<EditText>(R.id.etBio).setText(prefs.getString("bio", "Digital goodies designer."))
        findViewById<View>(R.id.btnSave).setOnClickListener { prefs.edit().putString("name", findViewById<EditText>(R.id.etName).text.toString())
                .putString("bio", findViewById<EditText>(R.id.etBio).text.toString()).apply(); finish() }

    }


}
