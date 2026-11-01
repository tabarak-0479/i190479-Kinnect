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
        adaptWidgets(a, content)
        val rootBackground = (content as android.view.ViewGroup).getChildAt(0)?.background as? android.graphics.drawable.ColorDrawable
        if (rootBackground != null) {
            content.setBackgroundColor(rootBackground.color)
            val light = androidx.core.graphics.ColorUtils.calculateLuminance(rootBackground.color) > 0.5
            androidx.core.view.WindowInsetsControllerCompat(a.window, a.window.decorView).apply {
                isAppearanceLightStatusBars = light
                isAppearanceLightNavigationBars = light
            }
        }
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

    /** Large system fonts stack gender choices and separate floating captions from input text. */
    fun adaptWidgets(context: android.content.Context, root: View) {
        if (context.resources.configuration.fontScale < 1.5f) return
        val gender = root.findViewById<android.widget.RadioGroup>(R.id.genderGroup)
        gender?.let { group ->
            group.orientation = android.widget.LinearLayout.VERTICAL
            group.layoutParams = group.layoutParams.apply { height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT }
            for (i in 0 until group.childCount) {
                val child = group.getChildAt(i)
                if (child is android.widget.RadioButton) child.layoutParams = android.widget.RadioGroup.LayoutParams(-1, -2).apply { bottomMargin = dp(context, 8) }
                else child.visibility = View.GONE
            }
        }
        fun visit(view: View) {
            if (view is android.widget.LinearLayout && view.orientation == android.widget.LinearLayout.HORIZONTAL) {
                val children = (0 until view.childCount).map { view.getChildAt(it) }
                val labeledFields = children.filterIsInstance<android.widget.FrameLayout>().count { frame ->
                    (0 until frame.childCount).any { frame.getChildAt(it) is android.widget.EditText || frame.getChildAt(it) is android.widget.Spinner }
                }
                if (labeledFields > 1) {
                    view.orientation = android.widget.LinearLayout.VERTICAL
                    for (child in children) {
                        if (child is android.widget.Space) child.visibility = View.GONE
                        else child.layoutParams = android.widget.LinearLayout.LayoutParams(-1, dp(context, 80)).apply { bottomMargin = dp(context, 8) }
                    }
                }
            }
            if (view is android.widget.FrameLayout) {
                val children = (0 until view.childCount).map { view.getChildAt(it) }
                val field = children.filterIsInstance<android.widget.EditText>().firstOrNull()
                val spinner = children.filterIsInstance<android.widget.Spinner>().firstOrNull()
                val caption = children.any { it is TextView && it !is android.widget.EditText && it.text.isNotBlank() }
                if (caption && (field != null || spinner != null)) {
                    view.layoutParams?.let { it.height = dp(context, 80); view.layoutParams = it }
                    children.filterIsInstance<TextView>().filter { it !is android.widget.EditText }.forEach {
                        it.layoutParams = it.layoutParams.apply { height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT }
                    }
                    field?.setPadding(field.paddingLeft, dp(context, 32), field.paddingRight, dp(context, 4))
                    spinner?.setPadding(spinner.paddingLeft, dp(context, 30), spinner.paddingRight, spinner.paddingBottom)
                }
            }
            if (view is TextView && view !is android.widget.EditText) {
                if (view.text.toString() == context.getString(R.string.ui_aisha_and_1_2k_others)) {
                    // A full-size reaction count must not squeeze the author summary into one letter per line.
                    val row = view.parent as? android.widget.LinearLayout
                    row?.orientation = android.widget.LinearLayout.VERTICAL
                    row?.gravity = android.view.Gravity.START
                    view.layoutParams = android.widget.LinearLayout.LayoutParams(-1, -2)
                }
                if (view.id in intArrayOf(R.id.commentLike0, R.id.commentLike1, R.id.commentLike2, R.id.commentLike3, R.id.commentLike4,
                        R.id.commentReply0, R.id.commentReply1, R.id.commentReply2, R.id.commentReply3, R.id.commentReply4, R.id.btnCancelReply)) {
                    view.layoutParams = view.layoutParams.apply { width = android.view.ViewGroup.LayoutParams.WRAP_CONTENT }
                    view.setPadding(dp(context, 12), view.paddingTop, dp(context, 12), view.paddingBottom)
                }
            }
            if (view is android.view.ViewGroup) for (i in 0 until view.childCount) visit(view.getChildAt(i))
        }
        visit(root)
        // Story captions keep their text at the requested font size instead of clipping a second line.
        root.findViewById<View>(R.id.btnCreateStory)?.let { card ->
            val strip = card.parent?.parent as? android.widget.HorizontalScrollView
            if (strip != null) {
                strip.layoutParams = strip.layoutParams.apply { height = dp(context, 248) }
                val cards = card.parent as android.view.ViewGroup
                for (i in 0 until cards.childCount) {
                    val frame = cards.getChildAt(i) as? android.widget.FrameLayout ?: continue
                    frame.layoutParams = frame.layoutParams.apply { height = dp(context, 224) }
                    for (j in 0 until frame.childCount) {
                        val caption = frame.getChildAt(j)
                        if (caption is TextView && caption.layoutParams.width == -1) {
                            caption.layoutParams = caption.layoutParams.apply { height = dp(context, 84) }
                        }
                        if (frame === card && caption is android.widget.FrameLayout) {
                            // Keep the overlapping plus badge above the enlarged two-line caption.
                            val badge = caption.layoutParams as? android.widget.FrameLayout.LayoutParams
                            if (badge != null && badge.gravity and android.view.Gravity.BOTTOM == android.view.Gravity.BOTTOM) {
                                badge.bottomMargin = dp(context, 84)
                                caption.layoutParams = badge
                            }
                        }
                    }
                }
            }
        }
    }
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
