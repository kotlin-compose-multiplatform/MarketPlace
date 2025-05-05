package com.komekci.marketplace.features.chat.data.api

import com.komekci.marketplace.features.chat.data.entity.ChatFriends
import com.komekci.marketplace.features.chat.data.entity.CreateChat
import com.komekci.marketplace.features.chat.data.entity.CreateChatRequest
import com.komekci.marketplace.features.chat.data.entity.CreateKomekchiChatRequest
import com.komekci.marketplace.features.chat.data.entity.SendMessage
import com.komekci.marketplace.features.chat.data.entity.SendMessageRequest
import com.komekci.marketplace.features.chat.data.entity.Store
import com.komekci.marketplace.features.chat.data.entity.StoreChatRoot
import com.komekci.marketplace.features.chat.data.entity.history.GetRoomMessages
import com.komekci.marketplace.features.chat.data.entity.komekchi.KomekchiUserSearch
import com.komekci.marketplace.features.chat.data.entity.unread.GetUnreadMessagesItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatApi {
    @GET("client/chat/relation-users")
    suspend fun getFriends(
        @Header("authorization") token: String
    ): Response<ChatFriends?>

    @POST("client/chat/messages/{room_id}")
    suspend fun sendMessage(
        @Header("authorization") token: String,
        @Body body: SendMessageRequest,
        @Path("room_id") roomId: String
    ): Response<SendMessage?>

    @GET("client/chat/messages/{room_id}")
    suspend fun getRoomMessages(
        @Header("authorization") token: String,
        @Path("room_id") roomId: String
    ): Response<GetRoomMessages?>

    @POST("client/chat/user-room")
    suspend fun createChat(
        @Header("authorization") token: String,
        @Body body: CreateChatRequest
    ): Response<CreateChat>

    @GET("client/chat/unread-messages")
    suspend fun getUnreadMessages(
        @Header("authorization") token: String,
    ): Response<List<GetUnreadMessagesItem>?>


    //    Store
    @GET("admin/chat/relation-users")
    suspend fun getStoreFriends(
        @Header("store-authorization") token: String
    ): Response<StoreChatRoot>

    @GET("admin/chat/search")
    suspend fun getKomekchiList(
        @Header("store-authorization") token: String
    ): Response<List<KomekchiUserSearch>?>

    @POST("admin/chat/messages/{room_id}")
    suspend fun sendStoreMessage(
        @Header("store-authorization") token: String,
        @Body body: SendMessageRequest,
        @Path("room_id") roomId: String
    ): Response<SendMessage?>

    @GET("admin/chat/messages/{room_id}")
    suspend fun getStoreRoomMessages(
        @Header("store-authorization") token: String,
        @Path("room_id") roomId: String
    ): Response<GetRoomMessages?>

    @POST("admin/chat/user-room")
    suspend fun createStoreChat(
        @Header("store-authorization") token: String,
        @Body body: CreateChatRequest
    ): Response<CreateChat>

    @POST("admin/chat/user-room")
    suspend fun createHelperChat(
        @Header("store-authorization") token: String,
        @Body body: CreateKomekchiChatRequest
    ): Response<CreateChat>

    @GET("admin/chat/unread-messages")
    suspend fun getStoreUnreadMessages(
        @Header("store-authorization") token: String,
    ): Response<List<GetUnreadMessagesItem>?>

}