package com.zoovu.zuuvochat.domain.adapters.zoovu_v2.render_types

import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.Type
import com.zoovu.zuuvochat.utils.render_factory.render_builder.MessageBuilder
import com.zoovu.zuuvochat.utils.render_factory.render_builder.RenderBuilder
import com.zoovu.zuuvochat.utils.render_factory.render_types.RenderType
import org.json.JSONArray
import org.json.JSONObject

class ButtonRender(private val jsonArray:JSONArray):RenderType<ArrayList<Model.Message>> {

    private val messages:ArrayList<Model.Message> = arrayListOf()

    override fun render(): MessageBuilder {
        for (i in 0 until jsonArray.length()) {

            if (jsonArray.get(i) is JSONObject) {
                var json = jsonArray.getJSONObject(i)
                if (json.getString("messageType").toLowerCase() == "button" && json.has("question") && !json.isNull("question")) {
                    var message = Model.Message(
                        "none",
                        json.getString("question"),
                        type = Type.BUTTON_QUESTION
                    )
                    if (json.has("buttons") && !json.isNull("buttons")) {
                        var jsonArray = json.getJSONArray("buttons")
                        for (x in 0 until jsonArray.length()) {
                            var jsonButton = jsonArray.getJSONObject(x)
                            message.buttons.add(
                                Model.Button(
                                    jsonButton.getString("title"),
                                    jsonButton.getString("text")
                                )
                            )
                        }
                    }
                    messages.add(message)
                }
            }
        }
        return MessageBuilder(messages)
    }

}