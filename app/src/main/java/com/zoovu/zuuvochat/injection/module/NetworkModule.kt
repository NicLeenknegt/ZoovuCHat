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
import com.zoovu.zuuvochat.domain.adapters.zoovu.ZoovuAdapter
import com.zoovu.zuuvochat.domain.services.SubscribeService


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
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
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