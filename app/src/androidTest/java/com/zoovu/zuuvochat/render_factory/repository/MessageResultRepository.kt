package com.zoovu.zuuvochat.render_factory.repository

import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.Type

class MessageResultRepository {

    fun getRepeatedTextMessage(count:Int = 1):ArrayList<Model.Message> {
        val messages: ArrayList<Model.Message> = arrayListOf()
        for (i in 0 until count) {
            messages.add(Model.Message(
                "none",
                "Lorem ipsum",
                Type.TEXT
            ))
        }
        return messages
    }

    fun getRepeatedSingleImageMessage(count:Int = 1):ArrayList<Model.Message> {
        val messages: ArrayList<Model.Message> = arrayListOf()
        for (i in 0 until count) {
            messages.add(Model.Message(
                "none",
                "Lorem ipsum",
                Type.SINGLE_IMAGE,
                url = "katfoto"
            ))
        }
        return messages
    }

    fun getRepeatedSingleImageMessageWithQuestion(count: Int = 1):ArrayList<Model.Message> {
        val messages = getRepeatedSingleImageMessage(count)
        messages.add(0,Model.Message(
            "none",
            "Lorem ipsum",
            Type.TEXT
        ))
        return messages
    }

    fun getRepeatedMultipleImageImagesMessage(count: Int = 1):ArrayList<Model.Message> {
        val messages: ArrayList<Model.Message> = arrayListOf()
        val message = Model.Message(
            "none",
            "",
            type = Type.MULTIPLE_IMAGES
        )
        for (i in 0 until count) {
            message.images.add(
                Model.Image(
                    "Lorem ipsum",
                    "katfoto"
                )
            )
        }
        messages.add(message)
        return messages
    }

    fun getRepeatedMultipleImageMessage(count: Int = 1): ArrayList<Model.Message> {
        val messages: ArrayList<Model.Message> = arrayListOf()
        val question = Model.Message(
            "none",
            "Lorem ipsum",
            Type.TEXT
        )
        val message = Model.Message(
            "none",
            "",
            type = Type.MULTIPLE_IMAGES
        )
        for (i in 0 until count) {
            message.images.add(
                Model.Image(
                    "Lorem ipsum",
                    "katfoto"
                )
            )
        }
        messages.add(question)
        messages.add(message)
        return messages
    }

    fun getRepeatedButtonMessage(count: Int = 1):ArrayList<Model.Message> {
        val messages: ArrayList<Model.Message> = arrayListOf()
        val message = Model.Message(
                "none",
                "Lorem ipsum",
                Type.BUTTON_QUESTION
            )
        for (i in 0 until count) {
            message.buttons.add(Model.Button(
                "Lorem ipsum",
                "Lorem ipsum"
            ))
        }
        messages.add(message)
        return messages
    }
}