package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class ChatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)
        Ui.prepare(this)
        findViewById<View>(R.id.chatRow0).setOnClickListener { startActivity(Intent(this, ChatActivity::class.java)) }
        findViewById<View>(R.id.chatRow1).setOnClickListener { startActivity(Intent(this, ChatActivity::class.java)) }
        findViewById<View>(R.id.chatRow2).setOnClickListener { startActivity(Intent(this, ChatActivity::class.java)) }
        findViewById<View>(R.id.chatRow3).setOnClickListener { startActivity(Intent(this, ChatActivity::class.java)) }
        findViewById<View>(R.id.chatRow4).setOnClickListener { startActivity(Intent(this, ChatActivity::class.java)) }
        findViewById<View>(R.id.chatRow5).setOnClickListener { startActivity(Intent(this, ChatActivity::class.java)) }
        findViewById<View>(R.id.btnNewChat).setOnClickListener { startActivity(Intent(this, SearchActivity::class.java)) }
        findViewById<View>(R.id.btnPeopleTab).setOnClickListener { startActivity(Intent(this, FriendsActivity::class.java)) }
        findViewById<View>(R.id.btnStoriesTab).setOnClickListener { startActivity(Intent(this, YourStoryActivity::class.java)) }
        findViewById<View>(R.id.btnChatsTab).setOnClickListener { Toast.makeText(this, "Chats", Toast.LENGTH_SHORT).show() }

    }


}
