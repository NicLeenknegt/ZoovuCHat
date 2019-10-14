package com.zoovu.zuuvochat.domain.adapters.zoovu

import android.util.Log
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.zoovu.zuuvochat.domain.Model
import okhttp3.ResponseBody
import org.json.JSONObject


class ResponseAdapter {

    @FromJson
    fun responseFromJson(response: ResponseBody): Model.Conversation {
        var json = JSONObject(response.toString())
        Log.d("STRING_CHECK",json.getJSONObject("data").getJSONObject("output").getString("type"))
        return Model.Conversation(name = "none", messages = arrayListOf())
        /*var messages:ArrayList<Model.Message> = arrayListOf()
        val moshi = Moshi.Builder().build()
        when(response.data.output?.type) {
            "TEXT" -> messages = ArrayList(response.data.output?.text!!.map { Model.Message("none", it.toString()) })
            "BUTTON" -> {

                messages.add(Model.Message("none", response.data.output?.buttonQuestion.toString(), response.data.output?.isHorizontal!! ))
                for (it in response.data.output?.text!!) {
                    val json = moshi.adapter(Map::class.java).toJson(it as Map<*, *>)
                    Log.d("API", json)
                    Log.d("API", moshi.adapter(ZoovuModel.Text::class.java).fromJson(json).toString())
                    if (moshi.adapter(ZoovuModel.Text::class.java).fromJson(json) != null) {
                        messages.add(Model.Message("none", moshi.adapter(ZoovuModel.Text::class.java).fromJson(json)!!.text, response.data.output?.isHorizontal!! ))
                    }
                }
            }
        }

        return Model.Conversation(payload = response.data.context, name = "none", messages = messages)*/
    }

    @ToJson
    fun responseToJson(conversation: Model.Conversation):ResponseBody {
        var input = ZoovuModel.Input(
          //  SimpleDateFormat("yyyy-MM-ddTHH:mm:ss.SSSZ").format(Date()),
            conversation.messages.last().text
        )
        Log.d("STRING_CHECK", "CHECK")

        return ResponseBody.create(null,"")
    }
}