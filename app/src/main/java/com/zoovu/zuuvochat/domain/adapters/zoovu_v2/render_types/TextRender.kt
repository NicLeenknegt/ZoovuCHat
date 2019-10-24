package com.zoovu.zuuvochat.domain.adapters.zoovu_v2.render_types

import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.Type
import com.zoovu.zuuvochat.utils.render_factory.render_builder.MessageBuilder
import com.zoovu.zuuvochat.utils.render_factory.render_types.RenderType
import org.json.JSONArray

class TextRender(private var jsonArray: JSONArray):RenderType<ArrayList<Model.Message>> {

    private var messages:ArrayList<Model.Message> = arrayListOf()

    override fun render(): MessageBuilder{
        for(i in 0 until jsonArray.length()) {
            var json = jsonArray.getJSONObject(i)
            if (json.getString("messageType").toLowerCase() == "text") {
                var jsonMessages = json.getJSONArray("messages")
                for (x in 0 until jsonMessages.length()) {
                    var text = jsonMessages.getJSONObject(x)
                    if (text.has("text") && !text.isNull("text")) {
                        messages.add(
                            Model.Message(
                                "none",
                                text.getString("text"),
                                Type.TEXT
                            )
                        )
                    }

                }
            }
        }
        return MessageBuilder(messages)
    }
}