package com.zoovu.zuuvochat.domain.adapters.zoovu

import android.util.Log
import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.adapters.ModelAdapter
import com.zoovu.zuuvochat.utils.render_factory.RenderFactory
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject

class ZoovuAdapter(private val renderFactory: RenderFactory):ModelAdapter(renderFactory) {

    override fun fromResponseBody(response: ResponseBody): Model.Conversation {
        val data = JSONObject(response.string()).getJSONObject("data")
        val output = data.getJSONObject("output")
        return Model.Conversation(
            payload = data.getJSONObject("context"),
            messages = renderFactory
                .setInput(output)
                .selectMessages()
                .build(),
            name = "none")
    }

    override fun toRequestBody(conversation: Model.Conversation): RequestBody {
        var data = JSONObject()
        var text = JSONObject()
        text.put("text", conversation.userMessages()[0].text!!)
        data.put("input", text)
        data.put("context", conversation.payload)
        Log.d("REPLY_CHECK",data.toString())
        return RequestBody.create(MediaType.parse("application/json"), data.toString())
    }
}