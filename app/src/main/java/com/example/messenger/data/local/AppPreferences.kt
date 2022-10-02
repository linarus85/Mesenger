package com.example.messenger.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.messenger.data.value_object.UserListVO
import com.example.messenger.data.value_object.UserVO

class AppPreferences private constructor() {
    private lateinit var preferences: SharedPreferences

    companion object {
        private const val PREFERENCES_FILE_NAME = "APP_PREFERENCES"
        fun create(context: Context): AppPreferences {
            val appPreferences = AppPreferences()
            appPreferences.preferences = context.getSharedPreferences(
                PREFERENCES_FILE_NAME,
                0
            )
            return appPreferences
        }
    }

    val accessToken: String?
        get() = preferences.getString("ACCESS_TOKEN", "")

    fun storeAccessToken(accessToken: String) {
        preferences.edit().putString("ACCESS_TOKEN", accessToken).apply()
    }

    val userDetails: UserVO
        get() : UserVO {
            return UserVO(
                preferences.getLong("ID", 0),
                preferences.getString("USERNAME", null).toString(),
                preferences.getString("PHONE_NUMBER", null).toString(),
                preferences.getString("STATUS", null).toString(),
                preferences.getString("CREATE_AT", null).toString(),
            )
        }

    fun storeUserDetails(user: UserVO) {
        preferences.edit().putLong("ID", user.id).apply()
        preferences.edit().putString("USERNAME", user.username).apply()
        preferences.edit().putString("PHONE_NUMBER", user.phoneNumber).apply()
        preferences.edit().putString("STATUS", user.status).apply()
        preferences.edit().putString("CREATE_AT", user.createAt).apply()
    }
    fun clear(){
        preferences.edit().clear().apply()
    }


}