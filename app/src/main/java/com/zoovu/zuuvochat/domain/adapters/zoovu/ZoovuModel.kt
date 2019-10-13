package com.zoovu.zuuvochat.domain.adapters.zoovu

import org.json.JSONObject

class ZoovuModel {

    data class JsonResponse(
        var data:Data,
        var status:String = ""
    )

    data class Data(
        var context:Context,
        var input:Input? = null,
        var output: Output? = null
    )

    data class Context(
        var conversationId:String
    )

    data class Input(
      //  var date:String,
        var text:String
    )

    data class Output(
        var buttonQuestion:String?,
        var imageQuestion:String?,
        var intent:String?,
        var type:String,
        var isCarousel:Boolean,
        var isHorizontal:Boolean,
        var text: Array<Any>
    )

    data class Text(
        var title:String?,
        var text:String
    )
}