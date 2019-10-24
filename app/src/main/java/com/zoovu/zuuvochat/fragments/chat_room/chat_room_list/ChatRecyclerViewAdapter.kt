package com.zoovu.zuuvochat.fragments.chat_room.chat_room_list

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.github.ybq.android.spinkit.SpinKitView
import com.google.gson.Gson
import com.zoovu.zuuvochat.R
import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.Type
import com.zoovu.zuuvochat.domain.viewmodels.ConversationViewModel
import com.zoovu.zuuvochat.fragments.chat_room.ChatRoomController
import com.zoovu.zuuvochat.fragments.chat_room.ChatRoomFragment
import com.zoovu.zuuvochat.fragments.chat_room.button_list.ButtonRecyclerViewAdapter
import com.zoovu.zuuvochat.fragments.chat_room.image_list.ImageRecyclerViewAdapter
import com.zoovu.zuuvochat.utils.render_factory.RenderFactory
import com.zoovu.zuuvochat.utils.render_factory.render_picker.ViewHolderPicker
import kotlinx.android.synthetic.main.chat_recycler_view_message_item.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick


class ChatRecyclerViewAdapter(
    private val converViewModel:ConversationViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context
): RecyclerView.Adapter<ChatRecyclerViewAdapter.MessageItemViewHolder>() {
    private var dataset:ArrayList<Model.Message> = arrayListOf()



    init {
        converViewModel.selectedConversation.observe(lifecycleOwner, Observer {
            if (it != null) {
                dataset = ArrayList(it.messages.reversed())
                this.notifyDataSetChanged()
            }
        })
    }

    class MessageItemViewHolder(
        var view: ConstraintLayout,
        var context: Context,
        lifecycleOwner: LifecycleOwner
    ): RecyclerView.ViewHolder(view) {
        val userTextView:TextView = view.user_message_item_text
        val userCardView:CardView = view.user_message_item_card
        val replyTextView:TextView = view.reply_message_item_text
        val replyCardView:CardView = view.reply_message_item_card
        val recyclerView:RecyclerView = view.button_recycler_view
        val imageView:ImageView = view.reply_message_image
        var loadingSpinner:SpinKitView = view.loading_spinner
        var verticalLayoutManager = LinearLayoutManager(context)
        var horizontalLayoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        var buttonRecyclerViewAdapter = ButtonRecyclerViewAdapter(lifecycleOwner as ChatRoomController)
        var imageRecyclerViewAdapter = ImageRecyclerViewAdapter(lifecycleOwner as ChatRoomFragment)
        var chatRoomController = lifecycleOwner as ChatRoomController
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_recycler_view_message_item, parent, false) as ConstraintLayout

        return MessageItemViewHolder(view, context, lifecycleOwner)
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: MessageItemViewHolder, position: Int) {
        RenderFactory(ViewHolderPicker())
            .setInput(Pair(dataset[position], holder))
            .selectViewHolders()
            .build()
    }

    override fun onViewDetachedFromWindow(holder: MessageItemViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.view.clearAnimation()
    }

}