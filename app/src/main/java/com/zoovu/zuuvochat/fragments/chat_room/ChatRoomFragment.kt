package com.zoovu.zuuvochat.fragments.chat_room


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoovu.zuuvochat.MainActivity

import com.zoovu.zuuvochat.R
import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.viewmodels.ConversationViewModel
import com.zoovu.zuuvochat.fragments.chat_room.chat_room_list.ChatRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_chat_room.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 *
 */
class ChatRoomFragment : Fragment() {

    private lateinit var viewManager:LinearLayoutManager
    private lateinit var viewAdapter:ChatRecyclerViewAdapter
    private lateinit var conViewModel:ConversationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.run {
            conViewModel = ViewModelProviders.of(this).get(ConversationViewModel::class.java)
        }?: throw Exception("Invalid activity.")
        return inflater.inflate(R.layout.fragment_chat_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewManager  = LinearLayoutManager(activity as MainActivity)
        viewAdapter = ChatRecyclerViewAdapter(conViewModel, this, activity as MainActivity)
        viewManager.reverseLayout = true

        chat_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        message_send_button.onClick {
            if (message_text_input.text.toString().isNotEmpty()) {
                conViewModel.sendReply(Model.Message("none", message_text_input.text.toString(), fromUser = true))
                message_text_input.text.clear()
            }
        }
    }

}
