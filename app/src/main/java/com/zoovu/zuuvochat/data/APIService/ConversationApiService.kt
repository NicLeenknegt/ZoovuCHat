package com.zoovu.zuuvochat.data.APIService

import retrofit2.http.POST
import retrofit2.http.Path
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers

interface ConversationApiService {

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("{path}")
    fun startConversation(@Path(value = "path", encoded = true) path:String):Observable<ResponseBody>

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("{path}")
    fun sendReply(@Path(value = "path", encoded = true) path:String, @Body conversation: RequestBody):Observable<ResponseBody>
}