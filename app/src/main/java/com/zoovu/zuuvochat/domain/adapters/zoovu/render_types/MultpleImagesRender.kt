package com.zoovu.zuuvochat.domain.adapters.zoovu.render_types

import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.Type
import com.zoovu.zuuvochat.utils.render_factory.render_builder.MessageBuilder
import com.zoovu.zuuvochat.utils.render_factory.render_builder.RenderBuilder
import com.zoovu.zuuvochat.utils.render_factory.render_types.RenderType
import org.json.JSONObject

class MultpleImagesRender(private var json: JSONObject):
    RenderType<ArrayList<Model.Message>> {

    private var messages:ArrayList<Model.Message> = arrayListOf()

    override fun render(): RenderBuilder<ArrayList<Model.Message>> {

        if (json.has("text") && !json.isNull("text") && json.getBoolean("isCarousel")) {
            val jsonArray = json.getJSONArray("text")
            val message = Model.Message(
                "none",
                null,
                Type.MULTIPLE_IMAGES
            )
            val images:ArrayList<Model.Image> = arrayListOf()

            for (i in 0 until jsonArray.length()) {

                if (jsonArray.get(i) !is String) {
                    val text = jsonArray.getJSONObject(i)

                    if (text.has("url") && !text.isNull("url")) {
                        images.add(
                            Model.Image(
                                text.getString("text"),
                                text.getString("url")
                            )
                        )
                    }
                }
            }

            message.images.addAll(images)
            messages.add(message)
        }

        return MessageBuilder(messages)
    }
}