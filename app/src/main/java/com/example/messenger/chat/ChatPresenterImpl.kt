package com.example.messenger.chat

import android.annotation.SuppressLint
import android.widget.Toast
import com.example.messenger.R
import com.example.messenger.data.value_object.ConversationVO
import java.text.SimpleDateFormat

class ChatPresenterImpl(val view: ChatView) : ChatPresenter,
    ChatIneractor.OnMessageSendFinishListener,
    ChatIneractor.OnMessageLoadFinishListener {
    private val interactor: ChatIneractor = ChatInteractorImpl(view.getContext())

    override fun onSendSuccess() {
        Toast.makeText(
            view.getContext(), R.string.message_sent,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onSendError() {
        view.showMessageSendError()
    }

    @SuppressLint("SimpleDateFormat")
    override fun onLoadSuccess(conversationVO: ConversationVO) {
        val adapter = view.getmessageListAdapter()
        val dateFormatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        conversationVO.message.forEach { mes ->
            adapter.addToStart(
                Message(
                    mes.senderId, mes.body,
                    dateFormatter.parse(mes.createAt.split(".")[0])
                ), true
            )
        }
    }

    override fun onLoadError() {
        view.showConversationLoaderError()
    }

    override fun sendMessage(recipentId: Long, message: String) {
        interactor.sendMessage(recipentId, message, this)
    }

    override fun loadMessage(conversationId: Long) {
        interactor.loadMessage(conversationId, this)
    }


}