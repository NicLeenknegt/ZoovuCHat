package com.zoovu.zuuvochat.utils.render_factory.render_types

import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.utils.render_factory.render_builder.MessageBuilder
import org.json.JSONObject

class TextRender:RenderType {

    private var messages:ArrayList<Model.Message> = arrayListOf()

    override fun renderMessage(json: JSONObject):MessageBuilder{
        val jsonArray = json.getJSONArray("text")
        for (i in 0 until jsonArray.length()) {
            messages.add(
                Model.Message(
                    "none",
                    jsonArray.get(i).toString()
                )
            )
        }
        return MessageBuilder(messages)
    }
}