package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** Thread actions and newly posted replies work locally and survive reopening. */
class CommentsActivity : AppCompatActivity() {
    private var replyTo="Aisha Khan"
    private val prefs by lazy {getSharedPreferences("demo",MODE_PRIVATE)}
    override fun onCreate(state:Bundle?) {
        super.onCreate(state);setContentView(R.layout.activity_comments);Ui.prepare(this)
        replyTo=state?.getString("replyTo") ?: "Aisha Khan"
        findViewById<View>(R.id.btnCamera).setOnClickListener{startActivity(Intent(this,CameraActivity::class.java))}
        for((i,id)in intArrayOf(R.id.commentReply0,R.id.commentReply1,R.id.commentReply2,R.id.commentReply3,R.id.commentReply4).withIndex())findViewById<View>(id).setOnClickListener{replyTo=listOf("Aisha Khan","Lina Marsh","Zain Raza","Hamza Ali","Maya Chen")[i];updateLabel();findViewById<EditText>(R.id.etReply).requestFocus()}
        for(id in intArrayOf(R.id.commentLike0,R.id.commentLike1,R.id.commentLike2,R.id.commentLike3,R.id.commentLike4))findViewById<TextView>(id).setOnClickListener{it.isSelected=!it.isSelected;(it as TextView).text=if(it.isSelected)"Liked"else"Like";it.setTextColor(android.graphics.Color.parseColor(if(it.isSelected)"#0B5F63"else"#62655F"))}
        findViewById<View>(R.id.btnCancelReply).setOnClickListener{replyTo="";updateLabel()}
        findViewById<View>(R.id.btnMoreReplies).setOnClickListener{val c=findViewById<LinearLayout>(R.id.moreReplies);c.visibility=if(c.visibility==View.GONE)View.VISIBLE else View.GONE;if(c.childCount==0)for(s in listOf("Omar Farooq: Beautiful light!","Sara Iqbal: Love these colors","Bilal Ahmed: Great shot","Noor Fatima: The old pier is lovely","Daniyal Shah: Let’s go again","Lina Marsh: Absolutely!")){val t=TextView(this);t.text=s;t.textSize=15f;t.setPadding(Ui.dp(this,60),Ui.dp(this,10),Ui.dp(this,16),Ui.dp(this,10));c.addView(t)};(it as TextView).text=if(c.visibility==View.VISIBLE)"Hide replies"else"View 6 more replies"}
        findViewById<View>(R.id.btnReplySmile).setOnClickListener{Ui.choose(this,"Add emoji",arrayOf("☺","♥","Great shot!")){findViewById<EditText>(R.id.etReply).append(it)}}
        findViewById<View>(R.id.btnSend).setOnClickListener{val input=findViewById<EditText>(R.id.etReply);if(input.text.isBlank())return@setOnClickListener;val n=prefs.getInt("comments",0);prefs.edit().putString("comment:$n",(if(replyTo.isEmpty())""else"@$replyTo ")+input.text.toString()).putInt("comments",n+1).apply();input.text.clear();render();findViewById<ScrollView>(R.id.commentsScroll).post{findViewById<ScrollView>(R.id.commentsScroll).fullScroll(View.FOCUS_DOWN)}}
        findViewById<View>(R.id.btnAction).setOnClickListener{Ui.choose(this,"Comment order",arrayOf("Most relevant","Newest","Oldest")){value->findViewById<TextView>(R.id.btnAction).text=value;val c=findViewById<LinearLayout>(R.id.newComments);val p=c.parent as LinearLayout;p.removeView(c);p.addView(c,if(value=="Newest")2 else p.childCount);render(value=="Newest")}}
        updateLabel();render()
    }
    private fun updateLabel(){findViewById<TextView>(R.id.replyingTo).text=if(replyTo.isEmpty())"Write a comment"else"Replying to $replyTo";findViewById<EditText>(R.id.etReply).hint=if(replyTo.isEmpty())"Write a comment..."else"Write a reply..."}
    private fun render(newest:Boolean=false){val c=findViewById<LinearLayout>(R.id.newComments);c.removeAllViews();val indices=if(newest)(0 until prefs.getInt("comments",0)).reversed() else(0 until prefs.getInt("comments",0)).toList();for(i in indices){val t=TextView(this);t.text="Jacob West\n"+prefs.getString("comment:$i","");t.textSize=16f;t.setTextColor(android.graphics.Color.parseColor("#252722"));t.setPadding(Ui.dp(this,16),Ui.dp(this,12),Ui.dp(this,16),Ui.dp(this,12));c.addView(t)}}
    override fun onSaveInstanceState(out:Bundle){out.putString("replyTo",replyTo);super.onSaveInstanceState(out)}
}
