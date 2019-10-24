package com.zoovu.zuuvochat.domain.adapters.zoovu_v2.render_types

import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.Type
import com.zoovu.zuuvochat.utils.render_factory.render_builder.MessageBuilder
import com.zoovu.zuuvochat.utils.render_factory.render_builder.RenderBuilder
import com.zoovu.zuuvochat.utils.render_factory.render_types.RenderType
import org.json.JSONArray
import org.json.JSONObject

class ImageRender(private val input:JSONArray):RenderType<ArrayList<Model.Message>> {

    private var messages:ArrayList<Model.Message> = arrayListOf()

    override fun render(): MessageBuilder {
        for (i in 0 until input.length()) {
            var json = input.get(i)
            if (json is JSONObject) {
                if (json.has("messageType")) {
                    if (json.getString("messageType").toLowerCase() == "image" && json.getString("renderType").toLowerCase() == "list") {
                        if (json.has("question") && !json.isNull("question")) {
                            messages.add(
                                Model.Message(
                                    "none",
                                    json.getString("question"),
                                    Type.TEXT
                                )
                            )
                        }
                        if (json.has("images")) {
                            var jsonArray = json.getJSONArray("images")
                            for (x in 0 until jsonArray.length()) {
                                var img = jsonArray.getJSONObject(x)
                                messages.add(
                                    Model.Message(
                                        "none",
                                        img.getString("text"),
                                        Type.SINGLE_IMAGE,
                                        url = img.getString("url")
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
        return MessageBuilder(messages)
    }
}