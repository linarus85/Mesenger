package com.example.messenger.chat

import android.content.Context
import com.example.messenger.chat.ChatIneractor
import com.example.messenger.data.local.AppPreferences
import com.example.messenger.data.remote.MessageRequestObject
import com.example.messenger.data.remote.repositories.ConversationRepositoryImpl
import com.example.messenger.service.MessageApiService
import com.example.messengerapi.repositories.ConversationRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChatInteractorImpl(context: Context):ChatIneractor {
    private val preferences:AppPreferences = AppPreferences.create(context)
    private val serveice:MessageApiService = MessageApiService.getInstance()
    private val conversationRepository: ConversationRepository = ConversationRepositoryImpl(context)

    override fun sendMessage(
        resepientId: Long,
        message: String,
        listener: ChatIneractor.OnMessageSendFinishListener
    ) {
      serveice.createMessage(MessageRequestObject(resepientId,message),
      preferences.accessToken as String)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe({ _ ->
              listener.onSendSuccess()
          }, { error ->
              listener.onSendError()
              error.printStackTrace()
          })
    }

    override fun loadMessage(
        conversationId: Long,
        listener: ChatIneractor.OnMessageLoadFinishListener
    ) {
        conversationRepository.findConversationById(conversationId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                listener.onLoadSuccess(res)
            }, { error ->
                listener.onLoadError()
                error.printStackTrace()
            })
    }

}