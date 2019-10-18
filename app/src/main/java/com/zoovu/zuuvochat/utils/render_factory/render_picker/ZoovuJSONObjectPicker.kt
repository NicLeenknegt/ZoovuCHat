package com.zoovu.zuuvochat.utils.render_factory.render_picker

import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.utils.render_factory.render_types.ButtonRender
import com.zoovu.zuuvochat.utils.render_factory.render_types.ImageRender
import com.zoovu.zuuvochat.utils.render_factory.render_types.MultpleImagesRender
import com.zoovu.zuuvochat.utils.render_factory.render_types.TextRender
import org.json.JSONObject

class ZoovuJSONObjectPicker:RenderPicker {

    override fun setInput(input: Any):RenderSelector? {
        return if (input is JSONObject)
            RenderSelector(
                TextRender(input),
                ButtonRender(input),
                ImageRender(input),
                MultpleImagesRender(input)
            )
        else null
    }
}