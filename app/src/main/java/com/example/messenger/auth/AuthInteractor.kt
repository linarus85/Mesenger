package com.example.messenger.auth

import com.example.messenger.data.local.AppPreferences
import com.example.messenger.data.value_object.UserVO

interface AuthInteractor {
    var userDetail: UserVO
    var accessToken: String
    var sumbittedUsername: String
    var sumbittedPassword: String

    interface onAuthFineshedListener {
        fun onAuthSuccess()
        fun onAuthError()
        fun onUsernameError()
        fun onPasswordError()
    }

    fun persistAccessToken(preferences: AppPreferences)
    fun persistAccessDetails(preferences: AppPreferences)
}
