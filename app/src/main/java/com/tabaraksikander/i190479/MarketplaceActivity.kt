package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/** Bundled listings and locally saved listings share working filters and detail/message actions. */
class MarketplaceActivity : AppCompatActivity() {
    private val names=listOf("Road bike, 21-speed","Rattan armchair","Solid wood study desk","Ceramic plant pots, set of 3")
    private val prices=listOf("Rs 45,000","Rs 8,500","Rs 15,000","Rs 3,200")
    private val areas=listOf("Clifton","DHA Phase 6","Gulshan","Saddar")
    private var category="All"
    private var draftImage=4
    private var draftPreview:ImageView?=null
    override fun onCreate(state:Bundle?){
        super.onCreate(state);setContentView(R.layout.activity_marketplace);Ui.prepare(this);category=state?.getString("category") ?: "All"
        findViewById<View>(R.id.btnMarketplaceProfile).setOnClickListener{startActivity(Intent(this,ProfileActivity::class.java))}
        findViewById<View>(R.id.btnSearch).setOnClickListener{startActivity(Intent(this,SearchActivity::class.java).putExtra("category",4))}
        for((i,id)in intArrayOf(R.id.item0,R.id.item1,R.id.item2,R.id.item3).withIndex())findViewById<View>(id).setOnClickListener{details(names[i],prices[i],areas[i],intArrayOf(4,5,3,0)[i],listOf("Omar Farooq","Sara Iqbal","Bilal Ahmed","Noor Fatima")[i])}
        findViewById<View>(R.id.btnCategories).setOnClickListener{Ui.choose(this,"Categories",arrayOf("All","Vehicles","Furniture","Garden")){value->category=value;filter()}}
        findViewById<View>(R.id.btnLocation).setOnClickListener{Ui.choose(this,"Search radius",arrayOf("10 km","20 km","40 km")){value->findViewById<TextView>(R.id.btnLocation).text="Karachi · $value"}}
        findViewById<View>(R.id.btnSell).setOnClickListener{sell()};filter();renderOwn()
    }
    private fun filter(){for((i,id)in intArrayOf(R.id.item0,R.id.item1,R.id.item2,R.id.item3).withIndex())findViewById<View>(id).visibility=if(category=="All"||category==listOf("Vehicles","Furniture","Furniture","Garden")[i])View.VISIBLE else View.GONE;findViewById<TextView>(R.id.btnCategories).text=if(category=="All")"Categories"else category}
    private fun details(name:String,price:String,area:String,image:Int,seller:String){
        val box=LinearLayout(this);box.orientation=LinearLayout.VERTICAL;box.setPadding(Ui.dp(this,16),Ui.dp(this,12),Ui.dp(this,16),Ui.dp(this,12));box.addView(ImageView(this).apply{setImageResource(DemoPhotos.resource(image));scaleType=ImageView.ScaleType.CENTER_CROP;contentDescription=name},LinearLayout.LayoutParams(-1,Ui.dp(this,180)));box.addView(TextView(this).apply{text="$price\n$area\nSeller: $seller";textSize=16f;setPadding(0,Ui.dp(this@MarketplaceActivity,12),0,0)})
        val p=getSharedPreferences("demo",MODE_PRIVATE);androidx.appcompat.app.AlertDialog.Builder(this).setTitle(name).setView(box).setPositiveButton("Message seller"){_,_->startActivity(Intent(this,ChatActivity::class.java).putExtra("person",seller))}.setNeutralButton(if(p.getBoolean("savedListing:$name",false))"Unsave"else"Save"){_,_->p.edit().putBoolean("savedListing:$name",!p.getBoolean("savedListing:$name",false)).apply()}.setNegativeButton("Close",null).show()
    }
    private fun sell(){
        val box=LinearLayout(this);box.orientation=LinearLayout.VERTICAL;box.setPadding(Ui.dp(this,16),0,Ui.dp(this,16),0)
        val title=EditText(this).apply{hint="Listing title"};val price=EditText(this).apply{hint="Price in Rs";inputType=android.text.InputType.TYPE_CLASS_NUMBER};val area=EditText(this).apply{hint="Area in Karachi"}
        val pic=ImageView(this).apply{setImageResource(DemoPhotos.resource(draftImage));scaleType=ImageView.ScaleType.CENTER_CROP;contentDescription="Choose listing image";setOnClickListener{startActivityForResult(Intent(this@MarketplaceActivity,PhotoPickerActivity::class.java).putExtra("multiple",false),701)}};draftPreview=pic
        box.addView(pic,LinearLayout.LayoutParams(-1,Ui.dp(this,120)));box.addView(title);box.addView(price);box.addView(area)
        val dialog=androidx.appcompat.app.AlertDialog.Builder(this).setTitle("Create listing").setView(box).setPositiveButton("Save",null).setNegativeButton("Cancel",null).create();dialog.setOnShowListener{dialog.getButton(-1).setOnClickListener{if(title.text.isBlank()||price.text.isBlank()){title.error="Title and price are required";return@setOnClickListener};val p=getSharedPreferences("demo",MODE_PRIVATE);val n=p.getInt("listings",0);p.edit().putString("listingTitle:$n",title.text.toString()).putString("listingPrice:$n",price.text.toString()).putString("listingArea:$n",area.text.toString()).putInt("listingImage:$n",draftImage).putInt("listings",n+1).apply();renderOwn();dialog.dismiss()}};dialog.show()
    }
    private fun renderOwn(){val box=findViewById<LinearLayout>(R.id.userListings);box.removeAllViews();val p=getSharedPreferences("demo",MODE_PRIVATE);for(i in 0 until p.getInt("listings",0)){val name=p.getString("listingTitle:$i","") ?: "";val price="Rs "+p.getString("listingPrice:$i","");val area=p.getString("listingArea:$i","") ?: "";val t=TextView(this);t.text="$name · $price\n$area";t.textSize=16f;t.setPadding(Ui.dp(this,16),Ui.dp(this,16),Ui.dp(this,16),Ui.dp(this,16));t.setOnClickListener{details(name,price,area,p.getInt("listingImage:$i",4),p.getString("name","Jacob West") ?: "Jacob West")};box.addView(t)}}
    @Deprecated("Simple Intent result handling") override fun onActivityResult(request:Int,result:Int,data:Intent?){super.onActivityResult(request,result,data);if(request==701&&result==RESULT_OK){draftImage=data?.getIntArrayExtra("selection")?.firstOrNull() ?: draftImage;draftPreview?.setImageResource(DemoPhotos.resource(draftImage))}}
    override fun onSaveInstanceState(out:Bundle){out.putString("category",category);super.onSaveInstanceState(out)}
    override fun onResume(){super.onResume();Ui.refreshBadges(this)}
}
