package com.zoovu.zuuvochat.utils.render_factory.render_picker

import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.fragments.chat_room.chat_room_list.ChatRecyclerViewAdapter
import com.zoovu.zuuvochat.utils.render_factory.render_types.view_type.ReplyButtonViewRender
import com.zoovu.zuuvochat.utils.render_factory.render_types.view_type.ReplyTextViewRender
import com.zoovu.zuuvochat.utils.render_factory.render_types.view_type.UserTextViewRender

class ViewHolderPicker:RenderPicker {
    override fun setInput(input: Any): RenderSelector? {
        if (input is Pair<*,*>) {
            input as Pair<Model.Message, ChatRecyclerViewAdapter.MessageItemViewHolder>
            return RenderSelector(
                UserTextViewRender(input),
                ReplyTextViewRender(input),
                ReplyButtonViewRender(input)
            )
        }
        return null
    }
}