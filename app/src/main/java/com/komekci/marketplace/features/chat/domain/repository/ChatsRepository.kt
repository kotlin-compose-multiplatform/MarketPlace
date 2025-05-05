package com.komekci.marketplace.features.chat.domain.repository

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.chat.data.entity.CreateChat
import com.komekci.marketplace.features.chat.data.entity.CreateChatRequest
import com.komekci.marketplace.features.chat.data.entity.SendMessage
import com.komekci.marketplace.features.chat.data.entity.SendMessageRequest
import com.komekci.marketplace.features.chat.data.entity.unread.GetUnreadMessagesItem
import com.komekci.marketplace.features.chat.domain.model.ChatHistoryModel
import com.komekci.marketplace.features.chat.domain.model.ChatsModel
import kotlinx.coroutines.flow.Flow

interface ChatsRepository {
    suspend fun getChats(token: String): Flow<Resource<List<ChatsModel>>>
    suspend fun getUnreadMessages(token: String): Flow<Resource<List<GetUnreadMessagesItem>>>
    suspend fun getChatHistory(token: String, roomId: String): Flow<Resource<List<ChatHistoryModel>>>
    suspend fun sendMessage(token: String, body: SendMessageRequest, roomId: String): Flow<Resource<SendMessage>>
    suspend fun createChat(token: String, body: CreateChatRequest): Flow<Resource<CreateChat>>
}