package com.example.messenger

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.messenger.data.local.AppPreferences
import com.example.messenger.login.LoginPresenter
import com.example.messenger.login.LoginView

class LoginActivity : AppCompatActivity(), LoginView,
    View.OnClickListener {
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button
    private lateinit var processBar: ProgressBar
    private lateinit var presenter: LoginPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bindView()
    }

    override fun showProgress() {
        processBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        processBar.visibility = View.GONE
    }

    override fun setUsernameError() {
        etUsername.error = "${R.string.username_field}"
    }

    override fun setPasswordError() {
        etPassword.error = "${R.string.password_field}"
    }

    override fun navigateToSignUp() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    override fun navigateToHome() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun bindView() {
        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        btnSignUp = findViewById(R.id.btn_sign_up)
        processBar = findViewById(R.id.progress_bar)
        btnLogin.setOnClickListener(this)
        btnSignUp.setOnClickListener(this)
    }

    override fun getContext(): Context {
        return this
    }

    override fun showAuthError() {
        Toast.makeText(
            this, R.string.invalid_data_text,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_login) {
            presenter.excuteLogin(
                etUsername.text.toString(),
                etPassword.text.toString()
            )
        } else if (v.id == R.id.btn_sign_up) {
            navigateToSignUp()
        }
    }
}