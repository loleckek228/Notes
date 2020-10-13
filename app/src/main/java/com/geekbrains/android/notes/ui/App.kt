package com.geekbrains.android.notes.ui

import android.app.Application
import com.geekbrains.android.notes.di.appModule
import com.geekbrains.android.notes.di.mainModule
import com.geekbrains.android.notes.di.noteModule
import com.geekbrains.android.notes.di.splashModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin { modules(appModule, mainModule, noteModule, splashModule) }
    }
}