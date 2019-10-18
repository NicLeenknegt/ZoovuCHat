package com.zoovu.zuuvochat.injection.component

import com.zoovu.zuuvochat.domain.viewmodels.ConversationViewModel
import com.zoovu.zuuvochat.fragments.chat_room.chat_room_list.ChatRecyclerViewAdapter
import com.zoovu.zuuvochat.injection.App
import com.zoovu.zuuvochat.injection.module.NetworkModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class])
interface ViewModelComponent {
    fun inject(app: App)
    fun inject(conversationViewModel: ConversationViewModel)
}