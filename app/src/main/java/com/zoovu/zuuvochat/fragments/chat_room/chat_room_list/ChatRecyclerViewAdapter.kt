package com.zoovu.zuuvochat.fragments.chat_room.chat_room_list

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zoovu.zuuvochat.R
import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.viewmodels.ConversationViewModel


class ChatRecyclerViewAdapter(
    private val converViewModel:ConversationViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context
): RecyclerView.Adapter<ChatRecyclerViewAdapter.MessageItemViewHolder>() {
    private var dataset:ArrayList<Model.Message> = arrayListOf()

    init {
        /*if (converViewModel.selectedConversation.value != null) {
            dataset = converViewModel.selectedConversation.value!!.messages
            this.notifyDataSetChanged()
        }*/
        converViewModel.selectedConversation.observe(lifecycleOwner, Observer {
            if (it != null) {
                dataset = ArrayList(it.messages.reversed())
                this.notifyDataSetChanged()
            }
        })
        /*dataset = arrayListOf(
            Model.Message("none","FIRST", false),
            Model.Message("none","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n", true),
            Model.Message("none","check", true),
            Model.Message("none","check", true),
            Model.Message("none","check", true),
            Model.Message("none","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n", false),
            Model.Message("none","check", true),
            Model.Message("none","check", true),
            Model.Message("none","check", true),
            Model.Message("none","check", true),
            Model.Message("none","check", false),
            Model.Message("none","check", true),
            Model.Message("none","check", true),
            Model.Message("none","check", false),
            Model.Message("none","check", true),
            Model.Message("none","check", true),
            Model.Message("none","check", true),
            Model.Message("none","check", true),
            Model.Message("none","check", true),
            Model.Message("none","check", true),
            Model.Message("none","check", true),
            Model.Message("none","check", true),
            Model.Message("none","check", true),
            Model.Message("none","check", true),
            Model.Message("none","check", true),
            Model.Message("none","check", true),
            Model.Message("none","check", true),
            Model.Message("none","LAST", true))*/
    }

    class MessageItemViewHolder(
        var view: ConstraintLayout
    ): RecyclerView.ViewHolder(view) {
        val userTextView = view.findViewById<TextView>(R.id.user_message_item_text)
        val userCardView = view.findViewById<CardView>(R.id.user_message_item_card)
        val replyTextView = view.findViewById<TextView>(R.id.reply_message_item_text)
        val replyCardView = view.findViewById<CardView>(R.id.reply_message_item_card)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_recycler_view_message_item, parent, false) as ConstraintLayout

        return MessageItemViewHolder(view)
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: MessageItemViewHolder, position: Int) {
        if (dataset[position].fromUser) {
            holder.userTextView.text = dataset[position].text
            holder.replyCardView.visibility = View.INVISIBLE
            holder.userCardView.visibility = View.VISIBLE
        } else {
            holder.replyTextView.text = dataset[position].text
            holder.userCardView.visibility = View.INVISIBLE
            holder.replyCardView.visibility = View.VISIBLE
        }

    }

}