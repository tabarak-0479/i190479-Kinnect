package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** Local friend-request rows map to distinct people. Confirm/delete state is stored and See all expands the remaining requests. */
class FriendsActivity : AppCompatActivity() {
    private val names=listOf("Sara Iqbal","Bilal Ahmed","Noor Fatima","Daniyal Shah","Omar Siddiqui","Omar Tariq","Maya Chen","Zain Raza","Hamza Ali","Amina Noor","Usman Ali","Sana Nadeem")
    private val rows=intArrayOf(R.id.request0,R.id.request1,R.id.request2,R.id.request3,R.id.request4,R.id.request5,R.id.request6,R.id.request7,R.id.request8,R.id.request9,R.id.request10,R.id.request11)
    private var all=false
    override fun onCreate(state:Bundle?){
        super.onCreate(state);setContentView(R.layout.activity_friends);Ui.prepare(this);all=state?.getBoolean("all") ?: false
        val prefs=getSharedPreferences("demo",MODE_PRIVATE)
        val profiles=intArrayOf(R.id.friendProfile0,R.id.friendProfile1,R.id.friendProfile2,R.id.friendProfile3,R.id.friendProfile4,R.id.friendProfile5,R.id.friendProfile6,R.id.friendProfile7,R.id.friendProfile8,R.id.friendProfile9,R.id.friendProfile10,R.id.friendProfile11);val labels=intArrayOf(R.id.friendName0,R.id.friendName1,R.id.friendName2,R.id.friendName3,R.id.friendName4,R.id.friendName5,R.id.friendName6,R.id.friendName7,R.id.friendName8,R.id.friendName9,R.id.friendName10,R.id.friendName11)
        val confirms=intArrayOf(R.id.confirm0,R.id.confirm1,R.id.confirm2,R.id.confirm3,R.id.confirm4,R.id.confirm5,R.id.confirm6,R.id.confirm7,R.id.confirm8,R.id.confirm9,R.id.confirm10,R.id.confirm11);val deletes=intArrayOf(R.id.delete0,R.id.delete1,R.id.delete2,R.id.delete3,R.id.delete4,R.id.delete5,R.id.delete6,R.id.delete7,R.id.delete8,R.id.delete9,R.id.delete10,R.id.delete11)
        for(i in names.indices){for(id in intArrayOf(profiles[i],labels[i]))findViewById<View>(id).setOnClickListener{Ui.openPerson(this,names[i])}
            findViewById<View>(confirms[i]).setOnClickListener{prefs.edit().putBoolean("confirmed:"+names[i],true).apply();(it as TextView).text="Confirmed";it.isEnabled=false;refresh()}
            if(prefs.getBoolean("confirmed:"+names[i],false)){findViewById<TextView>(confirms[i]).text="Confirmed";findViewById<View>(confirms[i]).isEnabled=false}
            findViewById<View>(deletes[i]).setOnClickListener{prefs.edit().putBoolean("deleted:"+names[i],true).apply();refresh()}
        }
        findViewById<View>(R.id.btnSeeAllRequests).setOnClickListener{all=!all;refresh()}
        findViewById<View>(R.id.btnSuggestions).setOnClickListener{findViewById<View>(R.id.requestSection).visibility=View.VISIBLE;findViewById<View>(R.id.yourFriendsList).visibility=View.GONE}
        findViewById<View>(R.id.btnYourFriends).setOnClickListener{val c=findViewById<LinearLayout>(R.id.yourFriendsList);c.removeAllViews();findViewById<View>(R.id.requestSection).visibility=View.GONE;c.visibility=View.VISIBLE;val friends=(listOf("Aisha Khan","Omar Farooq","Lina Marsh")+names.filter{prefs.getBoolean("confirmed:$it",false)}).distinct();for(name in friends){val t=TextView(this);t.text=name;t.textSize=18f;t.setPadding(Ui.dp(this,16),Ui.dp(this,18),Ui.dp(this,16),Ui.dp(this,18));t.contentDescription="View $name profile";t.setOnClickListener{Ui.openPerson(this,name)};c.addView(t)}}
        findViewById<View>(R.id.btnSuggestedAdd).setOnClickListener{val v=it as TextView;val sent=!prefs.getBoolean("request:Omar Farooq",false);prefs.edit().putBoolean("request:Omar Farooq",sent).apply();v.text=if(sent)"Request sent"else"Add friend"}
        refresh()
    }
    private fun refresh(){val p=getSharedPreferences("demo",MODE_PRIVATE);for(i in rows.indices)findViewById<View>(rows[i]).visibility=if((all||i<4)&&!p.getBoolean("deleted:"+names[i],false))View.VISIBLE else View.GONE;findViewById<TextView>(R.id.requestCount).text="Friend requests "+names.count{!p.getBoolean("deleted:$it",false)&&!p.getBoolean("confirmed:$it",false)};findViewById<TextView>(R.id.btnSeeAllRequests).text=if(all)"See less"else"See all"}
    override fun onSaveInstanceState(out:Bundle){out.putBoolean("all",all);super.onSaveInstanceState(out)}
}
