package com.example.messenger.chat

import com.example.messenger.base.BaseView
import com.stfalcon.chatkit.messages.MessagesListAdapter

interface ChatView : BaseView {
    interface ChatAdapter {
        fun navigateToChat(
            recepientName: String, recepientId: Long,
            conversationId: Long? = null
        )
    }

    fun showConversationLoaderError()
    fun showMessageSendError()
    fun getmessageListAdapter(): MessagesListAdapter<Message>
}