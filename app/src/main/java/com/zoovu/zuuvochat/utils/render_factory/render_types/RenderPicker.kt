package com.zoovu.zuuvochat.utils.render_factory.render_types

class RenderPicker {

    fun getRenderType(type:String):RenderType? {
        when(type.toUpperCase()) {
            "TEXT" -> return TextRender()
            "BUTTON" -> return ButtonRender()
        }
        return null
    }
}