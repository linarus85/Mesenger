package com.example.messenger.sign

import android.text.TextUtils
import com.example.messenger.auth.AuthInteractor
import com.example.messenger.data.local.AppPreferences
import com.example.messenger.data.remote.LoginRequestObject
import com.example.messenger.data.remote.UserRequestObject
import com.example.messenger.data.value_object.UserVO
import com.example.messenger.service.MessageApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignUpInteractorImpl : SignUpInteractor {


    override lateinit var userDetail: UserVO
    override lateinit var accessToken: String
    override lateinit var sumbittedUsername: String
    override lateinit var sumbittedPassword: String
    private val service: MessageApiService = MessageApiService.getInstance()


    override fun singUp(
        username: String,
        phoneNumber: String,
        password: String,
        listener: SignUpInteractor.OnSignUpListenerFinised
    ) {
        sumbittedUsername = username
        sumbittedPassword = password
        val userRequest = UserRequestObject(username, password, phoneNumber)
        when {
            TextUtils.isEmpty(username) -> listener.onUsernameError()
            TextUtils.isEmpty(phoneNumber) -> listener.onPhoneNumberError()
            TextUtils.isEmpty(password) -> listener.onPasswordError()
            else -> {
                service.createUser(userRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ res ->
                        userDetail = res
                        listener.onSuccess()
                    }, { error ->
                        listener.onError()
                        error.printStackTrace()
                    })
            }
        }
    }

    override fun getAuthtorization(listener: AuthInteractor.onAuthFineshedListener) {
        val userRequestObject = LoginRequestObject(sumbittedUsername, sumbittedPassword)
        service.login(userRequestObject)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                accessToken = res.headers()["Authorization"] as String
                listener.onAuthSuccess()
            }, { error ->
                listener.onAuthError()
                error.printStackTrace()
            })
    }


    override fun persistAccessToken(preferences: AppPreferences) {
        preferences.storeAccessToken(accessToken)
    }

    override fun persistAccessDetails(preferences: AppPreferences) {
        preferences.storeUserDetails(userDetail)
    }
}