package com.zoovu.zuuvochat.utils.render_factory.render_types

import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.utils.render_factory.render_builder.RenderBuilder
import org.json.JSONObject

interface RenderType<T> {
    fun render():RenderBuilder<T>
}