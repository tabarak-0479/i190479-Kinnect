package com.tabaraksikander.i190479

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/** Small shared functions; navigation still uses normal Activity Intents. */
object Ui {
    fun prepare(a: AppCompatActivity) {
        // Target SDK 37 draws edge to edge. Keep controls clear of system bars and keyboard.
        val content = a.findViewById<View>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(content) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
        ViewCompat.requestApplyInsets(content)
        a.findViewById<View>(R.id.btnBack)?.setOnClickListener { a.finish() }
        val targets = listOf(R.id.navHome to HomeActivity::class.java, R.id.navPeople to FriendsActivity::class.java,
            R.id.navMarketplace to MarketplaceActivity::class.java, R.id.navNotifications to NotificationsActivity::class.java,
            R.id.navMenu to MenuActivity::class.java, R.id.btnSearch to SearchActivity::class.java)
        for ((id, target) in targets) a.findViewById<View>(id)?.setOnClickListener {
            if (a.javaClass != target) a.startActivity(Intent(a, target))
        }
    }
    fun info(a: AppCompatActivity, title: String, message: String) {
        AlertDialog.Builder(a).setTitle(title).setMessage(message).setPositiveButton("OK", null).show()
    }
    fun toggle(a: AppCompatActivity, id: Int, on: String, off: String) {
        a.findViewById<TextView>(id).setOnClickListener {
            val t = it as TextView
            t.isSelected = !t.isSelected
            t.text = if (t.isSelected) on else off
        }
    }
}
