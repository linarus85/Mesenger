package com.example.messenger.main

import android.content.Context
import com.example.messenger.data.local.AppPreferences
import com.example.messenger.data.remote.repositories.ConversationRepositoryImpl
import com.example.messenger.data.remote.repositories.UserRepository
import com.example.messenger.data.remote.repositories.UserRepositoryImpl
import com.example.messengerapi.repositories.ConversationRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainInteractorInpl(val context: Context) : MainInteractor {
    private val userRepository: UserRepository = UserRepositoryImpl(context)
    private val conversationRepository: ConversationRepository = ConversationRepositoryImpl(context)

    override fun loadContracts(listener: MainInteractor.OnContactFinishedListener) {
        userRepository.all()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res -> listener.onContactLoadSuccess(res) },
                { error ->
                    listener.onContactLoaderError()
                    error.printStackTrace()
                }
            )
    }

    override fun loadConversation(listener: MainInteractor.OnConversationFinishedListener) {
        conversationRepository.all()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res -> listener.onConversationLoadSuccess(res) },
                { error ->
                    listener.onConversationLoaderError()
                    error.printStackTrace()
                }
            )
    }

    override fun logout(listener: MainInteractor.OnLogoutFinishedListener) {
        val preferences: AppPreferences = AppPreferences.create(context)
        preferences.clear()
        listener.onLogoutSuccess()
    }
}