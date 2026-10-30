package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** All reference shortcuts and expandable rows have meaningful local destinations/state. */
class MenuActivity : AppCompatActivity() {
    override fun onCreate(state:Bundle?){
        super.onCreate(state);setContentView(R.layout.activity_menu);Ui.prepare(this)
        val prefs=getSharedPreferences("demo",MODE_PRIVATE)
        findViewById<TextView>(R.id.menuProfileName).text=prefs.getString("name","Jacob West")
        for((id,target)in listOf(R.id.btnProfile to ProfileActivity::class.java,R.id.menuFriends to FriendsActivity::class.java,R.id.menuMarketplace to MarketplaceActivity::class.java,R.id.menuChats to ChatsActivity::class.java,R.id.menuNotifications to NotificationsActivity::class.java,R.id.menuMemories to StoryViewerActivity::class.java))findViewById<View>(id).setOnClickListener{startActivity(Intent(this,target))}
        findViewById<View>(R.id.menuSaved).setOnClickListener{if(prefs.getBoolean("savedPost",false))Ui.choose(this,"Saved posts",arrayOf("Lina Marsh · Clifton Beach")){startActivity(Intent(this,ReactionActivity::class.java))}else Ui.info(this,"Saved","You haven’t saved a post. Use the feed post’s menu to save it.")}
        findViewById<View>(R.id.menuGroups).setOnClickListener{Ui.choose(this,"Omar’s Photography Circle",arrayOf(if(prefs.getBoolean("joinedGroup",false))"Leave group"else"Join group")){value->prefs.edit().putBoolean("joinedGroup",value=="Join group").apply();Toast.makeText(this,if(value=="Join group")"Group joined"else"Group left",Toast.LENGTH_SHORT).show()}}
        findViewById<View>(R.id.menuEvents).setOnClickListener{val input=EditText(this);input.setText(prefs.getString("event",""));input.hint="Event title and date";androidx.appcompat.app.AlertDialog.Builder(this).setTitle("Your event").setView(input).setPositiveButton("Save"){_,_->prefs.edit().putString("event",input.text.toString()).apply()}.setNegativeButton("Cancel",null).show()}
        findViewById<View>(R.id.btnSeeMore).setOnClickListener{val v=findViewById<View>(R.id.moreShortcuts);v.visibility=if(v.visibility==View.GONE)View.VISIBLE else View.GONE;(it as TextView).text=if(v.visibility==View.VISIBLE)"See less"else"See more"}
        for((i,id)in intArrayOf(R.id.expand0,R.id.expand1,R.id.expand2).withIndex())findViewById<View>(id).setOnClickListener{val v=findViewById<View>(intArrayOf(R.id.expanded0,R.id.expanded1,R.id.expanded2)[i]);v.visibility=if(v.visibility==View.GONE)View.VISIBLE else View.GONE;it.contentDescription=(if(v.visibility==View.VISIBLE)"Collapse "else"Expand ")+listOf("Help and support","Settings and privacy","Also from SMD")[i]}
        findViewById<View>(R.id.helpNavigation).setOnClickListener{Ui.choose(this,"Open a section",arrayOf("Home","Friends","Marketplace","Notifications","Profile")){value->startActivity(Intent(this,when(value){"Friends"->FriendsActivity::class.java;"Marketplace"->MarketplaceActivity::class.java;"Notifications"->NotificationsActivity::class.java;"Profile"->ProfileActivity::class.java;else->HomeActivity::class.java}))}}
        findViewById<View>(R.id.helpAbout).setOnClickListener{Ui.info(this,"Kinnect","CS4039 Software for Mobile Devices\nKotlin + XML Views\nTabarak Sikander · i190479")}
        findViewById<Switch>(R.id.switchBadges).apply{isChecked=prefs.getBoolean("badges",true);setOnCheckedChangeListener{_,checked->prefs.edit().putBoolean("badges",checked).apply();Ui.refreshBadges(this@MenuActivity)}}
        findViewById<Switch>(R.id.switchLargeText).apply{isChecked=prefs.getBoolean("largeText",false);setOnCheckedChangeListener{_,checked->if(prefs.getBoolean("largeText",false)!=checked){prefs.edit().putBoolean("largeText",checked).apply();recreate()}}}
        findViewById<View>(R.id.btnDisplay).setOnClickListener{Ui.choose(this,"Display",arrayOf("Standard text","Larger text")){value->findViewById<Switch>(R.id.switchLargeText).isChecked=value=="Larger text"}}
        findViewById<View>(R.id.btnLogout).setOnClickListener{startActivity(Intent(this,LoginActivity::class.java).apply{flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK});finish()}
    }
    override fun onResume(){super.onResume();Ui.refreshBadges(this)}
}
