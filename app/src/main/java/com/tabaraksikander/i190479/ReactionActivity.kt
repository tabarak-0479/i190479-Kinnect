package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class ReactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reaction)
        Ui.prepare(this)
        findViewById<View>(R.id.btnShare).setOnClickListener {
            val clipboard=getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
            clipboard.setPrimaryClip(android.content.ClipData.newPlainText("Kinnect post", "Lina Marsh: Sunset walk with the crew. Worth every grain of sand in my shoes."))
            Toast.makeText(this, "Post text copied", Toast.LENGTH_SHORT).show()
        }
        findViewById<View>(R.id.reactionScrim).setOnClickListener {finish()}
        findViewById<View>(R.id.btnComments).setOnClickListener { startActivity(Intent(this, CommentsActivity::class.java)) }
        findViewById<View>(R.id.reaction0).setOnClickListener { getSharedPreferences("demo", MODE_PRIVATE).edit().putString("reaction", "Like").apply(); finish() }
        findViewById<View>(R.id.reaction1).setOnClickListener { getSharedPreferences("demo", MODE_PRIVATE).edit().putString("reaction", "Love").apply(); finish() }
        findViewById<View>(R.id.reaction2).setOnClickListener { getSharedPreferences("demo", MODE_PRIVATE).edit().putString("reaction", "Haha").apply(); finish() }
        findViewById<View>(R.id.reaction3).setOnClickListener { getSharedPreferences("demo", MODE_PRIVATE).edit().putString("reaction", "Wow").apply(); finish() }
        findViewById<View>(R.id.reaction4).setOnClickListener { getSharedPreferences("demo", MODE_PRIVATE).edit().putString("reaction", "Sad").apply(); finish() }
        findViewById<View>(R.id.reaction5).setOnClickListener { getSharedPreferences("demo", MODE_PRIVATE).edit().putString("reaction", "Angry").apply(); finish() }

    }


}
