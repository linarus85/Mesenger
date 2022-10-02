package com.example.messenger.chat

import com.example.messenger.data.value_object.ConversationVO

interface ChatIneractor {
    interface OnMessageSendFinishListener {
        fun onSendSuccess()
        fun onSendError()
    }

    interface OnMessageLoadFinishListener {
        fun onLoadSuccess(conversationVO: ConversationVO)
        fun onLoadError()
    }

    fun sendMessage(
        resepientId: Long,
        message: String,
        listener: OnMessageSendFinishListener
    )
    fun loadMessage(
        conversationId: Long,
        listener: OnMessageLoadFinishListener
    )

}