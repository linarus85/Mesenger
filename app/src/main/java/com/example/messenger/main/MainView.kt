package com.example.messenger.main

import com.example.messenger.MainActivity
import com.example.messenger.base.BaseView

interface MainView : BaseView{
    fun showConversationLoaderError()
    fun showContactLoaderError()
    fun showConversationScreen()
    fun showContactsScreen()
    fun getContactFragment(): MainActivity.ContactFragment
    fun getConversationFragment():MainActivity.ConversationFragment
    fun showNoConversation()
    fun navigateToLog()
    fun navigateToSettings()
}