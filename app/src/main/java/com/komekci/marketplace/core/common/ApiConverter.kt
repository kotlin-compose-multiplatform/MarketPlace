package com.komekci.marketplace.core.common

import retrofit2.converter.moshi.MoshiConverterFactory

class ApiConverter {
    companion object {
        val Converter = MoshiConverterFactory.create()
    }
}