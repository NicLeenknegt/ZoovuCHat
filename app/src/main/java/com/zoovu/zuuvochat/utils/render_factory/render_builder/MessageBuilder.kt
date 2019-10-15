package com.zoovu.zuuvochat.utils.render_factory.render_builder

import com.zoovu.zuuvochat.domain.Model

class MessageBuilder(private var messages:ArrayList<Model.Message>):RenderBuilder<ArrayList<Model.Message>> {

    override fun build(): ArrayList<Model.Message> {
        return messages
    }
}