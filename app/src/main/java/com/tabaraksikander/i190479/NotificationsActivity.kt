package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** Grouped notifications support read/hide and real request confirmation. */
class NotificationsActivity : AppCompatActivity() {
    private val ids=intArrayOf(R.id.notice0,R.id.notice1,R.id.notice2,R.id.notice3,R.id.notice4,R.id.notice5)
    override fun onCreate(state:Bundle?){
        super.onCreate(state);setContentView(R.layout.activity_notifications);Ui.prepare(this)
        val prefs=getSharedPreferences("demo",MODE_PRIVATE)
        for((i,id)in ids.withIndex())findViewById<View>(id).setOnClickListener{prefs.edit().putBoolean("noticeRead:$i",true).apply();when(i){0->startActivity(Intent(this,ReactionActivity::class.java));1->Ui.openPerson(this,"Sara Iqbal");2->startActivity(Intent(this,CommentsActivity::class.java));3->startActivity(Intent(this,HomeActivity::class.java));4->Ui.openPerson(this,"Hamza Ali");else->startActivity(Intent(this,YourStoryActivity::class.java))}}
        for((i,id)in intArrayOf(R.id.noticeMore0,R.id.noticeMore1,R.id.noticeMore2,R.id.noticeMore3,R.id.noticeMore4,R.id.noticeMore5).withIndex())findViewById<View>(id).setOnClickListener{Ui.choose(this,"Notification",arrayOf("Mark as read","Hide")){action->prefs.edit().putBoolean(if(action=="Hide")"noticeHidden:$i"else"noticeRead:$i",true).apply();refresh()}}
        findViewById<View>(R.id.btnConfirmNotice).setOnClickListener{prefs.edit().putBoolean("confirmed:Sara Iqbal",true).apply();(it as TextView).text="Confirmed";it.isEnabled=false}
        findViewById<View>(R.id.btnDeleteNotice).setOnClickListener{prefs.edit().putBoolean("noticeHidden:1",true).apply();refresh()}
        findViewById<View>(R.id.btnPrevious).setOnClickListener{val v=findViewById<View>(R.id.olderNotifications);v.visibility=if(v.visibility==View.GONE)View.VISIBLE else View.GONE;(it as TextView).text=if(v.visibility==View.VISIBLE)"Hide previous notifications"else"See previous notifications"}
    }
    private fun refresh(){val p=getSharedPreferences("demo",MODE_PRIVATE);for((i,id)in ids.withIndex()){findViewById<View>(id).visibility=if(p.getBoolean("noticeHidden:$i",false))View.GONE else View.VISIBLE;findViewById<View>(id).setBackgroundColor(android.graphics.Color.parseColor(if(i<2&&!p.getBoolean("noticeRead:$i",false))"#E3EFF0"else"#FFFFFF"))}}
    override fun onResume(){super.onResume();refresh();Ui.refreshBadges(this)}
}
