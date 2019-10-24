package com.zoovu.zuuvochat.utils.render_factory.render_types.view_type

import android.view.View
import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.Type
import com.zoovu.zuuvochat.fragments.chat_room.chat_room_list.ChatRecyclerViewAdapter
import com.zoovu.zuuvochat.utils.render_factory.render_builder.MessageItemViewHolderBuilder
import com.zoovu.zuuvochat.utils.render_factory.render_builder.RenderBuilder
import com.zoovu.zuuvochat.utils.render_factory.render_types.RenderType

class ReplyLoadingSpinnerRender(private var pair: Pair<Model.Message, ChatRecyclerViewAdapter.MessageItemViewHolder>):RenderType<ChatRecyclerViewAdapter.MessageItemViewHolder> {

    override fun render(): RenderBuilder<ChatRecyclerViewAdapter.MessageItemViewHolder> {
        var (message, holder) = pair
        if (message.type == Type.LOADING) {
            holder.loadingSpinner.visibility = View.VISIBLE
            holder.imageView.visibility = View.GONE
            holder.replyCardView.visibility = View.GONE
            holder.userCardView.visibility = View.GONE
            holder.recyclerView.visibility = View.GONE
        }
        return MessageItemViewHolderBuilder(holder)
    }
}