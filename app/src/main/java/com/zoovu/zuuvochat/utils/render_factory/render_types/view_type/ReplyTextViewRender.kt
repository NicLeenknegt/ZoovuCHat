package com.zoovu.zuuvochat.utils.render_factory.render_types.view_type

import android.view.View
import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.Type
import com.zoovu.zuuvochat.fragments.chat_room.chat_room_list.ChatRecyclerViewAdapter
import com.zoovu.zuuvochat.utils.render_factory.render_builder.MessageItemViewHolderBuilder
import com.zoovu.zuuvochat.utils.render_factory.render_builder.RenderBuilder
import com.zoovu.zuuvochat.utils.render_factory.render_types.RenderType

class ReplyTextViewRender(private var pair: Pair<Model.Message, ChatRecyclerViewAdapter.MessageItemViewHolder>):RenderType<ChatRecyclerViewAdapter.MessageItemViewHolder> {

    override fun render(): RenderBuilder<ChatRecyclerViewAdapter.MessageItemViewHolder> {
        var (message, holder) = pair
        if (message.type == Type.TEXT) {
            if (message.text != null) {
                holder.replyTextView.text = message.text!!
                holder.replyTextView.visibility = View.VISIBLE
            } else {
                holder.replyTextView.visibility = View.GONE
            }
            holder.userCardView.visibility = View.INVISIBLE
            holder.replyCardView.visibility = View.VISIBLE
            holder.recyclerView.visibility = View.GONE
            holder.imageView.visibility = View.GONE
        }
        return MessageItemViewHolderBuilder(holder)
    }

}