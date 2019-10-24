package com.zoovu.zuuvochat.render_factory

import com.zoovu.zuuvochat.utils.render_factory.RenderFactory
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test
import java.lang.UnsupportedOperationException

class RenderFactoryTest {

    @Test(expected = UnsupportedOperationException::class)
    fun wrongInputException() {
        RenderFactory()
            .setInput(JSONObject())

    }
}