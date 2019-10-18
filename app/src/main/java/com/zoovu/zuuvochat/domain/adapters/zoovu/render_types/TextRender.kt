package com.zoovu.zuuvochat.domain.adapters.zoovu.render_types

import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.Type
import com.zoovu.zuuvochat.utils.render_factory.render_builder.MessageBuilder
import com.zoovu.zuuvochat.utils.render_factory.render_types.RenderType
import org.json.JSONObject

class TextRender(private var json:JSONObject):
    RenderType<ArrayList<Model.Message>> {

    private var messages:ArrayList<Model.Message> = arrayListOf()

    override fun render():MessageBuilder{
        val jsonArray = json.getJSONArray("text")
        for (i in 0 until jsonArray.length()) {
            val value = jsonArray.get(i)
            if (value is String) {
                messages.add(
                    Model.Message(
                        "none",
                        value,
                        Type.TEXT
                    )
                )
            }
        }
        return MessageBuilder(messages)
    }
}