package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        Ui.prepare(this)
        findViewById<View>(R.id.searchPerson0).setOnClickListener { startActivity(Intent(this, OtherProfileActivity::class.java)) }
        findViewById<View>(R.id.searchPerson1).setOnClickListener { startActivity(Intent(this, OtherProfileActivity::class.java)) }
        findViewById<View>(R.id.searchPerson2).setOnClickListener { startActivity(Intent(this, OtherProfileActivity::class.java)) }
        findViewById<View>(R.id.btnAllPeople).setOnClickListener { startActivity(Intent(this, FriendsActivity::class.java)) }
        findViewById<View>(R.id.btnJoin).setOnClickListener { (it as TextView).text = "Joined" }
        findViewById<View>(R.id.filter0).setOnClickListener { it.isSelected = true; Toast.makeText(this, "All results selected", Toast.LENGTH_SHORT).show() }
        findViewById<View>(R.id.filter1).setOnClickListener { it.isSelected = true; Toast.makeText(this, "People results selected", Toast.LENGTH_SHORT).show() }
        findViewById<View>(R.id.filter2).setOnClickListener { it.isSelected = true; Toast.makeText(this, "Posts results selected", Toast.LENGTH_SHORT).show() }
        findViewById<View>(R.id.filter3).setOnClickListener { it.isSelected = true; Toast.makeText(this, "Groups results selected", Toast.LENGTH_SHORT).show() }

    }


}
