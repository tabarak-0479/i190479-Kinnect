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
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}