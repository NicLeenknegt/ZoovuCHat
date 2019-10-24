package com.zoovu.zuuvochat.utils.render_factory

import com.zoovu.zuuvochat.utils.render_factory.render_picker.RenderPicker
import com.zoovu.zuuvochat.utils.render_factory.render_picker.RenderSelector
import java.lang.UnsupportedOperationException

class RenderFactory(vararg renderPickers:RenderPicker) {
    private val renderPickers:ArrayList<RenderPicker> = arrayListOf(*renderPickers)

    /**
     * goes over every renderpicker to determine whether input type is supported.
     * Returns RenderSelector if input type is supported.
     */
    fun setInput(input:Any):RenderSelector {
        for (renderPicker in renderPickers) {
            if (renderPicker.setInput(input) != null) {
                return renderPicker.setInput(input)!!
            }
        }
        throw UnsupportedOperationException("Input type not supported at RenderFactory()")
    }

    /**
     *  RenderFactory => RenderPicker => RenderType => RenderSelector => BuilderCombiner => RenderBuilder
     */
}