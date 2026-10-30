package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class NotificationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        Ui.prepare(this)
        findViewById<View>(R.id.notice0).setOnClickListener { startActivity(Intent(this, ReactionActivity::class.java)) }
        findViewById<View>(R.id.notice1).setOnClickListener { startActivity(Intent(this, OtherProfileActivity::class.java)) }
        findViewById<View>(R.id.notice2).setOnClickListener { startActivity(Intent(this, CommentsActivity::class.java)) }
        findViewById<View>(R.id.notice3).setOnClickListener { startActivity(Intent(this, HomeActivity::class.java)) }
        findViewById<View>(R.id.notice4).setOnClickListener { startActivity(Intent(this, HomeActivity::class.java)) }
        findViewById<View>(R.id.notice5).setOnClickListener { startActivity(Intent(this, YourStoryActivity::class.java)) }
        findViewById<View>(R.id.btnConfirmNotice).setOnClickListener { (it as TextView).text = "Confirmed" }
        findViewById<View>(R.id.btnDeleteNotice).setOnClickListener { findViewById<View>(R.id.notice1).visibility = View.GONE }
        findViewById<View>(R.id.btnPrevious).setOnClickListener { Ui.info(this, "Notifications", "You have reached the oldest demonstration notifications.") }

    }


}
