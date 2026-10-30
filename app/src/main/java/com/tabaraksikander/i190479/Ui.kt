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
        refreshBadges(a)
        adjustText(a)
        content.viewTreeObserver.addOnWindowFocusChangeListener { hasFocus ->
            if (hasFocus) { refreshBadges(a); adjustText(a) }
        }
        // Small overlapping photo badges retain their visual size and receive a larger hit area.
        content.post {
            for (id in intArrayOf(R.id.avatarCamera, R.id.coverCamera, R.id.btnSignupPhoto)) {
                val target = a.findViewById<View>(id) ?: continue
                val parent = target.parent as? View ?: continue
                val bounds = android.graphics.Rect(); target.getHitRect(bounds)
                val extra = dp(a, 8); bounds.inset(-extra, -extra)
                parent.touchDelegate = android.view.TouchDelegate(bounds, target)
            }
        }
        // Limit long text lines on tablets, while keeping ordinary XML layouts on phones.
        content.addOnLayoutChangeListener { v, _, _, _, _, _, _, _, _ ->
            val group = v as android.view.ViewGroup
            val root = group.getChildAt(0) ?: return@addOnLayoutChangeListener
            val width = minOf(v.width - v.paddingLeft - v.paddingRight, dp(a, 720))
            val params = root.layoutParams as? android.widget.FrameLayout.LayoutParams
            if (params != null && width > 0 && params.width != width) {
                params.width = width; params.gravity = android.view.Gravity.CENTER_HORIZONTAL; root.layoutParams = params
            }
        }
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

    fun dp(a: android.content.Context, value: Int) = (value * a.resources.displayMetrics.density).toInt()
    fun initials(name: String) = name.split(" ").filter { it.isNotBlank() }.take(2).joinToString("") { it.first().uppercase() }
    fun openPerson(a: AppCompatActivity, name: String) {
        a.startActivity(Intent(a, OtherProfileActivity::class.java).putExtra("person", name))
    }
    fun personColor(name: String) = when (name) {
        "Omar Farooq", "Noor Fatima" -> R.drawable.circle_green
        "Aisha Khan", "Daniyal Shah" -> R.drawable.circle_red
        "Sara Iqbal", "Omar Siddiqui" -> R.drawable.circle_blue
        "Hamza Ali", "Omar Tariq" -> R.drawable.circle_gold
        else -> R.drawable.circle_purple
    }

    fun refreshBadges(a: AppCompatActivity) {
        val visible = a.getSharedPreferences("demo", android.content.Context.MODE_PRIVATE).getBoolean("badges", true)
        for (id in intArrayOf(R.id.navBadge, R.id.chatBadge)) a.findViewById<View>(id)?.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun adjustText(a: AppCompatActivity) {
        val factor = if (a.getSharedPreferences("demo", android.content.Context.MODE_PRIVATE).getBoolean("largeText", false)) 1.15f else 1f
        fun visit(view: View) {
            if (view is TextView) {
                val original = (view.getTag(R.id.originalTextSize) as? Float) ?: view.textSize.also { view.setTag(R.id.originalTextSize, it) }
                view.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, original * factor)
            }
            if (view is android.view.ViewGroup) for (i in 0 until view.childCount) visit(view.getChildAt(i))
        }
        visit(a.findViewById(android.R.id.content))
    }

    /** Use actual visible state for local single-choice secondary controls. */
    fun choose(a: AppCompatActivity, title: String, values: Array<String>, selected: (String) -> Unit) {
        AlertDialog.Builder(a).setTitle(title).setItems(values) { _, which -> selected(values[which]) }.setNegativeButton("Cancel", null).show()
    }
}
