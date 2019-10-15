package com.zoovu.zuuvochat.domain

import org.json.JSONObject

class Model {
    data class Conversation(
        var id:String? = null,
        @Transient
        var payload: JSONObject? = null,
        var name:String,
        @Transient
        var messages:ArrayList<Message>
    ) {
        fun userMessages():ArrayList<Message> {
            var replies:ArrayList<Message> = arrayListOf()

            for (message in this.messages.reversed()) {
                if (message.fromUser)
                    replies.add(message)
                else
                    return replies
            }
            return  replies
        }
    }

    data class Message(
        var id: String,
        var text: String,
        var isSpecial:Boolean = false,
        var fromUser:Boolean = false
    )


}