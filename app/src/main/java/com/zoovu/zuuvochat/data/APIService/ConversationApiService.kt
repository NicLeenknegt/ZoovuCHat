package com.zoovu.zuuvochat.data.APIService

import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.adapters.zoovu.ZoovuModel
import retrofit2.http.POST
import retrofit2.http.Path
import io.reactivex.Observable
import retrofit2.http.Body

interface ConversationApiService {

    @POST("conversation/{conversationId}")
    fun startConversation(@Path("conversationId") id:String):Observable<Model.Conversation>

    @POST("conversation/{conversationId}")
    fun sendReply(@Path("conversationId") id:String, @Body conversation: Model.Conversation):Observable<Model.Conversation>
}