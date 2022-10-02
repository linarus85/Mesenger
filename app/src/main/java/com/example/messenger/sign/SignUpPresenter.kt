package com.example.messenger.sign

import com.example.messenger.data.local.AppPreferences

interface SignUpPresenter {
    var preferences: AppPreferences
    fun executeSignUp(username: String, phoneNumber: String, password: String)
}