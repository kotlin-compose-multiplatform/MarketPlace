package com.komekci.marketplace.features.chat.data.repository

import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.core.utils.ErrorExtractor
import com.komekci.marketplace.features.chat.data.api.ChatApi
import com.komekci.marketplace.features.chat.data.entity.CreateChat
import com.komekci.marketplace.features.chat.data.entity.CreateChatRequest
import com.komekci.marketplace.features.chat.data.entity.SendMessage
import com.komekci.marketplace.features.chat.data.entity.SendMessageRequest
import com.komekci.marketplace.features.chat.data.entity.unread.GetUnreadMessagesItem
import com.komekci.marketplace.features.chat.domain.model.ChatHistoryModel
import com.komekci.marketplace.features.chat.domain.model.ChatsModel
import com.komekci.marketplace.features.chat.domain.repository.ChatsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChatsRepositoryImpl(
    private val api: ChatApi
) : ChatsRepository {
    override suspend fun getChats(token: String): Flow<Resource<List<ChatsModel>>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.getFriends(token)
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()?.store?.map { it.toUiEntity() }))
            } else {
                emit(
                    Resource.Error(
                        null,
                        ErrorExtractor.extract(result.errorBody()),
                        result.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }

    override suspend fun getUnreadMessages(token: String): Flow<Resource<List<GetUnreadMessagesItem>>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.getUnreadMessages(token)
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()))
            } else {
                emit(
                    Resource.Error(
                        null,
                        ErrorExtractor.extract(result.errorBody()),
                        result.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }

    override suspend fun getChatHistory(token: String, roomId: String): Flow<Resource<List<ChatHistoryModel>>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.getRoomMessages(token, roomId)
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()?.toUiEntity()))
            } else {
                emit(
                    Resource.Error(
                        null,
                        ErrorExtractor.extract(result.errorBody()),
                        result.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }

    override suspend fun sendMessage(
        token: String,
        body: SendMessageRequest,
        roomId: String
    ): Flow<Resource<SendMessage>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.sendMessage(token, body, roomId)
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()))
            } else {
                emit(
                    Resource.Error(
                        null,
                        ErrorExtractor.extract(result.errorBody()),
                        result.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }

    override suspend fun createChat(
        token: String,
        body: CreateChatRequest
    ): Flow<Resource<CreateChat>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.createChat(token, body)
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()))
            } else {
                emit(
                    Resource.Error(
                        null,
                        ErrorExtractor.extract(result.errorBody()),
                        result.code()
                    )
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage, null, 500))
        }
    }
}