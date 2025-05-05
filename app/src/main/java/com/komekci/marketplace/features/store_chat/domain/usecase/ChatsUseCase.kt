package com.komekci.marketplace.features.store_chat.domain.usecase

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.chat.data.entity.CreateChat
import com.komekci.marketplace.features.chat.data.entity.CreateChatRequest
import com.komekci.marketplace.features.chat.data.entity.CreateKomekchiChatRequest
import com.komekci.marketplace.features.chat.data.entity.SendMessage
import com.komekci.marketplace.features.chat.data.entity.SendMessageRequest
import com.komekci.marketplace.features.chat.data.entity.komekchi.KomekchiUserSearch
import com.komekci.marketplace.features.chat.data.entity.unread.GetUnreadMessagesItem
import com.komekci.marketplace.features.chat.domain.model.ChatHistoryModel
import com.komekci.marketplace.features.chat.domain.model.ChatsModel
import com.komekci.marketplace.features.store_chat.domain.repository.StoreChatsRepository
import kotlinx.coroutines.flow.Flow

class StoreChatsUseCase(
    private val repository: StoreChatsRepository
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
    suspend fun createKomekchiChat(token: String, body: CreateKomekchiChatRequest): Flow<Resource<CreateChat>> {
        return repository.createKomekchiChat(token, body)
    }
    suspend fun getUnreadMessages(token: String): Flow<Resource<List<GetUnreadMessagesItem>>> {
        return repository.getUnreadMessages(token)
    }
    suspend fun getKomekchiList(token: String): Flow<Resource<List<KomekchiUserSearch>>> {
        return repository.getKomekchiList(token)
    }
}