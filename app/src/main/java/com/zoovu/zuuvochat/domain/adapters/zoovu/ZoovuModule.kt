package com.zoovu.zuuvochat.domain.adapters.zoovu

import com.zoovu.zuuvochat.domain.adapters.ModelAdapter
import com.zoovu.zuuvochat.domain.adapters.Module
import com.zoovu.zuuvochat.utils.render_factory.RenderFactory
import com.zoovu.zuuvochat.utils.render_factory.render_picker.ViewHolderPicker
import com.zoovu.zuuvochat.utils.render_factory.render_picker.ZoovuJSONObjectPicker

class ZoovuModule:Module {
    override fun getConversationStartPath(id:String): String {
        return "conversation/${id}"
    }

    override fun getSendReplyPath(id:String): String {
        return "conversation/${id}"
    }

    override fun getModuleAdapter(): ModelAdapter {
        return ZoovuAdapter(RenderFactory(ZoovuJSONObjectPicker(),ViewHolderPicker()))
    }

    override fun getAPIUrl(): String {
        return "https://api.cleverbots.ai/api/v1/"
    }
}