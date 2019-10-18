package com.zoovu.zuuvochat.utils.render_factory.render_types.view_type

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.Type
import com.zoovu.zuuvochat.fragments.chat_room.chat_room_list.ChatRecyclerViewAdapter
import com.zoovu.zuuvochat.utils.render_factory.render_builder.MessageItemViewHolderBuilder
import com.zoovu.zuuvochat.utils.render_factory.render_builder.RenderBuilder
import com.zoovu.zuuvochat.utils.render_factory.render_types.RenderType
import org.jetbrains.anko.sdk27.coroutines.onClick

class ReplySingleImageViewRender(private var pair: Pair<Model.Message, ChatRecyclerViewAdapter.MessageItemViewHolder>):RenderType<ChatRecyclerViewAdapter.MessageItemViewHolder> {

    override fun render(): RenderBuilder<ChatRecyclerViewAdapter.MessageItemViewHolder> {
        val (message, holder) = pair
        if (message.type == Type.SINGLE_IMAGE) {
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
            Glide
                .with(holder.context)
                .asBitmap()
                .load(message.url)
                .fitCenter()
                .apply(RequestOptions.bitmapTransform(RoundedCorners(4)))
                .into(holder.imageView)

            holder.imageView.onClick {
                holder.chatRoomController.zoomImageFromThumb(holder.imageView, message.url)
            }

            holder.imageView.visibility = View.VISIBLE
        }
        return MessageItemViewHolderBuilder(holder)
    }

}