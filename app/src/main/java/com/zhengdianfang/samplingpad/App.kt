package com.zhengdianfang.samplingpad

import android.app.Application
import android.content.Intent
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.zhengdianfang.samplingpad.api.ApiClient
import com.zhengdianfang.samplingpad.user.LoginActivity
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
                Timber.d("get new token: %s", field)
            }
            return field
        }
        set(value) {
            Timber.d("set new token: %s", value)
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

    fun logout() {
        (ApiClient.okHttpClient.cookieJar() as ApiClient.AppCookieJar).clearCookies()
        startActivity(
            Intent(this, LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
    }

    inner class ReleaseTree : Timber.DebugTree()
}