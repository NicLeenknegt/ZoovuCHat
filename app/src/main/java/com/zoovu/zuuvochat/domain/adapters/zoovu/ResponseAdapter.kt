package com.zoovu.zuuvochat.domain.adapters.zoovu

import android.util.Log
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.zoovu.zuuvochat.domain.Model
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ResponseAdapter {

    @FromJson
    fun responseFromJson(jsonResponse: ZoovuModel.JsonResponse): Model.Conversation {
        var messages:ArrayList<Model.Message> = arrayListOf()
        val moshi = Moshi.Builder().build()
        when(jsonResponse.data.output?.type) {
            "TEXT" -> messages = ArrayList(jsonResponse.data.output?.text!!.map { Model.Message("none", it.toString()) })
            "BUTTON" -> {

                messages.add(Model.Message("none", jsonResponse.data.output?.buttonQuestion.toString(), jsonResponse.data.output?.isHorizontal!! ))
                for (it in jsonResponse.data.output?.text!!) {
                    val json = moshi.adapter(Map::class.java).toJson(it as Map<*, *>)
                    Log.d("API", json)
                    Log.d("API", moshi.adapter(ZoovuModel.Text::class.java).fromJson(json).toString())
                    if (moshi.adapter(ZoovuModel.Text::class.java).fromJson(json) != null) {
                        messages.add(Model.Message("none", moshi.adapter(ZoovuModel.Text::class.java).fromJson(json)!!.text, jsonResponse.data.output?.isHorizontal!! ))
                    }
                }
            }
        }

        return Model.Conversation(jsonResponse.data.context.conversationId, "none", messages)
    }

    @ToJson
    fun responseToJson(conversation: Model.Conversation):ZoovuModel.JsonResponse {
        var input = ZoovuModel.Input(
          //  SimpleDateFormat("yyyy-MM-ddTHH:mm:ss.SSSZ").format(Date()),
            conversation.messages.last().text
        )

        return ZoovuModel.JsonResponse(ZoovuModel.Data(ZoovuModel.Context(conversation.id), input = input))
    }
}