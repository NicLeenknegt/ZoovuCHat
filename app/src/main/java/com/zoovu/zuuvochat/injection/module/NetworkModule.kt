package com.zoovu.zuuvochat.injection.module

import com.zoovu.zuuvochat.data.APIService.ConversationApiService
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.zoovu.zuuvochat.domain.adapters.zoovu.ZoovuModule
import com.zoovu.zuuvochat.domain.services.SubscribeService


@Module
open class NetworkModule {

    @Provides
    internal fun provideModule():com.zoovu.zuuvochat.domain.adapters.Module {
        return ZoovuModule()
    }

    @Provides
    internal fun provideSubscribeService(conversationApiService: ConversationApiService, module:com.zoovu.zuuvochat.domain.adapters.Module):SubscribeService {
        return SubscribeService(conversationApiService, module)
    }

    @Provides
    internal fun provideConversationApi(retrofit: Retrofit): ConversationApiService {
        return retrofit.create(ConversationApiService::class.java)
    }

    @Provides
    internal fun provideRetrofitInstance(okHttpClient: OkHttpClient, module: com.zoovu.zuuvochat.domain.adapters.Module): Retrofit {
        return Retrofit.Builder()
            .baseUrl(module.getAPIUrl())
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