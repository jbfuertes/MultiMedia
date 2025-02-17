package com.exam.project

import android.app.Application
import com.exam.project.di.initKoin
import org.koin.android.ext.koin.androidContext

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@Application)
        }
    }
}