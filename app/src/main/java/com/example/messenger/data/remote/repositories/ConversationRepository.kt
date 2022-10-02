package com.example.messengerapi.repositories

import com.example.messenger.data.value_object.ConversationListVO
import com.example.messenger.data.value_object.ConversationVO
import io.reactivex.Observable


interface ConversationRepository  {
    fun findConversationById(id: Long): Observable<ConversationVO>
    fun all(): Observable<ConversationListVO>
}