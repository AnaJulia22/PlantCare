package com.example.plantcare

import android.app.Application
import com.example.plantcare.di.appModule
import com.example.plantcare.di.firebaseModule
import com.example.plantcare.di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PlantCareApplication: Application()  {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@PlantCareApplication)
            modules(
                appModule,
                storageModule,
                firebaseModule
            )
        }
    }
}