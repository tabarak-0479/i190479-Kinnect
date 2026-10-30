package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** Public profile reads saved name, bio and photo IDs when resumed. Private fields remain in EditProfile; tabs and friend tiles use basic listeners. */
class ProfileActivity : AppCompatActivity() {
    override fun onCreate(state: Bundle?) {
        super.onCreate(state); setContentView(R.layout.activity_profile); Ui.prepare(this)
        findViewById<View>(R.id.btnPrimary).setOnClickListener { startActivity(Intent(this, CameraActivity::class.java)) }
        findViewById<View>(R.id.btnSecondary).setOnClickListener { startActivity(Intent(this, EditProfileActivity::class.java)) }
        findViewById<View>(R.id.btnProfileMore).setOnClickListener { Ui.choose(this,"Profile",arrayOf("Copy profile name", "Edit profile")) { value -> if(value=="Edit profile") findViewById<View>(R.id.btnSecondary).performClick() else { getSystemService(android.content.ClipboardManager::class.java).setPrimaryClip(android.content.ClipData.newPlainText("Profile",findViewById<TextView>(R.id.profileName).text)); Toast.makeText(this,"Profile name copied",Toast.LENGTH_SHORT).show() } } }
        for(id in intArrayOf(R.id.coverCamera,R.id.avatarCamera)) findViewById<View>(id).setOnClickListener { startActivity(Intent(this, EditProfileActivity::class.java)) }
        for((i,id) in intArrayOf(R.id.profileFriend0,R.id.profileFriend1,R.id.profileFriend2).withIndex()) findViewById<View>(id).setOnClickListener { Ui.openPerson(this,listOf("Lina Marsh","Aisha Khan","Omar Farooq")[i]) }
        findViewById<View>(R.id.btnFindFriends).setOnClickListener { startActivity(Intent(this,FriendsActivity::class.java)) }
        for((i,id) in intArrayOf(R.id.profileTab0,R.id.profileTab1,R.id.profileTab2,R.id.profileTab3).withIndex()) findViewById<View>(id).setOnClickListener {
            for(other in intArrayOf(R.id.profileTab0,R.id.profileTab1,R.id.profileTab2,R.id.profileTab3)) findViewById<View>(other).setBackgroundResource(if(other==id) R.drawable.chip_active else android.R.color.transparent)
            findViewById<View>(R.id.profileDetailsSection).visibility=if(i==0) View.VISIBLE else View.GONE
            findViewById<View>(R.id.profileGallerySection).visibility=if(i==1) View.VISIBLE else View.GONE
            findViewById<TextView>(R.id.profileTabEmpty).apply { visibility=if(i>=2) View.VISIBLE else View.GONE; text=if(i==2) "No videos yet" else if(getSharedPreferences("demo",MODE_PRIVATE).getBoolean("joinedGroup",false))"Omar’s Photography Circle · Joined" else "No groups joined" }
        }
    }
    override fun onResume() {
        super.onResume(); val p=getSharedPreferences("demo",MODE_PRIVATE); val name=p.getString("name","Jacob West") ?: "Jacob West"
        findViewById<TextView>(R.id.profileName).text=name;findViewById<TextView>(R.id.profileToolbarName).text=name
        findViewById<TextView>(R.id.profileBio).text=p.getString("bio","Digital goodies designer. Everything is designed.")
        findViewById<TextView>(R.id.profileAvatar).text=Ui.initials(name)
        val avatar=p.getInt("avatar",-1);val cover=p.getInt("cover",-1)
        findViewById<ImageView>(R.id.profileAvatarPhoto).apply { visibility=if(avatar>=0) View.VISIBLE else View.GONE;if(avatar>=0)setImageResource(DemoPhotos.resource(avatar)) }
        findViewById<TextView>(R.id.profileAvatar).visibility=if(avatar>=0) View.INVISIBLE else View.VISIBLE
        findViewById<ImageView>(R.id.profileCover).setImageResource(if(cover>=0) DemoPhotos.resource(cover) else R.drawable.cover_sea)
    }
}
