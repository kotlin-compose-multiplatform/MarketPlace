package com.komekci.marketplace.features.create_store.presentation.state

import androidx.annotation.Keep
import com.komekci.marketplace.features.auth.data.entity.Message
import com.komekci.marketplace.features.create_store.data.entity.store.MyStore

@Keep
data class MyStoreState(
    val loading: Boolean = true,
    val error: String? = null,
    val messages: List<Message>? = emptyList(),
    val code: Int = 500,
    val data: MyStore? = null
) {
    fun isPayed(): Boolean {
        if(data == null) return false
        if(data.deadlineTimestamp == null) return false
        return isPaymentDeadlineExpired(data.deadlineTimestamp).not()
    }

    fun isPaymentDeadlineExpired(deadlineTimestamp: Long): Boolean {
        // Convert deadline timestamp to milliseconds
        val deadlineMillis = deadlineTimestamp * 1000
        // Get the current time in milliseconds
        val currentTimeMillis = System.currentTimeMillis()
        // Check if the deadline has passed
        return currentTimeMillis > deadlineMillis
    }
}
