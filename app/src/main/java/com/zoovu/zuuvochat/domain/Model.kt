package com.zoovu.zuuvochat.domain

class Model {
    data class Conversation(
        var id: String,
        var name:String,
        @Transient
        var messages:ArrayList<Message>
    )

    data class Message(
        var id: String,
        var text: String,
        var isSpecial:Boolean = false,
        var fromUser:Boolean = false
    )


}