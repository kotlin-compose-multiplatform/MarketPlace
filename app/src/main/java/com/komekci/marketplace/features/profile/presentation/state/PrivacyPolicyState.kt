package com.komekci.marketplace.features.profile.presentation.state

import com.komekci.marketplace.features.profile.data.entity.PrivacyPolicyEntity

data class PrivacyPolicyState(
    val loading: Boolean = true,
    val result: PrivacyPolicyEntity? = null
)
