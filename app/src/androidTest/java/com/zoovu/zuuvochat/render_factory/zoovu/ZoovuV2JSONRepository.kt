package com.zoovu.zuuvochat.render_factory.zoovu

import org.json.JSONArray
import org.json.JSONObject

class ZoovuV2JSONRepository {

    fun getButtonJsonArray(count: Int = 1):JSONArray {
        val buttons = JSONObject()
        buttons.put("messageType", "BUTTON")
        buttons.put("question", "Lorem ipsum")
        val button = JSONObject()
        button.put("title", "Lorem ipsum")
        button.put("text", "Lorem ipsum")
        buttons.put("buttons", getRepeatedJsonArray(button, count))
        return getRepeatedJsonArray(buttons)
    }

    fun getImageJsonArray(count: Int = 1,renderType:String = "LIST"):JSONArray {
        val images = getImageQuestionJson(renderType)
        val image = JSONObject()
        image.put("url", "katfoto")
        image.put("text", "Lorem ipsum")
        images.put("images", getRepeatedJsonArray(image, count))
        return getRepeatedJsonArray(images)
    }

    fun getImageNoQuestionJsonArray(count: Int = 1,renderType:String = "LIST"):JSONArray {
        val images = getImageQuestionJson(renderType)
        images.put("question", null)
        val image = JSONObject()
        image.put("url", "katfoto")
        image.put("text", "Lorem ipsum")
        images.put("images", getRepeatedJsonArray(image, count))
        return getRepeatedJsonArray(images)
    }

    private fun getImageQuestionJson(renderType:String = "LIST"):JSONObject {
        val images = JSONObject()
        images.put("messageType", "IMAGE")
        images.put("renderType", renderType)
        images.put("question", "Lorem ipsum")
        return images
    }

    fun getImageQuestionJsonArray(count: Int = 1, renderType:String = "LIST"):JSONArray {
        return getRepeatedJsonArray(getImageQuestionJson(renderType), count)
    }

    fun getTextJsonArray(count: Int = 1):JSONArray {
        val texts = JSONObject()
        texts.put("messageType", "TEXT")
        val message = JSONObject()
        message.put("text", "Lorem ipsum")
        val messages = getRepeatedJsonArray(message, count)
        texts.put("messages", messages)
        return getRepeatedJsonArray(texts)
    }

    fun getFaultyTextJsonArray(count: Int = 1):JSONArray {
        val texts = JSONObject()
        texts.put("messageType", "TEXT")
        val message = JSONObject()
        message.put("text", null)
        val messages = getRepeatedJsonArray(message, count)
        texts.put("messages", messages)
        return getRepeatedJsonArray(texts)
    }

    private fun getRepeatedJsonArray(json:JSONObject, count:Int = 1):JSONArray {
        val jsonArray = JSONArray()
        for (i in 0 until count) {
            jsonArray.put(json)
        }
        return jsonArray
    }
}