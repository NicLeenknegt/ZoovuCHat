package com.zoovu.zuuvochat.utils.render_factory.render_builder

import com.zoovu.zuuvochat.fragments.chat_room.chat_room_list.ChatRecyclerViewAdapter

class MessageItemViewHolderBuilder(private var holder:ChatRecyclerViewAdapter.MessageItemViewHolder): RenderBuilder<ChatRecyclerViewAdapter.MessageItemViewHolder> {

    private var builder:MessageItemViewHolderBuilder? = null

    override fun build(): ChatRecyclerViewAdapter.MessageItemViewHolder {
        if (builder != null) {
            builder!!.build()
        }
        return holder
    }

    override fun combine(renderBuilder: RenderBuilder<*>) {
        builder = renderBuilder as MessageItemViewHolderBuilder
    }
}