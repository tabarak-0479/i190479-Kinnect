package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** Local story sequence with person/image/progress state. A submitted reply is stored under that person so it appears in their conversation. */
class StoryViewerActivity : AppCompatActivity() {
    private val people=listOf("Omar Farooq","Sara Iqbal","Hamza Ali","Lina Marsh")
    private var index=0
    override fun onCreate(state:Bundle?) {
        super.onCreate(state);setContentView(R.layout.activity_story_viewer);Ui.prepare(this)
        index=state?.getInt("storyIndex") ?: people.indexOf(intent.getStringExtra("person") ?: "Omar Farooq").coerceAtLeast(0)
        findViewById<View>(R.id.btnStoryNext).setOnClickListener{if(index<3){index++;showStory()}else finish()}
        findViewById<View>(R.id.btnStoryPrevious).setOnClickListener{if(index>0){index--;showStory()}else finish()}
        findViewById<View>(R.id.btnStoryLike).setOnClickListener{react("Love")};findViewById<View>(R.id.btnStorySmile).setOnClickListener{react("Haha")};findViewById<View>(R.id.btnStoryThumb).setOnClickListener{react("Like")}
        findViewById<View>(R.id.btnStoryReplySend).setOnClickListener{sendReply()}
        findViewById<EditText>(R.id.etStoryReply).setOnEditorActionListener{_,action,_->if(action==android.view.inputmethod.EditorInfo.IME_ACTION_SEND){sendReply();true}else false}
        findViewById<View>(R.id.btnOptions).setOnClickListener {Ui.choose(this,"Story",arrayOf("View profile","Reply")){value->if(value=="View profile")Ui.openPerson(this,people[index])else findViewById<EditText>(R.id.etStoryReply).requestFocus()}}
        findViewById<EditText>(R.id.etStoryReply).addTextChangedListener(object:android.text.TextWatcher {
            override fun beforeTextChanged(s:CharSequence?,start:Int,count:Int,after:Int){}
            override fun afterTextChanged(s:android.text.Editable?){}
            override fun onTextChanged(s:CharSequence?,start:Int,before:Int,count:Int){findViewById<View>(R.id.btnStoryReplySend).visibility=if(s.isNullOrBlank())View.GONE else View.VISIBLE}
        })
        showStory()
    }
    private fun showStory(){findViewById<TextView>(R.id.storyPerson).text=people[index];findViewById<TextView>(R.id.storyPersonAvatar).apply{text=Ui.initials(people[index]);setBackgroundResource(Ui.personColor(people[index]))};findViewById<ImageView>(R.id.storyImage).setImageResource(DemoPhotos.resource(intArrayOf(6,5,4,2)[index]));findViewById<TextView>(R.id.storyCaption).text=listOf("Karachi nights","Saturday plans","Fresh air","Golden hour")[index];for((i,id)in intArrayOf(R.id.storySegment0,R.id.storySegment1,R.id.storySegment2,R.id.storySegment3).withIndex())findViewById<View>(id).alpha=if(i<=index)1f else 0.4f}
    private fun react(reaction:String){getSharedPreferences("demo",MODE_PRIVATE).edit().putString("storyReaction:"+people[index],reaction).apply();findViewById<View>(when(reaction){"Love"->R.id.btnStoryLike;"Haha"->R.id.btnStorySmile;else->R.id.btnStoryThumb}).isSelected=true;Toast.makeText(this,"$reaction sent to ${people[index]}",Toast.LENGTH_SHORT).show()}
    private fun sendReply(){val input=findViewById<EditText>(R.id.etStoryReply);if(input.text.isBlank())return;val p=getSharedPreferences("demo",MODE_PRIVATE);val person=people[index];val n=p.getInt("messages:$person",0);p.edit().putString("message:$person:$n","Replied to your story: "+input.text).putInt("messages:$person",n+1).apply();input.text.clear();Toast.makeText(this,"Reply added to your chat",Toast.LENGTH_SHORT).show()}
    override fun onSaveInstanceState(out:Bundle){out.putInt("storyIndex",index);super.onSaveInstanceState(out)}
}
