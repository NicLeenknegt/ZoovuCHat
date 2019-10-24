package com.zoovu.zuuvochat.domain.adapters.zoovu_v2

import com.zoovu.zuuvochat.domain.adapters.ModelAdapter
import com.zoovu.zuuvochat.domain.adapters.Module
import com.zoovu.zuuvochat.domain.adapters.zoovu_v2.render_picker.ZoovuV2JSONObjectPicker
import com.zoovu.zuuvochat.utils.render_factory.RenderFactory

class ZoovuV2Module:Module {
    //ck0w6475c00cneoqr46r4o6ua
    override fun getConversationStartPath(id: String): String {
        return "conversation/create/${id}"
    }

    override fun getSendReplyPath(id: String): String {
        return "conversation/message/${id}"
    }

    override fun getModuleAdapter(): ModelAdapter {
        return ZoovuV2Adapter(RenderFactory(ZoovuV2JSONObjectPicker()))
    }

    override fun getAPIUrl(): String {
        return "https://330lvphwr6.execute-api.ap-northeast-1.amazonaws.com/dev/"
    }
}