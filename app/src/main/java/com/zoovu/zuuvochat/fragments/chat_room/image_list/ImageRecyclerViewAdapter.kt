package com.zoovu.zuuvochat.fragments.chat_room.image_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.zoovu.zuuvochat.R
import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.fragments.chat_room.ChatRoomController
import com.zoovu.zuuvochat.fragments.chat_room.ChatRoomFragment
import kotlinx.android.synthetic.main.chat_message_image.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class ImageRecyclerViewAdapter(
    private val fragment:ChatRoomFragment
):RecyclerView.Adapter<ImageRecyclerViewAdapter.ImageItemViewHolder>() {

    lateinit var dataset:ArrayList<Model.Image>

    class ImageItemViewHolder(
        var view:CardView
    ):RecyclerView.ViewHolder(view) {
        val image = view.card_view_image
        val text = view.card_view_text
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_message_image, parent, false) as CardView

        return ImageItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ImageItemViewHolder, position: Int) {
        Glide
            .with(fragment)
            .asBitmap()
            .load(dataset[position].url)
            .fitCenter()
            .placeholder(R.drawable.zoovu_logo)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(4)))
            .into(holder.image)

        holder.text.text = dataset[position].text

        holder.view.onClick {
            fragment.zoomImageFromThumb(holder.view, dataset[position].url)
        }
    }

}