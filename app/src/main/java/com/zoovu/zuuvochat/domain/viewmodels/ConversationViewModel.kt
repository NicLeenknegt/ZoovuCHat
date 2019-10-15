package com.zoovu.zuuvochat.domain.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.zoovu.zuuvochat.data.APIService.ConversationApiService
import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.adapters.zoovu.ZoovuAdapter
import com.zoovu.zuuvochat.domain.services.SubscribeService
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class ConversationViewModel:InjectedViewModel() {

    var conversations = MutableLiveData<ArrayList<Model.Conversation>>()
    var toastMessage = MutableLiveData<String>()
    var selectedConversation = MutableLiveData<Model.Conversation>()
    var navigationState = MutableLiveData<String>()


    @Inject
    lateinit var subscribeService:SubscribeService


    init {
        conversations.value = arrayListOf()
    }

    private fun getConversationStartObserver(): Observer<Model.Conversation> {
        return object : Observer<Model.Conversation> {

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(conversation: Model.Conversation) {
                navigationState.postValue("CHAT_ROOM")
                Log.d("API", "SUCCESS")

                selectedConversation.postValue(conversation)
            }

            override fun onError(error: Throwable) {
                Log.d("API", "ERROR: " + error.message)
            }

            override fun onComplete() {
                Log.d("API", "COMPLETE")
            }
        }
    }


    private fun getConversationReplyObserver(): Observer<Model.Conversation> {
        return object : Observer<Model.Conversation> {

            override fun onSubscribe(d: Disposable) {
                Log.d("API", "SUBSCRIBE")
            }

            override fun onNext(replyConversation: Model.Conversation) {
                var conversation = selectedConversation.value
                conversation!!.messages.addAll(replyConversation.messages)
                selectedConversation.postValue(conversation)
            }

            override fun onError(error: Throwable) {
                Log.d("API", "ERROR: " + error.message)
            }

            override fun onComplete() {
                Log.d("API", "COMPLETE")
            }
        }
    }

    fun startConversation() {
        toastMessage.postValue(null)
        if (selectedConversation.value != null) {
            subscribeService.startConversation(selectedConversation.value!!.id!!, getConversationStartObserver())
        }
    }

    fun sendReply(message: Model.Message) {
        var conversation = selectedConversation.value
        conversation!!.messages.add(message)
        selectedConversation.postValue(conversation)

        subscribeService.sendReply("cjwix19y2007clrqrtyagcfjz",conversation, getConversationReplyObserver())
    }
}