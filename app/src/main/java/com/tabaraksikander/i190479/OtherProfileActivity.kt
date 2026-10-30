package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** Render the person selected in Search/Friends instead of a fixed profile. Pass the same name into Chat to preserve conversation identity. */
class OtherProfileActivity : AppCompatActivity() {
    override fun onCreate(state: Bundle?) {
        super.onCreate(state);setContentView(R.layout.activity_other_profile);Ui.prepare(this)
        val name=intent.getStringExtra("person") ?: "Omar Farooq"
        findViewById<TextView>(R.id.profileToolbarName).text=name;findViewById<TextView>(R.id.profileName).text=name
        findViewById<TextView>(R.id.profileAvatar).apply {text=Ui.initials(name);setBackgroundResource(Ui.personColor(name));contentDescription=name+" profile photo"}
        if(name!="Omar Farooq") { findViewById<TextView>(R.id.profileBio).text="Sharing moments from Karachi."; findViewById<TextView>(R.id.otherFriendCount).text="38 mutual friends" }
        val prefs=getSharedPreferences("demo",MODE_PRIVATE)
        val action=findViewById<TextView>(R.id.btnPrimary)
        fun update(){action.text=if(prefs.getBoolean("request:$name",false)) "Request sent" else "Add friend"}
        update();action.setOnClickListener { prefs.edit().putBoolean("request:$name",!prefs.getBoolean("request:$name",false)).apply();update() }
        findViewById<View>(R.id.btnSecondary).setOnClickListener {startActivity(Intent(this,ChatActivity::class.java).putExtra("person",name))}
        findViewById<View>(R.id.btnProfileMore).setOnClickListener {Ui.choose(this,name,arrayOf("View photos","Message")){value->if(value=="Message")findViewById<View>(R.id.btnSecondary).performClick() else findViewById<ScrollView>(R.id.otherProfileScroll).smoothScrollTo(0,findViewById<View>(R.id.otherPhotos).top)}}
        findViewById<View>(R.id.btnSeePhotos).setOnClickListener {startActivity(Intent(this,PhotoPickerActivity::class.java))}
    }
}
