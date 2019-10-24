package com.zoovu.zuuvochat.render_factory.zoovu

import com.zoovu.zuuvochat.domain.adapters.zoovu_v2.render_picker.ZoovuV2JSONObjectPicker
import com.zoovu.zuuvochat.render_factory.repository.MessageResultRepository
import com.zoovu.zuuvochat.utils.render_factory.RenderFactory
import org.json.JSONArray
import org.junit.Assert
import org.junit.Test

class MultipleImageRenderTest {

    private val renderFactory = RenderFactory(ZoovuV2JSONObjectPicker())
    private val zoovuV2JSONRepository = ZoovuV2JSONRepository()
    private val messageResultRepository = MessageResultRepository()

    @Test
    fun correctOneMultipleImage() {
        val array: JSONArray = zoovuV2JSONRepository.getImageJsonArray(renderType = "CAROUSEL")
        val messages = messageResultRepository.getRepeatedMultipleImageMessage()
        Assert.assertEquals(
            renderFactory
                .setInput(array)
                .selectMessages()
                .build(),
            messages
        )
    }

    @Test
    fun correctMultipleMultipleImage() {
        val array: JSONArray =
            zoovuV2JSONRepository.getImageJsonArray(renderType = "CAROUSEL", count = 5)
        val messages = messageResultRepository.getRepeatedMultipleImageMessage(5)
        Assert.assertEquals(
            renderFactory
                .setInput(array)
                .selectMessages()
                .build(),
            messages
        )
    }

    @Test
    fun correctNoImagesMultipleImage() {
        val array: JSONArray = zoovuV2JSONRepository.getImageQuestionJsonArray(renderType = "CAROUSEL")
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
    fun correctNoQuestionMultipleImage(){
        val array: JSONArray = zoovuV2JSONRepository.getImageNoQuestionJsonArray(renderType = "CAROUSEL")
        val messages = messageResultRepository.getRepeatedMultipleImageImagesMessage()
        Assert.assertEquals(
            renderFactory
                .setInput(array)
                .selectMessages()
                .build(),
            messages
        )
    }
}