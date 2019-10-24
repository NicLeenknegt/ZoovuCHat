package com.zoovu.zuuvochat.domain.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.Type
import com.zoovu.zuuvochat.domain.services.SubscribeService
import com.zoovu.zuuvochat.injection.domain.InjectedViewModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class ConversationViewModel: InjectedViewModel() {

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
                Log.d("API", "ERROR: CHECK")
            }

            override fun onComplete() {
                Log.d("API", "COMPLETE")
            }
        }
    }


    private fun getConversationReplyObserver(): Observer<Model.Conversation> {
        return object : Observer<Model.Conversation> {

            override fun onSubscribe(d: Disposable) {
                var conversation = selectedConversation.value!!
                conversation.messages.add(Model.Message("none", "none", Type.LOADING))
                selectedConversation.postValue(conversation)
            }

            override fun onNext(replyConversation: Model.Conversation) {
                var conversation = selectedConversation.value!!
                replyConversation.messages.addAll(0, conversation.messages)
                replyConversation.messages = ArrayList(replyConversation.messages.filter { it.type != Type.LOADING })
                selectedConversation.postValue(replyConversation)
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

        subscribeService.sendReply("cjwix19y2007clrqrtyagcfjz", selectedConversation.value!!, getConversationReplyObserver())
    }
}