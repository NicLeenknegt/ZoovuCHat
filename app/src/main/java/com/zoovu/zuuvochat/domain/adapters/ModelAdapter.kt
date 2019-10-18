package com.zoovu.zuuvochat.domain.adapters

import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.utils.render_factory.RenderFactory
import dagger.Module
import okhttp3.RequestBody
import okhttp3.ResponseBody

abstract class ModelAdapter(private val renderFactory: RenderFactory?) {

    abstract fun fromResponseBody(response:ResponseBody):Model.Conversation

    abstract fun toRequestBody(conversation: Model.Conversation):RequestBody
}