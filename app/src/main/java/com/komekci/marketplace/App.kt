package com.komekci.marketplace

import android.app.Application
import com.komekci.marketplace.core.NotificationHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()

        NotificationHelper.init(this)
    }
}