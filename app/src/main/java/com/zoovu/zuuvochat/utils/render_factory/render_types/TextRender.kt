package com.zoovu.zuuvochat.utils.render_factory.render_types

import android.util.Log
import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.Type
import com.zoovu.zuuvochat.utils.render_factory.render_builder.MessageBuilder
import org.json.JSONObject

class TextRender(private var json:JSONObject):RenderType<ArrayList<Model.Message>> {

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