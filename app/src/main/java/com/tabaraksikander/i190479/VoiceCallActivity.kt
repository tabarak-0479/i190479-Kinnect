package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** Local call UI only: Chronometer displays elapsed time and buttons change local states. No phone/microphone permission or actual call is requested. */
class VoiceCallActivity : AppCompatActivity() {
    override fun onCreate(state:Bundle?) {
        super.onCreate(state);setContentView(R.layout.activity_voice_call);Ui.prepare(this)
        val person=intent.getStringExtra("person") ?: "Ammi"
        findViewById<TextView>(R.id.callName).text=person;findViewById<TextView>(R.id.callAvatar).text=if(person=="Ammi")"AM" else Ui.initials(person)
        findViewById<Chronometer>(R.id.callDuration).apply {base=state?.getLong("base") ?: (android.os.SystemClock.elapsedRealtime()-192000L);start()}
        findViewById<View>(R.id.btnEndCall).setOnClickListener {finish()}
        for((id,label)in listOf(R.id.btnMute to "Microphone muted",R.id.btnSpeaker to "Speaker on"))findViewById<ImageButton>(id).setOnClickListener {
            it.isSelected=!it.isSelected;it.contentDescription=if(it.isSelected)label else if(id==R.id.btnMute)"Mute microphone" else "Speaker off"
            it.setBackgroundResource(if(it.isSelected)R.drawable.circle_white else R.drawable.call_control)
            (it as ImageButton).setColorFilter(android.graphics.Color.parseColor(if(it.isSelected)"#0B5F63" else "#FFFFFF"))
        }
        findViewById<View>(R.id.btnKeypad).setOnClickListener {val input=EditText(this);input.inputType=android.text.InputType.TYPE_CLASS_PHONE;input.hint="Enter keypad digits";androidx.appcompat.app.AlertDialog.Builder(this).setTitle("Keypad").setView(input).setPositiveButton("Done"){_,_->findViewById<View>(R.id.btnKeypad).contentDescription="Keypad digits "+input.text}.setNegativeButton("Cancel",null).show()}
    }
    override fun onSaveInstanceState(out:Bundle){out.putLong("base",findViewById<Chronometer>(R.id.callDuration).base);super.onSaveInstanceState(out)}
    override fun onDestroy(){findViewById<Chronometer>(R.id.callDuration).stop();super.onDestroy()}
}
