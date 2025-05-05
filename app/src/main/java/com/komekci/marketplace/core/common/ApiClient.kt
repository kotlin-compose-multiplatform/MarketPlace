package com.komekci.marketplace.core.common

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor



class ApiClient {
    companion object {
        fun client(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
            return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        }
    }
}