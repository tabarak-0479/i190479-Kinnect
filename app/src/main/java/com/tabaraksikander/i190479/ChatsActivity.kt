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
        val people=listOf("Aisha Khan","Design crew","Lina Marsh","Omar Farooq","Bilal Ahmed","Noor Fatima")
        val rows=intArrayOf(R.id.chatRow0,R.id.chatRow1,R.id.chatRow2,R.id.chatRow3,R.id.chatRow4,R.id.chatRow5)
        findViewById<EditText>(R.id.etChatSearch).addTextChangedListener(object:android.text.TextWatcher {
            override fun beforeTextChanged(s:CharSequence?,start:Int,count:Int,after:Int){}
            override fun afterTextChanged(s:android.text.Editable?){}
            override fun onTextChanged(s:CharSequence?,start:Int,before:Int,count:Int){for(i in rows.indices)findViewById<View>(rows[i]).visibility=if(people[i].contains(s.toString(),true))View.VISIBLE else View.GONE}
        })
        for((i,id)in intArrayOf(R.id.chatNote0,R.id.chatNote1,R.id.chatNote2,R.id.chatNote3,R.id.chatNote4).withIndex())findViewById<View>(id).setOnClickListener {
            if(i==0){val input=EditText(this);input.hint="Share a note";androidx.appcompat.app.AlertDialog.Builder(this).setTitle("Your note").setView(input).setPositiveButton("Save"){_,_->getSharedPreferences("demo",MODE_PRIVATE).edit().putString("note",input.text.toString()).apply();it.contentDescription="Your note: "+input.text}.setNegativeButton("Cancel",null).show()}
            else startActivity(Intent(this,ChatActivity::class.java).putExtra("person",listOf("Aisha Khan","Omar Farooq","Sara Iqbal","Hamza Ali")[i-1]))
        }
        findViewById<View>(R.id.chatRow0).setOnClickListener { startActivity(Intent(this, ChatActivity::class.java).putExtra("person", "Aisha Khan")) }
        findViewById<View>(R.id.chatRow1).setOnClickListener { startActivity(Intent(this, ChatActivity::class.java).putExtra("person", "Design crew")) }
        findViewById<View>(R.id.chatRow2).setOnClickListener { startActivity(Intent(this, ChatActivity::class.java).putExtra("person", "Lina Marsh")) }
        findViewById<View>(R.id.chatRow3).setOnClickListener { startActivity(Intent(this, ChatActivity::class.java).putExtra("person", "Omar Farooq")) }
        findViewById<View>(R.id.chatRow4).setOnClickListener { startActivity(Intent(this, ChatActivity::class.java).putExtra("person", "Bilal Ahmed")) }
        findViewById<View>(R.id.chatRow5).setOnClickListener { startActivity(Intent(this, ChatActivity::class.java).putExtra("person", "Noor Fatima")) }
        findViewById<View>(R.id.btnNewChat).setOnClickListener { startActivity(Intent(this, SearchActivity::class.java)) }
        findViewById<View>(R.id.btnPeopleTab).setOnClickListener { startActivity(Intent(this, FriendsActivity::class.java)) }
        findViewById<View>(R.id.btnStoriesTab).setOnClickListener { startActivity(Intent(this, YourStoryActivity::class.java)) }
        findViewById<View>(R.id.btnChatsTab).setOnClickListener { findViewById<ScrollView>(R.id.chatListScroll).smoothScrollTo(0,0) }

    }


    override fun onResume(){super.onResume();val p=getSharedPreferences("demo",MODE_PRIVATE);val names=listOf("Aisha Khan","Design crew","Lina Marsh","Omar Farooq","Bilal Ahmed","Noor Fatima");val ids=intArrayOf(R.id.chatRowPreview0,R.id.chatRowPreview1,R.id.chatRowPreview2,R.id.chatRowPreview3,R.id.chatRowPreview4,R.id.chatRowPreview5);for(i in ids.indices){val last=p.getString("last:"+names[i],"") ?: "";if(last.isNotEmpty())findViewById<TextView>(ids[i]).text="You: "+if(last.startsWith("[photo:"))"Photo" else last}}
}
