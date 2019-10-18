package com.zoovu.zuuvochat.fragments.chat_room.button_list

import android.content.Context
import android.service.autofill.Dataset
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.zoovu.zuuvochat.R
import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.Type
import com.zoovu.zuuvochat.domain.viewmodels.ConversationViewModel
import com.zoovu.zuuvochat.fragments.chat_room.ChatRoomController
import kotlinx.android.synthetic.main.chat_message_button.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class ButtonRecyclerViewAdapter(
    private val chatRoomController: ChatRoomController
): RecyclerView.Adapter<ButtonRecyclerViewAdapter.ButtonItemViewHolder>() {

    lateinit var dataset: ArrayList<Model.Button>

    class ButtonItemViewHolder(
        var view: ConstraintLayout
    ): RecyclerView.ViewHolder(view) {
        val button = view.chat_message_button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonItemViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.chat_message_button, parent, false) as ConstraintLayout

        return ButtonItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ButtonItemViewHolder, position: Int) {
        holder.button.text = dataset[position].text
        holder.button.onClick {
            chatRoomController.sendReply(
                Model.Message(
                    "none",
                    dataset[position].title,
                    Type.USER,
                    fromUser = true
                )
            )
        }
    }

}