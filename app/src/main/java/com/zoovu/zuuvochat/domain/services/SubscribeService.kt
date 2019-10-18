package com.zoovu.zuuvochat.domain.services

import com.zoovu.zuuvochat.data.APIService.ConversationApiService
import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.adapters.ModelAdapter
import com.zoovu.zuuvochat.domain.adapters.Module
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import io.reactivex.internal.util.NotificationLite.disposable

class SubscribeService(
    private val apiService: ConversationApiService,
    private val module:Module
) {

    fun startConversation(id:String, observer:Observer<Model.Conversation>):Observer<Model.Conversation>{
        return apiService.startConversation(module.getConversationStartPath(id))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                return@map module.getModuleAdapter().fromResponseBody(it)
            }
            .subscribeWith(observer)
    }

    fun sendReply(id:String, conversation: Model.Conversation, observer:Observer<Model.Conversation>):Observer<Model.Conversation>{
        return apiService.sendReply(module.getSendReplyPath(id), module.getModuleAdapter().toRequestBody(conversation))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                return@map module.getModuleAdapter().fromResponseBody(it)
            }
            .subscribeWith(observer)

    }
}