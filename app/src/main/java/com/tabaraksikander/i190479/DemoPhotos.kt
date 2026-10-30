package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** Bundled drawable IDs returned by the simple local photo picker. */
object DemoPhotos {
    val images = intArrayOf(R.drawable.landscape_sand, R.drawable.landscape_sea,
        R.drawable.landscape_purple, R.drawable.landscape_blue, R.drawable.landscape_green,
        R.drawable.landscape_pink, R.drawable.landscape_night, R.drawable.landscape_sea,
        R.drawable.landscape_purple, R.drawable.landscape_sand, R.drawable.landscape_green,
        R.drawable.landscape_blue, R.drawable.landscape_pink, R.drawable.landscape_night,
        R.drawable.landscape_blue, R.drawable.landscape_sand)
    fun resource(index: Int) = images[index.coerceIn(images.indices)]
}
