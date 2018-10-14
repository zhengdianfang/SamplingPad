package com.zhengdianfang.samplingpad

import android.app.Application
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        timber()
    }

    private fun timber() {
        Timber.plant(Timber.DebugTree())
    }


}