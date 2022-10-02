package com.example.messenger.main

import com.example.messenger.data.value_object.ConversationListVO
import com.example.messenger.data.value_object.UserListVO

class MainPrasenterImpl(val view: MainView) : MainPrasenter,
    MainInteractor.OnConversationFinishedListener,
    MainInteractor.OnContactFinishedListener,
    MainInteractor.OnLogoutFinishedListener {
    private val interactor: MainInteractor = MainInteractorInpl(view.getContext())
    override fun onConversationLoadSuccess(conversationListVO: ConversationListVO) {
        if (!conversationListVO.conversations.isEmpty()) {
            val conversationFragment = view.getConversationFragment()
            val conversations = conversationFragment.conversations
            val adapter = conversationFragment.conversationAdapter
            conversations.clear()
            adapter.notifyDataSetChanged()
            conversationListVO.conversations.forEach { contact ->
                conversations.add(contact)
                adapter.notifyItemInserted(conversations.size - 1)
            }
        } else {
            view.showNoConversation()
        }
    }

    override fun onConversationLoaderError() {
        view.showConversationLoaderError()
    }

    override fun onContactLoadSuccess(userListVO: UserListVO) {
        val contractFragment = view.getContactFragment()
        val contacts = contractFragment.contacts
        val adapter = contractFragment.contactsAdapter
        contacts.clear()
        adapter.notifyDataSetChanged()
        userListVO.user.forEach { contact ->
            contacts.add(contact)
            contractFragment.contactsAdapter.notifyItemInserted(contacts.size - 1)
        }
    }


    override fun onContactLoaderError() {
        view.showContactLoaderError()
    }

    override fun onLogoutSuccess() {
        view.navigateToLog()
    }

    override fun loadConversation() {
        interactor.loadConversation(this)
    }

    override fun loadContact() {
        interactor.loadContracts(this)
    }

    override fun executLogout() {
        interactor.logout(this)
    }
}