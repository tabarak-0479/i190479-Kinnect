package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class FriendsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        Ui.prepare(this)
        findViewById<View>(R.id.friendProfile0).setOnClickListener { startActivity(Intent(this, OtherProfileActivity::class.java)) }
        findViewById<View>(R.id.confirm0).setOnClickListener { (it as TextView).text = "Confirmed"; it.isEnabled = false }
        findViewById<View>(R.id.delete0).setOnClickListener { findViewById<View>(R.id.request0).visibility = View.GONE }
        findViewById<View>(R.id.friendProfile1).setOnClickListener { startActivity(Intent(this, OtherProfileActivity::class.java)) }
        findViewById<View>(R.id.confirm1).setOnClickListener { (it as TextView).text = "Confirmed"; it.isEnabled = false }
        findViewById<View>(R.id.delete1).setOnClickListener { findViewById<View>(R.id.request1).visibility = View.GONE }
        findViewById<View>(R.id.friendProfile2).setOnClickListener { startActivity(Intent(this, OtherProfileActivity::class.java)) }
        findViewById<View>(R.id.confirm2).setOnClickListener { (it as TextView).text = "Confirmed"; it.isEnabled = false }
        findViewById<View>(R.id.delete2).setOnClickListener { findViewById<View>(R.id.request2).visibility = View.GONE }
        findViewById<View>(R.id.friendProfile3).setOnClickListener { startActivity(Intent(this, OtherProfileActivity::class.java)) }
        findViewById<View>(R.id.confirm3).setOnClickListener { (it as TextView).text = "Confirmed"; it.isEnabled = false }
        findViewById<View>(R.id.delete3).setOnClickListener { findViewById<View>(R.id.request3).visibility = View.GONE }
        findViewById<View>(R.id.btnYourFriends).setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
        findViewById<View>(R.id.btnSuggestions).setOnClickListener { Ui.info(this, "Suggestions", "Friend requests and people you may know are shown below.") }

    }


}
