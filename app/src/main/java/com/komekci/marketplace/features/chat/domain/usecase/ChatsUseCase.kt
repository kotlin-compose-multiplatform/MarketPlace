package com.komekci.marketplace.features.chat.domain.usecase

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.chat.data.entity.CreateChat
import com.komekci.marketplace.features.chat.data.entity.CreateChatRequest
import com.komekci.marketplace.features.chat.data.entity.SendMessage
import com.komekci.marketplace.features.chat.data.entity.SendMessageRequest
import com.komekci.marketplace.features.chat.data.entity.unread.GetUnreadMessagesItem
import com.komekci.marketplace.features.chat.domain.model.ChatHistoryModel
import com.komekci.marketplace.features.chat.domain.model.ChatsModel
import com.komekci.marketplace.features.chat.domain.repository.ChatsRepository
import kotlinx.coroutines.flow.Flow

class ChatsUseCase(
    private val repository: ChatsRepository
) {
    suspend operator fun invoke(token: String): Flow<Resource<List<ChatsModel>>> = repository.getChats(token)
    suspend fun getHistory(token: String, roomId: String): Flow<Resource<List<ChatHistoryModel>>> = repository.getChatHistory(token, roomId)
    suspend fun sendMessage(token: String, message: String, roomId: String): Flow<Resource<SendMessage>> {
        return repository.sendMessage(
            token = token,
            body = SendMessageRequest(message),
            roomId = roomId
        )
    }
    suspend fun createChat(token: String, storeId: Int): Flow<Resource<CreateChat>> {
        return repository.createChat(token, CreateChatRequest(storeId))
    }
    suspend fun getUnreadMessages(token: String): Flow<Resource<List<GetUnreadMessagesItem>>> {
        return repository.getUnreadMessages(token)
    }
}