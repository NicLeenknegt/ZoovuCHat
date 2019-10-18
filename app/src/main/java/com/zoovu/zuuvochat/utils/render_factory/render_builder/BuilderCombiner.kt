package com.zoovu.zuuvochat.utils.render_factory.render_builder

class BuilderCombiner {

    fun render(vararg renderBuilders: RenderBuilder<*>):RenderBuilder<*> {
        var lastBuilder:RenderBuilder<*>? = null

        for (renderBuilder in renderBuilders) {
            if (lastBuilder != null) {
                renderBuilder.combine(lastBuilder)
            }
            lastBuilder = renderBuilder
        }

        return lastBuilder!!
    }

}