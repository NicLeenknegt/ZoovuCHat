package com.zoovu.zuuvochat.domain

import org.json.JSONObject

class Model {
    data class Conversation(
        var id:String? = null,
        @Transient
        var payload: JSONObject? = null,
        var name:String,
        @Transient
        var messages:ArrayList<Message>,
        @Transient
        var newMessages:ArrayList<Message> = arrayListOf()
    ) {
        fun userMessages():ArrayList<Message> {
            var replies:ArrayList<Message> = arrayListOf()

            for (message in this.messages.reversed()) {
                if (message.type == Type.USER_TEXT)
                    replies.add(message)
                else
                    return replies
            }
            return  replies
        }
    }

    data class Message(
        var id: String,
        var text: String?,
        var type:Type,
        var isSpecial:Boolean = false,
        var buttons:ArrayList<Button> = arrayListOf(),
        var images:ArrayList<Image> = arrayListOf(),
        var url:String = "none"
    )

    data class Button(
        var title:String,
        var text:String
    )

    data class Image(
        var text:String,
        var url:String
    )

}

enum class Type {
    USER_TEXT,
    USER_BUTTON_REPLY,
    LOADING,
    TEXT,
    BUTTON_QUESTION,
    SINGLE_IMAGE,
    MULTIPLE_IMAGES,
    POPUP
}