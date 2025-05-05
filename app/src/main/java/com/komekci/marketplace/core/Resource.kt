package com.komekci.marketplace.core

import com.komekci.marketplace.features.auth.data.entity.Message

sealed class Resource<T>(val data: T? = null, val message: String? = null, val errorMessage: List<Message>? = null, val code: Int = 500) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String?, errorMessage: List<Message>? = null, code: Int = 500, data: T? = null): Resource<T>(data, message, errorMessage)
    class Loading<T>(val isLoading: Boolean = true): Resource<T>(null)
}