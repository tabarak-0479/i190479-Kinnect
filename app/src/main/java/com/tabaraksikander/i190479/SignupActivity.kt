package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/** Simple signup form with birthday widgets and local profile data. Photo IDs return through an Activity result; this creates no server account. */
class SignupActivity : AppCompatActivity() {
    private var avatar=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        Ui.prepare(this)
        avatar=savedInstanceState?.getInt("avatar",-1) ?: -1
        if(avatar>=0)findViewById<android.widget.ImageView>(R.id.signupAvatarPhoto).apply{visibility=View.VISIBLE;setImageResource(DemoPhotos.resource(avatar))}
        findViewById<EditText>(R.id.etSignupPassword).addTextChangedListener(object:android.text.TextWatcher{
            override fun beforeTextChanged(s:CharSequence?,start:Int,count:Int,after:Int){}
            override fun afterTextChanged(s:android.text.Editable?){}
            override fun onTextChanged(s:CharSequence?,start:Int,before:Int,count:Int){val strength=if((s?.length?:0)>=10)3 else if((s?.length?:0)>=6)2 else if(s.isNullOrEmpty())0 else 1;for((i,id)in intArrayOf(R.id.strength0,R.id.strength1,R.id.strength2).withIndex())findViewById<View>(id).setBackgroundResource(if(i>=strength)R.drawable.progress_pale else if(strength==3)R.drawable.progress_teal else R.drawable.progress_orange);findViewById<TextView>(R.id.strengthLabel).text=listOf("","Weak","Medium","Strong")[strength]}
        })
        findViewById<View>(R.id.btnSignupPhoto).setOnClickListener {startActivityForResult(Intent(this,PhotoPickerActivity::class.java).putExtra("multiple",false),601)}
        findViewById<View>(R.id.btnPasswordEye).setOnClickListener {
            val field=findViewById<EditText>(R.id.etSignupPassword);it.isSelected=!it.isSelected
            field.transformationMethod=if(it.isSelected)null else android.text.method.PasswordTransformationMethod.getInstance()
            field.setSelection(field.text.length);it.contentDescription=if(it.isSelected)"Hide password"else"Show password"
        }

        if (savedInstanceState == null) {
            findViewById<android.widget.Spinner>(R.id.birthdayDay).setSelection(13)
            findViewById<android.widget.Spinner>(R.id.birthdayMonth).setSelection(2)
            findViewById<android.widget.Spinner>(R.id.birthdayYear).setSelection(22)
        }

        val btnBack = findViewById<View>(R.id.btnBack)
        val btnCreateAccount = findViewById<Button>(R.id.btnCreateAccountSignup)
        val tvAlreadyLogin = findViewById<TextView>(R.id.tvAlreadyLogin)

        btnBack.setOnClickListener {
            finish()
        }

        tvAlreadyLogin.setOnClickListener {
            finish()
        }

        btnCreateAccount.setOnClickListener {
            if (!findViewById<android.widget.CheckBox>(R.id.checkTerms).isChecked) {
                android.widget.Toast.makeText(this, "Please agree to the terms to continue", android.widget.Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val first=findViewById<EditText>(R.id.etFirstName);val last=findViewById<EditText>(R.id.etLastName)
            if(first.text.isBlank() || last.text.isBlank()){first.error="Enter your full name";return@setOnClickListener}
            getSharedPreferences("demo",MODE_PRIVATE).edit().putString("name",first.text.toString()+" "+last.text).putString("email",findViewById<EditText>(R.id.etSignupEmail).text.toString()).putInt("avatar",avatar).apply()
            val intent = Intent(this, HomeActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }
            startActivity(intent)
        }
    }
    @Deprecated("Simple Intent result handling") override fun onActivityResult(request:Int,result:Int,data:Intent?){super.onActivityResult(request,result,data);if(request==601 && result==RESULT_OK){avatar=data?.getIntArrayExtra("selection")?.firstOrNull() ?: -1;if(avatar>=0){findViewById<android.widget.ImageView>(R.id.signupAvatarPhoto).apply{visibility=View.VISIBLE;setImageResource(DemoPhotos.resource(avatar))};findViewById<View>(R.id.signupPerson).visibility=View.INVISIBLE}}}
    override fun onSaveInstanceState(out:Bundle){out.putInt("avatar",avatar);super.onSaveInstanceState(out)}
}
