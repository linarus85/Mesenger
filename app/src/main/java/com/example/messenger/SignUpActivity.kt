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
import com.example.messenger.sign.SignUpPresenter
import com.example.messenger.sign.SignUpPresenterImpl
import com.example.messenger.sign.SignUpView

class SignUpActivity : AppCompatActivity(), SignUpView,
    View.OnClickListener {
    private lateinit var etUsername: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var presenter: SignUpPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        presenter = SignUpPresenterImpl(this)
        presenter.preferences = AppPreferences.create(this)
        bindView()
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun showSignUpError() {
        Toast.makeText(
            this, R.string.unexpected_error,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun setUsernameError() {
        etUsername.error = "${R.string.username_field}"
    }

    override fun setPhoneNumberError() {
        etPhoneNumber.error = "${R.string.phone_field}"
    }

    override fun setPasswordError() {
        etPassword.error = "${R.string.password_field}"
    }

    override fun navigateToHome() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun bindView() {
        etUsername = findViewById(R.id.et_username)
        etPhoneNumber = findViewById(R.id.et_phone)
        etPassword = findViewById(R.id.et_password)
        btnSignUp = findViewById(R.id.btn_sign_up)
        progressBar = findViewById(R.id.progress_bar)
        btnSignUp.setOnClickListener(this)
    }

    override fun getContext(): Context {
       return this
    }

    override fun showAuthError() {
        Toast.makeText(
            this, R.string.authorisation_error,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_sign_up) {
            presenter.executeSignUp(
                etUsername.text.toString(),
                etPhoneNumber.text.toString(),
                etPassword.text.toString()
            )
        }
    }
}