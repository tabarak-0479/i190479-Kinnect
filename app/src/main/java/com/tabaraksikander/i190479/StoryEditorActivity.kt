package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** Edit a local story image/caption and decoration choices. Sharing keeps this Activity in history so Back from YourStory returns here. */
class StoryEditorActivity : AppCompatActivity() {
    private var image=4
    override fun onCreate(state:Bundle?) {
        super.onCreate(state);setContentView(R.layout.activity_story_editor);Ui.prepare(this)
        findViewById<View>(R.id.toolTag).setOnClickListener {Ui.choose(this,"Tag a friend",arrayOf("Aisha Khan","Omar Farooq","Lina Marsh")){name->findViewById<TextView>(R.id.storySticker).apply{text="@$name";visibility=View.VISIBLE}}}
        findViewById<View>(R.id.btnShareArrow).setOnClickListener{findViewById<View>(R.id.btnShareStory).performClick()}
        image=state?.getInt("image") ?: intent.getIntExtra("image",4)
        findViewById<ImageView>(R.id.storyImage).setImageResource(DemoPhotos.resource(image))
        val caption=findViewById<TextView>(R.id.storyCaption)
        fun editCaption(){val input=EditText(this);input.setText(caption.text);androidx.appcompat.app.AlertDialog.Builder(this).setTitle("Story text").setView(input).setPositiveButton("Save"){_,_->caption.text=input.text}.setNegativeButton("Cancel",null).show()}
        caption.setOnClickListener {editCaption()};findViewById<View>(R.id.tool0).setOnClickListener{editCaption()}
        findViewById<View>(R.id.tool1).setOnClickListener {Ui.choose(this,"Sticker",arrayOf("♥","☆","Weekend!","Remove sticker")){value->findViewById<TextView>(R.id.storySticker).apply{text=if(value=="Remove sticker")"" else value;visibility=if(text.isBlank())View.GONE else View.VISIBLE}}}
        findViewById<View>(R.id.tool2).setOnClickListener {Ui.choose(this,"Music label",arrayOf("Weekend walk","City lights","Remove music")){value->findViewById<TextView>(R.id.storyMusic).apply{text=if(value=="Remove music")"" else "♪ $value";visibility=if(text.isBlank())View.GONE else View.VISIBLE}}}
        findViewById<View>(R.id.tool3).setOnClickListener {Ui.choose(this,"Story effect",arrayOf("Natural","Sunset","Night")){v->image=when(v){"Sunset"->5;"Night"->6;else->4};findViewById<ImageView>(R.id.storyImage).setImageResource(DemoPhotos.resource(image))}}
        fun share(audience:String){getSharedPreferences("demo",MODE_PRIVATE).edit().putBoolean("story",true).putInt("storyImage",image).putString("storyCaption",caption.text.toString()).putString("storyAudience",audience).putString("storySticker",findViewById<TextView>(R.id.storySticker).text.toString()).putString("storyMusic",findViewById<TextView>(R.id.storyMusic).text.toString()).apply();startActivity(Intent(this,YourStoryActivity::class.java))}
        findViewById<View>(R.id.btnShareStory).setOnClickListener{share("Your story")};findViewById<View>(R.id.btnCloseFriends).setOnClickListener{share("Close friends")}
    }
    override fun onSaveInstanceState(out:Bundle){out.putInt("image",image);super.onSaveInstanceState(out)}
}
