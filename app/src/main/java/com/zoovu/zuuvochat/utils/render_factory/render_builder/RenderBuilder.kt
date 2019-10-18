package com.zoovu.zuuvochat.utils.render_factory.render_builder

interface RenderBuilder<T> {
    fun build():T
    fun combine(renderBuilder: RenderBuilder<*>)
}