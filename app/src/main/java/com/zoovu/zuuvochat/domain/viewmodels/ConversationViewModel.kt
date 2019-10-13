package com.zoovu.zuuvochat.domain.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.zoovu.zuuvochat.data.APIService.ConversationApiService
import com.zoovu.zuuvochat.domain.Model
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ConversationViewModel:InjectedViewModel() {

    var conversations = MutableLiveData<ArrayList<Model.Conversation>>()
    var toastMessage = MutableLiveData<String>()
    var selectedConversation = MutableLiveData<Model.Conversation>()
    var navigationState = MutableLiveData<String>()

    private lateinit var subscription:Disposable

    @Inject
    lateinit var conversationApiService: ConversationApiService

    init {
        conversations.value = arrayListOf()
    }

    fun startConversation() {
        toastMessage.postValue(null)
        if (selectedConversation.value != null) {
            subscription = conversationApiService.startConversation(selectedConversation.value!!.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        Log.d("API", "ERROR" + result)
                        navigationState.postValue("CHAT_ROOM")
                        selectedConversation.postValue(result)
                    },
                    { error -> Log.d("API", "ERROR" + error.message) }
                )

        }
    }

    fun sendReply(message: Model.Message) {
        var conversation = selectedConversation.value
        conversation!!.messages.add(message)
        selectedConversation.postValue(conversation)
        Log.d("API", "START")
        subscription = conversationApiService.sendReply(selectedConversation.value!!.id, conversation)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    result ->

                        Log.d("API", "RESULT: " + result)
                        var conversation = selectedConversation.value
                        conversation!!.messages.addAll(result.messages)
                        selectedConversation.postValue(conversation)
                },
                {
                    error ->
                        Log.d("API", "ERROR: " + error.message)
                }
            )
    }
}