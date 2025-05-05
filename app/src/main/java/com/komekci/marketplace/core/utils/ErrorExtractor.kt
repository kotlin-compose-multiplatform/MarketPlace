package com.komekci.marketplace.core.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.komekci.marketplace.features.auth.data.entity.ApiError
import com.komekci.marketplace.features.auth.data.entity.Message
import okhttp3.ResponseBody

class ErrorExtractor {
    companion object {
        fun extract(response: ResponseBody?): List<Message> {
            val gson = Gson()
            val type = object : TypeToken<ApiError>() {}.type
            val errorResponse: ApiError? = gson.fromJson(response?.charStream(), type)
            var result = errorResponse?.validationErrors?.map { it.message }
            if (result.isNullOrEmpty()) {
                result = listOf(
                    Message(
                        errorResponse?.message ?: "",
                        errorResponse?.message ?: "",
                        errorResponse?.message ?: ""
                    )
                )
            }
            return result
        }
    }
}