package com.zoovu.zuuvochat.utils.render_factory.render_builder

class BuilderCombiner {

    /**
     * Combines all given renderBuilders into one RenderBuilder via combine function of each renderBuilder.
     */
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

    /**
     *  BuilderCombiner => RenderBuilder
     */
}