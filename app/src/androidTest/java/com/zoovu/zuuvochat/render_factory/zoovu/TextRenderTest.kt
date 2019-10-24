package com.zoovu.zuuvochat.render_factory.zoovu

import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.adapters.zoovu_v2.render_picker.ZoovuV2JSONObjectPicker
import com.zoovu.zuuvochat.render_factory.repository.MessageResultRepository
import com.zoovu.zuuvochat.utils.render_factory.RenderFactory
import org.json.JSONArray
import org.junit.Assert
import org.junit.Test

class TextRenderTest {

    private val renderFactory:RenderFactory = RenderFactory(ZoovuV2JSONObjectPicker())
    private val zoovuV2JSONRepository:ZoovuV2JSONRepository = ZoovuV2JSONRepository()
    private val messageResultRepository = MessageResultRepository()


    @Test
    fun correctOneTextResult() {
        val array:JSONArray = zoovuV2JSONRepository.getTextJsonArray()
        val messages = messageResultRepository.getRepeatedTextMessage()
        Assert.assertEquals(
            renderFactory
                .setInput(array)
                .selectMessages()
                .build(),
               messages
        )
    }

    @Test
    fun correctMultipleTextResult() {
        val array:JSONArray = zoovuV2JSONRepository.getTextJsonArray(5)
        val messages = messageResultRepository.getRepeatedTextMessage(5)
        Assert.assertEquals(
            renderFactory
                .setInput(array)
                .selectMessages()
                .build(),
                messages
        )
    }

    @Test
    fun wrongTextJson() {
        val array:JSONArray = zoovuV2JSONRepository.getFaultyTextJsonArray()
        val messages =  arrayListOf<Model.Message>()
        Assert.assertEquals(
            renderFactory
                .setInput(array)
                .selectMessages()
                .build(),
                messages
            )
    }

}