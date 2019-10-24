package com.zoovu.zuuvochat.utils.render_factory.render_picker

import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.fragments.chat_room.chat_room_list.ChatRecyclerViewAdapter
import com.zoovu.zuuvochat.utils.render_factory.render_types.view_type.*

class ViewHolderPicker:RenderPicker {
    override fun setInput(input: Any): RenderSelector? {
        if (input is Pair<*,*>) {
            val (firstPairType, secondPairType) = input
            if (firstPairType is Model.Message && secondPairType is ChatRecyclerViewAdapter.MessageItemViewHolder) {
                input as Pair<Model.Message, ChatRecyclerViewAdapter.MessageItemViewHolder>
                return RenderSelector(
                    ReplyMultipleImagesViewRender(input),
                    UserTextViewRender(input),
                    ReplyTextViewRender(input),
                    ReplyButtonViewRender(input),
                    ReplySingleImageViewRender(input),
                    ReplyLoadingSpinnerRender(input)
                )
            }
            return null

        }
        return null
    }
}