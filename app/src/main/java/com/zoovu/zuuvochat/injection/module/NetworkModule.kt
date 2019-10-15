package com.zoovu.zuuvochat.injection.module

import com.zoovu.zuuvochat.data.APIService.ConversationApiService
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.zoovu.zuuvochat.domain.adapters.ModelAdapter
import com.zoovu.zuuvochat.domain.adapters.zoovu.ResponseAdapter
import com.zoovu.zuuvochat.domain.adapters.zoovu.TextAdapter
import com.zoovu.zuuvochat.domain.adapters.zoovu.ZoovuAdapter
import com.zoovu.zuuvochat.domain.services.SubscribeService
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
open class NetworkModule {
    val API_BASE_URL = "https://api.cleverbots.ai/api/v1/"

    @Provides
    internal fun provideSubscribeService(adapter: ZoovuAdapter, conversationApiService: ConversationApiService):SubscribeService {
        return SubscribeService(adapter, conversationApiService)
    }

    @Provides
    internal fun provideModelAdapter():ZoovuAdapter {
        return ZoovuAdapter()
    }

    @Provides
    internal fun provideConversationApi(retrofit: Retrofit): ConversationApiService {
        return retrofit.create(ConversationApiService::class.java)
    }

    @Provides
    internal fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setPrettyPrinting()
            .create()
        val moshi = Moshi.Builder()
            //.add(TextAdapter())
            .add(ResponseAdapter())
            .build()
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
            //.addConverterFactory(GsonConverterFactory.create())
            //.addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }


    @Provides
    internal fun provideOkHttpClient():OkHttpClient {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
        }.build()
    }
}