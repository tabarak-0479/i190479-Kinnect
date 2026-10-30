package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Ui.prepare(this)
        findViewById<View>(R.id.btnChats).setOnClickListener { startActivity(Intent(this, ChatsActivity::class.java)) }
        findViewById<View>(R.id.btnCreatePost).setOnClickListener { startActivity(Intent(this, CreatePostActivity::class.java)) }
        findViewById<View>(R.id.btnPhoto).setOnClickListener { startActivity(Intent(this, PhotoPickerActivity::class.java)) }
        findViewById<View>(R.id.btnCreateStory).setOnClickListener { startActivity(Intent(this, CameraActivity::class.java)) }
        findViewById<View>(R.id.btnStory).setOnClickListener { startActivity(Intent(this, StoryViewerActivity::class.java)) }
        findViewById<View>(R.id.btnStorySara).setOnClickListener { startActivity(Intent(this, StoryViewerActivity::class.java)) }
        findViewById<View>(R.id.btnStoryHamza).setOnClickListener { startActivity(Intent(this, StoryViewerActivity::class.java)) }
        findViewById<View>(R.id.btnLike).setOnClickListener { startActivity(Intent(this, ReactionActivity::class.java)) }
        findViewById<View>(R.id.btnComments).setOnClickListener { startActivity(Intent(this, CommentsActivity::class.java)) }
        findViewById<View>(R.id.btnOwnProfile).setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
        findViewById<View>(R.id.btnShare).setOnClickListener {
            val clipboard = getSystemService(android.content.ClipboardManager::class.java)
            clipboard.setPrimaryClip(android.content.ClipData.newPlainText("Kinnect post", "Sunset walk with the crew. Worth every grain of sand in my shoes."))
            Toast.makeText(this, "Post text copied", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onResume() {
        super.onResume()
        val p = getSharedPreferences("demo", MODE_PRIVATE)
        val published = p.getString("post", "") ?: ""
        val postView = findViewById<TextView>(R.id.publishedPost)
        postView.text = "Jacob West · Just now\n\n" + published
        postView.visibility = if (published.isBlank()) View.GONE else View.VISIBLE
        findViewById<View>(R.id.btnLike).contentDescription = p.getString("reaction", "Like")
    }
}
