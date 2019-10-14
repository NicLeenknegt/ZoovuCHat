package com.zoovu.zuuvochat.domain.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.google.gson.Gson
import com.zoovu.zuuvochat.data.APIService.ConversationApiService
import com.zoovu.zuuvochat.domain.Model
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
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
            subscription = conversationApiService.startConversation(selectedConversation.value!!.id!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    val data = JSONObject(it.string()).getJSONObject("data")
                    val output = data.getJSONObject("output")
                    val context:JSONObject = data.getJSONObject("context")
                    val messages:ArrayList<Model.Message> = arrayListOf()

                    when(output.getString("type")) {
                        "TEXT" -> {
                            var jsonArray = output.getJSONArray("text")
                            for (i in 0 until jsonArray.length()) {
                                messages.add(
                                    Model.Message(
                                        "none",
                                        jsonArray.get(i).toString()
                                    )
                                )
                            }
                        }
                        "BUTTON" -> {

                            messages.add(
                                Model.Message(
                                    "none",
                                    output.getString("buttonQuestion").toString(),
                                    output.getBoolean("isHorizontal")
                                )
                            )

                            var jsonArray = output.getJSONArray("text")
                            for (i in 0 until jsonArray.length()) {
                                messages.add(
                                    Model.Message(
                                        "none",
                                        jsonArray.getJSONObject(i).getString("text").toString(),
                                        output.getBoolean("isHorizontal")
                                    )
                                )
                            }
                        }
                    }
                    return@map Model.Conversation(payload = context, messages = messages, name = "none")
                }
                .subscribe(
                    { result ->
                        Log.d("API", "ERROR" + result)
                        navigationState.postValue("CHAT_ROOM")
                        selectedConversation.postValue(result)
                    },
                    { error ->
                        Log.d("API", "ERROR: " + error.message)
                    }
                )

        }
    }

    fun sendReply(message: Model.Message) {
        var conversation = selectedConversation.value
        conversation!!.messages.add(message)
        selectedConversation.postValue(conversation)
        Log.d("API", "START")
        var data = JSONObject()
        var input = JSONObject(Gson().toJson(message))

        data.put("context", conversation.payload)
        data.put("input", input)
        Log.d("STRING_CHECK", data.toString())
        var body = RequestBody.create(MediaType.parse("application/json"), data.toString())
        subscription = conversationApiService.sendReply("cjwix19y2007clrqrtyagcfjz", body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                val data = JSONObject(it.string()).getJSONObject("data")
                val output = data.getJSONObject("output")
                val context:JSONObject = data.getJSONObject("context")
                val messages:ArrayList<Model.Message> = arrayListOf()

                when(output.getString("type")) {
                    "TEXT" -> {
                        var jsonArray = output.getJSONArray("text")
                        for (i in 0 until jsonArray.length()) {
                            messages.add(
                                Model.Message(
                                    "none",
                                    jsonArray.get(i).toString()
                                )
                            )
                        }
                    }
                    "BUTTON" -> {

                        messages.add(
                            Model.Message(
                                "none",
                                output.getString("buttonQuestion").toString(),
                                output.getBoolean("isHorizontal")
                            )
                        )

                        var jsonArray = output.getJSONArray("text")
                        for (i in 0 until jsonArray.length()) {
                            messages.add(
                                Model.Message(
                                    "none",
                                    jsonArray.getJSONObject(i).getString("text").toString(),
                                    output.getBoolean("isHorizontal")
                                )
                            )
                        }
                    }
                }
                return@map Model.Conversation(payload = context, messages = messages, name = "none")
            }
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