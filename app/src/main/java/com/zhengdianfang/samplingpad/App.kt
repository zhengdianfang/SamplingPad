package com.zhengdianfang.samplingpad

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.zhengdianfang.samplingpad.api.ApiClient
import com.zhengdianfang.samplingpad.user.entities.User
import okhttp3.OkHttpClient
import org.jetbrains.anko.defaultSharedPreferences
import timber.log.Timber
import java.io.InputStream

class App: Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    var token: String = ""
        get() {
            if (field.isNullOrEmpty()) {
                field = defaultSharedPreferences.getString("token", "")
            }
            return field
        }
        set(value) {
            defaultSharedPreferences.edit().putString("token", value).apply()
        }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
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