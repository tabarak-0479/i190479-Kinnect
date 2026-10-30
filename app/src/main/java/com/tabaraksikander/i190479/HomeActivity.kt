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
        findViewById<View>(R.id.btnHidePost).setOnClickListener{findViewById<View>(R.id.feedPost).visibility=View.GONE;findViewById<View>(R.id.btnUndoHide).visibility=View.VISIBLE}
        findViewById<View>(R.id.btnUndoHide).setOnClickListener{findViewById<View>(R.id.feedPost).visibility=View.VISIBLE;it.visibility=View.GONE}
        findViewById<View>(R.id.btnPostMore).setOnClickListener{val prefs=getSharedPreferences("demo",MODE_PRIVATE);Ui.choose(this,"Lina’s post",arrayOf(if(prefs.getBoolean("savedPost",false))"Unsave post"else"Save post","Share post","Hide post")){value->when(value){"Share post"->findViewById<View>(R.id.btnShare).performClick();"Hide post"->findViewById<View>(R.id.btnHidePost).performClick();else->{prefs.edit().putBoolean("savedPost",!prefs.getBoolean("savedPost",false)).apply();Toast.makeText(this,if(value=="Save post")"Post saved"else"Post unsaved",Toast.LENGTH_SHORT).show()}}}}
        findViewById<View>(R.id.btnChats).setOnClickListener { startActivity(Intent(this, ChatsActivity::class.java)) }
        findViewById<View>(R.id.btnCreatePost).setOnClickListener { startActivity(Intent(this, CreatePostActivity::class.java)) }
        findViewById<View>(R.id.btnPhoto).setOnClickListener { startActivity(Intent(this, CreatePostActivity::class.java).putExtra("openPicker", true)) }
        findViewById<View>(R.id.btnCreateStory).setOnClickListener { startActivity(Intent(this, CameraActivity::class.java)) }
        findViewById<View>(R.id.btnStory).setOnClickListener { startActivity(Intent(this, StoryViewerActivity::class.java)) }
        findViewById<View>(R.id.btnStorySara).setOnClickListener { startActivity(Intent(this, StoryViewerActivity::class.java).putExtra("person","Sara Iqbal")) }
        findViewById<View>(R.id.btnStoryHamza).setOnClickListener { startActivity(Intent(this, StoryViewerActivity::class.java).putExtra("person","Hamza Ali")) }
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
        Ui.refreshBadges(this)
        val p = getSharedPreferences("demo", MODE_PRIVATE)
        findViewById<TextView>(R.id.homeInitials).text=Ui.initials(p.getString("name","Jacob West") ?: "Jacob West")
        val avatar=p.getInt("avatar",-1)
        findViewById<ImageView>(R.id.homeAvatarPhoto).apply{visibility=if(avatar>=0)View.VISIBLE else View.GONE;if(avatar>=0)setImageResource(DemoPhotos.resource(avatar))}
        val published = p.getString("post", "") ?: ""
        val postView = findViewById<TextView>(R.id.publishedPost)
        postView.text = (p.getString("name","Jacob West") ?: "Jacob West")+" · Just now\n\n"+published
        postView.visibility = if (published.isBlank()) View.GONE else View.VISIBLE
        val reaction=p.getString("reaction", "Like") ?: "Like"
        findViewById<View>(R.id.btnLike).contentDescription=reaction
        findViewById<TextView>(R.id.btnLikeLabel).text=reaction
        findViewById<TextView>(R.id.btnLikeLabel).setTextColor(android.graphics.Color.parseColor(if(p.contains("reaction"))"#0B5F63"else"#62655F"))
        findViewById<ImageView>(R.id.btnLikeIcon).setImageResource(when(reaction){"Love"->R.drawable.reaction_love_icon;"Haha"->R.drawable.reaction_haha;"Wow"->R.drawable.reaction_wow;"Sad"->R.drawable.reaction_sad;"Angry"->R.drawable.reaction_angry;else->R.drawable.ic_like})
        val attachments=findViewById<LinearLayout>(R.id.publishedPhotos);attachments.removeAllViews()
        if(published.isNotBlank())for(value in (p.getString("postImages", "") ?: "").split(",").filter{it.isNotBlank()}) { val image=ImageView(this);image.setImageResource(DemoPhotos.resource(value.toInt()));image.scaleType=ImageView.ScaleType.CENTER_CROP;image.contentDescription="Published photo";attachments.addView(image,LinearLayout.LayoutParams(Ui.dp(this,150),Ui.dp(this,120))) }
    }
}
