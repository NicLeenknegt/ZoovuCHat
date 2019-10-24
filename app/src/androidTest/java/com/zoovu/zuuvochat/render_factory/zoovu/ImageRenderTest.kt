package com.zoovu.zuuvochat.render_factory.zoovu

import com.zoovu.zuuvochat.domain.adapters.zoovu_v2.render_picker.ZoovuV2JSONObjectPicker
import com.zoovu.zuuvochat.render_factory.repository.MessageResultRepository
import com.zoovu.zuuvochat.utils.render_factory.RenderFactory
import org.json.JSONArray
import org.junit.Assert
import org.junit.Test

class ImageRenderTest {

    private val renderFactory:RenderFactory = RenderFactory(ZoovuV2JSONObjectPicker())
    private val messageResultRepository = MessageResultRepository()
    private val zoovuV2JSONRepository = ZoovuV2JSONRepository()

    @Test
    fun correctImageResult() {
        val array: JSONArray = zoovuV2JSONRepository.getImageJsonArray()
        val messages = messageResultRepository.getRepeatedSingleImageMessageWithQuestion()
        Assert.assertEquals(
            renderFactory
                .setInput(array)
                .selectMessages()
                .build(),
            messages
        )
    }

    @Test
    fun correctMultipleImageResult() {
        val array: JSONArray = zoovuV2JSONRepository.getImageJsonArray(5)
        val messages = messageResultRepository.getRepeatedSingleImageMessageWithQuestion(5)
        Assert.assertEquals(
            renderFactory
                .setInput(array)
                .selectMessages()
                .build(),
            messages
        )
    }

    @Test
    fun correctNoQuestionImageResult() {
        val array: JSONArray = zoovuV2JSONRepository.getImageNoQuestionJsonArray()
        val messages = messageResultRepository.getRepeatedSingleImageMessage()
        Assert.assertEquals(
            renderFactory
                .setInput(array)
                .selectMessages()
                .build(),
            messages
        )
    }

    @Test
    fun correctNoImagesImageResult() {
        val array: JSONArray = zoovuV2JSONRepository.getImageQuestionJsonArray()
        val messages = messageResultRepository.getRepeatedTextMessage()
        Assert.assertEquals(
            renderFactory
                .setInput(array)
                .selectMessages()
                .build(),
            messages
        )
    }
}