package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** Illustrated camera demonstration with local flash/flip/effects. The shutter or chosen gallery image opens StoryEditor through an explicit Intent. */
class CameraActivity : AppCompatActivity() {
    private var image=4
    override fun onCreate(state:Bundle?) {
        super.onCreate(state);setContentView(R.layout.activity_camera);Ui.prepare(this)
        findViewById<View>(R.id.btnBrightness).setOnClickListener { val v=findViewById<ImageView>(R.id.cameraPreview);v.alpha=if(v.alpha==1f)0.65f else 1f }
        image=state?.getInt("image",4) ?: 4
        val preview=findViewById<ImageView>(R.id.cameraPreview);preview.setImageResource(DemoPhotos.resource(image))
        findViewById<View>(R.id.btnGallery).setOnClickListener {startActivityForResult(Intent(this,PhotoPickerActivity::class.java).putExtra("multiple",false),501)}
        findViewById<View>(R.id.btnShutter).setOnClickListener {startActivity(Intent(this,StoryEditorActivity::class.java).putExtra("image",image))}
        findViewById<View>(R.id.btnFlash).setOnClickListener {it.isSelected=!it.isSelected;preview.alpha=if(it.isSelected)0.75f else 1f;it.contentDescription=if(it.isSelected)"Flash on" else "Flash off"}
        findViewById<View>(R.id.btnFlip).setOnClickListener {preview.scaleX=-preview.scaleX;it.isSelected=!it.isSelected;it.contentDescription=if(it.isSelected)"Front preview selected" else "Rear preview selected"}
        findViewById<View>(R.id.btnEffects).setOnClickListener {Ui.choose(this,"Preview effect",arrayOf("Natural","Sunset","Night")){value->image=when(value){"Sunset"->5;"Night"->6;else->4};preview.setImageResource(DemoPhotos.resource(image))}}
        for((i,id) in intArrayOf(R.id.cameraMode0,R.id.cameraMode1,R.id.cameraMode2,R.id.cameraMode3,R.id.cameraMode4).withIndex()) findViewById<View>(id).setOnClickListener {for(other in intArrayOf(R.id.cameraMode0,R.id.cameraMode1,R.id.cameraMode2,R.id.cameraMode3,R.id.cameraMode4))findViewById<TextView>(other).setTextColor(if(other==id)android.graphics.Color.WHITE else android.graphics.Color.LTGRAY);findViewById<View>(R.id.btnShutter).contentDescription="Capture "+listOf("text","live preview","story","post","boomerang")[i]}
    }
    @Deprecated("Simple Intent result handling") override fun onActivityResult(request:Int,result:Int,data:Intent?) {super.onActivityResult(request,result,data);if(request==501 && result==RESULT_OK){image=data?.getIntArrayExtra("selection")?.firstOrNull() ?: image;findViewById<ImageView>(R.id.cameraPreview).setImageResource(DemoPhotos.resource(image))}}
    override fun onSaveInstanceState(out:Bundle){out.putInt("image",image);super.onSaveInstanceState(out)}
}
