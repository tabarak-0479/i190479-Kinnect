package com.tabaraksikander.i190479

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/** Local demonstration login. Explicit Intent flags remove authentication screens after entering Home; the recent account uses the same action. */
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Ui.prepare(this)
        findViewById<View>(R.id.tvLanguage).setOnClickListener {Ui.choose(this,"Language",arrayOf("English (UK)","English (US)")){value->(it as android.widget.TextView).text=value}}
        findViewById<View>(R.id.btnPasswordEye).setOnClickListener {
            val field=findViewById<EditText>(R.id.etPassword);it.isSelected=!it.isSelected
            field.transformationMethod=if(it.isSelected)null else android.text.method.PasswordTransformationMethod.getInstance()
            field.setSelection(field.text.length);it.contentDescription=if(it.isSelected)"Hide password"else"Show password"
        }

        findViewById<android.view.View>(R.id.tvForgotPassword).setOnClickListener {
            Ui.info(this, "Forgot password?", "Account recovery is not connected to a server in this local UI assignment.")
        }

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnCreateAccount = findViewById<Button>(R.id.btnCreateAccount)

        findViewById<View>(R.id.btnRecentAccount).setOnClickListener {btnLogin.performClick()}
        btnLogin.setOnClickListener {
            if(findViewById<EditText>(R.id.etEmail).text.isBlank()){findViewById<EditText>(R.id.etEmail).error="Enter your email";return@setOnClickListener}
            val intent = Intent(this, HomeActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }
            startActivity(intent)
        }

        btnCreateAccount.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}
