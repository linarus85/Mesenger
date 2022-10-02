package com.example.messenger.login

import com.example.messenger.auth.AuthInteractor
import com.example.messenger.data.local.AppPreferences
import com.example.messenger.data.remote.LoginRequestObject
import com.example.messenger.data.value_object.UserVO
import com.example.messenger.service.MessageApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LoginInteractorImpl : LoginInteractor {

    override lateinit var userDetail: UserVO
    override lateinit var accessToken: String
    override lateinit var sumbittedUsername: String
    override lateinit var sumbittedPassword: String

    private val service: MessageApiService = MessageApiService.getInstance()


    override fun login(
        username: String,
        password: String,
        listener: AuthInteractor.onAuthFineshedListener
    ) {
        when {
            username.isBlank() -> listener.onUsernameError()
            password.isBlank() -> listener.onPasswordError()
            else -> {
                sumbittedUsername = username
                sumbittedPassword = password
                val requestObject = LoginRequestObject(username, password)
                service.login(requestObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ res ->
                        if (res.code() != 403) {
                            accessToken = res.headers()["Authorization"] as String
                            listener.onAuthSuccess()
                        } else {
                            listener.onAuthError()
                        }
                    }, { error ->
                        listener.onAuthError()
                        error.printStackTrace()
                    })
            }
        }
    }

    override fun retriveDetails(
        preferences: AppPreferences,
        listener: LoginInteractor.OnDetailFinisheListener
    ) {
        service.echoDetails(preferences.accessToken as String)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
            userDetail=res
                listener.onDetailSuccess()
            }, { error ->
                listener.onDetailError()
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