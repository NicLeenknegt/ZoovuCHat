package com.zoovu.zuuvochat.domain.adapters.zoovu

import com.google.gson.Gson
import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.adapters.ModelAdapter
import com.zoovu.zuuvochat.utils.render_factory.RenderFactory
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject

class ZoovuAdapter:ModelAdapter {

    override fun fromResponseBody(response: ResponseBody): Model.Conversation {
        val data = JSONObject(response.string()).getJSONObject("data")
        val output = data.getJSONObject("output")

        return Model.Conversation(
            payload = data.getJSONObject("context"),
            messages = RenderFactory()
                .getType(output.getString("type"))
                .renderMessage(output)
                .build(),
            name = "none")
    }

    override fun toRequestBody(conversation: Model.Conversation): RequestBody {
        var data = JSONObject()
        data.put("input", JSONObject(Gson().toJson(conversation.userMessages()[0])))
        data.put("context", conversation.payload)
        return RequestBody.create(MediaType.parse("application/json"), data.toString())
    }
}