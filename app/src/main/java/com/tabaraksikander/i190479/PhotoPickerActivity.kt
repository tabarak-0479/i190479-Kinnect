package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** Returns selected image identities to the Activity that opened the picker. */
class PhotoPickerActivity : AppCompatActivity() {
    private val selected = linkedSetOf<Int>()
    private var multiple = true
    private val cells = intArrayOf(R.id.photo0,R.id.photo1,R.id.photo2,R.id.photo3,R.id.photo4,R.id.photo5,R.id.photo6,R.id.photo7,R.id.photo8,R.id.photo9,R.id.photo10,R.id.photo11,R.id.photo12,R.id.photo13,R.id.photo14,R.id.photo15)
    private val marks = intArrayOf(R.id.selected0,R.id.selected1,R.id.selected2,R.id.selected3,R.id.selected4,R.id.selected5,R.id.selected6,R.id.selected7,R.id.selected8,R.id.selected9,R.id.selected10,R.id.selected11,R.id.selected12,R.id.selected13,R.id.selected14,R.id.selected15)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_photo_picker); Ui.prepare(this)
        selected.addAll((savedInstanceState?.getIntArray("selection") ?: intent.getIntArrayExtra("selection") ?: intArrayOf(0, 5)).toList())
        multiple = savedInstanceState?.getBoolean("multiple", true) ?: true
        for (i in cells.indices) findViewById<View>(cells[i]).setOnClickListener {
            if (!multiple) { selected.clear(); selected.add(i) }
            else if (!selected.add(i)) selected.remove(i)
            findViewById<ImageView>(R.id.photoPreview).setImageResource(DemoPhotos.resource(i)); refresh()
        }
        findViewById<View>(R.id.btnMultiple).setOnClickListener {
            multiple = !multiple
            if (!multiple && selected.size > 1) { val first = selected.first(); selected.clear(); selected.add(first) }
            refresh()
        }
        findViewById<View>(R.id.btnNext).setOnClickListener {
            if (selected.isNotEmpty()) { setResult(RESULT_OK, Intent().putExtra("selection", selected.toIntArray())); finish() }
        }
        findViewById<View>(R.id.btnTakePhoto).setOnClickListener { startActivity(Intent(this, CameraActivity::class.java)) }
        findViewById<View>(R.id.btnVideo).setOnClickListener { startActivity(Intent(this, CameraActivity::class.java).putExtra("mode", "Video")) }
        findViewById<View>(R.id.btnGallery).setOnClickListener { findViewById<ScrollView>(R.id.pickerScroll).smoothScrollTo(0, 0) }
        refresh()
    }
    private fun refresh() {
        findViewById<TextView>(R.id.btnNext).text = "Next (${selected.size})"
        findViewById<View>(R.id.btnNext).isEnabled = selected.isNotEmpty()
        findViewById<TextView>(R.id.btnMultiple).text = if (multiple) "SELECT MULTIPLE" else "SINGLE PHOTO"
        for (i in marks.indices) {
            val mark = findViewById<TextView>(marks[i]); val position = selected.indexOf(i)
            mark.text = if (position >= 0) (position + 1).toString() else ""
            mark.setBackgroundResource(if (position >= 0) R.drawable.selection_on else R.drawable.selection_off)
            findViewById<View>(cells[i]).contentDescription = "Photo ${i+1}, " + if (position >= 0) "selected ${position+1}" else "not selected"
        }
    }
    override fun onSaveInstanceState(out: Bundle) { out.putIntArray("selection", selected.toIntArray()); out.putBoolean("multiple", multiple); super.onSaveInstanceState(out) }
}
