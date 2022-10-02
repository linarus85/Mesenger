package com.example.messenger

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.messenger.chat.ChatPresenter
import com.example.messenger.chat.ChatPresenterImpl
import com.example.messenger.chat.ChatView
import com.example.messenger.chat.Message
import com.example.messenger.data.local.AppPreferences
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessagesList
import com.stfalcon.chatkit.messages.MessagesListAdapter
import java.util.*

class ChatActivity : AppCompatActivity(), ChatView,
    MessageInput.InputListener {
    private var recipientId: Long = -1
    private lateinit var messageList: MessagesList
    private lateinit var messaageInput: MessageInput
    private lateinit var preferences: AppPreferences
    private lateinit var presenter: ChatPresenter
    private lateinit var messageListAdapter: MessagesListAdapter<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("RECIPIENT_NAME")
        preferences = AppPreferences.create(this)
        messageListAdapter = MessagesListAdapter(
            preferences.userDetails.id.toString(),
            null
        )
        presenter = ChatPresenterImpl(this)
        bindView()
        val conversationId = intent.getLongExtra("CONVERSATION_ID", -1)
        recipientId = intent.getLongExtra("RECIPIENT_ID", -1)
        if (conversationId != -1L) {
            presenter.loadMessage(conversationId)
        }

    }

    override fun showConversationLoaderError() {
        Toast.makeText(
            this, R.string.Unable_load_,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun showMessageSendError() {
        Toast.makeText(
            this, R.string.Unable_load_,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId==android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun getmessageListAdapter(): MessagesListAdapter<Message> {
        return messageListAdapter
    }

    override fun bindView() {
        messageList = findViewById(R.id.message_list)
        messaageInput = findViewById(R.id.message_input)
        messageList.setAdapter(messageListAdapter)
        messageList.setAdapter(messageListAdapter)
        messaageInput.setInputListener(this)
    }

    override fun getContext(): Context {
        return this
    }

    override fun onSubmit(input: CharSequence?): Boolean {
        messageListAdapter.addToStart(
            Message(
                preferences.userDetails.id,
                input.toString(),
                Date()
            ),
            true
        )
        presenter.sendMessage(recipientId, input.toString())
        return true
    }

}