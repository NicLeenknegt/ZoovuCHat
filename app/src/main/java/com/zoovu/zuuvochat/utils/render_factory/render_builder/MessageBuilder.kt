package com.zoovu.zuuvochat.utils.render_factory.render_builder

import com.zoovu.zuuvochat.domain.Model

class MessageBuilder(private var messages:ArrayList<Model.Message>):RenderBuilder<ArrayList<Model.Message>> {

    private var builder:MessageBuilder? = null

    override fun build(): ArrayList<Model.Message> {
        if (builder != null) {
            messages.addAll(0,builder!!.build())
        }
        return messages
    }

    override fun combine(renderBuilder: RenderBuilder<*>) {
        builder = renderBuilder as MessageBuilder
    }
}