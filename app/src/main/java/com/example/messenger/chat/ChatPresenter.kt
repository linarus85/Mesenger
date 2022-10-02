package com.example.messenger.chat

interface ChatPresenter {
    fun sendMessage(recipientId: Long, message: String)
    fun loadMessage(conversationId: Long)
}