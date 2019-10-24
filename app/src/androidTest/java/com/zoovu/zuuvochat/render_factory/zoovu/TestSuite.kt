package com.zoovu.zuuvochat.render_factory.zoovu

import com.zoovu.zuuvochat.render_factory.RenderFactoryTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    TextRenderTest::class,
    ImageRenderTest::class,
    ButtonRenderTest::class,
    MultipleImageRenderTest::class,
    RenderFactoryTest::class
)
class TestSuite {
}