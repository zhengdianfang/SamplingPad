package com.zhengdianfang.samplingpad

import android.app.Application
import android.content.Intent
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.user.LoginActivity
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.doAsync
import timber.log.Timber
import java.io.InputStream

class App: Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    private var aMapLocationClient: AMapLocationClient? = null

    var latitude = 0.0
    var longitude = 0.0


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
        initGlide()
    }

    override fun onTerminate() {
        super.onTerminate()
        aMapLocationClient?.onDestroy()
    }

    private fun initGlide() {
        val factory = OkHttpUrlLoader.Factory(ApiClient.okHttpClient)
        Glide.get(this).registry.replace(GlideUrl::class.java, InputStream::class.java, factory)
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

    private fun timber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    fun initLocationClient() {
        doAsync {
            aMapLocationClient = AMapLocationClient(this.weakRef.get())
            val aMapLocationClientOption = AMapLocationClientOption()
            aMapLocationClientOption.locationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving
            aMapLocationClient?.setLocationOption(aMapLocationClientOption)
            aMapLocationClient?.setLocationListener {
                if (it.errorCode == 0) {
                    Timber.d("location longitude: $longitude, latitude: $latitude")
                    App.INSTANCE.longitude = it.longitude
                    App.INSTANCE.latitude = it.latitude

                }
            }
            aMapLocationClient?.startLocation()
        }
    }

    inner class ReleaseTree : Timber.DebugTree()
}