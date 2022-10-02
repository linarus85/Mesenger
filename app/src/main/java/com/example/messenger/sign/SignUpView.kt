package com.example.messenger.sign

import com.example.messenger.auth.AuthView
import com.example.messenger.base.BaseView

interface SignUpView : BaseView, AuthView {
    fun showProgress()
    fun showSignUpError()
    fun hideProgress()
    fun setUsernameError()
    fun setPhoneNumberError()
    fun setPasswordError()
    fun navigateToHome()
}
