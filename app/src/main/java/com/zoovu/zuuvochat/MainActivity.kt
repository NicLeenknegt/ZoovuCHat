package com.zoovu.zuuvochat

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.zoovu.zuuvochat.domain.viewmodels.ConversationViewModel
import com.zoovu.zuuvochat.fragments.chat_room.ChatRoomFragment
import com.zoovu.zuuvochat.fragments.conversation_list.ConversationListFragment
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private lateinit var fragment: Fragment
    private lateinit var conViewModel: ConversationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        conViewModel = ViewModelProviders.of(this).get(ConversationViewModel::class.java)
        fragment = ConversationListFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag_con, fragment)
            .addToBackStack(null)
            .commit()

        conViewModel.toastMessage.observe(this, Observer {
            if (it != null) {
                toast(it).show()
            }
        })

        conViewModel.navigationState.observe(this, Observer {
            if (it != null) {
                when(it) {
                    "CHAT_ROOM"-> {
                        fragment = ChatRoomFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frag_con, fragment)
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }
        })

    }
}
