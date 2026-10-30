package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** New text and image messages are stored in order for each selected conversation. */
class ChatActivity : AppCompatActivity() {
    private lateinit var person: String
    private val prefs by lazy { getSharedPreferences("demo",MODE_PRIVATE) }
    override fun onCreate(state:Bundle?) {
        super.onCreate(state);setContentView(R.layout.activity_chat);Ui.prepare(this)
        person=intent.getStringExtra("person") ?: "Aisha Khan"
        for(id in intArrayOf(R.id.chatName,R.id.chatIntroName))findViewById<TextView>(id).text=person
        for(id in intArrayOf(R.id.chatAvatar,R.id.chatIntroAvatar,R.id.chatLastAvatar,R.id.chatTypingAvatar))findViewById<TextView>(id).apply {text=Ui.initials(person);setBackgroundResource(Ui.personColor(person))}
        findViewById<TextView>(R.id.quotedReplyLabel).text="You replied to "+person.substringBefore(" ")
        findViewById<View>(R.id.btnCall).setOnClickListener { startActivity(Intent(this,VoiceCallActivity::class.java).putExtra("person",person)) }
        findViewById<View>(R.id.btnVideoCall).setOnClickListener { startActivity(Intent(this,VoiceCallActivity::class.java).putExtra("person",person).putExtra("video",true)) }
        findViewById<View>(R.id.btnChatInfo).setOnClickListener {Ui.openPerson(this,person)}
        findViewById<View>(R.id.btnCamera).setOnClickListener {startActivity(Intent(this,CameraActivity::class.java))}
        findViewById<View>(R.id.btnPhoto).setOnClickListener {startActivityForResult(Intent(this,PhotoPickerActivity::class.java),401)}
        findViewById<View>(R.id.btnChatPlus).setOnClickListener {Ui.choose(this,"Attach",arrayOf("Photos","Camera")){v->findViewById<View>(if(v=="Photos")R.id.btnPhoto else R.id.btnCamera).performClick()}}
        findViewById<View>(R.id.btnChatMic).setOnClickListener {Ui.choose(this,"Voice message",arrayOf("Record local note","Cancel")){v->if(v!="Cancel") {val input=EditText(this);androidx.appcompat.app.AlertDialog.Builder(this).setTitle("Voice-note caption").setView(input).setPositiveButton("Add note"){_,_->if(input.text.isNotBlank())append("Voice note: "+input.text)}.setNegativeButton("Cancel",null).show()}}}
        findViewById<View>(R.id.btnChatSmile).setOnClickListener {Ui.choose(this,"Emoji",arrayOf("☺","♥","Thanks!")){v->findViewById<EditText>(R.id.etMessage).append(v)}}
        findViewById<View>(R.id.btnChatLike).setOnClickListener {append("♥")}
        findViewById<View>(R.id.btnSend).setOnClickListener {val input=findViewById<EditText>(R.id.etMessage);if(input.text.isNotBlank()){append(input.text.toString());input.text.clear()}}
        findViewById<EditText>(R.id.etMessage).addTextChangedListener(object:android.text.TextWatcher {
            override fun beforeTextChanged(s:CharSequence?,start:Int,count:Int,after:Int){}
            override fun afterTextChanged(s:android.text.Editable?){}
            override fun onTextChanged(s:CharSequence?,start:Int,before:Int,count:Int){findViewById<View>(R.id.btnSend).visibility=if(s.isNullOrBlank())View.GONE else View.VISIBLE;findViewById<View>(R.id.btnChatLike).visibility=if(s.isNullOrBlank())View.VISIBLE else View.GONE}
        })
        render()
    }
    private fun append(value:String) {
        val count=prefs.getInt("messages:$person",0);prefs.edit().putString("message:$person:$count",value).putInt("messages:$person",count+1).putString("last:$person",value).apply();render()
        findViewById<ScrollView>(R.id.chatScroll).post {findViewById<ScrollView>(R.id.chatScroll).fullScroll(View.FOCUS_DOWN)}
    }
    private fun render() {
        val column=findViewById<LinearLayout>(R.id.newMessages);column.removeAllViews()
        for(i in 0 until prefs.getInt("messages:$person",0)) {
            val value=prefs.getString("message:$person:$i","") ?: ""
            val view:View=if(value.startsWith("[photo:"))ImageView(this).apply {setImageResource(DemoPhotos.resource(value.removePrefix("[photo:").removeSuffix("]").toInt()));scaleType=ImageView.ScaleType.CENTER_CROP;contentDescription="Sent image"} else TextView(this).apply {text=value;textSize=16f;setTextColor(android.graphics.Color.WHITE);setBackgroundResource(R.drawable.bubble_out);setPadding(Ui.dp(this@ChatActivity,12),Ui.dp(this@ChatActivity,8),Ui.dp(this@ChatActivity,12),Ui.dp(this@ChatActivity,8))}
            val p=LinearLayout.LayoutParams(if(view is ImageView)Ui.dp(this,200) else LinearLayout.LayoutParams.WRAP_CONTENT,if(view is ImageView)Ui.dp(this,145) else LinearLayout.LayoutParams.WRAP_CONTENT);p.gravity=android.view.Gravity.END;p.setMargins(Ui.dp(this,36),Ui.dp(this,6),Ui.dp(this,12),Ui.dp(this,6));column.addView(view,p)
        }
    }
    @Deprecated("Simple Intent result handling") override fun onActivityResult(request:Int,result:Int,data:Intent?) {super.onActivityResult(request,result,data);if(request==401 && result==RESULT_OK)for(i in data?.getIntArrayExtra("selection") ?: intArrayOf())append("[photo:$i]")}
    override fun onResume(){super.onResume();render()}
}
