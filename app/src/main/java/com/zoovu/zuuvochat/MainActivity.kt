package com.zoovu.zuuvochat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
            .addToBackStack("tag")
            .commit()

        conViewModel.toastMessage.observe(this, Observer { it:String? ->
            if (it != null) {
                toast(it).show()
            }
        })

        conViewModel.navigationState.observe(this, Observer { it:String? ->
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
