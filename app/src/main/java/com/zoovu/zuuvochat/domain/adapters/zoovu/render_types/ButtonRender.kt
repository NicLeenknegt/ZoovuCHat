package com.zoovu.zuuvochat.domain.adapters.zoovu.render_types

import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.Type
import com.zoovu.zuuvochat.utils.render_factory.render_builder.MessageBuilder
import com.zoovu.zuuvochat.utils.render_factory.render_types.RenderType
import org.json.JSONObject

class ButtonRender(private var json:JSONObject):
    RenderType<ArrayList<Model.Message>> {

    private var messages:ArrayList<Model.Message> = arrayListOf()



    override fun render(): MessageBuilder {

        if (json.has("buttonQuestion") && !json.isNull("buttonQuestion")) {

            var buttons:ArrayList<Model.Button> = arrayListOf()
            var message = Model.Message(
                    "none",
                    json.getString("buttonQuestion").toString(),
                    Type.BUTTON_QUESTION,
                    json.getBoolean("isHorizontal")
                )


            val jsonArray = json.getJSONArray("text")
            for (i in 0 until jsonArray.length()) {
                if (jsonArray.get(i) !is String){
                    val jsonElement:JSONObject = jsonArray.getJSONObject(i)
                    if (jsonElement.has("title")) {
                        buttons.add(
                            Model.Button(
                                jsonElement.getString("title").toString(),
                                jsonElement.getString("text").toString()
                            )
                        )
                    }
                }
            }
            message.buttons = buttons
            messages = arrayListOf(message)
        }
        return MessageBuilder(messages)
    }


}