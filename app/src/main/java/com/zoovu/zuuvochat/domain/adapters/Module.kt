package com.zoovu.zuuvochat.domain.adapters

interface Module {
    fun getConversationStartPath(id:String):String
    fun getSendReplyPath(id:String):String
    fun getModuleAdapter():ModelAdapter
    fun getAPIUrl():String
}