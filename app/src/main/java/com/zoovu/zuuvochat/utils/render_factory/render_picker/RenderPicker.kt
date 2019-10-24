package com.zoovu.zuuvochat.utils.render_factory.render_picker

interface RenderPicker  {
    /**
     * Executes all renderTypes.
     * Can contain code to determine which renderType gets executed.
     */
    fun setInput(input:Any):RenderSelector?


    /**
     *  RenderPicker => RenderType => RenderSelector => BuilderCombiner => RenderBuilder
     */
}