package com.example.messenger.login

import com.example.messenger.auth.AuthInteractor
import com.example.messenger.data.local.AppPreferences

interface LoginInteractor : AuthInteractor {
    interface OnDetailFinisheListener {
        fun onDetailSuccess()
        fun onDetailError()
    }

    fun login(
        username: String,
        password: String,
        listener: AuthInteractor.onAuthFineshedListener
    )

    fun retriveDetails(
        preferences: AppPreferences,
        listener: OnDetailFinisheListener
    )

}