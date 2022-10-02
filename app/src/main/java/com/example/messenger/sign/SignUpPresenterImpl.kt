package com.example.messenger.sign

import com.example.messenger.auth.AuthInteractor
import com.example.messenger.data.local.AppPreferences
import com.example.messenger.login.LoginInteractor
import com.example.messenger.login.LoginInteractorImpl

class SignUpPresenterImpl(private val view: SignUpView) :
    SignUpPresenter, SignUpInteractor.OnSignUpListenerFinised,
    AuthInteractor.onAuthFineshedListener {

    private val interactor: SignUpInteractor = SignUpInteractorImpl()
    override var preferences: AppPreferences = AppPreferences.create(view.getContext())

    override fun onSuccess() {
        interactor.getAuthtorization(this)
    }

    override fun onAuthSuccess() {
       interactor.persistAccessToken(preferences)
       interactor.persistAccessDetails(preferences)
        view.hideProgress()
        view.navigateToHome()
    }

    override fun onAuthError() {
        view.hideProgress()
        view.showAuthError()
    }

    override fun onUsernameError() {
        view.hideProgress()
        view.setUsernameError()
    }

    override fun onPasswordError() {
        view.hideProgress()
        view.setPasswordError()
    }

    override fun onPhoneNumberError() {
        view.hideProgress()
        view.setPhoneNumberError()
    }

    override fun onError() {
        view.hideProgress()
        view.showSignUpError()
    }


    override fun executeSignUp(username: String, phoneNumber: String, password: String) {
        view.showProgress()
        interactor.singUp(username, phoneNumber, password, this)
    }
}