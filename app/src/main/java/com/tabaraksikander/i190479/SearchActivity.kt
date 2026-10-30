package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** TextWatcher filters the local people list. Person extras preserve the identity of the tapped result; categories and recent searches are local. */
class SearchActivity : AppCompatActivity() {
    private val names = listOf("Omar Farooq", "Omar Siddiqui", "Omar Tariq")
    private var category = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_search); Ui.prepare(this)
        category=savedInstanceState?.getInt("category") ?: intent.getIntExtra("category",0)
        if(category==4)findViewById<EditText>(R.id.etSearch).setText("")
        val query = findViewById<EditText>(R.id.etSearch)
        query.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { filter() }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })
        val ids = intArrayOf(R.id.searchPerson0, R.id.searchPerson1, R.id.searchPerson2)
        for (i in ids.indices) findViewById<View>(ids[i]).setOnClickListener { Ui.openPerson(this, names[i]) }
        findViewById<View>(R.id.btnAllPeople).setOnClickListener { category = 1; filter() }
        findViewById<View>(R.id.btnJoin).setOnClickListener { val t=it as TextView; t.isSelected = !t.isSelected; t.text = if(t.isSelected) "Joined" else "Join";getSharedPreferences("demo",MODE_PRIVATE).edit().putBoolean("joinedGroup",t.isSelected).apply() }
        for ((i,id) in intArrayOf(R.id.filter0,R.id.filter1,R.id.filter2,R.id.filter3,R.id.filter4).withIndex()) findViewById<View>(id).setOnClickListener { category = i; filter() }
        findViewById<View>(R.id.btnClearSearch).setOnClickListener { query.text.clear() }
        for ((i,id) in intArrayOf(R.id.recent0,R.id.recent1,R.id.recent2).withIndex()) {
            findViewById<View>(id).setOnClickListener { query.setText(listOf("lina marsh", "study desk karachi", "SMD Class of 2026")[i]) }
        }
        for ((i,id) in intArrayOf(R.id.clearRecent0,R.id.clearRecent1,R.id.clearRecent2).withIndex()) findViewById<View>(id).setOnClickListener { findViewById<View>(intArrayOf(R.id.recentRow0,R.id.recentRow1,R.id.recentRow2)[i]).visibility=View.GONE }
        filter()
    }
    private fun filter() {
        val q=findViewById<EditText>(R.id.etSearch).text.toString().trim()
        val ids=intArrayOf(R.id.searchPerson0,R.id.searchPerson1,R.id.searchPerson2)
        var count=0
        for(i in ids.indices) { val show=(category==0 || category==1) && names[i].contains(q,true); findViewById<View>(ids[i]).visibility=if(show) View.VISIBLE else View.GONE; if(show) count++ }
        findViewById<View>(R.id.searchPeopleHeading).visibility=if(category==0 || category==1) View.VISIBLE else View.GONE
        findViewById<View>(R.id.searchGroupSection).visibility=if((category==0 || category==3) && (q.isBlank() || "Omar’s Photography Circle".contains(q,true))) View.VISIBLE else View.GONE
        findViewById<TextView>(R.id.searchEmpty).apply { visibility=if(count==0 && category!=3) View.VISIBLE else View.GONE; text=if(category==2) "No matching posts" else if(category==4) "No matching marketplace listings" else "No matching people" }
        for((i,id) in intArrayOf(R.id.filter0,R.id.filter1,R.id.filter2,R.id.filter3,R.id.filter4).withIndex()) { findViewById<View>(id).isSelected=i==category; findViewById<View>(id).setBackgroundResource(if(i==category) R.drawable.chip_active else R.drawable.button_pale) }
    }
    override fun onSaveInstanceState(out:Bundle){out.putInt("category",category);super.onSaveInstanceState(out)}
}
