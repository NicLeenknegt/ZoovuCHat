package com.zoovu.zuuvochat.fragments.chat_room.chat_room_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        var view: ConstraintLayout
    ): RecyclerView.ViewHolder(view) {
        val userTextView = view.findViewById<TextView>(R.id.user_message_item_text)
        val userCardView = view.findViewById<CardView>(R.id.user_message_item_card)
        val replyTextView = view.findViewById<TextView>(R.id.reply_message_item_text)
        val replyCardView = view.findViewById<CardView>(R.id.reply_message_item_card)
        val recyclerView = view.button_recycler_view as RecyclerView
        val imageView = view.reply_message_image as ImageView
        lateinit var verticalLayoutManager:LinearLayoutManager
        lateinit var horizontalLayoutManager:LinearLayoutManager
        lateinit var buttonRecyclerViewAdapter: ButtonRecyclerViewAdapter
        lateinit var imageRecyclerViewAdapter: ImageRecyclerViewAdapter

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_recycler_view_message_item, parent, false) as ConstraintLayout

        return MessageItemViewHolder(view)
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: MessageItemViewHolder, position: Int) {
        holder.verticalLayoutManager = LinearLayoutManager(context)
        holder.horizontalLayoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        holder.buttonRecyclerViewAdapter = ButtonRecyclerViewAdapter(lifecycleOwner as ChatRoomController)
        holder.imageRecyclerViewAdapter = ImageRecyclerViewAdapter(lifecycleOwner as ChatRoomFragment)
        RenderFactory(ViewHolderPicker())
            .setInput(Pair(dataset[position], holder))
            .selectViewHolders()
            .build()
        /*if (dataset[position].type == Type.MULTIPLE_IMAGES) {
            holder.replyCardView.visibility = View.GONE
            holder.userCardView.visibility = View.GONE
            holder.recyclerView.apply {
                layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
                adapter = ImageRecyclerViewAdapter(dataset[position].images, lifecycleOwner as ChatRoomFragment)
            }
            holder.recyclerView.visibility = View.VISIBLE
            holder.imageView.visibility = View.GONE
        } else if (dataset[position].fromUser) {
            holder.userTextView.text = dataset[position].text!!
            holder.replyCardView.visibility = View.INVISIBLE
            holder.userCardView.visibility = View.VISIBLE
            holder.recyclerView.visibility = View.GONE
            holder.imageView.visibility = View.GONE
        } else {
            if (dataset[position].text != null) {
                holder.replyTextView.text = dataset[position].text!!
                holder.replyTextView.visibility = View.VISIBLE
            } else {
                holder.replyTextView.visibility = View.GONE
            }
            holder.userCardView.visibility = View.INVISIBLE
            holder.replyCardView.visibility = View.VISIBLE
            holder.recyclerView.visibility = View.GONE
            holder.imageView.visibility = View.GONE
            if (dataset[position].type == Type.BUTTON_QUESTION) {
                val linerLayoutManager = if (dataset[position].isSpecial)
                    LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
                else
                    LinearLayoutManager(context)
                holder.recyclerView.apply {
                    layoutManager = linerLayoutManager
                    adapter = ButtonRecyclerViewAdapter(lifecycleOwner as ChatRoomController, dataset[position].buttons)
                }
                holder.recyclerView.visibility = View.VISIBLE
            } else if (dataset[position].type == Type.SINGLE_IMAGE) {
                Glide
                    .with(context)
                    .asBitmap()
                    .load(dataset[position].url)
                    .fitCenter()
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(4)))
                    .into(holder.imageView)

                holder.imageView.onClick {
                    (lifecycleOwner as ChatRoomFragment).zoomImageFromThumb(holder.imageView, dataset[position].url)
                }

                holder.imageView.visibility = View.VISIBLE
            }
        }
*/
    }

}