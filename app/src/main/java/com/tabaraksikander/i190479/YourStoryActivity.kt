package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** Read the last shared story and show its caption, image and audience. Highlight and Send update local state rather than contacting a service. */
class YourStoryActivity : AppCompatActivity() {
    override fun onCreate(state:Bundle?) {
        super.onCreate(state);setContentView(R.layout.activity_your_story);Ui.prepare(this)
        val p=getSharedPreferences("demo",MODE_PRIVATE)
        findViewById<TextView>(R.id.storyCaption).text=p.getString("storyCaption","Saturday plans")
        findViewById<ImageView>(R.id.storyImage).setImageResource(DemoPhotos.resource(p.getInt("storyImage",4)))
        findViewById<TextView>(R.id.storyAge).text="11h · "+p.getString("storyAudience","Close friends")
        for((id,key)in listOf(R.id.storySticker to "storySticker",R.id.storyMusic to "storyMusic")) findViewById<TextView>(id).apply{text=p.getString(key,"");visibility=if(text.isBlank())View.GONE else View.VISIBLE}
        findViewById<View>(R.id.btnCreate).setOnClickListener{startActivity(Intent(this,CameraActivity::class.java))}
        findViewById<View>(R.id.btnSend).setOnClickListener{Ui.choose(this,"Send story",arrayOf("Aisha Khan","Omar Farooq","Lina Marsh")){name->val n=p.getInt("messages:$name",0);p.edit().putString("message:$name:$n","Shared a story: "+findViewById<TextView>(R.id.storyCaption).text).putInt("messages:$name",n+1).apply();Toast.makeText(this,"Shared with $name",Toast.LENGTH_SHORT).show()}}
        findViewById<View>(R.id.btnSeen).setOnClickListener{Ui.info(this,"Seen by 24","Aisha Khan, Lina Marsh, Omar Farooq and 21 others")}
        findViewById<View>(R.id.btnHighlight).setOnClickListener{val selected=!p.getBoolean("highlight",false);p.edit().putBoolean("highlight",selected).apply();it.isSelected=selected;it.contentDescription=if(selected)"Remove highlight" else "Add highlight"}
        findViewById<View>(R.id.btnOptions).setOnClickListener{Ui.choose(this,"Your story",arrayOf("Edit story","Change audience")){v->if(v=="Edit story")finish()else Ui.choose(this,"Audience",arrayOf("Your story","Close friends")){audience->p.edit().putString("storyAudience",audience).apply();findViewById<TextView>(R.id.storyAge).text="11h · $audience"}}}
    }
}
