package com.example.messenger.login

import com.example.messenger.auth.AuthInteractor
import com.example.messenger.data.local.AppPreferences

class LoginPresenterIInpl(private val view: LoginView) :
    LoginPresenter, AuthInteractor.onAuthFineshedListener,
    LoginInteractor.OnDetailFinisheListener {
    private val interactor: LoginInteractor = LoginInteractorImpl()
    private val preferences: AppPreferences = AppPreferences.create(view.getContext())

    override fun onAuthSuccess() {
        interactor.persistAccessToken(preferences)
        interactor.retriveDetails(preferences, this)
    }

    override fun onAuthError() {
        view.showAuthError()
        view.hideProgress()
    }

    override fun onUsernameError() {
        view.hideProgress()
        view.setUsernameError()
    }

    override fun onPasswordError() {
        view.hideProgress()
        view.setPasswordError()
    }

    override fun onDetailSuccess() {
        interactor.persistAccessDetails(preferences)
        view.hideProgress()
        view.navigateToHome()
    }

    override fun onDetailError() {
        interactor.retriveDetails(preferences, this)
    }

    override fun excuteLogin(username: String, password: String) {
        view.showProgress()
        interactor.login(username, password, this)
    }
}