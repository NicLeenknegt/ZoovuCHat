package com.zoovu.zuuvochat.render_factory.zoovu

import com.zoovu.zuuvochat.domain.adapters.zoovu_v2.render_picker.ZoovuV2JSONObjectPicker
import com.zoovu.zuuvochat.render_factory.repository.MessageResultRepository
import com.zoovu.zuuvochat.utils.render_factory.RenderFactory
import org.json.JSONArray
import org.junit.Assert
import org.junit.Test

class ButtonRenderTest {

    private val renderFactory = RenderFactory(ZoovuV2JSONObjectPicker())
    private val messageResultRepository = MessageResultRepository()
    private val zoovuV2JSONRepository = ZoovuV2JSONRepository()

    @Test
    fun correctOneButtonTest() {
        val array: JSONArray = zoovuV2JSONRepository.getButtonJsonArray()
        val messages = messageResultRepository.getRepeatedButtonMessage()
        Assert.assertEquals(
            renderFactory
                .setInput(array)
                .selectMessages()
                .build(),
            messages
        )
    }

    @Test
    fun correctMultipleButtonTest() {
        val array: JSONArray = zoovuV2JSONRepository.getButtonJsonArray(5)
        val messages = messageResultRepository.getRepeatedButtonMessage(5)
        Assert.assertEquals(
            renderFactory
                .setInput(array)
                .selectMessages()
                .build(),
            messages
        )
    }
}