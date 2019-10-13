package com.zoovu.zuuvochat.injection

import android.app.Application
import com.zoovu.zuuvochat.injection.component.DaggerViewModelComponent
//import com.zoovu.zuuvochat.injection.component.DaggerViewModelComponent
import com.zoovu.zuuvochat.injection.component.ViewModelComponent
import com.zoovu.zuuvochat.injection.module.NetworkModule

open class App:Application() {

    companion object {
        lateinit var component:ViewModelComponent
    }

    override fun onCreate() {
        super.onCreate()
        initComponent()
    }

    open fun initComponent() {
       component =
            DaggerViewModelComponent
                .builder()
                .networkModule(NetworkModule())
                .build()
    }
}