package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        Ui.prepare(this)
        if (savedInstanceState == null) {
            findViewById<android.widget.Spinner>(R.id.birthdayDay).setSelection(13)
            findViewById<android.widget.Spinner>(R.id.birthdayMonth).setSelection(2)
            findViewById<android.widget.Spinner>(R.id.birthdayYear).setSelection(22)
        }

        val btnBack = findViewById<TextView>(R.id.btnBack)
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
            val intent = Intent(this, HomeActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }
            startActivity(intent)
        }
    }
}
