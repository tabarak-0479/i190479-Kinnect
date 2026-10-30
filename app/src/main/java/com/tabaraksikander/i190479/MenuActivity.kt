package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        Ui.prepare(this)
        findViewById<View>(R.id.btnProfile).setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
        findViewById<View>(R.id.menuFriends).setOnClickListener { startActivity(Intent(this, FriendsActivity::class.java)) }
        findViewById<View>(R.id.menuMarketplace).setOnClickListener { startActivity(Intent(this, MarketplaceActivity::class.java)) }
        findViewById<View>(R.id.menuMemories).setOnClickListener { Ui.info(this, "Memories", "No memories to display in this local UI demonstration.") }
        findViewById<View>(R.id.menuSaved).setOnClickListener { Ui.info(this, "Saved", "No saved to display in this local UI demonstration.") }
        findViewById<View>(R.id.menuGroups).setOnClickListener { Ui.info(this, "Groups", "No groups to display in this local UI demonstration.") }
        findViewById<View>(R.id.menuEvents).setOnClickListener { Ui.info(this, "Events", "No events to display in this local UI demonstration.") }
        findViewById<View>(R.id.btnSeeMore).setOnClickListener { Ui.info(this, "All shortcuts", "All available assignment shortcuts are displayed above.") }
        findViewById<View>(R.id.expand0).setOnClickListener { val v = findViewById<TextView>(R.id.expanded0); v.text = "Help with your Kinnect account"; v.visibility = if (v.visibility == View.GONE) View.VISIBLE else View.GONE }
        findViewById<View>(R.id.expand1).setOnClickListener { val v = findViewById<TextView>(R.id.expanded1); v.text = "Local profile and notification preferences"; v.visibility = if (v.visibility == View.GONE) View.VISIBLE else View.GONE }
        findViewById<View>(R.id.expand2).setOnClickListener { val v = findViewById<TextView>(R.id.expanded2); v.text = "Kinnect · from SMD"; v.visibility = if (v.visibility == View.GONE) View.VISIBLE else View.GONE }
        findViewById<View>(R.id.btnLogout).setOnClickListener { startActivity(Intent(this, LoginActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }); finish() }

    }


}
