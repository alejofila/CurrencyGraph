package com.example.alejofila.currencygraphs

import android.app.Application
import com.example.alejofila.currencygraphs.di.appModule
import org.koin.android.ext.android.startKoin

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))


    }
}