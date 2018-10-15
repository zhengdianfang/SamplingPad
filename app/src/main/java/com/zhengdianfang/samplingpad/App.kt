package com.zhengdianfang.samplingpad

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.zhengdianfang.samplingpad.api.ApiClient
import okhttp3.OkHttpClient
import timber.log.Timber
import java.io.InputStream

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        timber()

        val factory = OkHttpUrlLoader.Factory(ApiClient.okHttpClient)
        Glide.get(this).registry.replace(GlideUrl::class.java, InputStream::class.java, factory)
    }

    private fun timber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    inner class ReleaseTree : Timber.DebugTree()
}