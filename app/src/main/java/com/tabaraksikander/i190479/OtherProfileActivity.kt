package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class OtherProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_profile)
        Ui.prepare(this)
        findViewById<View>(R.id.btnSecondary).setOnClickListener { startActivity(Intent(this, ChatActivity::class.java)) }
        Ui.toggle(this, R.id.btnPrimary, "Request sent", "Add friend")

    }


}
