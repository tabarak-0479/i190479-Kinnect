package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** All seven fields and selected avatar/cover IDs are saved together. Photo result request codes distinguish which image is being edited. */
class EditProfileActivity : AppCompatActivity() {
    private val fields = listOf(R.id.etName to "name", R.id.etUsername to "username", R.id.etWebsite to "website", R.id.etBio to "bio", R.id.etEmail to "email", R.id.etPhone to "phone", R.id.etGender to "gender")
    private var avatar = -1
    private var cover = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_edit_profile); Ui.prepare(this)
        val prefs = getSharedPreferences("demo", MODE_PRIVATE)
        avatar = savedInstanceState?.getInt("avatar", -1) ?: prefs.getInt("avatar", -1)
        cover = savedInstanceState?.getInt("cover", -1) ?: prefs.getInt("cover", -1)
        if (savedInstanceState == null) for ((id,key) in fields) {
            val field = findViewById<EditText>(id); field.setText(prefs.getString(key, field.text.toString()))
        }
        findViewById<View>(R.id.btnEditAvatar).setOnClickListener { startActivityForResult(Intent(this, PhotoPickerActivity::class.java).putExtra("multiple",false).putExtra("selection",if(avatar>=0)intArrayOf(avatar)else intArrayOf()), 310) }
        findViewById<View>(R.id.btnEditAvatarLabel).setOnClickListener { findViewById<View>(R.id.btnEditAvatar).performClick() }
        findViewById<View>(R.id.btnEditCover).setOnClickListener { startActivityForResult(Intent(this, PhotoPickerActivity::class.java).putExtra("multiple",false).putExtra("selection",if(cover>=0)intArrayOf(cover)else intArrayOf()), 311) }
        findViewById<View>(R.id.btnEditCoverLabel).setOnClickListener { findViewById<View>(R.id.btnEditCover).performClick() }
        findViewById<View>(R.id.btnSave).setOnClickListener {
            if (findViewById<EditText>(R.id.etName).text.isBlank()) { findViewById<EditText>(R.id.etName).error = "Enter your name"; return@setOnClickListener }
            val edit = prefs.edit(); for ((id,key) in fields) edit.putString(key, findViewById<EditText>(id).text.toString())
            edit.putInt("avatar", avatar).putInt("cover", cover).apply(); finish()
        }
        findViewById<View>(R.id.editAvatarPhoto).setOnClickListener {findViewById<View>(R.id.btnEditAvatarLabel).performClick()}
        showImages()
    }
    private fun showImages() {
        findViewById<ImageView>(R.id.editAvatarPhoto).apply { visibility = if (avatar >= 0) View.VISIBLE else View.GONE; if (avatar >= 0) setImageResource(DemoPhotos.resource(avatar)) }
        findViewById<TextView>(R.id.btnEditAvatar).visibility = if (avatar >= 0) View.INVISIBLE else View.VISIBLE
        findViewById<ImageView>(R.id.btnEditCover).setImageResource(if (cover >= 0) DemoPhotos.resource(cover) else R.drawable.landscape_sea)
    }
    @Deprecated("Simple Activity result API retained to match class-style Intent navigation")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val image = data?.getIntArrayExtra("selection")?.firstOrNull() ?: return
        if (resultCode == RESULT_OK) { if (requestCode == 310) avatar = image else if (requestCode == 311) cover = image; showImages() }
    }
    override fun onSaveInstanceState(out: Bundle) { out.putInt("avatar", avatar); out.putInt("cover", cover); super.onSaveInstanceState(out) }
}
