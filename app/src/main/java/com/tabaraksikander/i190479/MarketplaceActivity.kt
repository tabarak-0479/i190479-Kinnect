package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** XML screen with ordinary click listeners and explicit Activity Intents. */
class MarketplaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)
        Ui.prepare(this)
        findViewById<View>(R.id.btnSell).setOnClickListener { Ui.info(this, "Sell", "Create a listing: add photos, title, price and location.") }
        findViewById<View>(R.id.btnCategories).setOnClickListener { Ui.info(this, "Categories", "Vehicles · Home · Furniture · Garden") }
        findViewById<View>(R.id.item0).setOnClickListener { Ui.info(this, "Road bike, 21-speed", "Rs 45,000 · Karachi\nMessage the seller to ask about availability.") }
        findViewById<View>(R.id.item1).setOnClickListener { Ui.info(this, "Rattan armchair", "Rs 8,500 · Karachi\nMessage the seller to ask about availability.") }
        findViewById<View>(R.id.item2).setOnClickListener { Ui.info(this, "Solid wood study desk", "Rs 15,000 · Karachi\nMessage the seller to ask about availability.") }
        findViewById<View>(R.id.item3).setOnClickListener { Ui.info(this, "Ceramic plant pots, set of 3", "Rs 3,200 · Karachi\nMessage the seller to ask about availability.") }

    }


}
