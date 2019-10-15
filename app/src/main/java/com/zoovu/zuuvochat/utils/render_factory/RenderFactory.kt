package com.zoovu.zuuvochat.utils.render_factory

import com.zoovu.zuuvochat.utils.render_factory.render_types.RenderPicker
import com.zoovu.zuuvochat.utils.render_factory.render_types.RenderType
import java.lang.Exception

class RenderFactory {
    private val renderPicker = RenderPicker()

    fun getType(type:String):RenderType{
        return renderPicker.getRenderType(type) ?: throw Exception("Type not found")
    }
}