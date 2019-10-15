package com.zoovu.zuuvochat.domain.adapters

import com.zoovu.zuuvochat.domain.Model
import dagger.Module
import okhttp3.RequestBody
import okhttp3.ResponseBody

interface ModelAdapter {

    fun fromResponseBody(response:ResponseBody):Model.Conversation

    fun toRequestBody(conversation: Model.Conversation):RequestBody
}