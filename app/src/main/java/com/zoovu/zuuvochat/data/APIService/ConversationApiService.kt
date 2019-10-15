package com.zoovu.zuuvochat.data.APIService

import retrofit2.http.POST
import retrofit2.http.Path
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers

interface ConversationApiService {

    @POST("conversation/{conversationId}")
    fun startConversation(@Path("conversationId") id:String):Observable<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("conversation/{conversationId}")
    fun sendReply(@Path("conversationId") id:String, @Body conversation: RequestBody):Observable<ResponseBody>
}