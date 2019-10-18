package com.zoovu.zuuvochat.utils.render_factory.render_types.view_type

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.Type
import com.zoovu.zuuvochat.fragments.chat_room.button_list.ButtonRecyclerViewAdapter
import com.zoovu.zuuvochat.fragments.chat_room.chat_room_list.ChatRecyclerViewAdapter
import com.zoovu.zuuvochat.utils.render_factory.render_builder.MessageItemViewHolderBuilder
import com.zoovu.zuuvochat.utils.render_factory.render_builder.RenderBuilder
import com.zoovu.zuuvochat.utils.render_factory.render_types.RenderType

class ReplyButtonViewRender(private var pair: Pair<Model.Message, ChatRecyclerViewAdapter.MessageItemViewHolder>):RenderType<ChatRecyclerViewAdapter.MessageItemViewHolder> {

    override fun render(): RenderBuilder<ChatRecyclerViewAdapter.MessageItemViewHolder> {
        var (message, holder) = pair
        if (message.type == Type.BUTTON_QUESTION) {
            holder.buttonRecyclerViewAdapter.dataset = message.buttons
            if (message.text != null) {
                holder.replyTextView.text = message.text!!
                holder.replyTextView.visibility = View.VISIBLE
            } else {
                holder.replyTextView.visibility = View.GONE
            }
            holder.userCardView.visibility = View.INVISIBLE
            holder.replyCardView.visibility = View.VISIBLE
            holder.imageView.visibility = View.GONE
            val linerLayoutManager = if (message.isSpecial)
                holder.horizontalLayoutManager
            else
                holder.verticalLayoutManager
            holder.recyclerView.apply {
                layoutManager = linerLayoutManager
                adapter = holder.buttonRecyclerViewAdapter
            }
            holder.recyclerView.visibility = View.VISIBLE
        }

        return MessageItemViewHolderBuilder(holder)
    }
}