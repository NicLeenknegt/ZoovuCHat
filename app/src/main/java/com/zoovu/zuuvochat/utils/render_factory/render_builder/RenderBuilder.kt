package com.zoovu.zuuvochat.utils.render_factory.render_builder

interface RenderBuilder<T> {
    /**
     * returns object of type T.
     * Can contain logic for determining how build of combined renderBuilder gets added to the build of this renderBuilder.
     */
    fun build():T

    /**
     * Function that accepts an RenderBuilder.
     * Can contain logic to save given renderBuilder.
     */
    fun combine(renderBuilder: RenderBuilder<*>)
}