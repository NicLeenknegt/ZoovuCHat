package com.zoovu.zuuvochat.domain.adapters.zoovu

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.json.JSONObject

class TextAdapter {

    @FromJson
    fun textFromJson(anyArray: ArrayList<Any>): ArrayList<ZoovuModel.Text> {
        var textArray = ArrayList<ZoovuModel.Text>()
        for (any in anyArray) {
            if (any is String) {
                textArray.add(ZoovuModel.Text(null, any))
            } else {
                textArray.add(any as ZoovuModel.Text)
            }
        }
        return textArray

    }

    @ToJson
    fun textToJson(textArray: ArrayList<ZoovuModel.Text>): ArrayList<Any> {

        return textArray as ArrayList<Any>
    }
}