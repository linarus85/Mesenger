package com.example.messenger.main

import com.example.messenger.data.value_object.ConversationListVO
import com.example.messenger.data.value_object.UserListVO

interface MainInteractor {
    interface OnConversationFinishedListener{
        fun onConversationLoadSuccess(conversationListVO: ConversationListVO)
        fun onConversationLoaderError()
    }
    interface OnContactFinishedListener{
        fun onContactLoadSuccess(userListVO: UserListVO)
        fun onContactLoaderError()
    }
    interface OnLogoutFinishedListener{
        fun onLogoutSuccess()
    }
    fun loadContracts(listener: OnContactFinishedListener)
    fun loadConversation(listener: OnConversationFinishedListener)
    fun logout(listener: OnLogoutFinishedListener)
}