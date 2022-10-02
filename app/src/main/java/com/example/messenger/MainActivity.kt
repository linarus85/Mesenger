package com.example.messenger

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.chat.ChatView
import com.example.messenger.data.local.AppPreferences
import com.example.messenger.data.value_object.ConversationVO
import com.example.messenger.data.value_object.UserVO
import com.example.messenger.main.MainPrasenter
import com.example.messenger.main.MainPrasenterImpl
import com.example.messenger.main.MainView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), MainView,
    View.OnClickListener {
    private lateinit var linContainer: LinearLayout
    private lateinit var presenter: MainPrasenter
    private val contactsFragment = ContactFragment()
    private val conversationFragment = ConversationFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPrasenterImpl(this)
        conversationFragment.setActivity(this)
        contactsFragment.setActivity(this)
        bindView()
        showConversationScreen()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home -> showConversationScreen()
            R.id.action_settings -> navigateToSettings()
            R.id.action_logout -> presenter.executLogout()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showConversationLoaderError() {
        Toast.makeText(
            this, R.string.Unable_load_,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun showContactLoaderError() {
        Toast.makeText(
            this, R.string.Unable_load_,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun showConversationScreen() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.lin_container, conversationFragment)
        fragmentTransaction.commit()
        presenter.loadConversation()
        supportActionBar?.title = "${R.string.app_name}"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun showContactsScreen() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.lin_container, contactsFragment)
        fragmentTransaction.commit()
        presenter.loadConversation()
        supportActionBar?.title = "${R.string.Contacts}"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }


    override fun getContactFragment(): ContactFragment {
        return contactsFragment
    }

    override fun getConversationFragment(): ConversationFragment {
        return conversationFragment
    }

    override fun showNoConversation() {
        Toast.makeText(
            this, R.string.not_active_conversation,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun navigateToLog() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun navigateToSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    override fun bindView() {
        linContainer = findViewById(R.id.lin_container)
    }

    override fun getContext(): Context {
        return this
    }

    class ConversationFragment : Fragment(),
        View.OnClickListener {
        private lateinit var activity: MainActivity
        private lateinit var rvConversation: RecyclerView
        private lateinit var flBtnContacts: FloatingActionButton
        var conversations: ArrayList<ConversationVO> = ArrayList()
        lateinit var conversationAdapter: ConversationAdapter


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val baseLayout = inflater.inflate(
                R.layout.fragment_conversation,
                container, false
            )
            rvConversation = baseLayout.findViewById(R.id.rv_conversation)
            flBtnContacts = baseLayout.findViewById(R.id.flbtn_contacts)
            conversationAdapter = ConversationAdapter(requireActivity(), conversations)
            rvConversation.adapter = conversationAdapter
            rvConversation.layoutManager = LinearLayoutManager(getActivity()?.baseContext)
            flBtnContacts.setOnClickListener(this)
            return baseLayout
        }

        override fun onClick(v: View) {
            if (view?.id == R.id.flbtn_contacts) {
                this.activity.showContactsScreen()
            }
        }

        fun setActivity(activity: MainActivity) {
            this.activity = activity
        }

        class ConversationAdapter(
            private val context: Context,
            private val dataSet: List<ConversationVO>
        ) : RecyclerView.Adapter<ConversationAdapter.ViewHolder>(),
            ChatView.ChatAdapter {
            class ViewHolder(val itemLoyaut: LinearLayout) :
                RecyclerView.ViewHolder(itemLoyaut)

            val preferences: AppPreferences = AppPreferences.create(context)
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val itemLoyaut = LayoutInflater.from(parent.context)
                    .inflate(R.layout.vh_conversation, parent, false)
                    .findViewById<LinearLayout>(R.id.lin_container)
                return ViewHolder(itemLoyaut)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val item = dataSet[position]
                val itemLoyaut = holder.itemLoyaut
                itemLoyaut.findViewById<TextView>(R.id.tv_username).text =
                    item.secandaryUsername
                itemLoyaut.findViewById<TextView>(R.id.tv_preview).text =
                    item.message[item.message.size - 1].body
                itemLoyaut.setOnClickListener {
                    val message = item.message[0]
                    val recepientId = if (message.senderId == preferences.userDetails.id) {
                        message.recipientId
                    } else {
                        message.senderId
                    }
                    navigateToChat(item.secandaryUsername, recepientId, item.conversionId)
                }
            }

            override fun navigateToChat(
                secandaryUsername: String,
                recepientId: Long,
                conversionId: Long?
            ) {
                val intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("CONVERSATION_ID", conversionId)
                intent.putExtra("RECIPIENT_ID", recepientId)
                intent.putExtra("RECIPIENT_NAME", secandaryUsername)
                context.startActivity(intent)
            }

            override fun getItemCount(): Int {
                return dataSet.size
            }


        }
    }


    class ContactFragment : Fragment() {
        private lateinit var activity: MainActivity
        private lateinit var rvContacts: RecyclerView
        var contacts: ArrayList<UserVO> = ArrayList()
         lateinit  var contactsAdapter : ContactsAdapter


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val baseLayout = inflater.inflate(
                R.layout.fragment_contacts,
                container, false
            )
            rvContacts = baseLayout.findViewById(R.id.rv_contacts)
            contactsAdapter = ContactsAdapter(requireActivity(), contacts)
            rvContacts.adapter = contactsAdapter
            rvContacts.layoutManager = LinearLayoutManager(getActivity()?.baseContext)
            return baseLayout
        }

        fun setActivity(activity: MainActivity) {
            this.activity = activity
        }

        class ContactsAdapter(
            private val context: Context,
            private val dataSet: List<UserVO>
        ) :
            RecyclerView.Adapter<ContactsAdapter.ViewHolder>(),
            ChatView.ChatAdapter {
            class ViewHolder(val itemLoyaut: LinearLayout) :
                RecyclerView.ViewHolder(itemLoyaut)

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val itemLoyaut = LayoutInflater.from(parent.context)
                    .inflate(R.layout.vh_contacts, parent, false)
                val linContainer = itemLoyaut.findViewById<LinearLayout>(R.id.lin_container)
                return ViewHolder(linContainer)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val item = dataSet[position]
                val itemLoyaut = holder.itemLoyaut
                itemLoyaut.findViewById<TextView>(R.id.tv_username).text =
                    item.username
                itemLoyaut.findViewById<TextView>(R.id.tv_phone).text =
                    item.phoneNumber
                itemLoyaut.findViewById<TextView>(R.id.tv_status).text =
                    item.status
                itemLoyaut.setOnClickListener {
                    navigateToChat(item.username, item.id)
                }
            }


            override fun getItemCount(): Int {
                return dataSet.size
            }

            override fun navigateToChat(
                recepientName: String,
                recepientId: Long,
                conversationId: Long?
            ) {
                val intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("RECIPIENT_ID", recepientId)
                intent.putExtra("USER_NAME", recepientName)
                context.startActivity(intent)
            }


        }
    }


    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}