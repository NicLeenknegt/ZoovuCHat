package com.zoovu.zuuvochat.fragments.chat_room

import android.view.View
import androidx.fragment.app.Fragment
import com.zoovu.zuuvochat.domain.Model

abstract class ChatRoomController:Fragment() {
    abstract fun sendReply(message: Model.Message)
   abstract fun zoomImageFromThumb(imageView: View, url:String)
}