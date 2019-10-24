package com.zoovu.zuuvochat.domain.adapters.zoovu_v2.render_picker

import com.zoovu.zuuvochat.domain.adapters.zoovu_v2.render_types.ButtonRender
import com.zoovu.zuuvochat.domain.adapters.zoovu_v2.render_types.ImageRender
import com.zoovu.zuuvochat.domain.adapters.zoovu_v2.render_types.MultipleImageRender
import com.zoovu.zuuvochat.domain.adapters.zoovu_v2.render_types.TextRender
import com.zoovu.zuuvochat.utils.render_factory.render_picker.RenderPicker
import com.zoovu.zuuvochat.utils.render_factory.render_picker.RenderSelector
import org.json.JSONArray

class ZoovuV2JSONObjectPicker:RenderPicker {

    override fun setInput(input: Any): RenderSelector? {
        if (input is JSONArray) {
            return RenderSelector(
                ButtonRender(input),
                TextRender(input),
                ImageRender(input),
                MultipleImageRender(input)
            )
        }
        return null
    }
}