package com.example.messenger.sign

import com.example.messenger.auth.AuthInteractor

interface SignUpInteractor : AuthInteractor {
    interface OnSignUpListenerFinised {
        fun onSuccess()
        fun onUsernameError()
        fun onPasswordError()
        fun onPhoneNumberError()
        fun onError()
    }

    fun singUp(
        username: String,
        phoneNumber: String,
        password: String,
        listener: OnSignUpListenerFinised
    )

    fun getAuthtorization(
        listener: AuthInteractor.onAuthFineshedListener
    )
}