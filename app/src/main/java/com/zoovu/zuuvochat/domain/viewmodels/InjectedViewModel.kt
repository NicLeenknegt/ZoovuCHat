package com.zoovu.zuuvochat.domain.viewmodels

import android.arch.lifecycle.ViewModel
import com.zoovu.zuuvochat.injection.App

abstract class InjectedViewModel : ViewModel() {

    init {
        inject()
    }

    private fun inject() {
        when(this) {
            is ConversationViewModel -> App.component.inject(this)
        }
    }
}