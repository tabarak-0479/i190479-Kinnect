package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class CreatePostActivity : AppCompatActivity() {
    private var selectedPhotos = intArrayOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        Ui.prepare(this)
        findViewById<View>(R.id.btnAudience).setOnClickListener{Ui.choose(this,"Audience",arrayOf("Public","Friends","Only me")){value->(it as TextView).text="$value ⌄";getSharedPreferences("demo",MODE_PRIVATE).edit().putString("postAudience",value).apply()}}
        findViewById<View>(R.id.btnAlbum).setOnClickListener{Ui.choose(this,"Album",arrayOf("Weekend","Travel","Friends","No album")){value->(it as TextView).text="$value ⌄";getSharedPreferences("demo",MODE_PRIVATE).edit().putString("postAlbum",value).apply()}}
        selectedPhotos = savedInstanceState?.getIntArray("selection") ?: intent.getIntArrayExtra("selection") ?: intArrayOf()
        if(savedInstanceState==null && intent.getBooleanExtra("openPicker",false))startActivityForResult(Intent(this,PhotoPickerActivity::class.java).putExtra("selection",selectedPhotos),410)
        findViewById<View>(R.id.btnPhoto).setOnClickListener { startActivityForResult(Intent(this, PhotoPickerActivity::class.java).putExtra("selection", selectedPhotos), 410) }
        findViewById<View>(R.id.btnCamera).setOnClickListener { startActivity(Intent(this, CameraActivity::class.java)) }
        findViewById<View>(R.id.btnPost).setOnClickListener { val value = findViewById<EditText>(R.id.etPost).text.toString()
            if (value.isNotBlank()) {
                getSharedPreferences("demo", MODE_PRIVATE).edit().putString("post", value).putString("postImages", selectedPhotos.joinToString(",")).apply()
                startActivity(Intent(this, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)); finish()
            } }
        findViewById<View>(R.id.color0).setOnClickListener { findViewById<EditText>(R.id.etPost).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")) }
        findViewById<View>(R.id.color1).setOnClickListener { findViewById<EditText>(R.id.etPost).setBackgroundColor(android.graphics.Color.parseColor("#DDEEEE")) }
        findViewById<View>(R.id.color2).setOnClickListener { findViewById<EditText>(R.id.etPost).setBackgroundColor(android.graphics.Color.parseColor("#F7E8DF")) }
        findViewById<View>(R.id.color3).setOnClickListener { findViewById<EditText>(R.id.etPost).setBackgroundColor(android.graphics.Color.parseColor("#E0DFF0")) }
        findViewById<View>(R.id.color4).setOnClickListener { findViewById<EditText>(R.id.etPost).setBackgroundColor(android.graphics.Color.parseColor("#E2EEE5")) }
        findViewById<View>(R.id.color5).setOnClickListener { findViewById<EditText>(R.id.etPost).setBackgroundColor(android.graphics.Color.parseColor("#FFF0CA")) }
        findViewById<View>(R.id.color6).setOnClickListener { findViewById<EditText>(R.id.etPost).setBackgroundColor(android.graphics.Color.parseColor("#EFDDEB")) }
        findViewById<View>(R.id.color7).setOnClickListener { findViewById<EditText>(R.id.etPost).setBackgroundColor(android.graphics.Color.parseColor("#DDDAD7")) }
        findViewById<View>(R.id.btnTag).setOnClickListener { Ui.choose(this,"Tag people",arrayOf("Aisha Khan","Omar Farooq","Lina Marsh","Remove tag")){value->(findViewById<LinearLayout>(R.id.btnTag).getChildAt(1) as TextView).text=if(value=="Remove tag")"Tag people"else"With $value";getSharedPreferences("demo",MODE_PRIVATE).edit().putString("postTag",value).apply()} }
        findViewById<View>(R.id.btnFeeling).setOnClickListener { Ui.choose(this,"Feeling",arrayOf("Happy","Excited","Relaxed","No feeling")){value->(findViewById<LinearLayout>(R.id.btnFeeling).getChildAt(1) as TextView).text=if(value=="No feeling")"Feeling/activity"else"Feeling $value"} }
        findViewById<View>(R.id.btnCheckin).setOnClickListener { Ui.choose(this,"Check in",arrayOf("Karachi, Pakistan","Clifton Beach","DHA Phase 6","Remove location")){value->(findViewById<LinearLayout>(R.id.btnCheckin).getChildAt(1) as TextView).text=if(value=="Remove location")"Check in"else value} }
        findViewById<View>(R.id.btnLive).setOnClickListener { startActivity(Intent(this,CameraActivity::class.java).putExtra("mode","Live preview")) }
        findViewById<View>(R.id.btnEvent).setOnClickListener { val input=EditText(this);input.hint="Event title";androidx.appcompat.app.AlertDialog.Builder(this).setTitle("Create event").setView(input).setPositiveButton("Save"){_,_->if(input.text.isNotBlank()){getSharedPreferences("demo",MODE_PRIVATE).edit().putString("event",input.text.toString()).apply();(findViewById<LinearLayout>(R.id.btnEvent).getChildAt(1) as TextView).text="Event: "+input.text}}.setNegativeButton("Cancel",null).show() }

    }


    override fun onResume() {
        super.onResume()
        val count = selectedPhotos.size
        val strip=findViewById<LinearLayout>(R.id.postAttachments);strip.removeAllViews()
        for(index in selectedPhotos) { val image=ImageView(this);image.setImageResource(DemoPhotos.resource(index));image.scaleType=ImageView.ScaleType.CENTER_CROP;image.contentDescription="Selected attachment";strip.addView(image,LinearLayout.LayoutParams(Ui.dp(this,96),Ui.dp(this,96))) }
        findViewById<View>(R.id.btnPhoto).contentDescription = "Photo/video · $count selected"
        (findViewById<LinearLayout>(R.id.btnPhoto).getChildAt(1) as TextView).text = if (count > 0) "Photo/video · $count selected" else "Photo/video"
    }
    @Deprecated("Simple Intent result handling")
    override fun onActivityResult(request:Int,result:Int,data:Intent?) {super.onActivityResult(request,result,data);if(request==410 && result==RESULT_OK)selectedPhotos=data?.getIntArrayExtra("selection") ?: intArrayOf()}
    override fun onSaveInstanceState(out:Bundle){out.putIntArray("selection",selectedPhotos);super.onSaveInstanceState(out)}
}
