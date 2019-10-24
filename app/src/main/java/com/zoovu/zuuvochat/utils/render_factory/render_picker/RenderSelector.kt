package com.zoovu.zuuvochat.utils.render_factory.render_picker

import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.fragments.chat_room.chat_room_list.ChatRecyclerViewAdapter
import com.zoovu.zuuvochat.utils.render_factory.render_builder.BuilderCombiner
import com.zoovu.zuuvochat.utils.render_factory.render_builder.RenderBuilder
import com.zoovu.zuuvochat.utils.render_factory.render_types.RenderType

class RenderSelector(vararg renderTypes: RenderType<*>) {

    private var renderTypes: ArrayList<RenderType<*>> = arrayListOf()

    init {
        for (renderType in renderTypes) {
            this.renderTypes.add(renderType)
        }
    }

    /**
     * Determines return type as ArrayList<Model.Message>
     */
    fun selectMessages():RenderBuilder<ArrayList<Model.Message>> {
        return select() as RenderBuilder<ArrayList<Model.Message>>
    }

    /**
     * Determines return type as ChatRecyclerViewAdapter.MessageItemViewHolder
     */
    fun selectViewHolders():RenderBuilder<ChatRecyclerViewAdapter.MessageItemViewHolder> {
        return select() as RenderBuilder<ChatRecyclerViewAdapter.MessageItemViewHolder>
    }

    /**
     * Collects all renderBuilders en combines them via BuilderCombiner.
     */
    private fun select(): RenderBuilder<*> {


        val renderBuilderArray: ArrayList<RenderBuilder<*>> = arrayListOf()

        for (renderType in this.renderTypes) {
            renderBuilderArray.add(renderType.render())
        }
        return BuilderCombiner().render(*renderBuilderArray.toTypedArray())
    }


    /**
     *   RenderSelector => BuilderCombiner => RenderBuilder
     */
}