package com.example.messenger.data.remote.repositories

import android.content.Context
import com.example.messenger.data.local.AppPreferences
import com.example.messenger.data.value_object.ConversationListVO
import com.example.messenger.data.value_object.ConversationVO
import com.example.messenger.service.MessageApiService
import com.example.messengerapi.repositories.ConversationRepository
import io.reactivex.Observable

class ConversationRepositoryImpl(context: Context) : ConversationRepository {
    private val preferences: AppPreferences = AppPreferences.create(context)
    private val service: MessageApiService = MessageApiService.getInstance()

    override fun findConversationById(id: Long): Observable<ConversationVO> {
        return service.showConversation(id, preferences.accessToken as String)
    }

    override fun all(): Observable<ConversationListVO> {
        return service.listConversations(preferences.accessToken as String)
    }
}