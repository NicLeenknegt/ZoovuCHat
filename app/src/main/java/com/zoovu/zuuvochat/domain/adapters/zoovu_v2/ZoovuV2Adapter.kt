package com.zoovu.zuuvochat.domain.adapters.zoovu_v2

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.adapters.ModelAdapter
import com.zoovu.zuuvochat.utils.render_factory.RenderFactory
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject

class ZoovuV2Adapter(private val renderFactory: RenderFactory):ModelAdapter(renderFactory) {

    override fun fromResponseBody(response: ResponseBody): Model.Conversation {
        val data = JSONObject(response.string())
        if (data.has("body") && data.get("body") is JSONObject) {
            val body = data.getJSONObject("body")
            Log.d("JSON","${body.has("context")}")
            if (body.has("context")) {
                var conver = Model.Conversation(
                    payload = body.getJSONObject("context"),
                    messages = renderFactory
                        .setInput(body.getJSONArray("responses"))
                        .selectMessages()
                        .build(),
                    name = "none"
                )
                Log.d("JSON", "${conver.messages.size}")
                Log.d("JSON", "${conver.messages.isEmpty()}")

                return conver
            }
        }


        return Model.Conversation(
            payload = data,
            messages = arrayListOf(),
            name = "none"
        )
    }

    override fun toRequestBody(conversation: Model.Conversation): RequestBody {
        Log.d("API_CHECK",Gson().toJson(conversation))
        var data = JSONObject()
        var context = conversation.payload!!
        context.remove("inputMessage")
        context.put("inputMessage", "")
        data.put("context", context)
        Log.d("API_CHECK",Gson().toJson(data))
        return RequestBody.create(MediaType.parse("application/json"), data.toString())
    }
}