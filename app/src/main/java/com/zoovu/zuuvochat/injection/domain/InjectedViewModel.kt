package com.zoovu.zuuvochat.injection.domain

import androidx.lifecycle.ViewModel
import com.zoovu.zuuvochat.domain.viewmodels.ConversationViewModel
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